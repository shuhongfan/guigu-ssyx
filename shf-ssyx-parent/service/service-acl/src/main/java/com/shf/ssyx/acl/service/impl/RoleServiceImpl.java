package com.shf.ssyx.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.ssyx.acl.mapper.RoleMapper;
import com.shf.ssyx.acl.service.AdminRoleService;
import com.shf.ssyx.acl.service.RoleService;
import com.shf.ssyx.model.acl.AdminRole;
import com.shf.ssyx.model.acl.Role;
import com.shf.ssyx.vo.acl.RoleQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private AdminRoleService adminRoleService;

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

    /**
     * 获取用户角色
     * @param adminId
     * @return
     */
    @Override
    public Map<String, Object> getRoleByAdminId(Long adminId) {
//        1.查询所有角色
        List<Role> allRolesList = baseMapper.selectList(null);

//        2.根据用户id查询用户分配角色列表
//        2.1 根据用户id查询 用户角色关系表 admin_role 查询用户分配角色id列表
//        List<adminRole>
        LambdaQueryWrapper<AdminRole> adminRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        设置查询条件，根据用户id adminId
        adminRoleLambdaQueryWrapper.eq(AdminRole::getAdminId, adminId);
        List<AdminRole> adminRoleList = adminRoleService.list(adminRoleLambdaQueryWrapper);

//        2.2 通过第一步返回集合 ，获取所有角色id的列表 List<Long>
        List<Long> roleIds = adminRoleList.stream().map(AdminRole::getRoleId).collect(Collectors.toList());

//        2.3 创建新的list集合，用于存储用户配置角色
        ArrayList<Role> assignRileList = new ArrayList<>();

//        2.4 遍历所有角色列表 allRolesList，得到每个角色
//        判断所有角色里面是否包括已经分配的角色id，封装到2.3里面新的list集合
        allRolesList.forEach(role -> {
            if (roleIds.contains(role.getId())) {
                assignRileList.add(role);
            }
        });

        Map<String, Object> result = new HashMap<>();
        result.put("allRolesList", allRolesList);
        result.put("assignRoles", assignRileList);
        return result;
    }

    /**
     * 为用户进行角色分配
     * @param adminId
     * @param roleIds
     */
    @Override
    public void saveAdminRole(Long adminId, Long[] roleIds) {
//        1.删除用户已经分配过的角色数据
//        根据用户id删除admin_role表里面对应数据
        LambdaQueryWrapper<AdminRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminRole::getAdminId, adminId);
        adminRoleService.remove(wrapper);

//        2.重新分配
//        遍历多个角色id，得到每个角色id，拿着每个角色id+用户id添加用户角色关系表
//        for (Long roleId : roleIds) {
//            AdminRole adminRole = new AdminRole();
//            adminRole.setAdminId(adminId);
//            adminRole.setRoleId(roleId);
//            adminRoleService.save(adminRole);
//        }
        ArrayList<AdminRole> list = new ArrayList<>();
        for (Long roleId : roleIds) {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(roleId);
            list.add(adminRole);
        }
        adminRoleService.saveBatch(list);
    }
}
