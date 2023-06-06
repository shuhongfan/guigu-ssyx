package com.atguigu.ssyx.model.order;

import com.atguigu.ssyx.enums.SkuType;
import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(description = "OrderItem")
@TableName("order_item")
public class OrderItem extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "order_id")
	@TableField("order_id")
	private Long orderId;

	@ApiModelProperty(value = "商品分类id")
	@TableField("category_id")
	private Long categoryId;

	@ApiModelProperty(value = "商品类型：0->普通商品 1->秒杀商品")
	@TableField("sku_type")
	private SkuType skuType;

	@ApiModelProperty(value = "商品sku编号")
	@TableField("sku_id")
	private Long skuId;

	@ApiModelProperty(value = "商品sku名字")
	@TableField("sku_name")
	private String skuName;

	@ApiModelProperty(value = "商品sku图片")
	@TableField("img_url")
	private String imgUrl;

	@ApiModelProperty(value = "商品sku价格")
	@TableField("sku_price")
	private BigDecimal skuPrice;

	@ApiModelProperty(value = "商品购买的数量")
	@TableField("sku_num")
	private Integer skuNum;

	@ApiModelProperty(value = "商品促销活动分解金额")
	@TableField("split_activity_amount")
	private BigDecimal splitActivityAmount;

	@ApiModelProperty(value = "优惠券优惠分解金额")
	@TableField("split_coupon_amount")
	private BigDecimal splitCouponAmount;

	@ApiModelProperty(value = "该商品经过优惠后的分解金额")
	@TableField("split_total_amount")
	private BigDecimal splitTotalAmount;

	@ApiModelProperty(value = "团长id（冗余）")
	@TableField("leader_id")
	private Long leaderId;

}