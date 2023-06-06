package com.atguigu.ssyx.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum PaymentStatus {
    UNPAID(1,"支付中"),
    PAID(2,"已支付");
    //REFUND(-1,"已退款");

    @EnumValue
    private Integer code ;
    private String comment ;

    PaymentStatus(Integer code, String comment) {
        this.code = code;
        this.comment = comment;
    }

}
