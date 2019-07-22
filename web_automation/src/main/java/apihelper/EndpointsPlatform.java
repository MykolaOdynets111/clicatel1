package apihelper;

import drivermanager.URLs;

public class EndpointsPlatform {

    // ==================  PLATFORM ============== //

    public static  String PLATFORM_ACCOUNTS = URLs.getBasePlatformUrl() + "/auth/accounts";

    public static  String PLATFORM_SIGN_IN = URLs.getBasePlatformUrl() + "/auth/accounts/sign-in";

    public static String PLATFORM_SEND_INVITATION = URLs.getBasePlatformUrl() + "/invitations";

    public static String PLATFORM_ACCEPT_INVITATION = URLs.getBasePlatformUrl() + "/invitations/%s/accepted";

    public static String PLATFORM_USER_ROLES = URLs.getBasePlatformUrl() + "/roles?details=false";

    public static String PLATFORM_USER = URLs.getBasePlatformUrl() + "/users";

    public static String PLATFORM_SUBSCRIPTIONS_LIST = URLs.getBasePlatformUrl() + "/subscriptions?pageNumber=1&pageSize=50&ascending=false";

    public static String PLATFORM_PAYMENT_METHODS = URLs.getBasePlatformUrl() + "/payment-methods";

    public static String PLATFORM_CLOSE_ACCOUNT = URLs.getBasePlatformUrl() + "/accounts/close";

    public static String PLATFORM_BILLING_INFO = URLs.getBasePlatformUrl() + "/accounts/billing-info";

    public static String PLATFORM_ROLES_PERMITIONS = URLs.getBasePlatformUrl() + "/roles/%s/permissions?details=false";

    public static String PLATFORM_ACCOUNT_BALANCE = URLs.getBasePlatformUrl() + "/accounts/balance";


    // ====================  PORTAL ================= //

    public static String PORTAL_LOGIN_PAGE = URLs.getBasePortalUrl() + "#/login";

    public static String PORTAL_SIGN_UP_PAGE = URLs.getBasePortalUrl() + "#/signup";

    public static String PORTAL_ACCOUNT_ACTIVATION = URLs.getBasePortalUrl() + "#/activation/%s ";

    public static String PORTAL_NEW_AGENT_ACTIVATION = URLs.getBasePortalUrl() + "/#/invitation/";

    public static String PORTAL_RESET_PASS_URL = URLs.getBasePortalUrl() + "/#/forgotPassword/";


}

