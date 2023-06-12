package com.shf.ssyx.product.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ssyx.model.product.Category;
import com.shf.ssyx.vo.product.CategoryQueryVo;

/**
 * <p>
 * 商品三级分类 服务类
 * </p>
 *
 * @author shf
 * @since 2023-06-11
 */
public interface CategoryService extends IService<Category> {

    /**
     * 商品分类列表
     * @param categoryPage
     * @param categoryQueryVo
     * @return
     */
    IPage<Category> selectPageCategory(Page<Category> categoryPage, CategoryQueryVo categoryQueryVo);
}
