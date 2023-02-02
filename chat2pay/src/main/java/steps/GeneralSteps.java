package steps;

import api.clients.ApiHelperWidgets;
import io.restassured.response.Response;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GeneralSteps {

    protected static final ThreadLocal<String> widgetId = new ThreadLocal<>();
    protected static final ThreadLocal<String> applicationID = new ThreadLocal<>();
    protected static final ThreadLocal<String> activationKey = new ThreadLocal<>();

    protected void setWidgetIdWidgetId(String widgetName) {
        widgetId.set(ApiHelperWidgets.getWidgetId(widgetName));
    }

    protected void setApplicationId() {
        applicationID.set(ApiHelperWidgets
                .getIntegrationResponse(widgetId.get())
                .getIntegrator()
                .getApplicationUuid());
    }

    protected void setActivationKey() {
        activationKey.set(ApiHelperWidgets.getActivationKey(widgetId.get()).getApiKey());
    }

    protected void checkStatusCode(Map<String, String> dataMap, Response response) {
        assertThat(response.getStatusCode())
                .as("Status code does not equal to expected")
                .isEqualTo(Integer.parseInt(dataMap.get("o.responseCode")));
    }
}
