package portalpages;

import drivermanager.DriverFactory;
import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portalpages.uielements.PageHeader;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

import java.util.List;

public class PortalAbstractPage implements WebActions, ActionsHelper, JSHelper {

    @FindBy(css = "section.cl-page-tabs ol.list-unstyled.list-inline")
    private WebElement selectionNavBar;

    @FindBy(css = "li[ng-repeat='tab in clTabs']")
    private List<WebElement> pageNavButtons;

    @FindBy(xpath = "//button[text()='Save changes ']")
    private WebElement saveChangesButton ;

    private static String notificationAlert = "div.alert-container";

    private static String processingAlert = "div.loader-bar-text";

    private PageHeader pageHeader;


    public PortalAbstractPage() {
        HtmlElementLoader.populatePageObject(this, DriverFactory.getAgentDriverInstance());
    }


    public PageHeader getPageHeader() {
        return pageHeader;
    }

    public String getNotificationAlertText(){
        if( isElementShownAgentByCSS(notificationAlert, 4, "admin")){
            return findElemByCSSAgent(notificationAlert).getText();
        } else{
            return "no notification alert";
        }
    }

    public void waitForNotificationAlertToDisappear(){
        try {
            waitForElementsToBeInvisibleByCssAgent(notificationAlert, 25);
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
        waitForElementToBeVisibleByCssAgent(notificationAlert, 14);
        waitForElementToBeInVisibleByCssAgent(notificationAlert, 20);
        } catch(NoSuchElementException|TimeoutException e){}
    }

    public void clickPageNavButton(String buttonName){
        waitForElementToBeVisibleAgent(selectionNavBar, 8);
        WebElement targetButton = pageNavButtons.stream()
                                        .filter(e -> e.getText().equalsIgnoreCase(buttonName))
                                        .findFirst().get();
        clickElemAgent(targetButton,1,"admin", buttonName);
    }

    public void clickSaveButton(){
        clickElemAgent(saveChangesButton, 4, "admin", "Save changes");
    }

}
