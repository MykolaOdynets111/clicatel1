package portal_pages;

import driverManager.DriverFactory;
import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

public class PortalAbstractPage implements WebActions, ActionsHelper, JSHelper {

    public PortalAbstractPage() {
        HtmlElementLoader.populatePageObject(this, DriverFactory.getAgentDriverInstance());
    }

    private static String notificationAlert = "div.alert-container";

    public String getNotificationAlertText(){
        if( isElementShownAgentByCSS(notificationAlert, 2, "admin")){
            return findElemByCSSAgent(notificationAlert).getText();
        } else{
            return "no notification alert";
        }
    }

    public void waitForNotificationAlertToDisappear(){
        try {
            waitForElementsToBeInvisibleByCssAgent(notificationAlert, 15);
        } catch(NoSuchElementException e){}
    }

    public static String getNotificationAlertLocator(){
        return notificationAlert;
    }
}
