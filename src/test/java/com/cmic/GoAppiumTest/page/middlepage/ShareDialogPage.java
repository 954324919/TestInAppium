package com.cmic.GoAppiumTest.page.middlepage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;
import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.SettingPage;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

@Tips(description = "用于描述分享弹窗的Page|由于冲突暂时不启用")
public class ShareDialogPage extends BasePage {

	private SettingPage settingPage;

	public ShareDialogPage(SettingPage settingPage) {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		this.settingPage = settingPage;
		action = new ShareDialogAction();
	}

	@Tips(description = "中间过渡页面")
	public class ShareDialogAction extends BaseAction {
		@Override
		public void go2SelfPage() {
			settingPage.go2SharePage();
		}

		public ShareDialogAction() {
			// TODO Auto-generated constructor stub
		}
	}
}
