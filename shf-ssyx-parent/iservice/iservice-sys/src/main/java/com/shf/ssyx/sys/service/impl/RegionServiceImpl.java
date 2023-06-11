package com.shf.ssyx.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shf.ssyx.common.result.Result;
import com.shf.ssyx.model.sys.Region;
import com.shf.ssyx.sys.mapper.RegionMapper;
import com.shf.ssyx.sys.service.RegionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 地区表 服务实现类
 * </p>
 *
 * @author shf
 * @since 2023-06-11
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements RegionService {

    /**
     * 根据区域关键词查询区域列表信息
     * @param keyword
     * @return
     */
    @Override
    public List<Region> getReginByKeyword(String keyword) {
        LambdaQueryWrapper<Region> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Region::getName, keyword);
        List<Region> list = baseMapper.selectList(wrapper);
        return list;
    }
}
