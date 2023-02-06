package steps;

import api.clients.ApiHelperWidgets;
import api.models.request.WidgetBody;
import api.models.response.failedresponse.ErrorResponse;
import api.models.response.widgetresponse.ConfigStatus;
import api.models.response.widgetresponse.Widget;
import api.models.response.widgetresponse.WidgetCreation;
import api.models.response.UpdatedEntityResponse;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import utils.Validator;

import java.time.LocalDate;
import java.util.Map;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WidgetSteps extends GeneralSteps {


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
        Response response = ApiHelperWidgets
                .createWidget(new WidgetBody(dataMap.get("i.type"), dataMap.get("i.environment")));
        SoftAssertions softly = new SoftAssertions();
        Validator.checkResponseCode(response, dataMap);
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
                Validator.validateErrorResponse(response, dataMap);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", status));
        }
    }

    @Then("^User gets newly created widget$")
    public void getCreatedWidget(Map<String, String> dataMap) {

        switch (dataMap.get("i.widgetId")) {
            case "valid":
                SoftAssertions softly = new SoftAssertions();

                Response response = ApiHelperWidgets.readWidget(createdWidgetId.get());
                Validator.checkResponseCode(response, dataMap);

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
                response = ApiHelperWidgets.readWidget("non_existed");
                Validator.validateErrorResponse(response, dataMap);
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
        Response response;
        switch (dataMap.get("i.widgetId")) {
            case "valid":
                response = ApiHelperWidgets.updateWidget(createdWidgetId.get(), updateBody);
                Validator.checkResponseCode(response, dataMap);
                assertThat(response.as(UpdatedEntityResponse.class).getUpdateTime())
                        .as(format("widget update date is not equals to %s", LocalDate.now()))
                        .isEqualTo(LocalDate.now());
                break;
            case "non_existed":
                response = ApiHelperWidgets.updateWidget("non_existed", updateBody);
                Validator.validateErrorResponse(response, dataMap);
                break;
            case "wrong_status":
            case "wrong_env":
                response = ApiHelperWidgets.updateWidget(createdWidgetId.get(), updateBody);
                Validator.validateErrorResponse(response, dataMap);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", dataMap.get("i.widgetId")));
        }

    }


    @Then("^User delete newly created widget$")
    public void deleteCreatedWidget() {
        if (createdWidgetId.get() != null) {
            UpdatedEntityResponse updatedEntityResponse = ApiHelperWidgets.deleteWidget(createdWidgetId.get()).as(UpdatedEntityResponse.class);
            assertThat(updatedEntityResponse.getUpdateTime())
                    .as(format("widget delete date is not equals to %s", LocalDate.now()))
                    .isEqualTo(LocalDate.now());
            Response response = ApiHelperWidgets.readWidget(createdWidgetId.get());
            Validator.checkResponseCode(response, "404");
            assertThat(response.as(ErrorResponse.class).getErrors().stream()
                    .anyMatch(e -> e.contains("Widget does not exist")))
                    .as(format("The newly created widget has not been deleted. widgetId : %s", createdWidgetId.get()));
        }
    }

}
