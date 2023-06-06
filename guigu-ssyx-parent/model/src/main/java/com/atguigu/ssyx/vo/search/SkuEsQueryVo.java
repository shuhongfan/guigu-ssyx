package com.atguigu.ssyx.vo.search;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

// 封装查询条件
@Data
public class SkuEsQueryVo {

    private Long categoryId;;//三级分类id

    private String keyword;//检索的关键字

    private Long wareId;

}
