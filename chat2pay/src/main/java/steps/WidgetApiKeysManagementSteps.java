package steps;

import api.ApiHelperChat2Pay;
import data.models.response.updatedresponse.UpdatedEntityResponse;
import data.models.response.widgetconfigurations.ApiKeysResponse;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

import static api.ApiHelperApiKeysManagement.getApiKeysManagement;
import static api.ApiHelperApiKeysManagement.removeApiKeysManagement;
import static api.ApiHelperApiKeysManagement.updateApiKeysManagement;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.Validator.verifyBadRequestResponse;

public class WidgetApiKeysManagementSteps extends GeneralSteps {

    private static final String TOKEN = ApiHelperChat2Pay.token.get();
    private Response response;

    @Then("^User gets 'API Keys Management'$")
    public void getApiKeyManagement(Map<String, String> dataMap) {
        response = getApiKeysManagement(getWidgetId(dataMap), TOKEN);
        int expectedResponseCode = getResponseCode(dataMap);

        if (expectedResponseCode == 200) {
            response.jsonPath().getList("", ApiKeysResponse.class).stream()
                    .filter(n -> n.apiKey.equals(dataMap.get("o.apiKey")))
                    .findFirst().ifPresent(twoWayNumbersBody ->
                            assertThat(twoWayNumbersBody.getCreatedTime())
                                    .isBeforeOrEqualTo(LocalDate.now()));
        } else if (expectedResponseCode == 404) {
            verifyBadRequestResponse(dataMap, response);
        }
    }

    @Then("^User posts 'API Keys Management'$")
    public void postApiKeyManagement(Map<String, String> dataMap) {
        String widgetId = getWidgetId(dataMap);
        int originalApiKeyNumber = getApiKeysNumber(widgetId);
        response = updateApiKeysManagement(widgetId, TOKEN);
        int expectedResponseCode = getResponseCode(dataMap);

        if (expectedResponseCode == 200) {
            int updatedApiKeyNumber = getApiKeysNumber(widgetId);
            response.jsonPath().getList("", ApiKeysResponse.class)
                    .forEach(k -> {
                        assertThat(k.getApiKey()).isNotNull();
                        assertThat(k.getCreatedTime()).isBeforeOrEqualTo(LocalDate.now());
                    });
            assertThat(updatedApiKeyNumber).isGreaterThan(originalApiKeyNumber);
        } else if (expectedResponseCode == 404) {
            verifyBadRequestResponse(dataMap, response);
        }
    }

    @Then("^User deletes 'API Keys Management'$")
    public void deleteApiKeyManagement(Map<String, String> dataMap) {
        String widgetId = getWidgetId(dataMap);
        response = getApiKeysManagement(widgetId, TOKEN);
        int originalApiKeysNumber = getApiKeysNumber(widgetId);
        int expectedResponseCode = getResponseCode(dataMap);

        if (expectedResponseCode == 200) {
            response.jsonPath().getList("", ApiKeysResponse.class).stream().findFirst().ifPresent(a -> {
                UpdatedEntityResponse entityResponse = removeApiKeysManagement(widgetId, a.apiKey, TOKEN)
                        .as(UpdatedEntityResponse.class);
                assertThat(entityResponse.getMessage())
                        .isEqualTo(format("Api key %s deleted successfully", a.apiKey));
            });
            int updatedApiKeyNumber = getApiKeysNumber(widgetId);
            assertThat(updatedApiKeyNumber).isLessThan(originalApiKeysNumber);
        } else if (expectedResponseCode == 404) {
            verifyBadRequestResponse(dataMap, response);
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
