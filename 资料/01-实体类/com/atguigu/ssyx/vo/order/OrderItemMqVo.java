package com.atguigu.ssyx.vo.order;

import com.atguigu.ssyx.enums.SkuType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "OrderItem")
public class OrderItemMqVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "商品类型：0->普通商品 1->秒杀商品")
	private SkuType skuType;

	@ApiModelProperty(value = "商品sku编号")
	private Long skuId;

	@ApiModelProperty(value = "商品购买的数量")
	private Integer skuNum;

}