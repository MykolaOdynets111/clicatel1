package steps;

import api.clients.ApiHelperChat2Pay;
import api.models.response.ApiKeysResponse;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static api.clients.ApiHelperApiKeysManagement.getApiKeysManagement;
import static api.clients.ApiHelperApiKeysManagement.updateApiKeysManagement;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.Validator.verifyBadRequestResponse;

public class WidgetApiKeysManagementSteps extends GeneralSteps {

    public static final String TOKEN = ApiHelperChat2Pay.token.get();
    private Response response;

    @Then("^User gets 'API Keys Management'$")
    public void getApiKeyManagement(Map<String, String> valuesMap) {
        String widgetId = valuesMap.get("i.widgetId");

        response = getApiKeysManagement(widgetId, TOKEN);
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

    @Then("^User posts 'API Keys Management'$")
    public void postApiKeyManagement(Map<String, String> valuesMap) {
        String widgetId = valuesMap.get("i.widgetId");
        int sizeBefore = getApiKeysNumber(widgetId);

        response = updateApiKeysManagement(widgetId, TOKEN);
        int statusCode = response.getStatusCode();
        int expectedResponseCode = parseInt(valuesMap.get("o.responseCode"));

        if (expectedResponseCode == statusCode) {
            if (statusCode == 200) {
                int sizeAfter = getApiKeysNumber(widgetId);
                response.jsonPath().getList("", ApiKeysResponse.class)
                        .forEach(k -> {
                            assertThat(k.getApiKey()).isNotNull();
                            assertThat(k.getCreatedTime()).isBeforeOrEqualTo(LocalDate.now());
                        });
                assertThat(sizeAfter).isGreaterThan(sizeBefore);
            } else if (expectedResponseCode == 404) {
                verifyBadRequestResponse(valuesMap, response);
            }
        } else {
            Assertions.fail(format("Expected response code %s but was %s", expectedResponseCode, statusCode));
        }
    }

    private static int getApiKeysNumber(String widgetId) {
        if (Objects.nonNull(widgetId)) {
            return getApiKeysManagement(widgetId, TOKEN).jsonPath().getList("", ApiKeysResponse.class).size();
        } else {
            return 0;
        }
    }
}
