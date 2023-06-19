package com.shf.ssyx.order.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ssyx.model.order.OrderInfo;
import com.shf.ssyx.vo.order.OrderConfirmVo;
import com.shf.ssyx.vo.order.OrderSubmitVo;
import com.shf.ssyx.vo.order.OrderUserQueryVo;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author shf
 * @since 2023-06-18
 */
public interface OrderInfoService extends IService<OrderInfo> {
    /**
     * 确认订单
     */
    OrderConfirmVo confirmOrder();

    /**
     * 生成订单
     * @param orderParamVo
     * @return
     */
    Long submitOrder(OrderSubmitVo orderParamVo);

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    OrderInfo getOrderInfoById(Long orderId);

    /**
     * 根据orderNo查询订单信息
     * @param orderNo
     * @return
     */
    OrderInfo getOrderInfoByOrderNo(String orderNo);

    /**
     * 订单支付成功，修改订单状态，扣减库存
     * @param orderNo
     */
    void orderPay(String orderNo);

    /**
     * 获取用户订单分页列表
     * @param pageParam
     * @param orderUserQueryVo
     * @return
     */
    IPage<OrderInfo> findUserOrderPage(Page<OrderInfo> pageParam, OrderUserQueryVo orderUserQueryVo);
}
