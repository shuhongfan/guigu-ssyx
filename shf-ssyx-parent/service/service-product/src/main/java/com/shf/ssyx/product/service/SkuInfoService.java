package com.shf.ssyx.product.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ssyx.model.product.SkuInfo;
import com.shf.ssyx.vo.product.SkuInfoQueryVo;
import com.shf.ssyx.vo.product.SkuInfoVo;

import java.util.List;

/**
 * <p>
 * sku信息 服务类
 * </p>
 *
 * @author shf
 * @since 2023-06-11
 */
public interface SkuInfoService extends IService<SkuInfo> {

    /**
     * SKU列表
     * @param pageParam
     * @param skuInfoQueryVo
     * @return
     */
    IPage<SkuInfo> selectPageSkuInfo(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo);

    /**
     * 添加商品SKU信息
     * @param skuInfoVo
     */
    void saveSkuInfo(SkuInfoVo skuInfoVo);

    /**
     * 获取SKU信息
     * @param id
     * @return
     */
    SkuInfoVo getSkuInfo(Long id);

    /**
     * 修改SKU
     * @param skuInfoVo
     */
    void updateSkuInfo(SkuInfoVo skuInfoVo);

    /**
     * 商品审核
     * @param skuId
     * @param status
     */
    void check(long skuId, Integer status);

    /**
     * 商品上下架
     * @param skuId
     * @param status
     */
    void publish(Long skuId, Integer status);

    /**
     * 新人专享
     * @param skuId
     * @param status
     */
    void isNewPerson(Long skuId, Integer status);

    /**
     * 根据skuId列表得到sku信息列表
     * @param skuIdList
     * @return
     */
    List<SkuInfo> findSkuInfoList(List<Long> skuIdList);

    /**
     * 根据关键词匹配SKU列表
     * @param keyword
     * @return
     */
    List<SkuInfo> findSkuInfoByKeyword(String keyword);

    /**
     * 获取新人专享商品
     * @return
     */
    List<SkuInfo> findNewPersonSkuInfoList();

    /**
     * 根据skuId获取SKu信息
     * @param skuId
     * @return
     */
    SkuInfoVo getSkuInfoVo(Long skuId);
}
