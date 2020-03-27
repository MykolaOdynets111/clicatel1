package touchpages.pages;

import abstractclasses.AbstractSocialPage;
import driverfactory.DriverFactory;
import drivermanager.ConfigManager;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import touchpages.uielements.TouchActionsMenu;
import touchpages.uielements.WidgetConversationArea;
import touchpages.uielements.WidgetFooter;
import touchpages.uielements.WidgetHeader;


public class Widget extends AbstractSocialPage {

    @FindBy(css = "div.ctl-chat-icon.ctl-close")
    private WebElement closeButton;

    @FindBy(css = "input.ctl-chat-widget-btn-close")
    private WebElement closeButtonColor;

    @FindBy(xpath = "//span[@class='connection-status-bar-message']")
    private WebElement conectingMassage;

    @FindBy(css = "div.ctl-chat-container.ctl-visible")
    public WebElement widgetWindow;

    @FindBy(css = "div.ctl-touch-button")
    private WebElement touchButton;

    @FindBy(css = "div.ctl-conversation-area")
    private WebElement conversationArea;

    @FindBy(css = "div.ctl-chat-area-header-container")
    private WebElement tenantNameWidget;

    @FindBy(css = "div.ctl-chat-header-agent-image")
    private WebElement tenantLogoImage;

    @FindBy(css = ".file-state.file-state--successful")
    private WebElement uploadedStatus;

    @FindBy(css = ".mr-3.ctl-stroke-path")
    private WebElement sendAttachmentButton;

    public Widget(WebDriver driver) {
        super(driver);
        waitUntilOpenedAndConnected();
    }

    public Widget() {
        super(DriverFactory.getTouchDriverInstance());
        waitUntilOpenedAndConnected();
    }

    public Widget(String withWait) {
        super(DriverFactory.getTouchDriverInstance());

    }

    private WidgetConversationArea widgetConversationArea;
    private WidgetFooter widgetFooter;
    private TouchActionsMenu touchActionsMenu;
    private WidgetHeader widgetHeader;

    public WidgetHeader getWidgetHeader() {
        widgetHeader.setCurrentDriver(this.getCurrentDriver());
        return widgetHeader;
    }

    public WidgetConversationArea getWidgetConversationArea() {
        widgetConversationArea.setCurrentDriver(this.getCurrentDriver());
        return widgetConversationArea;
    }

    public WidgetFooter getWidgetFooter() {
        widgetFooter.setCurrentDriver(this.getCurrentDriver());
        return widgetFooter;
    }

    public TouchActionsMenu getTouchActionsMenu() {
        touchActionsMenu.setCurrentDriver(this.getCurrentDriver());
        return touchActionsMenu;
    }

    private void waitUntilOpenedAndConnected() {
        waitForElementToBeVisible(this.getCurrentDriver(), widgetWindow, 5);
        if (isConnectingMessageShown()) {
            waitConnectingMessageToDisappear();
        }
    }

    public boolean isWidgetCollapsed() {
        return isElementRemoved(this.getCurrentDriver(), widgetWindow, 5);
    }

    private boolean isConnectingMessageShown() {
        try {
            return isElementShown(this.getCurrentDriver(), conectingMassage, 5);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void waitConnectingMessageToDisappear() {
        try{
            waitForElementToBeInvisible(this.getCurrentDriver(), conectingMassage, 17);
        } catch (TimeoutException e){
            Assert.fail(
                    "Widget is not connected after 17 seconds wait. Client ID: "+getUserNameFromLocalStorage(this.getCurrentDriver())+"");
        }
    }


    public TouchActionsMenu clickTouchButton(){
            clickElem(this.getCurrentDriver(), touchButton, 5, "Touch button");
            return touchActionsMenu;
    }

    public boolean isWidgetConnected(int wait) {
        try {
            waitForElementToBeInvisible(this.getCurrentDriver(),conectingMassage,wait);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void scrollABitToRevealHeaderButtons() {
        Actions action = new Actions(DriverFactory.getTouchDriverInstance());
        if(ConfigManager.isRemote()){
            action.dragAndDropBy(conversationArea, 0, -50).build().perform();
        }else {
            action.dragAndDropBy(conversationArea, 0, -20).build().perform();
        }
    }

    public void clickCloseButton() {
        clickElem(this.getCurrentDriver(), closeButton, 3, "Close button");
    }

    public boolean isTouchButtonShown(int wait){
        return isElementShown(this.getCurrentDriver(), touchButton, wait);
    }


    public String getTenantNameWidgetColor() {
        return Color.fromString(tenantNameWidget.getCssValue("background-color")).asHex();
    }

    public String getTenantCloseButtonColor() {
        return Color.fromString(closeButtonColor.getCssValue("background-color")).asHex();
    }

    public boolean isTenantImageShown(){
        return tenantLogoImage.getCssValue("background-image").contains("logo");
    }

    public boolean isFileUploaded(){
        return isElementShown(this.getCurrentDriver(), uploadedStatus, 15);
    }

    public void clickSendAttachmentButton(){
        clickElem(this.getCurrentDriver(), sendAttachmentButton, 5, "Send attachment button");
    }
}
