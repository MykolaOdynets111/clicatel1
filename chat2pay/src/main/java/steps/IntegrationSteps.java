package steps;

import api.models.response.integration.IntegrationResponse;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import java.util.Map;

import static api.clients.ApiHelperIntegration.getIntegrationResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.Validator.validateErrorResponse;

public class IntegrationSteps extends GeneralSteps {

    private Response response;

    @Then("^User gets integration information for widget$")
    public void getIntegrationConfiguration(Map<String, String> dataMap) {
        String widgetId = getWidgetId(dataMap);
        response = getIntegrationResponse(widgetId);
        int expectedResponseCode = getResponseCode(dataMap);

        if (expectedResponseCode == 200) {
            response.jsonPath().getList("", IntegrationResponse.class)
                    .forEach(k -> {
                        assertThat(k.getIntegrator()).isNotNull();
                        assertThat(k.getNotificationUrls()).isNotNull();
                        assertThat(k.getIntegrationStatus()).isEqualTo(dataMap.get("o.integrationStatus"));
                    });
        } else if (expectedResponseCode == 404) {
            validateErrorResponse(response, dataMap);
        }
    }

    @Then("^User gets details about application$")
    public void getApplicationDetails(Map<String, String> dataMap) {
//        String widgetId = getWidgetId(dataMap);
//        response = getIntegrationResponse(widgetId);
//        int expectedResponseCode = getResponseCode(dataMap);
//
//        if (expectedResponseCode == 200) {
//            response.jsonPath().getList("", IntegrationResponse.class)
//                    .forEach(k -> {
//                        assertThat(k.getIntegrator()).isNotNull();
//                        assertThat(k.getNotificationUrls()).isNotNull();
//                        assertThat(k.getIntegrationStatus()).isEqualTo(dataMap.get("o.integrationStatus"));
//                    });
//        } else if (expectedResponseCode == 404) {
//            validateErrorResponse(response, dataMap);
//        }
    }

}