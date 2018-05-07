package com.cmic.GoAppiumTest.biz.indexcollect;

import static org.testng.Assert.assertEquals;

import com.cmic.GoAppiumTest.biz.base.BaseTest4IndexCollect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.SearchPage;
import com.cmic.GoAppiumTest.page.middlepage.DownloadDialogPage;
import com.cmic.GoAppiumTest.page.middlepage.NotificationPage;
import com.cmic.GoAppiumTest.page.middlepage.SearchResultPage;
import com.cmic.GoAppiumTest.page.middlepage.DownloadDialogPage.DownloadDialogAction;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.Connection;

public class DownloadIndexCollect extends BaseTest4IndexCollect {

	@Override
	public void tearDownAfterClass() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setUpBeforeClass() {
	}

	@Tips(description = "下载在Wifi环境", riskPoint = "变量控制")
	public void download() {
		NotificationPage notificationPage = new NotificationPage();
		notificationPage.go2OpenTheWifi();
		// API
		Connection networkStatus = notificationPage.action.go2GetNetWorkStatus();
		assertEquals(networkStatus == Connection.ALL || networkStatus == Connection.WIFI, true);
		// 寻找最佳匹配
		SearchPage searchPage = new SearchPage();
		searchPage.action.go2SelfPage();
		searchPage.go2SearchByKeyWord("王者荣耀");
		//
		SearchResultPage searchResultPage = new SearchResultPage();
		if (searchResultPage.isElementShown(searchResultPage.searchResultCount)) {
			AndroidElement tempE = searchResultPage.statusBtnList.get(0);
			tempE.click();
			searchResultPage.action.go2GetTimeDiffElementShow(tempE);
		}
	}

	@Tips(description = "下载在移动数据网路", riskPoint = "变量控制")
	public void downloadWithoutWifi() {

		NotificationPage notificationPage = new NotificationPage();
		notificationPage.go2CloseTheWifi();
		// API
		Connection networkStatus = notificationPage.action.go2GetNetWorkStatus();
		assertEquals(networkStatus == Connection.ALL || networkStatus == Connection.WIFI, false);
		// 寻找最佳匹配
		SearchPage searchPage = new SearchPage();
		searchPage.action.go2SelfPage();
		searchPage.go2SearchByKeyWord("王者荣耀");
		//
		SearchResultPage searchResultPage = new SearchResultPage();
		if (searchResultPage.isElementShown(searchResultPage.searchResultCount)) {
			AndroidElement tempE = searchResultPage.statusBtnList.get(0);
			tempE.click();
			// 弹窗
			DownloadDialogPage downloadDialogPage = new DownloadDialogPage();
			DownloadDialogAction downloadDialogAction = (DownloadDialogAction) downloadDialogPage.action;
			downloadDialogAction.go2ClickAndWait(downloadDialogPage.downLoadGoOn, 1);
			searchResultPage.action.go2GetTimeDiffElementShow(tempE);
		}
	}
}
