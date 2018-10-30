package interfaces;

import driverManager.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public interface ActionsHelper {

    default void moveToElemAndClick(WebElement elem){
        Actions actions=new Actions(DriverFactory.getTouchDriverInstance());
        actions.moveToElement(elem).click().
                build().
                perform();
    }

    default void moveToElement(WebDriver driver, WebElement elem){
        Actions action = new Actions(DriverFactory.getTouchDriverInstance());
        action.moveToElement(elem).build().perform();
    }

    default void moveToElemAndClick(WebDriver driver, WebElement elem){
        Actions actions=new Actions(driver);
        actions.moveToElement(elem).click().release().
                build().
                perform();
    }

}
