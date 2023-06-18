package com.shf.ssyx.service;

import com.shf.ssyx.model.order.CartInfo;

import java.util.List;

public interface CartInfoService {
    /**
     * 添加商品到购物车
     * @param userId
     * @param skuId
     * @param skuNum
     */
    void addToCart(Long userId, Long skuId, Integer skuNum);

    /**
     * 根据skuid删除购物车
     * @param skuId
     * @param userId
     */
    void deleteCart(Long skuId, Long userId);

    /**
     * 清空购物车
     * @param userId
     */
    void deleteAllCart(Long userId);

    /**
     * 批量删除购物车
     * @param userId
     * @param skuIdList
     */
    void batchDeleteCart(Long userId, List<Long> skuIdList);

    /**
     * 购物车列表
     * @param userId
     * @return
     */
    List<CartInfo> getCartList(Long userId);

    /**
     * 根据SKUID选中
     * @param userId
     * @param skuId
     * @param isChecked
     */
    void checkCart(Long userId, Long skuId, Integer isChecked);

    /**
     * 全选 / 全不选
     * @param userId
     * @param isChecked
     */
    void checkAllCart(Long userId, Integer isChecked);

    /**
     * 批量选择购物车
     * @param skuIdList
     * @param userId
     * @param isChecked
     */
    void batchCheckCart(List<Long> skuIdList, Long userId, Integer isChecked);
}
