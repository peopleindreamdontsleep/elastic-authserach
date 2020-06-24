package com.chiw.search.pojo;

import org.springframework.data.elasticsearch.annotations.Document;


/**
 * "super_id","tag_name","tag_value","start_date","end_date","recommend_start_date","recommend_end_date"
 * ,""recommend_seq","importance","active","tag_id"
 */

@Document(indexName = "member_material_tag_level_rel",type = "doc")
public class ChiWRecord {

    private Integer id;

    private String super_id;

    private String tag_name;

    private String tag_value;

    private String start_date;

    private String end_date;

    private String recommend_start_date;

    private String recommend_end_date;

    private String recommend_seq;

    private String importance;

    private String active;

    private String tag_id;

    private String tag_value_level;

    private String matrials_id;

    private String real_matrials;

    private String matrials_name;

    private String logic_code;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSuper_id() {
        return super_id;
    }

    public void setSuper_id(String super_id) {
        this.super_id = super_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getTag_value() {
        return tag_value;
    }

    public void setTag_value(String tag_value) {
        this.tag_value = tag_value;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getRecommend_start_date() {
        return recommend_start_date;
    }

    public void setRecommend_start_date(String recommend_start_date) {
        this.recommend_start_date = recommend_start_date;
    }

    public String getReal_matrials() {
        return real_matrials;
    }

    public void setReal_matrials(String real_matrials) {
        this.real_matrials = real_matrials;
    }

    public String getRecommend_end_date() {
        return recommend_end_date;
    }

    public void setRecommend_end_date(String recommend_end_date) {
        this.recommend_end_date = recommend_end_date;
    }

    public String getRecommend_seq() {
        return recommend_seq;
    }

    public void setRecommend_seq(String recommend_seq) {
        this.recommend_seq = recommend_seq;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag_value_level() {
        return tag_value_level;
    }

    public void setTag_value_level(String tag_value_level) {
        this.tag_value_level = tag_value_level;
    }

    public String getMatrials_id() {
        return matrials_id;
    }

    public void setMatrials_id(String matrials_id) {
        this.matrials_id = matrials_id;
    }

    public String getMatrials_name() {
        return matrials_name;
    }

    public void setMatrials_name(String matrials_name) {
        this.matrials_name = matrials_name;
    }

    public String getLogic_code() {
        return logic_code;
    }

    public void setLogic_code(String logic_code) {
        this.logic_code = logic_code;
    }
}
