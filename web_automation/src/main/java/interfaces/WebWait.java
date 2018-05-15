package interfaces;

import driverManager.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public interface WebWait {

    default WebDriverWait initWait(){
        return new WebDriverWait(DriverFactory.getInstance(), 30);
    }

    default WebDriverWait initWait(int waitTime){
        return new WebDriverWait(DriverFactory.getInstance(), waitTime);
    }

    default WebDriverWait initAgentWait(int waitTime){
        return new WebDriverWait(DriverFactory.getSecondDriverInstance(), waitTime);
    }

    default WebElement waitForElementToBeClickable(WebElement element){
        return initWait().ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    default WebElement waitForElementToBeVisible(WebElement element){
        return initWait()
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOf(element));
    }

    default WebElement waitForElementToBeVisible(WebElement element, int time){
        return initWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOf(element));
    }

    default List<WebElement> waitForElementsToBeClickable(List<WebElement> elements){
        WebDriverWait wait = initWait();
        for(WebElement element: elements) {
            wait.ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.elementToBeClickable(element));
        }
        return elements;
        }

    default List<WebElement> waitForElementsToBeVisible(List<WebElement> elements, int wait){
            return initWait(wait).ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.visibilityOfAllElements(elements));
    }


    default void waitForElementToBeInvisible(WebElement element, int time){
        initWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.invisibilityOf(element));
    }

    default void waitForElementToBeVisibleByXpathAgent(String xpath, int time){
        initAgentWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    default void waitForElementsToBeVisibleByXpathAgent(String xpath, int time){
        initAgentWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
    }

    default void waitForElementToBeVisibleByXpath(String xpath, int time){
        initWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    default void waitForElementToBeInvisibleWithNoSuchElementException(WebElement element, int time){
        initWait(time).until(ExpectedConditions.invisibilityOf(element)).toString();
    }

}
