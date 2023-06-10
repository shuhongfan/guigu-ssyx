package com.shf.ssyx.acl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ssyx.model.acl.Role;
import com.shf.ssyx.vo.acl.RoleQueryVo;

public interface RoleService extends IService<Role> {
    //角色分页列表
    IPage<Role> selectRolePage(Page<Role> pageParam, RoleQueryVo roleQueryVo);
}
