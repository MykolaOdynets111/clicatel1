package api.clients;

import api.models.request.PaymentBody;
import api.models.response.ApiKeysResponse;
import api.models.response.Content;
import api.models.response.IntegrationResponse;
import api.models.response.paymentgatewaysettingsresponse.PaymentGatewaySettingsResponse;
import api.models.response.paymentgatewaysettingsresponse.PaymentLinkResponse;
import api.models.response.widget.Widget;
import io.restassured.response.Response;

import java.util.List;

import static api.clients.Endpoints.CHAT_TO_PAY_CONFIGURATION_ENDPOINT;
import static java.lang.String.format;

public class ApiHelperTransactions extends ApiHelperChat2Pay {

    public static String getWidgetId(String widgetName) {
        List<Widget> widgets = getChat2PayQuery(Endpoints.EXISTED_WIDGETS_ENDPOINT)
                .as(Content.class)
                .getWidgets();
        return widgets.stream().filter(w -> w.getName().equals(widgetName)).findFirst()
                .orElseThrow(() -> new AssertionError("Widget didn't find"))
                .getId();
    }

    public static String getPaymentGatewaySettingsId(String widgetId) {
        return getChat2PayQuery(format(Endpoints.PAYMENTS_GATEWAY_ENDPOINT, widgetId))
                .jsonPath()
                .getList("", PaymentGatewaySettingsResponse.class)
                .get(0)
                .getPaymentGatewaySettingsId();
    }

    public static String getApplicationId(String widgetId) {
        return getChat2PayQuery(format(Endpoints.WIDGET_INTEGRATION_ENDPOINT, widgetId))
                .jsonPath()
                .getList("", IntegrationResponse.class)
                .get(0)
                .getIntegrator().getApplicationUuid();
    }

    public static String getActivationKey(String widgetId) {
        return getChat2PayQuery(format(Endpoints.WIDGET_API_KEYS_ENDPOINT, widgetId))
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

    public static Response getC2PConfigurationResponse(String authToken) {
        return getQuery(CHAT_TO_PAY_CONFIGURATION_ENDPOINT, authToken);
    }

    public static void checkWorkingPaymentLink(String paymentLink) {
        getChat2PayQuery(paymentLink);
    }
}
