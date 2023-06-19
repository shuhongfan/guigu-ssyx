package com.shf.ssyx.activity.client;

import com.shf.ssyx.model.activity.CouponInfo;
import com.shf.ssyx.model.order.CartInfo;
import com.shf.ssyx.vo.order.CartInfoVo;
import com.shf.ssyx.vo.order.OrderConfirmVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient("service-activity")
public interface ActivityFeignClient {
    @ApiOperation("根据SKuId列表获取促销信息")
    @PostMapping("/api/activity/inner/findActivity")
    public Map<Long, List<String>> findActivity(@RequestBody List<Long> skuIdList);

    @ApiOperation("根据skuId获取促销与优惠券信息")
    @GetMapping("/api/activity/inner/findActivityAndCoupon/{skuId}/{userId}")
    public Map<String, Object> findActivityAndCoupon(@PathVariable Long skuId, @PathVariable Long userId);

    @ApiOperation("获取购物车里面满足优惠券和活动的信息")
    @PostMapping("/api/activity/inner/findCartActivityAndCoupon/{userId}")
    public OrderConfirmVo findCartActivityAndCoupon(@RequestBody List<CartInfo> cartInfoList, @PathVariable Long userId);

    @ApiOperation("获取购物车对应规则数据")
    @GetMapping("/api/activity/inner/findCartActivityList")
    public List<CartInfoVo> findCartActivityList(List<CartInfo> cartInfoList);

    @ApiOperation("获取购物车对应优惠券")
    @GetMapping("/api/activity/inner/findRangeSkuIdList/{couponId}")
    public CouponInfo findRangeSkuIdList(@RequestBody List<CartInfo> cartInfoList, @PathVariable("couponId") Long couponId);

    @ApiOperation("更新优惠卷使用状态")
    @GetMapping("/api/activity/inner/updateCouponInfoUseStatus/{couponId}/{userId}/{orderId}")
    public Boolean updateCouponInfoUseStatus(@PathVariable Long couponId, @PathVariable Long userId, @PathVariable Long orderId);
}
