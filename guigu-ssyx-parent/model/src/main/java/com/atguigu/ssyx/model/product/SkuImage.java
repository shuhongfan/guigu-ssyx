package com.atguigu.ssyx.model.product;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "SkuImages")
@TableName("sku_image")
public class SkuImage extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "sku_id")
	@TableField("sku_id")
	private Long skuId;

	@ApiModelProperty(value = "图片名称")
	@TableField("img_name")
	private String imgName;

	@ApiModelProperty(value = "图片地址")
	@TableField("img_url")
	private String imgUrl;

	@ApiModelProperty(value = "排序")
	@TableField("sort")
	private Integer sort;

}