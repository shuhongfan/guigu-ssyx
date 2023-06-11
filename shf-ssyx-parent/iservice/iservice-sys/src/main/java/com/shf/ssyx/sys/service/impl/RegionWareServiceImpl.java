package com.shf.ssyx.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.ssyx.common.exception.SsyxException;
import com.shf.ssyx.common.result.ResultCodeEnum;
import com.shf.ssyx.model.sys.RegionWare;
import com.shf.ssyx.sys.mapper.RegionWareMapper;
import com.shf.ssyx.sys.service.RegionWareService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.ssyx.vo.sys.RegionWareQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 城市仓库关联表 服务实现类
 * </p>
 *
 * @author shf
 * @since 2023-06-11
 */
@Service
public class RegionWareServiceImpl extends ServiceImpl<RegionWareMapper, RegionWare> implements RegionWareService {

    /**
     * 开通区域列表
     * @param pageParam
     * @param regionWareQueryVo
     * @return
     */
    @Override
    public IPage<RegionWare> selectPageRegionWare(Page<RegionWare> pageParam, RegionWareQueryVo regionWareQueryVo) {
//        1. 获取查询条件值
        String keyword = regionWareQueryVo.getKeyword();

//        2.判断条件值是否为空，不为空封装条件
        LambdaQueryWrapper<RegionWare> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)) {
            wrapper.like(RegionWare::getRegionName, keyword)
                    .or().like(RegionWare::getWareName,keyword);
        }

//        3.调用方法实现分页查询
        Page<RegionWare> regionWarePage = baseMapper.selectPage(pageParam, wrapper);
        return regionWarePage;
    }

    /**
     * 添加开通区域
     * @param regionWare
     */
    @Override
    public void saveRegionWare(RegionWare regionWare) {
//        判断区域是否已经开通了
        LambdaQueryWrapper<RegionWare> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RegionWare::getRegionId, regionWare.getRegionId());

        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) { // 已经存在
            throw new SsyxException(ResultCodeEnum.REGION_OPEN);
        }

    }

    /**
     * 取消开通区域
     * @param id
     * @param status
     */
    @Override
    public void updateStatus(Long id, Integer status) {
        RegionWare regionWare = baseMapper.selectById(id);
        regionWare.setStatus(status);
        baseMapper.updateById(regionWare);
    }
}
