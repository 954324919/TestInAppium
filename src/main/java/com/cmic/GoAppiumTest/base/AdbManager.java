package com.cmic.GoAppiumTest.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import com.cmic.GoAppiumTest.bean.DeviceEntity;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.LogUtil;

@Tips(description = "adb操作")
public class AdbManager {

	static int mountDeviceCount;
	// 获取UDID
	public static String udid = DriverManger.getUdid();

	static {
		String result = AdbManager.executeAdbCmdOnly4Init("adb devices");
		if (result.contains("daemon started successfully")) {// 重新获取
			result = AdbManager.executeAdbCmdOnly4Init("adb devices");
		}
		mountDeviceCount = result.split("\n").length - 1;
	}

	@Tips(description = "执行adb命令,不关注返回")
	public static void excuteAdbShell(String cmd) {
		Runtime runtime = Runtime.getRuntime();
		try {
			LogUtil.w("执行命令{}",mulAdbTransform(cmd, udid));//新增
			runtime.exec(mulAdbTransform(cmd, udid));
		} catch (IOException e) {
			LogUtil.e("执行命令:" + mulAdbTransform(cmd, udid) + "出错");
			e.printStackTrace();
		}
	}

	public static String executeAdbCmdFilter(String cmd, String filterWord) {
		Runtime rt = Runtime.getRuntime();
		Process pr = null;
		try {
			LogUtil.w("执行命令{}",mulAdbTransform(cmd, udid));//新增
			pr = rt.exec(mulAdbTransform(cmd, udid));
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = input.readLine()) != null) {
				if (line.contains(filterWord)) {
					// 进行精度更高的匹配
					sb.append(line);
					sb.append("\n");
				}
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			if (pr != null)
				pr.destroy();
		}
	}

	@Tips
	public static String executeAdbCmd(String cmd, int offsetLine) {
		Runtime rt = Runtime.getRuntime();
		Process pr = null;
		int index = 0;
		try {
			LogUtil.w("执行命令{}",mulAdbTransform(cmd, udid));//新增
			pr = rt.exec(mulAdbTransform(cmd, udid));
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = input.readLine()) != null) {
				if (index++ >= offsetLine) {
					sb.append(line.trim());
					sb.append("\n");
				}
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			if (pr != null)
				pr.destroy();
		}
	}

	public static String excuteAdbShellGetResult(String cmd) {
		try {
			Runtime rt = Runtime.getRuntime();
			LogUtil.w("执行命令{}",mulAdbTransform(cmd, udid));//新增
			Process pr = rt.exec(mulAdbTransform(cmd, udid)); // cmd /c calc
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream(), "GBK"));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = input.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String excuteAdbShellGetResultGrep(String cmd, String targetString) {
		try {
			Runtime rt = Runtime.getRuntime();
			LogUtil.w("执行命令{}",mulAdbTransform(cmd, udid));//新增
			Process pr = rt.exec(mulAdbTransform(cmd, udid)); // cmd /c calc
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = input.readLine()) != null) {
				if (!line.contains(targetString)) {
					continue;
				}
				sb.append(line);
				sb.append("\n");
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	@Tips(description = "更优的处理方法", riskPoint = "应用名不一定是最后一个")
	public static String executeAdbCmd(String cmd, String exactTargetString) {
		Runtime rt = Runtime.getRuntime();
		Process pr = null;
		try {
			LogUtil.w("执行命令{}",mulAdbTransform(cmd, udid));//新增
			pr = rt.exec(mulAdbTransform(cmd, udid));
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = input.readLine()) != null) {
				if (line.contains(exactTargetString)) {
					// 进行精度更高的匹配
					String[] result = line.split(" +");
					if (result[result.length - 1].equals(exactTargetString)) {
						sb.append(line);
						sb.append("\n");
					}
				}
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			if (pr != null)
				pr.destroy();
		}
	}

	/**
	 * 
	 * @param cmd
	 *            adb指令
	 * @param udid
	 *            表征唯一的挂载设备，也即是serialNumber
	 * @return
	 */
	@Tips(description = "多设备的adb命令转换")
	private static String mulAdbTransform(String cmd, String udid) {
		if (cmd.startsWith("adb shell ")) {
			StringBuilder sb = new StringBuilder(cmd);
			sb.insert(cmd.indexOf("adb") + 4, " ").insert(cmd.indexOf("adb") + 4, "-s " + udid);
			return sb.toString();
		} else {
			return cmd;
		}
	}

	public static String executeAdbCmd(String cmd, String targetString, boolean onlyFirst) {
		try {
			Runtime rt = Runtime.getRuntime();
			LogUtil.w("执行命令{}",mulAdbTransform(cmd, udid));//新增
			Process pr = rt.exec(mulAdbTransform(cmd, udid)); // cmd /c calc
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			if (onlyFirst) {// 仅适配第一个
				while ((line = input.readLine()) != null) {
					if (line.contains(targetString)) {
						sb.append(line);
						sb.append("\n");
						break;
					}
				}
			} else {
				while ((line = input.readLine()) != null) {
					if (line.contains(targetString)) {
						sb.append(line);
						sb.append("\n");
					}
				}
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	@Tips(description = "获取Pid", riskPoint = "可能不同的Adb版本会导致错误")
	public static String easyGetPid(String pkn) {
		return executeAdbCmd("adb shell ps", pkn).split(" +")[1];
	}

	@Tips(description = "获取Uid", riskPoint = "可能不同的Adb版本会导致错误")
	public static String easyGetUid(String pkn) {
		return executeAdbCmd("adb shell ps", pkn).split(" +")[0];
	}

	@Tips(description = "获取Uid")
	public static String easyGetUidFormat(String pkn) {
		return executeAdbCmd("adb shell ps", pkn).split(" +")[0].replace("u0_a", "10");
	}

	@Tips(description = "更优的处理方法")
	public static String executeAdbCmd(String cmd) {
		Runtime rt = Runtime.getRuntime();
		Process pr = null;
		try {
			LogUtil.w("执行命令{}",mulAdbTransform(cmd, udid));//新增
			pr = rt.exec(mulAdbTransform(cmd, udid));
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = input.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			if (pr != null)
				pr.destroy();
		}
	}

	/**
	 * 
	 * @param cmd
	 * @param serialNumber
	 *            序列号
	 * @return
	 */
	@Tips(description = "多终端的adb调用转换")
	public static String executeMulAdbCmd(String cmd, String serialNumber) {
		String tramsformCmd = cmd.replace("{}", serialNumber);
		// LogUtil.i(tramsformCmd);
		return executeAdbCmd(tramsformCmd);
	}

	@Tips(description = "系统SDK版本", riskPoint = "空格导致的转换异常，用Trim")
	public static int getTargetSdk(String serialNumber) {
		String tempResult = AdbManager.executeMulAdbCmd("adb {} shell getprop ro.build.version.sdk",
				"-s " + serialNumber);
		// LogUtil.d(tempResult);// 查看日志
		try {
			return Integer.parseInt(tempResult.trim());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Tips(description = "设备名称")
	public static String getDeviceName(String serialNumber) {
		String tempResult = AdbManager.executeMulAdbCmd("adb {} shell getprop ro.product.model", "-s " + serialNumber);
		// LogUtil.d(tempResult);
		try {
			return tempResult.trim();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Tips(description = "获取挂载设备列表")
	public static List<DeviceEntity> fetchTheMountDeviceInfo() {
		List<DeviceEntity> deviceList = new ArrayList<>();
		if (getTheMountDeviceNum() > 0) {
			String result = AdbManager.executeAdbCmd("adb devices");
			String[] resultSplitWord = result.split("\n");
			//
			for (int i = 1; i < resultSplitWord.length; i++) {
				String[] temp = resultSplitWord[i].trim().split("\\t");
				String deviceModelName = getDeviceName(temp[0]);
				// LogUtil.i("发现挂载设备序列号为{},设备型号为{}", temp[0], deviceModelName);
				DeviceEntity entity = new DeviceEntity();
				entity.setDeviceModelName(deviceModelName);// 设置设备型号
				entity.setSerialNumber(temp[0]);// 设备序列号
				//
				int targetSdk = AdbManager.getTargetSdk(temp[0].trim());
				// LogUtil.i("其安卓系统SDK为{}", targetSdk);
				entity.setTargetSdk(targetSdk);
				deviceList.add(entity);
			}
			// YamlUtil.bean2Yaml("res/ini", "deviceInfo.yaml", deviceList);
			// 每次取到最新
		} else {
			// LogUtil.e("不存在挂载的设备");
		}
		return deviceList;
	}

	@Tips(description = "获取挂载设备数目")
	public static int getTheMountDeviceNum() {
		return mountDeviceCount;
	}

	@Tips(description = "更优的处理方法")
	public static String executeAdbCmdOnly4Init(String cmd) {
		Runtime rt = Runtime.getRuntime();
		Process pr = null;
		try {
			pr = rt.exec(cmd);
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = input.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			if (pr != null)
				pr.destroy();
		}
	}
}
