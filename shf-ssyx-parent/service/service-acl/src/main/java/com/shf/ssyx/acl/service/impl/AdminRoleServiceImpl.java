package com.shf.ssyx.acl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.ssyx.acl.mapper.AdminMapper;
import com.shf.ssyx.acl.mapper.AdminRoleMapper;
import com.shf.ssyx.acl.service.AdminRoleService;
import com.shf.ssyx.model.acl.AdminRole;
import org.springframework.stereotype.Service;

@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements AdminRoleService {
}
