package com.shf.ssyx.search.controller;

import com.shf.ssyx.common.result.Result;
import com.shf.ssyx.model.search.SkuEs;
import com.shf.ssyx.search.service.SkuService;
import com.shf.ssyx.vo.search.SkuEsQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "商品搜索列表接口")
@RestController
@RequestMapping("/api/search/sku")
public class SkuApiController {

    @Autowired
    private SkuService skuService;

    @ApiOperation("上架")
    @GetMapping("inner/upperSku/{skuId}")
    public Result upperSku(@PathVariable Long skuId) {
        skuService.upperSku(skuId);
        return Result.ok();
    }

    @ApiOperation("下架")
    @GetMapping("inner/lowerSku/{skuId}")
    public Result lowerSku(@PathVariable Long skuId) {
        skuService.lowerSku(skuId);
        return Result.ok();
    }

    @ApiOperation("获取爆款商品")
    @GetMapping("inner/findHotSkuList")
    public List<SkuEs> findHotSkuList() {
        return skuService.findHotSkuList();
    }


    @ApiOperation("查询分类商品")
    @GetMapping("{page}/{limit}")
    public Result listSku(@PathVariable Integer page, @PathVariable Integer limit, SkuEsQueryVo skuEsQueryVo) {
        PageRequest pageabel = PageRequest.of(page, limit);
        Page<SkuEs> pageModel = skuService.search(pageabel, skuEsQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation("更新商品热度")
    @GetMapping("inner/incrHotScore/{skuId}")
    public Boolean incrHotScore(@PathVariable Long skuId) {
        skuService.incrHotScore(skuId);
        return true;
    }
}
