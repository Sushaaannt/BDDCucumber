package stepdefination;

import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import generic.TestdataHandler;
import generic.WebElementHandler;
import helper.TestRunner;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.junit.CucumberOptions;
import org.openqa.selenium.*;

import generic.JsonHandler;
import io.cucumber.java.en.Given;

public class GenericSteps {

	WebDriver driver = null;

	public static String MethodName;
	WebElement element = null;

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
	public void user_enter_text_in_textbox(String identifier,String value){
		int count = 0;
		String val=null;
		while (count<2){
			try {
				element=WebElementHandler.getElement(identifier);
				val=element.getAttribute("value");
				if(!val.equals("")){
					element.clear();
				}
				element.sendKeys(value);
				break;
			}catch (StaleElementReferenceException e){
				System.out.println("Trying to recover from stale element exception...");
			} catch (Throwable e) {
                throw new RuntimeException(e);
            }
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
	@Before
	public void preExecution(Scenario scenario){
		String[] scenarioTags = scenario.getSourceTagNames().toArray(new String[]{});
		String[] filteredScenarioTags = getFilteredScenarioTags(scenarioTags);
		List<String> optionTags = getTags(TestRunner.class);
		String[] runningTag = getStringIntersection(scenarioTags, optionTags);

		List<String> tags = (List<String>) scenario.getSourceTagNames();
		if(filteredScenarioTags!=null){
			TestdataHandler.setTestId(filteredScenarioTags[0]);
		}else {
			TestdataHandler.setTestId(runningTag[0]);
		}

	}
	private String[] getTagsFromCMDParams(){
		String proptag = System.getProperty("cucumber.options");
		System.out.println("Getting tag From CMD Params : "+ proptag);
		if(proptag!=null && proptag.length()>0){
			Pattern p = Pattern.compile("--tags (@[^ ]+(,@[^ ]+)*)");
			Matcher m = p.matcher(proptag);
			boolean b = m.matches();
			if(b && m.groupCount() >=2){
				String test = m.group(1);
				if(test!=null && test.length()>0){
					String[] bits = test.split(",");
					if (bits.length>0){
						return bits;
					}
				}
			}
		}
        return new String[] {};
    }
	private List<String>getTagsFromAnnotations(Class<TestRunner>clazz){
		CucumberOptions co = clazz.getAnnotation(CucumberOptions.class);
		String tags = co.tags();
		List<String> runnerTags = new LinkedList<String>();
		String[] tagArray = tags.split(" or ");
		for(int i =0 ; i < tagArray.length; i++){
			System.out.println("cucumber option tags are : "+tags);
			if(tags.contains(" or ")){
				runnerTags.add(tagArray[i]);
			}else {
				runnerTags.add(tags);
			}
		}
        return runnerTags;
    }

	private List<String> getTags(Class<TestRunner> clazz) {
		String[] tags = this.getTagsFromCMDParams();
		if(tags.length>0){
			return Arrays.asList(tags);
		}
        return getTagsFromAnnotations(clazz);
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
}
