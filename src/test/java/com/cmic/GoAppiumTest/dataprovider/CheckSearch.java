package com.cmic.GoAppiumTest.dataprovider;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.dataprovider.util.ExcelUtil;
import com.cmic.GoAppiumTest.helper.FailSnapshotListener;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.ScreenUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
@Listeners(FailSnapshotListener.class)
public class CheckSearch {
	private String mTag;
	private AndroidDriver<AndroidElement> mDriver;

	@BeforeMethod
	public void tipBeforeTestCase() {
		// 点击同意并使用
		System.out.println("数据驱动用例[" + (++App.CASE_COUNT) + "]开始");
	}

	@AfterMethod
	public void tipAfterTestCase() {
		System.out.println("数据驱动用例[" + (App.CASE_COUNT) + "]结束");
	}

	@BeforeClass
	@Tips(description = "跳转进入SearchActivity")
	public void beforeClass() throws InterruptedException {
		mTag = getClass().getSimpleName();
		mDriver = DriverManger.getDriver();
		// TODO 在没有卸载软件时，可能会报错
		// PageRedirect.redirect2SearchActivity();
		WaitUtil.implicitlyWait(2);// 等待1S
		AndroidElement searchLayout = mDriver.findElement(By.id("com.cmic.mmnes:id/search_layout"));
		searchLayout.click();
		WaitUtil.forceWait(2);
		System.out.println("数据驱动用例集[" + mTag + "]开始");
	}

	@AfterClass
	public void afterClass() throws InterruptedException {// 执行一些初始化操作
		System.out.println("数据驱动用例集[" + mTag + "]结束");
	}

	@Test(enabled = false)
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		assertEquals(ContextUtil.getCurrentActivity(), ".activity.SearchActivity");
		ScreenUtil.screenShot("进入必备应用搜索界面");
		WaitUtil.implicitlyWait(2);
	}

	@DataProvider(name = "searchMetadata")
	public static Object[][] data() throws Exception {
		return ExcelUtil.readExcel(App.SEARCH_DATA_PROVIDER, App.SEARCH_SHEET_NAME);
	}

	@Test(dependsOnMethods = { "initCheck" }, dataProvider = "searchMetadata", enabled = false)
	public void searchDataProvider(String searchKeyWord, String searchResult1, String searchResult2) {
		// 点击搜索栏输入
		AndroidElement searchEt = mDriver.findElement(By.id("com.cmic.mmnes:id/searchText"));
		searchEt.click();
		searchEt.clear();
		searchEt.sendKeys(searchKeyWord);
		WaitUtil.implicitlyWait(2);
		mDriver.findElement(By.id("com.cmic.mmnes:id/search_icon_layout")).click();
		WaitUtil.implicitlyWait(15);
		// 判断搜索的结果是否包含期望的关键字
		List<AndroidElement> eList = mDriver.findElements(By.id("com.cmic.mmnes:id/app_name"));
		List<String> textList = new ArrayList<String>();
		for (AndroidElement e : eList) {
			textList.add(e.getText());
		}
		Assert.assertTrue(textList.contains(searchResult1));
		Assert.assertTrue(textList.contains(searchResult2));
	}
}
