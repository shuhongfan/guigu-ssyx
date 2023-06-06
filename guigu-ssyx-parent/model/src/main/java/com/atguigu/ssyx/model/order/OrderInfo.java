package com.atguigu.ssyx.model.order;

import com.atguigu.ssyx.enums.OrderStatus;
import com.atguigu.ssyx.enums.ProcessStatus;
import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "OrderInfo")
@TableName("order_info")
public class OrderInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "会员_id")
	@TableField("user_id")
	private Long userId;

	@ApiModelProperty(value = "昵称")
	@TableField("nick_name")
	private String nickName;

	@ApiModelProperty(value = "订单号")
	@TableField("order_no")
	private String orderNo;

	@ApiModelProperty(value = "使用的优惠券")
	@TableField("coupon_id")
	private Long couponId;

	@ApiModelProperty(value = "订单总额")
	@TableField("total_amount")
	private BigDecimal totalAmount;

	@ApiModelProperty(value = "促销活动金额")
	@TableField("activity_amount")
	private BigDecimal activityAmount;

	@ApiModelProperty(value = "优惠券")
	@TableField("coupon_amount")
	private BigDecimal couponAmount;

	@ApiModelProperty(value = "原价金额")
	@TableField("original_total_amount")
	private BigDecimal originalTotalAmount;

	@ApiModelProperty(value = "运费")
	@TableField("feight_fee")
	private BigDecimal feightFee;

	@ApiModelProperty(value = "减免运费")
	@TableField("feight_fee_reduce")
	private BigDecimal feightFeeReduce;

	@ApiModelProperty(value = "可退款日期（签收后1天）")
	@TableField("refundable_time")
	private Date refundableTime;

	@ApiModelProperty(value = "支付方式【1->微信】")
	@TableField("pay_type")
	private Integer payType;

	@ApiModelProperty(value = "订单来源[0->小程序；1->H5]")
	@TableField("source_type")
	private Integer sourceType;

	@ApiModelProperty(value = "订单状态【0->待付款；1->待发货；2->待团长收货；3->待用户收货，已完成；-1->已取消】")
	@TableField("order_status")
	private OrderStatus orderStatus;

	@ApiModelProperty(value = "进度状态")
	@TableField("process_status")
	private ProcessStatus processStatus;

	@ApiModelProperty(value = "团长id")
	@TableField("leader_id")
	private Long leaderId;

	@ApiModelProperty(value = "团长名称")
	@TableField("leader_name")
	private String leaderName;

	@ApiModelProperty(value = "团长手机")
	@TableField("leader_phone")
	private String leaderPhone;

	@ApiModelProperty(value = "提货点名称")
	@TableField("take_name")
	private String takeName;

	@ApiModelProperty(value = "收货人姓名")
	@TableField("receiver_name")
	private String receiverName;

	@ApiModelProperty(value = "收货人电话")
	@TableField("receiver_phone")
	private String receiverPhone;

	@ApiModelProperty(value = "收货人邮编")
	@TableField("receiver_post_code")
	private String receiverPostCode;

	@ApiModelProperty(value = "省份/直辖市")
	@TableField("receiver_province")
	private String receiverProvince;

	@ApiModelProperty(value = "城市")
	@TableField("receiver_city")
	private String receiverCity;

	@ApiModelProperty(value = "区")
	@TableField("receiver_district")
	private String receiverDistrict;

	@ApiModelProperty(value = "详细地址")
	@TableField("receiver_address")
	private String receiverAddress;

	@ApiModelProperty(value = "支付时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField("payment_time")
	private Date paymentTime;

	@ApiModelProperty(value = "发货时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField("delivery_time")
	private Date deliveryTime;

	@ApiModelProperty(value = "提货时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField("take_time")
	private Date takeTime;

	@ApiModelProperty(value = "确认收货时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField("receive_time")
	private Date receiveTime;

	@ApiModelProperty(value = "订单备注")
	@TableField("remark")
	private String remark;

	@ApiModelProperty(value = "取消订单时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField("cancel_time")
	private Date cancelTime;

	@ApiModelProperty(value = "取消订单原因")
	@TableField("cancel_reason")
	private String cancelReason;

	@ApiModelProperty(value = "仓库id")
	@TableField("ware_id")
	private Long wareId;

	@ApiModelProperty(value = "团长佣金")
	@TableField("commission_amount")
	private BigDecimal commissionAmount;

	@ApiModelProperty(value = "订单项列表")
	@TableField(exist = false)
	private List<OrderItem> orderItemList;

}