package twitter;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import twitter.uielements.OpenedTweet;
import twitter.uielements.TimelineTweet;

import java.util.List;

public class TweetsSection extends TwitterHomePage {

    @FindBy(css = "ol.stream-items")
    private WebElement timeline;

    @FindBy(css = "li.stream-item")
    private List<WebElement> timelineElemements;

    private String timelineElemementsCSS = "li.stream-item";

    private String targetTweetXPATH = "//p[contains(@class, 'tweet-text')][contains(text(), '%s')]";

    private String replyPopUpInputField = "form.tweet-form.is-reply div.RichEditor-scrollContainer>div[name='tweet']";

    private String newCommentIcon = "//p[contains(text(), '%s')]//following::span[@class='ProfileTweet-actionCount ']";


    private String toUserResponse = "//li[contains(@class,'stream-item')]//p[text()='%s']";


    private String newTweetsButon = "button.new-tweets-bar";

    private OpenedTweet openedTweet;

    public TweetsSection(WebDriver driver) {
        super(driver);
    }

    public OpenedTweet getOpenedTweet() {
        openedTweet.setCurrentDriver(this.getCurrentDriver());
        return openedTweet;
    }

    public boolean verifyFromAgentTweetArrives(int wait) {
        for (int i = 0; i <= wait/2; i++) {
            try {
                waitForElementToBeVisibleByCss(this.getCurrentDriver(), newTweetsButon, 1);
                return true;
            } catch (TimeoutException e) {
                try {
                     getTwitterHeader().checkIfNewNotificationShown(1);
                     return true;
                } catch (TimeoutException e1) {
                    //nothing to do here, one more loop
                }
            }
        }
        return false;
    }

    public void clickNewTweetsButtonIfShown(int wait){
        try {
                waitForElementToBeVisibleByCss(this.getCurrentDriver(), newTweetsButon, wait);
                try{
                    findElemByCSS(this.getCurrentDriver(), newTweetsButon).click();
                } catch (WebDriverException e1){
                    findElemByCSS(this.getCurrentDriver(), newTweetsButon).click();
                }
        } catch (TimeoutException e){
        }
    }

    public String getReplyIfShown(int wait, String answerSource){
        try {
            getTwitterHeader().waitForNewNotificationIconToBeShown(wait);
            getTwitterHeader().clickNotificationsIcon();
        } catch (TimeoutException e){
            try{
            waitForElementToBeVisibleByCss(this.getCurrentDriver(), newTweetsButon, wait/10);
            findElemByCSS(this.getCurrentDriver(), newTweetsButon).click();}
            catch (TimeoutException e1){}
        }

        waitForElementToBeVisible(this.getCurrentDriver(), timeline, 5);
        return new TimelineTweet(timelineElemements.get(1)).setCurrentDriver(this.getCurrentDriver()).getTweetText();

    }

    public OpenedTweet clickTimeLineTweetWithText(String expectedText){
        try{
        waitForElementsToBeVisible(this.getCurrentDriver(), findElemsByCSS(this.getCurrentDriver(), timelineElemementsCSS),5);
        }catch(TimeoutException e){}
        findElemByXPATH(this.getCurrentDriver(), String.format(targetTweetXPATH, expectedText)).click();
//        findElemntsByCSS(timelineElemementsCSS).stream().map(e -> new TimelineTweet(e)).collect(Collectors.toList())
//                .stream().filter(e -> e.getTweetText().contains(expectedText))
////                .findFirst().get().getWrappedElement().findElement(By.cssSelector("div.my-tweet")).click();
//                        .findFirst().get().getWrappedElement().click();

//        waitForElementToBeVisible(this.getCurrentDriver(), openedTweet., 5);
        return getOpenedTweet();
    }

    public void sendReplyForTweet(String expectedText, String reply){
//            waitForElementsToBeVisible(timelineElemements, 5);
//            timelineElemements.stream().map(TimelineTweet::new).collect(Collectors.toList())
//                    .stream().filter(e -> e.getTweetText().contains(expectedText))
//                    .findFirst().get().clickReplyButton();
        waitForElementToBeVisibleByCss(this.getCurrentDriver(), replyPopUpInputField, 10);
        findElemByCSS(this.getCurrentDriver(), replyPopUpInputField).sendKeys(reply);
            }
}
