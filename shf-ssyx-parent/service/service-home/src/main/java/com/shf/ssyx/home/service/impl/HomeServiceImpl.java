package com.shf.ssyx.home.service.impl;

import com.shf.ssyx.client.product.ProductFeignClient;
import com.shf.ssyx.client.search.SkuFeignClient;
import com.shf.ssyx.client.user.UserFeignClient;
import com.shf.ssyx.home.service.HomeService;
import com.shf.ssyx.model.product.Category;
import com.shf.ssyx.model.product.SkuInfo;
import com.shf.ssyx.model.search.SkuEs;
import com.shf.ssyx.vo.user.LeaderAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private SkuFeignClient skuFeignClient;

    /**
     * 首页数据显示接口
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> homeData(Long userId) {
        HashMap<String, Object> result = new HashMap<>();
//        1.根据UserId获取当前登录语句提货地址信息
//        远程调用service-user模块接口获取需要数据
        LeaderAddressVo leaderAddressVo = userFeignClient.getUserAddressByUserId(userId);
        result.put("leaderAddressVo", leaderAddressVo);

//        2.获取所有分类
//        远程调用service-product模块接口
        List<Category> allCategoryList = productFeignClient.findAllCategoryList();
        result.put("categoryList", allCategoryList);

//        3.获取新人专享商品
//        远程调用service-product模块接口
        List<SkuInfo> newPersonSkuInfoList = productFeignClient.findNewPersonSkuInfoList();
        result.put("newPersonSkuInfoList", newPersonSkuInfoList);

//        4.获取爆款商品
//        远程调用service-search模块接口
        List<SkuEs> hotSkuList = skuFeignClient.findHotSkuList();
        result.put("hotSkuList", hotSkuList);

//        5.封装获取数据到map集合
        return result;
    }
}
