package com.chiw.search.repositories;

import com.chiw.search.pojo.ChiWTag;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ChiWTagRepository extends ElasticsearchRepository<ChiWTag, Integer> {
}

