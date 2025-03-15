package com.sxx.jcc.common.action;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.sxx.jcc.common.utils.Escape;
import com.sxx.jcc.core.dao.hibernate.Page;

public class BaseAction extends ActionSupport implements SessionAware,
		ServletRequestAware, ServletResponseAware {
	
	private static final long serialVersionUID = BaseAction.class.hashCode();
	protected Map session = null;
	protected HttpServletRequest request = null;
	protected HttpServletResponse response = null;
	private Page page;
	protected Message message;
	protected String res;
	protected String basePath;
	
	public String getRes() {
		return getRequest().getContextPath() + "/resource";
	}

	public void setRes(String res) {
		this.res = res;
	}

	public String getBasePath() {
		return getRequest().getContextPath();
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}



	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	/** IoC注入方式 */
	public void setSession(Map session) {
		this.session = session;
	}

	/** IoC注入方式 */
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	/** IoC注入方式 */
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	/** 设置上次请求动作防止刷新 */
	protected void setPreAction(String actionName) {
		session.put("PreAction", actionName);
	}

	/** 取得上次请求动作防止刷新 */
	protected String getPreAction() {
		return (String) session.get("PreAction");
	}

	/** 非IoC注入方式 */
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/** 非IoC注入方式 */
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}


	/** 从Cookie中获取每页显示数量 */
	public int pagesizecol() {
		int j = 0;
		String pagesizecol = null;
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			if ("pagesizecol".equals(cookies[i].getName())) {
				pagesizecol = Escape.unescape(cookies[i].getValue());
			}
		}
		if (StringUtils.isNotBlank(pagesizecol)) {
			j = Integer.parseInt(pagesizecol);
		} else {
			//j = Constants.DEFAULT_PAGE_SIZE;
		}
		return j;
	}

	/** ajax配置response参数 */
	protected void setResponseParas() {
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache, no-store");
		response.setHeader("Expires", "Sun, 19 Nov 1978 05:00:00 GMT");
		response.setHeader("Pragma", "no-cache");
	}

	protected void setResponseJsonParas() {
		response.setContentType("text/script;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache, no-store");
		response.setHeader("Expires", "Sun, 19 Nov 1978 05:00:00 GMT");
		response.setHeader("Pragma", "no-cache");
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	public void setInfoMsg(String text){
		Message msg = new Message();
		msg.setMsgType("I");
		msg.setMsgText(text);
		this.message = msg;
	}
	public void setWarningMsg(String text){
		Message msg = new Message();
		msg.setMsgType("W");
		msg.setMsgText(text);
		this.message = msg;
	}
	public void setNewOrderInfoMsg(String text){
		Message msg = new Message();
		msg.setMsgType("N");
		msg.setMsgText(text);
		this.message = msg;
	}
}
