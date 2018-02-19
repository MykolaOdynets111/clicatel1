package touch_pages.pages;

import abstract_classes.AbstractPage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import touch_pages.uielements.WidgetConversationArea;
import touch_pages.uielements.WidgetFooter;


public class Widget extends AbstractPage {

    @FindBy(xpath = "//span[@class='connection-status-bar-message']")
    private WebElement conectingMassage;

    @FindBy(css = "div.ctl-chat-container.ctl-visible")
    public WebElement widgetWindow;

    @FindBy(css = "div.ctl-touch-button")
    private WebElement touchButton;

    public Widget() {
        waitUntilOpenedAndConnected();
    }

    private WidgetConversationArea widgetConversationArea;
    private WidgetFooter widgetFooter;

    public WidgetConversationArea getWidgetConversationArea() {
        return widgetConversationArea;
    }

    public WidgetFooter getWidgetFooter() {return widgetFooter;}

    private void waitUntilOpenedAndConnected() {
        waitForElementToBeVisible(widgetWindow, 10);
        if (isConnectingMessageShown()) {
            waitConnectingMessageToDisappear();
        }
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
            waitForElementToBeInvisibleWithNoSuchElementException(conectingMassage,65);
        } catch (TimeoutException e){
            Assert.assertTrue(false, "Widget is not connected after 65 seconds wait");
        }
    }


    public void clickTouchButton(){
        touchButton.click();
    }
}
