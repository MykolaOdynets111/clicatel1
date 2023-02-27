package api.clients;

import io.restassured.response.Response;

public class ApiHelperIntegration extends ApiHelperChat2Pay {

    public static Response getIntegrationResponse(String widgetId) {
        return getQuery(String.format(Endpoints.WIDGET_INTEGRATION_ENDPOINT, widgetId), token.get());
    }

    public static Response getApplicationResponse(String widgetId, String applicationId) {
        return getQuery(String.format(Endpoints.WIDGET_INTEGRATION_ENDPOINT, widgetId) + "/" + applicationId, token.get());
    }
}
