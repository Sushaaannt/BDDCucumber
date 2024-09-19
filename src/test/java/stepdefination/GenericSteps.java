package stepdefination;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.microsoft.playwright.Page;
import generic.TestdataHandler;
import generic.WebElementHandler;
import helper.TestRunner;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.junit.CucumberOptions;
import org.openqa.selenium.*;

import generic.JsonHandler;
import io.cucumber.java.en.Given;
import org.openqa.selenium.chrome.ChromeDriver;

public class GenericSteps {

	WebDriver driver = null;

	public String applicationName=null;
	public static String MethodName;
	WebElement element = null;

	final static Logger logger = Logger.getLogger(String.valueOf(GenericSteps.class));
	public static String ClassName;

	static GenericSteps genericSteps =null;
	public static GenericSteps getInstance(){
		if(genericSteps==null){
			genericSteps=new GenericSteps();
		}
		return genericSteps;
	}
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
	public void user_enter_text_in_textbox(String identifier,String value){
		int count = 0;
		String val;
		while (count<2){
			try {
				element=WebElementHandler.getElement(identifier);
				val=element.getAttribute("value");
				if(!val.isEmpty()){
					element.clear();
				}
				element.sendKeys(value);
				break;
			}catch (StaleElementReferenceException e){
				System.out.println("Trying to recover from stale element exception...");
			} catch (Throwable e) {
                throw new RuntimeException(e);
            }
			count++;
        }
	}
	public void user_clicks_on_the_button(String identifier) throws Throwable {
		element=WebElementHandler.getElement(identifier);
		if(element!=null && element.isEnabled()){
			try {
				element.click();
			}catch (StaleElementReferenceException e){
				System.out.println("Trying to recover from stale element exception...");
			}
		}
	}
	public void click_by_JS(String identifier) throws Throwable {
		element=WebElementHandler.getElement(identifier);
		if(element!=null && element.isEnabled()){
			try {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();",element);
			}catch (Exception e){
				System.out.println("Trying to recover from exception "+ e);
			}
		}
	}
	//@Before
	public void preExecution(Scenario scenario){
		String[] scenarioTags = scenario.getSourceTagNames().toArray(new String[]{});
		String[] filteredScenarioTags = getFilteredScenarioTags(scenarioTags);
		List<String> optionTags = getTags();
		//String[] runningTag = getStringIntersection(scenarioTags, optionTags);

		List<String> tags = (List<String>) scenario.getSourceTagNames();
		if(tags!=null && System.getenv("releaseEnvironment")==null){
            TestdataHandler.setTestId(filteredScenarioTags[0]);
        }else {
			if(System.getenv("releaseEnvironment")==null){
				TestdataHandler.setTestId("Default");
			}else {
				TestdataHandler.setTestId(System.getenv("releaseEnvironment"));
			}
		}
		TestdataHandler.setScenario(scenario);
		logger.info("current feature: "+ scenario.getId().split(";")[0]+" "+ "current scenario : "+ scenario.getName());
	}
	@After
	public void postExecution(Scenario scenario) throws IOException {
		if(applicationName.equalsIgnoreCase("Replatforming")){
			String tmpDir = System.getProperty("java.io.tmpdir");
			File folder = new File(tmpDir);
			File [] files = folder.listFiles();
            assert files != null;
            for(File file : files){
				if(file.getName().startsWith("MAE")){
					Files.deleteIfExists(file.toPath());
					logger.info("deleted file from temp directory : "+ file.getName());
				}
			}
		}
		if (scenario.isFailed()) {
			try {
				String scenarioName = "Failed_step_"+scenario.getName();
				String failedScreenshotPath="Execution Report path";
				if (applicationName.equalsIgnoreCase("replatforming")){
					PlaywrightGenericSteps.page.screenshot(new Page.ScreenshotOptions()
							.setPath(Paths.get(failedScreenshotPath + scenarioName + ".png")).setFullPage(true));
				}
				/*//Extent Report screenshots ==> Implements io.cucumber.plugin.currentEventListner
				ExtentCucumberAdapter.getCurrentStep().log(status.FAIL , MediaEntityBuilder
						.createScreenCaptureFromBase64String(getBase64Screenshot()).build());
				Reporter.addScreenCaptureFromPath(failedScreenshotPath+scenarioName+".png");*/

			}catch (Exception e){
				logger.info("Error occurred in Post Execution method"+e.getMessage());
			}
		}
	}
	private String[] getTagsFromCMDParams(){
		String proptag = System.getProperty("cucumber.options");
		System.out.println("Getting tag From CMD Params : "+ proptag);
		if(proptag!=null && !proptag.isEmpty()){
			Pattern p = Pattern.compile("--tags (@[^ ]+(,@[^ ]+)*)");
			Matcher m = p.matcher(proptag);
			boolean b = m.matches();
			if(b && m.groupCount() >=2){
				String test = m.group(1);
				if(test!=null && !test.isEmpty()){
					String[] bits = test.split(",");
					if (bits.length>0){
						return bits;
					}
				}
			}
		}
        return new String[] {};
    }
	private List<String>getTagsFromAnnotations(){
		CucumberOptions co = TestRunner.class.getAnnotation(CucumberOptions.class);
		String tags = co.tags();
		List<String> runnerTags = new LinkedList<>();
		String[] tagArray = tags.split(" or ");
        for (String s : tagArray) {
            System.out.println("cucumber option tags are : " + tags);
            if (tags.contains(" or ")) {
                runnerTags.add(s);
            } else {
                runnerTags.add(tags);
            }
        }
        return runnerTags;
    }

	private List<String> getTags() {
		String[] tags = this.getTagsFromCMDParams();
		if(tags.length>0){
			return Arrays.asList(tags);
		}
        return getTagsFromAnnotations();
    }
	private String[] getStringIntersection(String[]array1, List<String>array2){
		Set<String> s1 = new HashSet<>(Arrays.asList(array1));
		Set<String> s2=new HashSet<>(array2);
		s1.retainAll(s2);
		return s1.toArray(new String[0]);
	}

	private String[] getFilteredScenarioTags(String[] scenarioTags) {
		int newSize=0;
		for(String tag :scenarioTags){
			if(!tag.startsWith("@feature")){
				newSize++;
			}
		}
		String[] filteredScenarioTags = new String[newSize];
		int currentIndex=0;
		for(String tag :scenarioTags){
			if(!tag.startsWith("@feature")){
				filteredScenarioTags[currentIndex]=tag;
				currentIndex++;
			}
		}
        return filteredScenarioTags;
    }

	@Given("user navigates to <url> using chrome broser")
	public void userNavigatesToUrlUsingChromeBrowser(String url){
		System.setProperty("webdriver.chrome.driver",
				"/Users/sushaaannt/Eclipse/EclipseWorkspace/seleniumbased/drivers/chromedriver");
		driver = new ChromeDriver();
		driver.get(url);
	}
	@Given("user switches to application \"([^\"]*)\"$")
	public void users_witches_to_application(String application){
		applicationName=application;
		jsonh.setApplication(application);
	}

}
