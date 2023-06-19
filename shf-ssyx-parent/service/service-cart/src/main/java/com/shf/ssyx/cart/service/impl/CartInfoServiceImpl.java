package com.shf.ssyx.cart.service.impl;

import com.shf.ssyx.cart.service.CartInfoService;
import com.shf.ssyx.client.product.ProductFeignClient;
import com.shf.ssyx.common.constant.RedisConst;
import com.shf.ssyx.common.exception.SsyxException;
import com.shf.ssyx.common.result.ResultCodeEnum;
import com.shf.ssyx.enums.SkuType;
import com.shf.ssyx.model.base.BaseEntity;
import com.shf.ssyx.model.order.CartInfo;
import com.shf.ssyx.model.product.SkuInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CartInfoServiceImpl implements CartInfoService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ProductFeignClient productFeignClient;

    /**
     * 返回购物车在redsi的key
     * @param userId
     * @return
     */
    private String getCartKey(Long userId) {
        return RedisConst.USER_KEY_PREFIX + userId + RedisConst.USER_CART_KEY_SUFFIX;
    }

    /**
     * 添加商品到购物车
     * @param userId
     * @param skuId
     * @param skuNum
     */
    @Override
    public void addToCart(Long userId, Long skuId, Integer skuNum) {
//        1.因为购物车数据存储到redis里面，从redis里面根据key获取数据
//        从redis里面根据key获取数据,这个key包含userID
        String cartKey = getCartKey(userId);
        BoundHashOperations hashOperations = redisTemplate.boundHashOps(cartKey);

//        2.根据第一步查询出来的结果，得到的是SkuId+SkuNum关系
//        目的：判断是否是第一次添加这个商品到购物车
//        进行判断，判断结果里面，是否有skuId
        CartInfo cartInfo = null;
        if (hashOperations.hasKey(skuId.toString())) {
//        3.如果结果里面包含skuId，不是第一次添加
            cartInfo = (CartInfo) hashOperations.get(skuId.toString());
//            3.1根据skuId，获取对应数量，更新数量
            int currentSkuNum = cartInfo.getSkuNum() + skuNum;
            if (currentSkuNum < 1) {
                return;
            }
//            更新cartInfo对象
            cartInfo.setSkuNum(currentSkuNum);
            cartInfo.setCurrentBuyNum(currentSkuNum);

//            判断商品数量不能大于限购数量
            Integer perLimit = cartInfo.getPerLimit();
            if (currentSkuNum > perLimit) {
                throw new SsyxException(ResultCodeEnum.SKU_LIMIT_ERROR);
            }

//            更新其他值
            cartInfo.setIsChecked(1);
            cartInfo.setUpdateTime(new Date());
        } else {
//        4.如果结果里面没有skuId，就是第一次添加
//            4.1直接添加
            skuNum = 1;

//            封装cartInfo
            cartInfo = new CartInfo();

//            远程调用根据skuId获取skuInfo
            SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
            if (skuInfo == null) {
                throw new SsyxException(ResultCodeEnum.DATA_ERROR);
            }

//            封装cartInfo对象
            cartInfo.setSkuId(skuId);
            cartInfo.setCategoryId(skuInfo.getCategoryId());
            cartInfo.setSkuType(skuInfo.getSkuType());
            cartInfo.setIsNewPerson(skuInfo.getIsNewPerson());
            cartInfo.setUserId(userId);
            cartInfo.setCartPrice(skuInfo.getPrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setCurrentBuyNum(skuNum);
            cartInfo.setSkuType(SkuType.COMMON.getCode());
            cartInfo.setPerLimit(skuInfo.getPerLimit());
            cartInfo.setImgUrl(skuInfo.getImgUrl());
            cartInfo.setSkuName(skuInfo.getSkuName());
            cartInfo.setWareId(skuInfo.getWareId());
            cartInfo.setIsChecked(1);
            cartInfo.setStatus(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
        }

//        5.更新redis缓存
        hashOperations.put(skuId.toString(), cartInfo);

//        6.设置有效时间
        setCartKeyExpire(cartKey);
    }

    /**
     * 根据skuid删除购物车
     * @param skuId
     * @param userId
     */
    @Override
    public void deleteCart(Long skuId, Long userId) {
        BoundHashOperations hashOperations = redisTemplate.boundHashOps(getCartKey(userId));
        if (hashOperations.hasKey(skuId.toString())) {
            hashOperations.delete(skuId.toString());
        }
    }

    /**
     * 清空购物车
     * @param userId
     */
    @Override
    public void deleteAllCart(Long userId) {
        String cartKey = getCartKey(userId);
        BoundHashOperations hashOperations = redisTemplate.boundHashOps(cartKey);
        List<CartInfo> cartInfoList = hashOperations.values();
        cartInfoList.forEach(cartInfo -> hashOperations.delete(cartInfo.getSkuId().toString()));
    }

    /**
     * 批量删除购物车
     * @param userId
     * @param skuIdList
     */
    @Override
    public void batchDeleteCart(Long userId, List<Long> skuIdList) {
        String cartKey = getCartKey(userId);
        BoundHashOperations hashOperations = redisTemplate.boundHashOps(cartKey);
        skuIdList.forEach(skuId -> hashOperations.delete(skuId.toString()));
    }

    /**
     * 购物车列表
     * @param userId
     * @return
     */
    @Override
    public List<CartInfo> getCartList(Long userId) {
        List<CartInfo> cartInfoList = new ArrayList<>();
        if (StringUtils.isEmpty(userId)) {
            return cartInfoList;
        }

//        从redis获取购物车数据
        String cartKey = getCartKey(userId);
        BoundHashOperations<String, String, CartInfo> hashOperations = redisTemplate.boundHashOps(cartKey);
        cartInfoList =  hashOperations.values();

        if (!CollectionUtils.isEmpty(cartInfoList)) {
//            根据商品添加时间，降序
            cartInfoList.sort((Comparator.comparing(BaseEntity::getCreateTime)));
        }

        return cartInfoList;
    }

    /**
     * 根据SKUID选中
     * @param userId
     * @param skuId
     * @param isChecked
     */
    @Override
    public void checkCart(Long userId, Long skuId, Integer isChecked) {
//        获取redis的key
        String cartKey = getCartKey(userId);

//        cartKey获取field-value
        BoundHashOperations hashOperations = redisTemplate.boundHashOps(cartKey);

//        根据field（skuId） 获取value（cartInfo）
        CartInfo cartInfo = (CartInfo) hashOperations.get(skuId.toString());
        if (cartInfo != null) {
            cartInfo.setIsChecked(isChecked);
//            更新
            hashOperations.put(skuId.toString(), cartInfo);

//            设置key过期时间
            setCartKeyExpire(cartKey);
        }
    }

    /**
     * 全选 / 全不选
     * @param userId
     * @param isChecked
     */
    @Override
    public void checkAllCart(Long userId, Integer isChecked) {
        String cartKey = getCartKey(userId);
        BoundHashOperations hashOperations = redisTemplate.boundHashOps(cartKey);
        List<CartInfo> cartInfoList = hashOperations.values();
        cartInfoList.stream().forEach(cartInfo->{
            cartInfo.setIsChecked(isChecked);
            hashOperations.put(cartInfo.getSkuId().toString(), cartInfo);
        });
        setCartKeyExpire(cartKey);
    }

    /**
     * 批量选择购物车
     * @param skuIdList
     * @param userId
     * @param isChecked
     */
    @Override
    public void batchCheckCart(List<Long> skuIdList, Long userId, Integer isChecked) {
        String cartKey = getCartKey(userId);
        BoundHashOperations hashOperations = redisTemplate.boundHashOps(cartKey);
        skuIdList.forEach(skuId->{
            CartInfo cartInfo = (CartInfo) hashOperations.get(skuId.toString());
            cartInfo.setIsChecked(isChecked);
            hashOperations.put(cartInfo.getSkuId().toString(), cartInfo);
        });
        setCartKeyExpire(cartKey);
    }

    /**
     * 根据用户Id查询购物车列表
     * @param userId
     * @return
     */
    @Override
    public List<CartInfo> getCartCheckedList(Long userId) {
        String cartKey = getCartKey(userId);
        BoundHashOperations hashOperations = redisTemplate.boundHashOps(cartKey);
        List<CartInfo> cartInfoList = hashOperations.values();

        List<CartInfo> cartInfoListNew = cartInfoList.stream().filter(cartInfo -> cartInfo.getIsChecked().intValue() == 1).collect(Collectors.toList());

        return cartInfoListNew;
    }

    /**
     * 根据userId删除用户选中购物车记录
     * @param userId
     */
    @Override
    public void deleteCartCheck(Long userId) {
//        根据userId查询选中购物车记录
        List<CartInfo> cartInfoList = getCartCheckedList(userId);

//        查询list数据处理，得到skuId集合
        List<Long> skuIdList = cartInfoList.stream().map(CartInfo::getSkuId).collect(Collectors.toList());

//        构建redis的key值
//        hash类型 key filed-value
        String cartKey = this.getCartKey(userId);

//        根据key查询filed-value结构
        BoundHashOperations<String,String,CartInfo> hashOperations = redisTemplate.boundHashOps(cartKey);

//        根据filed（skuId）删除redis数据
        skuIdList.forEach(skuId->{
            hashOperations.delete(skuId.toString());
        });
    }

    /**
     * 设置key 过期时间
     * @param key
     */
    private void setCartKeyExpire(String key) {
        redisTemplate.expire(key, RedisConst.USER_CART_EXPIRE, TimeUnit.SECONDS);
    }
}
