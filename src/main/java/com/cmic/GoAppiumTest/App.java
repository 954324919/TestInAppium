package com.cmic.GoAppiumTest;

import java.io.File;

/**
 * @描述 全局配置参数
 * @author kiwi
 *
 */
public class App {
	public static int CASE_COUNT = 0;
	public static int PHONE_COUNT = 0;
	
	public static boolean SNAPSHOT_SWITCH = false; 
	//用来标识Notification的状态
	public static boolean NOTIFICATION_STATUS = false; 
	
	//必备1.5的一些配置信息
	public static final String PACKAGE_NAME = "com.cmic.mmnes";//必备新包名1.5
	public static final String LAUNCHER_ACTIVITY="com.cmic.mmnes.activity.SplashActivity";
	//由于需要跳转至MM，保存一些MM的信息
	public static final String MM_PACKAGE_NAME = "com.aspire.mm";
	//被测应用名
	public static final String APP_NAME= "必备应用";
	//被测应用版本
	public static final String APP_VERSION = "1.5.0";
	
	//TODO 不稳定,对于不同版本需要进行修改
	//必备1.5的一些界面参数，用于一些难以进行定位的控件
	public static final int STATUS_BAR_HEIGHT_DP = 24; 
	public static final int TITLE_BAR_HEIGHT_DP = 50;
	public static final int MAIN_AD_WEBVIEW_HEIGHT = 148;
	//搜索联想
	public static final int RELATION_DIRECTION_ITEM_HEIGHT_DP= 85;
	public static final int RELATION_NORMAL_ITEM_HEIGHT_DP= 28;
	//
	public static final int MARGIN_LEFT_OR_RIGHT_DP= 15;//有14和16的版本
	
	
	//默认用户目录为当前工作目录
    public static final File USER_DIR = new File(System.getProperty("user.dir"));
    //用于存储测试结果（截图、log等）的目录
    public static final String TEST_REPORT_DIR="xx";
    //用于存储截图的目录
    public static final String TEST_SNAP_DIR="xx";
}
