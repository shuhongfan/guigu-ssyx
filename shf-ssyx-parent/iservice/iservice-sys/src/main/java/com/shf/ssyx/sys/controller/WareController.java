package com.shf.ssyx.sys.controller;


import com.shf.ssyx.common.result.Result;
import com.shf.ssyx.model.sys.Region;
import com.shf.ssyx.sys.service.RegionService;
import com.shf.ssyx.sys.service.WareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 仓库表 前端控制器
 * </p>
 *
 * @author shf
 * @since 2023-06-11
 */
@RestController
@Api(value = "Ware管理", tags = "Ware管理")
@RequestMapping("/admin/sys/ware")
@CrossOrigin
public class WareController {

    @Autowired
    private RegionService regionService;

    @Autowired
    private WareService wareService;

    @ApiOperation("根据区域关键词查询区域列表信息")
    @GetMapping("findRegionByKeyword/{keyword}")
    public Result findRegionByKeyword(@PathVariable("keyword") String keyword) {
        List<Region> resultList = regionService.getReginByKeyword(keyword);
        return Result.ok(resultList);
    }

    @ApiOperation(value = "获取全部仓库")
    @GetMapping("findAllList")
    public Result findAllList() {
        return Result.ok(wareService.list());
    }
}

