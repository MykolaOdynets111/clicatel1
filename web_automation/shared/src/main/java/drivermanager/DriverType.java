package drivermanager;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public enum DriverType {

    CHROME {
        private final List<String> knownNames = Arrays.asList("chrome", "googlechrome", "google_chrome");

        @Override
		public MutableCapabilities getDesiredCapabilities() {
            LoggingPreferences logPrefs = new LoggingPreferences();
            logPrefs.enable(LogType.BROWSER, Level.ALL);
            logPrefs.enable(LogType.PERFORMANCE, Level.ALL);

            ChromeOptions options = new ChromeOptions();
            options.setCapability("profile.default_content_setting_values.cookies", 1);
            options.setCapability("profile.block_third_party_cookies", false);

            options.setCapability(CapabilityType.SUPPORTS_APPLICATION_CACHE, false);
            if(ConfigManager.isRemote())options.addArguments("window-size=1360,1020");
            options.setCapability("goog:loggingPrefs", logPrefs);
            options.addArguments("disable-site-isolation-trials");
            options.addArguments("test-type=browser");
            options.addArguments("start-maximized");
            options.addArguments("disable-web-security");
            options.addArguments("disable-gpu");
            options.addArguments("disable-webgl");
            options.addArguments("disable-infobars");
            options.addArguments("no-proxy-server");
            options.addArguments("no-sandbox");
            options.addArguments("disable-notifications");
            options.addArguments("incognito");
            return options;
		}


    public WebDriver getWebDriverObject(MutableCapabilities capabilities) {
            String hostName = getHostName();
            if (!hostName.contains("FANB0604") & !ConfigManager.isRemote()) {
                ChromeDriverManager.getInstance().version("72.0.3626").setup();
                return new ChromeDriver((ChromeOptions) capabilities);
            }
            if (hostName.equals("FANB0604")) {
                ChromeDriverManager.getInstance().version("75").setup();
                return new ChromeDriver((ChromeOptions) capabilities);
            }
            ChromeDriverManager.getInstance().setup();
            return new ChromeDriver((ChromeOptions) capabilities);
        }

        @Override
        protected boolean isKnownAs(String name) {
            return knownNames.contains(name);
        }
    },

    HEADLESS_CHROME {
        private final List<String> knownNames = Arrays.asList("headlesschrome", "chromeheadless",
                "chrome_headless", "headless_chrome");

        @Override
        public MutableCapabilities getDesiredCapabilities() {
            LoggingPreferences logPrefs = new LoggingPreferences();
            logPrefs.enable(LogType.BROWSER, Level.ALL);

            ChromeOptions options = new ChromeOptions();
            options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-extensions");
            options.setExperimentalOption("useAutomationExtension", false);
            options.addArguments("--proxy-server='direct://'");
            options.addArguments("--proxy-bypass-list=*");
            options.addArguments("--start-maximized");
            options.addArguments("--headless");
            return options;
        }


        public WebDriver getWebDriverObject(MutableCapabilities capabilities) {
            ChromeDriverManager.getInstance().setup();
            return new ChromeDriver((ChromeOptions) capabilities);
        }

        @Override
        protected boolean isKnownAs(String name) {
            return knownNames.contains(name);
        }
    }
    ;

    public static DriverType from(String browser) {
        if (browser != null) {
            String trimmed = browser.trim().toLowerCase();
            for (DriverType candidate : DriverType.values()) {
                if (candidate.isKnownAs(trimmed))
                    return candidate;
            }
        }
        throw new IllegalArgumentException("\nUnsupported browser: " + browser + "\n");
    }

    protected abstract boolean isKnownAs(String name);
    public abstract MutableCapabilities getDesiredCapabilities();
    public abstract WebDriver getWebDriverObject(MutableCapabilities capabilities);



    private static void setDimension(WebDriver driver) {
        int width = (int) (long) ((JavascriptExecutor) driver).executeScript("return screen.width;");
        int height = (int) (long) ((JavascriptExecutor) driver).executeScript("return screen.height;");
        driver.manage().window().setSize(new Dimension(width, height));
    }

    public String getHostName(){
        String hostName = "";

//        hostName = System.getenv("COMPUTERNAME");
//        if(hostName==null) {
            try {
                hostName = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
//        }
    return hostName;
    }
}

