package com.atguigu.ssyx.model.activity;

import com.atguigu.ssyx.enums.CouponRangeType;
import com.atguigu.ssyx.enums.CouponType;
import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "CouponInfo")
@TableName("coupon_info")
public class CouponInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "购物券类型 1 现金券 2 满减券")
	@TableField("coupon_type")
	private CouponType couponType;

	@ApiModelProperty(value = "优惠卷名字")
	@TableField("coupon_name")
	private String couponName;

	@ApiModelProperty(value = "金额")
	@TableField("amount")
	private BigDecimal amount;

	@ApiModelProperty(value = "使用门槛 0->没门槛")
	@TableField("condition_amount")
	private BigDecimal conditionAmount;

	@ApiModelProperty(value = "可以领取的开始日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@TableField("start_time")
	private Date startTime;

	@ApiModelProperty(value = "可以领取的结束日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@TableField("end_time")
	private Date endTime;

	@ApiModelProperty(value = "使用范围[0->全场通用；1->指定分类；2->指定商品]")
	@TableField("range_type")
	private CouponRangeType rangeType;

	@ApiModelProperty(value = "使用范围描述")
	@TableField("range_desc")
	private String rangeDesc;

	@ApiModelProperty(value = "发行数量")
	@TableField("publish_count")
	private Integer publishCount;

	@ApiModelProperty(value = "每人限领张数")
	@TableField("per_limit")
	private Integer perLimit;

	@ApiModelProperty(value = "已使用数量")
	@TableField("use_count")
	private Integer useCount;

	@ApiModelProperty(value = "领取数量")
	@TableField("receive_count")
	private Integer receiveCount;

	@ApiModelProperty(value = "过期时间")
	@TableField("expire_time")
	private Date expireTime;

	@ApiModelProperty(value = "发布状态[0-未发布，1-已发布]")
	@TableField("publish_status")
	private Boolean publishStatus;

	@TableField(exist = false)
	private String couponTypeString;
	@TableField(exist = false)
	private String rangeTypeString;

	@ApiModelProperty(value = "使用状态")
	@TableField(exist = false)
	private Integer couponStatus;

	@ApiModelProperty(value = "是否可选")
	@TableField(exist = false)
	private Integer isSelect = 0;

	@ApiModelProperty(value = "是否最优选项")
	@TableField(exist = false)
	private Integer isOptimal = 0;

	@ApiModelProperty(value = "优惠券对应的skuId列表，提交订单使用")
	@TableField(exist = false)
	private List<Long> skuIdList;
}