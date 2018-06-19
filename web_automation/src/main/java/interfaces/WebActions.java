package interfaces;

import driverManager.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.util.List;


public interface WebActions extends WebWait {

    public default void waitFor(int milisecs){
        try {
            Thread.sleep(milisecs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    default void click(WebElement element){
        waitForElementToBeClickable(element).click();
    }

    default void clickOnElementFromListByText(List<WebElement> elements, String text){
        waitForElementsToBeClickable(elements).stream().filter(e -> e.getText().toUpperCase().equals(text.toUpperCase())).findFirst().get().click();
    }

    default void inputText(WebElement element, String text){
        try {
            waitForElementToBeClickable(element).clear();
            element.sendKeys(text);
        } catch (TimeoutException e){
            Assert.assertTrue(false, "Cannot insert text '"+text+"' because input is not clickable.");
        }

    }

    default boolean isElementShown(WebElement element){
        try {
            return waitForElementToBeVisible(element, 5).isDisplayed();
        } catch (TimeoutException|NoSuchElementException e) {
            return false;
        }
    }

    default boolean isElementShownAgent(WebElement element){
        try {
            return waitForElementToBeVisibleAgent(element, 5).isDisplayed();
        } catch (TimeoutException|NoSuchElementException e) {
            return false;
        }
    }

    default boolean isElementShownAgent(WebElement element, int wait){
        try {
            return waitForElementToBeVisibleAgent(element, wait).isDisplayed();
        } catch (TimeoutException|NoSuchElementException e) {
            return false;
        }
    }

    default boolean isElementShown(WebElement element, int wait){
        try {
            return waitForElementToBeVisible(element, wait).isDisplayed();
        } catch (TimeoutException|NoSuchElementException e) {
            return false;
        }
    }

    default boolean isElementEnabled(WebElement element){
        try {
            return waitForElementToBeVisible(element, 5).isEnabled();
        } catch (TimeoutException e) {
            return false;
        }
    }

    default boolean isElementNotShown(WebElement element, int wait){
        try {
            waitForElementToBeInvisibleWithNoSuchElementException(element, wait);
            return true;
        } catch (TimeoutException e) {
            try {
                return element.isDisplayed();
            } catch(NoSuchElementException e1) {
                return true;
            }
        }
    }


    default String getTextFrom(WebElement elem) {
        try{
            return elem.getText();
        } catch (NoSuchElementException e) {
            return "no element to get text from";
        }
    }

    default WebElement findElemByXPATH(String xpath){
        return DriverFactory.getInstance().findElement(By.xpath(xpath));
    }

    default WebElement findElemByCSS(String css){
        return DriverFactory.getInstance().findElement(By.cssSelector(css));
    }

    default List<WebElement> findElemsByXPATH(String xpath){
        return DriverFactory.getInstance().findElements(By.xpath(xpath));
    }

    default void pressEnterForWebElem(WebElement elem){
        elem.sendKeys(Keys.ENTER);
    }
}
