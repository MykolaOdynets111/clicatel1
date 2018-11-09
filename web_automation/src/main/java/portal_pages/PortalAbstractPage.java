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

    @FindBy(css = "div.alert-container")
    private WebElement notificationAlert;

    public String getNotificationAlertText(){
        if( isElementShownAgent(notificationAlert, 2)){
            return notificationAlert.getText();
        } else{
            return "no notification alert";
        }
    }

    public void waitForNotificationAlertToDisappear(){
        try {
            waitForElementToBeInvisibleAgent(notificationAlert, 5);
        } catch(NoSuchElementException e){}
    }
}
