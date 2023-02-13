package steps;

import api.ChatHubApiHelper;
import clients.Endpoints;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import datamodelsclasses.Endpoints.EndpointDetail;
import datamodelsclasses.Endpoints.EndpointProperties;
import datamodelsclasses.Endpoints.ProviderEndpoints;
import datamodelsclasses.Endpoints.RequestParameters;
import datamodelsclasses.Specifications.AuthDetails;
import datamodelsclasses.Specifications.Specifications;
import datamodelsclasses.validator.Validator;
import io.cucumber.java.en.Given;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class EndpointSteps {
    @Given("User is able to get all endpoint detail for Provider")
    public void validateAllEndpointDetailForProvider(List<Map<String, String>> datatable) throws JsonProcessingException {

        String url = format(Endpoints.ADMIN_ENDPOINTS, datatable.get(0).get("i.providerID"), datatable.get(0).get("i.versionID"));
        int responseCode = Integer.parseInt(datatable.get(0).get("o.responseCode"));

        if (datatable.size() == 1) {
            Validator.validatedErrorResponseWithoutAuth(url, datatable.get(0));
        } else {
            ObjectMapper mapper = new ObjectMapper();
            List<String> expectedEndpoints = new ArrayList<>();
            for (Map<String, String> element : datatable) {
                try {
                    expectedEndpoints.add(mapper.writeValueAsString(new ProviderEndpoints(element.get("o.id"), element.get("o.name"))));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
            String ActualEndpoints = mapper.writeValueAsString(ChatHubApiHelper.getChatHubQueryWithoutAuth(url, responseCode).as(ProviderEndpoints[].class));
            Assert.assertEquals(ActualEndpoints, expectedEndpoints.toString().replace(", ", ","), "Expected endpoints does not match actual endpoints response");
        }
    }

    @Given("User is able to get all endpoint detail for Provider via Internal API")
    public void validateAllEndpointDetailForProviderViaInternalAPI(List<Map<String, String>> datatable) throws JsonProcessingException {
        String url = format(Endpoints.INTERNAL_ENDPOINTS, datatable.get(0).get("i.providerID"), datatable.get(0).get("i.versionID"));
        int responseCode = Integer.parseInt(datatable.get(0).get("o.responseCode"));

        if (datatable.size() == 1) {
            Validator.validatedErrorResponseWithInternalAuth(url, datatable.get(0));
        } else {
            ObjectMapper mapper = new ObjectMapper();
            List<String> expectedEndpoints = new ArrayList<>();
            for (Map<String, String> element : datatable) {
                try {
                    expectedEndpoints.add(mapper.writeValueAsString(new ProviderEndpoints(element.get("o.id"), element.get("o.name"))));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
            String ActualEndpoints = mapper.writeValueAsString(ChatHubApiHelper.getChatHubQueryWithInternalAuth(url, responseCode).as(ProviderEndpoints[].class));
            Assert.assertEquals(ActualEndpoints, expectedEndpoints.toString().replace(", ", ","), "Expected endpoints does not match actual endpoints response");
        }
    }

    @Given("User is able to get all specifications for a provider")
    public void validateAllSpecificationsForAProvider(Map<String, String> dataMap) throws JsonProcessingException {
        String url = format(Endpoints.ADMIN_SPECIFICATIONS, dataMap.get("i.providerID"));

        int responseCode = Integer.parseInt(dataMap.get("o.responseCode"));
        if (responseCode == 200) {
            String[] authDetails_scopes = new String[]{dataMap.get("o.authDetails.scopes")};
            AuthDetails authDetails = new AuthDetails(
                    dataMap.get("o.authDetails.grantType"), dataMap.get("o.authDetails.authPath"),
                    dataMap.get("o.authDetails.refreshPath"), dataMap.get("o.authDetails.tokenPath"), dataMap.get("o.authDetails.tokenExpirationDurationSeconds"),
                    authDetails_scopes, dataMap.get("o.authDetails.authorizationHeaderValuePrefix") + " ", dataMap.get("o.authDetails.authType"));

            ObjectMapper mapper = new ObjectMapper();
            List<String> expectedSpecifications = new ArrayList<>();
            expectedSpecifications.add(mapper.writeValueAsString(new Specifications(
                    dataMap.get("o.id"), authDetails,
                    dataMap.get("o.version"), dataMap.get("o.openApiSpecS3Key"))));

            String actualSpecification = mapper.writeValueAsString(ChatHubApiHelper.getChatHubQueryWithoutAuth(url, responseCode).as(Specifications[].class));
            Assert.assertEquals(actualSpecification, expectedSpecifications.toString(), "Specifications response is not as expected");
        } else {
            Validator.validatedErrorResponseWithoutAuth(url, dataMap);
        }
    }

    @Given("User is able to get specific endpoint detail for Provider via Admin api")
    public void validateSpecificEndpointDetailForProviderViaAdminApi(List<Map<String, String>> dataMap) throws JsonProcessingException {

        String url = format(Endpoints.ADMIN_ENDPOINTS_ENDPOINT, dataMap.get(0).get("i.endpointID"));
        ObjectMapper mapper = new ObjectMapper();
        int responseCode = Integer.parseInt(dataMap.get(0).get("o.responseCode"));
        SoftAssert assertion = new SoftAssert();
        if (dataMap.size() == 1) {
            if (responseCode == 200) {
                String[] requestParameters_Constraints = new String[]{};
                List<String> expectedRequestParameter = new ArrayList<>();
                expectedRequestParameter.add(mapper.writeValueAsString(new RequestParameters(
                        dataMap.get(0).get("o.requestParameters.id"), dataMap.get(0).get("o.requestParameters.label"), dataMap.get(0).get("o.requestParameters.placeholder"), dataMap.get(0).get("o.requestParameters.default"), Boolean.valueOf(dataMap.get(0).get("o.requestParameters.required")),
                        requestParameters_Constraints, dataMap.get(0).get("o.requestParameters.parameterType"), dataMap.get(0).get("o.requestParameters.availableOptions"), Boolean.valueOf(dataMap.get(0).get("o.requestParameters.isArray")), dataMap.get(0).get("o.requestParameters.presentationType"),
                        dataMap.get(0).get("o.requestParameters.repeatableGroupId"), dataMap.get(0).get("o.requestParameters.repeatableGroupName"), dataMap.get(0).get("o.requestParameters.placementType"), dataMap.get(0).get("o.requestParameters.destinationPath"))));
                EndpointDetail actualEndpointDetail = ChatHubApiHelper.getChatHubQueryWithoutAuth(url, responseCode).as(EndpointDetail.class);
                String actualRequestParameter = mapper.writeValueAsString(actualEndpointDetail.getRequestParameters());

                assertion.assertEquals(actualRequestParameter, expectedRequestParameter.toString(), "Request parameter is not as expected");
                assertion.assertEquals(actualEndpointDetail.getId(), dataMap.get(0).get("i.endpointID"));
                assertion.assertEquals(actualEndpointDetail.getOperationName(), dataMap.get(0).get("o.operationName"));
            } else {
                Validator.validatedErrorResponseWithoutAuth(url, dataMap.get(0));
            }
        } else {
            List<String> expectedProperties = new ArrayList<>();
            for (Map<String, String> element : dataMap) {
                try {
                    expectedProperties.add(mapper.writeValueAsString(new EndpointProperties(
                            element.get("o.isArray"), element.get("o.label"), element.get("o.type"), element.get("o.sourceRef"))));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
            List<String> expectedPropertiesFormatted = Collections.singletonList(String.join(",", expectedProperties));
            EndpointDetail actualEndpointDetail = ChatHubApiHelper.getChatHubQueryWithoutAuth(url, responseCode).as(EndpointDetail.class);
            String actualProperties = mapper.writeValueAsString(actualEndpointDetail.getResponseSample().get(0).getProperties());
            int actualStatusCode = Integer.parseInt(mapper.writeValueAsString(actualEndpointDetail.getResponseSample().get(0).getStatusCode()));
            assertion.assertEquals(actualProperties, expectedPropertiesFormatted.toString());
            assertion.assertEquals(actualStatusCode, Integer.parseInt(dataMap.get(0).get("o.statusCode")));
        }
        assertion.assertAll();
    }

    @Given("User is able to get specific endpoint detail for Provider via Internal Api")
    public void validateSpecificEndpointDetailForProviderViaInternalApi(List<Map<String, String>> dataMap) throws JsonProcessingException {
        String url = format(Endpoints.INTERNAL_ENDPOINTS_ENDPOINT, dataMap.get(0).get("i.endpointID"));
        ObjectMapper mapper = new ObjectMapper();
        int responseCode = Integer.parseInt(dataMap.get(0).get("o.responseCode"));
        SoftAssert assertion = new SoftAssert();
        if (dataMap.size() == 1) {
            if (responseCode == 200) {
                String[] requestParameters_Constraints = new String[]{};
                List<String> expectedRequestParameter = new ArrayList<>();
                expectedRequestParameter.add(mapper.writeValueAsString(new RequestParameters(
                        dataMap.get(0).get("o.requestParameters.id"), dataMap.get(0).get("o.requestParameters.label"), dataMap.get(0).get("o.requestParameters.placeholder"), dataMap.get(0).get("o.requestParameters.default"), Boolean.valueOf(dataMap.get(0).get("o.requestParameters.required")),
                        requestParameters_Constraints, dataMap.get(0).get("o.requestParameters.parameterType"), dataMap.get(0).get("o.requestParameters.availableOptions"), Boolean.valueOf(dataMap.get(0).get("o.requestParameters.isArray")), dataMap.get(0).get("o.requestParameters.presentationType"),
                        dataMap.get(0).get("o.requestParameters.repeatableGroupId"), dataMap.get(0).get("o.requestParameters.repeatableGroupName"), dataMap.get(0).get("o.requestParameters.placementType"), dataMap.get(0).get("o.requestParameters.destinationPath"))));
                EndpointDetail actualEndpointDetail = ChatHubApiHelper.getChatHubQueryWithInternalAuth(url, responseCode).as(EndpointDetail.class);
                String actualRequestParameter = mapper.writeValueAsString(actualEndpointDetail.getRequestParameters());

                assertion.assertEquals(actualRequestParameter, expectedRequestParameter.toString(), "Request parameter is not as expected");
                assertion.assertEquals(actualEndpointDetail.getId(), dataMap.get(0).get("i.endpointID"));
                assertion.assertEquals(actualEndpointDetail.getOperationName(), dataMap.get(0).get("o.operationName"));
            } else {
                Validator.validatedErrorResponseWithInternalAuth(url, dataMap.get(0));
            }
        } else {
            List<String> expectedProperties = new ArrayList<>();
            for (Map<String, String> element : dataMap) {
                try {
                    expectedProperties.add(mapper.writeValueAsString(new EndpointProperties(
                            element.get("o.isArray"), element.get("o.label"), element.get("o.type"), element.get("o.sourceRef"))));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
            EndpointDetail actualEndpointDetail = ChatHubApiHelper.getChatHubQueryWithInternalAuth(url, responseCode).as(EndpointDetail.class);

            String actualResponseSampleProperties = mapper.writeValueAsString(actualEndpointDetail.getResponseSample().get(0).getProperties());
            int actualStatusCode = Integer.parseInt(mapper.writeValueAsString(actualEndpointDetail.getResponseSample().get(0).getStatusCode()));
            assertion.assertEquals(actualResponseSampleProperties, expectedProperties.toString().replace(", ", ","));
            assertion.assertEquals(actualStatusCode, Integer.parseInt(dataMap.get(0).get("o.statusCode")));
        }
        assertion.assertAll();
    }
}
