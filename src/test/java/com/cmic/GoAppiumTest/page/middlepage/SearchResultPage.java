package com.cmic.GoAppiumTest.page.middlepage;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.action.SearchAction;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SearchResultPage extends BasePage {

	@AndroidFindBy(id = "com.cmic.mmnes:id/tv_refuse")
	public AndroidElement btnRefuce;// 拒绝Button

	@AndroidFindBy(id = "com.cmic.mmnes:id/tv_ok")
	public AndroidElement btnAccept;// 同意并使用Button

	@AndroidFindBy()
	public List<AndroidElement> d;

	public SearchResultPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new SearchAction();
	}

	public class SearchResultAction extends BaseAction {

		public SearchResultAction() {
			// TODO Auto-generated constructor stub
		}

		@Override
		@Tips(description = "该过度页不可直接到达")
		public void go2SelfPage() {
			// TODO Auto-generated method stub
		}

		public void go2SelfPage(String keyword) {

		}
	}
}
