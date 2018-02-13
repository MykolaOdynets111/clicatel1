package driverManager;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;


public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<WebDriver> secondDriver = new ThreadLocal<>();


    public static WebDriver getInstance(){
        if (driver.get() != null)
            return driver.get();
        return startNewInstance();
    }

    public static WebDriver getSecondDriverInstance(){
        if (secondDriver.get() != null)
            return secondDriver.get();
        return startNewSecondDriverInstance();
    }

    public static WebDriver startNewInstance(){
        DriverType driverType = ConfigManager.getDriverType();
        MutableCapabilities capabilities = driverType.getDesiredCapabilities();
//        if (IS_REMOTE)
//            return startRemoteDriver(capabilities);
        WebDriver cratedDriver = driverType.getWebDriverObject(capabilities);
        driver.set(cratedDriver);
        return cratedDriver;
    }


    public static WebDriver startNewSecondDriverInstance(){
        DriverType driverType = ConfigManager.getDriverType();
        MutableCapabilities capabilities = driverType.getDesiredCapabilities();
//        if (IS_REMOTE)
//            return startRemoteDriver(capabilities);
        WebDriver cratedDriver = driverType.getWebDriverObject(capabilities);
        secondDriver.set(cratedDriver);
        return cratedDriver;
    }

    public static void startBrowser() {
        DriverFactory.getInstance().get(URLs.getURL());
    }

    private static void startRemoteChrome(){
//        container.get().getContainerInfo();
//        container.get().start();
//        driver.set(container.get().getWebDriver());
    }

    public static void closeBrowser(){
        if(driver.get() != null) {
            driver.get().quit();
        }
        driver.set(null);
    }


    public static void closeSecondBrowser(){
        if(secondDriver.get() != null) {
            secondDriver.get().quit();
        }
        secondDriver.set(null);
    }
}
