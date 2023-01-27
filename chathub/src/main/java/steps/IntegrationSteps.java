package steps;

import api.MainApi;
import clients.Endpoints;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import datamodelsclasses.Endpoints.*;
import datamodelsclasses.configurations.ActivateConfiguration;
import datamodelsclasses.configurations.Configurations;
import datamodelsclasses.providers.*;
import datamodelsclasses.configurations.ConfigurationSecrets;
import datamodelsclasses.configurations.ConfigurationState;
import datamodelsclasses.providers.UpdatedProviderDetails;
import datamodelsclasses.configurations.*;
import datamodelsclasses.Specifications.AuthDetails;
import datamodelsclasses.Specifications.Specifications;
import datamodelsclasses.providers.ProviderState;
import datamodelsclasses.validator.Validator;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import api.ChatHubApiHelper;
import org.testng.asserts.SoftAssert;
import urlproxymanager.Proxymanager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import java.util.*;

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
        public void GETProviderAPI(List<Map<String, String>> datatable) throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            List<String> expectedProvidersBody = new ArrayList<>();
        for(Map<String, String> data : datatable)
                 {
                    expectedProvidersBody.add(mapper.writeValueAsString(new AllProviders(data.get("o.id"),
                            data.get("o.name"),data.get("o.logoUrl"),data.get("o.description"),
                            data.get("o.moreInfoUrl"),data.get("o.vid"),
                            data.get("o.version"),data.get("o.latest"),
                            data.get("o.isAdded"))));
                }
        String getProviders = mapper.writeValueAsString(ChatHubApiHelper.getChatHubQuery(Endpoints.PROVIDERS, 200).as(AllProviders[].class));
        String expectedProviders = expectedProvidersBody.toString();
        expectedProviders = expectedProviders.replace(", ", ",");
            Assert.assertEquals(expectedProviders,getProviders , "Providers response is not as expected");
        }

    @Given("User is able to GET providers API response - Internal")
    public void userIsAbleToGETProvidersAPIResponseInternal(List<Map<String, String>> datatable) throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            List<String> expectedProvidersBody = new ArrayList<>();
            for(Map<String, String> data : datatable)
                 {
                    expectedProvidersBody.add(mapper.writeValueAsString(new AllProviders(data.get("o.id"),
                            data.get("o.name"),data.get("o.logoUrl"),
                            data.get("o.description"),
                            data.get("o.moreInfoUrl"),data.get("o.vid"),
                            data.get("o.version"),data.get("o.latest"))));
                 }
            String getProviders = mapper.writeValueAsString(ChatHubApiHelper.getChatHubQueryWithInternalAuth(Endpoints.INTERNAL_PROVIDERS, 200).as(AllProviders[].class));
            String expectedProviders = expectedProvidersBody.toString();
        expectedProviders = expectedProviders.replace(", ", ",");
            Assert.assertEquals(expectedProviders,getProviders , "Providers response is not as expected");
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

    @Given("User is able to GET providers state in API response - Internal")
    public void userIsAbleToGETProvidersStateInAPIResponseInternal(Map<String,String> dataMap) {
        String url = format(Endpoints.INTERNAL_PROVIDERS_STATE, dataMap.get("i.providerId"));
        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        if (responseCode == 200) {
            ProviderState expectedProviderState = new ProviderState(dataMap);
            ProviderState getProvider = ChatHubApiHelper.getChatHubQueryWithInternalAuth(url, responseCode).as(ProviderState.class);
            Assert.assertEquals(expectedProviderState, getProvider, "Providers response is not as expected");
        } else {
            Validator.validatedErrorResponseforGetInternalProviders(url, dataMap);
        }
    }

    @Given("User is able to activate configuration for a provider")
    public void userIsAbleToActivateConfigurationForAProvider(Map<String, String> dataMap) {
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
            softAssert.assertNotNull(postActiveConfiguration.getCreatedDate(), "CurrentDate is Empty");
            softAssert.assertNotNull(postActiveConfiguration.getModifiedDate(), "Modfied Date is empty");

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
        List<String> expectedProvidersBody = new ArrayList<>();
        for(Map<String, String> data : datatable)
            {
            expectedProvidersBody.add(mapper.writeValueAsString(new ConfiguredProviderDetail(data.get("o.id"),
                    data.get("o.name"),data.get("o.logoUrl")
                    ,data.get("o.description"),
                    data.get("o.moreInfoUrl"),data.get("o.vid"),
                    data.get("o.version"),data.get("o.latest")
                    )));
            }
        String expectedProviders = expectedProvidersBody.toString();
        expectedProviders = expectedProviders.replace(", ", ",");
        String getProviders = mapper.writeValueAsString(ChatHubApiHelper.getChatHubQueryWithoutAuthToken(Endpoints.ADMIN_PROVIDERS, 200).as(ConfiguredProviderDetail[].class));
        Assert.assertEquals(expectedProviders,getProviders , "Providers response is not as expected");
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
        for(Map<String, String> data : datatable)
            {
                expectedConfiguredProviderDetails.add(mapper.writeValueAsString(new ConfiguredProviderDetail(data.get("o.id"),
                data.get("o.name"),data.get("o.logoUrl"),
                data.get("o.description"),
                data.get("o.moreInfoUrl"),data.get("o.vid"),
                data.get("o.version"),data.get("o.latest"))));
            }
        String getProviders = mapper.writeValueAsString(ChatHubApiHelper.getChatHubQueryWithoutAuthToken(url, 200).as(ConfiguredProviderDetail[].class));
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
        for (String key : dataMap.keySet()) {
            if (key.startsWith("i.")) {
                updateProviderBody.put(key.substring(2), dataMap.get(key));
            }
        }

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
        for (String key : dataMap.keySet()) {
            if (key.startsWith("i.")) {
                reActivateConfigurationBody.put(key.substring(2), dataMap.get(key));
            }
        }

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

    @Given("User is able to get all configurations for a provider - Public")
    public void userIsAbleToGetAllConfigurationsForAProviderCheckNonResponses(List<Map<String, String>> dataMap) throws JsonProcessingException {
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

            String expectedConfigurations = expectedConfigurationsBody.toString();
            expectedConfigurations = expectedConfigurations.replace(", ", ",");
            System.out.println(expectedConfigurations);
            System.out.println(actualConfigurations);
            Assert.assertEquals(actualConfigurations, expectedConfigurations, "Configurations response is not as expected");
        }
    }

    @Given("User is able to get all configurations for a provider - Internal")
    public void userIsAbleToGetAllConfigurationsForAProviderCheckNonResponsesInternal(List<Map<String, String>> dataMap) throws JsonProcessingException {
        String url = format(Endpoints.INTERNAL_CONFIGURATIONS, dataMap.get(0).get("i.providerId"));
        if (dataMap.size() == 1) {
            Validator.validatedErrorResponseforGetInternalProviders(url, dataMap.get(0));
        }
        else {
            ObjectMapper mapper = new ObjectMapper();
            List<String> expectedConfigurationsBody = new ArrayList<>();
            for (Map<String, String> data : dataMap)
                expectedConfigurationsBody.add(mapper.writeValueAsString(new InternalConfigurations(data.get("o.id"),
                        data.get("o.displayName"), data.get("o.configurationEnvironmentTypeId"),
                        data.get("o.accountProviderConfigStatusId"))));
            String actualConfigurations = mapper.writeValueAsString(ChatHubApiHelper.getChatHubQueryWithInternalAuth(url, 200).as(InternalConfigurations[].class));
            String expectedConfigurations = expectedConfigurationsBody.toString();
            expectedConfigurations = expectedConfigurations.replace(", ", ",");
            Assert.assertEquals(actualConfigurations, expectedConfigurations, "Configurations response is not as expected");
        }
    }

    @Given("Admin is able to create provider")
    public void adminIsAbleToCreateProvider(Map<String, String> dataMap)throws JsonProcessingException {
            String url = Endpoints.ADMIN_PROVIDERS;
            int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        Map<String, String> createProviderBody = new LinkedHashMap<>();
        for (String key : dataMap.keySet()) {
            if (key.startsWith("i.")) {
                createProviderBody.put(key.substring(2), dataMap.get(key));
            }
        }
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
                Validator.validatedErrorResponseforPost(url, createProviderBody, dataMap);
            }

    }

    @Given("User is able to get all endpoint detail for Provider")
    public void userIsAbleToGetAllEndpointDetailForProvider(Map<String, String> datatable) throws JsonProcessingException {

        String url = format(Endpoints.ADMIN_ENDPOINTS, datatable.get("i.providerID"), datatable.get("i.versionID"));

        int responseCode = Integer.parseInt(datatable.get("o.responseCode"));
        if (responseCode == 200) {
            ObjectMapper mapActualEndpoints=new ObjectMapper();
            String getActualEndpoints = mapActualEndpoints.writeValueAsString(ChatHubApiHelper.getChatHubQueryWithoutAuth(url, responseCode).as(ProviderEndpoints[].class));
            Assert.assertNotNull( getActualEndpoints,"Endpoint response is not as expected");
        } else {
            Validator.validatedErrorResponseWithoutAuth(url, datatable);
        }
    }

    @Given("User is able to verify all available endpoints for provider")
    public void userIsAbleToVerifyAllAvailableEndpointsForProvider(List<Map<String, String>> datatable)throws JsonProcessingException {

        String url = format(Endpoints.ADMIN_ENDPOINTS_SUCCESS_REQUEST);
        int responseCode = 200;
        ObjectMapper mapper = new ObjectMapper();
        List<String> endpoints = new ArrayList<>();
        for(int i = 0; i < datatable.size(); i++){
            try {
                endpoints.add(mapper.writeValueAsString(new ProviderEndpoints(datatable.get(i).get("o.id"),datatable.get(i).get("o.name"))));
            } catch (JsonProcessingException e){
                throw new RuntimeException(e);
            }
        }

        List<String> expectedEndpoints = Collections.singletonList(String.join(",", endpoints));

            ObjectMapper mapActualEndpoints=new ObjectMapper();
            String getActualEndpoints = mapActualEndpoints.writeValueAsString(ChatHubApiHelper.getChatHubQueryWithoutAuth(url, responseCode).as(ProviderEndpoints[].class));
            Assert.assertEquals(getActualEndpoints, expectedEndpoints.toString(), "Endpoint response is not as expected");
    }

    @Given("User is able to get all specifications for a provider")
    public void userIsAbleToGetAllSpecificationsForAProvider(Map<String, String> dataMap) throws JsonProcessingException {
        String url = format(Endpoints.ADMIN_SPECIFICATIONS, dataMap.get("i.providerID"));

        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        if (responseCode == 200) {
            String[] authDetails_scopes = new String[]{dataMap.get("o.authDetails.scopes")};
            AuthDetails authDetails =  new AuthDetails(
                    dataMap.get("o.authDetails.grantType"), dataMap.get("o.authDetails.authPath"),
                    dataMap.get("o.authDetails.refreshPath"), dataMap.get("o.authDetails.tokenPath"), dataMap.get("o.authDetails.tokenExpirationDurationSeconds"),
                    authDetails_scopes,dataMap.get(" o.authDetails.authorizationHeaderValuePrefix") , dataMap.get("o.authDetails.authType"));

            ObjectMapper mapperExpectedSpecifications = new ObjectMapper();
            List<String> getExpectedSpecifications = new ArrayList<>();
            getExpectedSpecifications.add(mapperExpectedSpecifications.writeValueAsString(new Specifications(
                    dataMap.get("o.id"), authDetails,
                  dataMap.get("o.version"), dataMap.get("o.openApiSpecS3Key"))));

            ObjectMapper mapperGetSpecifications = new ObjectMapper();
            String getActualSpecification = mapperGetSpecifications.writeValueAsString(ChatHubApiHelper.getChatHubQueryWithoutAuth(url, responseCode).as(Specifications[].class));
            Assert.assertEquals(getActualSpecification, getExpectedSpecifications.toString(), "Specifications response is not as expected");
        } else {
            Validator.validatedErrorResponseWithoutAuth(url, dataMap);
        }
    }

    @Given("User is able to get all endpoint detail for Provider via Internal API")
    public void userIsAbleToGetAllEndpointDetailForProviderViaInternalAPI(Map<String, String> datatable) throws JsonProcessingException {
        String url = format(Endpoints.INTERNAL_ENDPOINTS, datatable.get("i.providerID"), datatable.get("i.versionID"));

        int responseCode = Integer.parseInt(datatable.get("o.responseCode"));
        if (responseCode == 200) {
            ObjectMapper mapActualEndpoints=new ObjectMapper();
            String getActualEndpoints = mapActualEndpoints.writeValueAsString(ChatHubApiHelper.getChatHubQueryWithInternalAuth(url, responseCode).as(ProviderEndpoints[].class));
            Assert.assertNotNull( getActualEndpoints,"Endpoint response is not as expected");
        } else {
            Validator.validatedErrorResponseWithInternalAuth(url, datatable);
        }
    }

    @Given("User is able to verify all available endpoints for provider via Internal API")
    public void userIsAbleToVerifyAllAvailableEndpointsForProviderViaInternalAPI(List<Map<String, String>> datatable) throws JsonProcessingException {
        String url = format(Endpoints.INTERNAL_ENDPOINT_SUCCESS_REQUEST);
        int responseCode = 200;
        ObjectMapper mapper = new ObjectMapper();
        List<String> endpoints = new ArrayList<>();

        for (int i = 0; i < datatable.size(); i++) {
            try {
                endpoints.add(mapper.writeValueAsString(new ProviderEndpoints(
                        datatable.get(i).get("o.id"),datatable.get(i).get("o.name"))));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        List<String> expectedEndpoints = Collections.singletonList(String.join(",", endpoints));

        ObjectMapper mapperExpectedEndpoints = new ObjectMapper();
        String getActualEndpoints = mapperExpectedEndpoints.writeValueAsString(ChatHubApiHelper.getChatHubQueryWithInternalAuth(url, responseCode).as(ProviderEndpoints[].class));
        Assert.assertEquals(getActualEndpoints, expectedEndpoints.toString(),"Endpoint response is not as expected");
    }

    @Given("User is able to get specific endpoint detail for Provider via Admin api")
    public void userIsAbleToGetSpecificEndpointDetailForProviderViaAdminApi(Map<String, String> dataMap) throws JsonProcessingException {

        String url = format(Endpoints.ADMIN_ENDPOINTS_ENDPOINT, dataMap.get("i.endpointID"));
        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        if (responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            String[] requestParameters_Constraints = new String[]{};
            List<String> getRequestParameter = new ArrayList<>();
            getRequestParameter.add(mapper.writeValueAsString(new RequestParameters(
                    dataMap.get("o.requestParameters.id"),dataMap.get("o.requestParameters.label"),dataMap.get("o.requestParameters.placeholder"),dataMap.get("o.requestParameters.default"),Boolean.valueOf(dataMap.get("o.requestParameters.required")),
                    requestParameters_Constraints,dataMap.get("o.requestParameters.parameterType"),dataMap.get("o.requestParameters.availableOptions"),Boolean.valueOf(dataMap.get("o.requestParameters.isArray")),dataMap.get("o.requestParameters.presentationType"),
                    dataMap.get("o.requestParameters.repeatableGroupId"),dataMap.get("o.requestParameters.repeatableGroupName"),dataMap.get("o.requestParameters.placementType"),dataMap.get("o.requestParameters.destinationPath"))));

            System.out.println(getRequestParameter);
            ObjectMapper actualEndpointMapper = new ObjectMapper();

            EndpointDetail actualEndpointDetail = ChatHubApiHelper.getChatHubQueryWithoutAuth(url, responseCode).as(EndpointDetail.class);
            String actualRequestParameters = actualEndpointMapper.writeValueAsString(actualEndpointDetail.getRequestParameters());
            System.out.println(actualRequestParameters);
            SoftAssert softAssertions = new SoftAssert();
            softAssertions.assertEquals(actualRequestParameters,getRequestParameter.toString(),"Request parameter is not as expected");
            softAssertions.assertEquals(actualEndpointDetail.getId(),dataMap.get("i.endpointID"));
            softAssertions.assertEquals(actualEndpointDetail.getOperationName(),dataMap.get("o.operationName"));
            softAssertions.assertNotNull(actualEndpointDetail.getResponseSample());
            //Due to more than 40 objects in responseSample, it will be catered in a separate test case.
            softAssertions.assertAll();
        }else {
            Validator.validatedErrorResponseWithoutAuth(url, dataMap);
        }
    }

    @Given("User is able to get specific endpoint detail for Provider via Internal Api")
    public void userIsAbleToGetSpecificEndpointDetailForProviderViaInternalApi(Map<String, String> dataMap) throws JsonProcessingException {
        String url = format(Endpoints.INTERNAL_ENDPOINTS_ENDPOINT, dataMap.get("i.endpointID"));
        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        if (responseCode == 200) {
            ObjectMapper mapper = new ObjectMapper();
            String[] requestParameters_Constraints = new String[]{};
            List<String> getRequestParameter = new ArrayList<>();
            getRequestParameter.add(mapper.writeValueAsString(new RequestParameters(
                    dataMap.get("o.requestParameters.id"),dataMap.get("o.requestParameters.label"),dataMap.get("o.requestParameters.placeholder"),dataMap.get("o.requestParameters.default"),Boolean.valueOf(dataMap.get("o.requestParameters.required")),
                    requestParameters_Constraints,dataMap.get("o.requestParameters.parameterType"),dataMap.get("o.requestParameters.availableOptions"),Boolean.valueOf(dataMap.get("o.requestParameters.isArray")),dataMap.get("o.requestParameters.presentationType"),
                    dataMap.get("o.requestParameters.repeatableGroupId"),dataMap.get("o.requestParameters.repeatableGroupName"),dataMap.get("o.requestParameters.placementType"),dataMap.get("o.requestParameters.destinationPath"))));

            System.out.println(getRequestParameter);
            ObjectMapper actualEndpointMapper = new ObjectMapper();

            EndpointDetail actualEndpointDetail = ChatHubApiHelper.getChatHubQueryWithInternalAuth(url, responseCode).as(EndpointDetail.class);
            String actualRequestParameters = actualEndpointMapper.writeValueAsString(actualEndpointDetail.getRequestParameters());
            System.out.println(actualRequestParameters);
            SoftAssert softAssertions = new SoftAssert();
            softAssertions.assertEquals(actualRequestParameters,getRequestParameter.toString(),"Request parameter is not as expected");
            softAssertions.assertEquals(actualEndpointDetail.getId(),dataMap.get("i.endpointID"));
            softAssertions.assertEquals(actualEndpointDetail.getOperationName(),dataMap.get("o.operationName"));
            softAssertions.assertNotNull(actualEndpointDetail.getResponseSample());
            //Due to more than 40 objects in responseSample, it will be catered in a separate test case.

            softAssertions.assertAll();
        }else {
            Validator.validatedErrorResponseWithoutAuth(url, dataMap);
        }
    }

    @Given("User is able to verify response sample from specific endpoint detail via Internal Api")
    public void userIsAbleToVerifyResponseSampleFromSpecificEndpointDetailViaInternalApi(List<Map<String, String>> dataMap) throws JsonProcessingException {
        String url = format(Endpoints.INTERNAL_ENDPOINTS_ENDPOINT, dataMap.get(0).get("i.endpointID"));
        int responseCode = Integer.parseInt(dataMap.get(0).get("o.statusCode"));
        ObjectMapper mapper = new ObjectMapper();
        List<String> properties = new ArrayList<>();

        for (int i = 0; i < dataMap.size(); i++) {
            try {
                properties.add(mapper.writeValueAsString(new EndpointProperties(
                        dataMap.get(i).get("isArray"),dataMap.get(i).get("label"),dataMap.get(i).get("type"),dataMap.get(i).get("sourceRef"))));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        List<String> getProperties = Collections.singletonList(String.join(",", properties));

        ObjectMapper actualEndpointMapper = new ObjectMapper();

        EndpointDetail actualEndpointDetail = ChatHubApiHelper.getChatHubQueryWithInternalAuth(url, responseCode).as(EndpointDetail.class);

        String actualResponseSampleProperties = actualEndpointMapper.writeValueAsString(actualEndpointDetail.getResponseSample().get(0).getProperties());
        int actualResponseStatusCode = Integer.parseInt(actualEndpointMapper.writeValueAsString(actualEndpointDetail.getResponseSample().get(0).getStatusCode()));
        SoftAssert assertion = new SoftAssert();

        assertion.assertEquals(actualResponseSampleProperties,getProperties.toString());
        assertion.assertEquals(actualResponseStatusCode,responseCode);
        assertion.assertAll();
    }

    @Given("User is able to verify response sample from specific endpoint detail via Admin api")
    public void userIsAbleToVerifyResponseSampleFromSpecificEndpointDetailViaAdminApi(List<Map<String, String>> dataMap) throws JsonProcessingException {
        String url = format(Endpoints.ADMIN_ENDPOINTS_ENDPOINT, dataMap.get(0).get("i.endpointID"));
        int responseCode = Integer.parseInt(dataMap.get(0).get("o.statusCode"));
        ObjectMapper mapper = new ObjectMapper();
        List<String> properties = new ArrayList<>();

        for (int i = 0; i < dataMap.size(); i++) {
            try {
                properties.add(mapper.writeValueAsString(new EndpointProperties(
                        dataMap.get(i).get("isArray"),dataMap.get(i).get("label"),dataMap.get(i).get("type"),dataMap.get(i).get("sourceRef"))));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        List<String> getProperties = Collections.singletonList(String.join(",", properties));

        ObjectMapper actualEndpointMapper = new ObjectMapper();

        EndpointDetail actualEndpointDetail = ChatHubApiHelper.getChatHubQueryWithoutAuth(url, responseCode).as(EndpointDetail.class);

        String actualResponseSampleProperties = actualEndpointMapper.writeValueAsString(actualEndpointDetail.getResponseSample().get(0).getProperties());
        int actualResponseStatusCode = Integer.parseInt(actualEndpointMapper.writeValueAsString(actualEndpointDetail.getResponseSample().get(0).getStatusCode()));

        SoftAssert assertion = new SoftAssert();

        assertion.assertEquals(actualResponseSampleProperties,getProperties.toString());
        assertion.assertEquals(actualResponseStatusCode,responseCode);
        assertion.assertAll();
    }
}
