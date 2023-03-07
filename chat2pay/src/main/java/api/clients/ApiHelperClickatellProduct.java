package api.clients;

import api.models.request.ApplicationBody;
import api.models.request.MerchantBillingInfoBody;
import io.restassured.response.Response;

public class ApiHelperClickatellProduct extends ApiHelperChat2Pay {

    public static Response postClickatellProduct(String widgetId, ApplicationBody applicationBody) {
        return postQuery(String.format(Endpoints.CLICKATELL_PRODUCT_ENDPOINT, widgetId), applicationBody, token.get());
    }

//    public static Response getMerchantBillingInfoCreatedWidget(String widgetId) {
//        return getQuery(String.format(Endpoints.MERCHANTS_BILLING_INFO, widgetId), token.get());
//    }
//
//    public static Response deleteMerchantBillingInfoCreatedWidget(String widgetId) {
//        return deleteQuery(String.format(Endpoints.MERCHANTS_BILLING_INFO, widgetId), token.get());
//    }
}
