package com.shf.ssyx.home.service.impl;

import com.shf.ssyx.activity.client.ActivityFeignClient;
import com.shf.ssyx.client.product.ProductFeignClient;
import com.shf.ssyx.client.search.SkuFeignClient;
import com.shf.ssyx.common.auth.AuthContextHolder;
import com.shf.ssyx.home.service.ItemService;
import com.shf.ssyx.vo.product.SkuInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private ActivityFeignClient activityFeignClient;

    @Autowired
    private SkuFeignClient skuFeignClient;

    /**
     * 获取sku详细信息
     *
     * @param id
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> item(Long id, Long userId) {
        Map<String, Object> result = new HashMap<>();

//        根据skuId查询
        CompletableFuture<SkuInfoVo> skuInfoCompletableFuture = CompletableFuture.supplyAsync(() -> {
//            远程调用获取SKU对应数据
            SkuInfoVo skuInfoVo = productFeignClient.getSkuInfoVo(id);
            result.put("skuInfoVo", skuInfoVo);
            return skuInfoVo;
        },threadPoolExecutor);

//        sku对应优惠券信息
        CompletableFuture<Void> activityCompletableFuture = CompletableFuture.runAsync(() -> {
//            远程调用获取优惠券
            Map<String, Object> activityMap = activityFeignClient.findActivityAndCoupon(id, userId);
            result.putAll(activityMap);
        },threadPoolExecutor);

//        更新商品热度
        CompletableFuture<Void> hotCompletableFuture = CompletableFuture.runAsync(() -> {
//            远程调用商品热度
            skuFeignClient.incrHotScore(id);
        },threadPoolExecutor);

//        任务组合
        CompletableFuture.allOf(
                skuInfoCompletableFuture,
                activityCompletableFuture,
                hotCompletableFuture
        ).join();

        return result;
    }
}
