package portalpages;

import driverfactory.MC2DriverFactory;
import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import portaluielem.PageHeader;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

import java.util.List;

public class PortalAbstractPage implements WebActions, ActionsHelper, JSHelper {

    @FindBy(css = "section.cl-page-tabs ol.list-unstyled.list-inline")
    private WebElement selectionNavBar;

    @FindBy(css = "section.cl-page-tabs ol.list-unstyled.list-inline li[ng-repeat]")
    private List<WebElement> pageNavButtons;

    @FindBy(css = "section.cl-page-tabs ol.list-unstyled.list-inline li.active")
    private WebElement focusedNavButton;

    @FindBy(css = "div.cl-header--controls.cl-header--item button")
    private List<WebElement> pageActionButtons;

    @FindBy(css = "div.cl-header--controls")
    private WebElement headerControlsContainer;

    @FindBy(xpath = "//button[text()='Save changes ']")
    private WebElement saveChangesButton ;

    private static String notificationAlert = "div[ng-bind-html='alert']";

    private PageHeader pageHeader;

    private WebDriver currentDriver;

    // == Constructors == //

    public PortalAbstractPage() {
        HtmlElementLoader.populatePageObject(this, MC2DriverFactory.getPortalDriver());
        this.currentDriver = MC2DriverFactory.getPortalDriver();
    }


    public PortalAbstractPage(String agent) {
        Assert.fail(" Use only constructor with driver");
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
        pageHeader.setCurrentDriver(this.getCurrentDriver());
        return pageHeader;
    }

    @Step(value = "Get notification alert text")
    public String getNotificationAlertText(){
        if(isElementShownByCSS(this.getCurrentDriver(), notificationAlert, 2)) {
            String alertText = findElemByCSS(this.getCurrentDriver(), notificationAlert).getText();
            waitForNotificationAlertToBeProcessed(1, 5);
            return alertText;
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


    @Step(value = "Click '{userManagementPage}' page navigation button")
    public void clickPageNavButton(String buttonName){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        waitForElementToBeVisible(this.getCurrentDriver(), selectionNavBar, 8);
        WebElement targetButton = pageNavButtons.stream()
                                        .filter(e -> e.getText().trim().equalsIgnoreCase(buttonName))
                                        .findFirst().get();
        clickElem(this.getCurrentDriver(), targetButton,1, buttonName);
    }

    @Step(value = "Get opened tab title")
    public String getOpenedTabTitle(){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        try {
            waitForElementToBeVisible(this.getCurrentDriver(), selectionNavBar, 8);
        }catch(TimeoutException e){
            Assert.fail("Selection nav bar Billing Details page is not visible");
        }
        return focusedNavButton.getText().trim();
    }

    @Step(value = "Click '{buttonName}' page action button")
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

    public void waitWhileProcessing(int toAppears, int toDisappear) {
        waitWhileProcessing(this.getCurrentDriver(), toAppears, toDisappear);
    }
}
