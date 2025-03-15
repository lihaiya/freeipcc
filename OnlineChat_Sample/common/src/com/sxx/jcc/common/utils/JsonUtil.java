package com.sxx.jcc.common.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;


public class JsonUtil {

	/** */
	/**
	 * 从一个JSON 对象字符格式中得到一个java对象
	 * 
	 * @param jsonString
	 * @param pojoClass
	 * @return
	 */
	public static Object getObject4JsonString(String jsonString, Class pojoCalss) {
		Object pojo;
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		pojo = JSONObject.toBean(jsonObject, pojoCalss);
		return pojo;
	}

	/** */
	/**
	 * 从json HASH表达式中获取一个map，改map支持嵌套功能
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Map getMap4Json(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map valueMap = new HashMap();

		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}

		return valueMap;
	}

	/** */
	/**
	 * 从json数组中得到相应java数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Object[] getObjectArray4Json(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return jsonArray.toArray();

	}

	/** */
	/**
	 * 从json对象集合表达式中得到一个java对象列表
	 * 
	 * @param jsonString
	 * @param pojoClass
	 * @return
	 */
	public static List getList4Json(String jsonString, Class pojoClass) {
		if (StringUtils.isNotBlank(jsonString)) {
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(jsonString);
            jsonString = m.replaceAll("");
        }
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		Object pojoValue;

		List list = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {

			jsonObject = jsonArray.getJSONObject(i);
			pojoValue = JSONObject.toBean(jsonObject, pojoClass);
			list.add(pojoValue);

		}
		return list;

	}
	
	/**
	 * 从json对象集合表达式中得到一个java对象列表(map)
	 * 
	 * @param jsonString
	 * @param pojoClass
	 * @return
	 */
	public static List getMapList4Json(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		List list = new ArrayList();
		Iterator keyIter;
		String key;
		Object value;
		Map valueMap;
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			keyIter = jsonObject.keys();
			valueMap = new HashMap();
			while (keyIter.hasNext()) {
				key = (String) keyIter.next();
				value = jsonObject.get(key);
				valueMap.put(key, value);
			}
			list.add(valueMap);
		}
		return list;
	}

	/** */
	/**
	 * 从json数组中解析出java字符串数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static String[] getStringArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		String[] stringArray = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			stringArray[i] = jsonArray.getString(i);

		}

		return stringArray;
	}

	/** */
	/**
	 * 从json数组中解析出javaLong型对象数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Long[] getLongArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Long[] longArray = new Long[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			longArray[i] = jsonArray.getLong(i);

		}
		return longArray;
	}

	/** */
	/**
	 * 从json数组中解析出java Integer型对象数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Integer[] getIntegerArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Integer[] integerArray = new Integer[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			integerArray[i] = jsonArray.getInt(i);

		}
		return integerArray;
	}

	/** */
	/**
	 * 从json数组中解析出java Date 型对象数组，使用本方法必须保证
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Date[] getDateArray4Json(String jsonString, String DataFormat) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Date[] dateArray = new Date[jsonArray.size()];
		String dateString;
		Date date;

		for (int i = 0; i < jsonArray.size(); i++) {
			dateString = jsonArray.getString(i);
			date = DateUtil.stringToDate(dateString, DataFormat);
			dateArray[i] = date;
		}
		return dateArray;
	}

	/** */
	/**
	 * 从json数组中解析出java Integer型对象数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Double[] getDoubleArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Double[] doubleArray = new Double[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			doubleArray[i] = jsonArray.getDouble(i);

		}
		return doubleArray;
	}

	/** */
	/**
	 * 将java对象转换成json字符串
	 * 
	 * @param javaObj
	 * @return
	 */
	public static String getJsonString4JavaPOJO(Object javaObj) {

		JSONObject json;
		json = JSONObject.fromObject(javaObj);
		return json.toString();

	}

	/** */
	/**
	 * 将java对象转换成json字符串,并设定日期格式
	 * 
	 * @param javaObj
	 * @param dataFormat
	 * @return
	 */
	public static String getJsonString4JavaPOJO(Object javaObj, String dataFormat) {
		JSONObject json;
		JsonConfig jsonConfig = configJson(dataFormat);
		json = JSONObject.fromObject(javaObj, jsonConfig);
		return json.toString();
	}

	/**
	 * 将java对象转换成json字符串，java.util.Date日期格式为：yyyy-MM-dd
	 * 
	 * @param object
	 * @return
	 */
	public static String objectToJson(Object object) {
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(null));
		JSONArray jsonarray = JSONArray.fromObject(object, config);
		return jsonarray.toString();
	}

	/**
	 * 将java对象转换成json字符串，其中format参数用来设置java.util.Date日期格式， 如：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param object
	 * @param format
	 * @return
	 */
	public static String objectToJson(Object object, String format) {
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(format));
		JSONArray jsonarray = JSONArray.fromObject(object, config);
		return jsonarray.toString();
	}

	/** */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
	}

	/** */
	/**
	 * JSON 时间解析器具
	 * 
	 * @param datePattern
	 * @return
	 */
	public static JsonConfig configJson(String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "" });
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(datePattern));

		return jsonConfig;
	}

	/** */
	/**
	 * 
	 * @param excludes
	 * @param datePattern
	 * @return
	 */
	public static JsonConfig configJson(String[] excludes, String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(datePattern));

		return jsonConfig;
	}

}