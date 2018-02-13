package driverManager;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public enum DriverType {

    CHROME {
        private final List<String> knownNames = Arrays.asList("chrome", "googlechrome", "google_chrome");

        @Override
		public MutableCapabilities getDesiredCapabilities() {
            ChromeOptions options = new ChromeOptions();
//        Map<String, Object> prefs = new LinkedHashMap<>();
//        prefs.put("credentials_enable_service", Boolean.valueOf(false));
//        prefs.put("profile.password_manager_enabled", Boolean.valueOf(false));
//        prefs.put("xdg-open", Boolean.valueOf(false));
//        //    prefs.put("profile.default_content_settings.popups", 0);
//        //     prefs.put("download.default_directory", pathToExtract);
//        //    prefs.put("browser.download.folderList", 2);
//        //    prefs.put("browser.download.manager.showWhenStarting", false);
//        //   options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
//        options.setExperimentalOption("prefs", prefs);
            options.addArguments("test-type");
            options.addArguments("start-maximized");
            options.addArguments("disable-web-security");
            options.addArguments("disable-gpu");
            options.addArguments("disable-webgl");
            options.addArguments("disable-infobars");
            options.addArguments("no-proxy-server");
            options.addArguments("no-sandbox");
			return options;
		}


    public WebDriver getWebDriverObject(MutableCapabilities capabilities) {
            ChromeDriverManager.getInstance().setup();

            WebDriver localDriver = new ChromeDriver((ChromeOptions) capabilities);
            DriverType.setDimension(localDriver);
            localDriver.manage().window().maximize();
            return localDriver;
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

