package steps;

import api.clients.ApiHelperChannelManagement;
import api.clients.ApiHelperChat2Pay;
import api.models.request.channels.ChannelManagement;
import api.models.request.channels.ChannelStatus;
import api.models.request.channels.ChannelType;
import api.models.response.updatedresponse.UpdatedEntityResponse;
import api.models.response.widgetconfigurations.ChannelManagementStatusResponse;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.time.LocalDate;
import java.util.Map;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.Validator.verifyBadRequestResponse;
import static utils.Validator.verifyUnauthorisedResponse;

public class WidgetChannelSteps extends GeneralSteps {

    private Response response;

    @Then("^User links channel to the widget")
    public void linkChannelToWidget(Map<String, String> dataMap) {
        ChannelManagement body = ChannelManagement.builder()
                .smsOmniIntegrationId(dataMap.get("i.smsOmniIntegrationId"))
                .whatsappOmniIntegrationId(dataMap.get("i.whatsappOmniIntegrationId"))
                .build();

        response = ApiHelperChannelManagement.postChannelManagement(body, getWidgetId(dataMap), getActivationKey(dataMap));
        int statusCode = response.getStatusCode();
        int expectedResponseCode = getResponseCode(dataMap);

        if (expectedResponseCode == statusCode) {
            if (statusCode == 202) {
                assertThat(response.as(UpdatedEntityResponse.class).getUpdateTime())
                        .isBeforeOrEqualTo(LocalDate.now());
            } else if (expectedResponseCode == 401) {
                verifyUnauthorisedResponse(dataMap, response);
            } else if (expectedResponseCode == 404) {
                verifyBadRequestResponse(dataMap, response);
            }
        } else {
            Assertions.fail(format("Expected response code %s but was %s", expectedResponseCode, statusCode));
        }
    }

    @Then("^User updates channel status")
    public void updateChannelStatus(Map<String, String> dataMap) {
        ChannelStatus body = ChannelStatus.builder()
                .smsOmniIntStatus(dataMap.get("i.smsOmniIntStatus"))
                .waOmniIntStatus(dataMap.get("i.waOmniIntStatus"))
                .build();

        response = ApiHelperChannelManagement.updateChannelStatus(body, getWidgetId(dataMap), ApiHelperChat2Pay.token.get());
        int statusCode = response.getStatusCode();
        int responseCode = getResponseCode(dataMap);

        if (responseCode == statusCode) {
            if (statusCode == 200) {
                ChannelManagementStatusResponse statusResponse = response.as(ChannelManagementStatusResponse.class);
                softly.assertThat(statusResponse.getUpdateTime()).isBeforeOrEqualTo(LocalDate.now());
                softly.assertThat(statusResponse.smsChannelEnabled).isEqualTo(Boolean.parseBoolean(dataMap.get("i.smsOmniIntStatus")));
                softly.assertThat(statusResponse.whatsappChannelEnabled).isEqualTo(Boolean.parseBoolean(dataMap.get("i.waOmniIntStatus")));
                softly.assertAll();
            } else if (responseCode == 400 || responseCode == 404) {
                verifyBadRequestResponse(dataMap, response);
            }
        } else {
            Assertions.fail(format("Expected response code %s but was %s", responseCode, statusCode));
        }
    }

    @Then("^User deletes channel integration")
    public void deleteChannelIntegration(Map<String, String> dataMap) {
        ChannelType body = ChannelType.builder()
                .channelType(dataMap.get("i.channelType"))
                .build();

        response = ApiHelperChannelManagement.removeChannelIntegration(body, getWidgetId(dataMap), ApiHelperChat2Pay.token.get());
        int statusCode = response.getStatusCode();
        int expectedResponseCode = getResponseCode(dataMap);

        if (expectedResponseCode == statusCode) {
            if (statusCode == 200) {
                assertThat(response.as(UpdatedEntityResponse.class).getMessage())
                        .isEqualTo(format("Channel %s deleted successfully", dataMap.get("i.channelType")));
            } else if (expectedResponseCode == 400) {
                verifyBadRequestResponse(dataMap, response);
            }
        } else {
            Assertions.fail(format("Expected response code %s but was %s", expectedResponseCode, statusCode));
        }
    }
}
