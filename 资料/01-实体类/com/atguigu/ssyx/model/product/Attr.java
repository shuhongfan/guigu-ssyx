package com.atguigu.ssyx.model.product;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Attr")
@TableName("attr")
public class Attr extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "属性名")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "属性录入方式：0->手工录入；1->从列表中选取")
	@TableField("input_type")
	private Integer inputType;

	@ApiModelProperty(value = "可选值列表[用逗号分隔]")
	@TableField("select_list")
	private String selectList;

	@ApiModelProperty(value = "属性分组id")
	@TableField("attr_group_id")
	private Long attrGroupId;

}