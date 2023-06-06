package com.atguigu.ssyx.model.user;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(description = "Leader")
@TableName("leader")
public class Leader extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	@TableField("user_id")
	private Long userId;

	@ApiModelProperty(value = "区域id")
	@TableField("region_id")
	private Long regionId;

	@ApiModelProperty(value = "名称")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "手机号码")
	@TableField("phone")
	private String phone;

	@ApiModelProperty(value = "身份证")
	@TableField("id_no")
	private String idNo;

	@ApiModelProperty(value = "身份证图片路径")
	@TableField("id_no_url1")
	private String idNoUrl1;

	@ApiModelProperty(value = "身份证图片路径")
	@TableField("id_no_url2")
	private String idNoUrl2;

	@ApiModelProperty(value = "提货点名称")
	@TableField("take_name")
	private String takeName;

	@ApiModelProperty(value = "提货点类型；1->宝妈；2->便利店店主；3->快递站点；4->物业中心")
	@TableField("take_type")
	private String takeType;

	@ApiModelProperty(value = "省c")
	@TableField("province")
	private Long province;

	@ApiModelProperty(value = "城市")
	@TableField("city")
	private Long city;

	@ApiModelProperty(value = "区域")
	@TableField("district")
	private Long district;

	@ApiModelProperty(value = "详细地址")
	@TableField("detail_address")
	private String detailAddress;

	@ApiModelProperty(value = "经度")
	@TableField("longitude")
	private Double longitude;

	@ApiModelProperty(value = "纬度")
	@TableField("latitude")
	private Double latitude;

	@ApiModelProperty(value = "有无门店")
	@TableField("have_store")
	private Integer haveStore;

	@ApiModelProperty(value = "门店照片")
	@TableField("store_path")
	private String storePath;

	@ApiModelProperty(value = "营业时间")
	@TableField("work_time")
	private String workTime;

	@ApiModelProperty(value = "营业状态")
	@TableField("work_status")
	private Integer workStatus;

	@ApiModelProperty(value = "审核状态")
	@TableField("check_status")
	private Integer checkStatus;

	@ApiModelProperty(value = "审核时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField("check_time")
	private Date checkTime;

	@ApiModelProperty(value = "审核用户")
	@TableField("check_user")
	private String checkUser;

	@ApiModelProperty(value = "审核内容")
	@TableField("check_content")
	private String checkContent;

}