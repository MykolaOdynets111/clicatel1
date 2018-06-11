package twitter.uielements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import twitter.TweetsSection;

@FindBy(css = "div.PermalinkOverlay-modal")
public class OpenedTweet extends AbstractUIElement {

    @FindBy(css = "div.PermalinkOverlay-modal div.RichEditor-scrollContainer")
    private WebElement replyInputArea;

    @FindBy(xpath = "//div[@class='PermalinkOverlay-modal']//form//span[contains(text(),'Reply')]")
    private WebElement replyButton;

    private String tweetAnswer = "(//div[@class='TweetBoxToolbar-tweetButton tweet-button']//span[contains(text(),'Tweet')])[4]";

    String closeTweetButtonCSS = "div.modal-close-fixed";

    String replyInputFieldCSS = "div.PermalinkOverlay-modal div.RichEditor-scrollContainer div[name='tweet']";

    String agentResponseXpath = "//div[@class='js-tweet-text-container']/p[text()='%s']";

    public void sendReply(String tweetText, String agentMessage){
        new TweetsSection().clickReplyButtonForTweet(agentMessage, tweetText);
//        moveToElemAndClick(replyInputArea);
//        findElemByCSS(replyInputFieldCSS).sendKeys(tweetText);
//        executeJSclick(findElemByXPATH(tweetAnswer));
    }

    public void closeTweet(){
        findElemByCSS(closeTweetButtonCSS).click();
    }

    public boolean ifAgentReplyShown(String replyText, int wait){
        try {
            waitForElementToBeVisibleByXpath(String.format(agentResponseXpath, replyText),wait);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
