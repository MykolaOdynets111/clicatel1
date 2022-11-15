package driverfactory;

import drivermanager.ConfigManager;
import drivermanager.DriverType;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;

public class UnityDriverFactory {

    private static final ThreadLocal<WebDriver> unityDriver = new ThreadLocal<>();

    public static boolean isUnityDriverExists(){
        return unityDriver.get() != null;
    }

    public static WebDriver getUnityDriverInstance(){
        if (unityDriver.get() != null) {
            return unityDriver.get();
        } else {
            return startNewUnityInstance();
        }
    }

    public static synchronized WebDriver startNewUnityInstance(){
        DriverType driverType = ConfigManager.getDriverType();

        MutableCapabilities capabilities = driverType.getDesiredCapabilities();
        unityDriver.set(driverType.getWebDriverObject(capabilities));
        return  unityDriver.get();
    }

    public static void closeUnityBrowser(){
        if(unityDriver.get() != null) {
            unityDriver.get().quit();
        }
        unityDriver.remove();
    }
}
