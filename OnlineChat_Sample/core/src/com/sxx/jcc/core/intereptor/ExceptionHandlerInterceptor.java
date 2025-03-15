package com.sxx.jcc.core.intereptor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.sxx.jcc.core.exception.BaseException;
import com.sxx.jcc.core.struts.CRUDActionSupport;
import com.sxx.jcc.core.struts.SimpleActionSupport;
import com.sxx.jcc.core.utils.Constant;


public class ExceptionHandlerInterceptor implements Interceptor {

	private static final long serialVersionUID = ExceptionHandlerInterceptor.class.hashCode();
	protected final Log logger = LogFactory.getLog(getClass());
	public static final String exceptionAttribute = "exception";

	public static final String exceptionTraceAttribute = "exceptionTrace";

	private List<ExceptionDealBean> exceptionDealList;

	/**
	 * Invoke action and if an exception occurs, route it to the mapped result.
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		String result = null;
		try {
			result = invocation.invoke();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			if (this.exceptionDealList != null) {
				for (int i = 0; i < exceptionDealList.size(); i++) {
					ExceptionDealBean exceptionDealBean = (ExceptionDealBean) exceptionDealList.get(i);
					if (exceptionDealBean.exceptionClass.isInstance(ex)) {
						result = exceptionDealBean.exceptionDealResult;
						if(BaseException.class.isInstance(ex)){
							SimpleActionSupport baseAction = (SimpleActionSupport) invocation.getAction();
							baseAction.getSessionMap().put(Constant.APP_EXCEPTION_MSG, ex.getMessage());
						}
						ServletActionContext.getRequest().setAttribute(
								exceptionAttribute, ex);
						ServletActionContext.getRequest().setAttribute(
								exceptionTraceAttribute, getStackTrace(ex));
						break;
					}
				}
			}
		}
		return result;
	}

	public void destroy() {
	}

	public void init() {
		exceptionDealList = new ArrayList<ExceptionDealBean>();
		exceptionDealList.add(new ExceptionDealBean(
				AuthorizationInterceptor.class, "pageError"));
		exceptionDealList.add(new ExceptionDealBean(BaseException.class,CRUDActionSupport.APPERROR));
		exceptionDealList.add(new ExceptionDealBean(
				org.springframework.dao.DataAccessException.class,
				"dataAccessFailure"));
		exceptionDealList.add(new ExceptionDealBean(
				java.lang.RuntimeException.class, "runtimeError"));

		exceptionDealList.add(new ExceptionDealBean(java.lang.Exception.class,
				"unkownError"));
	}

	public static String getStackTrace(Exception ex) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ex.getStackTrace().length; i++) {
			sb.append((StackTraceElement) ex.getStackTrace()[i]);
		}
		return String.valueOf(sb);
	}

	/**
	 * 
	 * @author Andy_shao
	 * 
	 */
	class ExceptionDealBean {
		public ExceptionDealBean(Class<?> exceptionClass,
				String exceptionDealResult) {
			this.exceptionClass = exceptionClass;
			this.exceptionDealResult = exceptionDealResult;
		}

		public Class<?> exceptionClass;

		public String exceptionDealResult;
	}
}
