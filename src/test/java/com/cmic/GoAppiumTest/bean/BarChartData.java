package com.cmic.GoAppiumTest.bean;

import com.cmic.GoAppiumTest.helper.Tips;

@Tips(description = "柱形图的数据实体")
public class BarChartData {
	public String dataCategory;
	public String dataCategorySet;
	public double dataValue;
	
	public BarChartData(String category, String categorySet, double value) {
		this.dataCategory = category;
		this.dataCategorySet = categorySet;
		this.dataValue = value;
	}
}
