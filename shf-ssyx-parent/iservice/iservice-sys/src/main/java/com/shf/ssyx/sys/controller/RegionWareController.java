package com.shf.ssyx.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.ssyx.common.result.Result;
import com.shf.ssyx.model.sys.RegionWare;
import com.shf.ssyx.sys.service.RegionWareService;
import com.shf.ssyx.vo.sys.RegionWareQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 城市仓库关联表 前端控制器
 * </p>
 *
 * @author shf
 * @since 2023-06-11
 */
@Api(value = "RegionWare管理", tags = "RegionWare管理")
@RestController
@RequestMapping(value="/admin/sys/regionWare")
@SuppressWarnings({"unchecked", "rawtypes"})
public class RegionWareController {

    @Autowired
    private RegionWareService regionWareService;

    @ApiOperation("开通区域列表")
    @GetMapping("{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, RegionWareQueryVo regionWareQueryVo) {
        Page<RegionWare> pageParam = new Page<RegionWare>(page, limit);
        IPage<RegionWare> pageModel = regionWareService.selectPageRegionWare(pageParam, regionWareQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation("添加开通区域")
    @PostMapping("save")
    public Result addReginWare(@RequestBody RegionWare regionWare) {
        regionWareService.saveRegionWare(regionWare);
        return Result.ok(null);
    }

    @ApiOperation("删除开通区域")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        regionWareService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation("取消开通区域")
    @PostMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        regionWareService.updateStatus(id, status);
        return Result.ok(null);
    }

}

