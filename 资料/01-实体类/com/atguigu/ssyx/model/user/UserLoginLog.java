package com.atguigu.ssyx.model.user;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "UserLoginLog")
@TableName("user_login_log")
public class UserLoginLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	@TableField("user_id")
	private Long userId;

	@ApiModelProperty(value = "登录ip")
	@TableField("ip")
	private String ip;

	@ApiModelProperty(value = "登录城市")
	@TableField("city")
	private String city;

	@ApiModelProperty(value = "登录类型【0-web，1-移动】")
	@TableField("type")
	private Boolean type;

}