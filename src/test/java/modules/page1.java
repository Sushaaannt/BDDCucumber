package modules;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import generic.WebElementHandler;

public class page1 {
	
	public void sampleMethod() {
		System.out.println("Method Worked");
	}
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
