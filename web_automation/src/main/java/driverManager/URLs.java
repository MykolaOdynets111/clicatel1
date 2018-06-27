package driverManager;

import api_helper.Endpoints;
import api_helper.RequestSpec;
import dataprovider.Tenants;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class URLs {

    private static final String BASE_ENV_URL = "https://%s-touch-web.clickatelllabs.com";

    private static String BASE_PORTAL_URL = "https://%s-portal.clickatelllabs.com/#/login";

    private static String FINAL_AGENT_URL = null;

    private static final String BASE_AGENT_URL = "https://%s-agentdesk.clickatelllabs.com/#/login?tenantId=";

    private static String FACEBOOK_URL = "https://www.facebook.com/%s/";

    public static String getURL(){
        String env = ConfigManager.getEnv();
        switch (env) {
            case "qa":
                return String.format(URLs.BASE_ENV_URL, "qa");
            case "dev":
                return String.format(URLs.BASE_ENV_URL, "dev");
            case "testing":
                return String.format(URLs.BASE_ENV_URL, "testing");
            case "demo":
                return String.format(URLs.BASE_ENV_URL, "demo");
            case "beta":
                return String.format(URLs.BASE_ENV_URL, "beta");
            case "integration":
                return String.format(URLs.BASE_ENV_URL, "integration");
            case "demo1":
                return String.format(URLs.BASE_ENV_URL, "demo1");
            default:
                return String.format(URLs.BASE_ENV_URL, "testing");
        }
    }

    /**
     * @param tenantOrgName
     * @param updateURL - boolean value to indicate if we need to log into different tenant Agent desk
     */
    public static String getAgentURL(String tenantOrgName, boolean updateURL) {
        if (FINAL_AGENT_URL == null || updateURL==true) {
            String baseUrl;
            String env = ConfigManager.getEnv();

            switch (env) {
                case "qa":
//                    baseUrl = String.format(URLs.BASE_CHATDESK_AGENT_URL, "qa");
                    baseUrl =String.format(URLs.BASE_AGENT_URL, "qa");
                    break;
                case "dev":
                    baseUrl =String.format(URLs.BASE_AGENT_URL, "dev");
//                    baseUrl = String.format(URLs.BASE_CHATDESK_AGENT_URL, "dev");
                    break;
                case "testing":
                    baseUrl =String.format(URLs.BASE_AGENT_URL, "testing");
                    break;
                case "demo":
                    baseUrl =String.format(URLs.BASE_AGENT_URL, "demo");
                    break;
                case "beta":
                    baseUrl =String.format(URLs.BASE_AGENT_URL, "beta");
                    break;
                case "integration":
                    baseUrl =String.format(URLs.BASE_AGENT_URL, "integration");
                    break;
                case "demo1":
                    baseUrl =String.format(URLs.BASE_AGENT_URL, "demo1");
                    break;
                default:
                    baseUrl = String.format(URLs.BASE_AGENT_URL, "testing");
                    break;
            }
//            if(tenantOrgName.equalsIgnoreCase("general bank demo") && ConfigManager.getEnv().equalsIgnoreCase("demo")){
//                tenantOrgName="standard bank";
//            }
            FINAL_AGENT_URL = baseUrl + getTenantID(tenantOrgName);
        }

        return FINAL_AGENT_URL;
    }

    private static String getTenantID(String tenantOrgName){
        return Tenants.getTenantInfo(tenantOrgName, "id");
    }

    public static String getTieURL(String tenantOrgName, String message) {
        String env = ConfigManager.getEnv();
        String tenantName = null;
        switch (tenantOrgName) {
            case "General Bank Demo":
                tenantName="generalbank";
                break;
            default:
                tenantName=tenantOrgName;
        }
//        return  String.format(Endpoints.BASE_TIE_PROD_URL+"%s/chats/?q=%s&sentiment=true", tenantName, message);
        return String.format(Endpoints.TIE_INTENT_PLUS_SENTIMENT_URL, env, tenantName, message);
    }

    public static String getBaseTieChatURL(String tenant) {
        String env = ConfigManager.getEnv();
        String baseUrl = Endpoints.BASE_TIE_CHAT_URL;
        return String.format(baseUrl, env, tenant);
    }

    public static String getTIEURLForAnswers(String tenantOrgName, String intent) {
        String env = ConfigManager.getEnv();
        String tenantName = null;
        switch (tenantOrgName) {
            case "General Bank Demo":
                tenantName="generalbank";
                break;
        }
        return String.format(Endpoints.TIE_ANSWER_URL, env, tenantName, intent);
    }


    public static String getFBPageURL(String tenantOrgName) {
        String token = RequestSpec.getAccessTokenForPortalUser(tenantOrgName);
        Response resp = RestAssured.given()
                .header("Authorization", token)
                .get(String.format(Endpoints.BASE_TOUCH_ENDPOINT, ConfigManager.getEnv())+Endpoints.FACEBOOK_INTEGRATION);
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
