package steps;

import api.ApiHelperCustomerApplication;
import data.models.request.ApplicationBody;
import data.models.response.integration.IntegrationResponse;
import data.models.response.integration.NotificationUrls;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static api.ApiHelperIntegration.getIntegrationResponse;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.Validator.checkResponseCode;
import static utils.Validator.validateErrorResponse;

public class CustomerApplicationSteps extends GeneralSteps {

    private Response response;

    @Then("^User adds customer application to the widget$")
    public void postCustomerApplication(Map<String, String> dataMap) {
        response = ApiHelperCustomerApplication.postCustomerApplication(getWidgetId(dataMap), setCustomerApplicationBody(dataMap).build());
        if (getResponseCode(response) == 200) {
            checkResponseCode(response, getExpectedCode(dataMap));
            createdCustomerApplicationId.set(response.as(ApplicationBody.class).getApplicationId());
        } else if (String.valueOf(getResponseCode(response)).startsWith("4")) {
            validateErrorResponse(response, dataMap);
        } else {
            Assertions.fail(format("Expected response code %s but was %s", getExpectedCode(dataMap), getResponseCode(response)));
        }
    }

    @Then("^User updates customer application to the widget$")
    public void updateCustomerApplication(Map<String, String> dataMap) {
        response = ApiHelperCustomerApplication.updateCustomerApplication(getWidgetId(dataMap),
                setCustomerApplicationBody(dataMap).build(), createdCustomerApplicationId.get());
        if (getResponseCode(response) == 200) {
            checkResponseCode(response, getExpectedCode(dataMap));
            assertThat(response.as(ApplicationBody.class).getApplicationId()).isEqualTo(createdCustomerApplicationId.get());
        } else if (String.valueOf(getResponseCode(response)).startsWith("4")) {
            validateErrorResponse(response, dataMap);
        } else {
            Assertions.fail(format("Expected response code %s but was %s", getExpectedCode(dataMap), getResponseCode(response)));
        }
    }

    @Then("^User deletes customer-application from the widget$")
    public void deleteCustomerApplication(Map<String, String> dataMap) {
        response = ApiHelperCustomerApplication.deleteCustomerApplication(getWidgetId(dataMap), createdCustomerApplicationId.get());
        checkResponseCode(response, getExpectedCode(dataMap));
        if (getResponseCode(response) == 200) {
            assertThat(response.as(ApplicationBody.class).getApplicationId())
                    .isEqualTo(createdCustomerApplicationId.get());
        } else if (String.valueOf(getResponseCode(response)).startsWith("4")) {
            validateErrorResponse(response, dataMap);
        } else {
            Assertions.fail(format("Expected response code %s but was %s", getExpectedCode(dataMap), getResponseCode(response)));
        }
    }

    @Then("^User deletes all customer-applications from the widget$")
    public void deleteAllCustomerApplication(Map<String, String> dataMap) {
        response = ApiHelperCustomerApplication.deleteAllCustomerApplication(getWidgetId(dataMap));
        checkResponseCode(response, getExpectedCode(dataMap));
        if (getResponseCode(response) == 200) {
            assertThat(isApplicationPresent(response)).isTrue();
        } else if (String.valueOf(getResponseCode(response)).startsWith("4")) {
            validateErrorResponse(response, dataMap);
        } else {
            Assertions.fail(format("Expected response code %s but was %s", getExpectedCode(dataMap), getResponseCode(response)));
        }
    }

    @Then("^User adds order management system to the widget$")
    public void postOMS(Map<String, String> dataMap) {
        ApplicationBody application = setCustomerApplicationBody(dataMap)
                .applicationId("1")
                .status("ACTIVATED")
                .applicationType(dataMap.get("i.applicationType"))
                .build();

        switch (dataMap.get("i.widgetId")) {
            case "valid":
                response = ApiHelperCustomerApplication.postOrderManagementSystem(createdWidgetId.get(), application);
                String id = response.jsonPath().get("applicationId");
                assertThat(id).isEqualTo(application.getApplicationId());
                break;
            case "invalid":
                response = ApiHelperCustomerApplication.postOrderManagementSystem(null, application);
                validateErrorResponse(response, dataMap);
                break;
            default:
                Assertions.fail(format("The response code is not as expected %s", getExpectedCode(dataMap)));
        }
    }

    @Then("^User puts order management system to the widget$")
    public void putOMS(Map<String, String> dataMap) {
        String applicationId;
        ApplicationBody application = setCustomerApplicationBody(dataMap).status("DEACTIVATED").build();

        switch (dataMap.get("i.widgetId")) {
            case "valid":
                applicationId = getIntegrationResponse(createdWidgetId.get())
                        .jsonPath().getList("", IntegrationResponse.class)
                        .get(0).getIntegrator().getApplicationUuid();
                response = ApiHelperCustomerApplication.putOrderManagementSystem(createdWidgetId.get(), applicationId, application);
                String id = response.jsonPath().get("applicationId");
                assertThat(id).isEqualTo(application.getApplicationId());
                break;
            case "invalid":
                response = ApiHelperCustomerApplication.putOrderManagementSystem(null, null, application);
                validateErrorResponse(response, dataMap);
                break;
            default:
                Assertions.fail(format("The response code is not as expected %s", getExpectedCode(dataMap)));
        }
    }

    private boolean isApplicationPresent(Response response) {
        return response.jsonPath().getList("").stream().anyMatch(id -> id.equals(createdCustomerApplicationId.get()));
    }

    @NotNull
    private ApplicationBody.ApplicationBodyBuilder setCustomerApplicationBody(Map<String, String> dataMap) {
        return ApplicationBody.builder()
                .applicationName(faker.funnyName().name())
                .status(dataMap.get("i.applicationStatus"))
                .notificationUrls(NotificationUrls.builder()
                        .paymentStatusNotification(dataMap.get("i.paymentStatusNotification"))
                        .build());
    }
}