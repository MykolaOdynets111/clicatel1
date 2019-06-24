package interfaces;

import drivermanager.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public interface WebWait {

    default WebDriverWait initWait(int waitTime){
        return new WebDriverWait(DriverFactory.getTouchDriverInstance(), waitTime);
    }

    default WebDriverWait initAgentWait(int waitTime){
        return new WebDriverWait(DriverFactory.getAgentDriverInstance(), waitTime);
    }

    default WebDriverWait  initAgentWait(int waitTime, String ordinalAgentNumber){
        if (ordinalAgentNumber.equalsIgnoreCase("second agent")) return new WebDriverWait(DriverFactory.getSecondAgentDriverInstance(), waitTime);
        else return new WebDriverWait(DriverFactory.getAgentDriverInstance(), waitTime);
    }

    default WebElement waitForElementToBeClickable(WebElement element){
        return initWait(5).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    default WebElement waitForElementToBeClickableAgent(WebElement element, int wait, String agent){
        return initAgentWait(wait, agent).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    default WebElement waitForElementToBeVisible(WebElement element){
        return initWait(5)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOf(element));
    }

    default WebElement waitForElementToBeVisible(WebElement element, int time){
        return initWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOf(element));
    }


    default WebElement waitForElementToBeClickable(WebElement element, int time) {
        return initWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    default WebElement waitForElementToBeVisibleAgent(WebElement element, int time){
        return initAgentWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOf(element));
    }

    default List<WebElement> waitForElementsToBeVisibleAgent(List<WebElement> elements, int time, String agent){
        return initAgentWait(time, agent).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    default WebElement waitForElementToBeVisibleAgent(WebElement element, int time, String agent){
        return initAgentWait(time, agent).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOf(element));
    }

    default List<WebElement> waitForElementsToBeClickable(List<WebElement> elements){
        WebDriverWait wait = initWait(5);
        for(WebElement element: elements) {
            wait.ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.elementToBeClickable(element));
        }
        return elements;
        }

    default List<WebElement> waitForElementsToBeClickableAgent(List<WebElement> elements, int waitTime, String agent){
        WebDriverWait wait = initAgentWait(waitTime, agent);
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

    default void waitForElementToBeInvisibleAgent(WebElement element, int time){
        initAgentWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.invisibilityOf(element));
    }

    default void waitForElementToBeVisibleByXpathAgent(String xpath, int time){
        initAgentWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    default void waitForElementToBePresentByXpathAgent(String xpath, int time){
        initAgentWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    default void waitForElementToBePresentByCssAgent(String css, int time, String agent){
        initAgentWait(time, agent).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(css)));
    }

    default void waitForElementsToBePresentByXpathAgent(String xpath, int time){
        initAgentWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));
    }

    default void waitForElementToBeVisibleByCssAgent(String css, int time){
        initAgentWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css)));
    }

    default void waitForElementToBeVisibleByCssAgent(String css, int time, String agent){
        initAgentWait(time, agent).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css)));
    }

    default void waitForElementToBeInVisibleByCssAgent(String css, int time){
        initAgentWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(css)));
    }

    default void waitForElementToBeVisibleByXpathAgent(String xpath, int time, String agent){
        initAgentWait(time, agent).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    default void waitForElementToBeInVisibleByXpathAgent(String xpath, int time){
        initAgentWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
    }

    default void waitForElementsToBeVisibleByXpathAgent(String xpath, int time){
        initAgentWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
    }


    default void waitForElementsToBeVisibleByCssAgent(String css, int time) {
        initAgentWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(css)));
    }

    default void waitForElementsToBeInvisibleByCssAgent(String css, int time){
        initAgentWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(css)));
    }

    default void waitForElementsToBeInvisibleByXpathAgent(String xpath, int time, String agent){
        initAgentWait(time, agent).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
    }

    default void waitForElementToBeVisibleByXpath(String xpath, int time){
        initWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    default void waitForElementToBeVisibleByCss(String css, int time){
        initWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css)));
    }

    default void waitForElementToBeCklickableByCss(String css, int time){
        initWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(css)));
    }

    default void waitForElementToBeInVisibleByCss(String css, int time){
        initWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(css)));
    }

    default void waitForElementToBeInVisibleByXpath(String xpath, int time){
        initWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
    }

    default void waitForElementsToBeVisibleByXpath(String xpath, int time){
        initWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
    }

    default void waitForElementsToBeVisibleBycSS(String css, int time){
        initWait(time).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(css)));
    }

    default void waitForElementToBeInvisibleWithNoSuchElementException(WebElement element, int time){
        initWait(time).until(ExpectedConditions.invisibilityOf(element)).toString();
    }

}
