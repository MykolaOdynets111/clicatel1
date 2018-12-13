package interfaces;

import driverManager.DriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface JSHelper {

    default void executeJSclick(WebElement elem) {
        JavascriptExecutor executor = (JavascriptExecutor) DriverFactory.getTouchDriverInstance();
        executor.executeScript("arguments[0].click();", elem);
    }


    default void executeJSclick(WebElement elem, WebDriver driver) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", elem);
    }

    default void executeJSHover(WebElement element, WebDriver driver){
        String javaScript = "var evObj = document.createEvent('MouseEvents');" +
                "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
                "arguments[0].dispatchEvent(evObj);";
        ((JavascriptExecutor) driver).executeScript(javaScript, element);
    }

    /**
     * Positive offset scroll will scroll element to the bottom, negative - to the top.
     * @author tmytlovych
     */
    default void scrollInsideElement(WebElement elem, int offset) {
        JavascriptExecutor jsExec = (JavascriptExecutor) DriverFactory.getTouchDriverInstance();
        jsExec.executeScript("arguments[0].scrollTop = " + offset + "", elem);
    }


    default void setUpGeolocation(String latitude, String longitude) {
        JavascriptExecutor executor = (JavascriptExecutor) DriverFactory.getTouchDriverInstance();
        executor.executeScript("navigator.geolocation.getCurrentPosition = function(success, failure) { \n" +
                "    success({ coords: { \n" +
                "        latitude: "+latitude+", \n" +
                "        longitude: "+longitude+",\n" +
                "\n" +
                "    }, timestamp: Date.now() }); \n" +
                "} ");
    }

    default String getUserNameFromLocalStorage(){
        if(DriverFactory.isTouchDriverExists()){
            JavascriptExecutor jsExec = (JavascriptExecutor)  DriverFactory.getTouchDriverInstance();
            return (String) jsExec.executeScript("return window.localStorage.getItem('ctlUsername');");
        }else{
            return "";
        }

    }

    default void scrollPageToTheTop(WebDriver driver){
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollBy(0,-250)", "");
    }
}
