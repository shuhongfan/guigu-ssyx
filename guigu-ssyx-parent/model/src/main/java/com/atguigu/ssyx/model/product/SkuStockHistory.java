package com.atguigu.ssyx.model.product;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(description = "SkuStockHistory")
@TableName("sku_stock_history")
public class SkuStockHistory extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "skuId")
	@TableField("sku_id")
	private Long skuId;

	@ApiModelProperty(value = "销售价格")
	@TableField("price")
	private String price;

	@ApiModelProperty(value = "库存")
	@TableField("stock")
	private Integer stock;

	@ApiModelProperty(value = "销量")
	@TableField("sale")
	private Integer sale;

	@ApiModelProperty(value = "销售日期")
	@TableField("sale_date")
	private Date saleDate;

	@ApiModelProperty(value = "仓库")
	@TableField("ware_id")
	private Long wareId;

}