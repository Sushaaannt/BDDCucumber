package stepdefination;

import com.microsoft.playwright.*;

import java.awt.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class PlaywrightGenericSteps {
    public static BrowserContext browserContext;
    public static Page page = null ;
    static int currentWindowCount;
    public static void launchBrowser(){
        String pathToChrome="/Users/sushaaannt/Eclipse/EclipseWorkspace/seleniumbased/drivers/chromedriver";
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        Map<String,String> env = new HashMap<>();
        env.put("PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD","1");
        Playwright.CreateOptions createOptions=new Playwright.CreateOptions();
        createOptions.setEnv(env);
        Playwright playwright = Playwright.create(createOptions);
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setExecutablePath(Paths.get(pathToChrome)).setChromiumSandbox(true));
        browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize((int) width,(int) height));
        page = browserContext.newPage();
    }

    public static void userLaunchesApplicationUsing(String url){
        page.navigate(url);
        currentWindowCount = browserContext.pages().size();
        System.out.println("current Window Count : "+currentWindowCount);
    }
}
