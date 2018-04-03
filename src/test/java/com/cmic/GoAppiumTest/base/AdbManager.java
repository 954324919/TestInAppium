package com.cmic.GoAppiumTest.base;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
}
