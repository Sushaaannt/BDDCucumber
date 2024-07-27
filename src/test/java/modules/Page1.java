package modules;

import generic.WebElementHandler;

public class Page1 {
	
	public void sampleMethod() throws Throwable {
		System.out.println("Method Worked");
		WebElementHandler.launch_browser();
		WebElementHandler.close_browser();
	}
}
