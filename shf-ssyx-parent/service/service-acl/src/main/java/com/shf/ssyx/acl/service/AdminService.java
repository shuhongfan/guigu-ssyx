package com.shf.ssyx.acl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ssyx.model.acl.Admin;
import com.shf.ssyx.vo.acl.AdminQueryVo;


public interface AdminService extends IService<Admin> {
    /**
     * 用户列表
     * @param pageParam
     * @param adminQueryVo
     * @return
     */
    public IPage<Admin> selectPageUser(Page<Admin> pageParam, AdminQueryVo adminQueryVo);
}
