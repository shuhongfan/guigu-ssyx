package com.shf.ssyx.activity.api;

import com.shf.ssyx.activity.service.ActivityInfoService;
import com.shf.ssyx.model.order.CartInfo;
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
}
