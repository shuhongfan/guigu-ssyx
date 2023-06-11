package com.shf.ssyx.acl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ssyx.model.acl.Permission;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    /**
     * 查询所有菜单
     * @return
     */
    List<Permission> queryAllPermission();

    /**
     * 递归删除菜单
     * @param id
     */
    void removeChildById(Long id);
}
