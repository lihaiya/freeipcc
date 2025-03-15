package com.sxx.jcc.core.view;


public interface ViewExporter {
    public View getView();
    
    public void setView(View view);
    
    public void export() throws Exception;
}
