package driverManager;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> touchDriver = new ThreadLocal<>();
    private static final ThreadLocal<WebDriver> agentDriver = new ThreadLocal<>();
    private static final ThreadLocal<WebDriver> portalDriver = new ThreadLocal<>();
    private static final ThreadLocal<WebDriver> secondAgentDriver = new ThreadLocal<>();

    public static boolean isAgentDriverExists(){
        if (agentDriver.get() != null)
            return true;
        else
            return false;
    }

    public static boolean isSecondAgentDriverExists(){
        if (secondAgentDriver.get() != null)
            return true;
        else
            return false;
    }

    public static boolean isTouchDriverExists(){
        if (touchDriver.get() != null)
            return true;
        else
            return false;
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

    public static void openUrl() {
        DriverFactory.getTouchDriverInstance().get(URLs.getURL());
            JavascriptExecutor jsExec = (JavascriptExecutor)  DriverFactory.getTouchDriverInstance();
            jsExec.executeScript("window.localStorage.setItem('ctlUsername', 'testing_"+(int)(Math.random()*(2000-1)+1)
                    +System.currentTimeMillis()+"');");
    }

    public static void openTouchUrlWithPredifinedUserID(String ctlUsername) {
        DriverFactory.getTouchDriverInstance().get(URLs.getURL());
        JavascriptExecutor jsExec = (JavascriptExecutor)  DriverFactory.getTouchDriverInstance();
        jsExec.executeScript("window.localStorage.setItem('ctlUsername', '"+ctlUsername+"');");
    }


    private static WebDriver createRemoteDriver(MutableCapabilities capabilities){
        try {
//            return new RemoteWebDriver(new URL("http://172.31.29.139:4441/wd/hub"), capabilities);
            return new RemoteWebDriver(new URL("http://35.164.148.100:4441/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeTouchBrowser(){
        if(touchDriver.get() != null) {
            touchDriver.get().quit();
        }
        touchDriver.set(null);
    }


    public static void closeAgentBrowser(){
        if(agentDriver.get() != null) {
            agentDriver.get().quit();
        }
        agentDriver.set(null);
    }

    public static void closePortalBrowser(){
        if(portalDriver.get() != null) {
            portalDriver.get().quit();
        }
        portalDriver.set(null);
    }

    public static void closeSecondAgentBrowser(){
        if(secondAgentDriver.get() != null) {
            secondAgentDriver.get().quit();
        }
        secondAgentDriver.set(null);
    }
}
