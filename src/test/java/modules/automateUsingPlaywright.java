package modules;

import org.testng.annotations.Test;
import stepdefination.PlaywrightGenericSteps;

public class automateUsingPlaywright {

    @Test
    public void sampleMethod2() throws Throwable {
        System.out.println("Method Worked");
        PlaywrightGenericSteps.launchBrowser();
        PlaywrightGenericSteps.userLaunchesApplicationUsing("https://www.google.com/");
    }
}
