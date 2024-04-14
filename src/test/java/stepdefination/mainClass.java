package stepdefination;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import generic.WebElementHandler;

public class mainClass {
	
	WebElement element;

	public void launchScript() throws Throwable {

		System.setProperty("webdriver.chrome.driver",
				"/Users/sushaaannt/Eclipse/EclipseWorkspace/seleniumbased/drivers/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.google.com/");
		element= WebElementHandler.getElement("search");
		element.sendKeys("abc");
		
	}

}
