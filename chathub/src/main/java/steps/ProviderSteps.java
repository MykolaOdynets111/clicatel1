package steps;

import api.ChatHubApiHelper;
import clients.Endpoints;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import datamodelsclasses.providers.*;
import datamodelsclasses.validator.Validator;
import io.cucumber.java.en.Given;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class ProviderSteps {
    @Given("User is able to GET providers API response")
    public void GETProviderAPI(List<Map<String, String>> datatable) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> expectedProvidersBody = new ArrayList<>();
        for (Map<String, String> data : datatable) {
            expectedProvidersBody.add(mapper.writeValueAsString(new AllProviders(data.get("o.id"),
                    data.get("o.name"), data.get("o.logoUrl"), data.get("o.description"),
                    data.get("o.moreInfoUrl"), data.get("o.vid"),
                    data.get("o.version"), data.get("o.latest"),
                    data.get("o.isAdded"))));
        }
        String getProviders = mapper.writeValueAsString(ChatHubApiHelper.getChatHubQuery(Endpoints.PROVIDERS, 200).as(AllProviders[].class));
        Assert.assertEquals(expectedProvidersBody.toString().replace(", ", ","), getProviders, "Providers response is not as expected");
    }

    @Given("User is able to GET providers API response - Internal")
    public void validateGETProvidersAPIResponseInternal(List<Map<String, String>> datatable) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> expectedProvidersBody = new ArrayList<>();
        for (Map<String, String> data : datatable) {
            expectedProvidersBody.add(mapper.writeValueAsString(new AllProviders(data.get("o.id"),
                    data.get("o.name"), data.get("o.logoUrl"),
                    data.get("o.description"),
                    data.get("o.moreInfoUrl"), data.get("o.vid"),
                    data.get("o.version"), data.get("o.latest"))));
        }
        String getProviders = mapper.writeValueAsString(ChatHubApiHelper.getChatHubQueryWithInternalAuth(Endpoints.INTERNAL_PROVIDERS, 200).as(AllProviders[].class));
        Assert.assertEquals(expectedProvidersBody.toString().replace(", ", ","), getProviders, "Providers response is not as expected");
    }

    @Given("User is able to GET providers state in API response")
    public void GETProviderStateAPI(Map<String, String> dataMap) {
        String url = format(Endpoints.PROVIDERS_STATE, dataMap.get("i.providerId"));
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
    public void validateGETProvidersStateInAPIResponseInternal(Map<String, String> dataMap) {
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

    @Given("Admin is able to GET providers API response")
    public void validateGETProvidersAPIResponse(List<Map<String, String>> datatable) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> expectedProvidersBody = new ArrayList<>();
        for (Map<String, String> data : datatable) {
            expectedProvidersBody.add(mapper.writeValueAsString(new ConfiguredProviderDetail(data.get("o.id"),
                    data.get("o.name"), data.get("o.logoUrl")
                    , data.get("o.description"),
                    data.get("o.moreInfoUrl"), data.get("o.vid"),
                    data.get("o.version"), data.get("o.latest")
            )));
        }
        String getProviders = mapper.writeValueAsString(ChatHubApiHelper.getChatHubQueryWithoutAuthToken(Endpoints.ADMIN_PROVIDERS, 200).as(ConfiguredProviderDetail[].class));
        Assert.assertEquals(expectedProvidersBody.toString().replace(", ", ","), getProviders, "Providers response is not as expected");
    }

    @Given("Admin is able to GET existing provider details")
    public void validateGETExistingProviderDetails(Map<String, String> dataMap) {
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
    public void validateGETConfiguredProviderForCustomer(List<Map<String, String>> datatable) throws JsonProcessingException {
        String url = format(Endpoints.ADMIN_CONFIGURED_PROVIDER_DETAILS, datatable.get(0).get("i.mc2AccountId"));
        ObjectMapper mapper = new ObjectMapper();
        List<String> expectedConfiguredProviderDetails = new ArrayList<>();
        for (Map<String, String> data : datatable) {
            expectedConfiguredProviderDetails.add(mapper.writeValueAsString(new ConfiguredProviderDetail(data.get("o.id"),
                    data.get("o.name"), data.get("o.logoUrl"),
                    data.get("o.description"),
                    data.get("o.moreInfoUrl"), data.get("o.vid"),
                    data.get("o.version"), data.get("o.latest"))));
        }
        String getProviders = mapper.writeValueAsString(ChatHubApiHelper.getChatHubQueryWithoutAuthToken(url, 200).as(ConfiguredProviderDetail[].class));
        Assert.assertEquals(expectedConfiguredProviderDetails.toString(), getProviders, "Providers response is not as expected");
    }

    @Given("Admin is able to create provider")
    public void validateToCreateProvider(Map<String, String> dataMap) {
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

    @Given("Admin is able to update existing provider details")
    public void validateUpdateExistingProviderDetails(Map<String, String> dataMap) {
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
            UpdatedProviderDetails getUpdatedProvider = ChatHubApiHelper.putChatHubQueryWithoutAuth(url, updateProviderBody, 200).as(UpdatedProviderDetails.class);
            Assert.assertEquals(expectedPorviderDetails, getUpdatedProvider, "Update provider details does not match");

        } else {
            Validator.validatedErrorResponseforPutWithoutAuth(url, updateProviderBody, dataMap);
        }
    }
}
