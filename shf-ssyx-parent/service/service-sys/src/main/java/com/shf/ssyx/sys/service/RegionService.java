package com.shf.ssyx.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ssyx.common.result.Result;
import com.shf.ssyx.model.sys.Region;

import java.util.List;

/**
 * <p>
 * 地区表 服务类
 * </p>
 *
 * @author shf
 * @since 2023-06-11
 */
public interface RegionService extends IService<Region> {

    /**
     * 根据区域关键词查询区域列表信息
     * @param keyword
     * @return
     */
    List<Region> getReginByKeyword(String keyword);
}
