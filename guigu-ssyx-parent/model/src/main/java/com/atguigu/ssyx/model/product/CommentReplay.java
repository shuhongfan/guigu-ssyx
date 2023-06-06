package com.atguigu.ssyx.model.product;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "CommentReplay")
@TableName("comment_replay")
public class CommentReplay extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "commentId")
	@TableField("comment_id")
	private Long commentId;

	@ApiModelProperty(value = "nickName")
	@TableField("nick_name")
	private String nickName;

	@ApiModelProperty(value = "icon")
	@TableField("icon")
	private String icon;

	@ApiModelProperty(value = "content")
	@TableField("content")
	private String content;

}