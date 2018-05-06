package com.cmic.GoAppiumTest.page.middlepage;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.PageFactory;
import com.cmic.GoAppiumTest.base.BaseAction;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.PageRedirect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.ContextUtil;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WithTimeout;

@Tips(description = "权限提示过度页", riskPoint = "由于是过渡页面，不拆分Page和Action", triggerTime = "授权过程不接受App要求的权限")
public class PermissionTipsPage extends BasePage {

	@WithTimeout(time = 5, unit = TimeUnit.SECONDS)
	@AndroidFindBy(id = "com.cmic.mmnes:id/show_pre_text")
	public AndroidElement btnPermissionDetail;// 权限获取说明按钮[PermissionTip]

	@WithTimeout(time = 5, unit = TimeUnit.SECONDS)
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

		@Override
		public void go2SelfPage() {
			if (ContextUtil.getCurrentPageActivtiy().equals("GrantPermissionsActivity")) {
				return;
			} else {
				PageRedirect.redirect2PermissionRalation();
			}
		}
	}
}
