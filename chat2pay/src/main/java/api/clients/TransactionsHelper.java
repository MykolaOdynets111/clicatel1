package api.clients;

import api.models.request.PaymentBody;
import api.models.response.*;
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

    public static String getPaymentGatewaySettingsId( String widgetId) {
        return getChat2PayQuery(String.format(Endpoints.PAYMENTS_GATEWAY_ENDPOINT, widgetId))
                .jsonPath()
                .getList("", PaymentGatewaySettingsResponse.class)
                .get(0)
                .getPaymentGatewaySettingsId();
    }

    public static String getApplicationId(String widgetId) {
        return getChat2PayQuery(String.format(Endpoints.WIDGET_INTEGRATION_ENDPOINT, widgetId))
                .jsonPath()
                .getList("", IntegrationResponse.class)
                .get(0)
                .getIntegrator().getApplicationUuid();
    }

    public static String getActivationKey(String widgetId) {
        return getChat2PayQuery(String.format(Endpoints.WIDGET_API_KEYS_ENDPOINT, widgetId))
                .jsonPath()
                .getList("", ApiKeysResponse.class)
                .get(0)
                .getApiKey();
    }

    public static String userCanGetAPaymentLink(PaymentBody paymentBody, String activationKey) {
        return postQuery(Endpoints.CHAT_TO_PAY_ENDPOINT, paymentBody, activationKey, 201)
                .as(PaymentLinkResponse.class)
                .getPaymentLink();
    }

    public static void userCanNotGetAPaymentLink(PaymentBody paymentBody, String activationKey) {
        postQuery(Endpoints.CHAT_TO_PAY_ENDPOINT, paymentBody, activationKey, 400);
    }

    public static void checkWorkingPaymentLink( String paymentLink) {
        getChat2PayQuery(paymentLink);
    }



}
