package api.clients;

import api.models.request.PaymentBody;
import api.models.request.ReceiptBody;
import api.models.request.WidgetBody;
import api.models.response.ApiKeysResponse;
import api.models.response.integrationresponse.IntegrationResponse;
import api.models.response.paymentgatewaysettingsresponse.PaymentGatewaySettingsResponse;
import api.models.response.widgetresponse.Widget;
import api.models.response.widgetresponse.WidgetsContent;
import io.restassured.response.Response;

import java.util.List;

public class ApiHelperTransactions extends ApiHelperChat2Pay {

    public static String getWidgetId(String widgetName) {
        List<Widget> widgets = getChat2PayQuery(Endpoints.EXISTED_WIDGETS_ENDPOINT)
                .as(WidgetsContent.class)
                .getWidgets();
        return widgets.stream().filter(w -> w.getName().equals(widgetName)).findFirst()
                .orElseThrow(() -> new AssertionError("Widget didn't find"))
                .getId();
    }

    public static PaymentGatewaySettingsResponse getPaymentGatewaySettingsResponse(String widgetId) {
        return getChat2PayQuery(String.format(Endpoints.PAYMENTS_GATEWAY_ENDPOINT, widgetId))
                .jsonPath()
                .getList("", PaymentGatewaySettingsResponse.class)
                .stream().findAny()
                .orElseThrow(() -> new AssertionError("No payment gateway settings found for widget" + widgetId));
    }

    public static IntegrationResponse getIntegrationResponse(String widgetId) {
        return getChat2PayQuery(String.format(Endpoints.WIDGET_INTEGRATION_ENDPOINT, widgetId))
                .jsonPath()
                .getList("", IntegrationResponse.class)
                .stream().findAny()
                .orElseThrow(() -> new AssertionError("No integrations found for widget" + widgetId));
    }

    public static ApiKeysResponse getActivationKey(String widgetId) {
        return getChat2PayQuery(String.format(Endpoints.WIDGET_API_KEYS_ENDPOINT, widgetId))
                .jsonPath()
                .getList("", ApiKeysResponse.class)
                .stream().findAny()
                .orElseThrow(() -> new AssertionError("No activation key found for widget" + widgetId));
    }

    public static void checkWorkingPaymentLink(String paymentLink) {
        getChat2PayQuery(paymentLink);
    }

    public static Response userGetAPaymentLinkResponse(PaymentBody paymentBody, String activationKey) {
        return postQuery(Endpoints.CHAT_TO_PAY_ENDPOINT, paymentBody, activationKey);
    }

    public static Response createWidget(WidgetBody widgetBody) {
        return postQuery(Endpoints.WIDGETS_ENDPOINT, widgetBody, token.get());
    }

    public static Response deleteWidget(String widgetId) {
        return deleteQuery(Endpoints.WIDGETS_ENDPOINT + "/" + widgetId, token.get());
    }

    public static Response cancelPaymentLink(String paymentLinkRef, String activationKey) {
        return postQuery(Endpoints.CANCEL_PAYMENT_LINK_ENDPOINT + paymentLinkRef, "", activationKey);
    }

    public static Response receivePaymentLink(String paymentLinkRef, String activationKey) {
        return postQuery(Endpoints.PAYMENT_RECEIPT, new ReceiptBody(paymentLinkRef), activationKey);
    }
}
