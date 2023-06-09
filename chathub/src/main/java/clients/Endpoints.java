package clients;

import driverfactory.UnityURLs;

public class Endpoints extends UnityURLs {
    public static String baseUrl = "https://demo-chathub-config-manager.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com";
    public static final String ADMIN_PROVIDERS = baseUrl + "/admin/providers";
    public static final String PROVIDERS_STATE = baseUrl + "/api/providers/%s/state";
    public static final String ACTIVATE_CONFIGURATION = baseUrl + "/api/configurations/activate";
    public static final String CONFIGURATIONS = baseUrl + "/api/configurations?providerId=%s" ;
    public static final String PROVIDERS = baseUrl + "/api/providers";
    public static final String ADMIN_PROVIDER_DETAILS = baseUrl + "/admin/providers/%s";
    public static final String ADMIN_CONFIGURED_PROVIDER_DETAILS = baseUrl + "/admin/providers/configured?mc2AccountId=%s";
    public static final String CONFIGURATION_SECRETS = baseUrl + "/api/configurations/%s/secrets";
    public static final String CONFIGURATION_STATE = baseUrl + "/api/configurations/%s/state";
    public static final String DISABLE_CONFIGURATION = baseUrl + "/api/configurations/%s/disable";
    public static final String ADMIN_UPDATE_PROVIDER = baseUrl + "/admin/providers/%s";
    public static final String DELETE_CONFIGURATION = baseUrl + "/api/configurations/%s";
    public static final String RE_ACTIVATE_CONFIGURATION = baseUrl + "/api/configurations/re-activate";
    public static final String ADMIN_ENDPOINTS = baseUrl + "/admin/endpoints?providerId=%s&versionId=%s";
    public static final String ADMIN_SPECIFICATIONS = baseUrl + "/admin/specifications?providerId=%s";
    public static final String ADMIN_INTERNAL_PRODUCTS = baseUrl+"/admin/internal-products";
    public static final String ADMIN_ENDPOINTS_ENDPOINT = baseUrl+"/admin/endpoints/%s";
    public static final String INTERNAL_ENDPOINTS = baseUrl + "/internal/endpoints?providerId=%s&versionId=%s";
    public static final String INTERNAL_ENDPOINTS_ENDPOINT = baseUrl+"/admin/endpoints/%s";
    public static final String INTERNAL_PROVIDERS = baseUrl + "/internal/providers";
    public static final String INTERNAL_PROVIDERS_STATE = baseUrl + "/internal/providers/%s/state";
    public static final String INTERNAL_CONFIGURATIONS = baseUrl + "/internal/configurations?providerId=%s" ;
    public static final String ADMIN_GET_CONFIGURATIONS = baseUrl+"/admin/configurations?providerId=%s&version=%s&mc2AccountId=%s";
    public static final String ADMIN_GET_CONFIGURATIONS_CONFIGURATION_ID = baseUrl+"/admin/configurations/%s";
    public static final String ADMIN_GET_CONFIGURATIONS_SECRET = baseUrl+"/admin/configurations/%s/secrets";
    public static final String ADMIN_CONFIGURATION_ACTIVATE = baseUrl+"/admin/configurations/activate?mc2AccountId=%s";
}
