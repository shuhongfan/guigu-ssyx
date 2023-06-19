package com.shf.ssyx.payment.controller;

import com.shf.ssyx.common.result.Result;
import com.shf.ssyx.common.result.ResultCodeEnum;
import com.shf.ssyx.enums.PaymentType;
import com.shf.ssyx.payment.service.PaymentInfoService;
import com.shf.ssyx.payment.service.WeixinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(tags = "微信支付接口")
@RestController
@RequestMapping("/api/payment/weixin")
public class WeixinController {
    @Autowired
    private WeixinService weixinService;

    @Autowired
    private PaymentInfoService paymentInfoService;

    @ApiOperation("调用微信支付系统生成预付单")
    @GetMapping("/createJsapi/{orderNo}")
    public Result createJsapi(@PathVariable String orderNo) {
        return Result.ok(weixinService.createJsapi(orderNo));
    }

    @ApiOperation("查询支付状态")
    @GetMapping("/queryPayStatus/{orderNo}")
    public Result queryPayStatus(@PathVariable String orderNo) {
//        1. 调用微信支付系统接口查询订单支付状态
        Map<String, String> resultMap = weixinService.queryPayStatus(orderNo);

//        2.微信支付系统返回值为null,支付失败
        if (resultMap == null) {
            return Result.build(null, ResultCodeEnum.PAYMENT_FAIL);
        }

//        3.如果微信支付系统返回值，判断支付成功
//        3.1 支付成功，修改支付记录表状态：已经支付
//        3.2支付成功，修改订单记录表已经支付，库存扣减
        if ("SUCCESS".equals(resultMap.get("trade_state"))) {
            String outTradeNo = resultMap.get("out_trade_no");
            paymentInfoService.paySuccess(outTradeNo, resultMap);
            return Result.ok(null);
        }

//        4.支付中，等等
        return Result.build(null, ResultCodeEnum.PAYMENT_WAITING);
    }

}
