package interfaces;

import driverManager.DriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public interface JSHelper {

    default void executeJSclick(WebElement elem) {
        JavascriptExecutor executor = (JavascriptExecutor) DriverFactory.getInstance();
        executor.executeScript("arguments[0].click();", elem);
    }

    /**
     * Positive offset scroll will scroll element to the bottom, negative - to the top.
     * @author tmytlovych
     */
    default void scrollInsideElement(WebElement elem, int offset) {
        JavascriptExecutor jsExec = (JavascriptExecutor) DriverFactory.getInstance();
        jsExec.executeScript("arguments[0].scrollTop = " + offset + "", elem);
    }


    default void setUpGeolocation(String latitude, String longitude) {
        JavascriptExecutor executor = (JavascriptExecutor) DriverFactory.getInstance();
        executor.executeScript("navigator.geolocation.getCurrentPosition = function(success, failure) { \n" +
                "    success({ coords: { \n" +
                "        latitude: "+latitude+", \n" +
                "        longitude: "+longitude+",\n" +
                "\n" +
                "    }, timestamp: Date.now() }); \n" +
                "} ");
    }

    default String getUserNameFromLocalStorage(){
        JavascriptExecutor jsExec = (JavascriptExecutor)  DriverFactory.getInstance();
        return (String) jsExec.executeScript("return window.localStorage.getItem('ctlUsername');");
    }
}
