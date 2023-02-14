package steps;


import api.clients.ApiHelperMessagesConfigurations;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import utils.Validator;

import java.util.Map;

import static java.lang.String.format;
import static utils.Validator.*;

public class MessageSteps extends GeneralSteps {

    private Response response;

    @Then("^User gets configuration for newly created widget$")
    public void getMessageConfiguration(Map<String, String> dataMap) {
        switch (dataMap.get("i.widgetId")) {
            case "valid":
                response = ApiHelperMessagesConfigurations.getMessageConfigurationResponse(createdWidgetId.get());
                Validator.checkResponseCode(response, dataMap.get("o.responseCode"));
                break;
            case "non_existed":
                response = ApiHelperMessagesConfigurations.getMessageConfigurationResponse(dataMap.get("i.widgetId"));
                validateErrorResponse(response, dataMap);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", dataMap.get("i.widgetId")));
        }
    }
}