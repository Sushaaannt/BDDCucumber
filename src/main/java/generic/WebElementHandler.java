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
        element = WebElementHandler.getElement("search");
        assert element != null;
        element.sendKeys("abc");
    }

    public static WebElement getElement(String identifier) throws Throwable {
        WebElement element;
        String locator = jsonh.getJsonData("locator", identifier);
        String value = jsonh.getJsonData("value", identifier);
        //element = searchElement(getBy(locator, value), false);
        element = getElementWhenDisplayed(getBy(locator, value));
        return element;
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
            case "name":
                by = By.name(value);
                break;
            case "linktext":
                by = By.linkText(value);
                break;
            case "cssselector":
                by = By.cssSelector(value);
                break;
            case "partiallinktext":
                by = By.partialLinkText(value);
                break;
            case "tagname":
                by = By.tagName(value);
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

    public static List<WebElement> getAllFrames() {
        List<WebElement> frames = driver.findElements(By.tagName("frame"));
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
        frames.addAll(iframes);
        return frames;
    }

    public static boolean IsFramePresent() {
        String frameType = null;
        if (driver.findElements(By.tagName("iframe")).size() > 0) {
            frameType = "iframe";
        } else {
            frameType = "frame";
        }
        List<WebElement> frames = driver.findElements(By.tagName(frameType));
        System.out.println("NoOfFramesPresent:" + frames.size());
        return frames.size() > 0;
    }

    public static WebElement searchElement(By by, boolean isCheckingForFrames) throws Throwable {
        List<WebElement> frames;
        if (isElementPresent(by)) {
            return getElementWhenDisplayed(by);
        } else {
            if (!isCheckingForFrames) {
                driver.switchTo().defaultContent();
                if (isElementPresent(by)) {
                    getElementWhenDisplayed(by);
                }
            }
            frames = getAllFrames();
            if (frames.size() > 0) {
                for (WebElement parentFrame : frames) {
                    driver.switchTo().frame(parentFrame);
                    if (isElementPresent(by)) {
                        getElementWhenDisplayed(by);
                    }
                    if (IsFramePresent()) {
                        for (WebElement childFrame : getAllFrames()) {
                            driver.switchTo().frame(childFrame);
                            if (isElementPresent(by)) {
                                getElementWhenDisplayed(by);
                            }
                            driver.switchTo().parentFrame();
                        }

                    }
                    driver.switchTo().parentFrame();
                }
            }
        }
        return null;
    }
}
