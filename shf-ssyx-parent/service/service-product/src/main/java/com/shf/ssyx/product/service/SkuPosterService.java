package com.shf.ssyx.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ssyx.model.product.SkuPoster;

import java.util.List;

/**
 * <p>
 * 商品海报表 服务类
 * </p>
 *
 * @author shf
 * @since 2023-06-11
 */
public interface SkuPosterService extends IService<SkuPoster> {

    /**
     * 根据id查询商品海报列表
     * @param id
     * @return
     */
    List<SkuPoster> getPosterListBySkuId(Long id);
}
