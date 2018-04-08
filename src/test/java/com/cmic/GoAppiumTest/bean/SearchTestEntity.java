package com.cmic.GoAppiumTest.bean;

public class SearchTestEntity {
	private String searchKeyword;
	private String expectResult1;
	private String expectResult2;

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public String getExpectResult1() {
		return expectResult1;
	}

	public void setExpectResult1(String expectResult1) {
		this.expectResult1 = expectResult1;
	}

	public String getExpectResult2() {
		return expectResult2;
	}

	public void setExpectResult2(String expectResult2) {
		this.expectResult2 = expectResult2;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return searchKeyword+"狗屎"+expectResult1+expectResult2;
	}
}
