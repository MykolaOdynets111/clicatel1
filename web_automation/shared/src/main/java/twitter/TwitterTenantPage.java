package twitter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import twitter.uielements.DMWindow;
import twitter.uielements.SendNewTweetWindow;

public class TwitterTenantPage extends TwitterHomePage {

    @FindBy(xpath = "//span[contains(text(),'Tweet')]")
    private WebElement messageButton;

    @FindBy(xpath = "//div[@aria-labelledby='modal-header']")
    private WebElement directConversationArea;

    private String newTweetButtonCss = "button.NewTweetButton";

    private String tweetSEndPopupCss = "alert-messages.js-message-drawer-visible";

    private String newTweetButtonXpath = "//a[@aria-label='Tweet']/div";

    private DMWindow dmWindow;
    private SendNewTweetWindow tweetWindow;

    public TwitterTenantPage(WebDriver driver) {
        super(driver);
    }

    public SendNewTweetWindow getTweetWindow(){
        tweetWindow.setCurrentDriver(this.getCurrentDriver());
        return tweetWindow;
    }

    public DMWindow getDmWindow() {
        dmWindow.setCurrentDriver(this.getCurrentDriver());
        return dmWindow;
    }

    public void openDMWindow() {
        waitForElementToBeClickableByXpath(this.getCurrentDriver(),newTweetButtonXpath,5);
        findElemByXPATH(this.getCurrentDriver(),newTweetButtonXpath).click();
        waitForElementToBeVisible(this.getCurrentDriver(), directConversationArea, 5);
    }


    public void openNewTweetWindow() {
        waitForElementToBeClickableByCss(this.getCurrentDriver(), newTweetButtonCss, 10);
        executeJSclick(this.getCurrentDriver(), findElemByCSS(this.getCurrentDriver(), newTweetButtonCss));
    }

    public boolean isDMWindowOpened(){
        return dmWindow.getWrappedElement().isDisplayed();
    }

    public boolean isTweetWindowOpened(){
        return tweetWindow.getWrappedElement().isDisplayed();
    }
}
