package com.cmic.GoAppiumTest.page.middlepage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

@Tips(description = "详情页面的权限细则Page")
public class PermissionDetailOfDetailPage extends BasePage {

	public PermissionDetailOfDetailPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new PermissionDetailOfDetailAction();
	}

	public class PermissionDetailOfDetailAction extends BaseAction {

		@Override
		public void go2SelfPage() {
			driver.findElement(By.id("com.cmic.mmnes:id/quanxian")).click();
			WaitUtil.forceWait(3);
		}

		public PermissionDetailOfDetailAction() {
			// TODO Auto-generated constructor stub
		}
	}
}
