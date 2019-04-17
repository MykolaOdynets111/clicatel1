package apihelper;

import drivermanager.URLs;

public class Endpoints {

    // =================== TOUCH =============== //

    public static String ACCESS_TOKEN_ENDPOINT = URLs.getTouchApiBaseURL() + "auth/access-token";

    public static String GET_ALL_TENANTS_ENDPOINT = "tenants?state=ACTIVE";

    public static String WIDGET_VISIBILITY_HOURS = URLs.getTouchApiBaseURL() + "tenants/%s/hours/webchat";

    public static String AGENT_SUPPORT_HOURS = URLs.getTouchApiBaseURL() + "tenants/%s/hours/agent-support";

    public static String WIDGET_VISIBILITY_TERRITORIES = URLs.getTouchApiBaseURL() + "territories/availability";

    public static String FACEBOOK_INTEGRATION =  URLs.getTouchApiBaseURL() + "facebook-registration/integration";

    public static String AGENT_INFO_ME = URLs.getTouchApiBaseURL() + "agents/infoMe";

    public static String FEATURE =  URLs.getTouchApiBaseURL() + "features/tenant";

    public static String INTEGRATIONS_ENABLING_DISABLING = URLs.getTouchApiBaseURL() + "integrations/channel/enable";

    public static String INTEGRATION_EXISTING_CHANNELS = URLs.getTouchApiBaseURL() + "integrations/channels";

    public static String INTEGRATIONS = URLs.getTouchApiBaseURL() + "integrations";

    public static String CHAT_HISTORY = URLs.getTouchApiBaseURL() + "chats/history?sessionId=%s";

    public static String SESSION_CAPACITY = URLs.getTouchApiBaseURL() + "tenants/sessions-capacity/";

    public static String AGENT_OVERNIGHT_TICKETS = URLs.getTouchApiBaseURL() + "agent/tickets?state=";

    public static String PROCESS_OVERNIGHT_TICKET = URLs.getTouchApiBaseURL() + "tickets/process-by-id/%s?state=PROCESSED";

    public static String CUSTOMER_VIEW = URLs.getTouchApiBaseURL() + "customer-view/";

    public static String DELETE_AGENT_IMAGE = URLs.getTouchApiBaseURL() + "agents/%s/image";

    public static String TENANT_CONFIG = URLs.getTouchApiBaseURL() + "tenants/%s/config";

    public static String TENANT_INFO = URLs.getTouchApiBaseURL() + "tenant";

    public static String ACTIVE_CHATS_BY_AGENT = "https://%s-touch.clickatelllabs.com/chats/active-by-agent?page=0&size=10";

    public static String CLOSE_ACTIVE_CHAT = "https://%s-touch.clickatelllabs.com/chats/%s/end";


    public static String CRM_TICKET = URLs.getTouchApiBaseURL() + "client-profiles/%s/crm-tickets";

    public static String DELETE_CRM_TICKET = URLs.getTouchApiBaseURL() + "client-profiles/crm-tickets/";

    // ================== INTERNAL ============== //

    public static String INTERNAL_TENANT_ADDRESS = URLs.getBaseInternalApiUrl() + "tenants/%s/addresses";

    public static String INTERNAL_LAST_CLIENT_SESSION = URLs.getBaseInternalApiUrl() + "sessions/tenant/%s/client/%s/last";

    public static String INTERNAL_TENANT_CONFIG = URLs.getBaseInternalApiUrl() + "tenants/%s/config ";

    public static String INTERNAL_FEATURE_STATE = URLs.getBaseInternalApiUrl() + "features/tenants/%s/%s/%s";

    public static String INTERNAL_COUNT_OF_LOGGED_IN_AGENTS = URLs.getBaseInternalApiUrl() + "loggedin-agents-count/%s";

    public static String INTERNAL_CREATE_USER_PROFILE_ENDPOINT = URLs.getBaseInternalApiUrl() + "client-profiles";

    public static String INTERNAL_DELETE_USER_PROFILE_ENDPOINT = URLs.getBaseInternalApiUrl() + "client-profiles/%s/TOUCH/%s";

    public static String INTERNAL_LOGOUT_AGENT = URLs.getBaseInternalApiUrl() + "v6/agents/logout/%s";

    public static String INTERNAL_DECREASING_TOUCHGO_PLAN = URLs.getBaseInternalApiUrl() + "tenants/touchGo/standard";

    public static String INTERNAL_SESSION_DETAILS = URLs.getBaseInternalApiUrl() + "sessions?tenantName=%s&clientId=%s&page=1&count=10&dateFrom=0&dateTo=0";

    public static String INTERNAL_TENANTS = URLs.getBaseInternalApiUrl() + "tenants/";

    public static String INTERNAL_GET_CHATS_FINISHED_BY_AGENT =  URLs.getBaseInternalApiUrl() + "chats/finished-by-agent?agentId=%s&page=%s&size=%s";

    public static String INTERNAL_CHAT_BY_CLIENT = URLs.getBaseInternalApiUrl() + "chats/by-client/paging?tenantId=%s&clientId=%s&page=0&size=10";

    public static String INTERNAL_CONFIG_ATTRIBUTES = URLs.getBaseInternalApiUrl() + "tenants/config-attributes/";


    // ==================  PLATFORM ============== //

    public static  String PLATFORM_ACCOUNTS = URLs.getBasePlatformUrl() + "/auth/accounts";

    public static  String PLATFORM_SIGN_IN = URLs.getBasePlatformUrl() + "/auth/accounts/sign-in";

    public static String PLATFORM_SEND_INVITATION = URLs.getBasePlatformUrl() + "/invitations";

    public static String PLATFORM_ACCEPT_INVITATION = URLs.getBasePlatformUrl() + "/invitations/%s/accepted";

    public static String PLATFORM_USER_ROLES = URLs.getBasePlatformUrl() + "/roles?details=false";

    public static String PLATFORM_USER = URLs.getBasePlatformUrl() + "/users";

    public static String PLATFORM_SUBSCRIPTIONS_LIST = URLs.getBasePlatformUrl() + "/subscriptions?pageNumber=1&pageSize=50&ascending=false";

    public static String PLATFORM_SUBSCRIPTIONS_DEACTIVATION = URLs.getBasePlatformUrl() + "/subscriptions/%s/deactivated";

    public static String PLATFORM_PAYMENT_METHODS = URLs.getBasePlatformUrl() + "/payment-methods";

    public static String PLATFORM_CLOSE_ACCOUNT = URLs.getBasePlatformUrl() + "/accounts/close";

    public static String PLATFORM_BILLING_INFO = URLs.getBasePlatformUrl() + "/accounts/billing-info";

    public static String PLATFORM_ROLES_PERMITIONS = URLs.getBasePlatformUrl() + "/roles/%s/permissions?details=false";

    // ====================  tie ================= //

    public static String TIE_CHAT_URL = URLs.getBaseTieURL() + "%s/chats/?q=";

    public static String TIE_INTENT_WITHOUT_SENTIMENT_URL = URLs.getBaseTieURL() + "%s/chats/?q=%s";

    public static String TIE_INTENT_WITH_TIE_SENTIMENT_URL = URLs.getBaseTieURL() + "%s/chats/?q=%s&tie_sentiment=True";

    public static String TIE_INTENT_WITH_TIE_TYPE_URL = URLs.getBaseTieURL() + "%s/chats/?q=%s&type=";

    public static String TIE_INTENT_PLUS_SENTIMENT_URL = URLs.getBaseTieURL() + "%s/chats/?q=%s&sentiment=true";

    public static String TIE_INTENT_SPECIFYING_SENTIMENT_URL = URLs.getBaseTieURL() + "%s/intents/%s";

    public static String TIE_SENTIMENTS = URLs.getBaseTieURL() + "%s/sentiment/?q=%s";

    public static String TIE_ANSWER_URL = URLs.getBaseTieURL() + "%s/answers/?intent=%s";

    public static String TIE_ANSWERS_LIST = URLs.getBaseTieURL() + "%s/answers_map/?intents=%s";

    public static String TIE_ANSWER_BY_CATEGORY_URL = URLs.getBaseTieURL() + "%s/answers/?category=%s";

    public static String TIE_DELETE_TENANT = URLs.getBaseTieURL() + "?tenant=%s";

    public static String TIE_CONFIG = URLs.getBaseTieURL() + "%s/config";

    public static String TIE_TRAININGS =  URLs.getBaseTieURL() + "%s/train";

    public static String TIE_ADDING_INTENT_SAMPLE_TEXT_TO_TRAINING = URLs.getBaseTieURL() + "%s/intents/%s/train/%s";

    public static String TIE_CLEARING_CONFIGS = URLs.getBaseTieURL() + "%s/reset?clear=nlp_config,train_data";

    public static String TIE_POST_TRAINSET = URLs.getBaseTieURL() + "%s/trainset/test";

    public static String TIE_GET_TRAINSET = URLs.getBaseTieURL() + "%s/trainset";

    public  static String TIE_NER = URLs.getBaseTieURL() + "%s/ner-trainset/";

    public  static String TIE_NER_DELETE = URLs.getBaseTieURL() + "%s/ner-trainset/%s";

    public static String TIE_BASE_INTENT_ANSWER_CREATING = URLs.getBaseTieURL() + "%s/answers/?intent=%s";

    public static String TIE_POST_SEMANTIC = URLs.getBaseTieURL() + "%s/chats/?q=%s";

    public static String TIE_USER_INPUT = URLs.getBaseTieURL() + "%s/user_inputs/";

    public static String TIE_NEW_INTENT_MANAGEMENT = URLs.getBaseTieURL() + "%s/intents-management/";

    public static String TIE_SAMPLES = URLs.getBaseTieURL() + "%s/train-data-management/";

    public static String TIE_TRAINING =  URLs.getBaseTieURL() + "%s/training";

    public static String TIE_MODELS =  URLs.getBaseTieURL() + "%s/models";

    public static String TIE_ALL_INTENTS =  URLs.getBaseTieURL() + "%s/intents";

    public static String TIE_SLOTS_MANAGEMENT =  URLs.getBaseTieURL() + "%s/slots-management";

    // ====================  SOCIAL ================= //

    public static String SOCIAL_HEALTH_CHECK = URLs.getBaseSocialUrl() + "health";

    // ====================  PORTAL ================= //

    public static String PORTAL_LOGIN_PAGE = URLs.getBasePortalUrl() + "#/login";

    public static String PORTAL_SIGN_UP_PAGE = URLs.getBasePortalUrl() + "#/signup";

    public static String PORTAL_ACCOUNT_ACTIVATION = URLs.getBasePortalUrl() + "#/activation/%s ";

    // ====================  TAF ================= //

    public static String TAF_MESSAGES = URLs.getBaseTafUrl() + "resources/taf-messages?tenant=%s";

    // ====================  .Control ================= //

    public static String DOT_CONTROL_HTTP_INTEGRATION = URLs.getTouchApiBaseURL() + "http-integrations";

    public static String DOT_CONTROL_TO_BOT_MESSAGE = URLs.getBaseSocialUrl() + "http/1.0/message";

    public static String DOT_CONTROL_INIT_MESSAGE = URLs.getBaseSocialUrl() + "http/1.0/init";

}

