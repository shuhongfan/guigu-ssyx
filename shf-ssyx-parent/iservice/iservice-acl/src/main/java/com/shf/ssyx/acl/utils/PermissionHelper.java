package com.shf.ssyx.acl.utils;

import com.shf.ssyx.model.acl.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PermissionHelper {
    public static List<Permission> buildPermission(List<Permission> allList) {
//        创建最终数据封装List集合
        ArrayList<Permission> trees = new ArrayList<>();

//        遍历所有菜单list集合，得到第一层数据，pid=0
        for (Permission permission : allList) {
//            pid=0数据，第一层数据
            if (permission.getPid() == 0) {
                permission.setLevel(1);

//                调用方法，从第一层开始往下找
                trees.add(findChildren(permission, allList));
            }
        }

        return trees;
    }

//    递归往下找
//    permission山层节点，从这里往下找
//    allList 所有菜单
    private static Permission findChildren(Permission permission, List<Permission> allList) {
        permission.setChildren(new ArrayList<>());

//        遍历allList所有菜单数据
//        判断:当前节点id=pid是否一样，封装，递归往下找
        allList.forEach(item->{
            if (Objects.equals(item.getPid(), permission.getId())) {
                int level = permission.getLevel() + 1;
                item.setLevel(level);
                if (permission.getChildren() == null) {
                    permission.setChildren(new ArrayList<>());
                }
//                封装下一层数据
                permission.getChildren().add(findChildren(item, allList));
            }
        });
        return permission;
    }
}
