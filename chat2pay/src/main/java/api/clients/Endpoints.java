package api.clients;


public class Endpoints {
    public static  String unityURl = "https://dev-platform.clickatelllabs.com";
    public static  String c2pUrl = "https://dev-chat2pay-service.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com";
    public static final String AUTH_ACCOUNTS = unityURl + "/auth/accounts";
    public static final String SIGN_IN_ENDPOINT = unityURl + "/auth/accounts/sign-in";
    public static final String WIDGETS_ENDPOINT = c2pUrl + "/v2/widget/";
    public static final String CHAT_TO_PAY_ENDPOINT = c2pUrl + "/api/v2/chat-2-pay";
    public static final String EXISTED_WIDGETS_ENDPOINT = WIDGETS_ENDPOINT + "all?detailed=false&page=0&size=20";
    public static final String PAYMENTS_GATEWAY_ENDPOINT = WIDGETS_ENDPOINT + "%s/payment-gateway-settings";
    public static final String WIDGET_INTEGRATION_ENDPOINT = WIDGETS_ENDPOINT + "%s/integration";
    public static final String WIDGET_API_KEYS_ENDPOINT = WIDGETS_ENDPOINT +"%s/api-keys";




}
