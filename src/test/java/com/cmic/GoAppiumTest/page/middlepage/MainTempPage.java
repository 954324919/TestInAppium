package com.cmic.GoAppiumTest.page.middlepage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.action.SplashAction;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class MainTempPage extends BasePage {

	@AndroidFindBy(id = "com.cmic.mmnes:id/search_layout")
	private AndroidElement searchContainer; // 居中的搜索栏

	@AndroidFindBy(id = "com.cmic.mmnes:id/managerview")
	private AndroidElement btnDownload; // 右上下载

	@AndroidFindBy(id = "com.cmic.mmnes:id/update_point_iv")
	private AndroidElement updateNumPoint;// 右上角角标计数

	public MainTempPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new MainTempAction();
	}

	@Tips(description = "获取可更新的应用数目")
	public int getCanUpdataNum() {
		try {
			String tempNumText = updateNumPoint.getText();
			int numCanUpdate = Integer.parseInt(tempNumText);
			return numCanUpdate;
		} catch (Exception e) {
			return 0;
		}
	}

	@Tips(description = "临时操作页面的内部Action")
	public class MainTempAction extends BaseAction {
		@Override
		public void go2SelfPage() {
			PageRedirect.redirect2MainActivity();
		}
	}
}
