package api.clients;

public class Endpoints {

    public static final String C2P_URL_DEMO = "https://demo-chat2pay-service.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com";

    // Widgets
    public static final String WIDGETS_ENDPOINT = C2P_URL_DEMO + "/v2/widget";
    public static final String EXISTED_WIDGETS_ENDPOINT = WIDGETS_ENDPOINT + "/all?detailed=false&page=0&size=50";
    public static final String WIDGET_INTEGRATION_ENDPOINT = WIDGETS_ENDPOINT + "/%s/integration";
    public static final String WIDGET_SHOWED_LINKED_API_ENDPOINT = WIDGETS_ENDPOINT + "/%s/show-linked-api";


    // Account Settings
    public static final String ACCOUNT_SETTINGS = C2P_URL_DEMO + "/v2/account/settings";
    public static final String ACCOUNT_SETTINGS_SHOW_TUTORIAL = C2P_URL_DEMO + "/v2/account/settings/show-tutorial";


    // API Key Management
    public static final String WIDGET_API_KEYS_ENDPOINT = WIDGETS_ENDPOINT + "/%s/api-keys";
    public static final String DELETE_API_KEY = WIDGET_API_KEYS_ENDPOINT + "/%s";


    // Channel Management
    public static final String CHANNEL_CONFIGURATION = C2P_URL_DEMO + "/v2/widget/%s/link-channels";
    public static final String CHANNEL_STATUS = C2P_URL_DEMO + "/v2/widget/%s/status";


    // Configurations
    public static final String CHAT_TO_PAY_CONFIGURATION_ENDPOINT = C2P_URL_DEMO + "/api/v2/config";


    // Message Configurations
    public static final String MESSAGE_CONFIGURATIONS = WIDGETS_ENDPOINT + "/%s/message-configurations";
    public static final String TEMPLATE_USAGE = WIDGETS_ENDPOINT + "/template-usage?templateId=%s";


    // Payments
    public static final String PAYMENTS_GATEWAY_ENDPOINT = WIDGETS_ENDPOINT + "/%s/payment-gateway-settings";
    public static final String CHAT_TO_PAY_ENDPOINT = C2P_URL_DEMO + "/api/v2/chat-2-pay";
    public static final String CANCEL_PAYMENT_LINK_ENDPOINT = C2P_URL_DEMO + "/api/v2/cancel/";
    public static final String PAYMENT_RECEIPT = C2P_URL_DEMO + "/api/v2/order-receipt";


    // Two Way Numbers
    public static final String TWO_WAY_NUMBER = C2P_URL_DEMO + "/v2/widget/%s/two-way-numbers";

    //Merchant's Billing Info
    public static final String MERCHANTS_BILLING_INFO = WIDGETS_ENDPOINT + "/%s/merchant-billing-info";

}
