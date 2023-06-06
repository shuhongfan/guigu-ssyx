package com.atguigu.ssyx.model.search;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Date;

@Data
@Document(indexName = "leaderes" ,shards = 3,replicas = 1)
public class LeaderEs {

    @Id
    private Long id;

    @Field(type = FieldType.Keyword, index = false)
    private String takeName;

    //https://blog.csdn.net/zaishijizhidian/article/details/81015988
    @GeoPointField
    private GeoPoint location; //x:经度 y:纬度

    @Field(type = FieldType.Keyword, index = false)
    private String storePath;

    @Field(type = FieldType.Keyword, index = false)
    private String detailAddress;

    @Field(type = FieldType.Double, index = false)
    private Double distance;
}
