package com.cmic.GoAppiumTest;
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
	public static String PACKAGE_NAME = "com.cmic.mmnes";//必备新包名1.5
	public static String LAUNCHER_ACTIVITY="com.cmic.mmnes.activity.SplashActivity";
}
