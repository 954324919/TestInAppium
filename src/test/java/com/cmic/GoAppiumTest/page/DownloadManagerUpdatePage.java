package com.cmic.GoAppiumTest.page;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.PageFactory;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.action.DownloadManagerUpdateAction;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.RandomUtil;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WithTimeout;

public class DownloadManagerUpdatePage extends BasePage {

	@WithTimeout(time = 3, unit = TimeUnit.SECONDS)
	@AndroidFindBy(id = "com.cmic.mmnes:id/app_name")
	public List<AndroidElement> targetPageItemList;

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").resourceId(\"com.cmic.mmnes:id/status_btn\")")
	public List<AndroidElement> targetUpdateList;// 更新的按钮

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").textContains(\"下载\")")
	public AndroidElement eDownloadTab;

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").textContains(\"更新\")")
	public AndroidElement eUpdateTab;

	@AndroidFindBy(id = "com.cmic.mmnes:id/updateall_tx")
	public AndroidElement btnAllUpdate;// 全部更新
	
	@AndroidFindBy(id = "com.cmic.mmnes:id/mm_down_goon")
	public AndroidElement btnDownloadGoOn; // 土豪继续下载

	@AndroidFindBy(id = "com.cmic.mmnes:id/mm_dialog_cancel")
	public AndroidElement btnCancelUpdate;// 取消更新

	@AndroidFindBy(id = "com.cmic.mmnes:id/mm_dialog_ok")
	public AndroidElement btnAcceptUpdate;// 确定更新

	public DownloadManagerUpdatePage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new DownloadManagerUpdateAction();
	}
	
	@Tips(description = "用于缓存随机点击的按钮")
	public int targetElementIndex = 0;

	@Tips(description = "下载管理页面下载Tab的某个Item暂停下载", riskPoint = "用例的逻辑先于randomGo2StartDownload被调用")
	public void randomGo2PauseDownload() {
		AndroidElement targetElement = targetUpdateList.get(targetElementIndex);

		String btnTextTip = action.go2GetText(targetElement);
		if (btnTextTip.equals("暂停")) {
			targetElement.click();
		} else if (btnTextTip.equals("打开")) {
			LogUtil.e("已经是打开的状态");
			return;
		} else {
			LogUtil.e("当前按钮状态为{}", btnTextTip);
			return;
		}
	}

	@Tips(description = "下载管理页面下载Tab的某个Item暂停下载")
	public void randomGo2StartDownload() {
		int tempIndex = RandomUtil.getRandomNum(targetUpdateList.size() - 1);
		int targetElementIndex = Math.min(tempIndex, 5);// 防止点击到超出显示页面Item

		LogUtil.w("选中开始下载位置为{}", targetElementIndex);
		AndroidElement targetElement = targetUpdateList.get(targetElementIndex);

		String btnTextTip = action.go2GetText(targetElement);
		if (btnTextTip.equals("打开")) {
			LogUtil.e("已经是打开的状态");
			return;
		}
		assertEquals(btnTextTip, "下载");
		// 开始下载
		action.go2ClickAndWait(targetElement, 1);
	}

	public String getRandomTargetText() {
		return targetUpdateList.get(targetElementIndex).getText();
	}
	
	public void go2RandomDetail() {
		int minItemSize = Math.min(targetPageItemList.size(), 5);
		int randomIndex = RandomUtil.getRandomNum(minItemSize);
		action.go2ClickAndWait(targetPageItemList.get(randomIndex), 2);
	}

}
