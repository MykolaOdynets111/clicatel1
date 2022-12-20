package steps;

import clients.Endpoints;
import datamodelsclasses.Providers.ProviderState;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import datamodelsclasses.Providers.GetProvider;
import org.testng.asserts.SoftAssert;
import steps.unitysteps.AbstractUnitySteps;
import api.ChatHubApiHelper;

import java.util.List;
import java.util.Map;

import static abstractclasses.IntegrationsAbstractSteps.getIntegrationsPage;
import static java.lang.String.format;

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

    @Given("User is able to GET providers API response")
    public void GETProviderAPI(int responseCode) {
        GetProvider getProvider = ChatHubApiHelper.getChatHubQuery(Endpoints.ADMIN_PROVIDERS, responseCode)
                .jsonPath().getList("", GetProvider.class).get(0);

        Assert.assertEquals(getProvider.getId(), "");
        Assert.assertEquals(getProvider.getName(), "Zendesk Support");
    }

    @Given("User is able to GET providers state in API response")
    public void GETProviderStateAPI(List<Map<String, String>> datatable) {
        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < datatable.size(); i++) {
            String providerId = datatable.get(i).get("i.ProviderID");
            String url = format(Endpoints.PROVIDERS_STATE, providerId);

            int responseCode = Integer.parseInt(datatable.get(i).get("o.resposecode"));
            if (responseCode == 200) {
                ProviderState getProvider = ChatHubApiHelper.getChatHubQuery(url, responseCode).as(ProviderState.class);
                softAssert.assertEquals(getProvider.getId(), datatable.get(i).get("o.providerid"));
                softAssert.assertEquals(getProvider.getName(), datatable.get(i).get("o.providername"));
                softAssert.assertEquals(getProvider.getIsActive(), datatable.get(i).get("o.status"));
                softAssert.assertAll();
            } else {
                String errorMessageText = datatable.get(i).get("o.errordescription");
                Assert.assertTrue(ChatHubApiHelper.getChatHubQuery(url, responseCode).asString().contains(errorMessageText),
                        "Error message is incorrect or not displayed");
            }
        }
    }
}