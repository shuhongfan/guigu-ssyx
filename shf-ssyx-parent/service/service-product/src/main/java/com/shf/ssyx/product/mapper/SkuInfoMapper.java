package com.shf.ssyx.product.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shf.ssyx.model.product.SkuInfo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * sku信息 Mapper 接口
 * </p>
 *
 * @author shf
 * @since 2023-06-11
 */
public interface SkuInfoMapper extends BaseMapper<SkuInfo> {

    /**
     * 解锁库存
     * @param skuId
     * @param skuNum
     */
    void unlockStock(@Param("skuId") Long skuId, @Param("skuNum") Integer skuNum);

    /**
     * 验证库存
     * @param skuId
     * @param skuNum
     * @return
     */
    SkuInfo checkStock(@Param("skuId") Long skuId, @Param("skuNum") Integer skuNum);

    /**
     * 锁定库存
     * @param skuId
     * @param skuNum
     * @return
     */
    Integer lockStock(@Param("skuId") Long skuId, @Param("skuNum") Integer skuNum);
}
