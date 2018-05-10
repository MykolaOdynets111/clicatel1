package twitter.uielements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = "div.PermalinkOverlay-modal")
public class OpenedTweet extends AbstractUIElement {

    @FindBy(css = "div.PermalinkOverlay-modal div.RichEditor-scrollContainer")
    private WebElement replyInputArea;

    @FindBy(xpath = "//div[@class='PermalinkOverlay-modal']//form//span[contains(text(),'Reply')]")
    private WebElement replyButton;

    String replyInputFieldCSS = "div.PermalinkOverlay-modal div.RichEditor-scrollContainer div[name='tweet']";

    public void sendReply(String tweetText){
//        WebElement input = findElemByXPATH("//div[@class='PermalinkOverlay-modal']//form//span[contains(text(),'Reply')]");
//        waitForElementToBeVisible(replyInputField, 6);
        moveToElemAndClick(replyInputArea);
        findElemByCSS(replyInputFieldCSS).sendKeys(tweetText);
        replyButton.click();
    }
}
