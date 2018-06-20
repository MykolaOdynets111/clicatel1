package twitter;

import abstract_classes.AbstractPage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import twitter.uielements.OpenedTweet;
import twitter.uielements.TimelineTweet;

import java.util.List;
import java.util.stream.Collectors;

public class TweetsSection extends TwitterHomePage {

    @FindBy(css = "ol.stream-items")
    private WebElement timeline;

    @FindBy(css = "li.stream-item")
    private List<WebElement> timelineElemements;

    private String timelineElemementsCSS = "li.stream-item";

    private String replyPopUpInputField = "form.tweet-form.is-reply div.RichEditor-scrollContainer>div[name='tweet']";

    private String newCommentIcon = "//p[contains(text(), '%s')]//following::span[@class='ProfileTweet-actionCount ']";


    private String toUserResponse = "//li[contains(@class,'stream-item')]//p[text()='%s']";


    private String newTweetsButon = "button.new-tweets-bar";

    private OpenedTweet openedTweet;

    public OpenedTweet getOpenedTweet() {
        return openedTweet;
    }

    public boolean verifyFromAgentTweetArrives(int wait){
        try {
            waitForElementToBeVisibleByCss(newTweetsButon, wait);
//            findElemByCSS(newTweetsButon).click();
            return true;
        } catch (TimeoutException e){
                return getTwitterHeader().waitForNewNotificationIconToBeShown(wait);
        }
    }


    public String getReplyIfShown(int wait, String answerSource){
        try {
            getTwitterHeader().waitForNewNotificationIconToBeShown(wait);
            getTwitterHeader().clickNotificationsIcon();
        } catch (TimeoutException e){
            try{
            waitForElementToBeVisibleByCss(newTweetsButon, wait/10);
            findElemByCSS(newTweetsButon).click();}
            catch (TimeoutException e1){}
        }

        waitForElementToBeVisible(timeline);
        return new TimelineTweet(timelineElemements.get(1)).getTweetText();

    }

    public OpenedTweet clickTimeLineTweetWithText(String expectedText){
        waitForElementsToBeVisible(findElemntsByCSS(timelineElemementsCSS),5);
        findElemntsByCSS(timelineElemementsCSS).stream().map(e -> new TimelineTweet(e)).collect(Collectors.toList())
                .stream().filter(e -> e.getTweetText().contains(expectedText))
                .findFirst().get().getWrappedElement().click();
        waitForElementToBeVisible(openedTweet);
        return getOpenedTweet();
    }

    public void clickReplyButtonForTweet(String expectedText, String reply){
        waitForElementsToBeVisible(timelineElemements,5);
        timelineElemements.stream().map(TimelineTweet::new).collect(Collectors.toList())
                .stream().filter(e -> e.getTweetText().contains(expectedText))
                .findFirst().get().clickReplyButton();
        waitForElementToBeClickable(findElemByCSS(replyPopUpInputField)).sendKeys(reply);
    }
}
