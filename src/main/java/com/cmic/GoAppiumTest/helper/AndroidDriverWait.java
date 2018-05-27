package com.cmic.GoAppiumTest.helper;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.Clock;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.SystemClock;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

@Tips(description = "用于AndroidDriver的显示等待")
public class AndroidDriverWait extends FluentWait<AndroidDriver<AndroidElement>> {
	public final static long DEFAULT_SLEEP_TIMEOUT = 500;
	private final AndroidDriver<AndroidElement> driver;

	/**
	 * Wait will ignore instances of NotFoundException that are encountered (thrown)
	 * by default in the 'until' condition, and immediately propagate all others.
	 * You can add more to the ignore list by calling ignoring(exceptions to add).
	 *
	 * @param driver
	 *            The WebDriver instance to pass to the expected conditions
	 * @param timeOutInSeconds
	 *            The timeout in seconds when an expectation is called
	 * @see WebDriverWait#ignoring(java.lang.Class)
	 */
	public AndroidDriverWait(AndroidDriver<AndroidElement> driver, long timeOutInSeconds) {
		this(driver, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeOutInSeconds, DEFAULT_SLEEP_TIMEOUT);
	}

	/**
	 * Wait will ignore instances of NotFoundException that are encountered (thrown)
	 * by default in the 'until' condition, and immediately propagate all others.
	 * You can add more to the ignore list by calling ignoring(exceptions to add).
	 *
	 * @param driver
	 *            The WebDriver instance to pass to the expected conditions
	 * @param timeOutInSeconds
	 *            The timeout in seconds when an expectation is called
	 * @param sleepInMillis
	 *            The duration in milliseconds to sleep between polls.
	 * @see AndroidDriverWait#ignoring(java.lang.Class)
	 */
	public AndroidDriverWait(AndroidDriver<AndroidElement> driver, long timeOutInSeconds, long sleepInMillis) {
		this(driver, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeOutInSeconds, sleepInMillis);
	}

	/**
	 * @param driver
	 *            The WebDriver instance to pass to the expected conditions
	 * @param clock
	 *            The clock to use when measuring the timeout
	 * @param sleeper
	 *            Object used to make the current thread go to sleep.
	 * @param timeOutInSeconds
	 *            The timeout in seconds when an expectation is
	 * @param sleepTimeOut
	 *            The timeout used whilst sleeping. Defaults to 500ms called.
	 */
	public AndroidDriverWait(AndroidDriver<AndroidElement> driver, Clock clock, Sleeper sleeper, long timeOutInSeconds,
			long sleepTimeOut) {
		super(driver, clock, sleeper);
		withTimeout(timeOutInSeconds, TimeUnit.SECONDS);
		pollingEvery(sleepTimeOut, TimeUnit.MILLISECONDS);
		ignoring(NotFoundException.class);
		this.driver = driver;
	}

	@Override
	protected RuntimeException timeoutException(String message, Throwable lastException) {
		TimeoutException ex = new TimeoutException(message, lastException);
		ex.addInfo(WebDriverException.DRIVER_INFO, driver.getClass().getName());
		if (driver instanceof AndroidDriver) {
			AndroidDriver<AndroidElement> remote = (AndroidDriver<AndroidElement>) driver;
			if (remote.getSessionId() != null) {
				ex.addInfo(WebDriverException.SESSION_ID, remote.getSessionId().toString());
			}
			if (remote.getCapabilities() != null) {
				ex.addInfo("Capabilities", remote.getCapabilities().toString());
			}
		}
		throw ex;
	}
}
