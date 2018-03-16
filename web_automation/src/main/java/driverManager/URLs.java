package driverManager;

import dataprovider.Tenants;

public class URLs {

    private static final String BASE_ENV_URL = "https://%s-touch-web.clickatelllabs.com";

    private static String FINAL_AGENT_URL = null;

    private static final String BASE_AGENT_URL = "https://%s-agentdesk.clickatelllabs.com/#/login?tenantId=";

    private static String BASE_TIE_SENTIMENT_URL = "http://%s-tie.clickatelllabs.com/tenants/%s/chats/?q=%s&sentiment=true";

    private static String BASE_TIE_ANSWER_URL = "http://%s-tie.clickatelllabs.com/tenants/%s/answers/?intent=%s";

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
                    baseUrl = String.format(URLs.BASE_AGENT_URL, "qa");
                    break;
                case "dev":
                    baseUrl = String.format(URLs.BASE_AGENT_URL, "dev");
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
        }
        return String.format(BASE_TIE_SENTIMENT_URL, env, tenantName, message);
    }

    public static String getTIEURLForAnswers(String tenantOrgName, String intent) {
        String env = ConfigManager.getEnv();
        String tenantName = null;
        switch (tenantOrgName) {
            case "General Bank Demo":
                tenantName="generalbank";
                break;
        }
        return String.format(BASE_TIE_ANSWER_URL, env, tenantName, intent);
    }

}
