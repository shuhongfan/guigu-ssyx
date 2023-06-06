package com.atguigu.ssyx.vo.activity;

import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class SeckillSkuNoticeVo {
	
	@ApiModelProperty(value = "member_id")
	private Long memberId;

	@ApiModelProperty(value = "sku_id")
	private Long skuId;

	@ApiModelProperty(value = "活动场次id")
	private Long sessionId;

	@ApiModelProperty(value = "订阅时间")
	private Date subcribeTime;

	@ApiModelProperty(value = "发送时间")
	private Date sendTime;

	@ApiModelProperty(value = "通知方式[0-短信，1-邮件]")
	private Boolean noticeType;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "更新时间")
	private Date updateTime;

	@ApiModelProperty(value = "删除标记（0:不可用 1:可用）")
	private Integer isDeleted;


}

