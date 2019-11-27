package interfaces;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface WebWait {

    String processingAlert = "div.loader-bar-text";

    default void waitFor(int milisecs){
        try {
            Thread.sleep(milisecs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    default WebDriverWait initWait(WebDriver driver, int waitTime){
        return new WebDriverWait(driver, waitTime);
    }

    // ================================ Elements to be Clickable  ======================================= //

    default WebElement waitForElementToBeClickable(WebDriver driver, WebElement element, int wait){
        return initWait(driver, wait).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    default List<WebElement> waitForElementsToBeClickable(WebDriver driver, List<WebElement> elements, int wait){
        WebDriverWait waiter = initWait(driver, wait);
        for(WebElement element: elements) {
            waiter.ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.elementToBeClickable(element));
        }
        return elements;
    }

    default void waitForElementToBeClickableByCss(WebDriver driver, String css, int time){
        initWait(driver, time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(css)));
    }

    default void waitForElementToBeClickableByXpath(WebDriver driver, String xpath, int time){
        initWait(driver, time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    // ================================== Elements to be Visible  ======================================== //


    default WebElement waitForElementToBeVisible(WebDriver driver, WebElement element, int wait){
        return initWait(driver, wait).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOf(element));
    }

    default List<WebElement> waitForElementsToBeVisible(WebDriver driver, List<WebElement> elements, int wait){
        return initWait(driver, wait).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    default List<WebElement> waitForElementsToBeVisibleByXpath(WebDriver driver, String xpath, int wait){
        return initWait(driver, wait).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
    }

    default List<WebElement> waitForElementsToBeVisibleByCss(WebDriver driver, String css, int wait){
        return initWait(driver, wait).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(css)));
    }

    default WebElement waitForElementToBeVisibleByXpath(WebDriver driver, String xpath, int wait){
        return initWait(driver, wait).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    default WebElement waitForElementToBeVisibleByCss(WebDriver driver, String css, int wait){
        return initWait(driver, wait).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css)));
    }


    // ================================== Elements to be Present  ======================================== //


    default List<WebElement> waitForElementsToBePresentByXpath(WebDriver driver, String xpath, int wait){
        return initWait(driver, wait).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));
    }

    default WebElement waitForElementToBePresentByCss(WebDriver driver, String css, int wait){
        return initWait(driver, wait).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(css)));
    }

    default WebElement waitForElementToBePresentByXpath(WebDriver driver, String xpath, int wait){
        return initWait(driver, wait).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    // ================================== Elements to be Invisible  ======================================== //


    default void waitForElementToBeInVisibleByCss(WebDriver driver, String css, int wait){
        initWait(driver, wait).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(css)));
    }

    default void waitForElementToBeInvisible(WebDriver driver, WebElement element, int wait){
        initWait(driver, wait).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.invisibilityOf(element));
    }

    default void waitUntilElementNotDisplayed(WebDriver driver, WebElement webElement, int waitTime) {
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        ExpectedCondition elementIsDisplayed = (ExpectedCondition<Boolean>) arg0 -> {
            try {
                webElement.isDisplayed();
                return false;
            }
            catch (NoSuchElementException e ) {
                return true;
            }
            catch (StaleElementReferenceException f) {
                return true;
            }
        };
        wait.until(elementIsDisplayed);
    }

    default void waitForElementToBeInvisibleByXpath(WebDriver driver, String xpath, int wait){
        initWait(driver, wait).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
    }

    default void waitForElementsToBeInvisibleByCss(WebDriver driver, String css, int wait){
        initWait(driver, wait).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(css)));
    }

    // ================================== Stabilization waits  ======================================== //

    default void waitForAngularRequestsToFinish(WebDriver driver){
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        new NgWebDriver(jsExec).waitForAngularRequestsToFinish();

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

    default void waitWhileProcessing(WebDriver driver, int toAppears, int toDisappear){
        try {
            waitForElementToBeVisibleByCss(driver, processingAlert, toAppears);
            waitForElementToBeInVisibleByCss(driver, processingAlert, toDisappear);
        } catch(NoSuchElementException|TimeoutException e){}
    }
}
