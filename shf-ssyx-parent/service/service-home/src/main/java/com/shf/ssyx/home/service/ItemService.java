package com.shf.ssyx.home.service;

import java.util.Map;

public interface ItemService {
    /**
     * 获取sku详细信息
     * @param id
     * @param userId
     * @return
     */
    Map<String, Object> item(Long id, Long userId);
}
