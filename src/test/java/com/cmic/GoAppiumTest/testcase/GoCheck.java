package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jfree.chart.entity.JFreeChartEntity;
import org.jfree.data.category.DefaultCategoryDataset;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.FailSnapshotListener;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.AppUtil;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.DeviceUtil;
import com.cmic.GoAppiumTest.util.ElementUtil;
import com.cmic.GoAppiumTest.util.JFreeCharUtil;
import com.cmic.GoAppiumTest.util.JFreeCharUtil.FreeCharDataEntity;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.NetworkUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.RandomUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

/**
 * @描述 用来暂时存放一些有意义的测试用例
 * @author kiwi
 *
 */
@Listeners(FailSnapshotListener.class)
public class GoCheck {

	private AndroidDriver<AndroidElement> driver = DriverManger.getDriver();
	private AndroidDriver<AndroidElement> mDriver = DriverManger.getDriver();

	public static void main() {
		new JFreeCharUtil.BarChartBuilder()//
				.setTitle("几种不同情况下的页面加载速度")//
				.setXAxisName("测试事件").setYAxisName("加载时间/秒")//
				.setImagePath("D:\\Allin\\测试结果.jpg")//
				.setDataSource(createDataset())//
				.outputImage();
	}

	public static DefaultCategoryDataset createDataset() {
		// 标注类别
		String[] categories = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
		List<FreeCharDataEntity> dataList = new ArrayList<JFreeCharUtil.FreeCharDataEntity>();
		// 柱子名称：柱子所有的值集合
		dataList.add(new FreeCharDataEntity("Tokyo",
				new Double[] { 49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4 }));
		dataList.add(new FreeCharDataEntity("New York",
				new Double[] { 83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3 }));
		dataList.add(new FreeCharDataEntity("London",
				new Double[] { 48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2 }));
		dataList.add(new FreeCharDataEntity("Berlin",
				new Double[] { 42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1 }));
		DefaultCategoryDataset dataset = JFreeCharUtil.createDefaultCategoryDataset(dataList, categories);
		return dataset;
	}

	@FindBy
	private AndroidElement e;

	@Tips(description = "意义:卸载应用")
	@AfterClass
	public void afterClass() {
		AppUtil.unInstall(App.PACKAGE_NAME);

		// mDriver = DriverManger.getDriver();
		// String packageName = App.PACKAGE_NAME;
		// if (AppUtil.isInstallWithoutDriver(packageName)) {
		// AppUtil.unInstall(packageName);
		// }
	}

	/**
	 * 显示等待，等待Id对应的控件出现time秒，一出现马上返回，time秒不出现也返回
	 */
	// public AndroidElement waitAuto(final By by, int time) {
	// try {
	// return ((Object) new ExpectedCondition(driver, time))
	// .until(new ExpectedCondition<AndroidElement>() {
	// @Override
	// public AndroidElement apply(WebDriver arg0) {
	// // TODO Auto-generated method stub
	// return (AndroidElement) arg0.findElement(by);
	// }
	// });
	// } catch (TimeoutException e) {
	// return null;
	// }
	// }

	@Test
	public void test() throws InterruptedException {
		AndroidElement e1 = driver.findElementByXPath("//android.widget.Button[@content-desc=\"Add Contact\"]");
		e1.click();
		Thread.sleep(3000);
		driver.pressKeyCode(AndroidKeyCode.BACK);
	}

	@Test
	public void test1() throws InterruptedException {
		AndroidElement el = driver.findElementById("com.example.android.contactmanager:id/addContactButton");
		if (el == null) {
			assertEquals(true, false);
			return;
		}
		el.click();
		Thread.sleep(3000);
		driver.pressKeyCode(AndroidKeyCode.BACK);
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void goAnyWhere() {
		ContextUtil.goTargetActivity("com.cmic.mmnes", ".SearchActivity");
		WaitUtil.implicitlyWait(2);
		PageRouteUtil.pressBack();
		ContextUtil.goTargetActivity("com.example.android.contactmanager", ".ContactAdder");
		WaitUtil.implicitlyWait(2);
		PageRouteUtil.pressBack();
	}

	@Test(dependsOnMethods = { "initCheck" }, enabled = false)
	public void futureHope() {// 检查重定向通过
		PageRedirect.redirect2RequestiteActivity();
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void testNetworkStatus() {
		System.err.println(NetworkUtil.getNetworkState());
		System.err.println(DeviceUtil.getDeviceTime());
	}

	@Test(dependsOnMethods = { "initCheck" })
	@Tips(description = "点击随机的一个热词Item", riskPoint = "耦合度过高，与下列clickTheClearSearchRly风险点太高")
	public void randomCheckOne() throws InterruptedException {
		LogUtil.printCurrentMethodName();
		Random random = new Random();
		// TODO 模拟一个数字
		int randomIndex = random.nextInt(driver.findElementsByClassName("android.widget.LinearLayout").size());
		String hotKeyItemXPath = "//android.widget.FrameLayout[1]/android.widget.ScrollView[1]/android.widget.LinearLayout["
				+ randomIndex + "]";
		AndroidElement hotkeyItem = driver.findElement(By.xpath(hotKeyItemXPath));
		String hotKeyInnerTvXpath = "//android.widget.FrameLayout[1]/android.widget.ScrollView[1]/android.widget.LinearLayout["
				+ randomIndex + "]/android.widget.TextView[1]";
		System.out.println(hotKeyInnerTvXpath);
		AndroidElement hotKeyInnerTv = driver.findElement(By.xpath(hotKeyInnerTvXpath));
		// System.out.println(searchBeforePerform = hotKeyInnerTv.getText());
		// TODO 必要时截图
		hotkeyItem.click();
		WaitUtil.forceWait(2);
		// TODO 风险不一定退出
		PageRouteUtil.pressBack();
	}

	@Test(dependsOnMethods = { "initCheck" }, expectedExceptions = StaleElementReferenceException.class)
	@Tips(description = "意义：expectedExceptions", riskPoint = "可能进入安装状态")
	public void checkDownloadPauseAndResumeOne() throws InterruptedException {
		WaitUtil.implicitlyWait(5);
		LogUtil.printCurrentMethodName();
		List<AndroidElement> eList = mDriver.findElements(By.id("com.cmic.mmnes:id/status_btn"));
		if (eList.size() > 0) {
			int minItemSize = Math.min(eList.size(), 5);
			int randomIndex = RandomUtil.getRandomNum(minItemSize);
			AndroidElement targetElement = eList.get(randomIndex);
			targetElement.click();// 开始下载
			// TODO 网速判断
			// 开始下载
			WaitUtil.implicitlyWait(5);
			if (ElementUtil.isElementPresent(By.id("com.cmic.mmnes:id/mm_down_goon"))) {
				// mDriver.findElement(By.id("com.cmic.mmnes:id/mm_down_goon")).click();
				WaitUtil.forceWait(2);
				assertEquals(targetElement.getText(), "暂停");
			}
			targetElement.click(); // 暂停下载
			WaitUtil.forceWait(1);
			assertEquals(targetElement.getText(), "继续");
			// TODO 不稳定待日后完善
		}
	}
}
