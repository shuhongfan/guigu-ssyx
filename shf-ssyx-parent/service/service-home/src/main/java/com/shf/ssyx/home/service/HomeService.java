package com.shf.ssyx.home.service;

import java.util.Map;

public interface HomeService {
    /**
     * 首页数据显示接口
     * @param userId
     * @return
     */
    Map<String, Object> homeData(Long userId);
}
