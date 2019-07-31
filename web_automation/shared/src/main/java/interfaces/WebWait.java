package interfaces;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public interface WebWait {

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

    default void waitForElementToBeInVisibleByXpath(WebDriver driver, String xpath, int wait){
        initWait(driver, wait).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
    }

    default void waitForElementsToBeInvisibleByCss(WebDriver driver, String css, int wait){
        initWait(driver, wait).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(css)));
    }
}
