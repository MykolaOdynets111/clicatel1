package api;

import api.MainApi;
import api.Endpoints;
import io.restassured.response.Response;

import static java.lang.String.format;


public class ApiHelperApiKeysManagement extends MainApi {

    public static Response getApiKeysManagement(String widgetId, String authToken) {
        return getQuery(authToken, String.format(Endpoints.WIDGET_API_KEYS_ENDPOINT, widgetId));
    }

    public static Response updateApiKeysManagement(String widgetId, String authToken) {
        return postQuery(authToken, format(Endpoints.WIDGET_API_KEYS_ENDPOINT, widgetId), "");
    }

    public static Response removeApiKeysManagement(String widgetId, String apiKeyId, String authToken) {
        return deleteQuery(authToken, format(Endpoints.DELETE_API_KEY, widgetId, apiKeyId));
    }
}
