package steps;

import api.MainApi;
import clients.Endpoints;
import datamanager.jacksonschemas.chatextension.ChatExtension;
import datamodelsclasses.providers.ProviderState;
import datamodelsclasses.validator.Validator;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;
import datamodelsclasses.providers.AllProviders;
import api.ChatHubApiHelper;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
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
    public void GETProviderAPI(List<Map<String, String>> datatable) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> expectedProviders = new ArrayList<>();
        for (int i = 0; i < datatable.size(); i++) {
            try {
                expectedProviders.add(mapper.writeValueAsString(new AllProviders(datatable.get(i).get("o.id"),
                        datatable.get(i).get("o.name"),datatable.get(i).get("o.logoUrl")
                        ,datatable.get(i).get("o.description"),
                        datatable.get(i).get("o.moreInfoUrl"),datatable.get(i).get("o.vid"),
                        datatable.get(i).get("o.version"),datatable.get(i).get("o.latest"),
                        datatable.get(i).get("o.isAdded"))));

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        ObjectMapper mappergetProviders = new ObjectMapper();
        String getProviders = mappergetProviders.writeValueAsString(ChatHubApiHelper.getChatHubQuery(Endpoints.PROVIDERS, 200).as(AllProviders[].class));
        Assert.assertEquals(expectedProviders.toString(),getProviders , "Providers response is not as expected");
        }

    @Given("User is able to GET providers state in API response")
    public void GETProviderStateAPI(Map<String, String> dataMap) {
        String url = format(Endpoints.PROVIDERS_STATE, dataMap.get("i.providerID"));

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