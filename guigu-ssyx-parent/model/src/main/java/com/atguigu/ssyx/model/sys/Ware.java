package com.atguigu.ssyx.model.sys;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Ware")
@TableName("ware")
public class Ware extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "名称")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "省code")
	@TableField("province")
	private String province;

	@ApiModelProperty(value = "城市code")
	@TableField("city")
	private String city;

	@ApiModelProperty(value = "区域code")
	@TableField("district")
	private String district;

	@ApiModelProperty(value = "详细地址")
	@TableField("detail_address")
	private String detailAddress;

	@ApiModelProperty(value = "经度")
	@TableField("longitude")
	private String longitude;

	@ApiModelProperty(value = "纬度")
	@TableField("latitude")
	private String latitude;

}