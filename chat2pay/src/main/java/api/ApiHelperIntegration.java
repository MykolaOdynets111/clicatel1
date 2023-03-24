package api;

import io.restassured.response.Response;

public class ApiHelperIntegration extends ApiHelperChat2Pay {

    public static Response getIntegrationResponse(String widgetId) {
        return getQuery(token.get(), String.format(Endpoints.WIDGET_INTEGRATION_ENDPOINT, widgetId));
    }

    public static Response getApplicationResponse(String widgetId, String applicationId) {
        return getQuery(token.get(), String.format(Endpoints.WIDGET_INTEGRATION_ENDPOINT, widgetId) + "/" + applicationId);
    }
}
