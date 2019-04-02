package touchpages.pages;

import abstractclasses.AbstractPage;
import drivermanager.ConfigManager;
import drivermanager.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import touchpages.uielements.TouchActionsMenu;
import touchpages.uielements.WidgetConversationArea;
import touchpages.uielements.WidgetFooter;
import touchpages.uielements.WidgetHeader;


public class Widget extends AbstractPage {

    @FindBy(css = "div.ctl-chat-icon.ctl-close")
    private WebElement closeButton;

    @FindBy(xpath = "//span[@class='connection-status-bar-message']")
    private WebElement conectingMassage;

    @FindBy(css = "div.ctl-chat-container.ctl-visible")
    public WebElement widgetWindow;

    @FindBy(css = "div.ctl-touch-button")
    private WebElement touchButton;

    @FindBy(css = "div.ctl-conversation-area")
    private WebElement conversationArea;

    public Widget() {
        waitUntilOpenedAndConnected();
    }

    public Widget(String withWait) {
    }

    private WidgetConversationArea widgetConversationArea;
    private WidgetFooter widgetFooter;
    private TouchActionsMenu touchActionsMenu;
    private WidgetHeader widgetHeader;

    public WidgetHeader getWidgetHeader() {
        return widgetHeader;
    }

    public WidgetConversationArea getWidgetConversationArea() {
        return widgetConversationArea;
    }

    public WidgetFooter getWidgetFooter() {return widgetFooter;}

    public TouchActionsMenu getTouchActionsMenu() {
        return touchActionsMenu;
    }

    private void waitUntilOpenedAndConnected() {
        waitForElementToBeVisible(widgetWindow, 5);
        if (isConnectingMessageShown()) {
            waitConnectingMessageToDisappear();
        }
    }

    public boolean isWidgetCollapsed() {
        return isElementNotShown(widgetWindow, 5);
    }

    private boolean isConnectingMessageShown() {
        try {
            return isElementShown(conectingMassage);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void waitConnectingMessageToDisappear() {
        try{
            waitForElementToBeInvisibleWithNoSuchElementException(conectingMassage, 15);
        } catch (TimeoutException e){
            Assert.assertTrue(false, "Widget is not connected after 15 seconds wait. Client ID: "+getUserNameFromLocalStorage()+"");
        }
    }


    public TouchActionsMenu clickTouchButton(){
        try {
            click(touchButton);
            return touchActionsMenu;
        } catch(TimeoutException e) {
            Assert.assertTrue(false, "Touch button is not shown");
            return null;
        }

    }

    public boolean isWidgetConnected(int wait) {
        try {
            waitForElementToBeInvisibleWithNoSuchElementException(conectingMassage,wait);
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
        click(closeButton);
    }

    public boolean isTouchButtonShown(int wait){
        return isElementShown(touchButton, wait);
    }
}
