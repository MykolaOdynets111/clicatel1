package steps;

import api.clients.ApiHelperChat2Pay;
import api.models.response.ApiKeysResponse;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static api.clients.ApiHelperApiKeysManagement.getApiKeysManagement;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.Validator.verifyBadRequestResponse;

public class WidgetApiKeysManagementSteps extends GeneralSteps {

    private Response response;

    @Then("^User gets 'API Keys Management'$")
    public void getApiKeyManagement(Map<String, String> valuesMap) {
        String widgetId = valuesMap.get("i.widgetId");

        response = getApiKeysManagement(widgetId, ApiHelperChat2Pay.token.get());
        int statusCode = response.getStatusCode();
        int expectedResponseCode = parseInt(valuesMap.get("o.responseCode"));

        if (expectedResponseCode == statusCode) {
            if (statusCode == 200) {
                List<ApiKeysResponse> apiKeysResponse = response.jsonPath().getList("", ApiKeysResponse.class);

                apiKeysResponse.stream().filter(n -> n.apiKey.equals(valuesMap.get("o.apiKey")))
                        .findFirst().ifPresent(twoWayNumbersBody ->
                                assertThat(twoWayNumbersBody.getCreatedTime()).isBeforeOrEqualTo(LocalDate.now()));
            } else if (expectedResponseCode == 404) {
                verifyBadRequestResponse(valuesMap, response);
            }
        } else {
            Assertions.fail(format("Expected response code %s but was %s", expectedResponseCode, statusCode));
        }
    }
}
