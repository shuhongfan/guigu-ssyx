package com.atguigu.ssyx.vo.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "OrderDeliver")
public class OrderDeliverVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "配送日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date deliverDate;

	@ApiModelProperty(value = "团长id")
	private Long leaderId;

	@ApiModelProperty(value = "司机id")
	private Long driverId;

	@ApiModelProperty(value = "司机名称")
	private String driverName;

	@ApiModelProperty(value = "司机电话")
	private String driverPhone;

	@ApiModelProperty(value = "状态（0：默认，1：已发货，2：团长收货）")
	private Integer status;

}