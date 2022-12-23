package api.clients;

import api.datamanager.Credentials;
import api.models.request.AuthBody;
import api.models.request.PaymentBody;
import api.models.response.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionsHelper extends Chat2PayApiHelper {

    public static String logInToUnity() {
        GetAuthToken getAuthToken = getAuthToken();
        AuthBody authBody = new AuthBody(getAuthToken.getToken(), getAuthToken.getAccounts().get(0).getId());
        return postQueryWithoutAuth(Endpoints.SIGN_IN_ENDPOINT, authBody, 200)
                .jsonPath()
                .getString("token");
    }

    public static String getWidgetId(String widgetName, String token) {
        List<Widget> widgets = getQuery(Endpoints.EXISTED_WIDGETS_ENDPOINT, token, 200)
                .as(Content.class)
                .getWidgets();
        return widgets.stream().filter(w -> w.getName().equals(widgetName)).findFirst()
                .orElseThrow(() -> new AssertionError("Widget didn't find"))
                .getId();
    }

    public static String getPaymentGatewaySettingsId(String token, String widgetId) {

        return getQuery(String.format(Endpoints.PAYMENTS_GATEWAY_ENDPOINT, widgetId), token, 200)
                .jsonPath()
                .getList("", PaymentGatewaySettingsResponse.class)
                .get(0)
                .getPaymentGatewaySettingsId();
    }

    public static String getApplicationId(String token, String widgetId) {
        return getQuery(String.format(Endpoints.WIDGET_INTEGRATION_ENDPOINT, widgetId), token, 200)
                .jsonPath()
                .getList("", IntegrationResponse.class)
                .get(0)
                .getIntegrator().getApplicationUuid();
    }

    public static String getActivationKey(String token, String widgetId) {
        return getQuery(String.format(Endpoints.WIDGET_API_KEYS_ENDPOINT, widgetId), token, 200)
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

    public static void checkWorkingPaymentLink(int statusCode, String token, String paymentLink) {
        getQuery(paymentLink, token, statusCode);
    }

    private static GetAuthToken getAuthToken() {
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", Credentials.DEV_CHAT_2_PAY_USER.getUsername());
        credentials.put("password", Credentials.DEV_CHAT_2_PAY_USER.getPassword());
        return postQueryWithoutAuth(Endpoints.AUTH_ACCOUNTS, credentials, 200)
                .as(GetAuthToken.class);
    }

}
