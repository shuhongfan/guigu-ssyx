package com.atguigu.ssyx.model.activity;

import com.atguigu.ssyx.enums.ActivityType;
import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * ActivityRule
 * </p>
 *
 * @author qy
 */
@Data
@ApiModel(description = "ActivityRule")
@TableName("activity_rule")
public class ActivityRule extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "类型")
	@TableField("activity_id")
	private Long activityId;

	@ApiModelProperty(value = "活动类型（满减、折扣）")
	@TableField("activity_type")
	private ActivityType activityType;

	@ApiModelProperty(value = "满减金额")
	@TableField("condition_amount")
	private BigDecimal conditionAmount;

	@ApiModelProperty(value = "满减件数")
	@TableField("condition_num")
	private Long conditionNum;

	@ApiModelProperty(value = "优惠金额")
	@TableField("benefit_amount")
	private BigDecimal benefitAmount;

	@ApiModelProperty(value = "优惠折扣")
	@TableField("benefit_discount")
	private BigDecimal benefitDiscount;

	@ApiModelProperty(value = "活动skuId")
	@TableField(exist = false)
	private Long skuId;

	@ApiModelProperty(value = "优惠后减少金额")
	@TableField(exist = false)
	private BigDecimal reduceAmount;

	@ApiModelProperty(value = "选中类型：1：去凑单 2：逛一逛")
	@TableField(exist = false)
	private Integer selectType;

	@ApiModelProperty(value = "规则优惠描述")
	@TableField(exist = false)
	private String ruleDesc;

}

