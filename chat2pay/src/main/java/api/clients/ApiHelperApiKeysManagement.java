package api.clients;

import api.MainApi;
import io.restassured.response.Response;

import static java.lang.String.format;


public class ApiHelperApiKeysManagement extends MainApi {

    public static Response getApiKeysManagement(String widgetId, String authToken) {
        return getQuery(format(Endpoints.WIDGET_API_KEYS_ENDPOINT, widgetId), authToken);
    }

//    public static Response updateTwoWayNumbers(String widgetId, TwoWayNumberConfiguration configuration, String authToken) {
//        return postQuery(format(Endpoints.API_KEYS_MANAGEMENT, widgetId), configuration, authToken);
//    }
}
