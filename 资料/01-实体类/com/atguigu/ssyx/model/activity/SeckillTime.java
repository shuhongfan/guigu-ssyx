package com.atguigu.ssyx.model.activity;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(description = "SeckillTime")
@TableName("seckill_time")
public class SeckillTime extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "场次名称")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "每日开始时间")
	@JsonFormat(pattern = "HH:mm:ss")
	@TableField("start_time")
	private Date startTime;

	@ApiModelProperty(value = "每日结束时间")
	@JsonFormat(pattern = "HH:mm:ss")
	private Date endTime;

	@ApiModelProperty(value = "启用状态")
	@TableField("status")
	private Integer status;

	@ApiModelProperty(value = "场次状态 1：已开抢 2：抢购中 3：即将开抢")
	@TableField(exist = false)
	private Integer timeStaus;

}