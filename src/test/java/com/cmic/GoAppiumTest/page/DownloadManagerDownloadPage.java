package com.cmic.GoAppiumTest.page;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.PageFactory;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.action.DownloadManagerDownloadAction;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.RandomUtil;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WithTimeout;

public class DownloadManagerDownloadPage extends BasePage {

	@WithTimeout(time = 1, unit = TimeUnit.SECONDS)
	@AndroidFindBy(id = "com.cmic.mmnes:id/function_tv")
	public AndroidElement btnAllOperate;// 全部操作

	@AndroidFindBy(id = "com.cmic.mmnes:id/delete_btn")
	public List<AndroidElement> btnDeleteDownloadList;// 取消下载

	@WithTimeout(time = 2, unit = TimeUnit.SECONDS)
	@AndroidFindBy(id = "com.cmic.mmnes:id/app_name")
	public List<AndroidElement> targetPageItemList;// 可下载Item

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").resourceId(\"com.cmic.mmnes:id/status_btn\")")
	public List<AndroidElement> targetDownItemList;// 下载按钮

	@AndroidFindBy(id = "com.cmic.mmnes:id/mm_dialog_ok")
	public AndroidElement okBtn;// 确定删除

	public DownloadManagerDownloadPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new DownloadManagerDownloadAction();
	}

	/**
	 * 
	 * @return 被删除的Item应用名称
	 */
	@Tips(description = "使用目标的index")
	public String randomGo2Delete() {// 点击删除按钮，进入确定删除弹窗
		int eListSize = btnDeleteDownloadList.size();
		if (eListSize > 0) {//
			String temp = action.go2GetText(targetPageItemList.get(0));
			AndroidElement randomItem = btnDeleteDownloadList.get(0);
			action.go2ClickAndWait(randomItem, 1);
			// 确定删除
			return temp;
		}
		return null;

		// 先前的代码
		// if (eListSize > 0 && DownloadManagerUpdatePage.targetElementIndex <
		// eListSize) {//
		// int tempIndex = RandomUtil.getRandomNum(eListSize - 1);
		// int minItemSize = Math.min(tempIndex, 5);// 防止点击到超出显示页面Item
		// String temp = action.go2GetText(targetPageItemList.get(minItemSize));
		// AndroidElement randomItem = btnDeleteDownloadList.get(minItemSize);
		// action.go2ClickAndWait(randomItem, 1);
		// // 确定删除
		// return temp;
		// }
	}

	public boolean findTargetElement(String appName) {
		for (AndroidElement e : targetDownItemList) {
			if (e.getText().equals(appName)) {
				return false;
			}
		}
		return true;
	}

	@Tips(description = "用于缓存随机点击的按钮")
	public static int targetElementIndex = 0;

	@Tips(description = "下载管理页面下载Tab的某个Item暂停下载", riskPoint = "用例的逻辑先于randomGo2StartDownload被调用")
	public void randomGo2PauseDownload() {
		AndroidElement targetElement = targetDownItemList.get(targetElementIndex);
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

	@Tips(description = "下载管理页面下载Tab的某个Item暂停下载", riskPoint = "该测试用例耦合度高，从更新[全部更新的测试用例]进入，导致不是待下载的初始状态")
	public AndroidElement randomGo2StartDownload() {
		int tempIndex = RandomUtil.getRandomNum(targetDownItemList.size() - 1);
		if (tempIndex < 0) {
			LogUtil.e("异常，产生随机点击下标为负数");
		}
		targetElementIndex = Math.min(tempIndex, 5);// 防止点击到超出显示页面Item
		LogUtil.w("选中开始下载位置为{}", targetElementIndex);
		AndroidElement targetElement = targetDownItemList.get(targetElementIndex);

		String btnTextTip = action.go2GetText(targetElement);
		if (btnTextTip.equals("打开")) {
			LogUtil.e("已经是打开的状态");
			return null;
		}
		assertEquals(btnTextTip, "继续");// 从更新[全部更新的测试用例]进入，导致不是待下载的初始状态
		// 开始下载
		action.go2ClickAndWait(targetElement, 1);
		return targetElement;
	}

	public String getRandomTargetText() {
		return targetDownItemList.get(targetElementIndex).getText();
	}
}
