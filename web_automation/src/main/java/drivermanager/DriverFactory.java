package drivermanager;

import com.github.javafaker.Faker;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> touchDriver = new ThreadLocal<>();
    private static final ThreadLocal<WebDriver> agentDriver = new ThreadLocal<>();
    private static final ThreadLocal<WebDriver> portalDriver = new ThreadLocal<>();
    private static final ThreadLocal<WebDriver> secondAgentDriver = new ThreadLocal<>();

    private static String WINDOWS_SERVER_REMOTE_EXTERNAL_URL = "http://35.164.148.100:4441/wd/hub";
    private static String WINDOWS_SERVER_REMOTE_URL = "http://172.31.29.139:4441/wd/hub";
    private static String LINUX_SELENIUM_DOCKERS_URL = "http://selenium.clickatelllabs.com:4444/wd/hub";
    private static String LINUX_SELENIUM_DOCKERS_URL_FB = "http://selenium.clickatelllabs.com:5900/wd/hub";
    private static String LINUX_SELENIUM_DOCKERS_URL_WITH_MONITOR = "http://172.31.70.236:4445/wd/hub";


    public static boolean isAgentDriverExists(){
        return agentDriver.get() != null;
    }

    public static boolean isSecondAgentDriverExists(){
        return secondAgentDriver.get() != null;
    }

    public static boolean isTouchDriverExists(){
        return touchDriver.get() != null;
    }

    public static WebDriver getTouchDriverInstance(){
        if (touchDriver.get() != null) {
            return touchDriver.get();
        } else {
            return startNewTouchInstance();
        }
    }

    public static WebDriver getAgentDriverInstance(){
        if (agentDriver.get() != null)
            return agentDriver.get();
        return startNewAgentDriverInstance();
    }

    public static WebDriver getSecondAgentDriverInstance(){
        if (secondAgentDriver.get() != null)
            return secondAgentDriver.get();
        return startNewSecondAgentDriverInstance();
    }

    public static  WebDriver getDriverForAgent(String agent){
        System.out.println("!! inside getDriverForAgent \n");
        if (agent.equalsIgnoreCase("second agent")){
            return DriverFactory.getSecondAgentDriverInstance();
        } else {
            return DriverFactory.getAgentDriverInstance();
        }
    }

    public static WebDriver getPortalDriverInstance(){
        if (portalDriver.get() != null)
            return portalDriver.get();
        return startNewPortalDriverInstance();
    }

    public static WebDriver startNewTouchInstance(){
        DriverType driverType = ConfigManager.getDriverType();
        MutableCapabilities capabilities = driverType.getDesiredCapabilities();
        if (ConfigManager.isRemote()) {
            touchDriver.set(createRemoteDriver(capabilities));
            return touchDriver.get();
        }
        touchDriver.set(driverType.getWebDriverObject(capabilities));
        return  touchDriver.get();
    }


    public static WebDriver startNewAgentDriverInstance(){
        System.out.println("!!?? INSIDE startNewAgentDriverInstance");
        DriverType driverType = ConfigManager.getDriverType();
        MutableCapabilities capabilities = driverType.getDesiredCapabilities();
        if (ConfigManager.isRemote()) {
            agentDriver.set(createRemoteDriver(capabilities));
            return agentDriver.get();
        }
        agentDriver.set(driverType.getWebDriverObject(capabilities));
        return agentDriver.get();
    }

    public static WebDriver startNewSecondAgentDriverInstance(){
        DriverType driverType = ConfigManager.getDriverType();
        MutableCapabilities capabilities = driverType.getDesiredCapabilities();
        if (ConfigManager.isRemote()) {
            secondAgentDriver.set(createRemoteDriver(capabilities));
            return secondAgentDriver.get();
        }
        secondAgentDriver.set(driverType.getWebDriverObject(capabilities));
        return secondAgentDriver.get();
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

    public static void openUrl(String tenantOrgName) {
        DriverFactory.getTouchDriverInstance().get(URLs.getWidgetURL(tenantOrgName));
            JavascriptExecutor jsExec = (JavascriptExecutor)  DriverFactory.getTouchDriverInstance();
        Random r = new Random( System.currentTimeMillis() );
        Faker f = new Faker();
        jsExec.executeScript("window.localStorage.setItem('ctlUsername', 'testing_"+
                ((1 + r.nextInt(2)) * 1000000 + r.nextInt(1000000))+ f.lorem().character()+f.lorem().character()+"');");
    }

    public static void openUrlForDynamicTenant() {
        DriverFactory.getTouchDriverInstance().get(URLs.getWidgetURLForDynamicTenant());
    }

    public static void openTouchUrlWithPredifinedUserID(String tenantorgName, String ctlUsername) {
        DriverFactory.getTouchDriverInstance().get(URLs.getWidgetURL(tenantorgName));
        JavascriptExecutor jsExec = (JavascriptExecutor)  DriverFactory.getTouchDriverInstance();
        jsExec.executeScript("window.localStorage.setItem('ctlUsername', '"+ctlUsername+"');");
    }


    private static WebDriver createRemoteDriver(MutableCapabilities capabilities){
        try {
            RemoteWebDriver remoteWebDriver;
            if(ConfigManager.getSuite().equalsIgnoreCase("touchgo")){
                remoteWebDriver = new RemoteWebDriver(new URL(WINDOWS_SERVER_REMOTE_URL), capabilities);
            }else {
                remoteWebDriver = new RemoteWebDriver(new URL(LINUX_SELENIUM_DOCKERS_URL), capabilities);
            }
            return remoteWebDriver;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeTouchBrowser(){
        if(touchDriver.get() != null) {
            touchDriver.get().quit();
        }
        touchDriver.remove();
    }


    public static void closeAgentBrowser(){
        if(agentDriver.get() != null) {
            agentDriver.get().quit();
        }
        agentDriver.remove();
    }

    public static void closePortalBrowser(){
        if(portalDriver.get() != null) {
            portalDriver.get().quit();
        }
        portalDriver.remove();
    }

    public static void closeSecondAgentBrowser(){
        if(secondAgentDriver.get() != null) {
            secondAgentDriver.get().quit();
        }
        secondAgentDriver.remove();
    }

    public static void closeBrowser(String agent){
        if (agent.equalsIgnoreCase("second agent")){
            closeSecondAgentBrowser();
        } else {
            closeAgentBrowser();
        }
    }
}
