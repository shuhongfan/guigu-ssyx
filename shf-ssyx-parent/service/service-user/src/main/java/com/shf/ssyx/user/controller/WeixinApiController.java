package com.shf.ssyx.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.shf.ssyx.common.auth.AuthContextHolder;
import com.shf.ssyx.common.constant.RedisConst;
import com.shf.ssyx.common.result.Result;
import com.shf.ssyx.common.utils.JwtHelper;
import com.shf.ssyx.enums.UserType;
import com.shf.ssyx.model.user.User;
import com.shf.ssyx.user.service.UserService;
import com.shf.ssyx.user.utils.ConstantPropertiesUtil;
import com.shf.ssyx.user.utils.HttpClientUtils;
import com.shf.ssyx.vo.user.LeaderAddressVo;
import com.shf.ssyx.vo.user.UserLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Api(tags = "微信接口")
@RestController
@RequestMapping("/api/user/weixin")
public class WeixinApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "微信登录获取openid(小程序)")
    @GetMapping("/wxLogin/{code}")
    public Result callback(@PathVariable String code) {
//        1. 得到微信返回code临时票据值
//        2.拿着Code + 小程序id + 小程序密钥 请求微信接口服务
        String wxOpenAppId = ConstantPropertiesUtil.WX_OPEN_APP_ID;
        String wxOpenAppSecret = ConstantPropertiesUtil.WX_OPEN_APP_SECRET;
        StringBuffer url = new StringBuffer()
                .append("https://api.weixin.qq.com/sns/jscode2session")
                .append("?appid=%s")
                .append("&secret=%s")
                .append("&js_code=%s")
                .append("&grant_type=authorization_code");
        String tokenUrl = String.format(url.toString(), wxOpenAppId, wxOpenAppSecret, code);

//        HttpClient发送get请求
        String result = null;
        try {
            result = HttpClientUtils.get(tokenUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        3.请求微信接口服务，返回两个值 session_key和openID
        JSONObject jsonObject = JSONObject.parseObject(result);
        String sessionKey = jsonObject.getString("session_key");
        String openid = jsonObject.getString("openid");

//        4. 添加到数据库中
//        判断是否是第一次使用微信授权登录
        User user = userService.getUserByOpenId(openid);
        if (null == user) {
            user = new User();
            user.setOpenId(openid);
            user.setNickName(openid);
            user.setPhotoUrl("");
            user.setUserType(UserType.USER);
            user.setIsNew(0);
            userService.save(user);
        }

//        5.根据userId查询提货点和团长信息
        LeaderAddressVo leaderAddressVo = userService.getLeaderAddressByUserId(user.getId());

//        6.使用JWT工具根据userId和userName生成token
        String token = JwtHelper.createToken(user.getId(), user.getNickName());

//        7.获取当前登录用户信息，放到Redis里面，设置有效时间
        UserLoginVo userLoginVo = userService.getUserLoginVo(user.getId());
        redisTemplate.opsForValue()
                .set(
                        RedisConst.USER_LOGIN_KEY_PREFIX + user.getId(),
                        userLoginVo,
                        RedisConst.USERKEY_TIMEOUT,
                        TimeUnit.DAYS
                );

//        8.需要数据封装到map返回
        HashMap<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("token", token);
        map.put("leaderAddressVo", leaderAddressVo);
        return Result.ok(map);
    }

    @PostMapping("/auth/updateUser")
    @ApiOperation(value = "更新用户昵称与头像")
    public Result updateUser(@RequestBody User user) {
        User user1 = userService.getById(AuthContextHolder.getUserId());
        //把昵称更新为微信用户
        user1.setNickName(user.getNickName().replaceAll("[ue000-uefff]", "*"));
        user1.setPhotoUrl(user.getPhotoUrl());
        userService.updateById(user1);
        return Result.ok(null);
    }
}
