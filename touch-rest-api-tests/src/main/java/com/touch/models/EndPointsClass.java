package com.touch.models;

import com.touch.utils.ConfigApp;

import java.util.Map;

public class EndPointsClass {

    public static final String APP_CONFIG = ConfigApp.API_VERSION+"/app-config";
    public static final String APP_CONFIG_PROFILE = ConfigApp.API_VERSION+"/app-config/profile";


// TOUCH END POINTS
    //Tenants endpoint
    public final static String TENANTS =  ConfigApp.API_VERSION+"/tenants";
    public final static String TENANT =  TENANTS+"/{tenantId}";
    public final static String ADDRESS =  TENANT+"/address/{addressId}";
    public final static String BUSINESS_HOURS_FOR_ADDRESS =  TENANT+"/addresses/{addressId}/business-hours";
    public final static String BUSINESS_HOURS_ID_FOR_ADDRESS =  BUSINESS_HOURS_FOR_ADDRESS+"/{business-hours-id}";
    public final static String BUSINESS_HOURS_FOR_TENANT = TENANT+"/business-hours";
    public final static String BUSINESS_HOURS_ID_FOR_TENANT = BUSINESS_HOURS_FOR_TENANT+"/{business-hours-id}";
    public final static String TENANT_FAQS = TENANT+"/faqs";
    public final static String TENANT_FAQ = TENANT_FAQS+"/{faq-id}";
    public final static String TENANT_TAGS = TENANT+"/tags";
    public final static String TENANT_PROPERTIES =  TENANT+"/properties";
    public final static String RESOURCES =  TENANT+"/resources/{name}";
    public final static String COMMON_FLOWS =  TENANTS+"/common/flows";
    public final static String COMMON_FLOW =  COMMON_FLOWS+"/files/{flow-name}";
    public final static String DELETE_COMMON_FLOW =COMMON_FLOWS+"/{flow-name}";
    public final static String TENANT_FLOWS =  TENANTS+"/{tenant-id}/flows";
    public final static String TENANT_FLOW =  TENANT_FLOWS+"/files/{flow-name}";
    public final static String DELETE_TENANT_FLOW =  TENANT_FLOWS+"/{flow-name}";
    //User profiles endpoint
    public static final String TOUCH_USER_PROFILES = ConfigApp.API_VERSION+"/user-profiles";
    public static final String TOUCH_USER_PROFILE = TOUCH_USER_PROFILES+"/{profileId}";
    public static final String TOUCH_USER_PROFILE_IMAGE = TOUCH_USER_PROFILE+"/image";
    //Agents endpoints
    public static final String AGENTS = "/v6/agents";
    public static final String AGENTS_CREDENTIALS = AGENTS+"/credentials";
    public static final String AGENTS_MAX_CHATS = AGENTS+"/max-chats";
    public static final String AGENT = AGENTS+"/{agentId}";
    public static final String AGENT_IMAGE = AGENTS+"/{agentId}/image";
    //Departments endpoints
    public static final String DEPARTMENTS = ConfigApp.API_VERSION+"/departments";;
    public static final String DEPARTMENTS_AGENTS = DEPARTMENTS+"/agents";
    public static final String DEPARTMENT = DEPARTMENTS+"/{id}";
    //Chats endpoints
    public static final String CHATS_ATTACHMENTS = ConfigApp.API_VERSION+"/chats/attachments";;
    public static final String CHATS_ATTACHMENT = CHATS_ATTACHMENTS+"/{attachment-id}";
    public static final String CHATS_ROOMS = ConfigApp.API_VERSION+"/chats/rooms";
    public static final String CHATS_SESSIONS = ConfigApp.API_VERSION+"/chats/sessions";
    public static final String CHATS_SESSION = CHATS_SESSIONS+"/{sessionId}";
    public static final String CHATS_SESSION_TERMINATE = CHATS_SESSION+"/terminate";
    //business-blp endpoints
    public static final String INTEGRATIONS = "/v6/business-blp/integrations";
    public static final String INTEGRATION = INTEGRATIONS+"/{name}";
    public static final String INTEGRATION_ARTIFACT = INTEGRATION+"/artifact";
    public static final String INTEGRATION_CALL = INTEGRATION+"/{action}/call";

    //Auth endpoints
    public static final String AUTH_TOCKEN = "/v5/auth/token";


    public static String generateQueryPath(Map<String, String> map) {
        String path = "?";
        int amountOfVar = 0;
        for (String key : map.keySet()) {
            if (!(map.get(key) == null)) {
                if (amountOfVar == 0)
                    path += key + "=" + map.get(key);
                if (amountOfVar > 0)
                    path += "&" + key + "=" + map.get(key);
                amountOfVar++;
            }
        }
        if (amountOfVar == 0)
            return "";
        return path;

    }

}
