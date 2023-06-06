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
@ApiModel(description = "Seckill")
@TableName("seckill")
public class Seckill extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "活动标题")
	@TableField("title")
	private String title;

	@ApiModelProperty(value = "开始时间")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@TableField("start_time")
	private Date startTime;

	@ApiModelProperty(value = "结束时间")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@TableField("end_time")
	private Date endTime;

	@ApiModelProperty(value = "上下线状态")
	@TableField("status")
	private Integer status;

}