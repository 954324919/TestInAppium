package com.cmic.GoAppiumTest.helper;

@Tips(description = "安卓系统Version和Code的对应枚举")
public enum AndroidVersion {

	_7_1_1("Nougat", 25), //
	_7_0("Nougat", 25), //
	_6_0("Marshmallow", 23), //
	_5_1("Lollipop", 21), //
	_5_0("Lollipop", 20), //
	_4_4("KitKat", 19);

	private int apiCode;
	private String versionName;

	private AndroidVersion(String versionName, int apiCode) {
		this.setApiCode(apiCode);
		this.setVersionName(versionName);
	}

	public int getApiCode() {
		return apiCode;
	}

	public void setApiCode(int apiCode) {
		this.apiCode = apiCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

}
