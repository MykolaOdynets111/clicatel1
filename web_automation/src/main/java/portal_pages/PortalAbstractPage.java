package portal_pages;

import driverManager.DriverFactory;
import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

public class PortalAbstractPage implements WebActions, ActionsHelper, JSHelper {

    public PortalAbstractPage() {
        HtmlElementLoader.populatePageObject(this, DriverFactory.getAgentDriverInstance());
    }

    private static String notificationAlert = "div.alert-container";

    private static String processingAlert = "div.loader-bar-text";

    public String getNotificationAlertText(){
        if( isElementShownAgentByCSS(notificationAlert, 4, "admin")){
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

    public static String getProcessingAlertLocator(){
        return processingAlert;
    }

    public void waitWhileProcessing(){
        try {
        waitForElementToBeVisibleByCssAgent(notificationAlert, 4);
        waitForElementToBeInVisibleByCssAgent(notificationAlert, 20);
        } catch(NoSuchElementException|TimeoutException e){}
    }
}
