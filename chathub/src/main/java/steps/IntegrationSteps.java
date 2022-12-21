package steps;

import api.MainApi;
import clients.Endpoints;
import datamodelsclasses.providers.ProviderState;
import datamodelsclasses.validator.Validator;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import datamodelsclasses.providers.GetProvider;
import api.ChatHubApiHelper;


import java.util.Map;

import static abstractclasses.IntegrationsAbstractSteps.getIntegrationsPage;
import static java.lang.String.format;

public class IntegrationSteps  extends MainApi {

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

    @Given("User is able to GET providers API response")
    public void GETProviderAPI(int responseCode) {
        GetProvider getProvider = ChatHubApiHelper.getChatHubQuery(Endpoints.ADMIN_PROVIDERS, responseCode)
                .jsonPath().getList("", GetProvider.class).get(0);

        Assert.assertEquals(getProvider.getId(), "");
        Assert.assertEquals(getProvider.getName(), "Zendesk Support");
    }

    @Given("User is able to GET providers state in API response")
    public void GETProviderStateAPI(Map<String, String> dataMap) {

        String url = format(Endpoints.PROVIDERS_STATE, dataMap.get("i.o.providerID"));

        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        if (responseCode == 200) {
            ProviderState expectedProviderState = new ProviderState(dataMap);
            ProviderState getProvider = ChatHubApiHelper.getChatHubQuery(url, responseCode).as(ProviderState.class);
            Assert.assertEquals(expectedProviderState, getProvider, "Providers response is not as expected");
        } else {

            Validator.validatedErrorResponse(url, dataMap);
        }

    }
}