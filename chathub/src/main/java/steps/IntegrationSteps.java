package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.json.JSONException;
import org.junit.Assert;
import restHandler.repo.Authentication;
import steps.unitysteps.AbstractUnitySteps;
import restHandler.RestHandler_ChatHub;

import static abstractclasses.IntegrationsAbstractSteps.getIntegrationsPage;

public class IntegrationSteps extends AbstractUnitySteps {

    RestHandler_ChatHub rh = new RestHandler_ChatHub();
    Authentication a = new Authentication();
    String auth;

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

    @Given("User is able to get the auth token")
    public String userIsAbleToGetTheAuthToken() {
        auth = a.getAuthToken("https://dev-platform.clickatelllabs.com/auth/accounts", "chat2payqauser11+chathub@gmail.com", "Password#1");
        return auth;
    }

    @And("User is able to execute GET provider API")
    public void GETProviderAPI() {
        rh.getProviders(auth, "https://demo-chathub-config-manager.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com/admin/providers");
    }
}
