package com.touch.models;

import com.touch.utils.ConfigApp;

import java.util.Map;

public class EndPointsClass {

    public static final String APP_CONFIG_PROFILE = ConfigApp.API_VERSION + "/app-config/profile";


    // TOUCH END POINTS
    //Tenants endpoint
    public final static String TENANTS = ConfigApp.API_VERSION + "/tenants";
    public final static String TENANT = TENANTS + "/{tenantId}";
    public final static String TENANT_TBOT = TENANT + "/tbot";
    public final static String ADDRESSES = TENANT + "/addresses";
    public final static String ADDRESS = TENANT + "/address/{addressId}";
    public final static String BUSINESS_HOURS_FOR_ADDRESS = TENANT + "/addresses/{addressId}/business-hours";
    public final static String BUSINESS_HOURS_ID_FOR_ADDRESS = BUSINESS_HOURS_FOR_ADDRESS + "/{business-hours-id}";
    public final static String BUSINESS_HOURS_FOR_TENANT = TENANT + "/business-hours";
    public final static String BUSINESS_HOURS_ID_FOR_TENANT = BUSINESS_HOURS_FOR_TENANT + "/{business-hours-id}";
    public final static String TENANT_FAQS = TENANT + "/faqs";
    public final static String TENANT_FAQ = TENANT_FAQS + "/{faq-id}";
    public final static String TENANT_TAGS = TENANT + "/tags";
    public final static String TENANT_PROPERTIES = TENANT + "/properties";
    public final static String RESOURCES = TENANT + "/resources/{name}";
    public final static String COMMON_FLOWS = TENANTS + "/common/flows";
    public final static String COMMON_FLOW = COMMON_FLOWS + "/files/{flow-name}";
    public final static String DELETE_COMMON_FLOW = COMMON_FLOWS + "/{flow-name}";
    public final static String TENANT_FLOWS = TENANTS + "/{tenant-id}/flows";
    public final static String TENANT_FLOW = TENANT_FLOWS + "/files/{flow-name}";
    public final static String DELETE_TENANT_FLOW = TENANT_FLOWS + "/{flow-name}";
    public final static String TENANT_CONFIG = TENANT + "/config";
    //User profiles endpoint
    public static final String TOUCH_USER_PROFILES_OLD = "/v5/user-profiles";
    public static final String TOUCH_USER_PROFILES = ConfigApp.API_VERSION + "/user-profiles";
    public static final String TOUCH_USER_PROFILE = TOUCH_USER_PROFILES + "/{profileId}";
    public static final String TOUCH_USER_PROFILE_IMAGE = TOUCH_USER_PROFILE + "/image";
    //Agents endpoints
    public static final String AGENTS = ConfigApp.API_VERSION + "/agents";
    public static final String AGENTS_CREDENTIALS = AGENTS + "/credentials";
    public static final String AGENTS_MAX_CHATS = AGENTS + "/max-chats";
    public static final String AGENT = AGENTS + "/{agentId}";
    public static final String AGENT_IMAGE = AGENTS + "/{agentId}/image";
    //Departments endpoints
    public static final String DEPARTMENTS =ConfigApp.API_VERSION +"/departments";
    public static final String DEPARTMENTS_AGENTS = DEPARTMENTS + "/agents";
    public static final String DEPARTMENT = DEPARTMENTS + "/{id}";
    //Chats endpoints
    public static final String CHATS_ATTACHMENTS = ConfigApp.API_VERSION+"/chats/attachment";
    public static final String CHATS_ATTACHMENT =  CHATS_ATTACHMENTS+"/{attachment-id}";
    public static final String CHATS_ATTACHMENT_WITH_NAME =  CHATS_ATTACHMENT+"/{file-name}";
    public static final String CHATS_EVENTS = ConfigApp.API_VERSION+"/chats/events";
    public static final String CHATS_PRIVATE_HISTORY = ConfigApp.API_VERSION+"/chats/private-history";
    public static final String CHATS_HISTORIES = ConfigApp.API_VERSION+"/chats/history";
    public static final String CHATS_HISTORY = CHATS_HISTORIES+"/{sessionId}";
    public static final String CHATS_INVITES = ConfigApp.API_VERSION+"/chats/invites";
    public static final String CHATS_INVITE = CHATS_INVITES+"/{sessionId}";
    public static final String CHATS_INVITE_ARCHIVE = CHATS_INVITES+"/archive";
    public static final String CHATS_ROOMS = ConfigApp.API_VERSION+"/chats/rooms";
    public static final String CHATS_SESSIONS = ConfigApp.API_VERSION+"/chats/sessions";
    public static final String CHATS_SESSION = CHATS_SESSIONS +"/{sessionId}";
    public static final String CHATS_SESSION_TERMINATE = CHATS_SESSION+"/terminate";
    public static final String CHATS_SESSION_TERMINATE_ALL = CHATS_SESSIONS+"/{clientId}/terminate/all";
    //business-blp endpoints
    public static final String INTEGRATIONS = ConfigApp.API_VERSION+"/business-blp/integrations";
    public static final String INTEGRATION = INTEGRATIONS + "/{name}";
    public static final String INTEGRATION_ARTIFACT = INTEGRATION + "/artifact";
    public static final String INTEGRATION_CALL = INTEGRATION + "/{action}/call";
    //    Cards endpoints
    public static final String CARDS = ConfigApp.API_VERSION+"/cards";
    public static final String CARD = CARDS + "/{name}";
    public static final String CARD_BUNDLE = CARD + "/bundle";


    //Auth endpoints
    public static final String AUTH_TOCKEN = "/v5/auth/token";
    public static final String AUTH_AUTHENTICATED = "/v5/auth/authenticated";
    public static final String AUTH_ACCESS_TOKEN = ConfigApp.API_VERSION+"/auth/access-token";
    public static final String AUTH_REFRESH_TOKEN = ConfigApp.API_VERSION+"/auth/refresh-token";

    //Analytics endpoints
    public static final String ANALYTICS = ConfigApp.API_VERSION+"/analytics";
    public static final String ANALYTICS_REAL_TIME = ANALYTICS+"/real-time";
    public static final String CHATSSTATS_CONVERSATION_COUNT = ConfigApp.API_VERSION+"/chats/stats/conversation-count";
    public static final String CHATSSTATS_CONVERSATION_TIME = ConfigApp.API_VERSION+ "/chats/stats/conversation-time";

    //App_Config endpoints
    public static final String APP_PROFILE = ConfigApp.API_VERSION+"/app-config/profile";
    public static final String APP_XMPP = ConfigApp.API_VERSION+"/app-config/xmpp";

    //Roster endpoints
    public static final String ROSTER = ConfigApp.API_VERSION+"/roster";
    public static final String ROSTER_AGENTS = ROSTER+"/agents";
    public static final String ROSTERS ="/v5/rosters";

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
