package steps;

import api.clients.ApiHelperWidgets;

public class GeneralSteps {

    protected static final ThreadLocal<String> widgetId = new ThreadLocal<>();
    protected static final ThreadLocal<String> applicationID = new ThreadLocal<>();
    protected static final ThreadLocal<String> activationKey = new ThreadLocal<>();
    protected static final ThreadLocal<String> createdWidgetId = new ThreadLocal<>();

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
}