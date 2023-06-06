package com.atguigu.ssyx.vo.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "分类")
public class CategoryVo  implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "分类id")
	private Long id;

	@ApiModelProperty(value = "分类名称")
	private String name;
}