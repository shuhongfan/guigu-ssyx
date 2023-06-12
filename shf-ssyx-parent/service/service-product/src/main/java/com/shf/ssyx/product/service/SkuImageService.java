package com.shf.ssyx.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ssyx.model.product.SkuImage;

import java.util.List;

/**
 * <p>
 * 商品图片 服务类
 * </p>
 *
 * @author shf
 * @since 2023-06-11
 */
public interface SkuImageService extends IService<SkuImage> {

    /**
     * 根据id查询商品图片列表
     * @param id
     * @return
     */
    List<SkuImage> getImageListBySkuId(Long id);
}
