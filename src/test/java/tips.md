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

# 添加依赖实现监听
<listener class-name="***.testng.TestngListener" /> </listeners>

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
	
	
# testNg 范例
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<!--suite（测试套件）为根路径，仅允许出现1次，是多个test（测试用例）的集合，以下为各属性含义及取值
    @name 必填，标记suite的名称
    @junit 选填，是否以junit模式运行，可选值（true|false） 默认值"false"
    @verbose 选填，命令行信息打印等级（与测报内容无关），可在测试代码注释中配置，可选值（1|2|3|4|5）
    @parallel 选填，是否多线程并发运行测试，可选值(false | methods | tests | classes | instances)，默认 "false"
    @thread-count 选填，填写值为正整数，当为并发执行时的线程池数量，默认为"5"
    @configfailurepolicy 
    一旦Before/After Class/Methods这些方法失败后，是继续执行测试还是跳过测试；可选值 (skip | continue)，默认"skip
    @annotations="javadoc" 获取注解的位置，如果为"javadoc", 则使用javadoc注解，否则使用jdk注解
    @time-out 为具体执行单元设定一个超时时间，具体参照parallel的执行单元设置；单位为毫秒
    @skipfailedinvocationcounts 是否跳过失败的调用，可选值(true | false)，默认"false"
    @data-provider-thread-count 并发执行时data-provider的线程池数量，默认为"10"
    @object-factory 一个实现IObjectFactory接口的类，用来实例测试对象
    @allow-return-values="true" 是否允许返回函数值，可选值(true | false)，默认"false"
    @preserve-order：顺序执行开关，可选值(true | false) "true"
    @group-by-instances：是否按实例分组，可选值(true | false) "false"
    @guice-stage 支持使用JSR-330的​@Inject注解​来配置运行时提供的实例
    @parent-module 和Guice框架有关，只运行一次，创建一个parent injector给所有guice injectors
    -->
<suite name="suitename" junit="false" verbose="3" parallel="false" thread-count="5" configfailurepolicy="skip"
       annotations="javadoc" time-out="10000" skipfailedinvocationcounts="true" data-provider-thread-count="5"
       object-factory="classname" allow-return-values="true" preserve-order="true" group-by-instances="false">

    <!--可以执行多个suite，@path 必填，欲引用的suitefile的绝对路径-->
    <suite-files>
        <suite-file path="/path/to/suitefile1"></suite-file>
    </suite-files>
    <!--全局参数，@name和@value必填，分别为参数名和参数值-->
    <parameter name="par1" value="value1"></parameter>
    <parameter name="par2" value="value2"></parameter>

    <!--方法选择器，在suite/test中增加需要额外执行的类（根据父标签而定），及安排执行优先级-->
    <method-selectors>
        <method-selector>
            <!--
                @name 必填
                @priority 选填
                -->
            <selector-class name="classname" priority="1"></selector-class>
            <!--
                @language 必填
                -->
            <script language="java"></script>
        </method-selector>
    </method-selectors>

    <!--test定义一次测试执行，以下为各属性含义及取值
        @name：必填，test的名字，测试报告中会有体现
        @junit：选填，是否以Junit模式运行，可选值(true | false)，默认"false"
        @verbose：选填，命令行信息打印等级，不会影响测试报告输出内容；可选值(1|2|3|4|5)
        @parallel：选填，是否多线程并发运行测试；可选值(false | methods | tests | classes | instances)，默认 "false"
        @thread-count：选填，当为并发执行时的线程池数量，默认为"5"
        @annotations：选填，获取注解的位置，如果为"javadoc", 则使用javadoc注解，否则使用jdk5注解
        @time-out：选填，为具体执行单元设定一个超时时间，具体参照parallel的执行单元设置；单位为毫秒
        @enabled：选填，设置当前test是否生效，可选值(true | false)，默认"true"
        @skipfailedinvocationcounts：选填，是否跳过失败的调用，可选值(true | false)，默认"false"
        @preserve-order：选填，顺序执行开关，可选值(true | false) "true"
        @group-by-instances：选填，是否按实例分组，可选值(true | false) "false"
        @allow-return-values：选填，是否允许返回函数值，可选值(true | false)，默认"false"
        -->
    <test name="testename" junit="false" verbose="3" parallel="false" thread-count="5" annotations="javadoc"
          time-out="10000" enabled="true" skipfailedinvocationcounts="true" preserve-order="true"
          allow-return-values="true">
        <!--局部参数，@name和@value必填，分别为参数名和参数值，如果参数名与全局参数一致，则覆盖全局参数取值-->
        <parameter name="par1" value="value1"></parameter>
        <parameter name="par2" value="value2"></parameter>
        <!--搭配class使用，执行class内指定组-->
        <groups>
            <!--定义执行组名，在run中使用
                @name 必填，组中组的名称
            -->
            <define name="xxx">
                <!--定义包含的测试组，测试方法属于哪个测试组在测试代码注释中定义。
                    @name 必填，需要包含进组中组的组名
                    @description 选填，关于组的描述
                    @invocation-numbers 选填，执行次序或者执行次数——TODO
                    -->
                <include name="" description="" invocation-numbers=""/>
                <include name="" description="" invocation-numbers=""/>
            </define>
            <!--运行组中组的配置-->
            <run>
                <!--执行指定的组中组，@name必填，与define name一致-->
                <include name=""/>
                <!--排除指定的组中组，@name必填，与define name一致-->
                <exclude name=""/>
            </run>
            <!--组中组的依赖配置-->
            <dependencies>
                <!--配置依赖
                    @name 必填，需要依赖其他组的组名，define中设置
                    @depends-on 必填，被依赖的组名，define中设置，可以有多个，用空格隔开
                    -->
                <group name="" depends-on=""></group>
                <group name="" depends-on=""></group>
            </dependencies>
        </groups>
        <!--配置要执行的类，是多个class的集合-->
        <classes>
            <!--局部参数，@name和@value必填，分别为参数名和参数值，如果参数名与全局参数和父标签的局部参数一致，则覆盖全局参数和父标签的局部参数取值-->
            <parameter name="par1" value="value1"></parameter>
            <parameter name="par2" value="value2"></parameter>
            <!--多个methods的集合，@name 必填，对应class的名称，如com.example.autotest.testcase-->
            <class name="classname">
                <!--要执行的方法，如为空，则执行整个class内包含的全部方法-->
                <methods>
                    <!--局部参数，@name和@value必填，分别为参数名和参数值，如果参数名与全局参数和父标签的局部参数一致，则覆盖全局参数和父标签的局部参数取值-->
                    <parameter name="par3" value="value3"></parameter>
                    <!--类内要执行的测试方法名，在测试代码注释中配置，如设置inclde，则只执行该方法，其他跳过
                        @name 必填，执行方法名
                        @description 选填，方法描述
                        @invocation-number 选填，宣发执行顺序或执行次数——TODO
                        -->
                    <include name="" description="" invocation-numbers=""></include>
                    <!--除了该方法外，类内其他方法都执行，@name 必填，不执行的方法名-->
                    <exclude name=""></exclude>
                </methods>
                <methods></methods>
            </class>
        </classes>
        <!--可以执行指定包下面所有类，是多个package的汇聚-->
        <packages>
            <!--配置要  name="">
                <!--包内要执行的测试方法名，在测试代码注释中配置，如设置inclde，则只执行该方法，其他跳过
                    @name 必填，执行方法名
                    @description 选填，方法描述
                    @invocation-number 选填，宣发执行顺序或执行次数——TODO
                    -->
                <include name="" description="" invocation-numbers=""></include>
                <!--除了该方法外，包内其他方法都执行，name 必填，不执行的方法名-->
                <exclude name=""></exclude>
            </package>
        </packages>
    </test>
    <!--设置监听的类名，可设置多个，class-name 必填，类名，如com.example.autotest.Listener-->
    <listeners>
        <listener class-name="classname1"/>
        <listener class-name="classname2"/>
    </listeners>
</suite>
```	


# log4j详细教程：http://www.codeceo.com/article/log4j-usage.html
	