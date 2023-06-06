package com.atguigu.ssyx.model.activity;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(description = "SeckillSkuNotice")
@TableName("seckill_sku_notice")
public class SeckillSkuNotice extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "user_id")
	@TableField("user_id")
	private Long userId;

	@ApiModelProperty(value = "sku_id")
	@TableField("sku_id")
	private Long skuId;

	@ApiModelProperty(value = "活动场次id")
	@TableField("session_id")
	private Long sessionId;

	@ApiModelProperty(value = "订阅时间")
	@TableField("subcribe_time")
	private Date subcribeTime;

	@ApiModelProperty(value = "发送时间")
	@TableField("send_time")
	private Date sendTime;

	@ApiModelProperty(value = "通知方式[0-短信，1-邮件]")
	@TableField("notice_type")
	private Boolean noticeType;

}