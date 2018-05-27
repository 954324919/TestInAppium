package com.cmic.GoAppiumTest.bean;

public class LineCharEntity {
	public String xAxis;
	public double yAxis;

	public String category;

	public LineCharEntity(String xAxis, double yAxis, String category) {
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.category = category;
	}

	public LineCharEntity(String xAxis, double yAxis) {
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		// 初始化为空字符串
		this.category = "";
	}
}
