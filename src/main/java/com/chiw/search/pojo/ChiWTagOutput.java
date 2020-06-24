package com.chiw.search.pojo;

public class ChiWTagOutput {


    private String tag_id;

    private String tag_value_level;

    private String tag_name;

    public ChiWTagOutput() {
    }

    public ChiWTagOutput(String tag_id, String tag_value_level, String tag_name) {
        this.tag_id = tag_id;
        this.tag_value_level = tag_value_level;
        this.tag_name = tag_name;
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

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }
}
