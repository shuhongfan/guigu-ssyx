package com.shf.ssyx.payment.service;

import java.util.Map;

public interface WeixinService {
    /**
     * 调用微信支付系统生成预付单
     * @param orderNo
     * @return
     */
    Map<String, String> createJsapi(String orderNo);

    /**
     * 调用微信支付系统接口查询订单支付状态
     * @param orderNo
     * @return
     */
    Map<String, String> queryPayStatus(String orderNo);
}
