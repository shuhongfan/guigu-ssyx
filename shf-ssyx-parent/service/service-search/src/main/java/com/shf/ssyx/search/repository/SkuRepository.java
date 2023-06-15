package com.shf.ssyx.search.repository;

import com.shf.ssyx.model.search.SkuEs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuRepository extends ElasticsearchRepository<SkuEs,Long> {
    /**
     * 获取爆款商品
     * @param pageable
     * @return
     */
    Page<SkuEs> findByOrderByHotScoreDesc(PageRequest pageable);

    /**
     * 根据仓库id+分类id查询
     * @param categoryId
     * @param wareId
     * @param pageabel
     * @return
     */
    Page<SkuEs> finByCategoryIdAndWareId(Long categoryId, Long wareId, PageRequest pageabel);

    /**
     * 根据仓库id+keyword查询
     * @param keyword
     * @param wareId
     * @return
     */
    Page<SkuEs> findByKeywordAndWareId(String keyword, Long wareId);
}
