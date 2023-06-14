package com.shf.ssyx.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.ssyx.model.user.Leader;
import com.shf.ssyx.model.user.User;
import com.shf.ssyx.model.user.UserDelivery;
import com.shf.ssyx.user.service.UserService;
import com.shf.ssyx.user.mapper.LeaderMapper;
import com.shf.ssyx.user.mapper.UserDeliveryMapper;
import com.shf.ssyx.user.mapper.UserMapper;
import com.shf.ssyx.vo.user.LeaderAddressVo;
import com.shf.ssyx.vo.user.UserLoginVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserDeliveryMapper userDeliveryMapper;

    @Autowired
    private LeaderMapper leaderMapper;


    /**
     * 根据Openid查询用户信息
     *
     * @param openid
     * @return
     */
    @Override
    public User getUserByOpenId(String openid) {
        return baseMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getOpenId, openid)
        );
    }

    /**
     * 根据userId查询提货点和团长信息
     *
     * @param id
     * @return
     */
    @Override
    public LeaderAddressVo getLeaderAddressByUserId(Long id) {
//        根据用户id查询用户默认团长id
        UserDelivery userDelivery = userDeliveryMapper.selectOne(
                new LambdaQueryWrapper<UserDelivery>()
                        .eq(UserDelivery::getUserId, id)
                        .eq(UserDelivery::getIsDefault, 1)
        );
        if (userDelivery == null) {
            return null;
        }

        Long leaderId = userDelivery.getLeaderId();
//        拿着上面查询团长id查询leader表查询团长其他信息
        Leader leader = leaderMapper.selectById(leaderId);

//        封装数据到LeaderAddressVO
        LeaderAddressVo leaderAddressVo = new LeaderAddressVo();
        BeanUtils.copyProperties(leader, leaderAddressVo);
        leaderAddressVo.setUserId(id);
        leaderAddressVo.setLeaderId(leaderId);
        leaderAddressVo.setLeaderName(leader.getName());
        leaderAddressVo.setLeaderPhone(leader.getPhone());
        leaderAddressVo.setWareId(userDelivery.getWareId());
        leaderAddressVo.setStorePath(leader.getStorePath());

        return leaderAddressVo;
    }

    /**
     * 获取当前登录用户信息
     *
     * @param id
     * @return
     */
    @Override
    public UserLoginVo getUserLoginVo(Long id) {
        User user = baseMapper.selectById(id);

        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setUserId(id);
        userLoginVo.setNickName(user.getNickName());
        userLoginVo.setPhotoUrl(user.getPhotoUrl());
        userLoginVo.setIsNew(user.getIsNew());
        userLoginVo.setOpenId(user.getOpenId());

        UserDelivery userDelivery = userDeliveryMapper.selectOne(
                new LambdaQueryWrapper<UserDelivery>()
                        .eq(UserDelivery::getUserId, id)
                        .eq(UserDelivery::getIsDefault, 1)
        );
        if (userDelivery != null) {
            userLoginVo.setLeaderId(userDelivery.getLeaderId());
            userLoginVo.setWareId(userDelivery.getWareId());
        } else {
            userLoginVo.setLeaderId(1L);
            userLoginVo.setWareId(1L);
        }
        return userLoginVo;
    }
}
