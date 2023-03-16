package api.clients;

import api.MainApi;
import api.models.request.widgetconfigurations.TwoWayNumberConfiguration;
import io.restassured.response.Response;

import static java.lang.String.format;


public class ApiHelperTwoWayNumbers extends MainApi {

    public static Response getTwoWayNumbers(String widgetId, String authToken) {
        return getQuery(authToken, format(Endpoints.TWO_WAY_NUMBER, widgetId));
    }

    public static Response updateTwoWayNumbers(String widgetId, TwoWayNumberConfiguration configuration, String authToken) {
        return postQuery(authToken, format(Endpoints.TWO_WAY_NUMBER, widgetId), configuration);
    }
}
