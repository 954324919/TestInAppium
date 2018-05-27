package com.cmic.GoAppiumTest.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cmic.GoAppiumTest.base.AdbManager;
import com.cmic.GoAppiumTest.bean.DeviceEntity;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.YamlUtil;

@Tips(description = "用于多设备的Adb命令转换", riskPoint = "不稳固，迁移所有代码完成后删除")
public class MulAdbTransform {

	@SuppressWarnings("unchecked")
	@Tips(description = "获取保存的挂载设备信息")
	@Test
	public void fetchTheMountDeviceInfo() {
		ArrayList<DeviceEntity> savedMountDeviceList = YamlUtil.yaml2Bean(new File("res/ini/deviceInfo.yaml"),
				new ArrayList<DeviceEntity>().getClass());
		LogUtil.e("保存的设备列表共{}个", savedMountDeviceList.size());
	}

	@Tips(description = "获取挂载设备数目")
	public int getTheMountDeviceNum() {
		String result = AdbManager.executeAdbCmd("adb devices");
		if (result.contains("daemon started successfully")) {// 重新获取
			result = AdbManager.executeAdbCmd("adb devices");
		}
		return result.split("\n").length - 1;
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
				entity.setSerialNumber(temp[0]);// 设备序列号
				//
				int targetSdk = AdbManager.getTargetSdk(temp[0].trim());
				LogUtil.w("目标sdk为{}", targetSdk);
				entity.setTargetSdk(targetSdk);
				deviceList.add(entity);
			}
			YamlUtil.bean2Yaml("res/ini", "deviceInfo.yaml", deviceList);
		}
	}

	@Tips(description = "获取挂载设备列表")
	public List<DeviceEntity> fetchTheMountDeviceInfo2List() {
		List<DeviceEntity> deviceList = new ArrayList<>();
		if (getTheMountDeviceNum() > 0) {
			String result = AdbManager.executeAdbCmd("adb devices");
			String[] resultSplitWord = result.split("\n");
			//
			for (int i = 1; i < resultSplitWord.length; i++) {
				String[] temp = resultSplitWord[i].trim().split("\\t");
				LogUtil.i("设备序列号为{},设备名称为{}", temp[0], temp[1]);
				DeviceEntity entity = new DeviceEntity();
				entity.setSerialNumber(temp[0]);// 设备序列号
				//
				int targetSdk = AdbManager.getTargetSdk(temp[0].trim());
				LogUtil.w("目标sdk为{}", targetSdk);
				entity.setTargetSdk(targetSdk);
				deviceList.add(entity);
			}
			YamlUtil.bean2Yaml("res/ini", "deviceInfo.yaml", deviceList);
		} else {
			LogUtil.e("不存在挂载的设备");
		}
		return deviceList;
	}
}
