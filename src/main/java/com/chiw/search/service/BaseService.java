package com.chiw.search.service;

import java.util.HashMap;
import java.util.Map;

/**
 * 所有Service类的基类
 * 背景：所有DAO层（除多线程包装的之外）会直接向service层返回数据和抛出异常，需要在service层进行捕获并封装成一个Map返回给controller层
 * Map结构：{"msg":"成功", "data":null}
 */
public class BaseService {

	protected Map<String, Object> initServiceResult() {
		Map<String, Object> serviceResult = new HashMap<String, Object>();
		serviceResult.put("msg", "成功");
		serviceResult.put("data", null);
		return serviceResult;
	}
}
