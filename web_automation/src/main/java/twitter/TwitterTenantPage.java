package twitter;

import abstract_classes.AbstractPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import twitter.uielements.DMWindow;
import twitter.uielements.TweetWindow;

public class TwitterTenantPage extends TwitterHomePage {

    @FindBy(css = "button.DMButton")
    private WebElement messageButton;

    @FindBy(css = "div.DMDock-conversations")
    private WebElement directConversationArea;

    private String newTweetButtonCss = "button.NewTweetButton";

    private String tweetSEndPopupCss = "alert-messages.js-message-drawer-visible";

    private DMWindow dmWindow;
    private TweetWindow tweetWindow;

    public TweetWindow getTweetWindow(){
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
        waitForElementToBeVisibleByCss(newTweetButtonCss, 6);
        findElemByCSS(newTweetButtonCss).click();
    }
}
