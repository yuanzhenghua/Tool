package web;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementTool {
	public static boolean isElementExist(WebElement element, By locator){
		boolean flag = false;
		try {
			return null != element.findElement(locator);
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isElementExist(WebDriver driver, By locator){
		boolean flag = false;
		try {
			return null != driver.findElement(locator);
		} catch (Exception e) {
			return false;
		}
	}
	
	public static String getBrowser(WebDriver driver) throws Exception {
		String browser;
		String userAgent = (String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");
		if (userAgent.indexOf("Opera") > -1) {
			browser = "Opera";
		} else if (userAgent.indexOf("Firefox") > -1) {
			browser = "Firefox";
		} else if (userAgent.indexOf("Chrome") > -1) {
			browser = "Chrome";
		} else if (userAgent.indexOf("Safari") > -1) {
			browser = "Safari";
		} else if (userAgent.indexOf("compatible") > -1 || userAgent.indexOf("MSIE") > -1 && userAgent.indexOf("Opera") <= -1) {
			browser = "IE";
		} else {
			throw new Exception("浏览器类型位置: "+userAgent);
		}
		return browser;
	}
	
	public static String checkAttribute(WebElement el, String attr) {
		if (el.getCssValue(attr) != null) {
			return attr;
		} else if (el.getCssValue("-webkit-"+attr) != null) {
			return "-webkit-"+attr;
		} else {
			return attr;
		}
	}
	
	public static float getsWidth(WebDriver driver) {
		return Float.parseFloat(String.valueOf(((JavascriptExecutor) driver).executeScript("return screen.width")));
	}

	public static float getsHeight(WebDriver driver) {
		return Float.parseFloat(String.valueOf(((JavascriptExecutor) driver).executeScript("return screen.height")));
	}

	public static float getFontSize(WebDriver driver) {
		return Float.parseFloat(driver.findElement(By.tagName("html")).getCssValue("font-size").substring(0, driver.findElement(By.tagName("html")).getCssValue("font-size").length()-2));
	}
}
