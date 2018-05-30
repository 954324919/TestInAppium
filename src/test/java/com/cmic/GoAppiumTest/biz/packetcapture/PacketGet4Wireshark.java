package com.cmic.GoAppiumTest.biz.packetcapture;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cmic.GoAppiumTest.base.AdbManager;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.FileUtil;

@Tips(description = "用于网络抓包的业务", riskPoint = "存在兼容性风险")
public class PacketGet4Wireshark {

	@Tips(description = "获取网络包的示例代码")
	public void go2SnapPacket() throws InterruptedException {
		ExecutorService executor = Executors.newScheduledThreadPool(3);
		SnapPacketRunnable runable = new SnapPacketRunnable();
		executor.execute(runable);
		//
		Thread.sleep(20000);
		runable.cancel();
		executor.shutdown();
	}

	@Tips(description = "网络抓包的Runable")
	class SnapPacketRunnable implements Runnable {

		@Tips(description = "关闭网络抓包")
		public void cancel() {
			String tempResult = AdbManager.executeAdbCmdFilter("adb shell ps", "tcpdump");
			// 直接杀死该线程
			if (tempResult != null && !tempResult.isEmpty()) {
				AdbManager.excuteAdbShell("adb shell kill " + tempResult.trim().split(" +")[1]);
			}
			// 抓取到本地
			FileUtil.pullFile("/data/local/result.pcap", "/result/packet_result.pcap");
		}

		public void run() {
			// 开启网络抓包，不关注结果
			AdbManager.excuteAdbShell("adb shell tcpdump -i any -p -s 0 -w /data/local/result.pcap");
		}
	}
}
