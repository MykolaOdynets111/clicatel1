package steps;

import api.clients.ApiHelperClickatellProduct;
import api.clients.ApiHelperCustomerApplication;
import api.models.request.ApplicationBody;
import api.models.response.integration.NotificationUrls;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.Validator.checkResponseCode;
import static utils.Validator.validateErrorResponse;

public class CustomerApplicationSteps extends GeneralSteps {


    @Then("^User adds customer application to the widget$")
    public void postCustomerApplication(Map<String, String> dataMap) {
        Response response = ApiHelperCustomerApplication.postCustomerApplication(getWidgetId(dataMap), setCustomerApplicationBody(dataMap));
        if (response.statusCode() == 200) {
            checkResponseCode(response, getResponseCode(dataMap));
            createdCustomerApplicationId.set(response.as(ApplicationBody.class).getApplicationId());
        } else if (String.valueOf(response.statusCode()).startsWith("4")) {
            validateErrorResponse(response, dataMap);
        } else {
            Assertions.fail(format("Expected response code %s but was %s", getResponseCode(dataMap), response.statusCode()));
        }
    }

    @Then("^User updates customer application to the widget$")
    public void updateCustomerApplication(Map<String, String> dataMap) {
        Response response = ApiHelperCustomerApplication.updateCustomerApplication(getWidgetId(dataMap), setCustomerApplicationBody(dataMap), createdCustomerApplicationId.get() );
        if (response.statusCode() == 200) {
            checkResponseCode(response, getResponseCode(dataMap));
            assertThat(response.as(ApplicationBody.class).getApplicationId()).isEqualTo(createdCustomerApplicationId.get());
        } else if (String.valueOf(response.statusCode()).startsWith("4")) {
            validateErrorResponse(response, dataMap);
        } else {
            Assertions.fail(format("Expected response code %s but was %s", getResponseCode(dataMap), response.statusCode()));
        }
    }

    @Then("^User deletes customer-application from the widget$")
    public void deleteCustomerApplication(Map<String, String> dataMap) {
        Response response = ApiHelperCustomerApplication.deleteCustomerApplication(getWidgetId(dataMap), createdCustomerApplicationId.get());
        checkResponseCode(response, getResponseCode(dataMap));
        if (response.statusCode() == 200) {
            assertThat(response.as(ApplicationBody.class).getApplicationId())
                    .isEqualTo(createdCustomerApplicationId.get());
        } else if (String.valueOf(response.statusCode()).startsWith("4")) {
            validateErrorResponse(response, dataMap);
        } else {
            Assertions.fail(format("Expected response code %s but was %s", getResponseCode(dataMap), response.statusCode()));
        }
    }
    @NotNull
    private ApplicationBody setCustomerApplicationBody(Map<String, String> dataMap) {
        return ApplicationBody.builder()
                .applicationName(faker.funnyName().name())
                .status(dataMap.get("i.applicationStatus"))
                .notificationUrls(NotificationUrls.builder()
                        .paymentStatusNotification(dataMap.get("i.paymentStatusNotification"))
                        .build()).build();
    }
}