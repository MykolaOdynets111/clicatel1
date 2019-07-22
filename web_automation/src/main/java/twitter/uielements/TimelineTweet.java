package twitter.uielements;

import interfaces.JSHelper;
import interfaces.WebActionsDeprecated;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TimelineTweet extends Widget implements WebActionsDeprecated, JSHelper {

    @FindBy(css = "div.ReplyingToContextBelowAuthor span.username")
    private WebElement toUserName;

    @FindBy(css = "p.tweet-text")
    private WebElement tweeterText;

    @FindBy(css = "span.Icon.Icon--medium.Icon--reply")
    private WebElement replyButton;

    public TimelineTweet(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }

    public String getUserName() {
        return toUserName.getText();
    }

    public String getTweetText() {
        return tweeterText.getText();
    }


    public void clickReplyButton(){
        executeJSclick(replyButton);
    }
}
