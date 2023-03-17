package steps;

import api.ApiHelperClickatellProduct;
import data.models.request.ApplicationBody;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.Validator.checkResponseCode;
import static utils.Validator.validateErrorResponse;

public class ClickatellProductSteps extends GeneralSteps {

    @Then("^User updates clickatell-product application to the widget$")
    public void updateClickatellProduct(Map<String, String> dataMap) {
        Response response = ApiHelperClickatellProduct.updateClickatellProduct(getWidgetId(dataMap), setApplicationBody(dataMap));
        if (response.statusCode() == 200) {
            checkResponseCode(response, getResponseCode(dataMap));
        } else if (String.valueOf(response.statusCode()).startsWith("4")) {
            validateErrorResponse(response, dataMap);
        } else {
            Assertions.fail(format("Expected response code %s but was %s", getResponseCode(dataMap), response.statusCode()));
        }
    }

    @Then("^User adds clickatell-product application to the widget$")
    public void postClickatellProduct(Map<String, String> dataMap) {
        Response response = ApiHelperClickatellProduct.postClickatellProduct(getWidgetId(dataMap), setApplicationBody(dataMap));
        if (response.statusCode() == 200) {
            checkResponseCode(response, getResponseCode(dataMap));
        } else if (String.valueOf(response.statusCode()).startsWith("4")) {
            validateErrorResponse(response, dataMap);
        } else {
            Assertions.fail(format("Expected response code %s but was %s", getResponseCode(dataMap), response.statusCode()));
        }
    }

    @Then("^User gets clickatell-product application for the widget$")
    public void getClickatellProduct(Map<String, String> dataMap) {
        Response response = ApiHelperClickatellProduct.getClickatellProduct(getWidgetId(dataMap), dataMap.get("i.applicationId"));
        checkResponseCode(response, getResponseCode(dataMap));
        if (response.statusCode() == 200) {
            assertThat(response.getBody().asString()).isEqualTo(dataMap.get("o.body"));
        } else if (String.valueOf(response.statusCode()).startsWith("4")) {
            validateErrorResponse(response, dataMap);
        } else {
            Assertions.fail(format("Expected response code %s but was %s", getResponseCode(dataMap), response.statusCode()));
        }
    }

    @Then("^User deletes clickatell-product application from the widget$")
    public void deleteClickatellProduct(Map<String, String> dataMap) {
        Response response = ApiHelperClickatellProduct.deleteClickatellProduct(getWidgetId(dataMap), dataMap.get("i.applicationId"));
        checkResponseCode(response, getResponseCode(dataMap));
        if (response.statusCode() == 200) {
            assertThat(response.as(ApplicationBody.class).getApplicationId())
                    .isEqualTo(dataMap.get("o.applicationId"));
        } else if (String.valueOf(response.statusCode()).startsWith("4")) {
            validateErrorResponse(response, dataMap);
        } else {
            Assertions.fail(format("Expected response code %s but was %s", getResponseCode(dataMap), response.statusCode()));
        }
    }

    @NotNull
    private ApplicationBody setApplicationBody(Map<String, String> dataMap) {
        return ApplicationBody.builder()
                .applicationId(dataMap.get("i.applicationId"))
                .status(dataMap.get("i.applicationStatus"))
                .build();
    }
}