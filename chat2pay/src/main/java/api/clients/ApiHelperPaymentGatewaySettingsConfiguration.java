package api.clients;

import api.MainApi;
import api.models.request.payments.PaymentGatewaySettingsUnifiedPayments;
import io.restassured.response.Response;

import java.io.File;

import static java.lang.String.format;


public class ApiHelperPaymentGatewaySettingsConfiguration extends MainApi {

    public static Response getLogo(String authToken, String widgetId, String gatewayId) {
        return getQuery(format(Endpoints.GET_PAYMENTS_GATEWAY_LOGO, widgetId, gatewayId), authToken);
    }

    public static Response postLogo(String authToken, String widgetId, File logo) {
        return postMediaFile(authToken, format(Endpoints.POST_PAYMENTS_GATEWAY_LOGO, widgetId), logo);
    }

    public static Response getPaymentsGatewaySettings(String authToken, String widgetId) {
        return getQuery(authToken, format(Endpoints.PAYMENTS_GATEWAY_ENDPOINT, widgetId));
    }

    public static Response postUnifiedPaymentsSettings(String authToken, String widgetId,
                                                       PaymentGatewaySettingsUnifiedPayments unifiedPayments) {
        return post(authToken, format(Endpoints.UNIFIED_PAYMENTS_SETTINGS, widgetId), unifiedPayments);
    }
}
