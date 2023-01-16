package steps;

import api.MainApi;
import clients.Endpoints;
import datamodelsclasses.providers.ProviderState;
import datamodelsclasses.validator.Validator;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.common.mapper.TypeRef;
import org.testng.Assert;
import datamodelsclasses.providers.AllProviders;
import api.ChatHubApiHelper;

import java.util.*;

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
        AllProviders allProviders = ChatHubApiHelper.getChatHubQuery(Endpoints.ADMIN_PROVIDERS, responseCode)
                .jsonPath().getList("", AllProviders.class).get(0);

        Assert.assertEquals(allProviders.getId(), "");
        Assert.assertEquals(allProviders.getName(), "Zendesk Support");
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

    @Given("User is able to get all endpoint detail for Provider")
    public void userIsAbleToGetAllEndpointDetailForProvider(Map<String, String> dataMap) {

        String url = format(Endpoints.ADMIN_ENDPOINTS, dataMap.get("i.providerID"),dataMap.get("i.versionID"));
        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        if (responseCode == 200) {

            Map<String, Object> expectedEndpoints_0 = new HashMap<>();
            expectedEndpoints_0.put("id", dataMap.get("o.ID_0"));
            expectedEndpoints_0.put("name", dataMap.get("o.name_0"));

            Map<String, Object> expectedEndpoints_1 = new HashMap<>();
            expectedEndpoints_1.put("id", dataMap.get("o.ID_1"));
            expectedEndpoints_1.put("name", dataMap.get("o.name_1"));

            Map<String, Object> expectedEndpoints_2 = new HashMap<>();
            expectedEndpoints_2.put("id", dataMap.get("o.ID_2"));
            expectedEndpoints_2.put("name", dataMap.get("o.name_2"));

            Map<String, Object> expectedEndpoints_3 = new HashMap<>();
            expectedEndpoints_3.put("id", dataMap.get("o.ID_3"));
            expectedEndpoints_3.put("name", dataMap.get("o.name_3"));

            Map<String, Object> expectedEndpoints_4 = new HashMap<>();
            expectedEndpoints_4.put("id", dataMap.get("o.ID_4"));
            expectedEndpoints_4.put("name", dataMap.get("o.name_4"));

            Map<String, Object> expectedEndpoints_5 = new HashMap<>();
            expectedEndpoints_5.put("id", dataMap.get("o.ID_5"));
            expectedEndpoints_5.put("name", dataMap.get("o.name_5"));

            Map<String, Object> expectedEndpoints_6 = new HashMap<>();
            expectedEndpoints_6.put("id", dataMap.get("o.ID_6"));
            expectedEndpoints_6.put("name", dataMap.get("o.name_6"));

            List<Map<String, Object>> expectedEndpoints = new ArrayList<>();
            expectedEndpoints.add(expectedEndpoints_0);
            expectedEndpoints.add(expectedEndpoints_1);
            expectedEndpoints.add(expectedEndpoints_2);
            expectedEndpoints.add(expectedEndpoints_3);
            expectedEndpoints.add(expectedEndpoints_4);
            expectedEndpoints.add(expectedEndpoints_5);
            expectedEndpoints.add(expectedEndpoints_6);
            List<Map<String, Object>> getActualEndpoints = ChatHubApiHelper.getChatHubQueryWithoutAuth(url, responseCode).as(new TypeRef<List<Map<String, Object>>>(){});
            System.out.println(getActualEndpoints);

            Assert.assertEquals(expectedEndpoints, getActualEndpoints, "Providers response is not as expected");
            } else {
            Validator.validatedErrorResponseWithoutAuth(url, dataMap);
        }
    }

}