package driverManager;

import api_helper.Endpoints;
import api_helper.RequestSpec;
import dataprovider.Tenants;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class URLs {

    private static final String BASE_ENV_URL = "https://%s-touch-web.clickatelllabs.com";

    private static String BASE_PORTAL_URL = "https://%s-portal.clickatelllabs.com/#/login";

    private static String BASE_AGENT_URL = "https://%s-agentdesk.clickatelllabs.com/#/login?tenantId=";

    private static String FINAL_AGENT_URL = null;

    // ================== API BASE URLs ========================= //

    private static String BASE_TIE_URL = "http://%s-tie.clickatelllabs.com/tenants/";

    public static String BASE_TIE_PROD_URL = "http://tie.clickatelllabs.com/tenants/";

    private static String FACEBOOK_URL = "https://www.facebook.com/%s/";

    private static String BASE_TOUCH_API_URL = "https://%s-touch.clickatelllabs.com/v6/";

    private static String BASE_INTERNAL_API_URL = "https://%s-touch.clickatelllabs.com/internal/";

    public static String BASE_PLATFORM_URL = "https://%s-platform.clickatelllabs.com";

    public static String getURL(){
        String targetEnvConfiguration = ConfigManager.getEnv();
        String env;
        if(targetEnvConfiguration.split("-").length==2) env=targetEnvConfiguration.split("-")[1];
        else env = targetEnvConfiguration;
        return String.format(URLs.BASE_ENV_URL, targetEnvConfiguration);
    }

    /**
     * @param tenantOrgName
     * @param updateURL - boolean value to indicate if we need to log into different tenant Agent desk
     */
    public static String getAgentURL(String tenantOrgName, boolean updateURL) {
        if (FINAL_AGENT_URL == null || updateURL==true) {
            String baseUrl;
            String targetEnvConfiguration = ConfigManager.getEnv();
            String env;
            if(targetEnvConfiguration.split("-").length==2) env=targetEnvConfiguration.split("-")[1];
            else env = targetEnvConfiguration;
            baseUrl =String.format(URLs.BASE_AGENT_URL, targetEnvConfiguration);
//            if(tenantOrgName.equalsIgnoreCase("general bank demo") && ConfigManager.getEnv().equalsIgnoreCase("demo")){
//                tenantOrgName="standard bank";
//            }
            FINAL_AGENT_URL = baseUrl + Tenants.getTenantInfo(tenantOrgName, "id");
        }
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

    public static String getTieURL(String tenantOrgName, String message) {
        String tenantName = null;
        switch (tenantOrgName) {
            case "General Bank Demo":
                tenantName="generalbank";
                break;
            default:
                tenantName=tenantOrgName;
        }
//        return  String.format(Endpoints.BASE_TIE_PROD_URL+"%s/chats/?q=%s&sentiment=true", tenantName, message);
        return String.format(Endpoints.TIE_INTENT_PLUS_SENTIMENT_URL, tenantName, message);
    }

    public static String getTIEURLForAnswers(String tenantOrgName, String intent) {
        String tenantName = null;
        switch (tenantOrgName) {
            case "General Bank Demo":
                tenantName="generalbank";
                break;
        }
        return String.format(Endpoints.TIE_ANSWER_URL, tenantName, intent);
    }


    public static String getFBPageURL(String tenantOrgName) {
        String token = RequestSpec.getAccessTokenForPortalUser(tenantOrgName);
        Response resp = RestAssured.given()
                .header("Authorization", token)
                .get(Endpoints.FACEBOOK_INTEGRATION);
        String pageName = resp.jsonPath().get("pageName");
        String pageID = resp.jsonPath().get("pageId");
//        return String.format(FACEBOOK_URL, pageName.replace(" ", "-")+"-"+pageID);
        return "https://www.facebook.com/General-Bank-QA-135125267153030/";
    }

    public static String getTwitterURL(String tenantOrgName) {
        return TwitterURLs.getURLByTenantAndURL(tenantOrgName, ConfigManager.getEnv());
    }

    public static String getPortalURL(){
        return String.format(BASE_PORTAL_URL, ConfigManager.getEnv());
    }
}
