package twitter.uielements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(xpath = "//div[@class='modal-content'][@role='document']")
public class TweetWindow extends AbstractUIElement {

    @FindBy(xpath = ".//div[@name='tweet']")
    private WebElement tweetInputArea;

    @FindBy(xpath = "(.//div[@class='TweetBoxToolbar-tweetButton tweet-button']//span[contains(text(),'Tweet')])[1]")
    private WebElement tweetButton;


    public void sendTweet(String tweetText){
        waitForElementToBeVisible(tweetInputArea, 6);
        moveToElemAndClick(tweetInputArea);
        tweetInputArea.sendKeys(tweetText);
        tweetButton.click();
    }
}
