package com.shf.ssyx.order.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ssyx.model.order.OrderInfo;
import com.shf.ssyx.vo.order.OrderConfirmVo;
import com.shf.ssyx.vo.order.OrderSubmitVo;

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
}
