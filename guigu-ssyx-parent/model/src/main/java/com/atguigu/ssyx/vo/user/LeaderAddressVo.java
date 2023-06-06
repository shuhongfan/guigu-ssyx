package com.atguigu.ssyx.vo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * UserAddress
 * </p>
 *
 * @author qy
 */
@Data
@ApiModel(description = "用户地址")
public class LeaderAddressVo {

	@ApiModelProperty(value = "用户id")
	private Long userId;

	@ApiModelProperty(value = "团长id")
	private Long leaderId;

	@ApiModelProperty(value = "团长名称")
	private String leaderName;

	@ApiModelProperty(value = "团长电话")
	private String leaderPhone;

	@ApiModelProperty(value = "仓库id")
	private Long wareId;

	@ApiModelProperty(value = "提货点名称")
	private String takeName;

	@ApiModelProperty(value = "省")
	private String province;

	@ApiModelProperty(value = "城市")
	private String city;

	@ApiModelProperty(value = "区域")
	private String district;

	@ApiModelProperty(value = "详细地址")
	private String detailAddress;

	@ApiModelProperty(value = "经度")
	private String longitude;

	@ApiModelProperty(value = "纬度")
	private String latitude;

	@ApiModelProperty(value = "门店照片")
	private String storePath;

}

