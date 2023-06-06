package com.atguigu.ssyx.vo.activity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "秒杀商品信息")
public class SeckillSkuVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "秒杀商品主键id")
	private Long seckillSkuId;

	@ApiModelProperty(value = "skuId")
	private Long skuId;

	@ApiModelProperty(value = "spu名称")
	private String skuName;

	@ApiModelProperty(value = "展示图片")
	private String imgUrl;

	@ApiModelProperty(value = "秒杀价格")
	private BigDecimal seckillPrice;

	@ApiModelProperty(value = "秒杀总量")
	private Integer seckillStock;

	@ApiModelProperty(value = "每人限购数量")
	private Integer seckillLimit;

	@ApiModelProperty(value = "秒杀销量")
	private Integer seckillSale;

	@ApiModelProperty(value = "场次名称")
	private String timeName;

	@ApiModelProperty(value = "每日开始时间")
	@JsonFormat(pattern = "HH:mm:ss")
	private Date startTime;

	@ApiModelProperty(value = "每日结束时间")
	@JsonFormat(pattern = "HH:mm:ss")
	private Date endTime;

	@ApiModelProperty(value = "场次状态 1：已开抢 2：抢购中 3：即将开抢")
	@TableField(exist = false)
	private Integer timeStaus;

}