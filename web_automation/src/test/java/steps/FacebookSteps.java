package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import driverManager.ConfigManager;
import facebook.FBHomePage;
import facebook.FBTenantPage;
import facebook.uielements.MessengerWindow;
import org.testng.Assert;

import java.util.List;

public class FacebookSteps {

    FBTenantPage fbTenantPage;
    MessengerWindow messengerWindow;

    @Given("^Open (.*) page$")
    public void openTenantPage(String tenant){
       FBHomePage.openTenantPage(tenant, ConfigManager.getEnv());
    }

    @When("^Open Messenger and send (.*) message$")
    public void clickSendMessageButton(String message){
        messengerWindow = getFbTenantPage().openMessanger();
        messengerWindow.waitUntilLoaded();
        messengerWindow.enterMessage(message);
    }

    @Then("^User have to receive the following on his message (.*): \"(.*)\"$")
    public void verifyMessengerResponse(String userMessage, String expectedResponse) {
       List<String> actualResponse = getMessengerWindow().getToUserResponse(userMessage);
        Assert.assertEquals("","");
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
