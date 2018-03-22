package interfaces;

import driverManager.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;


public interface WebActions extends WebWait {

    default void click(WebElement element){
        waitForElementToBeClickable(element).click();
    }

    default void clickOnElementFromListByText(List<WebElement> elements, String text){
        waitForElementsToBeClickable(elements).stream().filter(e -> e.getText().toUpperCase().equals(text.toUpperCase())).findFirst().get().click();
    }

    default void inputText(WebElement element, String text){
        waitForElementToBeClickable(element).clear();
        element.sendKeys(text);
    }

    default boolean isElementShown(WebElement element){
        try {
            return waitForElementToBeVisible(element, 5).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    default boolean isElementShown(WebElement element, int wait){
        try {
            return waitForElementToBeVisible(element, wait).isDisplayed();
        } catch (TimeoutException e) {
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

    default List<WebElement> findElemsByXPATH(String xpath){
        return DriverFactory.getInstance().findElements(By.xpath(xpath));
    }

    default void pressEnterForWebElem(WebElement elem){
        elem.sendKeys(Keys.ENTER);
    }
}
