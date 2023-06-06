package com.atguigu.ssyx.home.service;

import java.util.Map;

public interface HomeService {

    //首页数据显示接口
    Map<String, Object> homeData(Long userId);
}
