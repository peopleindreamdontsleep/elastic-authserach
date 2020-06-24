package com.chiw.search.controller;

import java.util.HashMap;
import java.util.Map;

public class BaseController {

    /**
     * 将数据封装成json格式作为RESTFUL接口返回给前端
     * @param serviceResult
     * @return Map<String, Object> restfulResult
     */
    public Map<String, Object> beanToJsonResult(Map<String, Object> serviceResult) {
        Map<String, Object> restfulResult = new HashMap<String, Object>();
        int status = 200;
        if (!serviceResult.get("msg").equals("成功")) {
            status = 500;
        }
        restfulResult.put("status", status);
        restfulResult.put("message", serviceResult.get("msg"));
        restfulResult.put("data", serviceResult.get("data"));
        if(serviceResult.containsKey("total")){
            //总共多少条
            restfulResult.put("total",serviceResult.get("total"));
            //当前页
            restfulResult.put("currentPage",serviceResult.get("currentPage"));
            //总页数
            restfulResult.put("totalPages",serviceResult.get("totalPages"));
        }

        return restfulResult;
    }

    public Map<String, Object> emptyJsonResult() {
        Map<String, Object> restfulResult = new HashMap<String, Object>();

        restfulResult.put("status", -1);
        restfulResult.put("message", "您搜索的日期可能超出现有数据啦");
        restfulResult.put("data", "数据空给你看");

        return restfulResult;
    }


}
