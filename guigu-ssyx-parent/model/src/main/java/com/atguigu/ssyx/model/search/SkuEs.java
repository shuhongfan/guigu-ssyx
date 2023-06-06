package com.atguigu.ssyx.model.search;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

@Data
@Document(indexName = "skues" ,shards = 3,replicas = 1)
public class SkuEs {

    // 商品Id= skuId
    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String keyword;

    @Field(type = FieldType.Integer, index = false)
    private Integer skuType;

    @Field(type = FieldType.Integer, index = false)
    private Integer isNewPerson;

    @Field(type = FieldType.Long)
    private Long categoryId;

    @Field(type = FieldType.Text)
    private String categoryName;

    @Field(type = FieldType.Keyword, index = false)
    private String imgUrl;

    //  es 中能分词的字段，这个字段数据类型必须是 text！keyword 不分词！
    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Integer, index = false)
    private Integer stock;

    @Field(type = FieldType.Integer, index = false)
    private Integer perLimit;

    @Field(type = FieldType.Integer, index = false)
    private Integer sale;

    @Field(type = FieldType.Long)
    private Long wareId;

    //  商品的热度！
    @Field(type = FieldType.Long)
    private Long hotScore = 0L;

    @Field(type = FieldType.Object, index = false)
    private List<String> ruleList;

}
