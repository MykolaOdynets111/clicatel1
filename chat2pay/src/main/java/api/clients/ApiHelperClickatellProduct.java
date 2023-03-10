package api.clients;

import api.models.request.ApplicationBody;
import api.models.request.MerchantBillingInfoBody;
import io.restassured.response.Response;

public class ApiHelperClickatellProduct extends ApiHelperChat2Pay {

    public static Response postClickatellProduct(String widgetId, ApplicationBody applicationBody) {
        return postQuery(String.format(Endpoints.CLICKATELL_PRODUCT_ENDPOINT, widgetId), applicationBody, token.get());
    }

    public static Response updateClickatellProduct(String widgetId, ApplicationBody applicationBody) {
        return putQuery(String.format(Endpoints.CLICKATELL_PRODUCT_ENDPOINT, widgetId), applicationBody, token.get());
    }

    public static Response getClickatellProduct(String widgetId, String applicationId) {
        return getQuery(String.format(Endpoints.CLICKATELL_PRODUCT_ENDPOINT, widgetId) + "/" + applicationId, token.get());
    }

    public static Response deleteClickatellProduct(String widgetId, String applicationId) {
        return deleteQuery(String.format(Endpoints.CLICKATELL_PRODUCT_ENDPOINT, widgetId) + "/" + applicationId, token.get());
    }
}
