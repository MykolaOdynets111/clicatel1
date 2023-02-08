package steps;

import api.clients.ApiHelperChannelManagement;
import api.clients.ApiHelperWidgets;
import api.models.request.ChannelManagement;
import api.models.request.WidgetBody;
import api.models.response.UpdatedEntityResponse;
import api.models.response.failedresponse.ErrorResponse;
import api.models.response.widgetresponse.ConfigStatus;
import api.models.response.widgetresponse.Widget;
import api.models.response.widgetresponse.WidgetCreation;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import utils.Validator;

import java.time.LocalDate;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.Validator.validateErrorResponse;
import static utils.Validator.verifyBadRequestResponse;
import static utils.Validator.verifyUnauthorisedResponse;

public class WidgetSteps extends GeneralSteps {

    private Response response;

    @When("^User gets widgetId for (.*) form$")
    public void getWidgetId(String widgetName) {
        setWidgetId(widgetName);
    }

    @When("^User gets application Id for widget$")
    public void getApplicationId() {
        setApplicationId();
    }

    @When("^User gets activation key for widget$")
    public void getActivationKey() {
        setActivationKey();
    }

    @Then("^User creates widget for an account$")
    public void createWidget(Map<String, String> dataMap) {
        response = ApiHelperWidgets.createWidget(new WidgetBody(dataMap.get("i.type"), dataMap.get("i.environment")));

        Validator.checkResponseCode(response, dataMap.get("o.responseCode"));
        String status = dataMap.get("i.widget");
        switch (status) {
            case "valid":
                WidgetCreation widgetCreation = response.as(WidgetCreation.class);
                softly.assertThat(widgetCreation.getWidgetId())
                        .as("widget ID is not exist in the response")
                        .isNotEqualTo(null);
                softly.assertThat(widgetCreation.getCreatedTime())
                        .as(format("widget creation date is not equals to %s", LocalDate.now()))
                        .isEqualTo(LocalDate.now());
                softly.assertAll();
                createdWidgetId.set(response.as(WidgetCreation.class).getWidgetId());
                break;
            case "nonexisted":
                validateErrorResponse(response, dataMap);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", status));
        }
    }

    @Then("^User gets newly created widget$")
    public void getCreatedWidget(Map<String, String> dataMap) {
        switch (dataMap.get("i.widgetId")) {
            case "valid":
                response = ApiHelperWidgets.getWidget(createdWidgetId.get());
                Validator.checkResponseCode(response, dataMap.get("o.responseCode"));

                Widget widget = response.as(Widget.class);
                softly.assertThat(widget.getModifiedTime()).isNotNull();
                softly.assertThat(widget.getCreatedTime()).isNotNull();
                softly.assertThat(dataMap.get("o.name")).isEqualTo(widget.getName());
                softly.assertThat(dataMap.get("o.status")).isEqualTo(widget.getStatus());
                softly.assertThat(Integer.parseInt(dataMap.get("o.configStatus_id"))).isEqualTo(widget.getConfigStatus().getId());
                softly.assertThat(dataMap.get("o.configStatus_name")).isEqualToIgnoringCase(widget.getConfigStatus().getName());
                softly.assertThat(dataMap.get("o.environment")).isEqualTo(widget.getEnvironment());
                softly.assertAll();
                break;
            case "non_existed":
                response = ApiHelperWidgets.getWidget(dataMap.get("i.widgetId"));
                validateErrorResponse(response, dataMap);
                break;
        }
    }

    @Then("^User updates newly created widget$")
    public void updateCreatedWidget(Map<String, String> dataMap) {
        Widget updateBody = new Widget();
        updateBody.setEnvironment(dataMap.get("i.environment"));
        updateBody.setStatus(dataMap.get("i.status"));
        updateBody.setConfigStatus
                (new ConfigStatus(Integer.parseInt(dataMap.get("i.configStatus_id")), dataMap.get("i.configStatus_name")));
        updateBody.setName(dataMap.get("i.name"));
        switch (dataMap.get("i.widgetId")) {
            case "valid":
                response = ApiHelperWidgets.updateWidget(createdWidgetId.get(), updateBody);
                Validator.checkResponseCode(response, dataMap.get("o.responseCode"));
                assertThat(response.as(UpdatedEntityResponse.class).getUpdateTime())
                        .as(format("widget update date is not equals to %s", LocalDate.now()))
                        .isEqualTo(LocalDate.now());
                break;
            case "non_existed":
                response = ApiHelperWidgets.updateWidget(dataMap.get("i.widgetId"), updateBody);
                validateErrorResponse(response, dataMap);
                break;
            case "wrong_status":
            case "wrong_env":
                response = ApiHelperWidgets.updateWidget(createdWidgetId.get(), updateBody);
                validateErrorResponse(response, dataMap);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", dataMap.get("i.widgetId")));
        }
    }

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
                        .isEqualTo(LocalDate.now());
            } else if (expectedResponseCode == 401) {
                verifyUnauthorisedResponse(valuesMap, response);
            } else if (expectedResponseCode == 404) {
                verifyBadRequestResponse(valuesMap, response);
            }
        } else {
            Assertions.fail(format("Expected response code %s but was %s", expectedResponseCode, statusCode));
        }
    }

    @Then("^User delete newly created widget$")
    public void deleteCreatedWidget() {
        if (createdWidgetId.get() != null) {
            deleteWidget(createdWidgetId.get());
            verifyWidgetIsDeleted(createdWidgetId.get());
        }
    }

    @Then("^User updates show_linked_api for newly created widget$")
    public void updateShowLinkedApiCreatedWidget(Map<String, String> dataMap) {
        Widget body = new Widget();
        body.setShowLinkedApi(Boolean.parseBoolean(dataMap.get("i.showLinkedApi")));


        switch (dataMap.get("i.widgetId")) {
            case "valid":
                response = ApiHelperWidgets.updateShowLinkedApiForWidget(createdWidgetId.get(), body);
                Validator.checkResponseCode(response, dataMap.get("o.responseCode"));
                UpdatedEntityResponse updatedEntityResponse = response.as(UpdatedEntityResponse.class);
                softly.assertThat(updatedEntityResponse.getUpdateTime())
                        .as(format("widget update date is not equals to %s", LocalDate.now()))
                        .isEqualTo(LocalDate.now());
                softly.assertThat(updatedEntityResponse.isShowLinkedApi())
                        .isEqualTo(Boolean.valueOf(Boolean.parseBoolean(dataMap.get("i.showLinkedApi"))));
                softly.assertThat(ApiHelperWidgets.getWidget(createdWidgetId.get()).as(Widget.class)
                                .isShowLinkedApi())
                        .isEqualTo(Boolean.valueOf(Boolean.parseBoolean(dataMap.get("i.showLinkedApi"))));
                softly.assertAll();
                break;
            case "non_existed":
                response = ApiHelperWidgets.updateShowLinkedApiForWidget(dataMap.get("i.widgetId"), body);
                Validator.checkResponseCode(response, dataMap.get("o.responseCode"));
                validateErrorResponse(response, dataMap);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", dataMap.get("i.widgetId")));

        }
    }


    private void verifyWidgetIsDeleted(String widgetId) {
        response = ApiHelperWidgets.getWidget(widgetId);
        Validator.checkResponseCode(response, "404");
        boolean widgetDoesNotExist = response.as(ErrorResponse.class).getErrors()
                .stream()
                .anyMatch(e -> e.contains("Widget does not exist"));
        assertThat(widgetDoesNotExist)
                .as(format("The newly created widget has not been deleted. widgetId : %s", widgetId))
                .isTrue();
    }

    private static void deleteWidget(String widgetId) {
        UpdatedEntityResponse updatedEntityResponse = ApiHelperWidgets.deleteWidget(widgetId)
                .as(UpdatedEntityResponse.class);
        assertThat(updatedEntityResponse.getUpdateTime())
                .as(format("widget delete date is not equals to %s", LocalDate.now()))
                .isEqualTo(LocalDate.now());
    }
}
