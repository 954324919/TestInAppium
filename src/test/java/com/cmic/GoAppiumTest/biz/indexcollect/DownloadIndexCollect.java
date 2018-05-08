package com.cmic.GoAppiumTest.biz.indexcollect;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.biz.base.BaseTest4IndexCollect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.SearchPage;
import com.cmic.GoAppiumTest.page.middlepage.DownloadDialogPage;
import com.cmic.GoAppiumTest.page.middlepage.InstalllerPage;
import com.cmic.GoAppiumTest.page.middlepage.NotificationPage;
import com.cmic.GoAppiumTest.page.middlepage.SearchResultPage;
import com.cmic.GoAppiumTest.page.middlepage.DownloadDialogPage.DownloadDialogAction;
import com.cmic.GoAppiumTest.page.middlepage.InstalllerPage.InstallerAction;
import com.cmic.GoAppiumTest.util.LogUtil;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.Connection;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class DownloadIndexCollect extends BaseTest4IndexCollect {

	@Override
	public void tearDownAfterClass() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setUpBeforeClass() {
	}

	@Test
	@Tips(description = "下载在Wifi环境", riskPoint = "变量控制")
	public void download() {
		LogUtil.printCurrentMethodNameInLog4J();
		NotificationPage notificationPage = new NotificationPage();
		notificationPage.go2OpenTheWifi();
		// 断言防止网络切换失败
		Connection networkStatus = notificationPage.action.go2GetNetWorkStatus();
		LogUtil.d("当前网络状态为{}", networkStatus);
		assertEquals(networkStatus == Connection.ALL || networkStatus == Connection.WIFI, true);
		// 寻找最佳匹配
		SearchPage searchPage = new SearchPage();
		searchPage.action.go2SelfPage();
		searchPage.go2SearchByKeyWord("MM资讯");
		//
		SearchResultPage searchResultPage = new SearchResultPage();
		if (searchResultPage.isElementShown(searchResultPage.searchResultCount)) {
			double appSize = searchResultPage.getAppSize();
			LogUtil.e("应用包大小为{}", appSize);
			// 准备接收判断的逻辑
			InstalllerPage installlerPage = new InstalllerPage();
			InstallerAction installerAction = (InstallerAction) installlerPage.action;
			// 开始下载
			searchResultPage.action.go2Click(searchResultPage.btnAppFirstDownload);
			double downloadTime = installerAction.go2GetTimeDiffElementShow(installlerPage.tvInstallingAppName);
			LogUtil.i("应用下载时间为{}", downloadTime);
			LogUtil.i("应用下载速率为{}{}/{}", String.format("%.2f", appSize / downloadTime), "M", "秒");
		} else {
			LogUtil.e("搜索{}失败", "MM资讯");
		}
	}

	@Test
	@Tips(description = "下载在移动数据网路", riskPoint = "变量控制")
	public void downloadWithoutWifi() {
		LogUtil.printCurrentMethodNameInLog4J();

		NotificationPage notificationPage = new NotificationPage();
		notificationPage.go2CloseTheWifi();
		// API
		Connection networkStatus = notificationPage.action.go2GetNetWorkStatus();
		LogUtil.d("当前网络状态为{}", networkStatus);
		assertEquals(networkStatus == Connection.ALL || networkStatus == Connection.WIFI, false);
		// 寻找最佳匹配
		SearchPage searchPage = new SearchPage();
		// 重新初始化应用
		searchPage.action.go2AppReset();
		// 回到本应用
		searchPage.action.go2SelfPage();
		searchPage.go2SearchByKeyWord("MM资讯");
		//
		SearchResultPage searchResultPage = new SearchResultPage();
		if (searchResultPage.isElementShown(searchResultPage.searchResultCount)) {
			double appSize = searchResultPage.getAppSize();
			LogUtil.e("应用包大小为{}", appSize);
			// 准备接收判断的逻辑
			InstalllerPage installlerPage = new InstalllerPage();
			InstallerAction installerAction = (InstallerAction) installlerPage.action;
			// 开始下载
			searchResultPage.action.go2Click(searchResultPage.btnAppFirstDownload);
			// 开始统计
			double downloadTime = installerAction.go2GetTimeDiffElementShow(installlerPage.tvInstallingAppName);
			LogUtil.i("应用下载时间为{}", downloadTime);
			LogUtil.i("应用下载速率为{}{}/{}", String.format("%.2f", appSize / downloadTime), "M", "秒");
		} else {
			LogUtil.e("搜索{}失败", "MM资讯");
		}
	}
}
