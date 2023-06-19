package com.shf.ssyx.activity.api;

import com.shf.ssyx.activity.service.ActivityInfoService;
import com.shf.ssyx.activity.service.CouponInfoService;
import com.shf.ssyx.model.activity.CouponInfo;
import com.shf.ssyx.model.order.CartInfo;
import com.shf.ssyx.vo.order.CartInfoVo;
import com.shf.ssyx.vo.order.OrderConfirmVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/activity")
public class ActivityInfoApiController {
    @Autowired
    private ActivityInfoService activityInfoService;

    @Autowired
    private CouponInfoService couponInfoService;

    @ApiOperation("根据SKuId列表获取促销信息")
    @PostMapping("inner/findActivity")
    public Map<Long, List<String>> findActivity(@RequestBody List<Long> skuIdList) {
        return activityInfoService.findActivity(skuIdList);
    }

    @ApiOperation("根据skuId获取促销与优惠券信息")
    @GetMapping("inner/findActivityAndCoupon/{skuId}/{userId}")
    public Map<String, Object> findActivityAndCoupon(@PathVariable Long skuId, @PathVariable Long userId) {
        Map<String, Object> activityAndCoupon = activityInfoService.findActivityAndCoupon(skuId, userId);
        return activityAndCoupon;
    }

    @ApiOperation("获取购物车里面满足优惠券和活动的信息")
    @PostMapping("inner/findCartActivityAndCoupon/{userId}")
    public OrderConfirmVo findCartActivityAndCoupon(@RequestBody List<CartInfo> cartInfoList, @PathVariable Long userId) {
        return activityInfoService.findCartActivityAndCoupon(cartInfoList, userId);
    }

    @ApiOperation("获取购物车对应规则数据")
    @GetMapping("inner/findCartActivityList")
    public List<CartInfoVo> findCartActivityList(List<CartInfo> cartInfoList) {
        return activityInfoService.findCartActivityList(cartInfoList);
    }

    @ApiOperation("获取购物车对应优惠券")
    @GetMapping("inner/findRangeSkuIdList/{couponId}")
    public CouponInfo findRangeSkuIdList(@RequestBody List<CartInfo> cartInfoList, @PathVariable("couponId") Long couponId) {
        return couponInfoService.findRangeSkuIdList(cartInfoList, couponId);
    }

    @ApiOperation("更新优惠卷使用状态")
    @GetMapping("inner/updateCouponInfoUseStatus/{couponId}/{userId}/{orderId}")
    public Boolean updateCouponInfoUseStatus(@PathVariable Long couponId, @PathVariable Long userId, @PathVariable Long orderId) {
        couponInfoService.updateCouponInfoUseStatus(couponId,userId,orderId);
        return true;
    }
}
