package com.shf.ssyx.product.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shf.ssyx.model.product.SkuPoster;
import com.shf.ssyx.product.mapper.SkuPosterMapper;
import com.shf.ssyx.product.service.SkuPosterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品海报表 服务实现类
 * </p>
 *
 * @author shf
 * @since 2023-06-11
 */
@Service
public class SkuPosterServiceImpl extends ServiceImpl<SkuPosterMapper, SkuPoster> implements SkuPosterService {

    /**
     * 根据id查询商品海报列表
     * @param id
     * @return
     */
    @Override
    public List<SkuPoster> getPosterListBySkuId(Long id) {
        LambdaQueryWrapper<SkuPoster> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuPoster::getSkuId, id);
        List<SkuPoster> skuPosterList = baseMapper.selectList(wrapper);
        return skuPosterList;
    }
}
