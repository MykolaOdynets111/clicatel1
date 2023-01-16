package clients;

import driverfactory.UnityURLs;

public class Endpoints extends UnityURLs {

    public static String baseUrl = "https://demo-chathub-config-manager.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com";

    public static final String ADMIN_PROVIDERS = baseUrl + "/admin/providers";
    public static final String PROVIDERS_STATE = baseUrl + "/api/providers/%s/state";
    public static final String ACTIVATE_CONFIGURATION = baseUrl + "/api/configurations/activate";

    public static final String CONFIGURATION_SECRETS = baseUrl + "/api/providers/%s/secrets";
}
