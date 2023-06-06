package com.atguigu.ssyx.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum PaymentType {
    ALIPAY(1,"支付宝"),
    WEIXIN(2,"微信" );

    @EnumValue
    private Integer code ;
    private String comment ;

    PaymentType(Integer code, String comment ){
        this.code = code;
        this.comment=comment;
    }

}
