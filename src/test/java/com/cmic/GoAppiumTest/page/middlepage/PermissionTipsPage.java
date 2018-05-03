package com.cmic.GoAppiumTest.page.middlepage;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.PageFactory;
import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

@Tips(description = "权限提示过度页", riskPoint = "由于是过渡页面，不拆分Page和Action", triggerTime = "授权过程不接受App要求的权限")
public class PermissionTipsPage extends BasePage {

	@AndroidFindBy(id = "com.cmic.mmnes:id/show_pre_text")
	public AndroidElement btnPermissionDetail;// 权限获取说明按钮[PermissionTip]

	@AndroidFindBy(id = "com.cmic.mmnes:id/mm_continue")
	public AndroidElement btnPermisssionRefuse;// 点击含泪拒绝[PermissionTip]

	@AndroidFindBy(id = "com.cmic.mmnes:id/mm_getagain")
	public AndroidElement btnRetry;// 应用信息重试管理页面[PermissionTip]

	public PermissionTipsPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new PermissinoTipsAction();
	}

	@Tips(description = "获取权限详情", riskPoint = "页面变化")
	public void click2GetPermissionDetail() {
		action.go2Click(btnPermissionDetail);
		forceWait(1);
	}

	@Tips(description = "点击含泪拒绝", riskPoint = "退出应用")
	public void click2RefucePermissionTip() {
		action.go2Click(btnPermisssionRefuse);
		forceWait(1);
	}

	@Tips(description = "点击再次获取权限", riskPoint = "进入PackageManager页面")
	public void click2RetryGetPermission() {
		action.go2Click(btnRetry);
		forceWait(1);
	}

	@Tips(description = "对应的Action内部类")
	public class PermissinoTipsAction extends BaseAction {
		public PermissinoTipsAction() {
			// TODO Auto-generated constructor stub
		}

		@Tips(description = "进入应用权限管理页面", triggerTime = "在PermissionTipsPage页面,拒绝权限提示的再次获取")
		public void go2PackageManager(AndroidElement e) {
			//
		}

		@Override
		public void go2SelfPage() {
			PageRedirect.redirect2PermissionRalation();
		}
	}
}
