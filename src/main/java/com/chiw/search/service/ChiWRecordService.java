package com.chiw.search.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.chiw.search.pojo.*;
import com.chiw.search.repositories.ChiWRecordRepository;
import com.chiw.search.repositories.ChiWTagRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

@Service
public class ChiWRecordService extends BaseService{

    private ChiWRecordRepository chiWRecordRepository;

    private ChiWTagRepository chiWTagRepository;

    @Autowired
    public ChiWRecordService(ChiWRecordRepository chiWRecordRepository,ChiWTagRepository chiWTagRepository) {
        this.chiWRecordRepository = chiWRecordRepository;
        this.chiWTagRepository = chiWTagRepository;
    }

    /**
     * 根据id查询标签
     */

    public Map<String,Object> findTagNameBySuperId(String superId){
        Map<String, Object> serviceResult = this.initServiceResult();
        try {

            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
            // 添加查询
            queryBuilder.withQuery(termQuery("super_id", superId));
            queryBuilder.withFields("tag_id","tag_name","tag_value_level");
            // 搜索，获取结果
            Page<ChiWRecord> chiWRecords = chiWRecordRepository.search(queryBuilder.build());
            List<ChiWRecord> contentList = chiWRecords.getContent();
            //处理输出格式
            //防止输出重复
            List<String> tagList = new ArrayList<>();
            List<ChiWTagOutput> outpuList = new ArrayList<>();

            contentList.forEach(eachContent->{
                String tagId = eachContent.getTag_id();
                if(tagList.contains(tagId)){
                }else{
                    tagList.add(tagId);
                    ChiWTagOutput detail = new ChiWTagOutput();
                    detail.setTag_id(tagId);
                    detail.setTag_name(eachContent.getTag_name());
                    detail.setTag_value_level(eachContent.getTag_value_level());
                    outpuList.add(detail);
                }
            });

            serviceResult.put("data",outpuList);

        }catch (Exception e){

            serviceResult.put("msg",e.getMessage());
        }
        return serviceResult;
    }

    /**
     * 根据标签查询会员id
     */

    public Map<String,Object> findMemIdByTagId(String pageNo,String pageSize,List<String> tags){
        Map<String, Object> serviceResult = this.initServiceResult();
        try {

            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
            Integer pageNum = Integer.valueOf(pageNo);

            Integer pageS = Integer.valueOf(pageSize);
//
            // 查询
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

            tags.forEach(tag->{
                queryBuilder.withQuery(boolQueryBuilder.must(termQuery("tag_id.keyword", tag)));
            });

            queryBuilder.withQuery(boolQueryBuilder.mustNot(termQuery("real_matrials.keyword", "30")));
            Pageable pageable = new Pageable() {
                @Override
                public int getPageNumber() {
                    return pageNum-1;
                }

                @Override
                public int getPageSize() {
                    return pageS;
                }

                @Override
                public long getOffset() {
                    return (pageNum-1)*pageS;
                }

                @Override
                public Sort getSort() {
                    return null;
                }

                @Override
                public Pageable next() {
                    return null;
                }

                @Override
                public Pageable previousOrFirst() {
                    return null;
                }

                @Override
                public Pageable first() {
                    return null;
                }

                @Override
                public boolean hasPrevious() {
                    return false;
                }
            };
            queryBuilder.withPageable(pageable);

            // 搜索，获取结果
            Page<ChiWRecord> chiWRecords = chiWRecordRepository.search(queryBuilder.build());

            HashSet hashSet = new HashSet();
            chiWRecords.getContent().forEach(chiWRecord->{
                hashSet.add(chiWRecord.getSuper_id());
            });

            serviceResult.put("data",hashSet);
            serviceResult.put("currentPage",pageNo);
            serviceResult.put("total",chiWRecords.getTotalElements());
            serviceResult.put("totalPages",chiWRecords.getTotalPages());



        }catch (Exception e){

            serviceResult.put("msg",e.getMessage());
        }
        return serviceResult;
    }

    /**
     * 根据会员标签
     */

    public Map<String,Object> findTagByCategory(String memberId,List<String> materialCodes){
        Map<String, Object> serviceResult = this.initServiceResult();
        try {

            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
            // 查询
            queryBuilder.withQuery(termQuery("super_id.keyword", memberId));

//            materialCodes.forEach();
            //queryBuilder.withQuery(QueryBuilders.boolQuery().should())

            // 搜索，获取结果
            Page<ChiWRecord> chiWRecords = chiWRecordRepository.search(queryBuilder.build());

            List<ChiWRecord> chiWRecordList = chiWRecords.getContent();

            //List<String> tagIdList = new ArrayList<>();

            HashMap<String, ChiWTagOutput> tagOutputHashMap = new HashMap<>();

            chiWRecordList.forEach(record->{
                //tagIdList.add(record.getTag_id());
                tagOutputHashMap.put(record.getReal_matrials(),new ChiWTagOutput(record.getTag_id(),record.getTag_value_level(),record.getTag_name()));
            });

//            //先找出memerid相关的tagid，在根据tagid和material_id去匹配
//            NativeSearchQueryBuilder queryBuilder2 = new NativeSearchQueryBuilder();
////
//            tagIdList.forEach(eachTag->{
//                queryBuilder2.withQuery(QueryBuilders.boolQuery().must(termQuery("tag_id.keyword", eachTag)));
//            });
//
//            TermsAggregationBuilder matrialsAgg = AggregationBuilders.terms("matrials_id").field("matrials_id.keyword").size(1000);
//            TermsAggregationBuilder tagLevelAgg = AggregationBuilders.terms("tag_value_level").field("tag_value_level.keyword").size(1000);
//
//
//            queryBuilder2.addAggregation(matrialsAgg.subAggregation(tagLevelAgg));

//            AggregatedPage<ChiWRecord> search = (AggregatedPage<ChiWRecord>)chiWRecordRepository.search(queryBuilder2.build());
//
//            StringTerms maAgg = (StringTerms) search.getAggregation("matrials_id");
//            List<StringTerms.Bucket> mabuckets = maAgg.getBuckets();

            List<Object> allMaMap = new ArrayList<>();

            materialCodes.forEach(eachMa->{

                Map<String,Object> oneMaMap = new HashMap<>();
                if(tagOutputHashMap.containsKey(eachMa)){
                    oneMaMap.put("tags",tagOutputHashMap.get(eachMa));
                    oneMaMap.put("materialCode",eachMa);
                    allMaMap.add(oneMaMap);
                }
            });

//            for (StringTerms.Bucket tbucket:mabuckets) {
//
//
//                String matrialValue = tbucket.getKeyAsString();
//
//                Aggregations aggregations = tbucket.getAggregations();
//
//                StringTerms tagValue = (StringTerms)aggregations.get("tag_value_level");
//
//                List<StringTerms.Bucket> buckets = tagValue.getBuckets();
//                List<ChiWTagOutput> ChiWTagList =  new ArrayList<>();
//
//                for (StringTerms.Bucket bucket:buckets) {
//                    ChiWTagOutput chiWValue = new ChiWTagOutput();
//                    chiWValue.setTag_value_level(bucket.getKeyAsString());
//                    chiWValue.setTag_name(tagOutputHashMap.get(matrialValue).getTag_name());
//                    chiWValue.setTag_id(tagOutputHashMap.get(matrialValue).getTag_id());
//                    ChiWTagList.add(chiWValue);
//                }
//
//                oneMaMap.put("tags",ChiWTagList);
//                oneMaMap.put("materialCode",matrialValue);
//                allMaMap.add(oneMaMap);
//
//            }

            serviceResult.put("data",allMaMap);

        }catch (Exception e){

            serviceResult.put("msg",e.getMessage());
        }
        return serviceResult;
    }
    /**
     * 查询会员标签
     */

    public Map<String,Object> findIdByTagName(){
        Map<String, Object> serviceResult = this.initServiceResult();
        try {

            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
//
//            TermQueryBuilder mpq2 = QueryBuilders
//                    .termQuery("tag_name", tagName);

            queryBuilder.withQuery(QueryBuilders.matchAllQuery());

            Pageable pageable = new Pageable() {
                @Override
                public int getPageNumber() {
                    return 0;
                }

                @Override
                public int getPageSize() {
                    return 200;
                }

                @Override
                public long getOffset() {
                    return 0;
                }

                @Override
                public Sort getSort() {
                    return null;
                }

                @Override
                public Pageable next() {
                    return null;
                }

                @Override
                public Pageable previousOrFirst() {
                    return null;
                }

                @Override
                public Pageable first() {
                    return null;
                }

                @Override
                public boolean hasPrevious() {
                    return false;
                }
            };
            queryBuilder.withPageable(pageable);
            // 搜索，获取结果
            Page<ChiWTag> tagValues = chiWTagRepository.search(queryBuilder.build());

            List<ChiWTag> tagList = tagValues.getContent();

            Map<String, String> collect = tagList.stream().collect(
                    Collectors.toMap(ChiWTag::getTag_id, ChiWTag::getTag_name,
                            (value1, value2 )->{
                                return value2;
                            }));

            List<ChiWNameOutput> ChiWNameList = new ArrayList<>();

            collect.forEach((k,v)->{
                ChiWNameOutput detail = new ChiWNameOutput();
                detail.setTag_id(k);
                detail.setTag_name(v);
                ChiWNameList.add(detail);
            });

            serviceResult.put("data",ChiWNameList);

        }catch (Exception e){

            serviceResult.put("msg",e.getMessage());
        }
        return serviceResult;
    }



//    /**
//     * 查询综合平台信息
//     * @param brandName
//     * @param jobId
//     * @param pageNumber
//     * @param pageSize
//     * @return
//     */
//    public Map<String, Object> findByBrandNameLike(String brandName, String jobId, Integer pageNumber, Integer pageSize){
//        Map<String, Object> serviceResult = this.initServiceResult();
//        try {
//            Pageable pageable = new Pageable() {
//                @Override
//                public int getPageNumber() {
//                    return pageNumber-1;
//                }
//
//                @Override
//                public int getPageSize() {
//                    return pageSize;
//                }
//
//                @Override
//                public long getOffset() {
//                    return (pageNumber-1)*pageSize;
//                }
//
//                @Override
//                public Sort getSort() {
//                    return null;
//                }
//
//                @Override
//                public Pageable next() {
//                    return null;
//                }
//
//                @Override
//                public Pageable previousOrFirst() {
//                    return null;
//                }
//
//                @Override
//                public Pageable first() {
//                    return null;
//                }
//
//                @Override
//                public boolean hasPrevious() {
//                    return false;
//                }
//            };
//            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
//            MatchPhraseQueryBuilder mpq2 = QueryBuilders
//                    .matchPhraseQuery("jobId",jobId);
//            if(StringUtils.isEmpty(brandName)){
//                queryBuilder.withQuery(QueryBuilders.boolQuery().must(mpq2));
//            }else{
//                MatchQueryBuilder mpq1 = QueryBuilders
//                        .matchQuery("brandName", brandName);
//                queryBuilder.withQuery(QueryBuilders.boolQuery().must(mpq1).must(mpq2));
//            }
//            queryBuilder.withPageable(pageable);
//            Page<ChiWRecord> search = chiWRecordRepository.search(queryBuilder.build());
//            serviceResult.put("data",search.getContent());
//            serviceResult.put("total",search.getTotalElements());
//            serviceResult.put("currentPage",pageNumber);
//            serviceResult.put("totalPages",search.getTotalPages());
//        }catch (Exception e){
//            serviceResult.put("msg",e.getMessage());
//        }
//        return serviceResult;
//    }


}
