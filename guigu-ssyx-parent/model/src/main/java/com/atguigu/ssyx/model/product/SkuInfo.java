package com.atguigu.ssyx.model.product;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(description = "SkuInfo")
@TableName("sku_info")
public class SkuInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "分类id")
	@TableField("category_id")
	private Long categoryId;

	@ApiModelProperty(value = "平台属性分组id")
	@TableField("attr_group_id")
	private Long attrGroupId;

	@ApiModelProperty(value = "商品类型：0->普通商品 1->秒杀商品")
	@TableField("sku_type")
	private Integer skuType;

	@ApiModelProperty(value = "spu名称")
	@TableField("sku_name")
	private String skuName;

	@ApiModelProperty(value = "展示图片")
	@TableField("img_url")
	private String imgUrl;

	@ApiModelProperty(value = "限购个数/每天（0：不限购）")
	@TableField("per_limit")
	private Integer perLimit;

	@ApiModelProperty(value = "上架状态：0->下架；1->上架")
	@TableField("publish_status")
	private Integer publishStatus;

	@ApiModelProperty(value = "审核状态：0->未审核；1->审核通过")
	@TableField("check_status")
	private Integer checkStatus;

	@ApiModelProperty(value = "是否新人专享：0->否；1->是")
	@TableField("is_new_person")
	private Integer isNewPerson;

	@ApiModelProperty(value = "排序")
	@TableField("sort")
	private Integer sort;

	@ApiModelProperty(value = "sku编码")
	@TableField("sku_code")
	private String skuCode;

	@ApiModelProperty(value = "价格")
	@TableField("price")
	private BigDecimal price;

	@ApiModelProperty(value = "市场价")
	@TableField("market_price")
	private BigDecimal marketPrice;

	@ApiModelProperty(value = "库存")
	@TableField("stock")
	private Integer stock;

	@ApiModelProperty(value = "锁定库存")
	@TableField("lock_stock")
	private Integer lockStock;

	@ApiModelProperty(value = "预警库存")
	@TableField("low_stock")
	private Integer lowStock;

	@ApiModelProperty(value = "销量")
	@TableField("sale")
	private Integer sale;

	@ApiModelProperty(value = "仓库")
	@TableField("ware_id")
	private Long wareId;
}