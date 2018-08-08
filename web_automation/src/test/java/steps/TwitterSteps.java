package steps;

import com.github.javafaker.Faker;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataManager.Tenants;
import driverManager.URLs;
import org.openqa.selenium.StaleElementReferenceException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import twitter.TwitterHomePage;
import twitter.TwitterTenantPage;
import twitter.TweetsSection;
import twitter.uielements.DMWindow;
import twitter.uielements.OpenedTweet;
import twitter.uielements.TweetWindow;

import java.time.LocalDateTime;

public class TwitterSteps {

    private TwitterTenantPage twitterTenantPage;
    private DMWindow dmWindow;
    private TweetWindow tweetWindow;
    private TweetsSection tweetsSection;
    private OpenedTweet openedTweet;
    private static int invocationCount = 0;
    private static String tweetMessage;

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

    @Given("^User closes DM window$")
    public void closeDMWindow(){
        getTwitterTenantPage().getDmWindow().closeDMWindow();
    }

    @Given("^Open new tweet window$")
    public void openNewTweetWindow() {
        getTwitterTenantPage().openNewTweetWindow();
    }

    @When("^User sends twitter direct (?:message regarding|message:) (.*)$")
    public void sendTwitterDM(String userMessage){
        if (userMessage.contains("agent")||userMessage.contains("support")){
            userMessage = createToAgentTweetText();
        }
        getDmWindow().sendUserMessage(userMessage);
    }

    @When("^User sends tweet regarding \"(.*)\"$")
    public void sendTweet(String tweetMessage){
        if (tweetMessage.contains("agent")||tweetMessage.contains("support")){
                tweetMessage = createToAgentTweetText();
        }

        getTweetWindow().sendTweet(tweetMessage);
    }

    @Then("^(?:He|User) has to receive \"(.*)\" answer$")
    public void verifyReceivingAnswerInTimeline(String expectedAnswer){
        if(expectedAnswer.length()>132){
            expectedAnswer = expectedAnswer.substring(0,131);
        }
        Assert.assertEquals(getTweetsSection().getReplyIfShown(100, "touch"), expectedAnswer,
                "Expected tweet answer is missing after 70 secs wait");
    }

//    @Then("^(?:He|User) has to receive \"(.*)\" answer from the agent$")
    @Then("^Agent's answer arrives to twitter$")
    public void verifyReceivingAnswerInTimelineFromAgent(){
//        if(expectedAnswer.length()>132){
//            expectedAnswer = expectedAnswer.substring(0,131);
//        }
        Assert.assertTrue(getTweetsSection().verifyFromAgentTweetArrives(100),
                "Expected tweet answer from the agent is missing after 100 secs wait");
    }

    @Then("^User has to receive \"(.*)\" answer from the agent as a comment on his initial tweet (.*)$")
    public void verifyFromAgentResponseAsACommentOnTweet(String expectedAgentMessage, String initialUserTweet){
        getTwitterTenantPage().getTwitterHeader().openHomePage().waitForPageToBeLoaded();
        if(initialUserTweet.contains("agent")||initialUserTweet.contains("support")){
            initialUserTweet = getCurrentConnectToAgentTweetText();
        }
        openedTweet = getTweetsSection().clickTimeLineTweetWithText(initialUserTweet);
        if(expectedAgentMessage.length()>132){
            expectedAgentMessage = expectedAgentMessage.substring(0,131);
        }
        Assert.assertTrue(openedTweet.ifAgentReplyShown(expectedAgentMessage,5),
                "Agent response "+expectedAgentMessage+" for user is not shown as comment for tweet");
    }


    @When("^He clicks \"(.*)\" tweet$")
    public void openTweet(String expectedAnswer){
        openedTweet = getTweetsSection().clickTimeLineTweetWithText(expectedAnswer);
    }

    @When("^Send \"(.*)\" reply on agent's tweet \"(.*)\"$")
    public void sendResponseIntoTweet(String replyMessage, String agentMessage ){
        openedTweet.sendReply(replyMessage, agentMessage);
    }


    @When("^User have to receive (.*) agent response as comment for (.*) tweet$")
    public void verifyAgentResponse(String expectedResponse, String targetTweet){
        boolean result = false;
            for (int i =0; i < 10; i++){
                openedTweet.closeTweet();
                try{
                    getTweetsSection().clickNewTweetsButtonIfShown(50);
                    if(targetTweet.contains("agent")||targetTweet.contains("support")){
                        targetTweet = getCurrentConnectToAgentTweetText();
                    }
                    result = checkAgentsResponse(expectedResponse, targetTweet);
                    if(result) break;
                    else openedTweet.waitFor(2000);
                } catch(StaleElementReferenceException e){
                    if(targetTweet.contains("agent")||targetTweet.contains("support")){
                        targetTweet = getCurrentConnectToAgentTweetText();
                    }
                    result = checkAgentsResponse(expectedResponse, targetTweet);
                    if(result) break;
                    else openedTweet.waitFor(2000);
                }
            }
        Assert.assertTrue(result, "Agent response for user is not shown as comment for tweet");
    }

    private boolean checkAgentsResponse(String expectedResponse, String targetTweet){
            openedTweet = getTweetsSection().clickTimeLineTweetWithText(targetTweet);
            if(openedTweet.ifAgentReplyShown(expectedResponse,1)) {
                return true;
            } else{
                return false;
            }

    }

    @Then("^User have to receive correct response \"(.*)\" on his message \"(.*)\"$")
    public void verifyDMTwitterResponse(String expectedResponse, String userMessage){
        SoftAssert soft = new SoftAssert();
        if (userMessage.contains("agent")||userMessage.contains("support")){
            userMessage = getCurrentConnectToAgentTweetText();
        }
        soft.assertTrue(getDmWindow().isTextResponseForUserMessageShown(userMessage),
                "There is no response on "+userMessage+" user message");
        soft.assertEquals(getDmWindow().getToUserResponse(userMessage), expectedResponse,
                "To user response is not as expected");
        soft.assertAll();
    }

    private static String createToAgentTweetText(){
        Faker faker = new Faker();
        int day = LocalDateTime.now().getDayOfMonth();
        if (day % 2 == 0) {
            if (invocationCount<1) tweetMessage = "chat to agent " +  faker.lorem().character();
            else tweetMessage= "connect to agent " + invocationCount +  faker.lorem().character();
            invocationCount ++;
        }
        else {
            if (invocationCount<1) tweetMessage = "chat to support " +  faker.lorem().character();
            else tweetMessage= invocationCount +" connect to support " + invocationCount + faker.lorem().character();
            invocationCount ++;
        }
        return tweetMessage;
    }

    public static String getCurrentConnectToAgentTweetText(){
        return tweetMessage;
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

    private TweetsSection getTweetsSection() {
        if (tweetsSection ==null) {
            tweetsSection = new TweetsSection();
            return tweetsSection;
        } else{
            return tweetsSection;
        }
    }

}