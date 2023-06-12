package com.shf.ssyx.search.repository;

import com.shf.ssyx.model.search.SkuEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuRepository extends ElasticsearchRepository<SkuEs,Long> {
}
