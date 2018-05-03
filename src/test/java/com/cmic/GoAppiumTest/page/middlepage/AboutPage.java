package com.cmic.GoAppiumTest.page.middlepage;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.PageFactory;
import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class AboutPage extends BasePage {

	@AndroidFindBy(id = "com.cmic.mmnes:id/about_check_update_layout")
	AndroidElement aboutUpdateLly;// 应用更新

	@AndroidFindBy(id = "com.cmic.mmnes:id/about_user_protocol_layout")
	AndroidElement aboutProtocolLly;// 用户协议

	public AboutPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new AboutAction();
	}

	public void click2Update() {
		action.go2Click(aboutUpdateLly);
		forceWait(1);
	}

	public void click2UserProtocol() {
		action.go2Click(aboutProtocolLly);
		forceWait(1);
	}

	/**
	 * 内部类Action,用于过度页AboutPage
	 *
	 */
	public class AboutAction extends BaseAction {

		@Override
		public void go2SelfPage() {
			PageRedirect.redirect2AboutActivity();
		}

		public AboutAction() {
			// TODO Auto-generated constructor stub
		}
	}
}
