package com.shf.ssyx.search.service.impl;

import com.shf.ssyx.client.product.ProductFeignClient;
import com.shf.ssyx.enums.SkuType;
import com.shf.ssyx.model.product.Category;
import com.shf.ssyx.model.product.SkuInfo;
import com.shf.ssyx.model.search.SkuEs;
import com.shf.ssyx.search.repository.SkuRepository;
import com.shf.ssyx.search.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private ProductFeignClient productFeignClient;

    /**
     * 上架
     * @param skuId
     */
    @Override
    public void upperSku(Long skuId) {
//        1. 通过远程调用 根据SKUID获取相关信息

//        查询sku信息
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        if (skuInfo == null) {
            return;
        }

//        查询分类
        Category category = productFeignClient.getCategory(skuInfo.getCategoryId());

//        2. 获取数据封装SkuES对象
        SkuEs skuEs = new SkuEs();

//        封装分类
        if (category != null) {
            skuEs.setCategoryId(category.getId());
            skuEs.setCategoryName(category.getName());
        }

//        封装sku信息部分
        skuEs.setId(skuInfo.getId());
        skuEs.setKeyword(skuInfo.getSkuName()+","+skuEs.getCategoryName());
        skuEs.setWareId(skuInfo.getWareId());
        skuEs.setIsNewPerson(skuInfo.getIsNewPerson());
        skuEs.setImgUrl(skuInfo.getImgUrl());
        skuEs.setTitle(skuInfo.getSkuName());
        if(skuInfo.getSkuType() == SkuType.COMMON.getCode()) { // 普通商品
            skuEs.setSkuType(0);
            skuEs.setPrice(skuInfo.getPrice().doubleValue());
            skuEs.setStock(skuInfo.getStock());
            skuEs.setSale(skuInfo.getSale());
            skuEs.setPerLimit(skuInfo.getPerLimit());
        } else {
            //TODO 待完善-秒杀商品

        }

//        3.调用方法添加ES
        skuRepository.save(skuEs);
    }

    /**
     * 下架
     * @param skuId
     */
    @Override
    public void lowerSku(Long skuId) {
        skuRepository.deleteById(skuId);
    }
}
