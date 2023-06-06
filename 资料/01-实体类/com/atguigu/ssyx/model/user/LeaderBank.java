package com.atguigu.ssyx.model.user;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "LeaderBank")
@TableName("leader_bank")
public class LeaderBank extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "团长ID")
	@TableField("leader_id")
	private String leaderId;

	@ApiModelProperty(value = "账户类型(微信,银行)")
	@TableField("account_type")
	private String accountType;

	@ApiModelProperty(value = "银行名称")
	@TableField("bank_name")
	private String bankName;

	@ApiModelProperty(value = "银行账号")
	@TableField("bank_account_no")
	private String bankAccountNo;

	@ApiModelProperty(value = "银行账户名")
	@TableField("bank_account_name")
	private String bankAccountName;

	@ApiModelProperty(value = "微信ID")
	@TableField("wechat_id")
	private String wechatId;

}