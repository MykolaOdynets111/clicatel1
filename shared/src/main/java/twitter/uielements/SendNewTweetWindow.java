package twitter.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(xpath = "//div[@class='modal-content'][@role='document']")
public class SendNewTweetWindow extends AbstractUIElement {

    @FindBy(xpath = ".//div[@name='tweet']")
    private WebElement tweetInputArea;

    @FindBy(xpath = "(.//div[@class='TweetBoxToolbar-tweetButton tweet-button']//span[contains(text(),'Tweet')])[1]")
    private WebElement tweetButton;

    public void sendTweet(String tweetText){
        waitForElementToBeVisible(this.getCurrentDriver(), tweetInputArea, 6);
        moveToElemAndClick(this.getCurrentDriver(), tweetInputArea);
        tweetInputArea.sendKeys(tweetText);
        tweetButton.click();
    }
}
