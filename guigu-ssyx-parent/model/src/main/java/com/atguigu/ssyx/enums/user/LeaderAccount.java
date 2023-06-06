package com.atguigu.ssyx.enums.user;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(description = "LeaderAccount")
@TableName("leader_account")
public class LeaderAccount extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "团长ID")
	@TableField("leader_id")
	private Long leaderId;

	@ApiModelProperty(value = "总收益, 可能有部分余额因为订单未结束而不能提现")
	@TableField("total_amount")
	private BigDecimal totalAmount;

	@ApiModelProperty(value = "可提现余额")
	@TableField("available_amount")
	private BigDecimal availableAmount;

	@ApiModelProperty(value = "冻结余额")
	@TableField("frozen_amount")
	private BigDecimal frozenAmount;

}