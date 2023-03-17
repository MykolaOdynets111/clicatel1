package api;

import data.models.request.ApplicationBody;
import io.restassured.response.Response;

public class ApiHelperClickatellProduct extends ApiHelperChat2Pay {

    public static Response postClickatellProduct(String widgetId, ApplicationBody applicationBody) {
        return postQuery(token.get(), String.format(Endpoints.CLICKATELL_PRODUCT_ENDPOINT, widgetId), applicationBody);
    }

    public static Response updateClickatellProduct(String widgetId, ApplicationBody applicationBody) {
        return putQuery(token.get(), String.format(Endpoints.CLICKATELL_PRODUCT_ENDPOINT, widgetId), applicationBody);
    }

    public static Response getClickatellProduct(String widgetId, String applicationId) {
        return getQuery(token.get(), String.format(Endpoints.CLICKATELL_PRODUCT_ENDPOINT, widgetId) + "/" + applicationId);
    }

    public static Response deleteClickatellProduct(String widgetId, String applicationId) {
        return deleteQuery(token.get(), String.format(Endpoints.CLICKATELL_PRODUCT_ENDPOINT, widgetId) + "/" + applicationId);
    }
}
