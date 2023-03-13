package api.clients;

import static java.lang.String.format;

public class Endpoints {

    public static final String C2P_URL_DEMO = "https://demo-chat2pay-service.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com";
    public static final String API_VERSION = "v2";
    public static final String C2P_URL_WITH_VERSION = C2P_URL_DEMO + "/" + API_VERSION;

    // Widgets
    public static final String WIDGETS_ENDPOINT = C2P_URL_WITH_VERSION + "/widget";
    public static final String EXISTED_WIDGETS_ENDPOINT = WIDGETS_ENDPOINT + "/all?detailed=false&page=0&size=50";
    public static final String WIDGET_SHOWED_LINKED_API_ENDPOINT = WIDGETS_ENDPOINT + "/%s/show-linked-api";

    // Account Settings
    public static final String ACCOUNT_SETTINGS = C2P_URL_WITH_VERSION + "/account/settings";
    public static final String ACCOUNT_SETTINGS_SHOW_TUTORIAL = C2P_URL_WITH_VERSION + "/account/settings/show-tutorial";

    // API Key Management
    public static final String WIDGET_API_KEYS_ENDPOINT = WIDGETS_ENDPOINT + "/%s/api-keys";
    public static final String DELETE_API_KEY = WIDGET_API_KEYS_ENDPOINT + "/%s";

    // Channel Management
    public static final String CHANNEL_CONFIGURATION = WIDGETS_ENDPOINT + "/%s/link-channels";
    public static final String CHANNEL_STATUS = WIDGETS_ENDPOINT + "/%s/status";

    // Configurations
    public static final String CHAT_TO_PAY_CONFIGURATION_ENDPOINT = C2P_URL_DEMO + format("/api/%s/config", API_VERSION);

    // Message Configurations
    public static final String MESSAGE_CONFIGURATIONS = WIDGETS_ENDPOINT + "/%s/message-configurations";
    public static final String TEMPLATE_USAGE = WIDGETS_ENDPOINT + "/template-usage?templateId=%s";

    // Payment Configuration Support Endpoints
    public static final String BILLING_TYPE = WIDGETS_ENDPOINT + "/payment-config/billing-type";
    public static final String CARD_NETWORK = WIDGETS_ENDPOINT + "/payment-config/card-network";
    public static final String COUNTRY = WIDGETS_ENDPOINT + "/payment-config/country";
    public static final String CURRENCY = WIDGETS_ENDPOINT + "/payment-config/currency";
    public static final String CURRENCY_FOR_PAYMENT_INTEGRATION_TYPE = CURRENCY + "?paymentIntegrationTypeId=%s";
    public static final String INTEGRATION_TYPE = WIDGETS_ENDPOINT + "/payment-config/integration-type";
    public static final String LOCALE = WIDGETS_ENDPOINT + "/payment-config/locale";
    public static final String LOCALE_FOR_PAYMENT_GATEWAY = LOCALE + "?paymentGatewayId=%s";
    public static final String PAYMENT_TYPE = WIDGETS_ENDPOINT + "/payment-config/payment-type";
    public static final String STATE = WIDGETS_ENDPOINT + "/payment-config/state";
    public static final String TRANSACTION_TYPE = WIDGETS_ENDPOINT + "/payment-config/transaction-type";

    // Payments
    public static final String PAYMENTS_GATEWAY_ENDPOINT = WIDGETS_ENDPOINT + "/%s/payment-gateway-settings";
    public static final String CHAT_TO_PAY_ENDPOINT = C2P_URL_DEMO + format("/api/%s/chat-2-pay", API_VERSION);
    public static final String CANCEL_PAYMENT_LINK_ENDPOINT = C2P_URL_DEMO + format("/api/%s/cancel/", API_VERSION);
    public static final String PAYMENT_RECEIPT = C2P_URL_DEMO + format("/api/%s/order-receipt", API_VERSION);

    // Two Way Numbers
    public static final String TWO_WAY_NUMBER = WIDGETS_ENDPOINT + "/%s/two-way-numbers";

    //Merchant's Billing Info
    public static final String MERCHANTS_BILLING_INFO = WIDGETS_ENDPOINT + "/%s/merchant-billing-info";

    //Payment Gateway Settings Configuration
    public static final String GET_PAYMENTS_GATEWAY_LOGO = PAYMENTS_GATEWAY_ENDPOINT + "/%s/logo";
    public static final String POST_PAYMENTS_GATEWAY_LOGO = PAYMENTS_GATEWAY_ENDPOINT + "/logo";
    public static final String UNIFIED_PAYMENTS_SETTINGS = PAYMENTS_GATEWAY_ENDPOINT + "/2";

    //IntegrationManagement
    public static final String WIDGET_INTEGRATION_ENDPOINT = WIDGETS_ENDPOINT + "/%s/integration";
    public static final String CLICKATELL_PRODUCT_ENDPOINT = WIDGET_INTEGRATION_ENDPOINT + "/clickatell-product";

}
