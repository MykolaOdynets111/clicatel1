package mc2api.endpoints;

import driverfactory.MC2URLs;

public class EndpointsPlatform {

    // ==================  PLATFORM ============== //

    public static  String PLATFORM_ACCOUNTS = MC2URLs.getBasePlatformUrl() + "/auth/accounts";

    public static  String PLATFORM_SIGN_IN = MC2URLs.getBasePlatformUrl() + "/auth/accounts/sign-in";

    // ====================  PORTAL ================= //

    public static String PORTAL_LOGIN_PAGE = MC2URLs.getBasePortalUrl() + "#/login";

}

