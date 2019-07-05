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

    @FindBy(css = "div.cl-header--controls.cl-header--item button")
    private List<WebElement> pageActionButtons;

    @FindBy(css = "div.cl-header--controls")
    private WebElement headerControlsContainer;

    @FindBy(xpath = "//button[text()='Save changes ']")
    private WebElement saveChangesButton ;

    private static String notificationAlert = "div.alert-window div[ng-bind-html='alert']";

    private static String processingAlert = "div.loader-bar-text";

    private PageHeader pageHeader;

    private String currentAgent = "main_agent";

    public PortalAbstractPage() {
        HtmlElementLoader.populatePageObject(this, DriverFactory.getAgentDriverInstance());
    }

    public PortalAbstractPage(String agent) {
        this.currentAgent = agent;
        HtmlElementLoader.populatePageObject(this, DriverFactory.getDriverForAgent(agent));
    }


    public PageHeader getPageHeader() {
        return pageHeader;
    }

    public String getNotificationAlertText(){
        if(isElementShownAgentByCSS(notificationAlert, 4, "admin")){
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

    public void waitForNotificationAlertToBeProcessed(int toAppear, int toDisappear){
        try {
            waitForElementToBeVisibleByCssAgent(notificationAlert, toAppear);
            waitForElementsToBeInvisibleByCssAgent(notificationAlert, toDisappear);
        } catch(NoSuchElementException|TimeoutException e){}
    }

    public static String getNotificationAlertLocator(){
        return notificationAlert;
    }

    public static String getProcessingAlertLocator(){
        return processingAlert;
    }

    public void waitWhileProcessing(){
        try {
        waitForElementToBeVisibleByCssAgent(processingAlert, 14);
        waitForElementToBeInVisibleByCssAgent(processingAlert, 20);
        } catch(NoSuchElementException|TimeoutException e){}
    }

    public void waitWhileProcessing(int toAppears, int toDisappear){
        try {
            waitForElementToBeVisibleByCssAgent(processingAlert, toAppears);
            waitForElementToBeInVisibleByCssAgent(processingAlert, toDisappear);
        } catch(NoSuchElementException|TimeoutException e){}
    }

    public void clickPageNavButton(String buttonName){
        waitForElementToBeVisibleAgent(selectionNavBar, 8);
        WebElement targetButton = pageNavButtons.stream()
                                        .filter(e -> e.getText().equalsIgnoreCase(buttonName))
                                        .findFirst().get();
        clickElemAgent(targetButton,1,"admin", buttonName);
    }

    public void clickPageActionButton(String buttonName){
        waitForElementToBeVisibleAgent(headerControlsContainer, 8);
        WebElement targetButton = pageActionButtons.stream()
                .filter(e -> e.getText().trim().equalsIgnoreCase(buttonName))
                .findFirst().get();
        clickElemAgent(targetButton,1,"admin", buttonName);
    }

    public void clickSaveButton(){
        clickElemAgent(saveChangesButton, 4, "admin", "Save changes");
    }

    public String getCurrentAgent(){
        return currentAgent;
    }
}
