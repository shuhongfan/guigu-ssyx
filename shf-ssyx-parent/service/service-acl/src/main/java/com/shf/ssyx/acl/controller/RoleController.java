package com.shf.ssyx.acl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.ssyx.acl.service.RoleService;
import com.shf.ssyx.common.result.Result;
import com.shf.ssyx.model.acl.Role;
import com.shf.ssyx.vo.acl.RoleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/acl/role")
@Api(tags = "角色接口")
@Slf4j
@CrossOrigin
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("条件分页查询接口")
    @GetMapping("{current}/{limit}")
    public Result index(@PathVariable Long current, @PathVariable Long limit, RoleQueryVo roleQueryVo) {
//        1.创建Page对象,传递当前页和每页记录数
        Page<Role> pageParam = new Page<Role>(current, limit);

//        2.调用Service方法显示条件分页查询，返回分页对象
        IPage<Role> pageModel = roleService.selectRolePage(pageParam, roleQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation("根据id查询角色")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Role role = roleService.getById(id);
        return Result.ok(role);
    }

    @ApiOperation("新增角色")
    @PostMapping("save")
    public Result save(@RequestBody Role role) {
        roleService.save(role);
        return Result.ok(null);
    }

    @ApiOperation("修改角色")
    @PutMapping("update")
    public Result update(@RequestBody Role role) {
        roleService.updateById(role);
        return Result.ok(null);
    }

    @ApiOperation("根据id删除角色")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        roleService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation("批量删除角色")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids) {
        roleService.removeByIds(ids);
        return Result.ok(null);
    }
}
