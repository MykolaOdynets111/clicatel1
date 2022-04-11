import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BrowserImplementation {

    public static final int WAIT=10;
    private static String LINUX_SELENIUM_DOCKERS_URL = "http://selenium.clickatelllabs.com:4444/wd/hub";
//    "https://dev-shared-selenium-hub.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com/wd/hub";

    public WebDriver browserImplementation() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\chromedriver.exe");
//        WebDriver driver = new ChromeDriver(); //uncomment in case of local run
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--headless");
        RemoteWebDriver driver = new RemoteWebDriver(getUrl(), options);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(WAIT, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        return driver;
    }

    private URL getUrl(){
        try {
            return new URL(LINUX_SELENIUM_DOCKERS_URL) ;
        } catch (MalformedURLException e){}
        return null;
    }
}
