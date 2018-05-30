package com.cmic.GoAppiumTest.helper;

import com.google.common.base.Function;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * This is extended version of
 * {@link org.openqa.selenium.support.ui.ExpectedCondition}. It is combined with
 * {@link java.util.function.Function}.
 *
 * @param <T>
 *            The return type
 */
@Tips(description = "用于AndroidWait显示等待")
@FunctionalInterface
public interface ExpectedCondition4AndroidDriver<T> extends Function<AndroidDriver<AndroidElement>, T> {
}
