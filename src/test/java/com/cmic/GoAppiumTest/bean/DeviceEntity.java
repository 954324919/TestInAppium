package com.cmic.GoAppiumTest.bean;

import com.cmic.GoAppiumTest.helper.Tips;

@Tips(description = "存放adb device得到的设备信息", riskPoint = "保证该类为POJO")
public class DeviceEntity {
	private String deviceName;
	private String serialNumber;
	private int targetSdk;

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

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
}
