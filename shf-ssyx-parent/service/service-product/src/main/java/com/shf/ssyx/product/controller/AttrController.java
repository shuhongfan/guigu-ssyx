package com.shf.ssyx.product.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.ssyx.common.result.Result;
import com.shf.ssyx.model.product.AttrGroup;
import com.shf.ssyx.product.service.AttrGroupService;
import com.shf.ssyx.vo.product.AttrGroupQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品属性 前端控制器
 * </p>
 *
 * @author shf
 * @since 2023-06-11
 */
@Api(value = "Attr管理", tags = "平台属性管理")
@RestController
@RequestMapping(value="/admin/product/attr")
@CrossOrigin
public class AttrController {
    @Autowired
    private AttrGroupService attrGroupService;

    @ApiOperation("平台属性分组列表")
    @GetMapping("{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, AttrGroupQueryVo attrGroupQueryVo) {
        Page<AttrGroup> attrGroupPage = new Page<>(page, limit);
        IPage<AttrGroup> pageModel = attrGroupService.selectPageAttrGroup(attrGroupPage, attrGroupQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation("查询所有平台属性分组列表")
    @GetMapping("findAllList")
    public Result findAllList() {
        List<AttrGroup> list = attrGroupService.findAllListAttrGroup();
        return Result.ok(list);
    }

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        AttrGroup attrGroup = attrGroupService.getById(id);
        return Result.ok(attrGroup);
    }

    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(@RequestBody AttrGroup attrGroup) {
        attrGroupService.save(attrGroup);
        return Result.ok();
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(@RequestBody AttrGroup attrGroup) {
        attrGroupService.updateById(attrGroup);
        return Result.ok();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        attrGroupService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        attrGroupService.removeByIds(idList);
        return Result.ok();
    }


}

