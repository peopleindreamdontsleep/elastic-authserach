package com.chiw.search.controller;

import com.chiw.search.service.ChiWRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ChiWRecordController extends BaseController {

    @Autowired
    private ChiWRecordService chiWRecordService;

    @Autowired
    public void setZhxBjAllRankService(ChiWRecordService chiWRecordService) {
        this.chiWRecordService = chiWRecordService;
    }

    @CrossOrigin
    @RequestMapping("/member/tag/list")
    public Map<String, Object> findTagNameBySuperId(@RequestParam(value = "memberId") String memberId) {
        Map<String, Object> getTagNameBySuperIdMap = chiWRecordService.findTagNameBySuperId(memberId);
        return this.beanToJsonResult(getTagNameBySuperIdMap);
    }

    @CrossOrigin
    @RequestMapping("/tag/list")
    public Map<String, Object> findIdByTagName() {
        Map<String, Object> getSuperIdByTagNameMap = chiWRecordService.findIdByTagName();
        return this.beanToJsonResult(getSuperIdByTagNameMap);
    }

    @CrossOrigin
    @RequestMapping("/members/list")
    public Map<String, Object> findMemIdByTagId(@RequestParam(value = "pageNo") String pageNo,
                                                @RequestParam(value = "pageSize") String pageSize,
                                                @RequestParam(value = "tags") List<String> tags) {
        Map<String, Object> getMemIdByTagIdMap = chiWRecordService.findMemIdByTagId(pageNo,pageSize,tags);
        return this.beanToJsonResult(getMemIdByTagIdMap);
    }

    @CrossOrigin
    @RequestMapping("/category/tags")
    public Map<String, Object> findTagByCategory(@RequestParam(value = "memberId") String memberId,
                                                @RequestParam(value = "materialCodes") List<String> materialCodes) {
        Map<String, Object> getTagByCategoryMap = chiWRecordService.findTagByCategory(memberId,materialCodes);
        return this.beanToJsonResult(getTagByCategoryMap);
    }



}
