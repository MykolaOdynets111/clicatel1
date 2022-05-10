package driverfactory;

import drivermanager.ConfigManager;

public class MC2URLs {


    private static String BASE_PORTAL_URL = "https://%s-portal.clickatelllabs.com/";

    // ================== API BASE URLs ========================= //

    private static String BASE_PLATFORM_URL = "https://%s-platform.clickatelllabs.com";


    public static String getBasePlatformUrl(){
        return String.format(BASE_PLATFORM_URL, ConfigManager.getEnv());

    }

    public static String getBasePortalUrl(){
        return String.format(BASE_PORTAL_URL, ConfigManager.getEnv());
    }


}
