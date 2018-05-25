package com.cmic.GoAppiumTest.other;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.testng.ITestListener;
import org.yaml.snakeyaml.Yaml;

import com.cmic.GoAppiumTest.base.AdbManager;
import com.cmic.GoAppiumTest.bean.DeviceEntity;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.YamlUtil;

public class Test4Test {

	static {// 最先执行
		PropertyConfigurator.configure("res/log4j/log4j.properties");
	}

	@Tips(description = "获取挂载设备数目")
	public int getTheMountDeviceNum() {
		String result = AdbManager.executeAdbCmd("adb devices");
		if (result.contains("daemon started successfully")) {// 重新获取
			result = AdbManager.executeAdbCmd("adb devices");
		}
		return result.split("\n").length - 1;
	}

	@Tips(description = "系统SDK版本", riskPoint = "空格导致的转换异常，用Trim")
	public int getTargetSdk(String serialNumber) {
		String tempResult = AdbManager.executeMulAdbCmd("adb {} shell getprop ro.build.version.sdk",
				"-s " + serialNumber);
		LogUtil.i(tempResult);
		try {
			return Integer.parseInt(tempResult.trim());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Tips(description = "系统SDK版本", riskPoint = "空格导致的转换异常，用Trim")
	public int getTargetSdk() {
		String tempResult = AdbManager.executeAdbCmd("adb shell getprop ro.build.version.sdk");
		LogUtil.i(tempResult);
		try {
			return Integer.parseInt(tempResult.trim());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Tips(description = "获取并转存设备信息")
	public void saveTheMountDeviceInfo() {
		if (getTheMountDeviceNum() > 0) {
			String result = AdbManager.executeAdbCmd("adb devices");
			String[] resultSplitWord = result.split("\n");
			//
			List<DeviceEntity> deviceList = new ArrayList<>();
			for (int i = 1; i < resultSplitWord.length; i++) {
				String[] temp = resultSplitWord[i].trim().split("\\t");
				LogUtil.i("设备序列号为{},设备名称为{}", temp[0], temp[1]);
				DeviceEntity entity = new DeviceEntity();
				entity.setDeviceName(temp[1]);
				entity.setSerialNumber(temp[0]);// 设备序列号
				//
				int targetSdk = getTargetSdk(temp[0].trim());
				LogUtil.w("目标sdk为{}", targetSdk);
				entity.setTargetSdk(targetSdk);
				deviceList.add(entity);
			}
			YamlUtil.bean2Yaml("res/ini", "deviceInfo.yaml", deviceList);
		}
	}

	@SuppressWarnings("unchecked")
	@Tips(description = "获取保存的挂载设备信息")
	@Test
	public void fetchTheMountDeviceInfo() {
		ArrayList<DeviceEntity> savedMountDeviceList = YamlUtil.yaml2Bean(new File("res/ini/deviceInfo.yaml"), new ArrayList<DeviceEntity>().getClass());
        LogUtil.e("保存的设备列表共{}个",savedMountDeviceList.size());
	}

	@Tips(description = "获取sdk")
	public void getTheTargetSdk() {
		String a = "adb {} shell getprop ro.build.version.sdk";
		LogUtil.w(a.replace("{}", "-s 123"));
	}
	
	@Test
	public void go2() {
	}
}
