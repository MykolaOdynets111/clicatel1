package api.clients;

import api.models.request.payments.PaymentGatewaySettingsSecureAcceptance;
import api.models.request.payments.PaymentGatewaySettingsUnifiedPayments;
import io.restassured.response.Response;

import java.io.File;

import static java.lang.String.format;


public class ApiHelperPaymentGatewaySettingsConfiguration extends ApiHelperChat2Pay {

    public static Response getLogo(String widgetId, String gatewayId) {
        return getQuery(format(Endpoints.GET_PAYMENTS_GATEWAY_LOGO, widgetId, gatewayId), token.get());
    }

    public static Response postLogo(String widgetId, File logo) {
        return postMediaFile(token.get(), format(Endpoints.POST_PAYMENTS_GATEWAY_LOGO, widgetId), logo);
    }

    public static Response getPaymentsGatewaySettings(String widgetId) {
        return getQuery(format(Endpoints.PAYMENTS_GATEWAY_ENDPOINT, widgetId), token.get());
    }

    public static Response postSecureAcceptanceSettings(String widgetId,
                                                        PaymentGatewaySettingsSecureAcceptance secureAcceptance) {
        return post(token.get(), format(Endpoints.SECURE_ACCEPTANCE_SETTINGS, widgetId), secureAcceptance);
    }

    public static Response postUnifiedPaymentsSettings(String widgetId,
                                                       PaymentGatewaySettingsUnifiedPayments unifiedPayments) {
        return post(token.get(), format(Endpoints.UNIFIED_PAYMENTS_SETTINGS, widgetId), unifiedPayments);
    }

    public static Response deletePaymentGatewaySettings(String widgetId,
                                                        String paymentGatewaySettingsId) {
        return deleteQuery(token.get(), format(Endpoints.DELETE_PAYMENTS_SETTINGS, widgetId, paymentGatewaySettingsId));
    }
}
