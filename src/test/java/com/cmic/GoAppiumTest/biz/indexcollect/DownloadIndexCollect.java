package com.cmic.GoAppiumTest.biz.indexcollect;

import com.cmic.GoAppiumTest.App;
import com.cmic.GoAppiumTest.biz.base.BaseTest4IndexCollect;
import com.cmic.GoAppiumTest.helper.Tips;
import com.cmic.GoAppiumTest.util.AppUtil;

public class DownloadIndexCollect extends BaseTest4IndexCollect {

	@Override
	public void tearDownAfterClass() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setUpBeforeClass() {
	}

	@Tips(description = "下载在Wifi环境", riskPoint = "变量控制")
	public void download() {

	}

	@Tips(description = "下载在移动数据网路", riskPoint = "变量控制")
	public void downloadWithoutWifi() {

	}
}
