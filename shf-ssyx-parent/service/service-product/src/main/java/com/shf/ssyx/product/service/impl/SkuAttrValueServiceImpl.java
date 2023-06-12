package com.shf.ssyx.product.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shf.ssyx.model.product.SkuAttrValue;
import com.shf.ssyx.product.mapper.SkuAttrValueMapper;
import com.shf.ssyx.product.service.SkuAttrValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * spu属性值 服务实现类
 * </p>
 *
 * @author shf
 * @since 2023-06-11
 */
@Service
public class SkuAttrValueServiceImpl extends ServiceImpl<SkuAttrValueMapper, SkuAttrValue> implements SkuAttrValueService {

    /**
     * 根据id查询商品属性信息列表
     * @param id
     * @return
     */
    @Override
    public List<SkuAttrValue> getAttrValueListBySkuId(Long id) {
        LambdaQueryWrapper<SkuAttrValue> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuAttrValue::getSkuId, id);
        List<SkuAttrValue> skuAttrValueList = baseMapper.selectList(wrapper);
        return skuAttrValueList;
    }
}
