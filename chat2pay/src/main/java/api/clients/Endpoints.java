package api.clients;

public class Endpoints {

    public static final String C2P_URL_DEMO = "https://demo-chat2pay-service.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com";
    public static final String C2P_URL = "https://dev-chat2pay-service.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com";
    public static final String WIDGETS_ENDPOINT = C2P_URL + "/v2/widget";
    public static final String CHAT_TO_PAY_ENDPOINT = C2P_URL + "/api/v2/chat-2-pay";
    public static final String CANCEL_PAYMENT_LINK_ENDPOINT = C2P_URL + "/api/v2/cancel/";
    public static final String CHAT_TO_PAY_CONFIGURATION_ENDPOINT = C2P_URL_DEMO + "/api/v2/config";
    public static final String EXISTED_WIDGETS_ENDPOINT = WIDGETS_ENDPOINT + "/all?detailed=false&page=0&size=20";
    public static final String PAYMENTS_GATEWAY_ENDPOINT = WIDGETS_ENDPOINT + "/%s/payment-gateway-settings";
    public static final String WIDGET_INTEGRATION_ENDPOINT = WIDGETS_ENDPOINT + "/%s/integration";
    public static final String WIDGET_API_KEYS_ENDPOINT = WIDGETS_ENDPOINT + "/%s/api-keys";
    public static final String WIDGET_SHOWED_LINKED_API_ENDPOINT =WIDGETS_ENDPOINT + "/%s/show-linked-api";
    public static final String PAYMENT_RECEIPT = C2P_URL + "/api/v2/order-receipt";
    public static final String ACCOUNT_SETTINGS = C2P_URL + "/v2/account/settings";
    public static final String ACCOUNT_SETTINGS_SHOW_TUTORIAL = C2P_URL + "/v2/account/settings/show-tutorial";
    public static final String CHANNEL_MANAGEMENT_LINK_CHANNEL = C2P_URL + "/v2/widget/%s/link-channels";
}
