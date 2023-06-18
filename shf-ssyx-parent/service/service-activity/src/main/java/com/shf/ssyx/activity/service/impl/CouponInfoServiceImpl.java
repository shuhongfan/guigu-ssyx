package com.shf.ssyx.activity.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.ssyx.activity.mapper.CouponInfoMapper;
import com.shf.ssyx.activity.mapper.CouponRangeMapper;
import com.shf.ssyx.activity.service.CouponInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.ssyx.client.product.ProductFeignClient;
import com.shf.ssyx.enums.CouponRangeType;
import com.shf.ssyx.model.activity.CouponInfo;
import com.shf.ssyx.model.activity.CouponRange;
import com.shf.ssyx.model.base.BaseEntity;
import com.shf.ssyx.model.order.CartInfo;
import com.shf.ssyx.model.product.Category;
import com.shf.ssyx.model.product.SkuInfo;
import com.shf.ssyx.vo.activity.CouponRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 优惠券信息 服务实现类
 * </p>
 *
 * @author shf
 * @since 2023-06-12
 */
@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo> implements CouponInfoService {

    @Autowired
    private CouponRangeMapper couponRangeMapper;

    @Autowired
    private ProductFeignClient productFeignClient;

    /**
     * 优惠券分页查询
     *
     * @param page
     * @param limit
     * @return
     */
    @Override
    public IPage<CouponInfo> selectPageCouponInfo(Long page, Long limit) {
        Page<CouponInfo> pageParam = new Page<>(page, limit);
        Page<CouponInfo> couponInfoPage = baseMapper.selectPage(pageParam, null);
        List<CouponInfo> couponInfoList = couponInfoPage.getRecords();

        couponInfoList.forEach(couponInfo -> {
//            使用范围
            couponInfo.setCouponTypeString(couponInfo.getCouponType().getComment());
            CouponRangeType rangeType = couponInfo.getRangeType();
            if (rangeType != null) {
//                优惠券类型
                couponInfo.setRangeTypeString(rangeType.getComment());
            }
        });
        return couponInfoPage;
    }

    /**
     * 根据id查询优惠券
     *
     * @param id
     * @return
     */
    @Override
    public CouponInfo getCouponInfo(Long id) {
        CouponInfo couponInfo = baseMapper.selectById(id);
        couponInfo.setCouponTypeString(couponInfo.getCouponType().getComment());
        if (couponInfo.getRangeType() != null) {
            couponInfo.setRangeTypeString(couponInfo.getRangeType().getComment());
        }
        return couponInfo;
    }

    /**
     * 根据优惠券id查询规则数据
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> findCouponRuleList(Long id) {
//        第一步 根据优惠券id查询优惠券基本信息  coupon_info表
        CouponInfo couponInfo = baseMapper.selectById(id);

//        第二步 根据优惠券id查询coupon_range 查询里面对应range_id
        List<CouponRange> couponRangeList = couponRangeMapper.selectList(
                new LambdaQueryWrapper<CouponRange>().eq(CouponRange::getCouponId, id)
        );
//        couponRangeList获取所有range_id
        List<Long> randIdList = couponRangeList.stream().map(CouponRange::getRangeId).collect(Collectors.toList());


        HashMap<String, Object> result = new HashMap<>();
//        第三步 分别判断封装不同数据
//        如果规则类型是SKU,得到SKUID,远程调用根据多个SKUID值获取对应SKU信息
        if (!CollectionUtils.isEmpty(randIdList)) {
            CouponRangeType rangeType = couponInfo.getRangeType();
//        如果规则类型SKU range_id就是skuId值
            if (rangeType == CouponRangeType.SKU) {
                List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoList(randIdList);
                result.put("skuInfoList", skuInfoList);
            } else if (rangeType == CouponRangeType.CATEGORY) {
//        如果规则类型 category range_id就是分类id值
                List<Category> categoryList = productFeignClient.findCategoryList(randIdList);
                result.put("categoryList", categoryList);
            }
        }

//        如果规则类型是分类,得到分类id,远程调用根据多个分类id值获取对应分类信息

        return result;
    }

    /**
     * 添加优惠券规则数据
     *
     * @param couponRuleVo
     */
    @Override
    public void saveCouponRule(CouponRuleVo couponRuleVo) {
//        根据优惠券id删除规则数据
        couponRangeMapper.delete(
                new LambdaQueryWrapper<CouponRange>().eq(CouponRange::getCouponId, couponRuleVo.getCouponId())
        );

//        更新优惠券基本信息
        CouponInfo couponInfo = baseMapper.selectById(couponRuleVo.getCouponId());
        couponInfo.setRangeType(couponRuleVo.getRangeType());
        couponInfo.setConditionAmount(couponRuleVo.getConditionAmount());
        couponInfo.setAmount(couponRuleVo.getAmount());
        couponInfo.setConditionAmount(couponRuleVo.getConditionAmount());
        couponInfo.setRangeDesc(couponRuleVo.getRangeDesc());
        baseMapper.updateById(couponInfo);

//        添加优惠券新规则数据
        List<CouponRange> couponRangeList = couponRuleVo.getCouponRangeList();
        for (CouponRange couponRange : couponRangeList) {
//            设置优惠券id
            couponRange.setCouponId(couponRuleVo.getCouponId());
//            添加
            couponRangeMapper.insert(couponRange);
        }
    }

    /**
     * 根据skuId+userId查询优惠券信息
     *
     * @param skuId
     * @param userId
     * @return
     */
    @Override
    public List<CouponInfo> findCouponInfoList(Long skuId, Long userId) {
//        远程调用：根据SkuId获取skuInfo
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);

//        根据条件查询：skuId+分类Id+userId
        List<CouponInfo> couponInfoList = baseMapper.selectCouponInfo(
                skuInfo.getId(),
                skuInfo.getCategoryId(),
                userId
        );
        return couponInfoList;
    }

    /**
     * 获取购物车可以使用优惠券列表
     *
     * @param cartInfoList
     * @param userId
     * @return
     */
    @Override
    public List<CouponInfo> findCartCouponInfo(List<CartInfo> cartInfoList, Long userId) {
//        1.根据userId获取用户全部优惠券
        List<CouponInfo> userAllCouponInfoList = baseMapper.selectCartCouponInfoList(userId);

//        2.从第一步返回list中，获取所有优惠券id列表
        List<Long> couponIdList = userAllCouponInfoList.stream().map(BaseEntity::getId).collect(Collectors.toList());

//        3.查询优惠券对应的范围 coupon_range
        LambdaQueryWrapper<CouponRange> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(CouponRange::getCouponId, couponIdList);
        List<CouponRange> couponRangeList = couponRangeMapper.selectList(wrapper);

//        4.获取优惠券id，对应skuId列表 优惠券id进行分组，得到map集合
        Map<Long, List<Long>> couponIdToSkuIdMap = findCouponIdToSkuIdMap(cartInfoList, couponRangeList);

//        5.遍历全部优惠券集合，判断优惠券类型
//        全场通用 sku和分类
        //优惠后减少金额
        BigDecimal reduceAmount = new BigDecimal("0");
        //记录最优优惠券
        CouponInfo optimalCouponInfo = null;
        for (CouponInfo couponInfo : userAllCouponInfoList) {
            if (CouponRangeType.ALL == couponInfo.getRangeType()) {
                //全场通用
                //判断是否满足优惠使用门槛
                //计算购物车商品的总价
                BigDecimal totalAmount = computeTotalAmount(cartInfoList);
                if (totalAmount.subtract(couponInfo.getConditionAmount()).doubleValue() >= 0) {
                    couponInfo.setIsSelect(1);
                }
            } else {
                // 优惠券id对应的满足使用范围的购物项skuId列表
                List<Long> skuIdList = couponIdToSkuIdMap.get(couponInfo.getId());
                // 当前满足使用范围的购物项
                List<CartInfo> currentCartInfoList = cartInfoList.stream().filter(cartInfo -> skuIdList.contains(cartInfo.getSkuId())).collect(Collectors.toList());
                BigDecimal totalAmount = computeTotalAmount(currentCartInfoList);
                if (totalAmount.subtract(couponInfo.getConditionAmount()).doubleValue() >= 0) {
                    couponInfo.setIsSelect(1);
                }
            }
        }
        if(null != optimalCouponInfo) {
            optimalCouponInfo.setIsOptimal(1);
        }
        return userAllCouponInfoList;
    }

    /**
     * 计算总金额
     *
     * @param cartInfoList
     * @return
     */
    private BigDecimal computeTotalAmount(List<CartInfo> cartInfoList) {
        BigDecimal total = new BigDecimal("0");
        for (CartInfo cartInfo : cartInfoList) {
            //是否选中
            if (cartInfo.getIsChecked().intValue() == 1) {
                BigDecimal itemTotal = cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum()));
                total = total.add(itemTotal);
            }
        }
        return total;
    }


    /**
     * 获取优惠券id，对应skuId列表 优惠券id进行分组，得到map集合
     *
     * @param cartInfoList
     * @param couponRangeList
     * @return
     */
    private Map<Long, List<Long>> findCouponIdToSkuIdMap(List<CartInfo> cartInfoList, List<CouponRange> couponRangeList) {
        HashMap<Long, List<Long>> couponIdToSkuIdMap = new HashMap<>();

//        couponRangeList数据处理，根据优惠券id分组
        Map<Long, List<CouponRange>> couponRangeToRangeListMap = couponRangeList.stream().collect(
                Collectors.groupingBy(CouponRange::getCouponId)
        );

//        遍历map集合
        Iterator<Map.Entry<Long, List<CouponRange>>> iterator = couponRangeToRangeListMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, List<CouponRange>> entry = iterator.next();
            Long couponId = entry.getKey();
            List<CouponRange> rangeList = entry.getValue();

//            创建集合 set
            HashSet<Long> skuIdSet = new HashSet<>();
            for (CartInfo cartInfo : cartInfoList) {
                for (CouponRange couponRange : rangeList) {
                    if (couponRange.getRangeType() == CouponRangeType.SKU &&
                            couponRange.getRangeId().longValue() == cartInfo.getSkuId().longValue()) {
                        skuIdSet.add(cartInfo.getSkuId());
                    } else if (couponRange.getRangeType() == CouponRangeType.CATEGORY &&
                            couponRange.getRangeId().longValue() == cartInfo.getCategoryId().longValue()
                    ) {
                        skuIdSet.add(cartInfo.getSkuId());
                    }
                }
            }
            couponIdToSkuIdMap.put(couponId, new ArrayList<>(skuIdSet));
        }
        return couponIdToSkuIdMap;
    }

}
