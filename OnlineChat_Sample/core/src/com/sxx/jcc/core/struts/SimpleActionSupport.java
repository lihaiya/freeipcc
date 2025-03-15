package com.sxx.jcc.core.struts;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.sxx.jcc.core.utils.Constant;

@SuppressWarnings( { "serial" })
public class SimpleActionSupport extends ActionSupport implements SessionAware{
	
	protected Logger logger = Logger.getLogger(SimpleActionSupport.class.getName());
	private Map<String, Object> session;
	private String propMsg;
	public String getPropMsg() {
		return propMsg;
	}

	public void setPropMsg(String propMsg) {
		this.propMsg = propMsg;
	}

	protected String render(String text, String contentType) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	

	protected String renderText(String text) {
		return render(text, "text/plain;charset=UTF-8");
	}


	protected String renderHtml(String html) {
		return render(html, "text/html;charset=UTF-8");
	}

	
	protected String renderXML(String xml) {
		return render(xml, "text/xml;charset=UTF-8");
	}

	
	/**
	 * 
	 * @return httpServletRequest
	 */
	protected HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
	}
	
	/**
	 * 
	 * @return response
	 */
	protected HttpServletResponse  getRespose(){
		return ServletActionContext.getResponse();
	}

	/**
	 * Gets the Map of HttpSession values when in a servlet environment or a
	 * generic session map otherwise.
	 * 
	 * @return the Map of HttpSession values when in a servlet environment or a
	 *         generic session map otherwise.
	 */
	public Map<String, Object> getSessionMap() {
		return this.session;
	}

	public void setSession(Map<String, Object> session) {
		this.session=session;
	}
	
	public void doPutSessionObject(String key, Object value) {
		this.getSessionMap().put(key, value);
	}
	
	public void putPopMsg(String msg) {
		if(StringUtils.isNotEmpty(msg)){
			doPutSessionObject(Constant.PROP_MSG_KEY,msg);
		}
	}
	
	public void removeSessionObject(Object key) {
		this.getSessionMap().remove(key);
	}
	
	public void removeSessionPopMsg(String key){
		Object sessionObj = this.getSessionMap().get(key);
		if(sessionObj!=null){
			this.setPropMsg((String)this.getSessionMap().get(key));
			this.getSessionMap().remove(key);
		}
	}
	
	public void saveSuccessMessage(String message) {
		if(this.getRequest().getAttribute("_EVENT_MESSAGE_") != null) {
			message = this.getRequest().getAttribute("_EVENT_MESSAGE_").toString() + "<br/>" + message;
		}
		this.getRequest().setAttribute("_EVENT_MESSAGE_", message);
	}
	
	public void saveErrorMessage(String message) {
		if(this.getRequest().getAttribute("_ERROR_MESSAGE_") != null) {
			message = this.getRequest().getAttribute("_ERROR_MESSAGE_").toString() + "<br/>" + message;
		}
		this.getRequest().setAttribute("_ERROR_MESSAGE_", message);
	}

}
