package com.shf.ssyx.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.ssyx.acl.mapper.PermissionMapper;
import com.shf.ssyx.acl.service.PermissionService;
import com.shf.ssyx.acl.utils.PermissionHelper;
import com.shf.ssyx.model.acl.Permission;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    /**
     * 查询所有菜单
     * @return
     */
    @Override
    public List<Permission> queryAllPermission() {
//        1. 查询所有菜单
        List<Permission> allPermissionList = baseMapper.selectList(null);

//        2.转换要求数据格式
        List<Permission> permissions = PermissionHelper.buildPermission(allPermissionList);

        return permissions;
    }

    /**
     * 递归删除菜单
     * @param id
     */
    @Override
    public void removeChildById(Long id) {
//        1.创建list集合idList，封装所有要删除的菜单id
        ArrayList<Long> idList = new ArrayList<>();

//        根据当前菜单id，获取当前菜单下面所有子菜单
//        如果子菜单下面还有子菜单，都要获取到
//        重点:地柜找当前菜单子菜单
        this.getAllPermissionId(id, idList);

//        调用方法根据多个菜单id删除
        baseMapper.deleteBatchIds(idList);
    }

//    重点：地柜找当前菜单下面的所有子菜单
//    第一个参数是当前菜单id
//    第二个参数最终封装list集合,包含所有菜单id
    private void getAllPermissionId(Long id, ArrayList<Long> idList) {
//        根据当前菜单id查询下面子菜单
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Permission::getPid, id);

//        子菜单
        List<Permission> childList = baseMapper.selectList(wrapper);

//        递归查询是否还有子菜单，有继续递归查询
        childList.forEach(item->{
//            封装菜单id到IDList集合里面
            idList.add(item.getId());

//            递归
            this.getAllPermissionId(item.getId(), idList);
        });
    }
}
