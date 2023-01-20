package steps;

import api.MainApi;
import clients.Endpoints;
import datamodelsclasses.configurations.ActivateConfiguration;
import datamodelsclasses.providers.*;
import datamodelsclasses.configurations.ConfigurationSecrets;
import datamodelsclasses.configurations.ConfigurationState;
import datamodelsclasses.providers.UpdatedProviderDetails;
import datamodelsclasses.configurations.*;
import datamodelsclasses.providers.ProviderState;
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

        Map<String, String> configurationBody = new LinkedHashMap<>();
        configurationBody.put("name", dataMap.get("i.name"));
        configurationBody.put("clientSecret", dataMap.get("i.clientSecret"));
        configurationBody.put("clientId", dataMap.get("i.clientId"));
        configurationBody.put("host", dataMap.get("i.host"));
        configurationBody.put("providerId", dataMap.get("i.providerId"));
        configurationBody.put("type", dataMap.get("i.type"));

        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        if (responseCode == 200) {
            ActivateConfiguration postActiveConfiguration = ChatHubApiHelper.postChatHubQuery(url, configurationBody).as(ActivateConfiguration.class);
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
            Validator.validatedErrorResponseforPost(url, configurationBody, dataMap);
        }
    }

    @Given("User is able to get configuration state")
    public void userIsAbleToGetConfigurationState(Map<String,String> dataMap) {
        String url = format(Endpoints.CONFIGURATION_STATE, dataMap.get("i.configurationId"));
        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        if (responseCode == 200) {
            ConfigurationState getConfigurationState = ChatHubApiHelper.getChatHubQuery(url, responseCode).as(ConfigurationState.class);
            Assert.assertNotNull(getConfigurationState.id);
            Assert.assertEquals(dataMap.get("o.accountProviderConfigStatusId"),getConfigurationState.accountProviderConfigStatusId);
        } else {
            Validator.validatedErrorResponseforGet(url, dataMap);
        }
    }

    @Given("User is able to get configuration secrects in astericks")
    public void userIsAbleToGetConfigurationSecrectsInAstericks(Map<String,String> dataMap) {
        String url = format(Endpoints.CONFIGURATION_SECRETS, dataMap.get("i.configurationId"));
        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        if (responseCode == 200) {
            ConfigurationSecrets expectedConfigurationSecret = new ConfigurationSecrets(dataMap);
            ConfigurationSecrets getConfigurationSecret = ChatHubApiHelper.getChatHubQuery(url, responseCode).as(ConfigurationSecrets.class);
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(getConfigurationSecret.getId(),expectedConfigurationSecret.getId());
            softAssert.assertEquals(getConfigurationSecret.getProviderId(),expectedConfigurationSecret.getProviderId());
            softAssert.assertEquals(getConfigurationSecret.getAccountProviderConfigStatusId(),expectedConfigurationSecret.getAccountProviderConfigStatusId());
            softAssert.assertEquals(getConfigurationSecret.getConfigurationEnvironmentTypeId(),expectedConfigurationSecret.getConfigurationEnvironmentTypeId());
            softAssert.assertEquals(getConfigurationSecret.getDisplayName(),expectedConfigurationSecret.getDisplayName());
            softAssert.assertEquals(getConfigurationSecret.getClientId(),expectedConfigurationSecret.getClientId());
            softAssert.assertEquals(getConfigurationSecret.getClientSecret(),expectedConfigurationSecret.getClientSecret());
            softAssert.assertEquals(getConfigurationSecret.getHostUrl(),expectedConfigurationSecret.getHostUrl());
            softAssert.assertNotNull(getConfigurationSecret.getCreatedDate());
            softAssert.assertNotNull(getConfigurationSecret.getModifiedDate());
            softAssert.assertAll();
        } else {
            Validator.validatedErrorResponseforGet(url, dataMap);
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
        String jsonString = expectedProviders.toString();
        jsonString = jsonString.replace(", ", ",");
        ObjectMapper mappergetProviders = new ObjectMapper();
        String getProviders = mappergetProviders.writeValueAsString(ChatHubApiHelper.getChatHubQueryWithoutAuthToken(Endpoints.ADMIN_PROVIDERS, 200).as(ConfiguredProviderDetail[].class));
        Assert.assertEquals(jsonString,getProviders , "Providers response is not as expected");
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

    @Given("User is able to disable configurations")
    public void userIsAbleToDisableConfigurations(Map<String,String> dataMap) {
        String url = format(Endpoints.DISABLE_CONFIGURATION, dataMap.get("i.configurationId"));

        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        if (responseCode == 200) {
            DisableConfiguration expectedDisableConfiguration = new DisableConfiguration(dataMap);
            DisableConfiguration actualDisableConfiguration = ChatHubApiHelper.putChatHubQuerywithAuthNoBody(url,responseCode).as(DisableConfiguration.class);
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(expectedDisableConfiguration.getId(),actualDisableConfiguration.getId());
            softAssert.assertEquals(expectedDisableConfiguration.getProviderId(), actualDisableConfiguration.getProviderId());
            softAssert.assertEquals(expectedDisableConfiguration.getType(), actualDisableConfiguration.getType());
            softAssert.assertEquals(expectedDisableConfiguration.getName(),actualDisableConfiguration.getName());
            softAssert.assertEquals(expectedDisableConfiguration.getStatus(),actualDisableConfiguration.getStatus());
            softAssert.assertEquals(expectedDisableConfiguration.getHost(),actualDisableConfiguration.getHost());
            softAssert.assertNotNull(actualDisableConfiguration.getCreatedDate());
            softAssert.assertNotNull(actualDisableConfiguration.getModifiedDate());
            softAssert.assertAll();
        } else {
            Validator.validatedErrorResponseforPutWithAuthWithoutBody(url, dataMap);
        }
    }

    @Given("Admin is able to update existing provider details")
    public void adminIsAbleToUpdateExistingProviderDetails(Map<String,String> dataMap) {
        String url = format(Endpoints.ADMIN_UPDATE_PROVIDER, dataMap.get("i.id"));
        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        Map<String, String> updateProviderBody = new LinkedHashMap<>();
        updateProviderBody.put("name",dataMap.get("i.name"));
        updateProviderBody.put("logoUrl",dataMap.get("i.logoUrl"));
        updateProviderBody.put("description",dataMap.get("i.description"));
        updateProviderBody.put("moreInfoUrl",dataMap.get("i.moreInfoUrl"));
        if (responseCode == 200) {
            UpdatedProviderDetails expectedPorviderDetails = new UpdatedProviderDetails(dataMap);
            UpdatedProviderDetails getUpdatedProvider = ChatHubApiHelper.putChatHubQueryWithoutAuth(url,updateProviderBody, 200).as(UpdatedProviderDetails.class);
            Assert.assertEquals(expectedPorviderDetails,getUpdatedProvider,"Update provider details does not match");

        } else {
            Validator.validatedErrorResponseforPutWithoutAuth(url, updateProviderBody,dataMap);
        }
    }

    @Given("User is able to delete configurations")
    public void userIsAbleToDeleteConfigurations(Map<String,String> dataMap) {
        String url = format(Endpoints.DELETE_CONFIGURATION, dataMap.get("i.configurationId"));
            Validator.validatedErrorResponseforDeleteWithAuth(url, dataMap);
        }

    @Given("User is able to re-activate configuration for a provider")
    public void userIsAbleToReActivateConfigurationForAProvider(Map<String,String> dataMap) {
        String url = format(Endpoints.RE_ACTIVATE_CONFIGURATION, dataMap.get("i.configurationId"));
        Map<String, String> reActivateConfigurationBody = new LinkedHashMap<>();
        reActivateConfigurationBody.put("id", dataMap.get("i.id"));
        reActivateConfigurationBody.put("providerId", dataMap.get("i.providerId"));

        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        if (responseCode == 200) {
            ReActivateConfiguration expectedReActivatedConfiguration = new ReActivateConfiguration(dataMap);
            ReActivateConfiguration actualReActivatedConfiguration = ChatHubApiHelper.putChatHubQuerywithAuthAndBody(url,reActivateConfigurationBody, responseCode).as(ReActivateConfiguration.class);
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(expectedReActivatedConfiguration.getId(),actualReActivatedConfiguration.getId());
            softAssert.assertEquals(expectedReActivatedConfiguration.getType(),actualReActivatedConfiguration.getType());
            softAssert.assertEquals(expectedReActivatedConfiguration.getSetupName(),actualReActivatedConfiguration.getSetupName());
            softAssert.assertNotNull(actualReActivatedConfiguration.getAuthenticationLink());
            softAssert.assertEquals(expectedReActivatedConfiguration.timeToExpire,expectedReActivatedConfiguration.timeToExpire);
            softAssert.assertNotNull(actualReActivatedConfiguration.getCreatedDate());
            softAssert.assertNotNull(actualReActivatedConfiguration.getModifiedDate());
            softAssert.assertAll();
        } else {
            Validator.validatedErrorResponseforPutWithAuthAndBody(url,reActivateConfigurationBody,dataMap);
        }
    }

    @Given("Admin is able to create provider")
    public void adminIsAbleToCreateProvider(Map<String, String> dataMap)throws JsonProcessingException {
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

