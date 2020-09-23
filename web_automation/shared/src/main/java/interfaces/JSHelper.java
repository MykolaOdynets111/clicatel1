package interfaces;

import org.openqa.selenium.*;
import org.testng.Assert;

import java.util.List;

public interface JSHelper extends WebActions{

    default void executeJSclick(WebDriver driver, WebElement elem) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
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

    default String readValueFromInput(WebElement elem, WebDriver driver){
        JavascriptExecutor exec = (JavascriptExecutor) driver;
        return (String) exec.executeScript("return arguments[0].value", elem);
    }

    /**
     * Positive offset scroll will scroll element to the bottom, negative - to the top.
     * @author tmytlovych
     */
    default void scrollInsideElement(WebElement elem, WebDriver driver, int offset) {
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        jsExec.executeScript("arguments[0].scrollTop = " + offset + "", elem);
    }


    default void setUpGeolocation(WebDriver driver, String latitude, String longitude) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("navigator.geolocation.getCurrentPosition = function(success, failure) { \n" +
                "    success({ coords: { \n" +
                "        latitude: "+latitude+", \n" +
                "        longitude: "+longitude+",\n" +
                "\n" +
                "    }, timestamp: Date.now() }); \n" +
                "} ");
    }

    default String getUserNameFromLocalStorage(WebDriver driver){
        if(driver!=null){
            try {
                JavascriptExecutor jsExec = (JavascriptExecutor) driver;
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

    default void scrollPageToTheBottom(WebDriver driver){
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    default void scrollToElem(WebDriver driver, String xpath, String elemName){
        try {
            WebElement element = driver.findElement(By.xpath(xpath));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        }catch(NoSuchElementException e){
            Assert.fail(elemName +" is not found");
        }
    }

    default boolean wheelScrollUpAndIsDisplayed(WebDriver driver, WebElement scrolArea, WebElement element){
        for (int i=0; i<4; i++)  {
            if(isElementShown(driver, element, 0)){
                wheelScroll(driver, scrolArea, 2000, 0,0);
                return true;
            }
            wheelScroll(driver, scrolArea, -200, 0,0);
        }
        return isElementShown(driver, element, 1);
    }

    default boolean wheelScrollDownAndIsDisplayed(WebDriver driver, WebElement scrolArea, WebElement element, int waitTime){
        wheelScroll(driver, scrolArea, 500, 0,0);
        return isElementShown(driver, element, waitTime);
    }

    default void wheelScroll(WebDriver driver, WebElement element, int deltaY, int offsetX, int offsetY){
        try{
            String script = "var element = arguments[0];"
                    +"var deltaY = arguments[1];"
                    +"var box = element.getBoundingClientRect();"
                    +"var clientX = box.left + (arguments[2] || box.width / 2);"
                    +"var clientY = box.top + (arguments[3] || box.height / 2);"
                    +"var target = element.ownerDocument.elementFromPoint(clientX, clientY);"
                    +"for (var e = target; e; e = e.parentElement) {"
                    +"if (e === element) {"
                    +"target.dispatchEvent(new MouseEvent('mouseover', {view: window, bubbles: true, cancelable: true, clientX: clientX, clientY: clientY}));"
                    +"target.dispatchEvent(new MouseEvent('mousemove', {view: window, bubbles: true, cancelable: true, clientX: clientX, clientY: clientY}));"
                    +"target.dispatchEvent(new WheelEvent('wheel',     {view: window, bubbles: true, cancelable: true, clientX: clientX, clientY: clientY, deltaY: deltaY}));"
                    +"return;"
                    +"}"
                    +"}";

            ((JavascriptExecutor) driver).executeScript(script, element, deltaY, offsetX, offsetY);
        }catch(WebDriverException e) {
            e.printStackTrace();
        }
    }

    default void scrollToElem(WebDriver driver, WebElement element, String elemName){
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        }catch(NoSuchElementException e){
            Assert.fail(elemName +" is not found");
        }
    }

    default void scrollAndClickElem(WebDriver driver, WebElement element, int wait, String elemName){
        try {
            waitForElementToBeVisible(driver, element, wait);
            scrollToElem(driver, element,elemName);
            waitForElementToBeClickable(driver, element, wait).click();
        } catch (TimeoutException|NoSuchElementException e){
            Assert.fail("Cannot click '" + elemName + "' because button is not clickable.");
        }
    }

    default void executeAngularClick(WebDriver driver, WebElement elem){
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        jsExec.executeScript("angular.element(arguments[0]).triggerHandler('click')", elem);
    }

    default void executeClickInElemListWithWait(WebDriver driver, List<WebElement> list, String item){
        for(int i = 0; i<10; i++){
            if(list.stream().anyMatch(e1 -> e1.getText().equalsIgnoreCase(item))){
                executeJSclick(list.stream().filter(e -> e.getText().equalsIgnoreCase(item)).findFirst()
                                .orElseThrow(() -> new AssertionError("Cannot click '" + item +"'")),
                       driver);
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
