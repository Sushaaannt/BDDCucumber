package generic;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebElementHandler {

	public static JsonHandler jsonh = JsonHandler.getInstance();
	public static WebDriver driver = null;
	static WebElement element;

	public static void launch_browser() throws Throwable {
		System.setProperty("webdriver.chrome.driver",
				"/Users/sushaaannt/Eclipse/EclipseWorkspace/seleniumbased/drivers/chromedriver");
		driver = new ChromeDriver();
		driver.get("https://www.google.com/");
		element= WebElementHandler.getElement("search");
        assert element != null;
        element.sendKeys("abc");
	}

	public static WebElement getElement(String identifier) throws Throwable {
		WebElement element;
		String locator = jsonh.getJsonData("locator", identifier);
		String value = jsonh.getJsonData("value", identifier);
		//element = searchElement(getBy(locator, value), false);
		element = getElementWhenDisplayed(getBy(locator,value));
		return element;
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
        return !driver.findElements(by).isEmpty();
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
