package steps;

import api.MainApi;
import clients.Endpoints;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import datamodelsclasses.Endpoints.EndpointDetail;
import datamodelsclasses.Endpoints.ProviderEndpoints;
import datamodelsclasses.Endpoints.RequestParameters;
import datamodelsclasses.Specifications.AuthDetails;
import datamodelsclasses.Specifications.Specifications;
import datamodelsclasses.providers.ProviderState;
import datamodelsclasses.validator.Validator;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import datamodelsclasses.providers.AllProviders;
import api.ChatHubApiHelper;
import org.testng.asserts.SoftAssert;

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
                endpoints.add(mapper.writeValueAsString(new ProviderEndpoints(datatable.get(i).get("id"),datatable.get(i).get("name"))));
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
                        datatable.get(i).get("id"),datatable.get(i).get("name"))));
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

            softAssertions.assertAll();
        }else {
            Validator.validatedErrorResponseWithoutAuth(url, dataMap);
        }
    }
}





