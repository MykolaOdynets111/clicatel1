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

    private OpenedTweet openedTweet;

    public OpenedTweet getOpenedTweet() {
        return openedTweet;
    }

    public String getReplyIfShown(int wait){
        try {
            waitForElementToBeVisibleByCss(newNotificationIcon, wait);
        } catch (TimeoutException e){}
        notificationsIcon.click();
        waitForElementToBeVisible(timeline);
        if(timelineElemements.size()==0) {
            Assert.assertTrue(false, "Tweet response is not shown");
        }
        return new TimelineTweet(timelineElemements.get(1)).getTweetText();
    }

    public OpenedTweet clickTimeLIneMentionWithText(String expectedText){
        timelineElemements.stream().map(e -> new TimelineTweet(e)).collect(Collectors.toList())
                .stream().filter(e -> e.getTweetText().equals(expectedText))
                .findFirst().get().getWrappedElement().click();
        waitForElementToBeVisible(openedTweet);
        getOpenedTweet().sendReply("Hallo");
        return getOpenedTweet();
    }
}
