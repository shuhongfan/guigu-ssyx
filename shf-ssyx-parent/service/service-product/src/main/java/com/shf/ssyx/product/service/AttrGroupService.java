package com.shf.ssyx.product.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ssyx.model.product.AttrGroup;
import com.shf.ssyx.vo.product.AttrGroupQueryVo;

import java.util.List;

/**
 * <p>
 * 属性分组 服务类
 * </p>
 *
 * @author shf
 * @since 2023-06-11
 */
public interface AttrGroupService extends IService<AttrGroup> {

    /**
     * 平台属性分组列表
     * @param attrGroupPage
     * @param attrGroupQueryVo
     * @return
     */
    IPage<AttrGroup> selectPageAttrGroup(Page<AttrGroup> attrGroupPage, AttrGroupQueryVo attrGroupQueryVo);

    /**
     * 查询所有平台属性分组列表
     * @return
     */
    List<AttrGroup> findAllListAttrGroup();
}
