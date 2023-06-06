package com.atguigu.ssyx.model.user;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(description = "LeaderWithdraw")
@TableName("leader_withdraw")
public class LeaderWithdraw extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "团长ID")
	@TableField("leader_id")
	private String leaderId;

	@ApiModelProperty(value = "提现金额")
	@TableField("merchant_amount")
	private String merchantAmount;

	@ApiModelProperty(value = "提现状态")
	@TableField("merchant_status")
	private String merchantStatus;

	@ApiModelProperty(value = "提现失败原因")
	@TableField("fail_reason")
	private String failReason;

	@ApiModelProperty(value = "提现支付方式")
	@TableField("payment_method")
	private String paymentMethod;

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

	@ApiModelProperty(value = "提现时间")
	@TableField("withdraw_time")
	private Date withdrawTime;

	@ApiModelProperty(value = "审核时间")
	@TableField("verify_time")
	private Date verifyTime;

	@ApiModelProperty(value = "打款时间")
	@TableField("transfer_time")
	private Date transferTime;

	@ApiModelProperty(value = "提现交易编号")
	@TableField("withdraw_no")
	private String withdrawNo;

	@ApiModelProperty(value = "审核拒绝理由")
	@TableField("reject_reason")
	private String rejectReason;

	@ApiModelProperty(value = "提现成功时间")
	@TableField("complete_time")
	private Date completeTime;

	@ApiModelProperty(value = "提现金额")
	@TableField("payment_amount")
	private String paymentAmount;

	@ApiModelProperty(value = "手续费")
	@TableField("tax_amount")
	private String taxAmount;

	@ApiModelProperty(value = "备注")
	@TableField("memo")
	private String memo;

}