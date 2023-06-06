package com.atguigu.ssyx.model.sys;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "RegionWare")
@TableName("region_ware")
public class RegionWare extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "开通区域")
	@TableField("region_id")
	private Long regionId;

	@ApiModelProperty(value = "区域名称")
	@TableField("region_name")
	private String regionName;

	@ApiModelProperty(value = "仓库")
	@TableField("ware_id")
	private Long wareId;

	@ApiModelProperty(value = "仓库名称")
	@TableField("ware_name")
	private String wareName;

	@ApiModelProperty(value = "状态（0：未开通 1：已开通）")
	@TableField("status")
	private Integer status;

}