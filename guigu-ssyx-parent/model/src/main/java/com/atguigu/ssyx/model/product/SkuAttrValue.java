package com.atguigu.ssyx.model.product;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "SkuAttrValue")
@TableName("sku_attr_value")
public class SkuAttrValue extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "商品id")
	@TableField("sku_id")
	private Long skuId;

	@ApiModelProperty(value = "属性id")
	@TableField("attr_id")
	private Long attrId;

	@ApiModelProperty(value = "属性名")
	@TableField("attr_name")
	private String attrName;

	@ApiModelProperty(value = "属性值")
	@TableField("attr_value")
	private String attrValue;

	@ApiModelProperty(value = "顺序")
	@TableField("sort")
	private Integer sort;

}