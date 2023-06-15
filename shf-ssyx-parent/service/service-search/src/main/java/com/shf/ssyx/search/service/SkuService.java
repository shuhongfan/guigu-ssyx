package com.shf.ssyx.search.service;

import com.shf.ssyx.model.search.SkuEs;
import com.shf.ssyx.vo.search.SkuEsQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface SkuService {
    /**
     * 上架
     * @param skuId
     */
    void upperSku(Long skuId);

    /**
     * 下架
     * @param skuId
     */
    void lowerSku(Long skuId);

    /**
     * 获取爆款商品
     * @return
     */
    List<SkuEs> findHotSkuList();

    /**
     * 查询分类商品
     * @param pageabel
     * @param skuEsQueryVo
     * @return
     */
    Page<SkuEs> search(PageRequest pageabel, SkuEsQueryVo skuEsQueryVo);

    /**
     * 更新商品热度
     * @param skuId
     */
    void incrHotScore(Long skuId);
}
