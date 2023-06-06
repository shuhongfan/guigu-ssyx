package com.atguigu.ssyx.vo.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegionVo {

	@ApiModelProperty(value = "开通区域")
	private Long regionId;

	@ApiModelProperty(value = "区域名称")
	private String regionName;

}

