package com.cmic.GoAppiumTest.page.middlepage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WithTimeout;

public class SharePage extends BasePage {

	@WithTimeout(time = 3, unit = TimeUnit.SECONDS)
	@AndroidFindBy(id = "com.cmic.mmnes:id/tv_copy")
	AndroidElement btnCopy;// 复制

	@WithTimeout(time = 3, unit = TimeUnit.SECONDS)
	@AndroidFindBy(id = "com.cmic.mmnes:id/tv_cancel")
	AndroidElement btnCancel;// 拒绝

	@WithTimeout(time = 3, unit = TimeUnit.SECONDS)
	@AndroidFindBy(id = "com.cmic.mmnes:id/tv_share")
	AndroidElement btnMoreShare;// 更多分享

	public SharePage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new ShareAction();
	}

	public void clickShareByLink() {
		action.go2Click(btnCopy);
		forceWait(1);
	}

	public void clickShareMore() {
		action.go2Click(btnMoreShare);
		WaitUtil.implicitlyWait(5);
		AndroidElement shareByMessageLly = driver.findElement(By.id("com.cmic.mmnes:id/item_layout"));
		shareByMessageLly.click();
		WaitUtil.forceWait(3);
	}

	public boolean shareDialogVisiable() {
		return isElementShown(btnCancel);
	}

	public class ShareAction extends BaseAction {

		@Tips(description = "无参数构造方法")
		public ShareAction() {
			// TODO
		}

		@Tips(description = "到达SharePage")
		@Override
		public void go2SelfPage() {
			if (!ContextUtil.getCurrentPageActivtiy().equals("SettingActivity")) {
				PageRedirect.redirect2SettingActivity();
				WaitUtil.implicitlyWait(2);
			}
			AndroidElement shareLly = driver.findElement(By.id("com.cmic.mmnes:id/rl_share"));
			shareLly.click();
			WaitUtil.forceWait(2);
		}
	}
}