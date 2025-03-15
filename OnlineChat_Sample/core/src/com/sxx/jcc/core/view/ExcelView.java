package com.sxx.jcc.core.view;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.sxx.jcc.core.taglib.model.HtmlColumn;
import com.sxx.jcc.core.view.utils.ItemUtils;

public class ExcelView extends AbstractExportView{

	public ExcelView(String caption, List<?> items,List<HtmlColumn> columns) {
		super(caption, items,columns);
	}

	public Object render() {
		HSSFWorkbook workbook = new HSSFWorkbook();
        String caption = getCaption();
        if (StringUtils.isEmpty(caption)) {
            caption = "Export";
        }
        HSSFSheet sheet = workbook.createSheet(caption);

        // renderer header
        HSSFRow hssfRow = sheet.createRow(0);
        int columncount = 0;
        for (HtmlColumn col : getColumns()) {
            HSSFCell cell = hssfRow.createCell(columncount++);
            cell.setCellValue(new HSSFRichTextString(col.getTitle()));
        }

        // renderer body
        List<?> items = getItems();
        int rowcount = 1;
        for (Object item : items) {
            HSSFRow r = sheet.createRow(rowcount++);
            columncount = 0;
            for (HtmlColumn col : getColumns()) {
                HSSFCell cell = r.createCell(columncount++);
                Object value = ItemUtils.getItemValue(item, col.getColumn());
                if (value == null) {
                    value = "";
                }

                if (value instanceof Number) {
                    Double number = Double.valueOf(value.toString());
                    cell.setCellValue(number);
                } else {
                    cell.setCellValue(new HSSFRichTextString(value.toString()));
                }
            }
        }
        return workbook;
	}
}
