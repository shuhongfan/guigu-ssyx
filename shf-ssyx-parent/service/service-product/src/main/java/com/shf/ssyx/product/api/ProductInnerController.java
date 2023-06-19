package com.shf.ssyx.product.api;

import com.shf.ssyx.model.product.Category;
import com.shf.ssyx.model.product.SkuInfo;
import com.shf.ssyx.product.service.CategoryService;
import com.shf.ssyx.product.service.SkuInfoService;
import com.shf.ssyx.vo.product.SkuInfoVo;
import com.shf.ssyx.vo.product.SkuStockLockVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "ES远程调用接口")
@RestController
@RequestMapping("/api/product")
public class ProductInnerController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SkuInfoService skuInfoService;

    @ApiOperation("根据分类Id获取分类信息")
    @GetMapping("inner/getCategory/{categoryId}")
    public Category getCategory(@PathVariable Long categoryId) {
        Category category = categoryService.getById(categoryId);
        return category;
    }

    @ApiOperation("根据SKUID获取SKU信息")
    @GetMapping("inner/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfo(@PathVariable Long skuId) {
        SkuInfo skuInfo = skuInfoService.getById(skuId);
        return skuInfo;
    }

    @ApiOperation("根据skuId列表得到sku信息列表")
    @PostMapping("inner/findSkuInfoList")
    public List<SkuInfo> findSkuInfoList(@RequestBody List<Long> skuIdList) {
        return skuInfoService.findSkuInfoList(skuIdList);
    }

    @ApiOperation("根据关键词匹配SKU列表")
    @GetMapping("inner/findSkuInfoByKeyword/{keyword}")
    public List<SkuInfo> findSkuInfoByKeyword(@PathVariable String keyword) {
        return skuInfoService.findSkuInfoByKeyword(keyword);
    }

    @ApiOperation("根据分类id获取分类列表")
    @PostMapping("inner/findCategoryList")
    public List<Category> findCategoryList(@RequestBody List<Long> categoryId) {
        return categoryService.listByIds(categoryId);
    }

    @ApiOperation("获取所有分类")
    @GetMapping("inner/findAllCategoryList")
    public List<Category> findAllCategoryList() {
        List<Category> categoryList = categoryService.list();
        return categoryList;
    }

    @ApiOperation("获取新人专享商品")
    @GetMapping("inner/findNewPersonSkuInfoList")
    public List<SkuInfo> findNewPersonSkuInfoList() {
        return skuInfoService.findNewPersonSkuInfoList();
    }

    @ApiOperation("根据skuId获取SKu信息")
    @GetMapping("inner/getSkuInfoVo/{skuId}")
    public SkuInfoVo getSkuInfoVo(@PathVariable Long skuId) {
        return skuInfoService.getSkuInfoVo(skuId);
    }

    @ApiOperation("验证和锁定库存")
    @PostMapping("inner/checkAndLock/{orderNo}")
    public Boolean checkAndLock(@RequestBody List<SkuStockLockVo> skuStockLockVoList, @PathVariable String orderNo) {
        return skuInfoService.checkAndLock(skuStockLockVoList, orderNo);
    }
}
