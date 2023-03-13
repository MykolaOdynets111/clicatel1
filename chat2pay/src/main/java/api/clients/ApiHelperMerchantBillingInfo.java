package api.clients;

import api.models.request.MerchantBillingInfoBody;
import io.restassured.response.Response;

public class ApiHelperMerchantBillingInfo extends ApiHelperChat2Pay {

    public static Response postMerchantBillingInfoCreatedWidget(String widgetId, MerchantBillingInfoBody widgetBody) {
        return postQuery(token.get(), String.format(Endpoints.MERCHANTS_BILLING_INFO, widgetId), widgetBody);
    }

    public static Response getMerchantBillingInfoCreatedWidget(String widgetId) {
        return getQuery(String.format(Endpoints.MERCHANTS_BILLING_INFO, widgetId), token.get());
    }

    public static Response deleteMerchantBillingInfoCreatedWidget(String widgetId) {
        return deleteQuery(String.format(Endpoints.MERCHANTS_BILLING_INFO, widgetId), token.get());
    }
}
