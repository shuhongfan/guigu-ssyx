package com.shf.ssyx.user.api;

import com.shf.ssyx.user.service.UserService;
import com.shf.ssyx.vo.user.LeaderAddressVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "团长接口")
@RestController
@RequestMapping("/api/user/leader")
public class LeaderAddressApiController {
    @Autowired
    private UserService userService;

    /**
     * 根据userId查询提货点和团长信息
     * @param userId
     * @return
     */
    @ApiOperation("提货点地址信息")
    @GetMapping("/inner/getUserAddressByUserId/{userId}")
    public LeaderAddressVo getUserAddressByUserId(@PathVariable Long userId) {
        return userService.getLeaderAddressByUserId(userId);
    }
}
