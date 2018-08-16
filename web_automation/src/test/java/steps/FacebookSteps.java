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
import org.testng.Assert;

public class FacebookSteps {

    FBTenantPage fbTenantPage;
    MessengerWindow messengerWindow;
    private static String fbMessage;

    @Given("^Open (.*) page$")
    public void openTenantPage(String tenant){
       FBHomePage.openTenantPage(URLs.getFBPageURL(tenant));
       if(tenant.equals("General Bank Demo")){
           Tenants.setTenantUnderTest("generalbank");
       }
    }

    @When("^User sends message regarding (.*)")
    public void sendMessage(String message){
//        messengerWindow.waitForWelcomeMessage(10);
        messengerWindow.enterMessage(createUniqueFBMessage(message));
    }

    @When("^User opens Messenger and send message regarding (.*)")
    public void openMessengerAndSendMessage(String message){
        messengerWindow = getFbTenantPage().openMessenger();
        messengerWindow.waitUntilLoaded();
//        messengerWindow.waitForWelcomeMessage(10);
        messengerWindow.enterMessage(createUniqueFBMessage(message));
    }


    @Then("^User have to receive the following on his message regarding (.*): \"(.*)\"$")
    public void verifyMessengerResponse(String userMessage, String expectedResponse) {
        if (expectedResponse.equalsIgnoreCase("agents_away")){
            expectedResponse = ApiHelper.getTenantMessageText("agents_away");
        }
        Assert.assertTrue(getMessengerWindow().isExpectedToUserMessageShown(getCurrentFBMessageText(), expectedResponse,30),
                "User does not receive response on the message \""+getCurrentFBMessageText()+"\" in FB messenger after 30 seconds wait.");
    }


    @When("^User makes post message with text (.*)$")
    public void makeAPOst(String postMessage) {
        getFbTenantPage().getPostFeed().makeAPost(postMessage);
    }

    @When("^Click \"View Post\" button$")
    public void clickViewPostButton(){
        getFbTenantPage().clickViewPostButton();
    }

    @Then("^User is shown \"(.*)\" on his message$")
    public void checkCommentResponse(String expectedResponse){
        getFbTenantPage().getLastVisitorPost().deletePost();
    }


    private static String createUniqueFBMessage(String baseMessage){
        Faker faker = new Faker();
        if(baseMessage.contains("thanks")) fbMessage=baseMessage;
        else fbMessage = baseMessage + " " + faker.lorem().character();
        return fbMessage;
    }

    public static String getCurrentFBMessageText(){
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


}
