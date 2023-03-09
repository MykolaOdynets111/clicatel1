package steps;

import api.clients.ApiHelperClickatellProduct;
import api.models.request.ApplicationBody;

import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static utils.Validator.validateErrorResponse;

public class ClickatellProductSteps extends GeneralSteps {

    @Then("^User adds clickatell-product application to the widget$")
    public void postClickatellProduct(Map<String, String> dataMap) {
        ApplicationBody body = setApplicationBody(dataMap);
        Response response = ApiHelperClickatellProduct.postClickatellProduct(getWidgetId(dataMap), body);
        checkStatusCode(dataMap, response);
        if (String.valueOf(response.statusCode()).startsWith("4")) {
            validateErrorResponse(response, dataMap);
        }
        softly.assertAll();
    }


    @Then("^User updates clickatell-product application to the widget$")
    public void updateClickatellProduct(Map<String, String> dataMap) {
        ApplicationBody body = setApplicationBody(dataMap);
        Response response = ApiHelperClickatellProduct.updateClickatellProduct(getWidgetId(dataMap), body);
        checkStatusCode(dataMap, response);
        if (String.valueOf(response.statusCode()).startsWith("4")) {
            validateErrorResponse(response, dataMap);
        }
        softly.assertAll();
    }

    @Then("^User gets clickatell-product application for the widget$")
    public void getClickatellProduct(Map<String, String> dataMap) {
        Response response = ApiHelperClickatellProduct.getClickatellProduct(getWidgetId(dataMap), dataMap.get("i.applicationId"));
        checkStatusCode(dataMap, response);
        if (response.statusCode() == 200) {
            softly.assertThat(response.getBody().asString()).isEqualTo(dataMap.get("o.body"));
        }
        if (String.valueOf(response.statusCode()).startsWith("4")) {
            validateErrorResponse(response, dataMap);
        }
        softly.assertAll();
    }

    @Then("^User deletes clickatell-product application from the widget$")
    public void deleteClickatellProduct(Map<String, String> dataMap) {
        Response response = ApiHelperClickatellProduct.deleteClickatellProduct(getWidgetId(dataMap), dataMap.get("i.applicationId"));
        checkStatusCode(dataMap, response);
        if (response.statusCode() == 200) {
            softly.assertThat(response.as(ApplicationBody.class).getApplicationId())
                    .isEqualTo(dataMap.get("o.applicationId"));
        }
        if (String.valueOf(response.statusCode()).startsWith("4")) {
            validateErrorResponse(response, dataMap);
        }
        softly.assertAll();
    }

    @NotNull
    private ApplicationBody setApplicationBody(Map<String, String> dataMap) {
        ApplicationBody body = new ApplicationBody();
        body.setApplicationId(dataMap.get("i.applicationId"));
        body.setStatus(dataMap.get("i.applicationStatus"));
        return body;
    }

    private void checkStatusCode(Map<String, String> dataMap, Response response) {
        softly.assertThat(response.statusCode()).isEqualTo(Integer.valueOf(dataMap.get("o.responseCode")));
    }
}