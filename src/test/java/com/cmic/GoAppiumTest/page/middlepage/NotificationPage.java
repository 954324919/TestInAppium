package com.cmic.GoAppiumTest.page.middlepage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.DeviceUtil;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.Connection;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WithTimeout;

/**
 * 由于开启Wifi或进行网络操作时没有权限，有待解决0507，暂时采用在通知栏点击来切换网络
 */
@Tips(description = "用于通知栏的操作", riskPoint = "不同的设备需要适配，兼容性差，当前仅用于N3")
public class NotificationPage extends BasePage {

	@WithTimeout(time = 5, unit = TimeUnit.SECONDS)
	@AndroidFindBy(xpath = "//android.widget.Switch[@content-desc='WLAN,']")
	public AndroidElement eWifiSwitch;

	public NotificationPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new NotificationAction();
	}

	@Tips(description = "开启Wifi", riskPoint = "移动数据网络状态不可知|仅可知Wifi开启")
	public void go2OpenTheWifi() {
		Connection beforeStatus = action.go2GetNetWorkStatus();
		if (beforeStatus == Connection.ALL || beforeStatus == Connection.WIFI) {
			// 不操作
		} else {
			action.go2SelfPage();
			action.go2ClickAndWait(eWifiSwitch, 2);
			action.go2CloseNotification();
		}
	}

	@Tips(description = "关闭Wifi", riskPoint = "移动数据网络状态不可知|仅可知Wifi关闭，不知是处于移动网络或是无网络状态")
	public void go2CloseTheWifi() {
		Connection beforeStatus = action.go2GetNetWorkStatus();
		if (beforeStatus == Connection.ALL || beforeStatus == Connection.WIFI) {
			action.go2SelfPage();
			action.go2ClickAndWait(eWifiSwitch, 2);
			action.go2CloseNotification();
		} else {
			// 不操作
		}
	}

	public class NotificationAction extends BaseAction {

		public NotificationAction() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void go2SelfPage() {
			DeviceUtil.openNotification();
			forceWait(0.5);
		}
	}
}
