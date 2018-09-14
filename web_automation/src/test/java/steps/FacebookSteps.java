package steps;

import api_helper.ApiHelper;
import com.github.javafaker.Faker;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataManager.Tenants;
import driverManager.URLs;
import facebook.FBHomePage;
import facebook.FBTenantPage;
import facebook.uielements.MessengerWindow;
import facebook.uielements.YourPostWindow;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import javax.annotation.concurrent.GuardedBy;
import java.util.Random;

public class FacebookSteps {

    private FBTenantPage fbTenantPage;
    private MessengerWindow messengerWindow;
    private YourPostWindow yourPostWindow;
    @GuardedBy("this") private static String fbMessage;

    @Given("^Open (.*) page$")
    public void openTenantPage(String tenant){
       FBHomePage.openTenantPage(URLs.getFBPageURL(tenant));
       if(tenant.equals("General Bank Demo")){
           Tenants.setTenantUnderTest("generalbank");
           Tenants.setTenantUnderTestOrgName("General Bank Demo");
       }
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
        Assert.assertTrue(getMessengerWindow().isExpectedToUserMessageShown(getCurrentUserMessageText(), expectedResponse,30),
                "User does not receive response on the message \""+ getCurrentUserMessageText()+"\" in FB messenger after 30 seconds wait.");
    }


    @When("^User makes post message regarding (.*)$")
    public void makeAPost(String postMessage) {
        getFbTenantPage().getPostFeed().makeAPost(createUniqueUserMessage(postMessage));
    }

    @Then("^Post response arrives$")
    public void checkThatPostResponseArrives(){
        Assert.assertTrue(getFbTenantPage().isYourPostWindowOpened(20),
                "New window 'Your post' with user's post and the answer is not shown.");

    }

    @Then("^User initial message regarding (.*) with following bot response '(.*)' in comments are shown$")
    public void verifyResponseOnUserPost(String userInitialPost, String expectedMessage){
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getYourPostWindow().isYourPostWindowContainsInitialUserPostText(getCurrentUserMessageText()),
                "User initial post '"+getCurrentUserMessageText()+"' is not shown in 'Your post' window\n");
        soft.assertTrue(getYourPostWindow().isExpectedResponseShownInComments(expectedMessage),
                "Expected '"+expectedMessage+"' response is not shown in comments");
        soft.assertAll();
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

    private YourPostWindow getYourPostWindow() {
        if (yourPostWindow==null) {
            yourPostWindow = getFbTenantPage().getYourPostWindow();
            return yourPostWindow;
        } else{
            return yourPostWindow;
        }
    }
}
