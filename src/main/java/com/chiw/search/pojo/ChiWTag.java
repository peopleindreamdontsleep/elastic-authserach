package com.chiw.search.pojo;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "tags",type = "doc")
public class ChiWTag {

    private Integer id;

    private String tag_id;

    private String tag_value;

    private String tag_name;

    private String tag_value_level;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag_value() {
        return tag_value;
    }

    public void setTag_value(String tag_value) {
        this.tag_value = tag_value;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getTag_value_level() {
        return tag_value_level;
    }

    public void setTag_value_level(String tag_value_level) {
        this.tag_value_level = tag_value_level;
    }
}
