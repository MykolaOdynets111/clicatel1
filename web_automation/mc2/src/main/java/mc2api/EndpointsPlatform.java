package mc2api;

import driverfactory.MC2URLs;

public class EndpointsPlatform {

    // ==================  PLATFORM ============== //

    public static  String PLATFORM_ACCOUNTS = MC2URLs.getBasePlatformUrl() + "/auth/accounts";

    public static  String PLATFORM_SIGN_IN = MC2URLs.getBasePlatformUrl() + "/auth/accounts/sign-in";

    public static String PLATFORM_SEND_INVITATION = MC2URLs.getBasePlatformUrl() + "/invitations";

    public static String PLATFORM_ACCEPT_INVITATION = MC2URLs.getBasePlatformUrl() + "/invitations/%s/accepted";

    public static String PLATFORM_USER_ROLES = MC2URLs.getBasePlatformUrl() + "/roles?details=false";

    public static String PLATFORM_USER = MC2URLs.getBasePlatformUrl() + "/users";

    public static String PLATFORM_SUBSCRIPTIONS_LIST = MC2URLs.getBasePlatformUrl() + "/subscriptions?pageNumber=1&pageSize=50&ascending=false";

    public static String PLATFORM_PAYMENT_METHODS = MC2URLs.getBasePlatformUrl() + "/payment-methods";

    public static String PLATFORM_CLOSE_ACCOUNT = MC2URLs.getBasePlatformUrl() + "/accounts/close";

    public static String PLATFORM_BILLING_INFO = MC2URLs.getBasePlatformUrl() + "/accounts/billing-info";

    public static String PLATFORM_ROLES_PERMITIONS = MC2URLs.getBasePlatformUrl() + "/roles/%s/permissions?details=false";

    public static String PLATFORM_ACCOUNT_BALANCE = MC2URLs.getBasePlatformUrl() + "/accounts/balance";

    public static String PLATFORM_ACCOUNT_SIGN_UP = MC2URLs.getBasePlatformUrl() + "/auth/sign-up";

    public static String PLATFORM_ACCOUNT_ACTIVATION = MC2URLs.getBasePlatformUrl() + "/auth/accounts/%s/activated";

    public static String PLATFORM_SANDBOX_NUMBERS = MC2URLs.getBasePlatformUrl() + "/sandbox-numbers";


    // ====================  PORTAL ================= //

    public static String PORTAL_LOGIN_PAGE = MC2URLs.getBasePortalUrl() + "#/login";

    public static String PORTAL_SIGN_UP_PAGE = MC2URLs.getBasePortalUrl() + "#/signup";

    public static String PORTAL_ACCOUNT_ACTIVATION = MC2URLs.getBasePortalUrl() + "#/activation/%s ";

    public static String PORTAL_NEW_AGENT_ACTIVATION = MC2URLs.getBasePortalUrl() + "/#/invitation/";

    public static String PORTAL_RESET_PASS_URL = MC2URLs.getBasePortalUrl() + "/#/forgotPassword/";
}

