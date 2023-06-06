package com.atguigu.ssyx.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "用户登录信息")
public class UserLoginVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	private Long userId;

	@ApiModelProperty(value = "会员头像")
	private String photoUrl;

	@ApiModelProperty(value = "昵称")
	private String nickName;

	@ApiModelProperty(value = "小程序open id")
	private String openId;

	@ApiModelProperty(value = "是否新用户")
	private Integer isNew;

	@ApiModelProperty(value = "当前登录用户团长id")
	private Long leaderId;

	@ApiModelProperty(value = "仓库id")
	private Long wareId;

}