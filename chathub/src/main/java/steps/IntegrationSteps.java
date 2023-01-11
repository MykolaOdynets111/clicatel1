package steps;

import api.MainApi;
import clients.Endpoints;
import datamodelsclasses.configurations.ActivateConfiguration;
import datamodelsclasses.providers.ProviderState;
import datamodelsclasses.validator.Validator;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import datamodelsclasses.providers.AllProviders;
import api.ChatHubApiHelper;
import urlproxymanager.ProxyManager;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static abstractclasses.IntegrationsAbstractSteps.getIntegrationsPage;
import static java.lang.String.format;

public class IntegrationSteps extends MainApi {

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
            Validator.validatedErrorResponseforGet(url, dataMap);
        }
    }

    @Given("User is able to activate configuration for a provider")
    public void userIsAbleToActivateConfigurationForAProvider(Map<String, String> dataMap) throws IOException {
        String url = format(Endpoints.ACTIVATE_CONFIGURATION);
        ProxyManager proxy = new ProxyManager();
        Map<String, String> body = new LinkedHashMap<>();
        body.put("name", dataMap.get("i.name"));
        body.put("clientSecret", dataMap.get("i.clientSecret"));
        body.put("clientId", dataMap.get("i.clientId"));
        body.put("host", dataMap.get("i.host"));
        body.put("providerId", dataMap.get("i.providerId"));
        body.put("type", dataMap.get("i.type"));
        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        if (responseCode == 200) {
            ActivateConfiguration postActiveConfiguration = ChatHubApiHelper.postChatHubQuery(url, body).as(ActivateConfiguration.class);
            Assert.assertNotNull(postActiveConfiguration.getId(), "Configuration Id is empty");
            Assert.assertEquals(dataMap.get("o.type"), postActiveConfiguration.getType());
            Assert.assertEquals(dataMap.get("o.setupName"), postActiveConfiguration.getSetupName());
            Assert.assertNotNull(postActiveConfiguration.getCreatedDate(), "CurrentDate is Empty");
            Assert.assertNotNull(postActiveConfiguration.getModifiedDate(), "Modfied Date is empty");

            //Check the authorization link
 /*           String printLink = postActiveConfiguration.authenticationLink;
            System.out.println(printLink);*/
            int code = proxy.addProxy("https://dev-mc2-authentication-front-end-service.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com/authorize/request/a6bad634-8d45-4165-a2a0-13e288ce8564");
            Assert.assertEquals(Integer.parseInt(dataMap.get("o.authenticationLink")), code);
            Assert.assertEquals(dataMap.get("o.timeToExpire"), postActiveConfiguration.getTimeToExpire());
        } else {
            Validator.validatedErrorResponseforPost(url, body, dataMap);
        }
    }
}