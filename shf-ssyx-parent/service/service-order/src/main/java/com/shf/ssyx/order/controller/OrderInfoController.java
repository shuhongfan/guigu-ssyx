package com.shf.ssyx.order.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.ssyx.common.auth.AuthContextHolder;
import com.shf.ssyx.common.result.Result;
import com.shf.ssyx.model.order.OrderInfo;
import com.shf.ssyx.order.service.OrderInfoService;
import com.shf.ssyx.vo.order.OrderSubmitVo;
import com.shf.ssyx.vo.order.OrderUserQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author shf
 * @since 2023-06-18
 */
@Api(value = "Order管理", tags = "Order管理")
@RestController
@RequestMapping("/api/order")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @ApiOperation("确认订单")
    @GetMapping("auth/confirmOrder")
    public Result confirm() {
        return Result.ok(orderInfoService.confirmOrder());
    }

    @ApiOperation("生成订单")
    @PostMapping("auth/submitOrder")
    public Result submitOrder(@RequestBody OrderSubmitVo orderParamVo, HttpServletRequest request) {
        // 获取到用户Id
        Long userId = AuthContextHolder.getUserId();
        return Result.ok(orderInfoService.submitOrder(orderParamVo));
    }

    @ApiOperation("获取订单详情")
    @GetMapping("auth/getOrderInfoById/{orderId}")
    public Result getOrderInfoById(@PathVariable("orderId") Long orderId){
        return Result.ok(orderInfoService.getOrderInfoById(orderId));
    }

    @ApiOperation("根据orderNo查询订单信息")
    @GetMapping("inner/getOrderInfo/{orderNo}")
    public OrderInfo getOrderInfo(@PathVariable String orderNo) {
        return orderInfoService.getOrderInfoByOrderNo(orderNo);
    }

    @ApiOperation("获取用户订单分页列表")
    @GetMapping("auth/findUserOrderPage/{page}/{limit}")
    public Result findUserOrderPage(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "orderVo", value = "查询对象", required = false)
            OrderUserQueryVo orderUserQueryVo) {
        Long userId = AuthContextHolder.getUserId();
        orderUserQueryVo.setUserId(userId);
        Page<OrderInfo> pageParam = new Page<>(page, limit);
        IPage<OrderInfo> pageModel = orderInfoService.findUserOrderPage(pageParam, orderUserQueryVo);
        return Result.ok(pageModel);
    }
}

