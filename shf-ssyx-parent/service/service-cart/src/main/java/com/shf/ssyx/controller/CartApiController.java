package com.shf.ssyx.controller;

import com.shf.ssyx.activity.client.ActivityFeignClient;
import com.shf.ssyx.common.auth.AuthContextHolder;
import com.shf.ssyx.common.result.Result;
import com.shf.ssyx.model.order.CartInfo;
import com.shf.ssyx.service.CartInfoService;
import com.shf.ssyx.vo.order.OrderConfirmVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "购物车接口")
@RestController
@RequestMapping("/api/cart")
public class CartApiController {

    @Autowired
    private CartInfoService cartInfoService;

    @Autowired
    private ActivityFeignClient activityFeignClient;

    @ApiOperation("添加商品到购物车")
    @GetMapping("addToCart/{skuId}/{skuNum}")
    public Result addToCart(@PathVariable Long skuId, @PathVariable Integer skuNum) {
//        获取当前登录用户id
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.addToCart(userId, skuId, skuNum);
        return Result.ok();
    }

    @ApiOperation("根据skuid删除购物车")
    @DeleteMapping("deleteCart/{skuId}")
    public Result deleteCart(@PathVariable Long skuId) {
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.deleteCart(skuId, userId);
        return Result.ok(null);
    }

    @ApiOperation("清空购物车")
    @DeleteMapping("deleteAllCart")
    public Result deleteAllCart() {
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.deleteAllCart(userId);
        return Result.ok(null);
    }

    @ApiOperation("批量删除购物车")
    @PostMapping("batchDeleteCart")
    public Result batchDeleteCart(@RequestBody List<Long> skuIdList) {
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.batchDeleteCart(userId, skuIdList);
        return Result.ok();
    }

    @ApiOperation("购物车列表")
    @GetMapping("cartList")
    public Result cartList() {
        Long userId = AuthContextHolder.getUserId();
        List<CartInfo> cartInfoList = cartInfoService.getCartList(userId);
        return Result.ok(cartInfoList);
    }

    @ApiOperation("查询带优惠卷的购物车")
    @GetMapping("activityCartList")
    public Result activityCartList() {
        Long userId = AuthContextHolder.getUserId();
        List<CartInfo> cartInfoList = cartInfoService.getCartList(userId);
        OrderConfirmVo orderTradeVo = activityFeignClient.findCartActivityAndCoupon(cartInfoList, userId);
        return Result.ok(orderTradeVo);
    }

    @ApiOperation("根据SKUID选中")
    @GetMapping("checkCart/{skuId}/{isChecked}")
    public Result checkCart(@PathVariable Long skuId, @PathVariable Integer isChecked) {
//        获取userId
        Long userId = AuthContextHolder.getUserId();

//        调用方法
        cartInfoService.checkCart(userId, skuId, isChecked);
        return Result.ok();
    }

    @ApiOperation("全选 / 全不选")
    @GetMapping("checkAllCart/{isChecked}")
    public Result checkAllCart(@PathVariable Integer isChecked) {
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.checkAllCart(userId, isChecked);
        return Result.ok();
    }

    @ApiOperation("批量选择购物车")
    @PostMapping("batchCheckCart/{isChecked}")
    public Result batchCheckCart(@RequestBody List<Long> skuIdList, @PathVariable Integer isChecked){
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.batchCheckCart(skuIdList, userId, isChecked);
        return Result.ok();
    }
}
