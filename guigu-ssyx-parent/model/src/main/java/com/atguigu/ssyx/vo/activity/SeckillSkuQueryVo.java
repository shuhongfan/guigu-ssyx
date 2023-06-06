package com.atguigu.ssyx.vo.activity;

import lombok.Data;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

@Data
public class SeckillSkuQueryVo {
	
	@ApiModelProperty(value = "秒杀活动id")
	private Long seckillId;

	@ApiModelProperty(value = "活动场次id")
	private Long seckillTimeId;

}

