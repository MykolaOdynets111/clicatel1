package drivermanager;

import apihelper.ApiHelper;
import apihelper.Endpoints;
import datamanager.FacebookPages;
import datamanager.Tenants;
import datamanager.TwitterPages;
import io.restassured.path.json.exception.JsonPathException;
import org.testng.Assert;

public class URLs {

    private static final String BASE_FB_URL = "https://www.facebook.com/";

    private static final String BASE_ENV_URL = "https://%s-touch-web.clickatelllabs.com/?tenantId=%s";

    private static final String BASE_PROD_URL = "https://touch-web.clickatell.com/?tenantId=%s";

    private static String BASE_PORTAL_URL = "https://%s-portal.clickatelllabs.com/";

    private static String BASE_AGENT_URL = "https://%s-agentdesk.clickatelllabs.com/#/login?tenantId=";

    private static String FINAL_AGENT_URL = null;

    private static ThreadLocal<String> CHATDESK_URL = new ThreadLocal<>();

    private static String BASE_SOCIAL_URL = "https://%s-touch-social.clickatelllabs.com/";

    // ================== API BASE URLs ========================= //

    private static String BASE_TIE_URL = "https://%s-tie.clickatelllabs.com/tenants/";

    private static String BASE_TIE_PROD_URL = "http://tie.clickatelllabs.com/tenants/";

    private static String FACEBOOK_URL = "https://www.facebook.com/%s/";

    private static String BASE_TOUCH_API_URL = "https://%s-touch.clickatelllabs.com/v6/";

    private static String BASE_INTERNAL_API_URL = "https://%s-touch.clickatelllabs.com/internal/";

    private static String BASE_PLATFORM_URL = "https://%s-platform.clickatelllabs.com";

    private static String BASE_TAF_URL = "http://%s-taf.clickatelllabs.com/";

    public static String getWidgetURL(String tenantOrgName){
        String tenantID = "";
        try {
            tenantID = ApiHelper.getTenantInfoMap(tenantOrgName).get("id");
        }catch (JsonPathException e){
            Assert.assertTrue(false, "Getting tenant info was not successful\n" +
            "Response with tenant info: " + ApiHelper.getTenantInfoMap(tenantOrgName).toString());
        }
        String targetEnvConfiguration = ConfigManager.getEnv();
        String env;
        if(targetEnvConfiguration.split("-").length==2) env=targetEnvConfiguration.split("-")[1];
        else env = targetEnvConfiguration;
        return String.format(URLs.BASE_ENV_URL, targetEnvConfiguration, tenantID);
    }

    public static String getWidgetURLForDynamicTenant(){
        String tenantID = ConfigManager.getID();
        String targetEnvConfiguration = ConfigManager.getEnv();
        String env;
        if(targetEnvConfiguration.split("-").length==2) env=targetEnvConfiguration.split("-")[1];
        else env = targetEnvConfiguration;
        if (env.equalsIgnoreCase("prod")) return String.format(URLs.BASE_PROD_URL, tenantID);
        else return String.format(URLs.BASE_ENV_URL, targetEnvConfiguration, tenantID);
    }

    /**
     * @param tenantOrgName - target tenant org name
     * @param updateURL - boolean value to indicate if we need to log into different tenant Agent desk
     */
    public static String getAgentURL(String tenantOrgName, boolean updateURL) {
        if (FINAL_AGENT_URL == null || updateURL) {
            String baseUrl;
            String targetEnvConfiguration = ConfigManager.getEnv();
            String env;
            if(targetEnvConfiguration.split("-").length==2) env=targetEnvConfiguration.split("-")[1];
            else env = targetEnvConfiguration;
            baseUrl =String.format(URLs.BASE_AGENT_URL, targetEnvConfiguration);
            FINAL_AGENT_URL = baseUrl + ApiHelper.getTenantInfoMap(tenantOrgName).get("id");
        }
        System.out.println("!!! URL to be returned getting it: " + FINAL_AGENT_URL);
        return FINAL_AGENT_URL;
    }

    public static String getTouchApiBaseURL(){
        return String.format(BASE_TOUCH_API_URL, ConfigManager.getEnv());
    }

    public static String getBaseInternalApiUrl(){
        return String.format(BASE_INTERNAL_API_URL, ConfigManager.getEnv());
    }

    public static String getBasePlatformUrl(){
        return String.format(BASE_PLATFORM_URL, ConfigManager.getEnv());

    }

    public static String getBaseTieURL(){
        return String.format(BASE_TIE_URL, ConfigManager.getEnv());
    }

    public static String getTieURL(String tenantName, String message) {
        return String.format(Endpoints.TIE_INTENT_PLUS_SENTIMENT_URL, tenantName, message);
    }

    public static String getTIEURLForAnswers(String tenantName, String intent) {
        return String.format(Endpoints.TIE_ANSWER_URL, tenantName, intent);
    }


    public static String getFBPageURL(String tenantOrgName) {
//        String token = RequestSpec.getAccessTokenForPortalUser(tenantOrgName);
//        Response resp = RestAssured.given()
//                .header("Authorization", token)
//                .get(Endpoints.FACEBOOK_INTEGRATION);
//        String pageName = resp.jsonPath().get("pageName");
//        String pageID = resp.jsonPath().get("pageId");
//        return String.format(FACEBOOK_URL, pageName.replace(" ", "-")+"-"+pageID);
        return BASE_FB_URL+ FacebookPages.getFBPageFromCurrentEnvByTenantOrgName(tenantOrgName).getFBPageLink();
    }

    public static String getTwitterURL(String tenantOrgName) {
        return TwitterPages.getURLByTenantAndURL(tenantOrgName, ConfigManager.getEnv());
    }

    public static String getBasePortalUrl(){
        return String.format(BASE_PORTAL_URL, ConfigManager.getEnv());
    }

    public static String getBaseSocialUrl(){
        return String.format(BASE_SOCIAL_URL, ConfigManager.getEnv());
    }

    public static String getBaseTafUrl(){
        return String.format(BASE_TAF_URL, ConfigManager.getEnv());
    }

    public static  void clearFinalAgentURL(){
        FINAL_AGENT_URL=null;
    }

}
