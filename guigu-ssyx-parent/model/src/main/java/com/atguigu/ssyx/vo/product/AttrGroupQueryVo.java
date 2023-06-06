package com.atguigu.ssyx.vo.product;

import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class AttrGroupQueryVo {
	
	@ApiModelProperty(value = "组名")
	private String name;

}

