package com.shf.ssyx.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.ssyx.acl.mapper.AdminMapper;
import com.shf.ssyx.acl.service.AdminService;
import com.shf.ssyx.model.acl.Admin;
import com.shf.ssyx.vo.acl.AdminQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService  {
    @Override
    public IPage<Admin> selectPageUser(Page<Admin> pageParam, AdminQueryVo adminQueryVo) {
        String username = adminQueryVo.getUsername();
        String name = adminQueryVo.getName();

        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(username)) {
            queryWrapper.eq(Admin::getUsername, username);
        }
        if (!StringUtils.isEmpty(username)) {
            queryWrapper.like(Admin::getName, name);
        }

        IPage<Admin> adminIPage = baseMapper.selectPage(pageParam, queryWrapper);
        return adminIPage;
    }
}
