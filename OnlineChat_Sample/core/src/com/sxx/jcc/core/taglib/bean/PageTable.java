package com.sxx.jcc.core.taglib.bean;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.UIBean;
import org.apache.struts2.views.annotations.StrutsTag;

import com.opensymphony.xwork2.util.ValueStack;

@StrutsTag(
	    name="pageList",
	    tldTagClass="com.sxx.jcc.core.taglib.ui.PageTableTag",
	    description="Render a pageList element",
	    allowDynamicAttributes=true)
public class PageTable extends UIBean{
	final public static String TEMPLATE = "pagetable";
	
	// core attributes
    private String caption;
    private String captionKey;
    private String action;

	// style attributes
    private String tableRenderer;
    private String width;
    private String border;
    private String columns;
    private String results;
    private String exports;
    private String selectType;
    private String deleteDisable;
    private String operatorDisable;
    
	public PageTable(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}

	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
	}

	public void evaluateExtraParams() {
        super.evaluateExtraParams();
        
        if (caption != null) {
            addParameter("caption", findString(caption));
        }
        
        if (captionKey != null) {
            addParameter("captionKey", findString(captionKey));
        }
        
        if (id != null) {
            addParameter("id", findString(id));
        }
        
        if (name != null) {
            addParameter("name", findString(name));
        }
        
        if (action != null) {
            addParameter("action", findString(action));
        }
        
        if (tableRenderer != null) {
            addParameter("tableRenderer", findString(tableRenderer));
        }
        
        if (width != null) {
            addParameter("width", findString(width));
        }
        
        if (border != null) {
            addParameter("border", findString(border));
        }
        
        if (columns != null) {
            addParameter("columns", findValue(columns));
            
        }
        
        if (results != null) {
            addParameter("page", findValue(results));
        }
        
        if (selectType != null) {
            addParameter("selectType", findString(selectType));
        }
        
        if (deleteDisable != null) {
            addParameter("deleteDisable", findString(deleteDisable));
        }
        
        if (operatorDisable != null) {
            addParameter("operatorDisable", findString(operatorDisable));
        }
        
        if(exports !=null){
        	String[] exportAry = exports.toLowerCase().split(",");
        	List<String> exportList=Arrays.asList(exportAry);
        	addParameter("exports", exportList);
        }
        
        addParameter("contextPath", request.getContextPath());
	}
	
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCaptionKey() {
        return captionKey;
    }

    public void setCaptionKey(String captionKey) {
        this.captionKey = captionKey;
    }

    public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
    
    public String getTableRenderer() {
        return tableRenderer;
    }
    
    public void setTableRenderer(String tableRenderer) {
        this.tableRenderer = tableRenderer;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }
    
	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public String getExports() {
		return exports;
	}

	public void setExports(String exports) {
		this.exports = exports;
	}

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	public String getDeleteDisable() {
		return deleteDisable;
	}

	public void setDeleteDisable(String deleteDisable) {
		this.deleteDisable = deleteDisable;
	}

	public String getOperatorDisable() {
		return operatorDisable;
	}

	public void setOperatorDisable(String operatorDisable) {
		this.operatorDisable = operatorDisable;
	}
	
}
