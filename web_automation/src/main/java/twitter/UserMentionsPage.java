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

public class UserMentionsPage extends AbstractPage {

    private String newNotificationIcon="span.count.new-count";

    @FindBy(css = "ol.stream-items")
    private WebElement timeline;

    @FindBy(css = "li.stream-item")
    private List<WebElement> timelineElemements;

    @FindBy(xpath = "//span[text()='Notifications']")
    private WebElement notificationsIcon;

    private String toUserResponse = "//li[contains(@class,'stream-item')]//p[text()='%s']";


    private String newTweetsButon = "button.new-tweets-bar";

    private OpenedTweet openedTweet;

    public OpenedTweet getOpenedTweet() {
        return openedTweet;
    }

    public boolean verifyFromAgentTweetIsShown(int wait, String agentREsponse){
        try {
            waitForElementToBeVisibleByCss(newTweetsButon, wait);
            findElemByCSS(newTweetsButon).click();
        } catch (TimeoutException e){}

        try {
            waitForElementToBeVisibleByXpath(String.format(toUserResponse, agentREsponse),wait);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }


    public String getReplyIfShown(int wait, String answerSource){
        try {
            waitForElementToBeVisibleByXpath(newNotificationIcon, wait);
            notificationsIcon.click();
        } catch (TimeoutException e){
            try{
            waitForElementToBeVisibleByCss(newTweetsButon, wait/10);
            findElemByCSS(newTweetsButon).click();}
            catch (TimeoutException e1){}
        }

        waitForElementToBeVisible(timeline);
        return new TimelineTweet(timelineElemements.get(1)).getTweetText();

    }

    public OpenedTweet clickTimeLIneMentionWithText(String expectedText){
        waitForElementsToBeVisible(timelineElemements,5);
        timelineElemements.stream().map(e -> new TimelineTweet(e)).collect(Collectors.toList())
                .stream().filter(e -> e.getTweetText().equals(expectedText))
                .findFirst().get().getWrappedElement().click();
        waitForElementToBeVisible(openedTweet);
        return getOpenedTweet();
    }
}
