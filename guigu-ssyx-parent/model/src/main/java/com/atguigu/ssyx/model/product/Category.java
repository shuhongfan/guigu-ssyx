package com.atguigu.ssyx.model.product;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Category")
@TableName("category")
public class Category extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "分类名称")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "图标")
	@TableField("img_url")
	private String imgUrl;

	@ApiModelProperty(value = "父分类id")
	@TableField("parent_id")
	private Long parentId;

	@ApiModelProperty(value = "是否显示[0-不显示，1显示]")
	@TableField("status")
	private Integer status;

	@ApiModelProperty(value = "排序")
	@TableField("sort")
	private Integer sort;

}