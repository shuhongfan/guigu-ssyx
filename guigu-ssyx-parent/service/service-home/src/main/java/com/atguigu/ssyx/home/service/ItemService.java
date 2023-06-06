package com.atguigu.ssyx.home.service;

import java.util.Map;

public interface ItemService {

    //详情
    Map<String, Object> item(Long id, Long userId);
}
