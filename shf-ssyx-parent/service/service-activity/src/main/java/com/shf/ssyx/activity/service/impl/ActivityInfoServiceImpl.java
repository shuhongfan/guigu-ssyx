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
import com.shf.ssyx.model.product.SkuInfo;
import com.shf.ssyx.vo.activity.ActivityRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class ActivityInfoServiceImpl extends ServiceImpl<ActivityInfoMapper,ActivityInfo> implements ActivityInfoService {

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
        activityInfoList.stream().forEach(item->{
            item.setActivityTypeString(item.getActivityType().getComment());
        });

        return activityInfoPage;
    }

    /**
     * 根据活动id获取活动规则数据
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
     * @param skuIdList
     * @return
     */
    @Override
    public Map<Long, List<String>> findActivity(List<Long> skuIdList) {
        HashMap<Long, List<String>> result = new HashMap<>();
//        skuIdList遍历，得到每个SKUID
        skuIdList.forEach(skuId->{
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
     * 构造规则名称
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
