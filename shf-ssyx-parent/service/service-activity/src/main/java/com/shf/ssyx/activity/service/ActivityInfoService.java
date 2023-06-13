package com.shf.ssyx.activity.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.ssyx.model.activity.ActivityInfo;
import com.shf.ssyx.model.product.SkuInfo;
import com.shf.ssyx.vo.activity.ActivityRuleVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 活动表 服务类
 * </p>
 *
 * @author shf
 * @since 2023-06-12
 */
public interface ActivityInfoService extends IService<ActivityInfo> {

    /**
     * 活动列表
     * @param pageParam
     * @return
     */
    IPage<ActivityInfo> selectPage(Page<ActivityInfo> pageParam);

    /**
     * 根据活动id获取活动规则数据
     * @param id
     * @return
     */
    Map<String, Object> findActivityRuleList(Long id);

    /**
     * 在活动里面添加规则数据
     * @param activityRuleVo
     */
    void saveActivityRule(ActivityRuleVo activityRuleVo);

    /**
     * 根据关键词查询匹配SKU信息
     * @param keyword
     * @return
     */
    List<SkuInfo> findSkuInfoByKeyword(String keyword);
}
