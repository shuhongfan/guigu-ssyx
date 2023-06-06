package com.atguigu.ssyx.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum OrderStatus {
    //订单状态【0->待付款；1->待发货；2->待团长收货；3->待用户收货，已完成；-1->已取消】
    UNPAID(0,"待支付"),
    WAITING_DELEVER(1,"待发货"),
    WAITING_TAKE(2,"待提货"),
    WAITING_COMMON(3,"待评论"),
    FINISHED(4,"已完结"),
    CANCEL(-1,"已取消");

    @EnumValue
    private Integer code ;
    private String comment ;

    OrderStatus(Integer code, String comment ){
        this.code = code;
        this.comment=comment;
    }
}