package twitter;

import abstractclasses.AbstractSocialPage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import twitter.uielements.TwitterHeader;

public class TwitterHomePage extends AbstractSocialPage {

    @FindBy(css = "div.RichEditor-scrollContainer div#tweet-box-home-timeline")
    private WebElement postTimelineTweet;

    private TwitterHeader twitterHeader;

    public TwitterHomePage(WebDriver driver) {
        super(driver);
    }

    public TwitterHeader getTwitterHeader() {
        twitterHeader.setCurrentDriver(this.getCurrentDriver());
        return twitterHeader;
    }

    public static void openTenantPage(WebDriver driver, String URL){
        driver.get(URL);
    }

    public void waitForPageToBeLoaded(){
        try {
            waitForElementToBeVisible(this.getCurrentDriver(), postTimelineTweet, 10);
        } catch (TimeoutException e) {
        }
    }
}
