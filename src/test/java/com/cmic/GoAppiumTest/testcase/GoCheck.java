package com.cmic.GoAppiumTest.testcase;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.util.ContextUtil;
import com.cmic.GoAppiumTest.util.DeviceUtil;
import com.cmic.GoAppiumTest.util.NetworkUtil;
import com.cmic.GoAppiumTest.util.PageRouteUtil;
import com.cmic.GoAppiumTest.util.WaitUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
/**
 * @描述 用来暂时存放一些有意义的测试用例
 * @author kiwi
 *
 */
public class GoCheck {

	private AndroidDriver<AndroidElement> driver = DriverManger.getDriver();

	@Test
	public void test() throws InterruptedException {
		AndroidElement e1 = driver.findElementByXPath("//android.widget.Button[@content-desc=\"Add Contact\"]");
		e1.click();
		Thread.sleep(3000);
		driver.pressKeyCode(AndroidKeyCode.BACK);
	}

	@Test
	public void test1() throws InterruptedException {
		AndroidElement el = driver.findElementById("com.example.android.contactmanager:id/addContactButton");
		if (el == null) {
			assertEquals(true, false);
			return;
		}
		el.click();
		Thread.sleep(3000);
		driver.pressKeyCode(AndroidKeyCode.BACK);
	}
	
	
	@Test(dependsOnMethods={"initCheck"})
	public void goAnyWhere(){
		ContextUtil.goTargetActivity("com.cmic.mmnes", ".SearchActivity");
		WaitUtil.implicitlyWait(2);
		PageRouteUtil.pressBack();
		ContextUtil.goTargetActivity("com.example.android.contactmanager", ".ContactAdder");
		WaitUtil.implicitlyWait(2);
		PageRouteUtil.pressBack();
	}
	
	@Test(dependsOnMethods = { "initCheck" },enabled=false)
	public void futureHope() {//检查重定向通过
		PageRedirect.redirect2RequestiteActivity();
	}

	@Test(dependsOnMethods = { "initCheck" })
	public void testNetworkStatus() {
		System.err.println(NetworkUtil.getNetworkState());
		System.err.println(DeviceUtil.getDeviceTime());
	}
}
