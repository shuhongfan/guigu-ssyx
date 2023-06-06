package com.atguigu.ssyx.vo.product;

import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class WareQueryVo {
	
	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "省code")
	private String province;

	@ApiModelProperty(value = "城市code")
	private String city;

	@ApiModelProperty(value = "区域code")
	private String district;

	@ApiModelProperty(value = "详细地址")
	private String detailAddress;

	@ApiModelProperty(value = "经度")
	private String longitude;

	@ApiModelProperty(value = "纬度")
	private String latitude;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "更新时间")
	private Date updateTime;

	@ApiModelProperty(value = "删除标记（0:不可用 1:可用）")
	private Integer isDeleted;


}

