package com.shf.ssyx.acl.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.ssyx.acl.mapper.RoleMapper;
import com.shf.ssyx.model.acl.Role;
import com.shf.ssyx.vo.acl.RoleQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public IPage<Role> selectRolePage(Page<Role> pageParam, RoleQueryVo roleQueryVo) {
//        获取条件值
        String roleName = roleQueryVo.getRoleName();

//        创建mp条件对象
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();

//        判断条件值是否为空
        if (!StringUtils.isEmpty(roleName)) {
            queryWrapper.eq(Role::getRoleName, roleName);
        }

//        调用方法实现条件分页查询
        Page<Role> rolePage = baseMapper.selectPage(pageParam, queryWrapper);

        return rolePage;
    }
}
