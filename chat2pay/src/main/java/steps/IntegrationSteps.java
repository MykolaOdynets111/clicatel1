package steps;

import api.models.response.integration.IntegrationResponse;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import java.util.Map;
import java.util.NoSuchElementException;

import static api.clients.ApiHelperIntegration.getApplicationResponse;
import static api.clients.ApiHelperIntegration.getIntegrationResponse;
import static utils.Validator.validateErrorResponse;

public class IntegrationSteps extends GeneralSteps {

    @Then("^User gets integration information for widget$")
    public void getIntegrationConfiguration(Map<String, String> dataMap) {
        Response response = getIntegrationResponse(getWidgetId(dataMap));
        int expectedResponseCode = getResponseCode(dataMap);
        if (expectedResponseCode == 200) {
            IntegrationResponse integrationResponse = response.jsonPath().getList("", IntegrationResponse.class)
                    .stream().filter(ir -> ir.getIntegrator().getName().equals(dataMap.get("o.name")))
                    .findFirst()
                    .orElseThrow(NoSuchElementException::new);
            checkIntegrationResponse(dataMap, integrationResponse);
        } else if (expectedResponseCode == 404) {
            validateErrorResponse(response, dataMap);
        }
    }

    @Then("^User gets details about application$")
    public void getApplicationDetails(Map<String, String> dataMap) {
        String applicationId = dataMap.get("i.applicationId");
        Response response = getApplicationResponse(getWidgetId(dataMap), applicationId);
        int expectedResponseCode = getResponseCode(dataMap);
        if (expectedResponseCode == 200) {
            IntegrationResponse integrationResponse = response.as(IntegrationResponse.class);
            checkIntegrationResponse(dataMap, integrationResponse);
        } else if (expectedResponseCode == 404) {
            validateErrorResponse(response, dataMap);
        }
    }

    private void checkIntegrationResponse(Map<String, String> dataMap, IntegrationResponse integrationResponse) {
        softly.assertThat(integrationResponse.getIntegrator().getType()).isEqualTo(dataMap.get("o.integratorType"));
        softly.assertThat(integrationResponse.getNotificationUrls().getPaymentStatusNotification()).isEqualTo(dataMap.get("o.paymentStatusNotification"));
        softly.assertThat(integrationResponse.getIntegrationStatus()).isEqualTo(dataMap.get("o.integrationStatus"));
        softly.assertAll();
    }
}