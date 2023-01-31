package api.clients;

import api.models.request.PaymentBody;
import api.models.request.ReceiptBody;
import api.models.response.paymentgatewaysettingsresponse.PaymentGatewaySettingsResponse;
import io.restassured.response.Response;

public class ApiHelperTransactions extends ApiHelperChat2Pay {

    public static PaymentGatewaySettingsResponse getPaymentGatewaySettingsResponse(String widgetId) {
        return getChat2PayQuery(String.format(Endpoints.PAYMENTS_GATEWAY_ENDPOINT, widgetId))
                .jsonPath()
                .getList("", PaymentGatewaySettingsResponse.class)
                .stream().findAny()
                .orElseThrow(() -> new AssertionError("No payment gateway settings found for widget" + widgetId));
    }

    public static void checkWorkingPaymentLink(String paymentLink) {
        getChat2PayQuery(paymentLink);
    }

    public static Response userGetAPaymentLinkResponse(PaymentBody paymentBody, String activationKey) {
        return postQuery(Endpoints.CHAT_TO_PAY_ENDPOINT, paymentBody, activationKey);
    }

    public static Response cancelPaymentLink(String paymentLinkRef, String activationKey) {
        return postQuery(Endpoints.CANCEL_PAYMENT_LINK_ENDPOINT + paymentLinkRef, "", activationKey);
    }

    public static Response receivePaymentLink(String paymentLinkRef, String activationKey) {
        return postQuery(Endpoints.PAYMENT_RECEIPT, new ReceiptBody(paymentLinkRef), activationKey);
    }
}
