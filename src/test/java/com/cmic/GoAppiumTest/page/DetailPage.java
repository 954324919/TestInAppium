package com.cmic.GoAppiumTest.page;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.PageFactory;
import com.cmic.GoAppiumTest.base.BasePage;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.action.DetailAction;
import com.cmic.GoAppiumTest.util.LogUtil;
import com.cmic.GoAppiumTest.util.RandomUtil;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WithTimeout;

public class DetailPage extends BasePage {

	@AndroidFindBy(id = "com.cmic.mmnes:id/search_iv")
	public AndroidElement searchLLy;// 详情页ActionBar搜索框

	@AndroidFindBy(id = "com.cmic.mmnes:id/horizontal_scroll_view")
	public AndroidElement imageAdScorll;// 图片广告画廊

	@AndroidFindBy(id = "com.cmic.mmnes:id/recycledImageView")
	public List<AndroidElement> imageAdList;// 单个广告

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").resourceId(\"com.cmic.mmnes:id/app_name\")")
	public List<AndroidElement> hotInstall;// 大家都在装的软件列表

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").resourceId(\"com.cmic.mmnes:id/status_btn\")")
	public List<AndroidElement> hotInstallStartBtn;// 大家都在装的下载按钮列表

	@AndroidFindBy(id = "com.cmic.mmnes:id/detail_appname_tv")
	public AndroidElement targetHotInstallAppName;// 具体选中的大家都在 搜游戏名称

	@WithTimeout(time = 3, unit = TimeUnit.SECONDS)
	@AndroidFindBy(id = "com.cmic.mmnes:id/videoImgLayout")
	public AndroidElement h5Ad;

	@WithTimeout(time = 3, unit = TimeUnit.SECONDS)
	@AndroidFindBy(id = "com.cmic.mmnes:id/bottom_mark_tv")
	public AndroidElement appMarkDetail;// 应用详情简介、

	@AndroidFindBy(id = "com.cmic.mmnes:id/detail_benefit_layout")
	public AndroidElement benifitLly;// 福利详情

	@AndroidFindBy(id = "com.cmic.mmnes:id/mm_down_goon")
	public AndroidElement goOnDownload;// 继续下载

	@AndroidFindBy(id = "com.cmic.mmnes:id/quanxian")
	public AndroidElement btnPermissino;// 权限

	@AndroidFindBy(id = "com.cmic.mmnes:id/jubao")
	public AndroidElement btnPost;// 举报按钮

	@AndroidFindBy(id = "com.cmic.mmnes:id/update_point_iv")
	public AndroidElement tvPointNum;// 下载计数

	@AndroidFindBy(id = "com.cmic.mmnes:id/install_btn")
	public AndroidElement btnInstall;// 底部安装按钮

	public DetailPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		action = new DetailAction();
	}

	@Tips(description = "进入免安装")
	public void go2WithoutInstall() {
		if (imageAdList.size() > 1) {
			action.go2ClickAndWait(imageAdList.get(1), 2);// 预期进入ImageBrowerActivity,避免取到免安装
		} else {
			throw new RuntimeException("进入免安装页面异常");
		}
	}

	@Tips(description = "点击大家都在装进入下一级的DetailAct")
	public void goRandomClick2DetailInOtherInstall() {
		int eListSize;
		if ((eListSize = hotInstall.size()) > 0) {//
			int tempIndex = RandomUtil.getRandomNum(eListSize - 1);
			AndroidElement randomItem = hotInstall.get(tempIndex);
			String tempAppName = randomItem.getText();
			action.go2ClickAndWait(randomItem, 1);
			// 进入下一级详情页Page
			assertEquals(targetHotInstallAppName.getText(), tempAppName);
		}
	}

	@Tips(description = "用于缓存随机点击的按钮")
	public int targetElementIndex = 0;

	@Tips
	public void go2RandomStartDownloadOtherInstall() {
		if (hotInstallStartBtn.size() > 0) {
			targetElementIndex = RandomUtil.getRandomNum(hotInstallStartBtn.size() - 1);
			LogUtil.w("选中开始下载位置为{}", targetElementIndex);
			AndroidElement targetElement = hotInstallStartBtn.get(targetElementIndex);
			if (targetElement.getText().equals("打开")) {
				LogUtil.e("已经是打开的状态");
				return;
			}
			assertEquals(targetElement.getText(), "下载");
			// 开始下载
			targetElement.click();
		} else {
			LogUtil.e("没有可下载的搜索结果");
			throw new RuntimeException("没有可下载的搜索结果");
		}
	}

	public void go2RandomPauseDownloadOtherInstall() {
		AndroidElement targetElement = hotInstallStartBtn.get(targetElementIndex);
		if (targetElement.getText().equals("暂停")) {
			targetElement.click();
		} else if (targetElement.getText().equals("打开")) {
			LogUtil.e("已经是打开的状态");
			return;
		} else {
			LogUtil.e("当前按钮状态为{}", targetElement.getText());
			return;
		}
	}

	public String getRandomTargetText() {
		return hotInstallStartBtn.get(targetElementIndex).getText();
	}
}
