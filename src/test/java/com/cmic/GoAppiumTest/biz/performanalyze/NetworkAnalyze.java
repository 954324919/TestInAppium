package com.cmic.GoAppiumTest.biz.performanalyze;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cmic.GoAppiumTest.base.AdbManager;
import com.cmic.GoAppiumTest.bean.LineCharEntity;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.JFreeCharUtil;

@Tips(description = "获取网络流量信息")
public class NetworkAnalyze {

	@Tips(description = "示例代码", riskPoint = "部分线程执行结果可能会丢失")
	public static void main() {
		ExecutorService executor = Executors.newFixedThreadPool(3);
		FlowInfoRunnable fRunnable = new FlowInfoRunnable();
		executor.execute(fRunnable);
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		fRunnable.cancel();
		executor.shutdown();
	}

	@Tips(description = "获取上传或下载流量Kb/S")
	public static long[] getUpdateFlowInOneSecond(String pkg) {
		long[] result = new long[3];
		try {
			long[] flowBefore = getFlow(pkg, FLOWTYPE.BOTH);
			Thread.sleep(1000);
			long[] flowAfter = getFlow(pkg, FLOWTYPE.BOTH);
			result[1] = (flowAfter[1] - flowBefore[1]) / 1024;
			result[0] = (flowAfter[0] - flowBefore[0]) / 1024;
			result[2] = (flowAfter[2] - flowBefore[2]) / 1024;
			return result;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return result;
		}
	}

	@Tips(description = "获取流量,单位bit", riskPoint = "用了硬编码获取接收和发送的流量数据")
	public static long[] getFlow(String pkg, FLOWTYPE type) {
		// 获取Uid
		String uid = AdbManager.easyGetUidFormat(pkg);
		Runtime rt = Runtime.getRuntime();
		Process pr = null;
		long[] sum = new long[3];// index=0为接收收据,index=1为发送数据
		try {
			pr = rt.exec("adb shell cat /proc/net/xt_qtaguid/stats");
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = null;
			if (type == FLOWTYPE.ONLYGPRS) {//
				while ((line = input.readLine()) != null) {// 获取
					if (line.contains("rmnet") && line.contains(uid)) {// 获取Wifi
						String[] temp = line.split(" +");// 分词
						sum[0] += Double.parseDouble(temp[5]);// [5表征接收数据]
						sum[1] += Double.parseDouble(temp[7]);// [7表征发送数据]
					}
				}
			} else if (type == FLOWTYPE.ONLYWIFI) {
				while ((line = input.readLine()) != null) {
					if (line.contains("wlan") && line.contains(uid)) {// 获取移动流量
						String[] temp = line.split(" +");// 分词
						sum[0] += Double.parseDouble(temp[5]);
						sum[1] += Double.parseDouble(temp[7]);
					}
				}
			} else {
				while ((line = input.readLine()) != null) {
					if (line.contains(uid)) {
						String[] temp = line.split(" +");// 分词
						sum[0] += Double.parseDouble(temp[5]);
						sum[1] += Double.parseDouble(temp[7]);
					}
				}
			}
			sum[2] = sum[0] + sum[1];
			return sum;
		} catch (Exception e) {
			e.printStackTrace();
			return sum;
		} finally {
			if (pr != null)
				pr.destroy();
		}
	}

	enum FLOWTYPE {
		ONLYWIFI, // 仅Wifi
		ONLYGPRS, // 仅流量
		BOTH// 两种方式
	}

	@Tips(description = "用于执行线程操作收集流量信息")
	public static class FlowInfoRunnable implements Runnable {
		private List<LineCharEntity> result = new ArrayList<LineCharEntity>();

		private SimpleDateFormat format = new SimpleDateFormat("hh时mm分ss秒");
		private boolean forceStop;

		public FlowInfoRunnable() {
			this.forceStop = false;
		}

		public void run() {
			try {
				while (!forceStop) {
					long[] temp = getUpdateFlowInOneSecond("com.cmic.mmnes");
					String timeStamp = format.format(new Date());
					result.add(new LineCharEntity(timeStamp, temp[1], "上行流量速度"));
					result.add(new LineCharEntity(timeStamp, temp[0], "下行流量速度"));
					result.add(new LineCharEntity(timeStamp, temp[2], "所有流量速度"));
					Thread.sleep(3000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void cancel() {
			this.forceStop = true;
			new JFreeCharUtil.LineChartBuilder().setTitle("测试过程流量变化趋势")//
					.setXAxisName("X轴:时间").setYAxisName("Y轴:Kb/秒")//
					.setImagePath("D:\\Allin\\flow.png")//
					.setDataSource(JFreeCharUtil.createDefaultDatasetInLineChatData(result))//
					.outputImage();
			result.clear();
		}
	}

	// -------------------- 以下为暂存代码 ------------------------------

	@Tips(description = "每秒流量Kb", riskPoint = "使用该方法使用/proc/uid_stat/uid/tcp_rcv稳定性较差，部分机型不兼容")
	public static double getFlowByOldInOneSecond(String PackageName) {
		try {
			double flowBefore = getFlowByOldWay(PackageName);
			Thread.sleep(1000);
			double flowDiffInOneSecond = getFlowByOldWay(PackageName) - flowBefore;
			return flowDiffInOneSecond;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Tips(description = "获取上行流量/Send[单位:kb]", riskPoint = "使用该方法稳定性较差，部分机型不兼容")
	public static double getSendFlowByOldWay(String pkg) {
		String uid = AdbManager.easyGetUidFormat(pkg);
		String result = AdbManager.executeAdbCmd("adb shell cat /proc/uid_stat/" + uid + "/tcp_rcv");
		return Double.parseDouble(result);
	}

	@Tips(description = "获取上行流量/Send[单位:kb]", riskPoint = "使用该方法稳定性较差，部分机型不兼容")
	public static double getReceiveFlowByOldWay(String pkg) {
		String uid = AdbManager.easyGetUidFormat(pkg);
		String result = AdbManager.executeAdbCmd("adb shell cat /proc/uid_stat/" + uid + "/tcp_snd");
		return Double.parseDouble(result);
	}

	@Tips(description = "获取Flow", riskPoint = "兼容性较差，仅作为参考")
	public static double getFlowByOldWay(String pkg) {
		double FlowSize = 0;
		String Pid = AdbManager.easyGetPid(pkg);
		try {
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec("adb shell cat /proc/" + Pid + "/net/dev");
			try {
				if (proc.waitFor() != 0) {
					System.err.println("exit value = " + proc.exitValue());
				}
				BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				StringBuffer stringBuffer = new StringBuffer();
				String line = null;
				while ((line = in.readLine()) != null) {
					stringBuffer.append(line + " ");
				}
				String str1 = stringBuffer.toString();
				String str2 = str1.substring(str1.indexOf("wlan0:"), str1.indexOf("wlan0:") + 90);
				String str4 = str2.substring(7, 16);
				str4 = str4.trim();
				int Flow = Integer.parseInt(str4);
				FlowSize = Flow / 1024;
			} catch (InterruptedException e) {
				System.err.println(e);
			} finally {
				try {
					proc.destroy();
				} catch (Exception e2) {
				}
			}
		} catch (Exception StringIndexOutOfBoundsException) {

		}
		return FlowSize;
	}

}
