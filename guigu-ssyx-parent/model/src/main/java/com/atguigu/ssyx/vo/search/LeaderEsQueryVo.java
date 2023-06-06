package com.atguigu.ssyx.vo.search;

import lombok.Data;

// 封装查询条件
@Data
public class LeaderEsQueryVo {

    double latitude = 39.9504550;
    double longitude = 116.3512330;
    double distance = 100;

}
