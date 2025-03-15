package com.sxx.jcc.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import com.sxx.jcc.common.utils.XKTools;
import com.sxx.jcc.core.dao.hibernate.Page;

public abstract class BaseReportService {
	protected Logger logger = Logger.getLogger(BaseReportService.class.getName());
	
	@Resource
	private JdbcTemplate informixTemplate;
	
	public List<Map<String,Object>>queryForMapList(String sql,final Object... objects){
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		try{
			logger.info(sql);
			List<Map<String,Object>> result = this.getInformixTemplate().queryForList(sql, objects);
			for(Map<String,Object> map : result){
				resultList.add(XKTools.coverMap(map));
			}
		}catch(Exception e){
			logger.error(e);
		}
		return resultList;
	}
	
	public <T> List<T>  queryForObjectList(String sql,Class<T> entityClass,final Object... objects){
		List<T> resultList = new ArrayList<T>();
		try{
			List<Map<String,Object>> result = this.getInformixTemplate().queryForList(sql, objects);
			Object obj;
			for(Map<String,Object> map : result){
				obj = entityClass.getDeclaredConstructor().newInstance();
				ConvertUtils.register(new DateConverter(null), java.util.Date.class);
				BeanUtils.populate(obj, XKTools.coverMap(map));
				resultList.add((T)obj);
			}
		}catch(Exception e){
			logger.error(e);
		}
		return resultList;
	}
	
	public Page queryForMapPage(String sql,Page pageParm,final Object... objects){
		int totalCount = this.getTotlaCount(sql, objects);
		if(totalCount<=0){
			return new Page();
		}
		int startIndex =  Page.getStartOfPage(pageParm.getPage(), pageParm.getPagesize());
		sql = addOrderByStr(sql,pageParm);
		String exeSql = getPageSQL(sql, startIndex, pageParm.getPagesize());
		List<Map<String,Object>> resultMapList =this.queryForMapList(exeSql, objects);
		return new Page(pageParm.getPage(),pageParm.getPagesize(),totalCount,resultMapList);
	}

	public int getTotlaCount(String sql,final Object... objects){
		String countSqlString = "select count(*) from (" + removeOrders(sql) + ") a";
		return this.getInformixTemplate().queryForInt(countSqlString, objects);
	}
	
	public void excuteProcedure(String sql,final Object... objects){
		//this.getJdbcTemplate().ex
	}
	
	private String addOrderByStr(String sql, Page pageParm){
		String orderbyStr ="";
		if(StringUtils.isNotBlank(pageParm.getSortname())){
			orderbyStr = "select * from (" + sql + ") a order by " +pageParm.getSortname()+" "+pageParm.getSortorder();
			//orderbyStr = removeOrders(sql)+" order by " +pageParm.getSortname()+" "+pageParm.getSortorder();
		}else{
			orderbyStr = sql;
		}
		return orderbyStr;
	}
	
	private  String removeOrders(String sql) {
		Assert.hasText(sql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(sql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	private String getPageSQL(String sql, int startIndex, int pageSize) {
		String sql2 = "select * " + " from (select row_.*, rownum rownum_ " + " from (" + sql + ") row_" + " where rownum <= " + (startIndex + pageSize) + ")" + " where rownum_ >" + startIndex;
		return sql2;
	}
	
	public JdbcTemplate getInformixTemplate() {
		return informixTemplate;
	}

	public void setInformixTemplate(JdbcTemplate informixTemplate) {
		this.informixTemplate = informixTemplate;
	}
}
