package com.atguigu.ssyx.model.product;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Comment")
@TableName("comment")
public class Comment extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "sku_id")
	@TableField("sku_id")
	private Long skuId;

	@ApiModelProperty(value = "商品名字")
	@TableField("sku_name")
	private String skuName;

	@ApiModelProperty(value = "会员昵称")
	@TableField("nick_name")
	private String nickName;

	@ApiModelProperty(value = "用户头像")
	@TableField("icon")
	private String icon;

	@ApiModelProperty(value = "星级")
	@TableField("star")
	private Boolean star;

	@ApiModelProperty(value = "会员ip")
	@TableField("ip")
	private String ip;

	@ApiModelProperty(value = "显示状态[0-不显示，1-显示]")
	@TableField("status")
	private Boolean status;

	@ApiModelProperty(value = "点赞数")
	@TableField("follow_count")
	private Integer followCount;

	@ApiModelProperty(value = "回复数")
	@TableField("reply_count")
	private Integer replyCount;

	@ApiModelProperty(value = "评论图片/视频[json数据；[{type:文件类型,url:资源路径}]]")
	@TableField("resources")
	private String resources;

	@ApiModelProperty(value = "内容")
	@TableField("content")
	private String content;

	@ApiModelProperty(value = "评论类型[0 - 对商品的直接评论，1 - 对评论的回复]")
	@TableField("type")
	private Integer type;

}