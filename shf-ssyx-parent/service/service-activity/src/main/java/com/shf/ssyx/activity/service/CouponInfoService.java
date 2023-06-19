package com.shf.ssyx.activity.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ssyx.model.activity.CouponInfo;
import com.shf.ssyx.model.order.CartInfo;
import com.shf.ssyx.vo.activity.CouponRuleVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 优惠券信息 服务类
 * </p>
 *
 * @author shf
 * @since 2023-06-12
 */
public interface CouponInfoService extends IService<CouponInfo> {

    /**
     * 优惠券分页查询
     * @param page
     * @param limit
     * @return
     */
    IPage<CouponInfo> selectPageCouponInfo(Long page, Long limit);

    /**
     * 根据id查询优惠券
     * @param id
     * @return
     */
    CouponInfo getCouponInfo(Long id);

    /**
     * 根据优惠券id查询规则数据
     * @param id
     * @return
     */
    Map<String, Object> findCouponRuleList(Long id);

    /**
     * 添加优惠券规则数据
     * @param couponRuleVo
     */
    void saveCouponRule(CouponRuleVo couponRuleVo);

    /**
     * 根据skuId+userId查询优惠券信息
     * @param skuId
     * @param userId
     * @return
     */
    List<CouponInfo> findCouponInfoList(Long skuId, Long userId);

    /**
     * 获取购物车可以使用优惠券列表
     * @param cartInfoList
     * @param userId
     * @return
     */
    List<CouponInfo> findCartCouponInfo(List<CartInfo> cartInfoList, Long userId);

    /**
     * 获取购物车对应优惠券
     * @param cartInfoList
     * @param couponId
     * @return
     */
    CouponInfo findRangeSkuIdList(List<CartInfo> cartInfoList, Long couponId);

    /**
     * 更新优惠卷使用状态
     * @param couponId
     * @param userId
     * @param orderId
     */
    void updateCouponInfoUseStatus(Long couponId, Long userId, Long orderId);
}
