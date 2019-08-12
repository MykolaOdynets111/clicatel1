package twitter.uielements;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TimelineTweet extends AbstractWidget {

    @FindBy(css = "div.ReplyingToContextBelowAuthor span.username")
    private WebElement toUserName;

    @FindBy(css = "p.tweet-text")
    private WebElement tweeterText;

    @FindBy(css = "span.Icon.Icon--medium.Icon--reply")
    private WebElement replyButton;

    public TimelineTweet(WebElement element) {
        super(element);
    }

    public TimelineTweet setCurrentDriver(WebDriver driver){
        this.currentDriver = driver;
        return this;
    }

    public String getUserName() {
        return toUserName.getText();
    }

    public String getTweetText() {
        return tweeterText.getText();
    }


    public void clickReplyButton(){
        executeJSclick(replyButton, this.getCurrentDriver());
    }
}
