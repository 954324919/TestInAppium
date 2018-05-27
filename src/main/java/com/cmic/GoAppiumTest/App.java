package com.cmic.GoAppiumTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @描述 全局配置参数
 * @author kiwi
 */
public class App {

	public static String SAVEPATH;// App的保存路径
	@Tips(description = "设备名称列表，当前版本默认只有1个挂载设备", riskPoint = "要求和型号完全对应")
	public static String DEVICENAME_LIST;//
	@Tips(description = "设备型号列表，当前版本默认只有1个挂载设备", riskPoint = "要求和名称完全对应")
	public static String DEVICEMODEL_LIST;

	static {
		InputStream in = ClassLoader.class.getResourceAsStream("/res/jenkins/jks.properties");
		Properties iniFromJks = new Properties();
		try {
			InputStreamReader iReader = new InputStreamReader(in, "UTF-8");
			iniFromJks.load(iReader);
		} catch (IOException e) {
			e.printStackTrace();
		} // 配置项从Jenkins传入
		SAVEPATH = iniFromJks.getProperty("APP_SAVEPATH","D:\\Jenkins\\TestAutomation\\Parpare\\mailResultSave");
		DEVICENAME_LIST = iniFromJks.getProperty("DEVICENAME_LIST");
		DEVICEMODEL_LIST = iniFromJks.getProperty("DEVICEMODE_LLIST");
		System.out.println("App保存路径为" + DEVICENAME_LIST);
	}

	// ----------------- 用于测试的代码 ---------------

	// ----------------- 进行TestNg时需要注释 -----------

	public static int CASE_COUNT = 0;
	public static int PHONE_COUNT = 0;

	public static boolean SNAPSHOT_SWITCH = false;
	// 用来标识Notification的状态
	public static boolean NOTIFICATION_STATUS = false;

	// 必备1.5的一些配置信息
	public static final String PACKAGE_NAME = "com.cmic.mmnes";// 必备新包名1.5
	public static final String LAUNCHER_ACTIVITY = "com.cmic.mmnes.activity.SplashActivity";
	// 由于需要跳转至MM，保存一些MM的信息
	public static final String MM_PACKAGE_NAME = "com.aspire.mm";
	// 被测应用名
	public static final String APP_NAME = "必备应用";
	// 被测应用版本
	public static final String APP_VERSION = "1.5.0";

	// TODO 不稳定,对于不同版本需要进行修改
	// 必备1.5的一些界面参数，用于一些难以进行定位的控件
	public static final int STATUS_BAR_HEIGHT_DP = 24;
	public static final int TITLE_BAR_HEIGHT_DP = 50;
	public static final int MAIN_AD_WEBVIEW_HEIGHT = 148;
	// 搜索联想
	public static final int RELATION_DIRECTION_ITEM_HEIGHT_DP = 85;
	public static final int RELATION_NORMAL_ITEM_HEIGHT_DP = 28;
	public static final int MARGIN_LEFT_OR_RIGHT_DP = 15;// 有14和16的版本

	// 默认用户目录为当前工作目录
	@Tips(description = "由于TestNg的特殊性，USER_DIR获取的是:classPath/target", riskPoint = "可能导致歧义")
	public static final File USER_DIR = new File(System.getProperty("user.dir")).getParentFile();
	@Tips(description = "在不进行TestNg时，用于目录是classPath")
	public static final File USER_DIR_RAW = new File(System.getProperty("user.dir"));
	public static final File CLASSPATHROOT = USER_DIR;
	public static final String CLASSPATH = CLASSPATHROOT.getAbsolutePath();

	// 用于存储测试结果（截图、log等）的目录
	public static final String TEST_REPORT_DIR = "xx";
	// 用于存储截图的目录
	public static final String TEST_SNAP_DIR = "xx";

	public static final String SEARCH_DATA_PROVIDER = "/res/dataprovider/search_data.xlsx";
	public static final String SEARCH_SHEET_NAME = "searchkeyword";

	public static final int WAIT_TIME_IMPLICITLY = 20;
	public static final int WAIT_TIME_FORCE = 5;

	// ----------------- 用于测试的代码 ---------------

	@Tips(description = "入口函数")
	public static void main(String[] args) throws IOException {
		System.out.println("Hello GoAppium!");
	}
	// ----------------- 进行TestNg时需要注释 -----------
}
