package steps;

import apihelper.ApiHelper;
import com.github.javafaker.Faker;
import datamanager.Tenants;
import driverfactory.DriverFactory;
import driverfactory.URLs;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.StaleElementReferenceException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import twitter.TweetsSection;
import twitter.TwitterHomePage;
import twitter.TwitterLoginPage;
import twitter.TwitterTenantPage;
import twitter.uielements.DMWindow;
import twitter.uielements.OpenedTweet;
import twitter.uielements.SendNewTweetWindow;

import java.time.LocalDateTime;

public class TwitterSteps {

    private TwitterTenantPage twitterTenantPage;
    private DMWindow dmWindow;
    private SendNewTweetWindow tweetWindow;
    private TweetsSection tweetsSection;
    private OpenedTweet openedTweet;
    private static int invocationCount = 0;
    private static String tweetMessage;

    @Given("Login to twitter")
    public void loginToTwitter(){
        TwitterLoginPage.openTwitterLoginPage(DriverFactory.getTouchDriverInstance()).loginUser();
    }

    @Given("^Open twitter page of (.*)$")
    public void openTwitterPage(String tenantOrgName){
        TwitterHomePage.openTenantPage(DriverFactory.getTouchDriverInstance(), URLs.getTwitterURL(tenantOrgName));
        getTwitterTenantPage().refreshPageIfCookiesWereNotSet();
        Tenants.setTenantUnderTestNames(tenantOrgName);
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
        }else {
            userMessage = FacebookSteps.createUniqueUserMessage(userMessage);
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

    @Then("^(?:Agent's|Bot's) answer arrives to twitter$")
    public void verifyReceivingAnswerInTimelineFromAgent(){
        // ToDo: Update timeout when it is specified in System timeouts page on Confluence
        Assert.assertTrue(getTweetsSection().verifyFromAgentTweetArrives(100),
                "Expected tweet answer from the agent is missing after 100 secs wait");
    }

    @Then("^User has to receive \"(.*)\" answer from the (?:agent|bot) as a comment on his initial tweet (.*)$")
    public void verifyFromAgentResponseAsACommentOnTweet(String expectedAgentMessage, String initialUserTweet){
        getTwitterTenantPage().getTwitterHeader().openHomePage().waitForPageToBeLoaded();
        if(initialUserTweet.contains("agent")||initialUserTweet.contains("support")){
            initialUserTweet = getCurrentConnectToAgentTweetText();
        }
        openedTweet = getTweetsSection().clickTimeLineTweetWithText(initialUserTweet);
        // ToDo: Update timeout when it is specified in System timeouts page on Confluence
        Assert.assertTrue(openedTweet.ifAgentReplyShown(expectedAgentMessage,15),
                "Expected response "+expectedAgentMessage+" for user is not shown as comment for tweet");
    }


    @When("^He clicks \"(.*)\" tweet$")
    public void openTweet(String expectedAnswer){
        openedTweet = getTweetsSection().clickTimeLineTweetWithText(expectedAnswer);
    }

    @When("^Send \"(.*)\" reply on agent's tweet \"(.*)\"$")
    public void sendResponseIntoTweet(String replyMessage, String agentMessage ){
        openedTweet.closeTweet();
        openedTweet = getTweetsSection().clickTimeLineTweetWithText(agentMessage);
        openedTweet.sendReply(replyMessage, agentMessage);
    }


    @When("^User have to receive (.*) agent response as comment for (.*) tweet$")
    public void verifyAgentResponse(String expectedResponse, String targetTweet){
        boolean result = false;
            for (int i =0; i < 10; i++){ // clarify_timeout
                openedTweet.closeTweet();
                try{
                    // ToDo: Update timeout when it is specified in System timeouts page on Confluence
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
        return openedTweet.ifAgentReplyShown(expectedResponse, 1);
    }

    private String getExpectedResp(String userMessage){
        if (userMessage.contains("agent")||userMessage.contains("support")){
            return getCurrentConnectToAgentTweetText();
        }else {
            return FacebookSteps.getCurrentUserMessageText();
        }
    }

    /**
     * Method to verify that response arrived right after the user's message
     * @param expectedResponse
     * @param userMessage
     */
    @Then("^User have to receive correct response \"(.*)\" on his message \"(.*)\"$")
    public void verifyDMTwitterResponse(String expectedResponse, String userMessage){
        SoftAssert soft = new SoftAssert();
        userMessage = getExpectedResp(userMessage);

        soft.assertTrue(getDmWindow().isTextResponseForUserMessageShown(userMessage),
                "There is no response on "+userMessage+" user message");
        soft.assertTrue(getDmWindow().getToUserResponse(userMessage).contains(expectedResponse),
                "To user response is not as expected \n" +
        "Actual message: " + getDmWindow().getToUserResponse(userMessage) + "\n" +
        "Expected message: " + expectedResponse);
        soft.assertAll();
    }

    @Then("^User has not received \"(.*)\" response on his message \"(.*)\"$")
    public void isResponseNotShown(String response, String userMessage){
        Assert.assertFalse(getDmWindow().getToUserResponse(userMessage).contains(response),
                response + " response should not be shown");
    }

    /**
     * Method to verify that response arrived regardless its position
     * @param expectedResponse
     * @param userMessage
     */
    @Then("^User have to receive response \"(.*)\" on his message \"(.*)\"$")
    public void verifyDMTwitterResponseAmongOthers(String expectedResponse, String userMessage){
        userMessage = getExpectedResp(userMessage);
        Assert.assertTrue(getDmWindow().isTextResponseForUserMessageShown(userMessage, expectedResponse),
                "There is no '" + expectedResponse + "' response on "+userMessage+" user message");
    }

    @Then("^User have to receive (.*) auto responder on his message \"(.*)\"$")
    public void verifyAutoResponder(String autoResponder, String userMessage){
        userMessage = getExpectedResp(userMessage);
        String expectedResponse = ApiHelper.getAutoResponderMessageText(autoResponder);

        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getDmWindow().isTextResponseForUserMessageShown(userMessage),
                "There is no response on "+userMessage+" user message");
        soft.assertTrue(getDmWindow().getToUserResponse(userMessage).contains(expectedResponse),
                "To user response is not as expected \n" +
                        "Actual message: " + getDmWindow().getToUserResponse(userMessage) + "\n" +
                        "Expected message: " + expectedResponse);
        soft.assertAll();
    }

    @Then("^User should see (.*) response on his message \"(.*)\"$")
    public void verifyTextResponseAmongOthers(String expectedMessage, String userMessage){
        userMessage = getExpectedResp(userMessage);

        Assert.assertTrue(getDmWindow().getToUserResponses(userMessage).contains(expectedMessage),
                "To user response is not as expected \n" +
                        "Actual message: " + getDmWindow().getToUserResponses(userMessage).toString() + "\n" +
                        "Expected message: " + expectedMessage);
    }

    private static String createToAgentTweetText(){
        Faker faker = new Faker();
        int day = LocalDateTime.now().getDayOfMonth();
        if (day % 2 == 0) {
            if (invocationCount<1) tweetMessage = "chat to agent" +  faker.letterify("??");
            else tweetMessage= "connect to agent" +  faker.lorem().character();
            invocationCount ++;
        }
        else {
            if (invocationCount<1) tweetMessage = "chat to support" +  faker.letterify("??");
            else tweetMessage= "connect to support" + invocationCount +  faker.letterify("??");
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
            twitterTenantPage = new TwitterTenantPage(DriverFactory.getTouchDriverInstance());
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

    private SendNewTweetWindow getTweetWindow() {
        if (tweetWindow==null) {
            tweetWindow = getTwitterTenantPage().getTweetWindow();
            return tweetWindow;
        } else{
            return tweetWindow;
        }
    }

    private TweetsSection getTweetsSection() {
        if (tweetsSection ==null) {
            tweetsSection = new TweetsSection(DriverFactory.getTouchDriverInstance());
            return tweetsSection;
        } else{
            return tweetsSection;
        }
    }

}
