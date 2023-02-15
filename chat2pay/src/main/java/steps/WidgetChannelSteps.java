package steps;

import api.clients.ApiHelperChannelManagement;
import api.clients.ApiHelperChat2Pay;
import api.models.request.channels.ChannelManagement;
import api.models.request.channels.ChannelStatus;
import api.models.response.updatedresponse.UpdatedEntityResponse;
import api.models.response.widgetconfigurations.ChannelManagementStatusResponse;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.time.LocalDate;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.Validator.verifyBadRequestResponse;
import static utils.Validator.verifyUnauthorisedResponse;

public class WidgetChannelSteps extends GeneralSteps {

    private Response response;

    @Then("^User links channel to the widget")
    public void linkChannelToWidget(Map<String, String> valuesMap) {
        String widgetId = valuesMap.get("i.widgetId");
        ChannelManagement body = ChannelManagement.builder()
                .smsOmniIntegrationId(valuesMap.get("i.smsOmniIntegrationId"))
                .whatsappOmniIntegrationId(valuesMap.get("i.whatsappOmniIntegrationId"))
                .build();

        response = ApiHelperChannelManagement.postChannelManagement(body, widgetId, getActivationKey(valuesMap));
        int statusCode = response.getStatusCode();
        int expectedResponseCode = parseInt(valuesMap.get("o.responseCode"));

        if (expectedResponseCode == statusCode) {
            if (statusCode == 202) {
                assertThat(response.as(UpdatedEntityResponse.class).getUpdateTime())
                        .isBeforeOrEqualTo(LocalDate.now());
            } else if (expectedResponseCode == 401) {
                verifyUnauthorisedResponse(valuesMap, response);
            } else if (expectedResponseCode == 404) {
                verifyBadRequestResponse(valuesMap, response);
            }
        } else {
            Assertions.fail(format("Expected response code %s but was %s", expectedResponseCode, statusCode));
        }
    }

    @Then("^User updates channel status")
    public void updateChannelStatus(Map<String, String> valuesMap) {
        String widgetId = valuesMap.get("i.widgetId");
        ChannelStatus body = ChannelStatus.builder()
                .smsOmniIntStatus(valuesMap.get("i.smsOmniIntStatus"))
                .waOmniIntStatus(valuesMap.get("i.waOmniIntStatus"))
                .build();

        response = ApiHelperChannelManagement.updateChannelStatus(body, widgetId, ApiHelperChat2Pay.token.get());
        int statusCode = response.getStatusCode();
        int responseCode = parseInt(valuesMap.get("o.responseCode"));

        if (responseCode == statusCode) {
            if (statusCode == 200) {
                ChannelManagementStatusResponse statusResponse = response.as(ChannelManagementStatusResponse.class);
                softly.assertThat(statusResponse.getUpdateTime()).isBeforeOrEqualTo(LocalDate.now());
                softly.assertThat(statusResponse.smsChannelEnabled).isEqualTo(Boolean.parseBoolean(valuesMap.get("i.smsOmniIntStatus")));
                softly.assertThat(statusResponse.whatsappChannelEnabled).isEqualTo(Boolean.parseBoolean(valuesMap.get("i.waOmniIntStatus")));
                softly.assertAll();
            } else if (responseCode == 400 || responseCode == 404) {
                verifyBadRequestResponse(valuesMap, response);
            }
        } else {
            Assertions.fail(format("Expected response code %s but was %s", responseCode, statusCode));
        }
    }
}
