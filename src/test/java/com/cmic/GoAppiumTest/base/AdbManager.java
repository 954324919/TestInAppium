package com.cmic.GoAppiumTest.base;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.cmic.GoAppiumTest.helper.Tips;


public class AdbManager {
	// 执行adb命令
	@SuppressWarnings("unused")
	public static void excuteAdbShell(String s) {
		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec(s);
		} catch (Exception e) {
			System.out.println("执行命令:" + s + "出错");
		}
	}
	
	@Tips
	public static String executeAdbCmd(String cmd, int offsetLine) {
		Runtime rt = Runtime.getRuntime();
		Process pr = null;
		int index = 0;
		try {
			pr = rt.exec(cmd);
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
			Process pr = rt.exec(cmd); // cmd /c calc
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
			Process pr = rt.exec(cmd); // cmd /c calc
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
			pr = rt.exec(cmd);
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

	public static String executeAdbCmd(String cmd, String targetString, boolean onlyFirst) {
		try {
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec(cmd); // cmd /c calc
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
	
	@Tips(description = "更优的处理方法", riskPoint = "应用名不一定是最后一个")
	public static String executeAdbCmd(String cmd) {
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
		} catch (

		Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			if (pr != null)
				pr.destroy();
		}
	}
}
