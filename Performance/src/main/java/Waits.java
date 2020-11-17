import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public interface Waits {

    default WebDriverWait initWait(WebDriver driver, int waitTime){
        return new WebDriverWait(driver, waitTime);
    }

    default void wait(int time){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    default WebElement waitForElementToBeClickable(WebDriver driver, WebElement element, int wait){
        return initWait(driver, wait).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    default WebElement waitForElementToBeVisible(WebDriver driver, WebElement element, int wait){
        return initWait(driver, wait).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOf(element));
    }

    default void waitForElementToBeInvisibleByXpath(WebDriver driver, String xpath, int wait){
        initWait(driver, wait).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
    }

    default List<WebElement> waitForElementsToBeVisibleByXpath(WebDriver driver, String xpath, int wait){
        return initWait(driver, wait).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
    }

    default WebElement waitForElementToBePresentByXpath(WebDriver driver, String xpath, int wait){
        return initWait(driver, wait).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    default WebElement waitForElementToBeVisibleByXpath(WebDriver driver, String xpath, int wait){
        return initWait(driver, wait).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
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
        wait.pollingEvery(Duration.ofMillis(100)).until(elementIsDisplayed);
    }

    default boolean isElementShown(WebDriver driver, WebElement element, int wait){
        try {
            return waitForElementToBeVisible(driver, element, wait).isDisplayed();
        } catch (TimeoutException|NoSuchElementException e) {
            return false;
        }
    }

    default boolean isElementShownByXpath(WebDriver driver, String xpath, int wait){
        try {
            waitForElementToBeVisibleByXpath(driver, xpath, wait);
            return true;
        } catch (TimeoutException|NoSuchElementException e) {
            return false;
        }
    }

}
