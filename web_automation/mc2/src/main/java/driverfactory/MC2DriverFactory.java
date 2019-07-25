package driverfactory;

import drivermanager.ConfigManager;
import drivermanager.DriverType;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class MC2DriverFactory {


    private static final ThreadLocal<WebDriver> portalDriver = new ThreadLocal<>();

    private static String WINDOWS_SERVER_REMOTE_EXTERNAL_URL = "http://35.164.148.100:4441/wd/hub";
    private static String WINDOWS_SERVER_REMOTE_URL = "http://172.31.29.139:4441/wd/hub";
    private static String LINUX_SELENIUM_DOCKERS_URL = "http://selenium.clickatelllabs.com:4444/wd/hub";
    private static String LINUX_SELENIUM_DOCKERS_URL_FB = "http://selenium.clickatelllabs.com:5900/wd/hub";
    private static String LINUX_SELENIUM_DOCKERS_URL_WITH_MONITOR = "http://selenium.clickatelllabs.com:4445/wd/hub";


    public static WebDriver getPortalDriver(){
        if (portalDriver.get() != null)
            return portalDriver.get();
        return startNewPortalDriverInstance();
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


    private static WebDriver createRemoteDriver(MutableCapabilities capabilities){
        try {
            RemoteWebDriver remoteWebDriver;
            remoteWebDriver = new RemoteWebDriver(new URL(LINUX_SELENIUM_DOCKERS_URL), capabilities);
//              debug remote run
//                remoteWebDriver = new RemoteWebDriver(new URL(LINUX_SELENIUM_DOCKERS_URL_WITH_MONITOR), capabilities);
            return remoteWebDriver;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closePortalBrowser(){
        if(portalDriver.get() != null) {
            portalDriver.get().quit();
        }
        portalDriver.remove();
    }

}
