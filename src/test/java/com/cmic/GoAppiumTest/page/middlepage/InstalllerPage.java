package com.cmic.GoAppiumTest.page.middlepage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.Tips;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

@Tips(description = "系统安装器界面", riskPoint = "不同的设备需要适配，兼容性差，当前仅用于N3")
public class InstalllerPage extends BasePage {

	@AndroidFindBy(id = "com.android.packageinstaller:id/app_name")
	public AndroidElement tvInstallingAppName;

	@AndroidFindBy(id = "com.android.packageinstaller:id/cancel_button")
	public AndroidElement btnCancelInstall;

	@AndroidFindBy(id = "com.android.packageinstaller:id/ok_button")
	public AndroidElement btnNextStep2Install;

	public InstalllerPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new InstallerAction();
	}

	public class InstallerAction extends BaseAction {

		@Tips(description = "没有特定的跳转逻辑，下载完成自动进入|点击下载完成的安装按钮")
		@Override
		public void go2SelfPage() {
			// TODO Auto-generated method stub
		}

	}
}
