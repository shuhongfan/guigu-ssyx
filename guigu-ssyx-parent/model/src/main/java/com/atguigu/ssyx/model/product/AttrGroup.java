package com.atguigu.ssyx.model.product;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "AttrGroup")
@TableName("attr_group")
public class AttrGroup extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "组名")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "排序")
	@TableField("sort")
	private Integer sort;

	@ApiModelProperty(value = "备注")
	@TableField("remark")
	private String remark;

}