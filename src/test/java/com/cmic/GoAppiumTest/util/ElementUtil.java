package com.cmic.GoAppiumTest.util;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

import com.cmic.GoAppiumTest.Heading;
import com.cmic.GoAppiumTest.base.DriverManger;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class ElementUtil {
	
	public static int getElementX(AndroidElement element) {//左上角X
		return element.getLocation().getX();
	}
	
	public static int getElementY(AndroidElement element) {//右上角Y
		return element.getLocation().getY();
	}
	
	public static int getElementWidth(AndroidElement element) {
		return element.getSize().width;
	}
	
	public static int getElementHeight(AndroidElement element) {//右上角Y
		return element.getSize().height;
	}
	
	//判断页面内是否存在元素
	public static boolean isElementExistByXpath(String xpath){
        try{
            DriverManger.getDriver().findElement(By.xpath(xpath));
            return true;
        }catch(org.openqa.selenium.NoSuchElementException ex){
            return false;
        }
    }
	
	 /**
     * 控件内上下滑动
     *
     * @param step    测试步骤
     * @param by      控件定位方式
     * @param heading 滑动方向 UP  DOWN
     */
    public static void swipeControl(By by, Heading heading) {
    	AndroidDriver<AndroidElement> driver = DriverManger.getDriver();
        // 获取控件开始位置的坐标轴
        Point start = driver.findElement(by).getLocation();
        int startX = start.x;
        int startY = start.y;
        // 获取控件宽高
        Dimension q = driver.findElement(by).getSize();
        int x = q.getWidth();
        int y = q.getHeight();
        // 计算出控件结束坐标
        int endX = x + startX;
        int endY = y + startY;
        // 计算中间点坐标
        int centreX = (endX + startX) / 2;
        int centreY = (endY + startY) / 2;

        switch (heading) {
            // 向上滑动
            case UP:
                driver.swipe(centreX, startY + 1, centreX, endY - 1, 1500);
                break;
            // 向下滑动
            case DOWN:
                driver.swipe(centreX,  endY - 1, centreX, startY + 1, 1500);
                break;
		default:
			break;
        }
    }
}
