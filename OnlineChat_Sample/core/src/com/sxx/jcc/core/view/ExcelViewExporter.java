package com.sxx.jcc.core.view;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class ExcelViewExporter extends AbstractViewExporter {

	public ExcelViewExporter(View view, HttpServletResponse response,
			String fileName) {
		super(view, response, fileName);
	}

	@Override
	public String getContextType() {
		return "application/vnd.ms-excel;charset=UTF-8";
	}

	@Override
	public String getExtensionName() {
		return "xls";
	}

	public void export() throws Exception {
		HttpServletResponse response = super.getResponse();
		HSSFWorkbook workbook = (HSSFWorkbook) this.getView().render();
		responseHeaders(response);
		ServletOutputStream out=response.getOutputStream();
		workbook.write(out);
		out.flush();
		out.close();
	}
}
