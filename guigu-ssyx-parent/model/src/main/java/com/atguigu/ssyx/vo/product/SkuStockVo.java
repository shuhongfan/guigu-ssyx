package com.atguigu.ssyx.vo.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SkuStockVo implements Serializable {

	@ApiModelProperty(value = "skuId")
	private Long skuId;

	@ApiModelProperty(value = "sku类型")
	private Integer skuType;

	@ApiModelProperty(value = "更新的库存数量")
	private Integer stockNum;

}

