package com.atguigu.ssyx.model.order;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(description = "OrderSet")
@TableName("order_set")
public class OrderSet extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "秒杀订单超时关闭时间(分)")
	@TableField("seckill_order_overtime")
	private Integer seckillOrderOvertime;

	@ApiModelProperty(value = "正常订单超时时间(分)")
	@TableField("normal_order_overtime")
	private Integer normalOrderOvertime;

	@ApiModelProperty(value = "发货后自动确认收货时间（天）")
	@TableField("confirm_overtime")
	private Integer confirmOvertime;

	@ApiModelProperty(value = "自动完成交易时间，不能申请售后（天）")
	@TableField("finish_overtime")
	private Integer finishOvertime;

	@ApiModelProperty(value = "佣金分成比例")
	@TableField("profit_rate")
	private BigDecimal profitRate;


}