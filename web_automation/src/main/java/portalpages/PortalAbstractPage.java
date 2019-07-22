package portalpages;

import drivermanager.DriverFactory;
import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import interfaces.WebActionsDeprecated;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
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

    private static String notificationAlert = "div[ng-bind-html='alert']";

    private static String processingAlert = "div.loader-bar-text";

    private PageHeader pageHeader;

    private WebDriver currentDriver;

    // == Constructors == //

    public PortalAbstractPage() {
        // here will be a default Driver for mc2 tests
//        HtmlElementLoader.populatePageObject(this, MC2DriverFactory.getAgentDriverInstance());
    }
    public PortalAbstractPage(String agent) {
        this.currentDriver = DriverFactory.getDriverForAgent(agent);
        HtmlElementLoader.populatePageObject(this, this.currentDriver);
    }
    public PortalAbstractPage(WebDriver driver) {
        this.currentDriver = driver;
        HtmlElementLoader.populatePageObject(this, this.currentDriver);
    }

    public static String getProcessingAlertLocator(){
        return processingAlert;
    }

    public static String getNotificationAlertLocator(){
        return notificationAlert;
    }

    public WebDriver getCurrentDriver(){
        return this.currentDriver;
    }

    public PageHeader getPageHeader() {
        pageHeader.setCurrentDriver(currentDriver);
        return pageHeader;
    }

    public String getNotificationAlertText(){
        if(isElementShownByCSS(this.getCurrentDriver(), notificationAlert, 2)) {
            return findElemByCSS(this.getCurrentDriver(), notificationAlert).getText();
        } else{
            return "no notification alert";
        }
    }

    public void waitForNotificationAlertToDisappear(){
        try {
            waitForElementToBeInVisibleByCss(this.getCurrentDriver(), notificationAlert, 25);
        } catch(NoSuchElementException e){}
    }

    public void waitForNotificationAlertToBeProcessed(int toAppear, int toDisappear){
        try {
            waitForElementToBeVisibleByCss(this.getCurrentDriver(), notificationAlert, toAppear);
            waitForElementToBeInVisibleByCss(this.getCurrentDriver(), notificationAlert, toDisappear);
        } catch(NoSuchElementException|TimeoutException e){}
    }

    public void waitWhileProcessing(int toAppears, int toDisappear){
        try {
            waitForElementToBeVisibleByCss(this.getCurrentDriver(), processingAlert, toAppears);
            waitForElementToBeInVisibleByCss(this.getCurrentDriver(), processingAlert, toDisappear);
        } catch(NoSuchElementException|TimeoutException e){}
    }

    public void clickPageNavButton(String buttonName){
        waitForElementToBeVisible(this.getCurrentDriver(), selectionNavBar, 8);
        WebElement targetButton = pageNavButtons.stream()
                                        .filter(e -> e.getText().equalsIgnoreCase(buttonName))
                                        .findFirst().get();
        clickElem(this.getCurrentDriver(), targetButton,1, buttonName);
    }

    public void clickPageActionButton(String buttonName){
        waitForElementToBeVisible(this.getCurrentDriver(), headerControlsContainer, 8);
        WebElement targetButton = pageActionButtons.stream()
                .filter(e -> e.getText().trim().equalsIgnoreCase(buttonName))
                .findFirst().get();
        clickElem(this.getCurrentDriver(), targetButton,1, buttonName);
    }

    public void clickSaveButton(){
        clickElem(this.getCurrentDriver(), saveChangesButton,1, "Save changes");
    }

}
