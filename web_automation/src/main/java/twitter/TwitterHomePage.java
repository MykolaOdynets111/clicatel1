package twitter;

import abstractclasses.AbstractPage;
import drivermanager.DriverFactory;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import twitter.uielements.TwitterHeader;

public class TwitterHomePage extends AbstractPage {

    @FindBy(css = "div.RichEditor-scrollContainer div#tweet-box-home-timeline")
    private WebElement postTimelineTweet;

    private TwitterHeader twitterHeader;

    public TwitterHeader getTwitterHeader() {
        return twitterHeader;
    }

    public static void openTenantPage(String URL){
        DriverFactory.getTouchDriverInstance().get(URL);
    }

    public void waitForPageToBeLoaded(){
        try {
            waitForElementToBeVisible(postTimelineTweet, 10);
        } catch (TimeoutException e) {
        }
    }
}
