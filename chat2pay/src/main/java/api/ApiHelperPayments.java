package api;

import data.models.request.PaymentBody;
import data.models.request.ReceiptBody;
import data.models.response.paymentgatewaysettingsresponse.PaymentGatewaySettingsResponse;
import io.restassured.response.Response;

public class ApiHelperPayments extends ApiHelperChat2Pay {

    public static PaymentGatewaySettingsResponse getPaymentGatewaySettingsResponse(String widgetId) {
        return getChat2PayQuery(String.format(Endpoints.PAYMENTS_GATEWAY_ENDPOINT, widgetId))
                .jsonPath()
                .getList("", PaymentGatewaySettingsResponse.class)
                .stream().findAny()
                .orElseThrow(() -> new AssertionError("No payment gateway settings found for widget - " + widgetId));
    }

    public static void checkWorkingPaymentLink(String paymentLink) {
        getChat2PayQuery(paymentLink);
    }

    public static Response userGetAPaymentLinkResponse(PaymentBody paymentBody, String activationKey) {
        return postQuery(activationKey, Endpoints.CHAT_TO_PAY_ENDPOINT, paymentBody);
    }

    public static Response cancelPaymentLink(String paymentLinkRef, String activationKey) {
        return postQuery(activationKey, Endpoints.CANCEL_PAYMENT_LINK_ENDPOINT + paymentLinkRef, "");
    }

    public static Response receivePaymentLink(String paymentLinkRef, String activationKey) {
        ReceiptBody receiptBody = new ReceiptBody();
        receiptBody.setPaymentLinkRef(paymentLinkRef);
        return postQuery(activationKey, Endpoints.PAYMENT_RECEIPT, receiptBody);
    }
}
