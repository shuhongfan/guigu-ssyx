package com.shf.ssyx.activity.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shf.ssyx.model.activity.CouponInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 优惠券信息 Mapper 接口
 * </p>
 *
 * @author shf
 * @since 2023-06-12
 */
@Repository
public interface CouponInfoMapper extends BaseMapper<CouponInfo> {

    /**
     * 根据skuId+userId查询优惠券信息
     * @param id
     * @param categoryId
     * @param userId
     * @return
     */
    List<CouponInfo> selectCouponInfo(@Param("skuId") Long skuId, @Param("categoryId") Long categoryId, @Param("userId") Long userId);

    /**
     * 根据userId获取用户全部优惠券
     * @param userId
     * @return
     */
    List<CouponInfo> selectCartCouponInfoList(@Param("userId") Long userId);
}
