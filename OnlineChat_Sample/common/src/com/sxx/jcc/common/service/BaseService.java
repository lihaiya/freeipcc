package com.sxx.jcc.common.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import com.sxx.jcc.common.utils.XKTools;
import com.sxx.jcc.core.dao.hibernate.HibernateDao;
import com.sxx.jcc.core.dao.hibernate.Page;

public abstract class BaseService implements IBaseService {
	
	protected Logger logger = Logger.getLogger(BaseService.class.getName());
	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private JdbcTemplate informixTemplate;
	@Autowired
	protected HibernateDao hibernateDao;
	public <T> T get(Class<T> entityClass, Serializable id) {
		try {
			return (T) hibernateDao.get(entityClass, id);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 获取全部对象.
	 */

	public <T> List<T> getAll(Class<T> entityClass) {
		return hibernateDao.getAll(entityClass);
	}

	/**
	 * 保存对象.
	 */
	public void save(Object o) {
		try{
			hibernateDao.save(o);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

	/**
	 * 保存对象.
	 */
	public void update(Object o) {
		try{
			hibernateDao.update(o);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

	/**
	 * 带返回值的保存 只能用于新增操作
	 */
	public Serializable add(Object o) {
		try{
			return hibernateDao.add(o);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return null;
	}

	/**
	 * 删除对象.
	 */
	public void delete(Object o) {
		hibernateDao.remove(o);
	}

	/**
	 * 根据ID删除对象.
	 */
	public <T> void removeById(Class<T> entityClass, Serializable id) {
		delete(get(entityClass, id));
	}

	/**
	 * 把指定的缓冲对象从hibernate缓存中进行清除
	 */
	public void evict(Object obj) {
		hibernateDao.getHibernateTemplate().evict(obj);
	}

	/**
	 * 把集合中指定的缓冲对象从hibernate缓存中进行清除
	 */
	public void evict(List list) {
		if (null != list && !list.isEmpty()) {
			for (Object obj : list) {
				evict(obj);
			}
		}
	}


	
	public List<Map<String,Object>>queryForMapList(String sql,final Object... objects){
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		try{
			logger.info(sql);
			List<Map<String,Object>> result = this.getJdbcTemplate().queryForList(sql, objects);
			for(Map<String,Object> map : result){
				resultList.add(XKTools.coverMap(map));
			}
		}catch(Exception e){
			logger.error(e);
		}
		return resultList;
	}
	
	public Map<String,Object> queryForMap(String sql,final Object... objects){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			logger.info(sql);
			Map<String,Object> map = this.getJdbcTemplate().queryForMap(sql, objects);
			resultMap = XKTools.coverMap(map);
		}catch(Exception e){
			logger.error(e);
		}
		return resultMap;
	}
	
	public <T> List<T>  queryForObjectList(String sql,Class<T> entityClass,final Object... objects){
		List<T> resultList = new ArrayList<T>();
		try{
			List<Map<String,Object>> result = this.getJdbcTemplate().queryForList(sql, objects);
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
	
	public <T> T queryForObject(String sql,Class<T> entityClass,final Object... objects) {
		Object obj=null;
		try{
			Map<String,Object> result = XKTools.coverMap(this.getJdbcTemplate().queryForMap(sql, objects));
			obj = entityClass.getDeclaredConstructor().newInstance();
			ConvertUtils.register(new DateConverter(null), java.util.Date.class);
			BeanUtils.populate(obj, result);
			return (T)obj;
		}catch(Exception e){
			logger.error(e);
		}
		return null;
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
		return this.getJdbcTemplate().queryForInt(countSqlString, objects);
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
	
	public String getWoPermissionSql(String field,Long staffId){
		if(1l == staffId.longValue()){
			return "";
		}
		
		StringBuffer psql = new StringBuffer(" and ( ");
		List<Map<String,Object>> dataList = getPermissionData(staffId,"ORDER_DATA","DEAL_DEPT");
		List<String> condList = new ArrayList<String>();
		if(CollectionUtils.isNotEmpty(dataList)){
			for(Map<String,Object> map : dataList){
				condList.add(field+" = "+map.get("code"));
			}
			psql.append(StringUtils.join(condList.iterator(), " or "));
		}else{
			psql.append(" 1= 2");
		}
		
		psql.append(" )");
		return psql.toString();
	}
	
	private List<Map<String,Object>> getPermissionData(Long staffId,String moudleCode,String authCode){
		StringBuffer sb = new StringBuffer("select m.module_name, m.module_code, a.auth_name, a.auth_code, o.object_id, o.code ");
		sb.append(" from sys_dr_rule r, sys_dr_module m, sys_dr_auth a, sys_dr_object o ");
		sb.append(" where m.module_id = a.module_id and a.auth_id = r.auth_id and r.rule_id = o.rule_id and r.entrity_type = 'S' ");
		sb.append(" and r.entrity_id=? and module_code=? and auth_code=?");
		List parm = new ArrayList();
		parm.add(staffId);
		parm.add(moudleCode);
		parm.add(authCode);
		return this.queryForMapList(sb.toString(), parm.toArray());
		
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getInformixTemplate() {
		return informixTemplate;
	}

	public void setInformixTemplate(JdbcTemplate informixTemplate) {
		this.informixTemplate = informixTemplate;
	}
	
}
