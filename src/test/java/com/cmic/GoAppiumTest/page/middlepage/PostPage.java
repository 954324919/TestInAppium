package com.cmic.GoAppiumTest.page.middlepage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.WaitUtil;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

@Tips(description = "举报页面")
public class PostPage extends BasePage {

	@AndroidFindBy(id = "com.cmic.mmnes:id/submit_button")
	public AndroidElement btnSubmit;// 提交举报

	@AndroidFindBy(id = "com.cmic.mmnes:id/report_type_layout")
	public AndroidElement btnReportTypeSelect;// 举报类型选择

	@AndroidFindBy(xpath = "//android.widget.ListView[@resource-id='com.cmic.mmnes:id/report_listview']/android.widget.LinearLayout[7]")
	public AndroidElement targetTypeItem;// 目标类型

	@AndroidFindBy(id = "com.cmic.mmnes:id/report_edittext")
	public AndroidElement etReportInfo;

	@AndroidFindBy(id = "com.cmic.mmnes:id/phonenumber_edittext")
	public AndroidElement etPhoneNum;

	@AndroidFindBy(id = "com.cmic.mmnes:id/email_edittext")
	public AndroidElement etEMailInfo;// 邮箱信息

	public PostPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new PostAction();
	}

	@Tips(description = "中间过渡页面")
	public class PostAction extends BaseAction {

		@Override
		public void go2SelfPage() {
			driver.findElement(By.id("com.cmic.mmnes:id/jubao")).click();
			WaitUtil.forceWait(3);
		}

		public PostAction() {
			// TODO Auto-generated constructor stub
		}
	}
}
