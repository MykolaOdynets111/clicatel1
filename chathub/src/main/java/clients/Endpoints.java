package clients;

public class Endpoints {

    public static String baseUrl = "https://demo-chathub-config-manager.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com";
    public static String unityURl = "https://dev-platform.clickatelllabs.com";

    public static final String AUTH_ACCOUNTS = unityURl + "/auth/accounts";
    public static final String JWT_ACCOUNT = unityURl + "/auth/accounts/sign-in";
    public static final String ADMIN_PROVIDERS = baseUrl + "/admin/providers";
    public static final String PROVIDERS_STATE = baseUrl + "/api/providers/%s/state";

}
