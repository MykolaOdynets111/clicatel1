package steps;

import clients.Endpoints;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import datamodelsclasses.Providers.GetProvider;
import steps.unitysteps.AbstractUnitySteps;
import api.ChatHubApiHelper;

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

    @Given("User is able to GET providers in API response")
    public void GETProviderAPI() {
        GetProvider getProvider = ChatHubApiHelper.getChatHubQuery(Endpoints.ADMIN_PROVIDERS)
                .jsonPath().getList("", GetProvider.class).get(0);

        //Move it to step definiation
        Assert.assertNotNull(getProvider.getId());
        Assert.assertEquals(getProvider.getName(), "Zendesk Support");
    }
}
