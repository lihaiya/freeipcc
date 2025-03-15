package com.sxx.jcc.core.dao.hibernate;


import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.sxx.jcc.common.utils.ReflectionUtils;


public class PropertyFilter {

	/**
	 * 多个属性间OR关系的分隔符.
	 */
	public static final String OR_SEPARATOR = "_OR_";

	/**
	 * 属性比较类型.
	 */
	public enum MatchType {
		EQ, LIKE, LT, GT, LE, GE;
	}

	public enum OperatorType {
		OR;
	}
	
	public enum OperatorTag {
		BEGIN,END;
	}
	
	/**
	 * 属性数据类型.
	 */
	public enum PropertyType {
		S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class),F(Float.class);

		private Class<?> clazz;

		PropertyType(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Class<?> getValue() {
			return clazz;
		}
	}

	private String[] propertyNames = null;
	private Class<?> propertyType = null;
	private Object propertyValue = null;
	private MatchType matchType = null;

	public PropertyFilter() {
	}

	/**
	 * @param filterName 比较属性字符串,含待比较的比较类型、属性值类型及属性列表. 
	 *                   eg. LIKES_NAME_OR_LOGIN_NAME
	 * @param value 待比较的值.
	 */
	public PropertyFilter(final String filterName, final String value) {

		String matchTypeStr = StringUtils.substringBefore(filterName, "_");
		String matchTypeCode = StringUtils.substring(matchTypeStr, 0, matchTypeStr.length() - 1);
		String propertyTypeCode = StringUtils.substring(matchTypeStr, matchTypeStr.length() - 1, matchTypeStr.length());
		try {
			matchType = Enum.valueOf(MatchType.class, matchTypeCode);
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性比较类型.", e);
		}

		try {
			propertyType = Enum.valueOf(PropertyType.class, propertyTypeCode).getValue();
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性值类型.", e);
		}

		String propertyNameStr = StringUtils.substringAfter(filterName, "_");
		if(propertyNameStr.indexOf(PropertyFilter.OR_SEPARATOR) != -1) {
			propertyNames = StringUtils.split(propertyNameStr, PropertyFilter.OR_SEPARATOR);
		} else {
			propertyNames = new String[] {propertyNameStr};
		}

		Assert.isTrue(propertyNames.length > 0, "filter名称" + filterName + "没有按规则编写,无法得到属性名称.");
		//按entity property中的类型将字符串转化为实际类型.
		this.propertyValue = ReflectionUtils.convertStringToObject(value, propertyType);
	}

	/**
	 * 是否比较多个属性.
	 */
	public boolean isMultiProperty() {
		return (propertyNames.length > 1);
	}

	/**
	 * 获取比较属性名称列表.
	 */
	public String[] getPropertyNames() {
		return propertyNames;
	}

	/**
	 * 获取唯一的比较属性名称.
	 */
	public String getPropertyName() {
		if (propertyNames.length > 1) {
			throw new IllegalArgumentException("There are not only one property");
		}
		return propertyNames[0];
	}

	/**
	 * 获取比较值.
	 */
	public Object getPropertyValue() {
		return propertyValue;
	}

	/**
	 * 获取比较值的类型.
	 */
	public Class<?> getPropertyType() {
		return propertyType;
	}

	/**
	 * 获取比较方式类型.
	 */
	public MatchType getMatchType() {
		return matchType;
	}
}
