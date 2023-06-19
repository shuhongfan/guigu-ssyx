package com.shf.ssyx.payment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.ssyx.common.exception.SsyxException;
import com.shf.ssyx.common.result.ResultCodeEnum;
import com.shf.ssyx.enums.PaymentStatus;
import com.shf.ssyx.enums.PaymentType;
import com.shf.ssyx.mq.constant.MqConst;
import com.shf.ssyx.mq.service.RabbitService;
import com.shf.ssyx.payment.mapper.PaymentInfoMapper;
import com.shf.ssyx.model.order.OrderInfo;
import com.shf.ssyx.model.order.PaymentInfo;
import com.shf.ssyx.order.client.OrderFeignClient;
import com.shf.ssyx.payment.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper,PaymentInfo> implements PaymentInfoService {

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Autowired
    private RabbitService rabbitService;

    /**
     * 根据orderNo查询支付记录
     * @param orderNo
     * @return
     */
    @Override
    public PaymentInfo getPaymentInfoByOrderNo(String orderNo) {
        PaymentInfo paymentInfo = baseMapper.selectOne(
                new LambdaQueryWrapper<PaymentInfo>()
                        .eq(PaymentInfo::getOrderNo, orderNo)
        );
        return paymentInfo;
    }

    /**
     * 添加支付记录
     * @param orderNo
     * @return
     */
    @Override
    public PaymentInfo savePaymentInfo(String orderNo) {
//        远程调用，根据orderNo查询订单信息
        OrderInfo orderInfo = orderFeignClient.getOrderInfo(orderNo);
        if (orderInfo == null) {
            throw new SsyxException(ResultCodeEnum.DATA_ERROR);
        }

//        封装到PaymentInfo对象
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setCreateTime(new Date());
        paymentInfo.setOrderId(orderInfo.getId());
        paymentInfo.setPaymentType(PaymentType.WEIXIN);
        paymentInfo.setUserId(orderInfo.getUserId());
        paymentInfo.setOrderNo(orderInfo.getOrderNo());
        paymentInfo.setPaymentStatus(PaymentStatus.UNPAID);
        String subject = "userId:"+orderInfo.getUserId()+"下订单";
        paymentInfo.setSubject(subject);
        paymentInfo.setTotalAmount(orderInfo.getTotalAmount());
//        paymentInfo.setTotalAmount(new BigDecimal("0.01"));

//        调用方法实现添加
        baseMapper.insert(paymentInfo);

        return paymentInfo;
    }

    /**
     * 修改支付记录表状态,扣减库存
     * @param outTradeNo
     * @param resultMap
     */
    @Override
    public void paySuccess(String outTradeNo, Map<String, String> resultMap) {
//        1. 查询当前订单支付记录表状态是否已经支付
        PaymentInfo paymentInfo = baseMapper.selectOne(
                new LambdaQueryWrapper<PaymentInfo>()
                        .eq(PaymentInfo::getOrderNo, outTradeNo)
        );
        if (paymentInfo.getPaymentStatus() != PaymentStatus.UNPAID) {
            return;
        }

//        2.如果支付记录表支付状态没有支付，更新
        paymentInfo.setPaymentStatus(PaymentStatus.PAID);
        paymentInfo.setTradeNo(resultMap.get("ransaction_id"));

//        3.整合RabbitMQ实现 修改订单记录已经支付，库存扣减
        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_TASK, MqConst.ROUTING_PAY_SUCCESS, outTradeNo);
    }
}
