package interfaces;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

public interface ActionsHelper {


    default void moveToElement(WebDriver driver, WebElement elem){
        Actions action = new Actions(driver);
        action.moveToElement(elem).build().perform();
    }

    default void moveToElemAndClick(WebDriver driver, WebElement elem){
        Actions actions=new Actions(driver);
        actions.moveToElement(elem).click().release().
                build().
                perform();
    }

    default void moveAndClickByOffset(WebDriver driver, WebElement element, int xOffset, int yOffset){
        Actions actions = new Actions(driver);
        actions.moveToElement(element, xOffset, yOffset).click().release().
                build().
                perform();
    }

}
