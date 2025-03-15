package com.sxx.jcc.core.intereptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sxx.jcc.common.utils.XKDataContext;


@SuppressWarnings("serial")
public class AuthorityInterceptor extends AbstractInterceptor {

	public String intercept(ActionInvocation invocation) throws Exception {
		Map session = ActionContext.getContext().getSession();
		Object staff = session.get(XKDataContext.STAFF_SESSION);
		if (staff == null) {
			HttpServletRequest request = ServletActionContext.getRequest();
			String ivrMark = request.getParameter("ivrMark");
			if ("0".equals(ivrMark)) {
				return invocation.invoke();
			}
			return "login";
		} else {
			return invocation.invoke();
		}
	}
}
