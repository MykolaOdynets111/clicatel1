package steps;

import apihelper.ApiHelper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.Tenants;
import drivermanager.URLs;
import facebook.FBHomePage;
import facebook.FBTenantPage;
import facebook.uielements.MessengerWindow;
import facebook.FBYourPostPage;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import javax.annotation.concurrent.GuardedBy;
import java.util.Random;

public class FacebookSteps {

    private FBTenantPage fbTenantPage;
    private MessengerWindow messengerWindow;
    private FBYourPostPage FBYourPostPage;
    @GuardedBy("this") private static String fbMessage;

    @Given("^Open (.*) page$")
    public void openTenantPage(String tenant){
       FBHomePage.openTenantPage(URLs.getFBPageURL(tenant));
       Tenants.setTenantUnderTestNames(tenant);
    }

    @When("^User sends message regarding (.*)")
    public void sendMessage(String message){
//        messengerWindow.waitForWelcomeMessage(10);
        messengerWindow.enterMessage(createUniqueUserMessage(message));
    }

    @When("^User opens Messenger and send message regarding (.*)")
    public void openMessengerAndSendMessage(String message){
        messengerWindow = getFbTenantPage().openMessenger();
        messengerWindow.waitUntilLoaded();
//        messengerWindow.waitForWelcomeMessage(10);
        messengerWindow.enterMessage(createUniqueUserMessage(message));
    }


    @Then("^User have to receive the following on his message regarding (.*): \"(.*)\"$")
    public void verifyMessengerResponse(String userMessage, String expectedResponse) {
        if (expectedResponse.equalsIgnoreCase("agents_away")){
            expectedResponse = ApiHelper.getTenantMessageText("agents_away");
        }
        boolean isExpectedMessageIsShown = getMessengerWindow().isExpectedToUserMessageShown(getCurrentUserMessageText(), expectedResponse,60);
        Assert.assertTrue(isExpectedMessageIsShown,
                "User does not receive response on the message \""+ getCurrentUserMessageText()+"\" in FB messenger after 30 seconds wait.");
    }


    @When("^User makes post message regarding (.*)$")
    public void makeAPost(String postMessage) {
        getFbTenantPage().getPostFeed().makeAPost(createUniqueUserMessage(postMessage));
    }

    @Then("^Post response arrives$")
    public void checkThatPostResponseArrives(){
        Assert.assertTrue(getFbTenantPage().isNotificationAboutNewCommentArrives(40),
                "Notification about new post reply is not shown.");

    }

    @When("^User sends a new post regarding (.*) in the same conversation$")
    public void postIntoTheSameFBBranch(String userText){
        if(getFbTenantPage().isNotificationAboutNewCommentArrives(2)) getFbTenantPage().clickNewCommentNotification();
        getFbTenantPage().waitForNewPostNotificationToDisappear();
        getFBYourPostPage().makeASecondPostInBranch(createUniqueUserMessage(userText));
    }

    @When("^Delete users post$")
    public void deleteUserPost(){
        getFbTenantPage().getFBYourPostPage().deletePost();
    }

    @Then("^User initial message regarding (.*) with following (?:bot|agent) response '(.*)' in comments are shown$")
    public void verifyResponseOnUserPost(String userInitialPost, String expectedMessage){
        getFbTenantPage().clickNewCommentNotification();
//        getFbTenantPage().closeYourPost
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getFBYourPostPage().isYourPostWindowContainsInitialUserPostText(getCurrentUserMessageText()),
                "User initial post '"+getCurrentUserMessageText()+"' is not shown in 'Your post' window\n");
        soft.assertTrue(getFBYourPostPage().isExpectedResponseShownInComments(expectedMessage),
                "Expected '"+expectedMessage+"' response is not shown in comments");
        soft.assertAll();
    }

    @Then("^(?:Bot|Agent) responds with '(.*)' on user additional question regarding (.*)$")
    public void verifyBotResponseOnAdditionalUserPostInComments(String expectedResponse, String userPost){
        getFbTenantPage().clickNewCommentNotification();
        Assert.assertTrue(getFBYourPostPage().isExpectedResponseShownInSecondLevelComments(getCurrentUserMessageText(), expectedResponse),
                "Bot response in user comment is not shown");
    }

    @When("^Click \"View Post\" button$")
    public void clickViewPostButton(){
        getFbTenantPage().clickViewPostButton();
    }

    @Then("^User is shown \"(.*)\" on his message$")
    public void checkCommentResponse(String expectedResponse){
        getFbTenantPage().getLastVisitorPost().deletePost();
    }


    public synchronized static String createUniqueUserMessage(String baseMessage){
        if(baseMessage.contains("thanks")) fbMessage=baseMessage;
        else {
            Random rnd = new Random();
            char c = (char) (rnd.nextInt(26) + 'a');
            fbMessage = baseMessage + c;
        }
        return fbMessage;
    }

    public synchronized static String getCurrentUserMessageText(){
        return fbMessage;
    }



    private FBTenantPage getFbTenantPage() {
        if (fbTenantPage==null) {
            fbTenantPage = new FBTenantPage();
            return fbTenantPage;
        } else{
            return fbTenantPage;
        }
    }

    private MessengerWindow getMessengerWindow() {
        if (messengerWindow==null) {
            messengerWindow = getFbTenantPage().getMessengerWindow();
            return messengerWindow;
        } else{
            return messengerWindow;
        }
    }

    private FBYourPostPage getFBYourPostPage() {
        if (FBYourPostPage ==null) {
            FBYourPostPage = getFbTenantPage().getFBYourPostPage();
            return FBYourPostPage;
        } else{
            return FBYourPostPage;
        }
    }
}
