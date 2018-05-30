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

@Tips(description = "获取屏幕流畅度的性能脚本", riskPoint = "部分机型可能不支持")
public class FPSAnalyze {
	// 两个关键指标60帧每秒以及16.67毫秒,表征肉眼视觉对图像刷新的临界值，大于此人就会意识到刷新卡顿 1000/60≈16.67
	// !(Fps的相关介绍)[https://testerhome.com/topics/4436]

	@Tips(description = "示例代码")
	public void main(String[] args) throws NumberFormatException, IOException, InterruptedException {
		// System.err.println(getFocusWindow());
		// getFPSInGFXinfo(pkgName);
		// getFPSInSurfaceFlinger();
		AdbManager.excuteAdbShell("adb shell dumpsys SurfaceFlinger --latency-clear");
		ExecutorService executor = Executors.newFixedThreadPool(3);
		FPSInfoGet runable = new FPSInfoGet();
		executor.execute(runable);
		Thread.sleep(60000);
		runable.cancel();
		executor.shutdown();
	}

	@Tips(description = "获取windows")
	public static String getFocusWindow() {
		String result = AdbManager.executeAdbCmdFilter("adb shell dumpsys window", "mFocusedWindow").trim();
		int before = result.indexOf("{") + 1;
		int after = result.indexOf("}");
		if (after < 0) {// 没有存在{}字符，由于刷新过dao'zi
			return null;
		}
		String targetWindowDesc = result.substring(before, after);
		String[] temp = targetWindowDesc.split(" +");
		return temp[temp.length - 1];
	}

	@Tips(description = "更优的处理FPS方法")
	public static FpsEntity getFPSInSurfaceFlinger() {
		String curWindow = getFocusWindow();// 当前窗体名称，用于获取SurfaceFliger
		double[] result = new double[4];
		if (curWindow == null || curWindow.isEmpty()) {// 可能存在当前窗体不可获取的问题
			return null;
		}
		// 微秒和纳秒的转换局部变量
		double MillSecds = 1000000.0;
		double NANOMillSecds = 1000000000.0;
		// 进行操作的ADB命令
		java.text.DecimalFormat df1 = new java.text.DecimalFormat("#.0");
		java.text.DecimalFormat df2 = new java.text.DecimalFormat("#.00");
		java.text.DecimalFormat df3 = new java.text.DecimalFormat("#.000");
		String cmd = "adb shell dumpsys SurfaceFlinger --latency " + curWindow;
		Process p = null;
		BufferedReader br = null;
		try {
			p = Runtime.getRuntime().exec(cmd);
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String returnInfo = null;
			double b = 0;
			int frames = 0, jank = 0;
			double totalCountPeriod = 0;
			double r = 0;
			double beginRenderTime = 0;
			while ((returnInfo = br.readLine()) != null) {
				if (!"".equals(returnInfo) && returnInfo.length() > 0) {
					int frameSize = returnInfo.split("\\t").length;
					if (frameSize == 1) {// 第一行，提示当前设备的刷新频率，一般为60Hz->16.67ms
						double refreshPriod = Double.parseDouble(returnInfo) / MillSecds;
						b = 0;
						frames = 0;
						r = refreshPriod;
					} else {
						if (frameSize == 3) {
							String[] timeStamps = returnInfo.split("\\t");
							double t0 = Double.parseDouble(timeStamps[0]);// 开始
							double t1 = Double.parseDouble(timeStamps[1]);// 垂直同步
							double t2 = Double.parseDouble(timeStamps[2]);// 结束
							if (t1 > 0 && !"9223372036854775807".equals(timeStamps[1])) {
								if (b == 0) {
									b = t1;
									jank = 0;
								} else {
									double countPeriod = (t1 - b) / MillSecds; // 统计周期，大于500ms重新置为0
									if (countPeriod > 500) {// 两个帧之间因为页面跳帧持续 时间久，会导致fps计算不准确，剔除
										b = t1;
										frames = 0;
										totalCountPeriod = 0;
										jank = 0;
									} else {
										frames += 1;
										if (countPeriod > r) {
											totalCountPeriod += countPeriod;
											if ((t2 - t0) / MillSecds > r) {
												jank += 1;
											}
											b = t1;
										} else {
											totalCountPeriod += r;
											b = Double.parseDouble(df1.format(b + r * MillSecds));
										}
									}
								}
								if (frames == 0) {
									beginRenderTime = t1 / NANOMillSecds;
								}
							}
						}
					}
				}
			}
			if (frames > 0) {
				result[0] = jank * 100.0 / frames;// 掉帧率
				result[1] = frames * 1000 / totalCountPeriod;// FPS 帧/MS
				result[2] = totalCountPeriod / frames;// 每一帧耗时
				FpsEntity e = new FpsEntity(result[0], result[1], result[2]);
				return e;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (p != null)
					p.destroy();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static class FpsEntity {
		public double jankFrameRate;// 跳帧率
		public double fps;// 刷新频率
		public double perRenderTime;// 每帧渲染时间
		@Tips(description = "简单处理为加入时间，作为x轴的节点")
		private String recordTime;// 采样时间点

		public FpsEntity(double jankRate, double fps, double perRenderTime) {
			this.jankFrameRate = jankRate;
			this.fps = fps;
			this.perRenderTime = perRenderTime;
			SimpleDateFormat format = new SimpleDateFormat("mm分ss秒");
			// 放入采样点
			this.recordTime = format.format(new Date());
		}

		public String getRecordTime() {
			return recordTime;
		}
	}

	class FPSInfoGet implements Runnable {
		private List<LineCharEntity> fpsResultList = new ArrayList<LineCharEntity>();
		private boolean forceStop;

		public FPSInfoGet() {
			this.forceStop = false;
		}

		public void run() {
			try {
				while (!forceStop) {
					Thread.sleep(2000);
					FpsEntity temp;
					if ((temp = getFPSInSurfaceFlinger()) != null) {
						fpsResultList.add(new LineCharEntity(temp.getRecordTime(), temp.jankFrameRate, "掉帧率|百分比"));
						fpsResultList.add(new LineCharEntity(temp.getRecordTime() + "", temp.fps, "FPS|帧/微秒"));
						fpsResultList
								.add(new LineCharEntity(temp.getRecordTime() + "", temp.perRenderTime, "每一帧耗时|微秒"));
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void cancel() {
			this.forceStop = true;
			new JFreeCharUtil.LineChartBuilder().setTitle("测试过程FPS相关指标变化趋势")//
					.setXAxisName("X轴:时间").setYAxisName("")//
					.setImagePath("D:\\Allin\\fps.png")//
					.setDataSource(JFreeCharUtil.createDefaultDatasetInLineChatData(fpsResultList))//
					.outputImage();
			fpsResultList.clear();
		}
	}
}
