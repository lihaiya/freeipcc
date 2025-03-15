/**
 * 
 */
package com.sxx.jcc.common.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

@Repository("SpringBeanFactory")
public class SpringBeanFactory implements ApplicationContextAware {
	
	private static ApplicationContext context;

	/**
	 * @return the context
	 */
	public static ApplicationContext getContext() {
		return context;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext){
        context = applicationContext;
        XKDataContext.setApplicationContext(applicationContext);
    }
	
	public final static Object getBean(String beanName) {
		if(getContext()==null){
			throw new RuntimeException("the springApplicationContext is not exist ! ");
		}
		return getContext().getBean(beanName);
	}
	
	public final static <T> T getBean(Class<T> requiredType) {
		if(getContext()==null){
			throw new RuntimeException("the springApplicationContext is not exist ! ");
		}
		return getContext().getBean(requiredType);
	}
	
}
