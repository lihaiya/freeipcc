package com.sxx.jcc.core.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;


public class Struts2Filter extends StrutsPrepareAndExecuteFilter { 
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		// 不过滤的url
		String url = request.getRequestURI();
		if (url.indexOf("imageUp.jsp") != -1 || url.indexOf("fileUp.jsp") != -1) {
			chain.doFilter(req, res);
		} else {
			// System.out.println("使用默认的过滤器");
			super.doFilter(req, res, chain);
		}
	}
}
