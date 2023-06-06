package com.atguigu.ssyx.vo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "weixinVo")
public class WeixinVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String iv;
	private String encryptedData;
	private String sessionKey;
	private String openId;

}