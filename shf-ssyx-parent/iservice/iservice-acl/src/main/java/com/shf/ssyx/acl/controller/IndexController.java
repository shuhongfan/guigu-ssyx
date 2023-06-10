package com.shf.ssyx.acl.controller;

import com.shf.ssyx.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Api(tags = "登录控制器")
@CrossOrigin
@RestController
@RequestMapping("/admin/acl/index")
public class IndexController {

    /**
     * 1.登录接口
     * @return
     */
    @ApiOperation("登录")
    @PostMapping("login")
    public Result login() {
//        返回token
        HashMap<String, String> map = new HashMap<>();
        map.put("token", "token-admin");
        return Result.ok(map);
    }

    /**
     * 2.获取用户信息
     * @return
     */
    @ApiOperation("获取信息")
    @GetMapping("info")
    public Result info() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "shf");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return Result.ok(map);
    }

    /**
     * 3.退出登录
     * @return
     */
    @ApiOperation("退出登录")
    @PostMapping("logout")
    public Result logout() {
        return Result.ok(null);
    }
}
