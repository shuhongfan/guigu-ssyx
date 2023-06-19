package com.shf.ssyx.payment.service.impl;

import com.github.wxpay.sdk.WXPayUtil;
import com.shf.ssyx.common.exception.SsyxException;
import com.shf.ssyx.common.result.ResultCodeEnum;
import com.shf.ssyx.model.order.PaymentInfo;
import com.shf.ssyx.payment.service.PaymentInfoService;
import com.shf.ssyx.payment.service.WeixinService;
import com.shf.ssyx.payment.utils.ConstantPropertiesUtils;
import com.shf.ssyx.payment.utils.HttpClient;
import com.shf.ssyx.vo.user.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class WeixinServiceImpl implements WeixinService {

    @Autowired
    private PaymentInfoService paymentInfoService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 调用微信支付系统生成预付单
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, String> createJsapi(String orderNo) {
//        1.向payment_info支付记录表添加记录，目前支付状态：正在支付中
        PaymentInfo paymentInfo = paymentInfoService.getPaymentInfoByOrderNo(orderNo);
        if (paymentInfo == null) {
            paymentInfo = paymentInfoService.savePaymentInfo(orderNo);
        }

//        2.封装微信支付系统接口需要参数
        Map<String, String> paramMap = new HashMap();
        paramMap.put("appid", ConstantPropertiesUtils.APPID);
        paramMap.put("mch_id", ConstantPropertiesUtils.PARTNER);
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
        paramMap.put("body", paymentInfo.getSubject());
        paramMap.put("out_trade_no", paymentInfo.getOrderNo());
        int totalFee = paymentInfo.getTotalAmount().multiply(new BigDecimal(100)).intValue();
        paramMap.put("total_fee", String.valueOf(totalFee));
        paramMap.put("spbill_create_ip", "127.0.0.1");
        paramMap.put("notify_url", ConstantPropertiesUtils.NOTIFYURL);
        paramMap.put("trade_type", "JSAPI");

//        从redis获取openId
//			paramMap.put("openid", "o1R-t5trto9c5sdYt6l1ncGmY5iY");
        UserLoginVo userLoginVo = (UserLoginVo)redisTemplate.opsForValue().get("user:login:" + paymentInfo.getUserId());
        if(null != userLoginVo && !StringUtils.isEmpty(userLoginVo.getOpenId())) {
            paramMap.put("openid", userLoginVo.getOpenId());
        } else {
            paramMap.put("openid", "oD7av4igt-00GI8PqsIlg5FROYnI");
        }

//        3.使用HttpClient调用微信支付系统接口
        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
        //client设置参数
        try {
            client.setXmlParam(WXPayUtil.generateSignedXml(paramMap, ConstantPropertiesUtils.PARTNERKEY));
            client.setHttps(true);
            client.post();

//        4. 调用微信支付系统接口之后，返回结果prepay_id
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

//        5.封装需要数据 - 包含预付单标识 prepay_id
            Map<String, String> parameterMap = new HashMap<>();
            String prepayId = String.valueOf(resultMap.get("prepay_id"));
            String packages = "prepay_id=" + prepayId;
            parameterMap.put("appId", ConstantPropertiesUtils.APPID);
            parameterMap.put("nonceStr", resultMap.get("nonce_str"));
            parameterMap.put("package", packages);
            parameterMap.put("signType", "MD5");
            parameterMap.put("timeStamp", String.valueOf(new Date().getTime()));
            String sign = WXPayUtil.generateSignature(parameterMap, ConstantPropertiesUtils.PARTNERKEY);

//        6.返回结果
            Map<String, String> result = new HashMap();
            result.put("timeStamp", parameterMap.get("timeStamp"));
            result.put("nonceStr", parameterMap.get("nonceStr"));
            result.put("signType", "MD5");
            result.put("paySign", sign);
            result.put("package", packages);
            if(null != resultMap.get("result_code")) {
                //微信支付二维码2小时过期，可采取2小时未支付取消订单
                redisTemplate.opsForValue().set(orderNo, result, 120, TimeUnit.MINUTES);
            }
            return result;
        } catch (Exception e) {
            throw new SsyxException(ResultCodeEnum.DATA_ERROR);
        }

    }

    /**
     * 调用微信支付系统接口查询订单支付状态
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            //1、封装参数
            Map paramMap = new HashMap<>();
            paramMap.put("appid", ConstantPropertiesUtils.APPID);
            paramMap.put("mch_id", ConstantPropertiesUtils.PARTNER);
            paramMap.put("out_trade_no", orderNo);
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(paramMap, ConstantPropertiesUtils.PARTNERKEY));
            client.setHttps(true);
            client.post();

            //3、返回第三方的数据
            String xml = client.getContent();
            //4、转成Map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //5、返回
            return resultMap;
        } catch (Exception e) {
            throw new SsyxException(ResultCodeEnum.DATA_ERROR);
        }
    }
}
