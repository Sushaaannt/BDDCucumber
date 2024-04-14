package generic;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebElementHandler {

	public static JsonHandler jsonh = JsonHandler.getInstance();
	public static WebDriver driver = null;

	public static WebElement getElement(String identifier) throws Throwable {
		WebElement element;
		String locator = jsonh.getJsonData("locator", identifier);
		String value = jsonh.getJsonData("value", identifier);
		element = searchElement(getBy(locator, value), false);
		if (element != null) {
			return element;
		}
		return null;
	}

	public static WebElement searchElement(By by, boolean isCheckingForFrames) {
		if (isElementPresent(by)) {
			return getElementWhenDisplayed(by);
		} else {
			if (!isCheckingForFrames) {
				driver.switchTo().defaultContent();
				if (isElementPresent(by)) {
					return getElementWhenDisplayed(by);
				}
			}
		}
		return null;

	}

	public static By getBy(String locator, String value) {
		By by = null;
		switch (locator) {
		case "id":
			by = By.id(value);
			break;
		case "xpath":
			by = By.xpath(value);
			break;

		default:
			break;
		}
		return by;
	}

	public static boolean isElementPresent(By by) {
		boolean status = false;
		if (driver.findElements(by).size() > 0) {
			status = true;
		}
		return status;
	}

	private static WebElement getElementWhenDisplayed(By by) {
		List<WebElement> listOfElements = driver.findElements(by);
		if (listOfElements.size() == 1) {
			if (listOfElements.get(0).isDisplayed()) {
				return listOfElements.get(0);
			}
		} else {
			for (WebElement element : listOfElements) {
				if (element.isDisplayed()) {
					return element;
				}
			}
		}
		return null;
	}

}
