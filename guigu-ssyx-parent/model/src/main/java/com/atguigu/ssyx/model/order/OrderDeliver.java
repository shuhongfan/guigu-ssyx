package com.atguigu.ssyx.model.order;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(description = "OrderDeliver")
@TableName("order_deliver")
public class OrderDeliver extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "配送日期")
	@TableField("deliver_date")
	private Date deliverDate;

	@ApiModelProperty(value = "团长id")
	@TableField("leader_id")
	private Long leaderId;

	@ApiModelProperty(value = "司机id")
	@TableField("driver_id")
	private Long driverId;

	@ApiModelProperty(value = "司机名称")
	@TableField("driver_name")
	private String driverName;

	@ApiModelProperty(value = "司机电话")
	@TableField("driver_phone")
	private String driverPhone;

	@ApiModelProperty(value = "状态（0：默认，1：已发货，2：团长收货）")
	@TableField("status")
	private Integer status;

}