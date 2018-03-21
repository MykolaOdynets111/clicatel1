package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import driverManager.ConfigManager;
import facebook.FBHomePage;
import facebook.FBTenantPage;
import facebook.uielements.MessengerWindow;

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

    private FBTenantPage getFbTenantPage() {
        if (fbTenantPage==null) {
            fbTenantPage = new FBTenantPage();
            return fbTenantPage;
        } else{
            return fbTenantPage;
        }
    }
}
