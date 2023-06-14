package com.shf.ssyx.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ssyx.model.user.User;
import com.shf.ssyx.vo.user.LeaderAddressVo;
import com.shf.ssyx.vo.user.UserLoginVo;

public interface UserService extends IService<User> {
    /**
     * 根据Openid查询用户信息
     * @param openid
     * @return
     */
    User getUserByOpenId(String openid);

    /**
     * 根据userId查询提货点和团长信息
     * @param id
     * @return
     */
    LeaderAddressVo getLeaderAddressByUserId(Long id);

    /**
     * 获取当前登录用户信息
     * @param id
     * @return
     */
    UserLoginVo getUserLoginVo(Long id);
}
