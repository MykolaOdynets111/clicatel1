package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import steps.unitysteps.AbstractUnitySteps;
import restHandler.RestHandler_ChatHub;

import static abstractclasses.IntegrationsAbstractSteps.getIntegrationsPage;

public class IntegrationSteps extends AbstractUnitySteps {

    @And("I click on Zendesk Integrations Card")
    public void openIntegrationsCard() {
        getIntegrationsPage().clickOnZendeskSupport();
    }

    @Then("Number of available integrations is displayed on integrations card")
    public void numberOfIntegrationsIsDisplayed() {
        Assert.assertTrue(getIntegrationsPage().availableIntegrationsDisplayed());
    }

    @Then("Integrations card is displayed as first card")
    public void integrationsCardIsDisplayedAsFirstCard() {
        String firstCard = getIntegrationsPage().integrationsIsFirstCard();
        Assert.assertEquals("Integrations", firstCard,
                "Integrations is First Card");
    }

    @Given("User is able to execute GET provider API")
    public void userIsAbleToExecuteGETProviderAPI() {
        RestHandler_ChatHub rh = new RestHandler_ChatHub();
        rh.get("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJpZFwiOlwiMzFhZTQ1ODQ5MWVlNDhkNjhiNDVhMmVlNzMyNDlkNTJcIixcImlzQWRtaW5cIjpmYWxzZX0iLCJleHAiOjE2NzEzODMyODQsImlhdCI6MTY3MDE3MzY4NH0.XPNqaV0YyF30sKWhZJc_rRXXmOCBXMBqoxsuBROQvoU", "https://demo-chathub-config-manager.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com/admin/providers");
    }
}
