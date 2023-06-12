package com.shf.ssyx.product.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.ssyx.common.result.Result;
import com.shf.ssyx.model.product.SkuInfo;
import com.shf.ssyx.product.service.SkuInfoService;
import com.shf.ssyx.vo.product.SkuInfoQueryVo;
import com.shf.ssyx.vo.product.SkuInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * sku信息 前端控制器
 * </p>
 *
 * @author shf
 * @since 2023-06-11
 */
@Api(value = "SkuInfo管理", tags = "商品Sku管理")
@RestController
@RequestMapping(value = "/admin/product/skuInfo")
public class SkuInfoController {
    @Autowired
    private SkuInfoService skuInfoService;


    @ApiOperation("SKU列表")
    @GetMapping("{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, SkuInfoQueryVo skuInfoQueryVo) {
        Page<SkuInfo> pageParam = new Page<>(page, limit);
        IPage<SkuInfo> pageModel = skuInfoService.selectPageSkuInfo(pageParam, skuInfoQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation("添加商品SKU信息")
    @PostMapping("save")
    public Result save(@RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.saveSkuInfo(skuInfoVo);
        return Result.ok();
    }

    @ApiOperation("获取SKU信息")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        SkuInfoVo skuInfoVo = skuInfoService.getSkuInfo(id);
        return Result.ok(skuInfoVo);
    }

    @ApiOperation("修改SKU")
    @PutMapping("update")
    public Result update(@RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.updateSkuInfo(skuInfoVo);
        return Result.ok();
    }

    @ApiOperation("删除SKU")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        skuInfoService.removeById(id);
        return Result.ok();
    }

    @ApiOperation("根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        skuInfoService.removeByIds(idList);
        return Result.ok();
    }

    @ApiOperation("商品审核")
    @GetMapping("check/{skuId}/{status}")
    public Result check(@PathVariable long skuId, @PathVariable Integer status) {
        skuInfoService.check(skuId, status);
        return Result.ok();
    }

    @ApiOperation("商品上下架")
    @GetMapping("publish/{skuId}/{status}")
    public Result publish(@PathVariable Long skuId, @PathVariable Integer status) {
        skuInfoService.publish(skuId, status);
        return Result.ok();
    }

    @ApiOperation("新人专享")
    @GetMapping("isNewPerson/{skuId}/{status}")
    public Result isNewPerson(@PathVariable Long skuId, @PathVariable Integer status) {
        skuInfoService.isNewPerson(skuId, status);
        return Result.ok();
    }

}

