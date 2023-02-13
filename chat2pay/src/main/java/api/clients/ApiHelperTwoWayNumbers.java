package api.clients;

import api.MainApi;
import api.models.request.widgetConfigurations.TwoWayNumberConfiguration;
import io.restassured.response.Response;

import static java.lang.String.format;


public class ApiHelperTwoWayNumbers extends MainApi {

    public static Response getTwoWayNumbers(String widgetId, String authToken) {
        return getQuery(format(Endpoints.TWO_WAY_NUMBER, widgetId), authToken);
    }

    public static Response updateTwoWayNumbers(String widgetId, TwoWayNumberConfiguration configuration, String authToken) {
        return postQuery(format(Endpoints.TWO_WAY_NUMBER, widgetId), configuration, authToken);
    }
}
