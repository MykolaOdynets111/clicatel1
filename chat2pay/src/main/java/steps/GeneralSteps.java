package steps;

import api.clients.ApiHelperChat2Pay;
import api.clients.ApiHelperWidgets;
import org.assertj.core.api.SoftAssertions;

import java.util.Map;

public class GeneralSteps {

    protected static final ThreadLocal<String> widgetId = new ThreadLocal<>();
    protected static final ThreadLocal<String> applicationID = new ThreadLocal<>();
    protected static final ThreadLocal<String> activationKey = new ThreadLocal<>();
    protected static final ThreadLocal<String> createdWidgetId = new ThreadLocal<>();
    protected final SoftAssertions softly = new SoftAssertions();

    protected void setWidgetId(String widgetName) {
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

    protected static String getActivationKey(Map<String, String> valuesMap) {
        String authToken = valuesMap.get("activationKey");
        if (authToken.equals("token")) {
            authToken = ApiHelperChat2Pay.token.get();
        }
        return authToken;
    }
}