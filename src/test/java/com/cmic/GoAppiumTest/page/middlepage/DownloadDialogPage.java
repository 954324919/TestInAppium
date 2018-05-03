package com.cmic.GoAppiumTest.page.middlepage;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.PageFactory;
import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.base.BasePage;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class DownloadDialogPage extends BasePage {

	@AndroidFindBy(id = "com.cmic.mmnes:id/mm_down_goon")
	public AndroidElement downLoadGoOn;// 下载继续按钮
	
	public DownloadDialogPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new DownloadDialogAction();
	}

	public class DownloadDialogAction extends BaseAction {

		@Override
		public void go2SelfPage() {
			// TODO 过度页面不设置跳转
		}

	}
}
