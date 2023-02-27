package steps;

import api.models.response.integration.IntegrationResponse;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import java.util.Map;

import static api.clients.ApiHelperIntegration.getApplicationResponse;
import static api.clients.ApiHelperIntegration.getIntegrationResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.Validator.validateErrorResponse;

public class IntegrationSteps extends GeneralSteps {

    @Then("^User gets integration information for widget$")
    public void getIntegrationConfiguration(Map<String, String> dataMap) {
        String widgetId = getWidgetId(dataMap);
        Response response = getIntegrationResponse(widgetId);
        int expectedResponseCode = getResponseCode(dataMap);
        if (expectedResponseCode == 200) {
            response.jsonPath().getList("", IntegrationResponse.class)
                    .forEach(k -> checkIntegrationRsponse(dataMap, k));
        } else if (expectedResponseCode == 404) {
            validateErrorResponse(response, dataMap);
        }
    }

    @Then("^User gets details about application$")
    public void getApplicationDetails(Map<String, String> dataMap) {
        String widgetId = getWidgetId(dataMap);
        String applicationId = dataMap.get("i.applicationId");
        Response response = getApplicationResponse(widgetId, applicationId);
        int expectedResponseCode = getResponseCode(dataMap);
        if (expectedResponseCode == 200) {
            IntegrationResponse integrationResponse = response.as(IntegrationResponse.class);
            checkIntegrationRsponse(dataMap, integrationResponse);
        } else if (expectedResponseCode == 404) {
            validateErrorResponse(response, dataMap);
        }
    }

    private void checkIntegrationRsponse(Map<String, String> dataMap, IntegrationResponse k) {
        softly.assertThat(k.getIntegrator()).isNotNull();
        softly.assertThat(k.getNotificationUrls()).isNotNull();
        softly.assertThat(k.getIntegrationStatus()).isEqualTo(dataMap.get("o.integrationStatus"));
        softly.assertAll();
    }
}