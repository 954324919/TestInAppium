package com.cmic.GoAppiumTest.dataprovider;

import static org.testng.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.base.BaseTest;
import com.cmic.GoAppiumTest.base.DriverManger;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.page.SearchPage;
import com.cmic.GoAppiumTest.page.action.SearchAction;
import com.cmic.GoAppiumTest.page.middlepage.SearchResultPage;
import com.cmic.GoAppiumTest.util.LogUtil;

public class CheckSearchPageObject extends BaseTest {

	private SearchPage searchPage;
	private SearchAction searchAction;

	@BeforeMethod
	@Tips(description = "覆盖了BaseTest的方法")
	public void tipBeforeTestCase() {
		// 点击同意并使用
		LogUtil.w("数据驱动用例[" + (++App.CASE_COUNT) + "]开始");
	}

	@AfterMethod
	public void tipAfterTestCase() {
		LogUtil.w("数据驱动用例[" + (App.CASE_COUNT) + "]结束");
	}

	@Override
	@Tips(description = "被tipBeforeTestCase覆盖,不执行")
	public void setUpBeforeClass() {
		// TODO
	}

	@Override
	@Tips(description = "被tipAfterTestCase覆盖,不执行")
	public void tearDownAfterClass() {
		// TODO
	}

	@BeforeClass
	@Tips(description = "跳转进入SearchActivity，覆盖了BaseTest的方法")
	public void beforeClass() {
		mTag = getClass().getSimpleName();
		mDriver = DriverManger.getDriver();
		// PageObject初始化
		searchPage = new SearchPage();
		searchAction = (SearchAction) searchPage.action;
		searchAction.go2SelfPage();
		LogUtil.w("数据驱动用例集[" + mTag + "]开始");
	}

	@AfterClass
	@Tips(description = "跳转进入SearchActivity，覆盖了BaseTest的方法")
	public void afterClass() {// 执行一些初始化操作
		LogUtil.w("数据驱动用例集[" + mTag + "]结束");
	}

	@Test(enabled = false)
	public void initCheck() {// 1
		// TODO 后期需要确定是否为初次安装还是应用启动
		// 先确认是否进入该页面
		assertEquals(getCurrentPackageName(), ".activity.SearchActivity");
		searchPage.snapScreen("进入必备应用搜索界面");
	}

	@DataProvider(name = "searchMetadata")
	public Object[][] data() {
		try {
			return searchAction.readExcel(App.SEARCH_DATA_PROVIDER, App.SEARCH_SHEET_NAME);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Test(dependsOnMethods = { "initCheck" }, dataProvider = "searchMetadata", enabled = false)
	public void searchDataProvider(String searchKeyWord, String searchResult1, String searchResult2) {
		// 点击搜索栏输入
		searchAction.go2SendWord(searchPage.searchContainer, searchKeyWord);
		searchAction.go2ClickAndWait(searchPage.btnSubmitSearch, 2);
		// 进入SearchResult页面
		SearchResultPage tempSearchResultPage = new SearchResultPage();
		List<String> textList = new ArrayList<>();// 缓存搜索结果列表
		textList.clear();
		textList = tempSearchResultPage.getSearchResultList();
		Assert.assertTrue(textList.contains(searchResult1));
		Assert.assertTrue(textList.contains(searchResult2));
	}
}
