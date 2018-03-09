package touch_pages.pages;

import abstract_classes.AbstractPage;
import driverManager.DriverFactory;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import touch_pages.uielements.TouchActionsMenu;
import touch_pages.uielements.WidgetConversationArea;
import touch_pages.uielements.WidgetFooter;
import touch_pages.uielements.WidgetHeader;


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
        waitForElementToBeVisible(widgetWindow, 10);
        if (isConnectingMessageShown()) {
            waitConnectingMessageToDisappear();
        }
    }

    public boolean isWidgetCollapsed() {
        return isElementNotShown(widgetWindow);
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
            waitForElementToBeInvisibleWithNoSuchElementException(conectingMassage,25);
        } catch (TimeoutException e){
            Assert.assertTrue(false, "Widget is not connected after 25 seconds wait. Client ID: "+getUserNameFromLocalStorage()+"");
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

        Point point = conversationArea.getLocation();
        Actions action = new Actions(DriverFactory.getInstance());
        action.clickAndHold(conversationArea).moveByOffset(point.x, point.y+20).release().build().perform();
    }

    public void clickCloseButton() {
        click(closeButton);
    }
}
