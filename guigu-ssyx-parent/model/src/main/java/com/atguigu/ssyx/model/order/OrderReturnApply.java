package com.atguigu.ssyx.model.order;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(description = "OrderReturnApply")
@TableName("order_return_apply")
public class OrderReturnApply extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "订单id")
	@TableField("order_id")
	private Long orderId;

	@ApiModelProperty(value = "团长门店id")
	@TableField("leader_id")
	private Long leaderId;

	@ApiModelProperty(value = "退货商品id")
	@TableField("sku_id")
	private Long skuId;

	@ApiModelProperty(value = "订单编号")
	@TableField("order_sn")
	private String orderSn;

	@ApiModelProperty(value = "会员用户名")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "退款金额")
	@TableField("return_amount")
	private String returnAmount;

	@ApiModelProperty(value = "退货人姓名")
	@TableField("return_name")
	private String returnName;

	@ApiModelProperty(value = "退货人电话")
	@TableField("return_phone")
	private String returnPhone;

	@ApiModelProperty(value = "申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝")
	@TableField("status")
	private Integer status;

	@ApiModelProperty(value = "处理时间")
	@TableField("handle_time")
	private Date handleTime;

	@ApiModelProperty(value = "商品图片")
	@TableField("sku_pic")
	private String skuPic;

	@ApiModelProperty(value = "商品名称")
	@TableField("spu_name")
	private String spuName;

	@ApiModelProperty(value = "退货数量")
	@TableField("sku_num")
	private Integer skuNum;

	@ApiModelProperty(value = "商品单价")
	@TableField("sku_price")
	private String skuPrice;

	@ApiModelProperty(value = "商品实际支付单价")
	@TableField("sku_real_price")
	private String skuRealPrice;

	@ApiModelProperty(value = "原因")
	@TableField("reason")
	private String reason;

	@ApiModelProperty(value = "描述")
	@TableField("description")
	private String description;

	@ApiModelProperty(value = "凭证图片，以逗号隔开")
	@TableField("proof_pics")
	private String proofPics;

	@ApiModelProperty(value = "处理备注")
	@TableField("handle_note")
	private String handleNote;

	@ApiModelProperty(value = "处理人员")
	@TableField("handle_man")
	private String handleMan;

	@ApiModelProperty(value = "收货人")
	@TableField("receive_man")
	private String receiveMan;

	@ApiModelProperty(value = "收货时间")
	@TableField("receive_time")
	private Date receiveTime;

	@ApiModelProperty(value = "收货备注")
	@TableField("receive_note")
	private String receiveNote;

}