package com.atguigu.ssyx.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum ProcessStatus {
    UNPAID(1, OrderStatus.UNPAID),
    WAITING_DELEVER(2, OrderStatus.WAITING_DELEVER),
    WAITING_LEADER_TAKE(3, OrderStatus.WAITING_TAKE),
    WAITING_USER_TAKE(4, OrderStatus.WAITING_TAKE),
    WAITING_COMMON(5, OrderStatus.WAITING_COMMON),
    FINISHED(6, OrderStatus.FINISHED),
    CANCEL(-1, OrderStatus.CANCEL),
    PAY_FAIL(-2, OrderStatus.UNPAID);

     @EnumValue
    private Integer code ;
    private OrderStatus orderStatus;

    ProcessStatus(Integer code, OrderStatus orderStatus){
        this.code=code;
        this.orderStatus=orderStatus;
    }

}
