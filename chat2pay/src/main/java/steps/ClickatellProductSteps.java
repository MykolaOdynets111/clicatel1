package steps;

import api.clients.ApiHelperClickatellProduct;
import api.models.request.ApplicationBody;

import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.util.Map;

import static utils.Validator.validateErrorResponse;

public class ClickatellProductSteps extends GeneralSteps {

    @Then("^User adds clickatell-product application to the widget$")
    public void postClickatellProduct(Map<String, String> dataMap) {
        ApplicationBody body = new ApplicationBody();
        body.setApplicationId(dataMap.get("i.applicationId"));
        body.setStatus(dataMap.get("i.applicationStatus"));
        Response response = ApiHelperClickatellProduct.postClickatellProduct(getWidgetId(dataMap), body);
        softly.assertThat(response.statusCode()).isEqualTo(Integer.valueOf(dataMap.get("o.responseCode")));
        if (response.statusCode() == 404) {
            validateErrorResponse(response, dataMap);
        }
        softly.assertAll();
    }


    @Then("^User updates clickatell-product application to the widget$")
    public void updateClickatellProduct(Map<String, String> dataMap) {

        String applicationId = dataMap.get("i.applicationId");

    }


}