package com.atguigu.ssyx.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum ActivityType {
    FULL_REDUCTION(1,"满减"),
    FULL_DISCOUNT(2,"满量打折" );

    @EnumValue
    private Integer code ;
    private String comment ;

    ActivityType(Integer code, String comment ){
        this.code=code;
        this.comment=comment;
    }
}