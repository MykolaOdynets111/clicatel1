package steps;

import api.clients.ApiHelperWidgets;
import api.models.request.WidgetBody;
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
    public static final ThreadLocal<String> createdWidgetId = new ThreadLocal<>();

    @When("^User gets widgetId for (.*) form$")
    public void getWidgetId(String widgetName) {
        setWidgetIdWidgetId(widgetName);
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
        softly.assertThat(response.getStatusCode())
                .as("Status code does not equal to expected")
                .isEqualTo(Integer.parseInt(dataMap.get("o.responseCode")));
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
                checkStatusCode(dataMap, response);
                assertThat(response.as(UpdatedEntityResponse.class).getUpdateTime())
                        .as(format("widget update date is not equals to %s", LocalDate.now()))
                        .isEqualTo(LocalDate.now());
                break;
            case "non_existed":
                response = ApiHelperWidgets.updateWidget("non_existed", updateBody);
                checkStatusCode(dataMap, response);
                Validator.validateErrorResponse(response, dataMap);
                break;
            case "wrong_status":
            case "wrong_env":
                response = ApiHelperWidgets.updateWidget(createdWidgetId.get(), updateBody);
                checkStatusCode(dataMap, response);
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
            createdWidgetId.remove();
        }
    }

}
