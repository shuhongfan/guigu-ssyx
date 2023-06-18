package com.shf.ssyx.activity.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.ssyx.activity.mapper.ActivityInfoMapper;
import com.shf.ssyx.activity.mapper.ActivityRuleMapper;
import com.shf.ssyx.activity.mapper.ActivitySkuMapper;
import com.shf.ssyx.activity.service.ActivityInfoService;
import com.shf.ssyx.activity.service.CouponInfoService;
import com.shf.ssyx.client.product.ProductFeignClient;
import com.shf.ssyx.enums.ActivityType;
import com.shf.ssyx.model.activity.ActivityInfo;
import com.shf.ssyx.model.activity.ActivityRule;
import com.shf.ssyx.model.activity.ActivitySku;
import com.shf.ssyx.model.activity.CouponInfo;
import com.shf.ssyx.model.order.CartInfo;
import com.shf.ssyx.model.product.SkuInfo;
import com.shf.ssyx.vo.activity.ActivityRuleVo;
import com.shf.ssyx.vo.order.CartInfoVo;
import com.shf.ssyx.vo.order.OrderConfirmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 活动表 服务实现类
 * </p>
 *
 * @author shf
 * @since 2023-06-12
 */
@Service
public class ActivityInfoServiceImpl extends ServiceImpl<ActivityInfoMapper, ActivityInfo> implements ActivityInfoService {

    @Autowired
    private ActivityRuleMapper activityRuleMapper;

    @Autowired
    private ActivitySkuMapper activitySkuMapper;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private CouponInfoService couponInfoService;

    /**
     * 活动列表
     *
     * @param pageParam
     * @return
     */
    @Override
    public IPage<ActivityInfo> selectPage(Page<ActivityInfo> pageParam) {
        Page<ActivityInfo> activityInfoPage = baseMapper.selectPage(pageParam, null);

//        分页查询对象里面获取列表数据
        List<ActivityInfo> activityInfoList = activityInfoPage.getRecords();

//        遍历activityInfoList集合，得到每个activityInfo对象
//        向activityInfo对象封装活动类型到activityTypeString属性里面
        activityInfoList.stream().forEach(item -> {
            item.setActivityTypeString(item.getActivityType().getComment());
        });

        return activityInfoPage;
    }

    /**
     * 根据活动id获取活动规则数据
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> findActivityRuleList(Long id) {
        HashMap<String, Object> result = new HashMap<>();

//       1. 根据活动id查询，查询规则列表 activity_rule表
        LambdaQueryWrapper<ActivityRule> activityRuleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        activityRuleLambdaQueryWrapper.eq(ActivityRule::getActivityId, id);
        List<ActivityRule> activityRuleList = activityRuleMapper.selectList(activityRuleLambdaQueryWrapper);
        result.put("activityRuleList", activityRuleList);

//       2. 根据活动id查询，查询使用规则商品SkuId列表 activity_sku表
        LambdaQueryWrapper<ActivitySku> activitySkuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        activitySkuLambdaQueryWrapper.eq(ActivitySku::getActivityId, id);
        List<ActivitySku> activitySkuList = activitySkuMapper.selectList(activitySkuLambdaQueryWrapper);

//        获取所有SKUID
        List<Long> skuIdList = activitySkuList.stream().map(ActivitySku::getSkuId).collect(Collectors.toList());

//        2.1 通过远程调用 service-product模块接口，根据skuid列表 得到商品信息
        List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoList(skuIdList);
        result.put("skuInfoList", skuInfoList);
        return result;
    }

    /**
     * 在活动里面添加规则数据
     *
     * @param activityRuleVo
     */
    @Override
    public void saveActivityRule(ActivityRuleVo activityRuleVo) {
        Long activityId = activityRuleVo.getActivityId();
//        1. 根据活动id删除之前规则数据
        activityRuleMapper.delete(new LambdaQueryWrapper<ActivityRule>().eq(ActivityRule::getActivityId, activityId));

//        ActivityRule数据删除
        activitySkuMapper.delete(new LambdaQueryWrapper<ActivitySku>().eq(ActivitySku::getActivityId, activityId));

//        2. 获取规则列表数据
        List<ActivityRule> activityRuleList = activityRuleVo.getActivityRuleList();

//        获取活动类型
        ActivityInfo activityInfo = baseMapper.selectById(activityId);
        ActivityType activityType = activityInfo.getActivityType();
        activityRuleList.forEach(activityRule -> {
            activityRule.setActivityId(activityId);
            activityRule.setActivityType(activityType);
            activityRuleMapper.insert(activityRule);
        });

//        3.获取规则范围数据
        List<ActivitySku> activitySkuList = activityRuleVo.getActivitySkuList();
        activitySkuList.forEach(activitySku -> {
            activitySku.setActivityId(activityId);
            activitySkuMapper.insert(activitySku);
        });
    }

    /**
     * 根据关键词查询匹配SKU信息
     *
     * @param keyword
     * @return
     */
    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {
//        1. 根据关键词查询SKU匹配内容列表
//        1.1 service-product模块创建接口 根据关键词查询Sku匹配内容列表
        List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoByKeyword(keyword);
        if (skuInfoList.size() == 0) {
            return skuInfoList;
        }

//        从SkuInfoList集合获取所有SKuid
        List<Long> skuIdList = skuInfoList.stream().map(SkuInfo::getId).collect(Collectors.toList());

//        1.2 service-activity 远程调用得到SKU内容列表

//        2. 判断添加商品之前是否参加过活动,如果之前参加过,活动正在进行中,排除商品
//        2.1 查询两张表判断 activity_info 和 activity_sku,编写SQL语句实现
        List<Long> existSkuIdList = baseMapper.selectSkuIdListExist(skuIdList);

//        2.2 判断逻辑处理
        ArrayList<SkuInfo> findSkuList = new ArrayList<>();
        skuInfoList.forEach(skuInfo -> {
            if (!existSkuIdList.contains(skuInfo.getId())) {
                findSkuList.add(skuInfo);
            }
        });

        return findSkuList;
    }

    /**
     * 根据SKuId列表获取促销信息
     *
     * @param skuIdList
     * @return
     */
    @Override
    public Map<Long, List<String>> findActivity(List<Long> skuIdList) {
        HashMap<Long, List<String>> result = new HashMap<>();
//        skuIdList遍历，得到每个SKUID
        skuIdList.forEach(skuId -> {
//        根据skuID进行查询，查询sku对应活动里面规则列表
            List<ActivityRule> activityRuleList = baseMapper.findActivityRule(skuId);

//        数据封装，规则名称
            if (!CollectionUtils.isEmpty(activityRuleList)) {
                ArrayList<String> ruleList = new ArrayList<>();
//                把规则名称处理
                activityRuleList.forEach(activityRule -> ruleList.add(getRuleDesc(activityRule)));
                result.put(skuId, ruleList);
            }
        });

        return result;
    }

    /**
     * 根据skuId获取促销与优惠券信息
     *
     * @param skuId
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> findActivityAndCoupon(Long skuId, Long userId) {
        Map<String, Object> result = new HashMap<>();
//        1.根据skuid获取sku营销活动，一个活动有多个规则
        List<ActivityRule> activityRuleList = findActivityRuleBySkuId(skuId);
        result.put("activityRuleList", activityRuleList);

//        2.根据skuId+userId查询优惠券信息
        List<CouponInfo> couponInfoList = couponInfoService.findCouponInfoList(skuId, userId);
        result.put("couponInfoList", couponInfoList);
        return result;
    }

    /**
     * 根据SKUId获取活动规则数据
     *
     * @param skuId
     * @return
     */
    @Override
    public List<ActivityRule> findActivityRuleBySkuId(Long skuId) {
        List<ActivityRule> activityRuleList = baseMapper.findActivityRule(skuId);
        activityRuleList.forEach(activityRule -> {
            String ruleDesc = getRuleDesc(activityRule);
            activityRule.setRuleDesc(ruleDesc);
        });
        return activityRuleList;
    }

    /**
     * 获取购物车里面满足优惠券和活动的信息
     *
     * @param cartInfoList
     * @param userId
     * @return
     */
    @Override
    public OrderConfirmVo findCartActivityAndCoupon(List<CartInfo> cartInfoList, Long userId) {
//        1.获取购物车，每个购物项参与活动，根据活动规则分组
//        一个规则对用多个商品
        List<CartInfoVo> cartInfoVoList = findCartActivityList(cartInfoList);

//        2.计算参与活动之后金额
        BigDecimal activityReduceAmount = cartInfoVoList.stream()
                .filter(cartInfoVo -> cartInfoVo.getActivityRule() != null)
                .map(cartInfoVo -> cartInfoVo.getActivityRule().getReduceAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

//        3.获取购物车可以使用优惠券列表
        List<CouponInfo> couponInfoList = couponInfoService.findCartCouponInfo(cartInfoList, userId);

//        4.计算商品使用优惠券之后金额，一次只能使用一张优惠券
        BigDecimal couponReduceAmount = new BigDecimal(0);
        if (!CollectionUtils.isEmpty(couponInfoList)) {
            couponReduceAmount = couponInfoList.stream()
                    .filter(couponInfo -> couponInfo.getIsOptimal().intValue() == 1)
                    .map(couponInfo -> couponInfo.getAmount())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

//        5.计算机没有参与活动，没有使用优惠券原始金额
        BigDecimal originalTotalAmount = cartInfoList.stream()
                .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                .map(cartInfo -> cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

//        6.参与活动，使用优惠券总金额
        BigDecimal totalAmount = originalTotalAmount.subtract(activityReduceAmount).subtract(couponReduceAmount);

//        7.封装需要数据到CartInfoVo，返回
        OrderConfirmVo orderConfirmVo = new OrderConfirmVo();
        orderConfirmVo.setCarInfoVoList(cartInfoVoList);
        orderConfirmVo.setActivityReduceAmount(activityReduceAmount);
        orderConfirmVo.setCouponInfoList(couponInfoList);
        orderConfirmVo.setCouponReduceAmount(couponReduceAmount);
        orderConfirmVo.setOriginalTotalAmount(originalTotalAmount);
        orderConfirmVo.setTotalAmount(totalAmount);
        return orderConfirmVo;
    }

    /**
     * 获取购物车对应规则数据
     *
     * @param cartInfoList
     * @return
     */
    @Override
    public List<CartInfoVo> findCartActivityList(List<CartInfo> cartInfoList) {
//        创建最终返回集合
        List<CartInfoVo> cartInfoVoList = new ArrayList<>();

//        获取所有skuId
        List<Long> skuIdList = cartInfoList.stream().map(CartInfo::getSkuId).collect(Collectors.toList());

//        根据所有skuId列表获取参与活动
        List<ActivitySku> activitySkuList = baseMapper.selectCartActivity(skuIdList);

//        根据活动进行分组，每个活动里面有哪些skuId信息
//        map里面key是分组字段 活动id
//        value是每组里面sku列表数据，set集合
        Map<Long, Set<Long>> activityIdToSkuIdListMap = activitySkuList.stream().collect(
                Collectors.groupingBy(ActivitySku::getActivityId,
                        Collectors.mapping(ActivitySku::getSkuId, Collectors.toSet())));

//        获取活动里面规则数据
//        key是活动id value是活动里面规则数据
        Map<Long, List<ActivityRule>> activityIdToActivityRuleListMap = new HashMap<>();

//        所有活动id
        Set<Long> activityIdSet = activitySkuList.stream().map(ActivitySku::getActivityId).collect(Collectors.toSet());

        if (!CollectionUtils.isEmpty(activityIdSet)) {
//            activity_rule表
            LambdaQueryWrapper<ActivityRule> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(ActivityRule::getActivityId, activityIdSet);
            wrapper.orderByDesc(ActivityRule::getConditionAmount, ActivityRule::getConditionNum);
            List<ActivityRule> activityRuleList = activityRuleMapper.selectList(wrapper);

//            封装到activityIdToActivityRuleListMap表中
//            根据活动id进行分组
            activityIdToActivityRuleListMap = activityRuleList.stream().collect(Collectors.groupingBy(ActivityRule::getActivityId));
        }

//        有活动的购物项skuId
        HashSet<Long> activitySkuIdSet = new HashSet<>();
        if (!CollectionUtils.isEmpty(activityIdToSkuIdListMap)) {
//            遍历activityIdToActivityRuleListMap集合
            Iterator<Map.Entry<Long, Set<Long>>> iterator = activityIdToSkuIdListMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Long, Set<Long>> entry = iterator.next();
//                活动id
                Long activityId = entry.getKey();

//                每个活动对应skuId列表
                Set<Long> currentActivitySkuIdSet = entry.getValue();

//                获取当前活动对应的购物项列表
                List<CartInfo> currntActivityCartInfoList = cartInfoList.stream()
                        .filter(cartInfo -> currentActivitySkuIdSet.contains(cartInfo.getSkuId()))
                        .collect(Collectors.toList());

//                计数购物项总金额和总数量
                BigDecimal activityTotalAmount = computeTotalAmount(currntActivityCartInfoList);
                int activityTotalNum = computeCartNum(currntActivityCartInfoList);

//                计算活动对应规则
//                根据activityId获取活动对应的规则
                List<ActivityRule> currentActivityRuleList = activityIdToActivityRuleListMap.get(activityId);
                ActivityType activityType = currentActivityRuleList.get(0).getActivityType();

//                判断活动类型：满减和打折
                ActivityRule activityRule = null;
                if (activityType == ActivityType.FULL_REDUCTION) { // 满减
                    activityRule = computeFullReduction(activityTotalAmount, currentActivityRuleList);
                } else { // 满量
                    activityRule = computeFullDiscount(activityTotalNum, activityTotalAmount, currentActivityRuleList);
                }

//                CartInfoVo封装
                CartInfoVo cartInfoVo = new CartInfoVo();
                cartInfoVo.setActivityRule(activityRule);
                cartInfoVo.setCartInfoList(currntActivityCartInfoList);
                cartInfoVoList.add(cartInfoVo);

//                记录哪些购物项参加活动
                activitySkuIdSet.addAll(currentActivitySkuIdSet);
            }
        }
//        没有活动购物项skuId
//        获取哪些skuId没有参加活动
        skuIdList.removeAll(activitySkuIdSet);

        if (!CollectionUtils.isEmpty(skuIdList)) {
//            skuId对应购物项
            Map<Long, CartInfo> skuIdCartInfoMap = cartInfoList.stream().collect(Collectors.toMap(CartInfo::getSkuId, CartInfo -> CartInfo));

            for (Long skuId : skuIdList) {
                CartInfoVo cartInfoVo = new CartInfoVo();
                cartInfoVo.setActivityRule(null);
                List<CartInfo> currentCartInfoList = new ArrayList<>();
                currentCartInfoList.add(skuIdCartInfoMap.get(skuId));
                cartInfoVo.setCartInfoList(currentCartInfoList);
                cartInfoVoList.add(cartInfoVo);
            }
        }

//        根据活动进行分组
        return cartInfoVoList;
    }


    /**
     * 计算满量打折最优规则
     *
     * @param totalNum
     * @param activityRuleList //该活动规则skuActivityRuleList数据，已经按照优惠折扣从大到小排序了
     */
    private ActivityRule computeFullDiscount(Integer totalNum, BigDecimal totalAmount, List<ActivityRule> activityRuleList) {
        ActivityRule optimalActivityRule = null;
        //该活动规则skuActivityRuleList数据，已经按照优惠金额从大到小排序了
        for (ActivityRule activityRule : activityRuleList) {
            //如果订单项购买个数大于等于满减件数，则优化打折
            if (totalNum.intValue() >= activityRule.getConditionNum()) {
                BigDecimal skuDiscountTotalAmount = totalAmount.multiply(activityRule.getBenefitDiscount().divide(new BigDecimal("10")));
                BigDecimal reduceAmount = totalAmount.subtract(skuDiscountTotalAmount);
                activityRule.setReduceAmount(reduceAmount);
                optimalActivityRule = activityRule;
                break;
            }
        }
        if (null == optimalActivityRule) {
            //如果没有满足条件的取最小满足条件的一项
            optimalActivityRule = activityRuleList.get(activityRuleList.size() - 1);
            optimalActivityRule.setReduceAmount(new BigDecimal("0"));
            optimalActivityRule.setSelectType(1);

            StringBuffer ruleDesc = new StringBuffer()
                    .append("满")
                    .append(optimalActivityRule.getConditionNum())
                    .append("元打")
                    .append(optimalActivityRule.getBenefitDiscount())
                    .append("折，还差")
                    .append(totalNum - optimalActivityRule.getConditionNum())
                    .append("件");
            optimalActivityRule.setRuleDesc(ruleDesc.toString());
        } else {
            StringBuffer ruleDesc = new StringBuffer()
                    .append("满")
                    .append(optimalActivityRule.getConditionNum())
                    .append("元打")
                    .append(optimalActivityRule.getBenefitDiscount())
                    .append("折，已减")
                    .append(optimalActivityRule.getReduceAmount())
                    .append("元");
            optimalActivityRule.setRuleDesc(ruleDesc.toString());
            optimalActivityRule.setSelectType(2);
        }
        return optimalActivityRule;
    }

    /**
     * 计算满减最优规则
     *
     * @param totalAmount
     * @param activityRuleList //该活动规则skuActivityRuleList数据，已经按照优惠金额从大到小排序了
     */
    private ActivityRule computeFullReduction(BigDecimal totalAmount, List<ActivityRule> activityRuleList) {
        ActivityRule optimalActivityRule = null;
        //该活动规则skuActivityRuleList数据，已经按照优惠金额从大到小排序了
        for (ActivityRule activityRule : activityRuleList) {
            //如果订单项金额大于等于满减金额，则优惠金额
            if (totalAmount.compareTo(activityRule.getConditionAmount()) > -1) {
                //优惠后减少金额
                activityRule.setReduceAmount(activityRule.getBenefitAmount());
                optimalActivityRule = activityRule;
                break;
            }
        }
        if (null == optimalActivityRule) {
            //如果没有满足条件的取最小满足条件的一项
            optimalActivityRule = activityRuleList.get(activityRuleList.size() - 1);
            optimalActivityRule.setReduceAmount(new BigDecimal("0"));
            optimalActivityRule.setSelectType(1);

            StringBuffer ruleDesc = new StringBuffer()
                    .append("满")
                    .append(optimalActivityRule.getConditionAmount())
                    .append("元减")
                    .append(optimalActivityRule.getBenefitAmount())
                    .append("元，还差")
                    .append(totalAmount.subtract(optimalActivityRule.getConditionAmount()))
                    .append("元");
            optimalActivityRule.setRuleDesc(ruleDesc.toString());
        } else {
            StringBuffer ruleDesc = new StringBuffer()
                    .append("满")
                    .append(optimalActivityRule.getConditionAmount())
                    .append("元减")
                    .append(optimalActivityRule.getBenefitAmount())
                    .append("元，已减")
                    .append(optimalActivityRule.getReduceAmount())
                    .append("元");
            optimalActivityRule.setRuleDesc(ruleDesc.toString());
            optimalActivityRule.setSelectType(2);
        }
        return optimalActivityRule;
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
            if (cartInfo.getIsChecked().intValue() == 1) {
                BigDecimal itemTotal = cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum()));
                total = total.add(itemTotal);
            }
        }
        return total;
    }

    /**
     * 计算总件数
     *
     * @param cartInfoList
     * @return
     */
    private int computeCartNum(List<CartInfo> cartInfoList) {
        int total = 0;
        for (CartInfo cartInfo : cartInfoList) {
            if (cartInfo.getIsChecked().intValue() == 1) {
                total += cartInfo.getSkuNum();
            }
        }
        return total;
    }

    /**
     * 构造规则名称
     *
     * @param activityRule
     * @return
     */
    private String getRuleDesc(ActivityRule activityRule) {
        ActivityType activityType = activityRule.getActivityType();
        StringBuffer ruleDesc = new StringBuffer();
        if (activityType == ActivityType.FULL_REDUCTION) {
            ruleDesc.append("满")
                    .append(activityRule.getConditionAmount())
                    .append("元减")
                    .append(activityRule.getBenefitAmount())
                    .append("元");
        } else {
            ruleDesc.append("满")
                    .append(activityRule.getConditionNum())
                    .append("元打")
                    .append(activityRule.getBenefitDiscount())
                    .append("折");
        }
        return ruleDesc.toString();
    }
}
