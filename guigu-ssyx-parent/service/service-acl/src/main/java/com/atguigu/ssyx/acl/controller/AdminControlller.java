package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.AdminService;
import com.atguigu.ssyx.acl.service.RoleService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.common.utils.MD5;
import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "用户接口")
@RestController
@RequestMapping("/admin/acl/user")
//@CrossOrigin
public class AdminControlller {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    //为用户进行分配
//    url: `${api_name}/doAssign`,
//    method: 'post',
//    params: {
//        adminId,
//        roleId
//    }
    //参数有用户id 和 多个角色id
    @ApiOperation("为用户进行角色分配")
    @PostMapping("doAssign")
    public Result doAssign(@RequestParam Long adminId,
                           @RequestParam Long[] roleId) {
        roleService.saveAdminRole(adminId,roleId);
        return Result.ok(null);
    }


    //获取所有角色，和根据用户id查询用户分配角色列表
//    url: `${api_name}/toAssign/${adminId}`,
//    method: 'get'
    @ApiOperation("获取用户角色")
    @GetMapping("toAssign/{adminId}")
    public Result toAssign(@PathVariable Long adminId) {
        //返回map集合包含两部分数据：所有角色 和 为用户分配角色列表
       Map<String,Object> map  = roleService.getRoleByAdminId(adminId);
       return Result.ok(map);
    }

    //1 用户列表
    @ApiOperation("用户列表")
    @GetMapping("{current}/{limit}")
    public Result list(@PathVariable Long current,
                       @PathVariable Long limit,
                       AdminQueryVo adminQueryVo) {
        Page<Admin> pageParam = new Page<Admin>(current,limit);
        IPage<Admin> pageModel = adminService.selectPageUser(pageParam,adminQueryVo);
        return Result.ok(pageModel);
    }

    //2 id查询用户
//    url: `${api_name}/get/${id}`,
//    method: 'get'
    @ApiOperation("根据id查询")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Admin admin = adminService.getById(id);
        return Result.ok(admin);
    }

    //3 添加用户
//    url: `${api_name}/save`,
//    method: 'post',
//    data: user
    @ApiOperation("添加用户")
    @PostMapping("save")
    public Result save(@RequestBody Admin admin) {
        //获取输入的密码
        String password = admin.getPassword();

        //对输入密码进行加密 MD5
        String passwordMD5 = MD5.encrypt(password);

        //设置到admin对象里面
        admin.setPassword(passwordMD5);

        //调用方法添加
        adminService.save(admin);
        return Result.ok(null);
    }

    //4 修改用户
//    url: `${api_name}/update`,
//    method: 'put',
//    data: user
    @ApiOperation("修改用户")
    @PutMapping("update")
    public Result update(@RequestBody Admin admin) {
        adminService.updateById(admin);
        return Result.ok(null);
    }

    //5 id删除
//    url: `${api_name}/remove/${id}`,
//    method: 'delete'
    @ApiOperation("根据id删除用户")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        adminService.removeById(id);
        return Result.ok(null);
    }

    //6 批量删除
//    url: `${api_name}/batchRemove`,
//    method: 'delete',
//    data: ids
    // [1,2,3]
    @ApiOperation("批量删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        adminService.removeByIds(idList);
        return Result.ok(null);
    }

}
