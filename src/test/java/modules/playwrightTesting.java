package modules;

import stepdefination.PlaywrightGenericSteps;

public class playwrightTesting {

    public void sampleMethod() throws Throwable {
        System.out.println("Method Worked");
        PlaywrightGenericSteps.launchBrowser();
        PlaywrightGenericSteps.userLaunchesApplicationUsing("https://www.google.com/");
    }
}
