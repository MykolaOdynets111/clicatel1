package driverManager;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;

public enum DriverType {

    CHROME {
        private final List<String> knownNames = Arrays.asList("chrome", "googlechrome", "google_chrome");

        @Override
		public MutableCapabilities getDesiredCapabilities() {
            LoggingPreferences logPrefs = new LoggingPreferences();
            logPrefs.enable(LogType.BROWSER, Level.ALL);

            ChromeOptions options = new ChromeOptions();
            options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
            options.addArguments("test-type");
            options.addArguments("start-maximized");
            options.addArguments("disable-web-security");
            options.addArguments("disable-gpu");
            options.addArguments("disable-webgl");
            options.addArguments("disable-infobars");
            options.addArguments("no-proxy-server");
            options.addArguments("no-sandbox");
            options.addArguments("disable-notifications");
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
            options.addArguments("--window-size=1024,768");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-extensions");
            options.setExperimentalOption("useAutomationExtension", false);
            options.addArguments("--proxy-server='direct://'");
            options.addArguments("--proxy-bypass-list=*");
            options.addArguments("--start-maximized");
            options.addArguments("--headless");
//            options.addArguments("--screenshot=out.png"); - Taking screen shoots crashes the webdriver, but not sure if we need it
//            options.addArguments("--no-sandbox");
//            options.addArguments("--disable-dev-shm-usage");
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
}

