package com.atguigu.ssyx.vo.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * OrderDetailActivity
 * </p>
 *
 * @author qy
 */
@Data
public class OrderMqVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "订单号")
	private String orderNo;

	@ApiModelProperty(value = "团长id")
	private Long leaderId;

	@ApiModelProperty(value = "团长佣金")
	private BigDecimal commissionAmount;

	@ApiModelProperty(value = "订单项列表")
	private List<OrderItemMqVo> orderItemMqVoList;


}

