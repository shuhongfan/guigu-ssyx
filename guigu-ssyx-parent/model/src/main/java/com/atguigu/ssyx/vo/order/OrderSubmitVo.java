package com.atguigu.ssyx.vo.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderSubmitVo {

	@ApiModelProperty(value = "使用预生产订单号防重")
	private String orderNo;

	@ApiModelProperty(value = "用户id")
	private Long userId;

	@ApiModelProperty(value = "团长id")
	private Long leaderId;

	@ApiModelProperty(value = "收货人姓名")
	private String receiverName;

	@ApiModelProperty(value = "收货人电话")
	private String receiverPhone;

	@ApiModelProperty(value = "下单选中的优惠券id")
	private Long couponId;

//	@ApiModelProperty("购买的sku信息")
//	private List<Long> skuIdList;
}

