package com.sxx.jcc.core.view;

import javax.servlet.http.HttpServletResponse;

import com.sxx.jcc.core.view.utils.ExportUtils;


public abstract class AbstractViewExporter implements ViewExporter {
	private View view;
	private HttpServletResponse response;
	private String fileName;

	public AbstractViewExporter(View view, HttpServletResponse response,
			String fileName) {
		this.view = view;
		this.response = response;
		this.fileName = fileName;
		if (fileName == null) {
			this.fileName = ExportUtils.exportFileName(view, getExtensionName());
		}else{
			this.fileName = fileName+"."+getExtensionName();
		}
	}

	public void responseHeaders(HttpServletResponse response) throws Exception {
		response.setContentType(getContextType());
		//String encoding = Charset.defaultCharset().name();
		//String fn = new String(fileName.getBytes(encoding), encoding);
		response.setHeader("Content-Disposition", "attachment;filename=\"" + new String(fileName.getBytes("gb2312"),"ISO8859-1") + "\"");
		response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
	}

	public abstract String getContextType();
	
	public abstract String getExtensionName();

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
