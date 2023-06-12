package com.shf.ssyx.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ssyx.model.product.SkuAttrValue;

import java.util.List;

/**
 * <p>
 * spu属性值 服务类
 * </p>
 *
 * @author shf
 * @since 2023-06-11
 */
public interface SkuAttrValueService extends IService<SkuAttrValue> {

    /**
     * 根据id查询商品属性信息列表
     * @param id
     * @return
     */
    List<SkuAttrValue> getAttrValueListBySkuId(Long id);
}
