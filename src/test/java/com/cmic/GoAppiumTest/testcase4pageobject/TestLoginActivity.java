package com.cmic.GoAppiumTest.testcase4pageobject;

import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.LoginPage;
import com.cmic.GoAppiumTest.page.action.LoginAction;

@Tips(description = "暂时保存|触发时机难以确定")
public class TestLoginActivity extends BaseTest {

	public LoginPage loginPage;
	public LoginAction loginAction;

	@Override
	public void setUpBeforeClass() {
		loginPage = new LoginPage();
		loginAction = (LoginAction) loginPage.action;
		// 非稳定页面无法goSelfPage
	}

	@Override
	public void tearDownAfterClass() {
		// TODO Auto-generated method stub

	}

	@Test
	public void initCheck() {
		// TODO 验证是否在登陆页面
	}

}
