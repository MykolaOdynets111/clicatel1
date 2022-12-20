package steps;

import apihelper.ApiHelper;
import com.github.javafaker.Faker;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import datamanager.Tenants;
import driverfactory.DriverFactory;
import driverfactory.URLs;
import facebook.FBHomePage;
import facebook.FBLoginPage;
import facebook.FBTenantPage;
import facebook.uielements.MessengerWindow;
import facebook.FBYourPostPage;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import javax.annotation.concurrent.GuardedBy;

public class FacebookSteps {

    private FBTenantPage fbTenantPage;
    private MessengerWindow messengerWindow;
    private FBYourPostPage FBYourPostPage;
    @GuardedBy("this") private static String fbMessage;

    @Given("Login to fb")
    public void loginToFb(){
        FBLoginPage.openFacebookLoginPage(DriverFactory.getTouchDriverInstance()).loginUser();
    }


    @Given("^Open (.*) page$")
    public void openTenantPage(String tenantOrgName){
       FBHomePage.openTenantPage(DriverFactory.getTouchDriverInstance(), URLs.getFBPageURL(tenantOrgName));
       Tenants.setTenantUnderTestNames(tenantOrgName);
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
        String welcomeMessage = ApiHelper.getAutoResponderMessageText("welcome_message");

        messengerWindow.waitForWelcomeMessage(welcomeMessage, 10);
        messengerWindow.enterMessage(createUniqueUserMessage(message));
    }

    @Then("^User have to receive the following on his message regarding (.*): \"(.*)\"$")
    public void verifyMessengerResponse(String userMessage, String expectedResponse) {
        if (expectedResponse.equalsIgnoreCase("agents_away")){
            expectedResponse = ApiHelper.getAutoResponderMessageText("agents_away");
        }
        // ToDo: Update timeout when it is specified in System timeouts page on Confluence
        boolean isExpectedMessageIsShown = getMessengerWindow().isExpectedToUserMessageShown(getCurrentUserMessageText(), expectedResponse,60);
        Assert.assertTrue(isExpectedMessageIsShown,
                "User does not receive response on the message \""+ getCurrentUserMessageText()+"\" in FB messenger after 60 seconds wait.");
    }


    @Then("^Facebook user has not received (.*) responce$")
    public void verifyMessengerAbsent(String expectedResponse) {
        Assert.assertFalse(getMessengerWindow().isExpectedToUserMessageShown(getCurrentUserMessageText(),
                expectedResponse,2),expectedResponse + " response should not be shown");
    }

    @When("^User makes post message regarding (.*)$")
    public void makeAPost(String postMessage) {
        getFbTenantPage().getPostFeed().makeAPost(createUniqueUserMessage(postMessage));
    }

    @Then("^Post response arrives$")
    public void checkThatPostResponseArrives(){
        // ToDo: update timeout after it is provided in System timeouts confluence page
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
        if(baseMessage.contains("thanks") || baseMessage.equalsIgnoreCase("//end")
                || baseMessage.equalsIgnoreCase("//stop") ) {
            fbMessage=baseMessage;
        } else {
            fbMessage = baseMessage + new Faker().letterify("??");
        }
        return fbMessage;
    }

    public synchronized static String getCurrentUserMessageText(){
        return fbMessage;
    }



    private FBTenantPage getFbTenantPage() {
        if (fbTenantPage==null) {
            fbTenantPage = new FBTenantPage(DriverFactory.getTouchDriverInstance());
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
