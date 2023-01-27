package steps;

import api.clients.ApiHelperTransactions;
import api.models.request.WidgetBody;
import api.models.response.widgetresponse.WidgetCreation;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.Map;

public class WidgetSteps {
    public static final ThreadLocal<WidgetBody> widgetBody = new ThreadLocal<>();
    public static final ThreadLocal<String> createdWidgetId = new ThreadLocal<>();


    @Then("^User creates widget for an account$")
    public void createWidget(Map<String, String> dataMap) {
        widgetBody.set(new WidgetBody(dataMap.get("i.type"), dataMap.get("i.environment")));
        Response response = ApiHelperTransactions.createWidget(widgetBody.get());
        createdWidgetId.set(response.as(WidgetCreation.class).getWidgetId());
    }

    @Then("^User delete newly created widget$")
    public void deleteCreatedWidget() {
        ApiHelperTransactions.deleteWidget(createdWidgetId.get());

    }

}
