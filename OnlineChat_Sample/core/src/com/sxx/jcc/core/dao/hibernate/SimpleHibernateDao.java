package com.sxx.jcc.core.dao.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public class SimpleHibernateDao extends HibernateDaoSupport {
	Logger logger = Logger.getLogger(SimpleHibernateDao.class.getName());

	@Autowired
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
}