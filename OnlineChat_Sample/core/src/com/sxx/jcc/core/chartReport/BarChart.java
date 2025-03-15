package com.sxx.jcc.core.chartReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sxx.jcc.report.pojo.ChartInfo;
import com.sxx.jcc.report.pojo.bar.ChartBarSeries;
import com.sxx.jcc.report.pojo.bar.ChartValueInfo;

public class BarChart extends BaseChart{
	private List<String> labelList = new ArrayList<String>();
	private List<String> categoryList = new ArrayList<String>();//legend
	private List<String> categoriesKeyList = new ArrayList<String>();
	private Map<String,ChartValueInfo> relateMap = new HashMap<String,ChartValueInfo>();
	private List<List<ChartInfo>> dataList = new ArrayList<List<ChartInfo>>();
	
	public BarChart(Map<String,String> categoryMap, Map<String,List<ChartInfo>> dataMap){
		for(Map.Entry<String, List<ChartInfo>> entry : dataMap.entrySet()){
			 String mapKey = entry.getKey();
			 List<ChartInfo> mapValue = entry.getValue();
			 labelList.add(mapKey);
			 dataList.add(mapValue);
		}
		
		for(Map.Entry<String, String> entry : categoryMap.entrySet()){
		    String mapKey = entry.getKey();
		    String mapValue = entry.getValue();
		    categoriesKeyList.add(mapKey);
		    categoryList.add(mapValue);
		    relateMap.put(mapKey, new ChartValueInfo(mapValue));
		}
	}
	
	
	public Map<String,Object> createBarReport(){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("label", this.getLabelList());
		map.put("categories", categoryList);
		map.put("data", this.createDataSeries());
		return map;
	}
	
	private List<ChartBarSeries> createDataSeries(){
		this.genReportData();
		List<ChartBarSeries>  chatSeriesList = new ArrayList<ChartBarSeries>();
		ChartBarSeries serie = null; 
		for(String key : categoriesKeyList){
			ChartValueInfo valueInfo = this.getRelateMap().get(key);
			if(valueInfo!=null){
				serie= new ChartBarSeries(valueInfo.getName());
				serie.setData(valueInfo.getValueList());
				chatSeriesList.add(serie);
			}
		}
		return chatSeriesList;
	}
	
	
	private void genReportData(){
		int index=1;
		for(List<ChartInfo> chatInfoList : this.getDataList()){
			for(ChartInfo chart: chatInfoList){
				String name = chart.getName();
				int value = chart.getValue();
				// push data to map
				ChartValueInfo chartvalue = this.getRelateMap().get(name);
				if(chartvalue != null){
					chartvalue.getValueList().add(value);
					continue;
				}
				//set default value 0 for missing key
				setDefaultValue(index++);
			}
			
			
		}
	}

	/**
	 * 补填第index项数据
	 * @param index
	 */
	private void setDefaultValue(int index){
		for(String key : categoriesKeyList){
			if(this.getRelateMap().get(key).getValueList().size()<index){
				this.getRelateMap().get(key).getValueList().add(0);
			}
		}
	}
	
	public List<String> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<String> labelList) {
		this.labelList = labelList;
	}

	public Map<String, ChartValueInfo> getRelateMap() {
		return relateMap;
	}

	public void setRelateMap(Map<String, ChartValueInfo> relateMap) {
		this.relateMap = relateMap;
	}

	public List<List<ChartInfo>> getDataList() {
		return dataList;
	}

	public void setDataList(List<List<ChartInfo>> dataList) {
		this.dataList = dataList;
	}
	
}
