package com.shf.ssyx.sys.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ssyx.model.sys.RegionWare;
import com.shf.ssyx.vo.sys.RegionWareQueryVo;

/**
 * <p>
 * 城市仓库关联表 服务类
 * </p>
 *
 * @author shf
 * @since 2023-06-11
 */
public interface RegionWareService extends IService<RegionWare> {

    /**
     * 开通区域列表
     * @param pageParam
     * @param regionWareQueryVo
     * @return
     */
    IPage<RegionWare> selectPageRegionWare(Page<RegionWare> pageParam, RegionWareQueryVo regionWareQueryVo);

    /**
     * 添加开通区域
     * @param regionWare
     */
    void saveRegionWare(RegionWare regionWare);

    /**
     * 取消开通区域
     * @param id
     * @param status
     */
    void updateStatus(Long id, Integer status);
}
