package interfaces;

import drivermanager.DriverFactory;
import org.openqa.selenium.*;
import org.testng.Assert;

import java.util.List;

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
    default void scrollInsideElement(WebElement elem, WebDriver driver, int offset) {
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
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
            try {
                JavascriptExecutor jsExec = (JavascriptExecutor) DriverFactory.getTouchDriverInstance();
                String clientId = (String) jsExec.executeScript("return window.localStorage.getItem('ctlUsername');");
                jsExec.executeScript("console.log(window.localStorage.getItem('ctlUsername'))");
                return clientId;
            } catch(WebDriverException e){
                Assert.fail("Getting client_id from local storage was not successful \n" +
                        e.getMessage());
                return "";}
        }else{
            return "";
        }
    }

    default void scrollPageToTheTop(WebDriver driver){
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollBy(0,-250)", "");
    }

    default void scrollToElem(WebDriver driver, String xpath, String elemName){
        try {
            WebElement element = driver.findElement(By.xpath(xpath));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        }catch(NoSuchElementException e){
            Assert.assertTrue(false, elemName +" is not found");
        }
    }

    default void waitForAngularToBeReady(WebDriver driver){
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        boolean isReady = (boolean) jsExec.executeScript("return (window.angular !== undefined) && (angular.element(document.body).injector() !== undefined) && (angular.element(document.body).injector().get('$http').pendingRequests.length === 0);");
        for(int i=0; i<20; i++){
            if(isReady) break;
            else{
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isReady = (boolean) jsExec.executeScript("return (window.angular !== undefined) && (angular.element(document.body).injector() !== undefined) && (angular.element(document.body).injector().get('$http').pendingRequests.length === 0);");
            }
        }
    }

    default void executeAngularClick(WebDriver driver, WebElement elem){
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
//        jsExec.executeScript("angular.element(arguments[0]).click();", elem);
        jsExec.executeScript("angular.element(arguments[0]).triggerHandler('click')", elem);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    default void executeClickInElemListWithWait(List<WebElement> list, String item, String agent){
        for(int i = 0; i<10; i++){
            if(list.stream().anyMatch(e1 -> e1.getText().equalsIgnoreCase(item))){
                executeJSclick(list.stream().filter(e -> e.getText().equalsIgnoreCase(item)).findFirst().get(),
                        DriverFactory.getDriverForAgent(agent));
                break;
            }else {
                sleepFor(300);
            }
        }
    }

    default void sleepFor(int i){
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
