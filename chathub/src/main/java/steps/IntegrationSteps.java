package steps;

import api.MainApi;
import clients.Endpoints;
import datamodelsclasses.configurations.ActivateConfiguration;
import datamodelsclasses.configurations.ActivateConfigurationBody;
import datamodelsclasses.providers.*;
import datamodelsclasses.validator.Validator;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;
import api.ChatHubApiHelper;
import org.testng.asserts.SoftAssert;
import urlproxymanager.Proxymanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
        Proxymanager proxy = new Proxymanager();

        ObjectMapper mapper = new ObjectMapper();
        List<String> activateConfigBody = new ArrayList<>();
        activateConfigBody.add(mapper.writeValueAsString(new ActivateConfigurationBody(
                dataMap.get("i.name"), dataMap.get("i.clientSecret"),
                dataMap.get("i.clientId"), dataMap.get("i.host"),
                dataMap.get("i.providerId"), dataMap.get("i.type"))));
        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        if (responseCode == 200) {
            ActivateConfiguration postActiveConfiguration = ChatHubApiHelper.postChatHubQuery(url, activateConfigBody).as(ActivateConfiguration.class);
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertNotNull(postActiveConfiguration.getId(), "Configuration Id is empty");
            softAssert.assertEquals(dataMap.get("o.type"), postActiveConfiguration.getType());
            softAssert.assertEquals(dataMap.get("o.setupName"), postActiveConfiguration.getSetupName());
            softAssert.assertNotNull(postActiveConfiguration.getCreatedDate(), "CurrentDate is Empty");
            softAssert.assertNotNull(postActiveConfiguration.getModifiedDate(), "Modfied Date is empty");

            //Checking of authentication link will be fixed later
            //Check the authorization link
 /*           String printLink = postActiveConfiguration.authenticationLink;
            System.out.println(printLink);*/
 /*          int code = proxy.addProxy("https://dev-mc2-authentication-front-end-service.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com/authorize/request/a6bad634-8d45-4165-a2a0-13e288ce8564");
            Assert.assertEquals(Integer.parseInt(dataMap.get("o.authenticationLink")), code);*/
            softAssert.assertEquals(dataMap.get("o.timeToExpire"), postActiveConfiguration.getTimeToExpire());
            softAssert.assertAll();
        } else {
            Validator.validatedErrorResponseforPost(url, (Map<String, String>) activateConfigBody, dataMap);
        }
    }

    @Given("Admin is able to GET providers API response")
    public void adminIsAbleToGETProvidersAPIResponse(List<Map<String, String>> datatable) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> expectedProviders = new ArrayList<>();
        for (int i = 0; i < datatable.size(); i++) {
            try {
                expectedProviders.add(mapper.writeValueAsString(new ConfiguredProviderDetail(datatable.get(i).get("o.id"),
                        datatable.get(i).get("o.name"),datatable.get(i).get("o.logoUrl")
                        ,datatable.get(i).get("o.description"),
                        datatable.get(i).get("o.moreInfoUrl"),datatable.get(i).get("o.vid"),
                        datatable.get(i).get("o.version"),datatable.get(i).get("o.latest")
                        )));
            } catch (org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        ObjectMapper mappergetProviders = new ObjectMapper();
        String getProviders = mappergetProviders.writeValueAsString(ChatHubApiHelper.getChatHubQueryWithoutAuthToken(Endpoints.ADMIN_PROVIDERS, 200).as(ConfiguredProviderDetail[].class));
        Assert.assertEquals(expectedProviders.toString(),getProviders , "Providers response is not as expected");
    }

    @Given("Admin is able to GET existing provider details")
    public void adminIsAbleToGETExistingProviderDetails(Map<String, String> dataMap) {
        String url = format(Endpoints.ADMIN_PROVIDER_DETAILS, dataMap.get("i.providerId"));
        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        if (responseCode == 200) {
            ProviderDetails expectedProviderState = new ProviderDetails(dataMap);
            ProviderDetails getProvider = ChatHubApiHelper.getChatHubQuery(url, responseCode).as(ProviderDetails.class);
            Assert.assertEquals(expectedProviderState, getProvider, "Providers response is not as expected");
        } else {
            Validator.validatedErrorResponseforGet(url, dataMap);
        }
    }

    @Given("Admin is able to GET configured provider for customer")
    public void adminIsAbleToGETConfiguredProviderForCustomer(List<Map<String, String>> datatable) throws JsonProcessingException {
        String url = format(Endpoints.ADMIN_CONFIGURED_PROVIDER_DETAILS,datatable.get(0).get("i.mc2AccountId"));
        ObjectMapper mapper = new ObjectMapper();
        List<String> expectedConfiguredProviderDetails = new ArrayList<>();
        for (int i = 0; i < datatable.size(); i++) {
            try {
                expectedConfiguredProviderDetails.add(mapper.writeValueAsString(new ConfiguredProviderDetail(datatable.get(i).get("o.id"),
                        datatable.get(i).get("o.name"),datatable.get(i).get("o.logoUrl")
                        ,datatable.get(i).get("o.description"),
                        datatable.get(i).get("o.moreInfoUrl"),datatable.get(i).get("o.vid"),
                        datatable.get(i).get("o.version"),datatable.get(i).get("o.latest"))));
            } catch (org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        ObjectMapper ActualConfiguredProviderDetails = new ObjectMapper();
        String getProviders = ActualConfiguredProviderDetails.writeValueAsString(ChatHubApiHelper.getChatHubQueryWithoutAuthToken(url, 200).as(ConfiguredProviderDetail[].class));
        Assert.assertEquals(expectedConfiguredProviderDetails.toString(),getProviders , "Providers response is not as expected");
    }

    @Given("Admin is able to create provider")
    public void adminIsAbleToCreateProvider(Map<String, String> dataMap) throws JsonProcessingException {
        String url = format(Endpoints.ADMIN_PROVIDERS);
        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        Map<String, String> createProviderBody = new LinkedHashMap<>();
        createProviderBody.put("name", dataMap.get("i.name"));
        createProviderBody.put("logoUrl", dataMap.get("i.logoUrl"));
        createProviderBody.put("description", dataMap.get("i.description"));
        createProviderBody.put("moreInfoUrl", dataMap.get("i.moreInfoUrl"));

        if (responseCode == 201) {
            ProviderDetails expectedCreateProviderDetails = new ProviderDetails(dataMap);
            ProviderDetails getCreatedProviderDetails = ChatHubApiHelper.postChatHubQueryWithoutAuth(url, createProviderBody, responseCode).as(ProviderDetails.class);
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertNotNull(getCreatedProviderDetails.getId(), "Provider Id is empty");
            softAssert.assertEquals(expectedCreateProviderDetails.getName(), getCreatedProviderDetails.getName(), "Names field does not match");
            softAssert.assertEquals(expectedCreateProviderDetails.getLogoUrl(), getCreatedProviderDetails.getLogoUrl(), "LogoUrl field does not match");
            softAssert.assertEquals(expectedCreateProviderDetails.getDescription(), getCreatedProviderDetails.getDescription(), "Description field does not match");
            softAssert.assertEquals(expectedCreateProviderDetails.getMoreInfoUrl(), getCreatedProviderDetails.getMoreInfoUrl(), "MoreInfoUrl field does not match");
            softAssert.assertAll();
        } else {
            Validator.validatedErrorResponseforGet(url, dataMap);
        }
    }
}
