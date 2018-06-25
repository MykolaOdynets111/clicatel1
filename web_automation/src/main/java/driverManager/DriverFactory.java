package driverManager;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final ThreadLocal<WebDriver> secondDriver = new ThreadLocal<>();
    private static final ThreadLocal<WebDriver> portalDriver = new ThreadLocal<>();

    public static WebDriver getInstance(){
        if (driver.get() != null) {
            return driver.get();
        } else {
            return startNewInstance();
        }
    }

    public static boolean isSecondDriverExists(){
        if (secondDriver.get() != null)
            return true;
        else
            return false;
    }

    public static boolean isDriverExists(){
        if (driver.get() != null)
            return true;
        else
            return false;
    }

    public static WebDriver getSecondDriverInstance(){
        if (secondDriver.get() != null)
            return secondDriver.get();
        return startNewSecondDriverInstance();
    }

    public static WebDriver getPortalDriverInstance(){
        if (portalDriver.get() != null)
            return portalDriver.get();
        return startNewPortalDriverInstance();
    }

    public static WebDriver startNewInstance(){
        DriverType driverType = ConfigManager.getDriverType();
        MutableCapabilities capabilities = driverType.getDesiredCapabilities();
        if (ConfigManager.isRemote()) {
            driver.set(createRemoteDriver(capabilities));
            return driver.get();
        }
        driver.set(driverType.getWebDriverObject(capabilities));
        return  driver.get();
    }


    public static WebDriver startNewSecondDriverInstance(){
        DriverType driverType = ConfigManager.getDriverType();
        MutableCapabilities capabilities = driverType.getDesiredCapabilities();
        if (ConfigManager.isRemote()) {
            secondDriver.set(createRemoteDriver(capabilities));
            return secondDriver.get();
        }
        secondDriver.set(driverType.getWebDriverObject(capabilities));
        return secondDriver.get();
    }

    public static WebDriver startNewPortalDriverInstance(){
        DriverType driverType = ConfigManager.getDriverType();
        MutableCapabilities capabilities = driverType.getDesiredCapabilities();
        if (ConfigManager.isRemote()) {
            portalDriver.set(createRemoteDriver(capabilities));
            return portalDriver.get();
        }
        portalDriver.set(driverType.getWebDriverObject(capabilities));
        return portalDriver.get();
    }

    public static void openUrl() {
        DriverFactory.getInstance().get(URLs.getURL());
            JavascriptExecutor jsExec = (JavascriptExecutor)  DriverFactory.getInstance();
            jsExec.executeScript("window.localStorage.setItem('ctlUsername', 'testing_"+(int)(Math.random()*(2000-1)+1)
                    +System.currentTimeMillis()+"');");
    }



    private static WebDriver createRemoteDriver(MutableCapabilities capabilities){
        try {
            return new RemoteWebDriver(new URL("http://172.31.29.139:4441/wd/hub"), capabilities);
//            return new RemoteWebDriver(new URL("http://35.164.148.100:4441/wd/hub"), capabilities);
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

    public static void closePortalBrowser(){
        if(portalDriver.get() != null) {
            portalDriver.get().quit();
        }
        portalDriver.set(null);
    }
}
