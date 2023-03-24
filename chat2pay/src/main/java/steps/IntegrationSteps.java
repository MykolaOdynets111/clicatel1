package steps;

import data.models.response.integration.IntegrationResponse;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.util.Map;
import java.util.NoSuchElementException;

import static api.ApiHelperIntegration.getApplicationResponse;
import static api.ApiHelperIntegration.getIntegrationResponse;
import static java.lang.String.format;
import static utils.Validator.validateErrorResponse;

public class IntegrationSteps extends GeneralSteps {

    @Then("^User gets integration information for widget$")
    public void getIntegrationConfiguration(Map<String, String> dataMap) {
        Response response = getIntegrationResponse(getWidgetId(dataMap));
        if (response.statusCode() == 200) {
            IntegrationResponse integrationResponse = response.jsonPath().getList("", IntegrationResponse.class)
                    .stream().filter(ir -> ir.getIntegrator().getName().equals(dataMap.get("o.name")))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("There is no integration with name: " + dataMap.get("o.name")));
            checkIntegrationResponse(dataMap, integrationResponse);
        } else if (response.statusCode() == 404) {
            validateErrorResponse(response, dataMap);
        } else {
            Assertions.fail(format("The response code is not as expected %n expected: %s ,%n actual %s", getResponseCode(dataMap), response.statusCode()));
        }
    }

    @Then("^User gets details about application$")
    public void getApplicationDetails(Map<String, String> dataMap) {
        String applicationId = dataMap.get("i.applicationId");
        Response response = getApplicationResponse(getWidgetId(dataMap), applicationId);
        if (response.statusCode() == 200) {
            IntegrationResponse integrationResponse = response.as(IntegrationResponse.class);
            checkIntegrationResponse(dataMap, integrationResponse);
        } else if (response.statusCode() == 404) {
            validateErrorResponse(response, dataMap);
        } else {
            Assertions.fail(format("The response code is not as expected %n expected: %s ,%n actual %s", getResponseCode(dataMap), response.statusCode()));
        }
    }

    private void checkIntegrationResponse(Map<String, String> dataMap, IntegrationResponse integrationResponse) {
        softly.assertThat(integrationResponse.getIntegrator().getType()).isEqualTo(dataMap.get("o.integratorType"));
        softly.assertThat(integrationResponse.getNotificationUrls().getPaymentStatusNotification()).isEqualTo(dataMap.get("o.paymentStatusNotification"));
        softly.assertThat(integrationResponse.getIntegrationStatus()).isEqualTo(dataMap.get("o.integrationStatus"));
        softly.assertAll();
    }
}