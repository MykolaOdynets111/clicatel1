package steps;

import api.ChatHubApiHelper;
import api.MainApi;
import clients.Endpoints;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import datamodelsclasses.configurations.*;
import datamodelsclasses.validator.Validator;
import datetimeutils.DateTimeHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import urlproxymanager.Proxymanager;
import urlproxymanager.UrlFormatValidator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static abstractclasses.IntegrationsAbstractSteps.getIntegrationsPage;
import static java.lang.String.format;

public class ConfigurationsSteps extends MainApi {

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

    @Given("User is able to activate configuration for a provider")
    public void validateActivateConfigurationForAProvider(Map<String, String> dataMap) {
        String url = Endpoints.ACTIVATE_CONFIGURATION;
        Proxymanager proxy = new Proxymanager();
        Map<String, String> configurationBody = new LinkedHashMap<>();
        for (String key : dataMap.keySet()) {
            if (key.startsWith("i.")) {
                configurationBody.put(key.substring(2), dataMap.get(key));
            }
        }
        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        if (responseCode == 200) {
            ActivateConfiguration postActiveConfiguration = ChatHubApiHelper.postChatHubQuery(url, configurationBody).as(ActivateConfiguration.class);
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertNotNull(postActiveConfiguration.getId(), "Configuration Id is empty");
            softAssert.assertEquals(dataMap.get("o.type"), postActiveConfiguration.getType());
            softAssert.assertEquals(dataMap.get("o.setupName"), postActiveConfiguration.getSetupName());
            softAssert.assertTrue(DateTimeHelper.checkDateTimeFormat(postActiveConfiguration.getCreatedDate()));
            softAssert.assertTrue(DateTimeHelper.checkDateTimeFormat(postActiveConfiguration.getModifiedDate()));
            // Checking if the generated URL is valid. It is a temporary fix till we get to add the certificates to validate the URL
            softAssert.assertTrue(UrlFormatValidator.isUrlValid(postActiveConfiguration.getAuthenticationLink()));
            /*
            //Check the authorization link
              * PROBLEM:
                * VPN (NetScope Client) is not allowing IntelliJ to access the link.

              *TRIED SOLUTIONS:
                *1- Added a proxy to bypass the security but it was working only for the expired links not for the valid ones.
                *2- Added the NetScope certificates to IntelliJ.
                *3- Added NetScope certifies to Java.

               OBSERVATIONS:
              * None of the above approaches worked.
              * Other developers added the certificates which worked for them (but for other links).
              * Another approach is to disabled NetScope Client by raising a request to IT. For the test automation it is not a viable solution because this will execute on multiple machines.

              CONCLUSTION:
              * Authentication link will be catered later
            */
            //Check the authorization link code
            /*String printLink = postActiveConfiguration.authenticationLink;
            System.out.println(printLink);
            int code = proxy.addProxy("https://dev-mc2-authentication-front-end-service.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com/authorize/request/a6bad634-8d45-4165-a2a0-13e288ce8564");
            Assert.assertEquals(Integer.parseInt(dataMap.get("o.authenticationLink")), code);*/
            softAssert.assertEquals(dataMap.get("o.timeToExpire"), postActiveConfiguration.getTimeToExpire());
            softAssert.assertAll();
        } else {
            Validator.validatedErrorResponseforPost(url, configurationBody, dataMap);
        }
    }

    @Given("User is able to get configuration state")
    public void validateGetConfigurationState(Map<String, String> dataMap) {
        String url = format(Endpoints.CONFIGURATION_STATE, dataMap.get("i.configurationId"));
        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        if (responseCode == 200) {
            ConfigurationState getConfigurationState = ChatHubApiHelper.getChatHubQuery(url, responseCode).as(ConfigurationState.class);
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertNotNull(getConfigurationState.id);
            softAssert.assertEquals(dataMap.get("o.accountProviderConfigStatusId"), getConfigurationState.accountProviderConfigStatusId);
            softAssert.assertAll();
        } else {
            Validator.validatedErrorResponseforGet(url, dataMap);
        }
    }

    @Given("User is able to get configuration secrects in astericks")
    public void validateGetConfigurationSecrectsInAstericks(Map<String, String> dataMap) {
        String url = format(Endpoints.CONFIGURATION_SECRETS, dataMap.get("i.configurationId"));
        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        if (responseCode == 200) {
            ConfigurationSecrets expectedConfigurationSecret = new ConfigurationSecrets(dataMap);
            ConfigurationSecrets getConfigurationSecret = ChatHubApiHelper.getChatHubQuery(url, responseCode).as(ConfigurationSecrets.class);
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(getConfigurationSecret.getId(), expectedConfigurationSecret.getId());
            softAssert.assertEquals(getConfigurationSecret.getProviderId(), expectedConfigurationSecret.getProviderId());
            softAssert.assertEquals(getConfigurationSecret.getAccountProviderConfigStatusId(), expectedConfigurationSecret.getAccountProviderConfigStatusId());
            softAssert.assertEquals(getConfigurationSecret.getConfigurationEnvironmentTypeId(), expectedConfigurationSecret.getConfigurationEnvironmentTypeId());
            softAssert.assertEquals(getConfigurationSecret.getDisplayName(), expectedConfigurationSecret.getDisplayName());
            softAssert.assertEquals(getConfigurationSecret.getClientId(), expectedConfigurationSecret.getClientId());
            softAssert.assertEquals(getConfigurationSecret.getClientSecret(), expectedConfigurationSecret.getClientSecret());
            softAssert.assertEquals(getConfigurationSecret.getHostUrl(), expectedConfigurationSecret.getHostUrl());
            softAssert.assertTrue(DateTimeHelper.checkDateTimeFormat(getConfigurationSecret.getCreatedDate()));
            softAssert.assertTrue(DateTimeHelper.checkDateTimeFormat(getConfigurationSecret.getModifiedDate()));
            softAssert.assertAll();
        } else {
            Validator.validatedErrorResponseforGet(url, dataMap);
        }
    }

    @Given("User is able to disable configurations")
    public void validateDisableConfigurations(Map<String, String> dataMap) {
        String url = format(Endpoints.DISABLE_CONFIGURATION, dataMap.get("i.configurationId"));

        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        if (responseCode == 200) {
            DisableConfiguration expectedDisableConfiguration = new DisableConfiguration(dataMap);
            DisableConfiguration actualDisableConfiguration = ChatHubApiHelper.putChatHubQuerywithAuthNoBody(url, responseCode).as(DisableConfiguration.class);
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(expectedDisableConfiguration.getId(), actualDisableConfiguration.getId());
            softAssert.assertEquals(expectedDisableConfiguration.getProviderId(), actualDisableConfiguration.getProviderId());
            softAssert.assertEquals(expectedDisableConfiguration.getType(), actualDisableConfiguration.getType());
            softAssert.assertEquals(expectedDisableConfiguration.getName(), actualDisableConfiguration.getName());
            softAssert.assertEquals(expectedDisableConfiguration.getStatus(), actualDisableConfiguration.getStatus());
            softAssert.assertEquals(expectedDisableConfiguration.getHost(), actualDisableConfiguration.getHost());
            softAssert.assertTrue(DateTimeHelper.checkDateTimeFormat(actualDisableConfiguration.getCreatedDate()));
            softAssert.assertTrue(DateTimeHelper.checkDateTimeFormat(actualDisableConfiguration.getModifiedDate()));
            softAssert.assertAll();
        } else {
            Validator.validatedErrorResponseforPutWithAuthWithoutBody(url, dataMap);
        }
    }

    @Given("User is able to delete configurations")
    public void validateDeleteConfigurations(Map<String, String> dataMap) {
        String url = format(Endpoints.DELETE_CONFIGURATION, dataMap.get("i.configurationId"));
        Validator.validatedErrorResponseforDeleteWithAuth(url, dataMap);
    }

    @Given("User is able to re-activate configuration for a provider")
    public void validateReActivateConfigurationForAProvider(Map<String, String> dataMap) {
        String url = format(Endpoints.RE_ACTIVATE_CONFIGURATION, dataMap.get("i.configurationId"));
        Map<String, String> reActivateConfigurationBody = new LinkedHashMap<>();
        for (String key : dataMap.keySet()) {
            if (key.startsWith("i.")) {
                reActivateConfigurationBody.put(key.substring(2), dataMap.get(key));
            }
        }
        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        if (responseCode == 200) {
            ReActivateConfiguration expectedReActivatedConfiguration = new ReActivateConfiguration(dataMap);
            ReActivateConfiguration actualReActivatedConfiguration = ChatHubApiHelper.putChatHubQuerywithAuthAndBody(url, reActivateConfigurationBody, responseCode).as(ReActivateConfiguration.class);
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(expectedReActivatedConfiguration.getId(), actualReActivatedConfiguration.getId());
            softAssert.assertEquals(expectedReActivatedConfiguration.getType(), actualReActivatedConfiguration.getType());
            softAssert.assertEquals(expectedReActivatedConfiguration.getSetupName(), actualReActivatedConfiguration.getSetupName());
            softAssert.assertNotNull(actualReActivatedConfiguration.getAuthenticationLink());
            softAssert.assertEquals(expectedReActivatedConfiguration.timeToExpire, expectedReActivatedConfiguration.timeToExpire);
            softAssert.assertTrue(DateTimeHelper.checkDateTimeFormat(actualReActivatedConfiguration.getCreatedDate()));
            softAssert.assertTrue(DateTimeHelper.checkDateTimeFormat(actualReActivatedConfiguration.getModifiedDate()));
            softAssert.assertAll();
        } else {
            Validator.validatedErrorResponseforPutWithAuthAndBody(url, reActivateConfigurationBody, dataMap);
        }
    }

    @Given("User is able to get all configurations for a provider - Public")
    public void validateGetAllConfigurationsForAProvider(List<Map<String, String>> dataMap) throws JsonProcessingException {
        String url = format(Endpoints.CONFIGURATIONS, dataMap.get(0).get("i.providerId"));
        if (dataMap.size() == 1)
            Validator.validatedErrorResponseforGet(url, dataMap.get(0));
        else {
            ObjectMapper mapper = new ObjectMapper();
            List<String> expectedConfigurationsBody = new ArrayList<>();
            for (Map<String, String> data : dataMap)
                expectedConfigurationsBody.add(mapper.writeValueAsString(new Configurations(data.get("o.id"),
                        data.get("o.providerId"), data.get("o.type"), data.get("o.name"),
                        data.get("o.status"), data.get("o.host"), data.get("o.createdDate"), data.get("o.modifiedDate"))));
            String actualConfigurations = mapper.writeValueAsString(ChatHubApiHelper.getChatHubQuery(url, 200).as(Configurations[].class));
            Assert.assertEquals(actualConfigurations, expectedConfigurationsBody.toString().replace(", ", ","), "Configurations response is not as expected");
        }
    }

    @Given("User is able to get all configurations for a provider - Internal")
    public void validateAllConfigurationsForAProviderInternal(List<Map<String, String>> dataMap) throws JsonProcessingException {
        String url = format(Endpoints.INTERNAL_CONFIGURATIONS, dataMap.get(0).get("i.providerId"));
        if (dataMap.size() == 1) {
            Validator.validatedErrorResponseforGetInternalProviders(url, dataMap.get(0));
        } else {
            ObjectMapper mapper = new ObjectMapper();
            List<String> expectedConfigurationsBody = new ArrayList<>();
            for (Map<String, String> data : dataMap)
                expectedConfigurationsBody.add(mapper.writeValueAsString(new InternalConfigurations(data.get("o.id"),
                        data.get("o.displayName"), data.get("o.configurationEnvironmentTypeId"),
                        data.get("o.accountProviderConfigStatusId"))));
            String actualConfigurations = mapper.writeValueAsString(ChatHubApiHelper.getChatHubQueryWithInternalAuth(url, 200).as(InternalConfigurations[].class));
            Assert.assertEquals(actualConfigurations, expectedConfigurationsBody.toString().replace(", ", ","), "Configurations response is not as expected");
        }
    }

    @Given("User is able to get all configurations for a provider via Admin API")
    public void validateAllConfigurationsForAProviderViaAdminAPI(List<Map<String, String>> datatable) throws JsonProcessingException {
        String url = format(Endpoints.ADMIN_GET_CONFIGURATIONS, datatable.get(0).get("i.providerId"), datatable.get(0).get("i.version"), datatable.get(0).get("i.mc2AccountId"));
        int responseCode = Integer.parseInt(datatable.get(0).get("o.responseCode"));

        if (datatable.size() == 1) {
            Validator.validatedErrorResponseWithoutAuth(url, datatable.get(0));
        } else {
            ObjectMapper mapper = new ObjectMapper();
            List<String> expectedConfigurations = new ArrayList<>();
            for (Map<String, String> data : datatable) {
                try {
                    expectedConfigurations.add(mapper.writeValueAsString(new Configurations(data.get("o.id"),
                            data.get("o.providerId"), data.get("o.type"), data.get("o.name"),
                            data.get("o.status"), data.get("o.host"), data.get("o.createdDate"), data.get("o.modifiedDate"))));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
            String getActualConfigurations = mapper.writeValueAsString(ChatHubApiHelper.getChatHubQueryWithoutAuth(url, responseCode).as(Configurations[].class));
            Assert.assertEquals(getActualConfigurations, expectedConfigurations.toString().replace(", ", ","), "Returned Configurations is not expected");
        }
    }

    @Given("User is able to get specific configuration detail")
    public void validateSpecificConfigurationDetail(Map<String, String> dataMap) {
        String url = format(Endpoints.ADMIN_GET_CONFIGURATIONS_CONFIGURATION_ID, dataMap.get("i.configurationID"));

        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        if (responseCode == 200) {

            Configurations expectedConfigurations = new Configurations(
                    dataMap.get("o.id"), dataMap.get("o.providerId"),
                    dataMap.get("o.type"), dataMap.get("o.name"), dataMap.get("o.status"),
                    dataMap.get("o.host"), dataMap.get("o.createdDate"), dataMap.get("o.modifiedDate"));

            Configurations actualConfigurations = ChatHubApiHelper.getChatHubQueryWithoutAuth(url, responseCode).as(Configurations.class);
            Assert.assertEquals(expectedConfigurations, actualConfigurations);
        } else {
            Validator.validatedErrorResponseWithoutAuth(url, dataMap);
        }
    }

    @Given("User is able to get configuration with client id and client secret")
    public void validateConfigurationWithClientIdAndClientSecret(Map<String, String> dataMap) {
        String url = format(Endpoints.ADMIN_GET_CONFIGURATIONS_SECRET, dataMap.get("i.configurationId"));

        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        if (responseCode == 200) {
            ConfigurationSecrets expectedConfigurationSecret = new ConfigurationSecrets(dataMap);
            ConfigurationSecrets getConfigurationSecret = ChatHubApiHelper.getChatHubQueryAdminSecret(url, responseCode).as(ConfigurationSecrets.class);
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(getConfigurationSecret.getId(), expectedConfigurationSecret.getId());
            softAssert.assertEquals(getConfigurationSecret.getProviderId(), expectedConfigurationSecret.getProviderId());
            softAssert.assertEquals(getConfigurationSecret.getAccountProviderConfigStatusId(), expectedConfigurationSecret.getAccountProviderConfigStatusId());
            softAssert.assertEquals(getConfigurationSecret.getConfigurationEnvironmentTypeId(), expectedConfigurationSecret.getConfigurationEnvironmentTypeId());
            softAssert.assertEquals(getConfigurationSecret.getDisplayName(), expectedConfigurationSecret.getDisplayName());
            softAssert.assertEquals(getConfigurationSecret.getClientId(), expectedConfigurationSecret.getClientId());
            softAssert.assertEquals(getConfigurationSecret.getClientSecret(), expectedConfigurationSecret.getClientSecret());
            softAssert.assertEquals(getConfigurationSecret.getHostUrl(), expectedConfigurationSecret.getHostUrl());
            softAssert.assertTrue(DateTimeHelper.checkDateTimeFormat(getConfigurationSecret.getCreatedDate()));
            softAssert.assertTrue(DateTimeHelper.checkDateTimeFormat(getConfigurationSecret.getModifiedDate()));
            softAssert.assertAll();
        } else {
            Validator.validatedErrorResponseAdminConfigurationsSecrets(url, dataMap);
        }
    }

    @Given("User should be able to create and activate configuration")
    public void validateCreateAndActivateConfiguration(Map<String, String> dataMap) {
        String url = format(Endpoints.ADMIN_CONFIGURATION_ACTIVATE, dataMap.get("i.mc2AccountId"));
        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));

        Map<String, String> expectedConfigurationBody = new LinkedHashMap<>();
        expectedConfigurationBody.put("name", dataMap.get("i.name"));
        expectedConfigurationBody.put("clientSecret", dataMap.get("i.clientSecret"));
        expectedConfigurationBody.put("clientId", dataMap.get("i.clientId"));
        expectedConfigurationBody.put("host", dataMap.get("i.host"));
        expectedConfigurationBody.put("providerId", dataMap.get("i.providerId"));
        expectedConfigurationBody.put("type", dataMap.get("i.type"));

        if (responseCode == 200) {
            ActivateConfiguration postActiveConfiguration = ChatHubApiHelper.postChatHubQueryWithMC2Token(url, expectedConfigurationBody, responseCode).as(ActivateConfiguration.class);
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertNotNull(postActiveConfiguration.getId(), "Configuration Id is empty");
            softAssert.assertEquals(dataMap.get("o.type"), postActiveConfiguration.getType());
            softAssert.assertEquals(dataMap.get("o.setupName"), postActiveConfiguration.getSetupName());
            softAssert.assertTrue(DateTimeHelper.checkDateTimeFormat(postActiveConfiguration.getCreatedDate()));
            softAssert.assertTrue(DateTimeHelper.checkDateTimeFormat(postActiveConfiguration.getModifiedDate()));
            softAssert.assertEquals(dataMap.get("o.timeToExpire"), postActiveConfiguration.getTimeToExpire());
            softAssert.assertNotNull(postActiveConfiguration.getAuthenticationLink(), "Authentication link is empty");

            softAssert.assertAll();
        } else {
            Validator.validatedErrorResponseAdminActiveConfiguration(url, expectedConfigurationBody, dataMap);
        }
    }
}
