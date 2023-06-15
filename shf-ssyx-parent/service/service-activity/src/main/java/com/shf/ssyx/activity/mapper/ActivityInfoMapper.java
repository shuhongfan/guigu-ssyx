package com.shf.ssyx.activity.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shf.ssyx.model.activity.ActivityInfo;
import com.shf.ssyx.model.activity.ActivityRule;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 活动表 Mapper 接口
 * </p>
 *
 * @author shf
 * @since 2023-06-12
 */
@Repository
public interface ActivityInfoMapper extends BaseMapper<ActivityInfo> {
    /**
     * 判断添加商品之前是否参加过活动,如果之前参加过,活动正在进行中,排除商品
     * @param skuIdList
     * @return
     */
    List<Long> selectSkuIdListExist(@Param("skuIdList")List<Long> skuIdList);

    /**
     * 根据skuID进行查询，查询sku对应活动里面规则列表
     * @param skuId
     * @return
     */
    List<ActivityRule> findActivityRule(@Param("skuId")Long skuId);

}
