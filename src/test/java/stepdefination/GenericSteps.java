package stepdefination;

import java.lang.reflect.Method;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import generic.JsonHandler;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GenericSteps {

	WebDriver driver = null;

	public static String MethodName;

	public static String ClassName;

	public static JsonHandler jsonh = JsonHandler.getInstance();

	String currentPage = null;

	public String sheetName = null;

	@Given("user switches to page {string}")
	public void user_switches_to_page(String page) throws Throwable {
		jsonh.setApplicationPage(page);
		currentPage = jsonh.getApplicationPage();
		JsonHandler.loadInJsonformat();
		sheetName = JsonHandler.getInstance().getApplicationPage();
		System.out.println("user switches to page " + page );
	}

	@Given("user calls business component {string} from location {string}")
	public void user_calls_business_component_from_location(String methodName, String className) throws Throwable {
		GenericSteps.MethodName = methodName;
		GenericSteps.ClassName = className;
		System.out.println("Starting Component " + className + "." + methodName);
		try {
			Object instanceOfRequiredClass = Class.forName("modules." + className).newInstance();

			Method method_Name = instanceOfRequiredClass.getClass().getMethod(methodName);

			method_Name.invoke(instanceOfRequiredClass);

		} catch (Exception e) {
			throw new Exception();
		}
	}

}
