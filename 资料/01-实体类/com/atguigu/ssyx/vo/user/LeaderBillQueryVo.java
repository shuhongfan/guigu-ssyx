package com.atguigu.ssyx.vo.user;

import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class LeaderBillQueryVo {
	
	@ApiModelProperty(value = "团长ID")
	private String leaderId;

	@ApiModelProperty(value = "账单类型")
	private String billType;

	@ApiModelProperty(value = "业务编号")
	private String businessNo;

	@ApiModelProperty(value = "交易时间")
	private Date billTime;

	@ApiModelProperty(value = "账单金额")
	private String billAmount;

	@ApiModelProperty(value = "账单编号")
	private String billNo;

	@ApiModelProperty(value = "账单描述")
	private String billDesc;

	@ApiModelProperty(value = "交易前资金余额")
	private String balanceBefore;

	@ApiModelProperty(value = "交易后资金余额")
	private String balanceAfter;

	@ApiModelProperty(value = "账单状态")
	private Integer billStatus;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

}

