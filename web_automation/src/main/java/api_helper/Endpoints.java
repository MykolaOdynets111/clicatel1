package api_helper;

public class Endpoints {

    public static String BASE_TOUCH_ENDPOINT = "https://%s-touch.clickatelllabs.com/v6/";

    public static String BASE_INTERNAL_ENDPOINT = "https://%s-touch.clickatelllabs.com/internal/";

    public static String ACCESS_TOKEN_ENDPOINT = "auth/access-token";

    public static String GET_ALL_TENANTS_ENDPOINT = "tenants?state=ACTIVE";

    public static String CREATE_USER_PROFILE_ENDPOINT = "client-profiles/%s/%s?key=%s&value=%s";

    public static String DELETE_USER_PROFILE_ENDPOINT = "client-profiles/%s/%s";

    public static String TENANT_CONFIGURED_MESSAGES = "taf/message-responses/%s";

    public static String WIDGET_VISIBILITY_HOURS = "tenants/%s/hours/webchat";

    public static String WIDGET_VISIBILITY_TERRITORIES = "territories/availability";

    public static String FACEBOOK_INTEGRATION = "facebook-registration/integration";


    // ================== INTERNAL ============== //

    public static String INTERNAL_TENANT_ADDRESS = "tenants/%s/addresses";

    public static String INTERNAL_LAST_CLIENT_SESSION = "sessions/tenant/%s/client/%s/last";


     // ==================  PORTAL ============== //

    public static String BASE_PLATFORM_ENDPOINT = "https://%s-platform.clickatelllabs.com";

    public static  String PLATFORM_ACCOUNTS = "/auth/accounts";

    public static  String PLATFORM_SIGN_IN = "/auth/accounts/sign-in";

}
