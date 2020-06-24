package com.chiw.search.repositories;

import com.chiw.search.pojo.ChiWRecord;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ChiWRecordRepository extends ElasticsearchRepository<ChiWRecord, Integer> {

}

