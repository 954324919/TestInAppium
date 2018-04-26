package com.cmic.GoAppiumTest.page;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.action.PermissionAction;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class PermissionPage extends BasePage {

	@AndroidFindBy(id = "com.android.packageinstaller:id/do_not_ask_checkbox")
	public AndroidElement cbNotAsk;// 权限不再询问[packageInstaller]

	@AndroidFindBy(id = "com.android.packageinstaller:id/permission_deny_button")
	public AndroidElement btnDeny;// 拒绝授予权限[packageInstaller]

	@AndroidFindBy(id = "com.android.packageinstaller:id/permission_allow_button")
	public AndroidElement btnAllow;// 拒绝授予权限[packageInstaller]

	public PermissionPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new PermissionAction();
	}

	@Tips(description = "点击接收全部权限")
	public void clickAllowTillAll() {
		for (int i = 0; i < 4; i++) {
			action.go2Click(btnAllow);
			forceWait(1);
		}
	}

	@Tips(description = "点击拒绝全部权限")
	public void clickDenyTillAll() {
		for (int i = 0; i < 4; i++) {
			action.go2Click(btnDeny);
			forceWait(1);
		}
	}

	@Tips(description="点击不再询问")
	public void clickNotAsk() {
		action.go2Click(cbNotAsk);
	}
}
