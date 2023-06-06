package com.atguigu.ssyx.model.activity;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(description = "SeckillSku")
@TableName("seckill_sku")
public class SeckillSku extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "秒杀活动id")
	@TableField("seckill_id")
	private Long seckillId;

	@ApiModelProperty(value = "活动场次id")
	@TableField("seckill_time_id")
	private Long seckillTimeId;

	@ApiModelProperty(value = "商品id")
	@TableField("sku_id")
	private Long skuId;

	@ApiModelProperty(value = "spu名称")
	@TableField("sku_name")
	private String skuName;

	@ApiModelProperty(value = "展示图片")
	@TableField("img_url")
	private String imgUrl;

	@ApiModelProperty(value = "秒杀价格")
	@TableField("seckill_price")
	private BigDecimal seckillPrice;

	@ApiModelProperty(value = "秒杀总量")
	@TableField("seckill_stock")
	private Integer seckillStock;

	@ApiModelProperty(value = "每人限购数量")
	@TableField("seckill_limit")
	private Integer seckillLimit;

	@ApiModelProperty(value = "秒杀销量")
	@TableField("seckill_sale")
	private Integer seckillSale;

	@ApiModelProperty(value = "排序")
	@TableField("seckill_sort")
	private Integer seckillSort;

	@ApiModelProperty(value = "sku信息")
	@TableField(exist = false)
	private SkuInfo skuInfo;

}