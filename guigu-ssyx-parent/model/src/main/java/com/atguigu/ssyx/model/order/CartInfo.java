package com.atguigu.ssyx.model.order;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "CartInfo")
public class CartInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	private Long userId;

	@ApiModelProperty(value = "分类id")
	private Long categoryId;

	@ApiModelProperty(value = "商品类型：0->普通商品 1->秒杀商品")
	private Integer skuType;

	@ApiModelProperty(value = "是否新人专享：0->否；1->是")
	private Integer isNewPerson;

	@ApiModelProperty(value = "sku名称 (冗余)")
	private String skuName;

	@ApiModelProperty(value = "skuid")
	private Long skuId;

	@ApiModelProperty(value = "放入购物车时价格")
	private BigDecimal cartPrice;

	@ApiModelProperty(value = "数量")
	private Integer skuNum;

	@ApiModelProperty(value = "限购个数")
	private Integer perLimit;

	@ApiModelProperty(value = "图片文件")
	private String imgUrl;

	@ApiModelProperty(value = "isChecked")
	private Integer isChecked;

	@ApiModelProperty(value = "状态")
	private Integer status;

	@ApiModelProperty(value = "秒杀开始时间")
	@JsonFormat(pattern = "HH:mm:ss")
	private Date startTime;

	@ApiModelProperty(value = "秒杀结束时间")
	@JsonFormat(pattern = "HH:mm:ss")
	private Date endTime;

	@ApiModelProperty(value = "仓库")
	private Long wareId;

	@ApiModelProperty(value = "当天已购买个数")
	private Integer currentBuyNum = 0;

}