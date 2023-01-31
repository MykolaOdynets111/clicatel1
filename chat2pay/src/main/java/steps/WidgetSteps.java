package steps;

import api.models.response.widgetresponse.WidgetsContent;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;

import java.util.Map;

import static api.clients.ApiHelperChat2Pay.token;
import static api.clients.ApiHelperWidgets.getWidgetConfigurationResponse;
import static api.models.response.failedresponse.UnauthorisedResponse.verifyUnauthorisedResponse;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;

public class WidgetSteps {

    @Then("^User gets the Widget configuration")
    public void getWidgetConfiguration(Map<String, String> valuesMap) {
        SoftAssertions softly = new SoftAssertions();
        Response response = getWidgetConfigurationResponse(token.get());
        int statusCode = response.getStatusCode();
        int expectedResponseCode = parseInt(valuesMap.get("responseCode"));

        if (expectedResponseCode == statusCode) {
            if (statusCode == 200) {
                WidgetsContent configuration = response.as(WidgetsContent.class);

//                softly.assertThat(configuration.getSize()).isNotNull();
//                softly.assertThat(Boolean.valueOf(valuesMap.get("whatsappChannelEnabled"))).isEqualTo(configuration.whatsappChannelEnabled);
//                softly.assertThat(Boolean.valueOf(valuesMap.get("smsChannelEnabled"))).isEqualTo(configuration.smsChannelEnabled);
//                softly.assertThat(valuesMap.get("apiKey")).isEqualTo(configuration.apiKey);
//                softly.assertThat(valuesMap.get("environment")).isEqualTo(configuration.environment);

            } else if (expectedResponseCode == 401) {
                verifyUnauthorisedResponse(valuesMap, response);
            }
            softly.assertAll();
        } else {
            Assertions.fail(format("Expected response code %s but was %s", expectedResponseCode, statusCode));
        }
    }
}
