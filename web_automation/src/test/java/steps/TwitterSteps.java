package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataprovider.Tenants;
import driverManager.URLs;
import facebook.FBHomePage;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import touch_pages.uielements.WidgetHeader;
import twitter.TwitterHomePage;
import twitter.TwitterTenantPage;
import twitter.UserMentionsPage;
import twitter.uielements.DMWindow;
import twitter.uielements.TweetWindow;

import java.time.LocalDateTime;

public class TwitterSteps {

    private TwitterTenantPage twitterTenantPage;
    private DMWindow dmWindow;
    private TweetWindow tweetWindow;
    private UserMentionsPage userMentionsPage;

    @Given("^Open twitter page of (.*)$")
    public void openTwitterPage(String tenantOrgName){
        TwitterHomePage.openTenantPage(URLs.getTwitterURL(tenantOrgName));
        if(tenantOrgName.equals("General Bank Demo")){
            Tenants.setTenantUnderTest("generalbank");
        }
    }

    @Given("^Open direct message channel$")
    public void openDirectMessage() {
        getTwitterTenantPage().openDMWindow();
    }

    @Given("^Open new tweet window$")
    public void openNewTweetWindow() {
        getTwitterTenantPage().openNewTweetWindow();
    }

    @When("^User sends twitter direct message \"(.*)\"$")
    public void sendTwitterDM(String userMessage){
        getDmWindow().sendUserMessage(userMessage);
    }

    @When("^User sends tweet regarding \"(.*)\"$")
    public void sendTweet(String tweetMessage){
        int day = LocalDateTime.now().getDayOfMonth();
        if ( day % 2 == 0 ) {
            if (tweetMessage.contains("account balance"))
                tweetMessage = "How can I check account balance?";
        }
        else {
            if (tweetMessage.contains("account balance"))
                tweetMessage = "How to check my account balance?";
        }
        getTweetWindow().sendTweet(tweetMessage);
    }

    @Then("^(?:He|User) has to receive \"(.*)\" answer$")
    public void verifyReceivingAnswerInTimeline(String expectedAnswer){
        if(expectedAnswer.length()>132){
            expectedAnswer = expectedAnswer.substring(0,131);
        }
        Assert.assertEquals(getUserMentionsPage().getReplyIfShown(20), expectedAnswer);
    }

    @When("^He clicks \"(.*)\" tweet$")
    public void openTweet(String expectedAnswer){
        getUserMentionsPage().clickTimeLIneMentionWithText(expectedAnswer);
    }

    @Then("^User have to receive correct response \"(.*)\" on his message \"(.*)\"$")
    public void verifyDMTwitterResponse(String expectedResponse, String userMessage){
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getDmWindow().isTextResponseForUserMessageShown(userMessage),
                "There is no response on "+userMessage+" user message");
        soft.assertEquals(getDmWindow().getToUserResponse(userMessage), expectedResponse,
                "To user response is not as expected");
        soft.assertAll();
    }


    // =======================  Private Class Members =========================== //

    private TwitterTenantPage getTwitterTenantPage() {
        if (twitterTenantPage==null) {
            twitterTenantPage = new TwitterTenantPage();
            return twitterTenantPage;
        } else{
            return twitterTenantPage;
        }
    }

    private DMWindow getDmWindow() {
        if (dmWindow==null) {
            dmWindow = getTwitterTenantPage().getDmWindow();
            return dmWindow;
        } else{
            return dmWindow;
        }
    }

    private TweetWindow getTweetWindow() {
        if (tweetWindow==null) {
            tweetWindow = getTwitterTenantPage().getTweetWindow();
            return tweetWindow;
        } else{
            return tweetWindow;
        }
    }

    private UserMentionsPage getUserMentionsPage() {
        if (userMentionsPage==null) {
            userMentionsPage = new UserMentionsPage();
            return userMentionsPage;
        } else{
            return userMentionsPage;
        }
    }
}
