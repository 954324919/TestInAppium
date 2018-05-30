package com.cmic.GoAppiumTest.biz.performanalyze;

import java.io.BufferedReader;
import java.io.IOException;
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

@Tips(description = "获取Cpu和Mem的数据性能指标")
public class CpuAndMemAnalyze {
	// public static final String pkgName = "com.netease.cloudmusic";
	public static final String pkgName = "com.cmic.mmnes";

	@Tips(description = "示例代码")
	public void main(String[] args) throws NumberFormatException, IOException, InterruptedException {
		// double[] a = getTheValueOfCpuAndMem("com.cmic.mmnes");
		// System.err.println(a[0] + " 内存(RSS)" + a[1]);
		ExecutorService executor = Executors.newFixedThreadPool(3);
		CpuNMenInfoGet runable = new CpuNMenInfoGet(pkgName);
		executor.execute(runable);
		Thread.sleep(10000);
		runable.cancel();
		executor.shutdown();
	}

	@Tips(description = "获取Cpu和Mem在Top指令中的位置")
	public static int[] getTheIndexOfCpuAndMem() {
		String[] tempResult = AdbManager.executeAdbCmdFilter("adb shell top -n 1", "PID").trim().split(" +");
		int index = 0;
		int[] result = new int[2];
		for (String s : tempResult) {
			if (s.contains("CPU")) {
				result[0] = index;
			} else if (s.contains("RSS")) {
				result[1] = index;
			}
			index++;
		}
		return result;
	}

	@Tips(description = "用于获取Cpu和Men的变化趋势")
	class CpuNMenInfoGet implements Runnable {

		@Tips(description = "统计")
		private int[] indexResult;

		private List<LineCharEntity> fpsResultList = new ArrayList<LineCharEntity>();
		private boolean forceStop;
		private String pkg;// 包名

		public CpuNMenInfoGet(String pkg) {
			this.forceStop = false;
			this.pkg = pkg;
			indexResult = getTheIndexOfCpuAndMem();
		}

		public void run() {
			try {
				while (!forceStop) {
					Thread.sleep(2000);
					double[] temp = getTheValueOfCpuAndMem(pkg, indexResult);
					String tempDate = new SimpleDateFormat("mm分ss秒").format(new Date());
					fpsResultList.add(new LineCharEntity(tempDate, temp[0], "CPU占用率"));
					fpsResultList.add(new LineCharEntity(tempDate, temp[1] / 10240, "内存占用|x10M"));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void cancel() {
			this.forceStop = true;
			new JFreeCharUtil.LineChartBuilder().setTitle("测试" + pkg + "过程内存和Cpu的变化趋势")//
					.setXAxisName("X轴:时间").setYAxisName("")//
					.setImagePath("D:\\Allin\\cpuNmem.png")//
					.setDataSource(JFreeCharUtil.createDefaultDatasetInLineChatData(fpsResultList))//
					.outputImage();
			fpsResultList.clear();
		}
	}

	@Tips(description = "执行获取Cpu和Mem的数据指标")
	public static double[] getTheValueOfCpuAndMem(String pkn, int[] resultIndex) {
		double[] result = new double[2];
		Runtime rt = Runtime.getRuntime();
		Process pr = null;
		try {
			pr = rt.exec("adb shell top -n 1");
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = input.readLine()) != null) {
				if (line.contains(pkn)) {
					// 进行精度更高的匹配
					String[] temp = line.split(" +");
					if (temp[temp.length - 1].equals(pkn)) {// 仅匹配一个
						result[0] = Double
								.parseDouble(temp[resultIndex[0]].substring(0, temp[resultIndex[0]].indexOf("%")));
						result[1] = Double
								.parseDouble(temp[resultIndex[1]].substring(0, temp[resultIndex[1]].indexOf("K")));
						return result;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		} finally {
			if (pr != null)
				pr.destroy();
		}
		return result;
	}

}
