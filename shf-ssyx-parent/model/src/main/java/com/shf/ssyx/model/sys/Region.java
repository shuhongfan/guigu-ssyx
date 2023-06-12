package com.shf.ssyx.model.sys;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shf.ssyx.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Region")
@TableName("region")
public class Region extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "上级id")
	@TableField("parent_id")
	private Long parentId;

	@ApiModelProperty(value = "名称")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "是否包含子节点")
	@TableField(exist = false)
	private boolean hasChildren;

}