package api.clients;

import api.models.request.PaymentBody;
import api.models.response.*;
import io.restassured.response.Response;

import java.util.List;

public class TransactionsHelper extends Chat2PayApiHelper {


    public static String getWidgetId(String widgetName) {
        List<Widget> widgets = getChat2PayQuery(Endpoints.EXISTED_WIDGETS_ENDPOINT)
                .as(Content.class)
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

    public static Response userGetAPaymentLinkResponse(PaymentBody paymentBody, String activationKey) {
        return postQuery(Endpoints.CHAT_TO_PAY_ENDPOINT, paymentBody, activationKey);
    }

    public static void checkWorkingPaymentLink(String paymentLink) {
        getChat2PayQuery(paymentLink);
    }

    public static Response cancelPaymentLink(String paymentLinkRef, String activationKey) {
        return postQuery(Endpoints.CANCEL_PAYMENT_LINK_ENDPOINT + paymentLinkRef, "", activationKey);
    }


}
