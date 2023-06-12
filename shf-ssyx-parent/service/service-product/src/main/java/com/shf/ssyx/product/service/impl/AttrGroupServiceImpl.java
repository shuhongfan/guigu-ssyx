package com.shf.ssyx.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.ssyx.model.product.AttrGroup;
import com.shf.ssyx.product.mapper.AttrGroupMapper;
import com.shf.ssyx.product.service.AttrGroupService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.ssyx.vo.product.AttrGroupQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 属性分组 服务实现类
 * </p>
 *
 * @author shf
 * @since 2023-06-11
 */
@Service
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroup> implements AttrGroupService {

    /**
     * 平台属性分组列表
     * @param attrGroupPage
     * @param attrGroupQueryVo
     * @return
     */
    @Override
    public IPage<AttrGroup> selectPageAttrGroup(Page<AttrGroup> attrGroupPage, AttrGroupQueryVo attrGroupQueryVo) {
        String name = attrGroupQueryVo.getName();
        LambdaQueryWrapper<AttrGroup> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like(AttrGroup::getName, name);
        }
        Page<AttrGroup> pageModel = baseMapper.selectPage(attrGroupPage, wrapper);
        return pageModel;
    }

    /**
     * 查询所有平台属性分组列表
     * @return
     */
    @Override
    public List<AttrGroup> findAllListAttrGroup() {
        LambdaQueryWrapper<AttrGroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(AttrGroup::getId);
        List<AttrGroup> list = baseMapper.selectList(wrapper);
        return list;
    }
}
