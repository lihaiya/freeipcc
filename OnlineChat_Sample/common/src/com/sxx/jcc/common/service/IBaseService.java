package com.sxx.jcc.common.service;

import java.io.Serializable;
import java.util.List;

public interface IBaseService {
	public <T> T get(Class<T> entityClass, Serializable id);

	public <T> List<T> getAll(Class<T> entityClass);

	public void save(Object o);

	public void update(Object o);

	public Serializable add(Object o);

	public void delete(Object o);

	public <T> void removeById(Class<T> entityClass, Serializable id);
}
