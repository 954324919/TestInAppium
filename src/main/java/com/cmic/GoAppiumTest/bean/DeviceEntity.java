package com.cmic.GoAppiumTest.bean;

import com.cmic.GoAppiumTest.helper.Tips;

@Tips(description = "存放adb device得到的设备信息", riskPoint = "保证该类为POJO")
public class DeviceEntity {
	private String deviceBrand;// 设备品牌
	private String deviceModelName;// 设备型号
	private String serialNumber;
	private int targetSdk;

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public int getTargetSdk() {
		return targetSdk;
	}

	public void setTargetSdk(int targetSdk) {
		this.targetSdk = targetSdk;
	}

	public String getDeviceModelName() {
		return deviceModelName;
	}

	public void setDeviceModelName(String deviceModelName) {
		this.deviceModelName = deviceModelName;
	}

	public String getDeviceBrand() {
		return deviceBrand;
	}

	public void setDeviceBrand(String deviceBrand) {
		this.deviceBrand = deviceBrand;
	}
}
