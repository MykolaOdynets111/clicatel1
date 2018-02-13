package interfaces;

import driverManager.DriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public interface JSHelper {

    default void executeJSclick(WebElement elem) {
        JavascriptExecutor executor = (JavascriptExecutor) DriverFactory.getInstance();
        executor.executeScript("arguments[0].click();", elem);
    }
}
