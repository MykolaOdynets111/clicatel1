package twitter;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import twitter.uielements.DMWindow;
import twitter.uielements.SendNewTweetWindow;

public class TwitterTenantPage extends TwitterHomePage {

    @FindBy(css = "button.DMButton")
//    @FindBy(xpath = "//span[contains(text(),'Messages')]")
    private WebElement messageButton;

    @FindBy(css = "div.DMDock-conversations")
    private WebElement directConversationArea;

    private String newTweetButtonCss = "button.NewTweetButton";

    private String tweetSEndPopupCss = "alert-messages.js-message-drawer-visible";

    private DMWindow dmWindow;
    private SendNewTweetWindow tweetWindow;

    public SendNewTweetWindow getTweetWindow(){
        return tweetWindow;
    }

    public DMWindow getDmWindow() {
        return dmWindow;
    }

    public void openDMWindow() {
        waitForElementToBeInVisibleByCss(tweetSEndPopupCss, 8);
        waitForElementToBeClickable(messageButton, 5);
        executeJSclick(messageButton);
        waitForElementToBeVisible(directConversationArea, 5);
    }


    public void openNewTweetWindow() {
        waitForElementToBeCklickableByCss(newTweetButtonCss, 10);
        executeJSclick(findElemByCSS(newTweetButtonCss));
    }

    public boolean isDMWindowOpened(){
        return dmWindow.getWrappedElement().isDisplayed();
    }

    public boolean isTweetWindowOpened(){
        return tweetWindow.getWrappedElement().isDisplayed();
    }
}
