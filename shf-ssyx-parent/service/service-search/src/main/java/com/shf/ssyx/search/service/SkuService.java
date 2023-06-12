package com.shf.ssyx.search.service;

public interface SkuService {
    /**
     * 上架
     * @param skuId
     */
    void upperSku(Long skuId);

    /**
     * 下架
     * @param skuId
     */
    void lowerSku(Long skuId);
}
