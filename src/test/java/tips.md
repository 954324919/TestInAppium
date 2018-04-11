# 总结部分
1.ResetApp Reset只是简单关闭App，然后再Launcher应用
2.注解所有@Test用例，加入Enable可关闭所有测试并不被TestNG识别
3.使用preserve-order可控制Class粒度级别的测试用例集顺序进行
4.使用以下方法控制Method顺序执行（https://blog.csdn.net/d6619309/article/details/52755578）
   (1) 使用priority指定执行顺序(默认值为0)，数值越小，越靠前执行
   (2) 从方法名称做手脚
   (3) 在xml里面使用<include>指定需要执行的方法和顺序
5.不可通过直接跳转到达某个不可导出export的Activity，0328/仅仅认为只能跳转到Intent.Main/Category。Launcher的Activity
6.理论上kill不会导致Appium进行所有测试，卸载软件不能让Appium停止测试
7.由于擦除，AndroidElement只有以下属性
	(1)类名System.out.println("[Button Add Contact]Class: "+el.getClass());   
	(2)控件类型 System.out.println("[Button Add Contact]TagName: "+el.getTagName());
	(3)控件的值System.out.println("[Button Add Contact]Text: "+el.getText());
8.抛出异常仍然会继续进行，只不过会导致严重的界面NoSuchException
9.
```java
// 跳到软件-榜单的分界面
//		driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.aspire.mm:id/hscrollid\")"
//				+ ".childSelector(new UiSelector().resourceId(\"android:id/tabs\")"
//				+ ".childSelector(new UiSelector().index(3)))").click();
```
10.thread.sleep() 是线程休眠若干秒，JAVA去实现。等待的时间需要预估的比较准确，
但实际上这是很难做到。而且系统一直再等待，预估的长了，时间就白白的浪费了，预估短了，不起作用。
implicitlyWait() 不是休眠，是设置超时时间，是每个driver自己去实现的
11.adb shell am force-stop 包名，杀死一个应用
12.先执行依赖部分，再执行非依赖部分。可见MainActivyt测试
13.HanlderInfo(https://blog.csdn.net/achang21/article/details/73331201)
14.PPI，相信大家都明白，也就是屏幕的像素密度，数值越高屏幕显示的越精细，这个数值也就是硬件数值
（荣耀6上的ROG技术只是将1080p屏幕内容缩小到720显示输出，屏幕本身并没有变）。而DPI在手机屏幕上的概念
是内容显示的密度。在分辨率相同的屏幕上，数值越小，一屏显示的内容就越多，也就是说如果你想吧你的手机
变成“老人机”一样的大字体大图标就可以把DPI数值调大，如果你想显示小图标小字体多内容的平板模式就把DPI数值调小。
15.H5调试（https://testerhome.com/topics/6871）
 启动测试（chrome://inspect/#devices）
16.	String targetToast = "已经是最新版本";
		Assert.assertEquals(ElementUtil.isTargetToast(targetToast), true);
17.POI解析Excel
每一个Excel文件都将被解析成一个WorkBook对象； 
Excel的每一页都将被解析成一个Sheet对象； 
然后，Excel中的每一行都是一个Row对象， 
每一个单元格都是一个Cell对象。
18.参数构建目标testng(https://www.cnblogs.com/imlvbu/p/7169918.html)

# TODO部分
1.在0329/仍然不能使用关闭initCheck来关闭初始检验异常后跳过其他不能进行的测试用例，仅用Enable注解管理

# 其他有意义的部分
# adb指令(http://www.mamicode.com/info-detail-2218552.html)
1.adb shell pm list package -f #查看包名
2.adb shell dumpsys window |findstr mCurrent 抓取当前Activity
3.adb shell pm clear <packagename> 清空缓存


# 改进部分
1.在运行之前要先检查程序，比如用例有没有加入xml管理、应用是不是应删除，避免浪费重启时间

# 异常
1.不可直接跳转
org.openqa.selenium.WebDriverException: 
An unknown server-side error occurred while processing the command. 
Original error: Error occured while starting App. Original error: Error executing adbExec. 
Original error: 'Command 'E\:\\AndroidStudioSDKUpdate\\platform-tools\\adb.exe -P 5037 -s cf49f213 shell am start -W -n com.cmic.mmnes/.activity.SearchActivity -S' exited with code 1'; Stderr: 'java.lang.SecurityException: Permission Denial: starting Intent 
{cmp=com.cmic.mmnes/.activity.SearchActivity } from null (pid=26867, uid=2000) not exported from uid 10220
2.滑动异常-加大Sleep时间
Encountered internal error running command:  {"jsonwp":{"sessionId":"6a6db355-f675-4a4c-ba0b-a5d87ac5e4b2","status":13,
"value":"Swipe did not complete successfully"}}
	