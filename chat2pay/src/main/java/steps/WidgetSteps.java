package steps;

import api.clients.ApiHelperWidgets;
import api.models.request.WidgetBody;
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

public class WidgetSteps extends GeneralSteps{
    public static final ThreadLocal<String> createdWidgetId = new ThreadLocal<>();

    @When("^User gets widgetId for (.*) form$")
    public void getWidgetId(String widgetName) {
        widgetId.set(ApiHelperWidgets.getWidgetId(widgetName));
    }

    @When("^User gets application Id for widget$")
    public void getApplicationId() {
        applicationID.set(ApiHelperWidgets
                .getIntegrationResponse(widgetId.get())
                .getIntegrator()
                .getApplicationUuid());
    }

    @When("^User gets activation key for widget$")
    public void getActivationKey() {
        activationKey.set(ApiHelperWidgets.getActivationKey(widgetId.get()).getApiKey());
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

    @Then("^User delete newly created widget$")
    public void deleteCreatedWidget() {
        if (createdWidgetId.get() != null) {
            UpdatedEntityResponse updatedEntityResponseResponse = ApiHelperWidgets.deleteWidget(createdWidgetId.get()).as(UpdatedEntityResponse.class);
            assertThat(updatedEntityResponseResponse.getUpdateTime())
                    .as(format("widget creation date is not equals to %s", LocalDate.now()))
                    .isEqualTo(LocalDate.now());
            createdWidgetId.remove();
        }
    }

}
