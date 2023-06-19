package com.shf.ssyx.payment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ssyx.model.order.PaymentInfo;

import java.util.Map;

public interface PaymentInfoService extends IService<PaymentInfo> {
    /**
     * 根据orderNo查询支付记录
     * @param orderNo
     * @return
     */
    PaymentInfo getPaymentInfoByOrderNo(String orderNo);

    /**
     * 添加支付记录
     * @param orderNo
     * @return
     */
    PaymentInfo savePaymentInfo(String orderNo);

    /**
     * 修改支付记录表状态,扣减库存
     * @param outTradeNo
     * @param resultMap
     */
    void paySuccess(String outTradeNo, Map<String, String> resultMap);
}
