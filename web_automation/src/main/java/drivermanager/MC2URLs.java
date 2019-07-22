package drivermanager;

import apihelper.ApiHelper;
import apihelper.Endpoints;
import datamanager.FacebookPages;
import datamanager.TwitterPages;
import io.restassured.path.json.exception.JsonPathException;
import org.testng.Assert;

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
