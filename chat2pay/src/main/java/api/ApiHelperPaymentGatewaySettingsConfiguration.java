package api;

import data.models.request.payments.PaymentGatewaySettingsSecureAcceptance;
import data.models.request.payments.PaymentGatewaySettingsUnifiedPayments;
import io.restassured.response.Response;

import java.io.File;

import static java.lang.String.format;


public class ApiHelperPaymentGatewaySettingsConfiguration extends ApiHelperChat2Pay {

    public static Response getLogo(String widgetId, String gatewayId) {
        return getMediaType(token.get(), String.format(Endpoints.GET_PAYMENTS_GATEWAY_LOGO, widgetId, gatewayId));
    }

    public static Response postLogo(String widgetId, File logo) {
        return postMediaFile(token.get(), format(Endpoints.POST_PAYMENTS_GATEWAY_LOGO, widgetId), logo);
    }

    public static Response getPaymentsGatewaySettings(String widgetId) {
        return getQuery(token.get(), format(Endpoints.PAYMENTS_GATEWAY_ENDPOINT, widgetId));
    }

    public static Response postSecureAcceptanceSettings(String widgetId,
                                                        PaymentGatewaySettingsSecureAcceptance secureAcceptance) {
        return post(token.get(), format(Endpoints.SECURE_ACCEPTANCE_SETTINGS, widgetId), secureAcceptance);
    }

    public static Response postUnifiedPaymentsSettings(String widgetId,
                                                       PaymentGatewaySettingsUnifiedPayments unifiedPayments) {
        return post(token.get(), format(Endpoints.UNIFIED_PAYMENTS_SETTINGS, widgetId), unifiedPayments);
    }

    public static Response postUnifiedPaymentsSettings(String widgetId, String logoId,
                                                       PaymentGatewaySettingsUnifiedPayments unifiedPayments) {
        unifiedPayments.setLogoId(logoId);
        return post(token.get(), format(Endpoints.UNIFIED_PAYMENTS_SETTINGS, widgetId), unifiedPayments);
    }

    public static Response deletePaymentGatewaySettings(String widgetId,
                                                        String paymentGatewaySettingsId) {
        return deleteQuery(token.get(), format(Endpoints.DELETE_PAYMENTS_SETTINGS, widgetId, paymentGatewaySettingsId));
    }
}
