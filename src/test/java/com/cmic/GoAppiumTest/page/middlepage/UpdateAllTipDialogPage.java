package com.cmic.GoAppiumTest.page.middlepage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.Tips;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class UpdateAllTipDialogPage extends BasePage {

	@AndroidFindBy(id = "com.cmic.mmnes:id/mm_dialog_cancel")
	public AndroidElement btnCancelUpdate;// 取消更新

	@AndroidFindBy(id = "com.cmic.mmnes:id/mm_dialog_ok")
	public AndroidElement btnAcceptUpdate;// 确定更新

	public UpdateAllTipDialogPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new UpdateAllTipDialogAction();
	}

	@Tips(description = "内部Action用于UpdateAllPage的操作")
	public class UpdateAllTipDialogAction extends BaseAction {

		@Override
		public void go2SelfPage() {
			// TODO 过度页没有固定的调整方法
		}

		public UpdateAllTipDialogAction() {
			// TODO Auto-generated constructor stub
		}
	}
}
