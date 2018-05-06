package com.cmic.GoAppiumTest.page;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.page.action.RequisiteAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class RequisitePage extends BasePage {

	@AndroidFindBy(xpath = "//android.widget.CheckBox[@text=' 全选']")
	public AndroidElement btnAllSelect;// 全选复选框

	@AndroidFindBy(xpath = "//android.support.v7.widget.RecyclerView[@resource-id='com.cmic.mmnes:id/rv_app']/android.widget.RelativeLayout[1]")
	public AndroidElement rlyItem;// 具体到一个Item

	@AndroidFindBy(id = "com.cmic.mmnes:id/tv_load")
	public AndroidElement btnAllDownLoad;// 一键全部下载button

	@AndroidFindBy(id = "com.cmic.mmnes:id/tv_main")
	public AndroidElement btnGo2MainPage;// 进入首页button

	public RequisitePage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new RequisiteAction();
	}

	public void clickAllCheckbox() {
		action.go2Click(btnAllSelect);
		forceWait(1);
	}

	public void clickOneItemCheckbox() {
		action.go2Click(rlyItem);
		forceWait(1);
	}

	public void clickGoDownloadManager() {
		action.go2Click(btnAllDownLoad);
		forceWait(2);
	}

	public void clickGoMain() {
		action.go2Click(btnGo2MainPage);
		forceWait(1);
	}
}
