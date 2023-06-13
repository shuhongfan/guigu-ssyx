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
import com.shf.ssyx.model.product.Category;
import com.shf.ssyx.model.product.SkuInfo;
import com.shf.ssyx.vo.activity.CouponRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            } else if (rangeType==CouponRangeType.CATEGORY){
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

}
