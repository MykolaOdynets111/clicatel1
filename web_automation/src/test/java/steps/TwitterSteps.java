package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataprovider.Tenants;
import driverManager.URLs;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import twitter.TwitterHomePage;
import twitter.TwitterTenantPage;
import twitter.UserMentionsPage;
import twitter.uielements.DMWindow;
import twitter.uielements.OpenedTweet;
import twitter.uielements.TweetWindow;

import java.time.LocalDateTime;

public class TwitterSteps {

    private TwitterTenantPage twitterTenantPage;
    private DMWindow dmWindow;
    private TweetWindow tweetWindow;
    private UserMentionsPage userMentionsPage;
    private OpenedTweet openedTweet;

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
        Assert.assertEquals(getUserMentionsPage().getReplyIfShown(20, "touch"), expectedAnswer);
    }

    @Then("^(?:He|User) has to receive \"(.*)\" answer from the agent$")
    public void verifyReceivingAnswerInTimelineFromAgent(String expectedAnswer){
        if(expectedAnswer.length()>132){
            expectedAnswer = expectedAnswer.substring(0,131);
        }
        Assert.assertTrue(getUserMentionsPage().verifyFromAgentTweetIsShown(40, expectedAnswer));

    }

    @When("^He clicks \"(.*)\" tweet$")
    public void openTweet(String expectedAnswer){
        openedTweet = getUserMentionsPage().clickTimeLIneMentionWithText(expectedAnswer);
    }

    @When("^Send \"(.*)\" reply into tweet$")
    public void sendResponseIntoTweet(String replyMessage){
        openedTweet.sendReply(replyMessage);
    }


    @When("^User have to receive (.*) agent response as comment for (.*) tweet$")
    public void checkAgentResponse(String expectedResponse, String targetTweet){
        boolean result = false;
        for (int i =0; i < 10; i++){
            openedTweet.closeTweet();
            openedTweet = getUserMentionsPage().clickTimeLIneMentionWithText(targetTweet);
            if(openedTweet.ifAgentReplyShown(expectedResponse,1)){
                result = true;
                break;
            } else {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        Assert.assertTrue(result, "Agent response for user is not shown as comment for tweet");
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
