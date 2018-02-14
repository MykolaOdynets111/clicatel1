package driverManager;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<WebDriver> secondDriver = new ThreadLocal<>();


    public static WebDriver getInstance(){
        if (driver.get() != null) {
            return driver.get();
        } else {
            return startNewInstance();
        }
    }

    public static WebDriver getSecondDriverInstance(){
        if (secondDriver.get() != null)
            return secondDriver.get();
        return startNewSecondDriverInstance();
    }

    public static WebDriver startNewInstance(){
        DriverType driverType = ConfigManager.getDriverType();
        MutableCapabilities capabilities = driverType.getDesiredCapabilities();
        WebDriver cratedDriver;
        if (ConfigManager.isRemote()) {
            cratedDriver = startRemoteChrome(capabilities);
            driver.set(cratedDriver);
            return cratedDriver;
        }
        cratedDriver = driverType.getWebDriverObject(capabilities);
        driver.set(cratedDriver);
        return cratedDriver;
    }


    public static WebDriver startNewSecondDriverInstance(){
        DriverType driverType = ConfigManager.getDriverType();
        MutableCapabilities capabilities = driverType.getDesiredCapabilities();
        WebDriver cratedDriver;
        if (ConfigManager.isRemote()) {
            cratedDriver = startRemoteChrome(capabilities);
            secondDriver.set(cratedDriver);
            return cratedDriver;
        }
        cratedDriver = driverType.getWebDriverObject(capabilities);
        secondDriver.set(cratedDriver);
        return cratedDriver;
    }

    public static void startBrowser() {
        DriverFactory.getInstance().get(URLs.getURL());
    }

    private static WebDriver startRemoteChrome(MutableCapabilities capabilities){
        try {
            return new RemoteWebDriver(new URL("http://172.31.29.139:4441/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
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
