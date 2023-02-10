package api.clients;

import api.MainApi;
import io.restassured.response.Response;

import static java.lang.String.format;


public class ApiHelperTwoWayNumbers extends MainApi {

    public static Response getTwoWayNumbers(String widgetId, String authToken) {
        return getQuery(format(Endpoints.TWO_WAY_NUMBER, widgetId), authToken);
    }
}
