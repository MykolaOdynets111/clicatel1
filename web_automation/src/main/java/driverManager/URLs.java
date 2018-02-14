package driverManager;

import api_helper.ApiHelper;
import dataprovider.TenantIDs;

public class URLs {

    private static final String BASE_ENV_URL = "https://%s-touch-web.clickatelllabs.com";

    private static String FINAL_AGENT_URL = null;

    private static final String BASE_AGENT_URL = "https://%s-agentdesk.clickatelllabs.com/#/login?tenantId=";

    private static final String DEV_ENV_BASE_AGENT_URL = "https://dev-agentdesk.clickatelllabs.com/#/login?tenantId=";

    public static String getURL(){
        String env = ConfigManager.getEnv();
        switch (env) {
            case "qa":
                return String.format(URLs.BASE_ENV_URL, "qa");
            case "dev":
                return String.format(URLs.BASE_ENV_URL, "dev");
            case "testing":
                return String.format(URLs.BASE_ENV_URL, "testing");
            default:
                return String.format(URLs.BASE_ENV_URL, "testing");
        }
    }

    /**
     * @param tenantName
     * @param updateURL - boolean value to indicate if we need to log into different tenant Agent desk
     */
    public static String getAgentURL(String tenantName, boolean updateURL) {
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
                default:
                    baseUrl = String.format(URLs.BASE_AGENT_URL, "testing");
                    break;
            }
            FINAL_AGENT_URL = baseUrl + getTenantID(tenantName);
        }

        return FINAL_AGENT_URL;
    }

    private static String getTenantID(String tenantName){
        return TenantIDs.getTenentIdFor(tenantName);
    }

}