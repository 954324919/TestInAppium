package com.cmic.GoAppiumTest.biz.performanalyze;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jfree.data.category.DefaultCategoryDataset;

import com.cmic.GoAppiumTest.base.AdbManager;
import com.cmic.GoAppiumTest.bean.LineCharEntity;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.JFreeCharUtil;

/**
 * 依附于Appium测试用例的基类不太合理，可以放在Monkey测试中
 * 
 * @author cmic
 *
 */
@Tips(description = "分析电池状态")
public class BatteryAnalyze {

	@Tips(description = "示例启动代码，可放入全局基类")
	public void go2StartExecutor() throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(3);
		BatteryInfoRunnable b = new BatteryInfoRunnable();
		executor.execute(b);
		Thread.sleep(9000);
		b.cancel();
		executor.shutdown();
	}

	@Tips(description = "Line图表示例代码,无意义")
	public static DefaultCategoryDataset createDataset() {
		// 标注类别
		List<LineCharEntity> series = new ArrayList<LineCharEntity>();
		// 柱子名称：柱子所有的值集合
		SimpleDateFormat format = new SimpleDateFormat("hh时mm分ss秒");
		series.add(new LineCharEntity(format.format(new Date()), 1.1));
		series.add(new LineCharEntity(format.format(new Date()) + "1", 1.2));
		series.add(new LineCharEntity(format.format(new Date()) + "2", 1.5));
		series.add(new LineCharEntity(format.format(new Date()) + "3", 1.7));
		// 1：创建单类型数据集合
		DefaultCategoryDataset dataset = JFreeCharUtil.createDefaultDatasetInLineChatData(series);
		return dataset;
	}

	@Tips(description = "获取电池信息", riskPoint = "得到的数据在连接数据线充电时意义不是很大")
	public static Map<String, String> easyGetBatteryStatus() {
		Map<String, String> batteryInfoMap = new HashMap<String, String>();
		// 由于第一行是无意义代码 [Current Battery Service state:]
		String result = AdbManager.executeAdbCmd("adb shell dumpsys battery", 1);
		// System.err.println(result);
		String[] splitResult = result.split("\n");
		for (int i = 0; i < splitResult.length; i++) {
			String[] temp = splitResult[i].split(": ");
			batteryInfoMap.put(temp[0], temp[1]);
		}
		return batteryInfoMap;
	}

	public class BatteryInfoRunnable implements Runnable {

		private List<LineCharEntity> result = new ArrayList<LineCharEntity>();

		private SimpleDateFormat format = new SimpleDateFormat("hh时mm分ss秒");
		private boolean forceStop;

		public BatteryInfoRunnable() {
			this.forceStop = false;
		}

		public void run() {
			try {
				while (!forceStop) {
					Thread.sleep(2000);
					String batteryLevelVaule = easyGetBatteryStatus().getOrDefault("level", "0");
					String batteryTemperatureValue = easyGetBatteryStatus().getOrDefault("temperature", "0");
					String timeStamp = format.format(new Date());
					result.add(new LineCharEntity(timeStamp, Double.parseDouble(batteryLevelVaule), "Level"));
					result.add(new LineCharEntity(timeStamp, Double.parseDouble(batteryTemperatureValue) / 10,
							"Temperature"));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void cancel() {
			this.forceStop = true;
			new JFreeCharUtil.LineChartBuilder().setTitle("测试过程电池状态变化")//
					.setXAxisName("X轴:时间").setYAxisName("Y轴:加载时间/秒|温度x10/C")//
					.setImagePath("D:\\Allin\\a.png")//
					.setDataSource(createDatasetInner())//
					.outputImage();
			result.clear();
		}

		public DefaultCategoryDataset createDatasetInner() {
			// 1：创建单类型数据集合
			DefaultCategoryDataset dataset = JFreeCharUtil.createDefaultDatasetInLineChatData(result);
			return dataset;
		}
	}
}
