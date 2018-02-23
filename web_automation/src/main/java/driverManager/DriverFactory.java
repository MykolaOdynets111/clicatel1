package driverManager;

import com.github.javafaker.Faker;
import interfaces.JSHelper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final ThreadLocal<WebDriver> secondDriver = new ThreadLocal<>();


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
            cratedDriver = createRemoteDriver(capabilities);
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
            cratedDriver = createRemoteDriver(capabilities);
            secondDriver.set(cratedDriver);
            return cratedDriver;
        }
        cratedDriver = driverType.getWebDriverObject(capabilities);
        secondDriver.set(cratedDriver);
        return cratedDriver;
    }

    public static void openUrl() {
        DriverFactory.getInstance().get(URLs.getURL());
//        if(ConfigManager.getEnv().equalsIgnoreCase("dev")){
            Faker faker=new Faker();
            JavascriptExecutor jsExec = (JavascriptExecutor)  DriverFactory.getInstance();
            jsExec.executeScript("window.localStorage.setItem('ctlUsername', 'testing_"+faker.code().ean8()+"');");
//        }
    }

    private static WebDriver createRemoteDriver(MutableCapabilities capabilities){
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
