package com.atguigu.ssyx.vo.order;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StockStatisticsVo implements Serializable {

    @ApiModelProperty(value = "skuId")
    private Long skuId;

    @ApiModelProperty(value = "销售价格")
    private String price;

    @ApiModelProperty(value = "销量")
    @TableField("sale")
    private Integer sale;
}
