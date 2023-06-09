package driverfactory;

import apihelper.ApiHelper;
import apihelper.Endpoints;
import drivermanager.ConfigManager;
import io.restassured.path.json.exception.JsonPathException;
import org.testng.Assert;
import socialaccounts.FacebookPages;
import socialaccounts.TwitterPages;

import static utils.PropertiesReader.getProperty;

public class URLs {

    private static final String BASE_FB_URL = "https://www.facebook.com/";

    private static final String BASE_ENV_URL = "https://%s-touch-web.clickatelllabs.com/?tenantId=%s";

    private static final String BASE_PROD_URL = "https://touch-web.clickatell.com/?tenantId=%s";

    private static String BASE_AGENT_URL = "https://%s-agentdesk.clickatelllabs.com/#/login?tenantId=";

    private static ThreadLocal<String> CHATDESK_URL = new ThreadLocal<>();

    private static String BASE_SOCIAL_URL = "https://%s-touch-social.clickatelllabs.com/";

    private static String ORCA_MESSAGE = "https://%s-chatdesk-channels-app-bravo.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com/orca/message";

    // ================== API BASE URLs ========================= //

    private static String BASE_TIE_URL = "https://%s-tie.clickatelllabs.com/tenants/";

    private static String BASE_TOUCH_API_URL = "https://%s-chatdesk-platform-app-bravo.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com/";

    private static String BASE_TOUCH_API_INTEGRATION_URL = "https://%s-touch.clickatelllabs.com/";

    private static String BASE_INTERNAL_API_URL = "https://%s-touch.clickatelllabs.com/internal/";

    private static String BASE_TAF_URL = "http://%s-taf.clickatelllabs.com/";

    private static String BASE_WS_INTERNAL_URL = "https://%s-chatdesk-platform-app-bravo.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com/internal/";

    private static String BASE_WS_URL = "https://%s-chatdesk-platform-app-bravo.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com/api/";

    private static String TOUCH_LOGIN_FORM = "https://%s-chatdesk-platform-app-%s.int-eks-%s.shared-%s.eu-west-1.aws.clickatell.com/internal/static/auth-tool/";

    private static String TOUCH_MAIN_URL = ".TouchMainURL";



    public static String getWidgetURL(String tenantOrgName){
        String tenantID = "";
        try {
            tenantID = ApiHelper.getTenantInfoMap(tenantOrgName).get("id");
        }catch (JsonPathException e){
            Assert.fail("Getting tenant info was not successful\n" +
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
        if (CHATDESK_URL.get() == null || updateURL) {
            String baseUrl;
            String targetEnvConfiguration = ConfigManager.getEnv();
            String env;
            if(targetEnvConfiguration.split("-").length==2) env=targetEnvConfiguration.split("-")[1];
            else env = targetEnvConfiguration;
            baseUrl =String.format(URLs.BASE_AGENT_URL, targetEnvConfiguration);
            CHATDESK_URL.set(baseUrl + ApiHelper.getTenantInfoMap(tenantOrgName).get("id"));
        }
        System.out.println("!!! URL to be returned getting it: " + CHATDESK_URL.get());
        return CHATDESK_URL.get();
    }

    public static String getTouchApiBaseURL(){
        return String.format(BASE_TOUCH_API_URL, ConfigManager.getEnv());
    }

    public static String getTouchApiBaseIntegrationURL(){
        return String.format(BASE_TOUCH_API_INTEGRATION_URL, ConfigManager.getEnv());
    }

    public static String getBaseInternalApiUrl(){
        return String.format(BASE_INTERNAL_API_URL, ConfigManager.getEnv());
    }

    public static String getBaseWSInternalURL(){
        return String.format(BASE_WS_INTERNAL_URL, ConfigManager.getEnv());
    }

    public static String getBaseWSApiURL(){
        return String.format(BASE_WS_URL, ConfigManager.getEnv());
    }

    public static String getTouchLoginForm(){
        String env = getProperty("environment");
        return String.format(TOUCH_LOGIN_FORM, ConfigManager.getEnv(), getProperty(env + ".environment.type"), getProperty(env + ".environment.prefix"), getProperty(env + ".environment.prefix"));
    }

    public static String getAgentDeskURL(){
        return String.format(getProperty(getProperty("environment") + TOUCH_MAIN_URL), ConfigManager.getEnv());
    }

    public static String getDashboardURL(){
        return String.format(getProperty(getProperty("environment") + TOUCH_MAIN_URL), ConfigManager.getEnv())+"dashboard";
    }

    public static String getSupervisorURL(){
        return String.format(getProperty(getProperty("environment") + TOUCH_MAIN_URL), ConfigManager.getEnv())+"supervisor";
    }

    public static String getBasePlatformUrl(){
        return MC2URLs.getBasePlatformUrl();
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
//        String token = RequestScpec.getAccessTokenForPortalUser(tenantOrgName);
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
        return MC2URLs.getBasePortalUrl();
    }

    public static String getBaseSocialUrl(){
        return String.format(BASE_SOCIAL_URL, ConfigManager.getEnv());
    }

    public static String getBaseTafUrl(){
        return String.format(BASE_TAF_URL, ConfigManager.getEnv());
    }

    public static  void clearFinalAgentURL(){
        CHATDESK_URL.remove();
    }

    public static String getORCAMessageURL(){
        return String.format(ORCA_MESSAGE, ConfigManager.getEnv());
    }

    public static String getUrlByNameOfPage(String name){
        switch (name) {
            case "Dashboard":
                return getDashboardURL();
            case "Supervisor Desk":
                return getSupervisorURL();
            case "Agent Desk":
                return getAgentDeskURL();
        }
        return "Incorrect page name was provided";
    }
}
