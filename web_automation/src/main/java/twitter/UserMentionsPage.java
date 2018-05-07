package twitter;

import abstract_classes.AbstractPage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import twitter.uielements.TimelineTweet;

import java.util.List;

public class UserMentionsPage extends AbstractPage {

    private String newNotificationIcon="span.count.new-count";

    @FindBy(css = "ol.stream-items")
    private WebElement timeline;

    @FindBy(css = "li.stream-item")
    private List<WebElement> timelineElemements;

    @FindBy(xpath = "//span[text()='Notifications']")
    private WebElement notificationsIcon;

    public String getReplyIfShown(int wait){
        try {
            waitForElementToBeVisibleByCss(newNotificationIcon, wait);
        } catch (TimeoutException e){}
        notificationsIcon.click();
        waitForElementToBeVisible(timeline);
        if(timelineElemements.size()==0) {
            Assert.assertTrue(false, "Tweet response is not shown");
        }
        return new TimelineTweet(timelineElemements.get(1)).getTweeterText();
    }
}
