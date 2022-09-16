package apihelper;

import driverfactory.URLs;

public class Endpoints {

    // =================== TOUCH =============== //

    public static String ACCESS_TOKEN_ENDPOINT = URLs.getTouchApiBaseURL() + "auth/access-token";

    public static String GET_ALL_TENANTS_ENDPOINT = "tenants?state=ACTIVE";

    public static String WIDGET_VISIBILITY_HOURS = URLs.getTouchApiBaseURL() + "tenants/%s/hours/webchat";

    public static String AGENT_SUPPORT_HOURS = URLs.getTouchApiBaseURL() + "api/support-hours";

    public static String WIDGET_VISIBILITY_TERRITORIES = URLs.getTouchApiBaseURL() + "territories/availability";

    public static String FACEBOOK_INTEGRATION = URLs.getTouchApiBaseURL() + "facebook-registration/integration";

    public static String TWITTER_INTEGRATION = URLs.getTouchApiBaseURL() + "twitter-registration/integration";

    public static String AGENT_INFO_ME = URLs.getBaseWSInternalURL() + "agents/%s";

    public static String FEATURE = URLs.getTouchApiBaseURL() + "features/tenant";

    public static String INTEGRATIONS_ENABLING_DISABLING = URLs.getTouchApiBaseURL() + "integrations/channel/enable";

    public static String INTEGRATION_EXISTING_CHANNELS = URLs.getBaseWSApiURL() + "channel";

    public static String INTEGRATIONS = URLs.getTouchApiBaseURL() + "integrations";

    public static String CHAT_HISTORY = URLs.getTouchApiBaseURL() + "chats/history?sessionId=%s";

    public static String SESSION_CAPACITY = URLs.getTouchApiBaseURL() + "tenants/sessions-capacity/";

    public static String AGENT_ASSIGNED_TICKETS = URLs.getTouchApiBaseURL() + "agent/tickets";

    public static String CUSTOMER_VIEW = URLs.getTouchApiBaseURL() + "customer-view/";

    public static String DELETE_AGENT_IMAGE = URLs.getTouchApiBaseURL() + "agents/%s/image";

    public static String TENANT_CONFIG = URLs.getTouchApiBaseURL() + "tenants/%s/config";

    public static String TENANT_INFO = URLs.getTouchApiBaseURL() + "tenant";

    public static String ACTIVE_CHATS_BY_AGENT = URLs.getBaseWSApiURL() + "chats/search?page=0&size=200";

    public static String CLOSE_ACTIVE_CHAT = URLs.getBaseWSApiURL() + "chats/close-chat-by-id?chatId=%s";

    public static String CRM_TICKET = URLs.getTouchApiBaseURL() + "client-profiles/%s/crm-tickets";

    public static String DELETE_CRM_TICKET = URLs.getTouchApiBaseURL() + "client-profiles/crm-tickets/";

    public static String TAGS_FOR_CRM_TICKET = URLs.getTouchApiBaseIntegrationURL() + "api/tags";

    public static String TENANT_BRAND_LOGO = URLs.getTouchApiBaseURL() + "tenants/%s/resources/tenant_logo";

    public static String TENANT_BRAND_LOGO_TRANS = URLs.getTouchApiBaseURL() + "tenants/%s/resources/tenant_logo_trans";

    public static String TENANT_AVAILABLE_AGENTS = URLs.getTouchApiBaseURL() + "agent-availability/logged-in";

    public static String CLIENT_PROFILE_ATTRIBUTES = URLs.getTouchApiBaseURL() + "client-profiles/attributes";

    public static String SURVEY_MANAGEMENT = URLs. getBaseWSApiURL() + "channel/%s";

    public static String UPDATE_SURVEY_MANAGEMENT = URLs.getAgentDeskURL() + "api/platform/channel/%s-orca/%s/survey-config";

    public static String CHATS_INFO = URLs.getTouchApiBaseIntegrationURL() + "chats/%s";

    public static String AUTORESPONDER_CONTROLLER = URLs.getTouchApiBaseIntegrationURL() + "api/autoresponders/%s";

    public static String PAST_SENTIMENT_REPORT = URLs.getBaseWSApiURL() + "dashboard/customer-history-report/past-sentiment-report";

    public static String AVERAGE_CUSTOMER_SATISFACTION_REPORT = URLs.getBaseWSApiURL() + "dashboard/customer-history-report/average-customer-satisfaction-report";

    // ================== INTERNAL ============== //

    public static String INTERNAL_TENANT_ADDRESS = URLs.getBaseInternalApiUrl() + "tenants/%s/addresses";

    public static String INTERNAL_LAST_CLIENT_SESSION = URLs.getBaseInternalApiUrl() + "sessions/tenant/%s/client/%s/last";

    public static String INTERNAL_TENANT_CONFIG = URLs.getBaseWSInternalURL() + "tenants/%s";

    public static String INTERNAL_FEATURE_STATE = URLs.getBaseInternalApiUrl() + "features/tenants/%s/%s/%s";

    public static String INTERNAL_COUNT_OF_LOGGED_IN_AGENTS = URLs.getBaseInternalApiUrl() + "loggedin-agents-count/%s";

    public static String INTERNAL_CREATE_USER_PROFILE_ENDPOINT = URLs.getBaseInternalApiUrl() + "client-profiles";

    public static String INTERNAL_DELETE_USER_PROFILE_ENDPOINT = URLs.getBaseInternalApiUrl() + "client-profiles/%s/TOUCH/%s";

    public static String INTERNAL_CLIENT_PROFILE_ATTRIBUTES_ENDPOINT = URLs.getBaseInternalApiUrl() + "client-profiles/aggregated/%s";

    public static String INTERNAL_LOGOUT_AGENT = URLs.getBaseInternalApiUrl() + "v6/agents/logout/%s";

    public static String INTERNAL_DECREASING_TOUCHGO_PLAN = URLs.getBaseInternalApiUrl() + "tenants/touchGo/standard";

    public static String INTERNAL_SESSION_DETAILS = URLs.getBaseInternalApiUrl() + "sessions?tenantName=%s&clientId=%s&page=1&count=50&dateFrom=0&dateTo=0";

    public static String INTERNAL_TENANTS = URLs.getBaseWSInternalURL() + "tenants";

    public static String INTERNAL_GET_CHATS_FINISHED_BY_AGENT = URLs.getBaseInternalApiUrl() + "chats/finished-by-agent?agentId=%s&page=%s&size=%s";

    public static String INTERNAL_CHAT_BY_CLIENT = URLs.getBaseInternalApiUrl() + "chats/by-client/paging?tenantId=%s&clientId=%s&page=0&size=10";

    public static String TENANT_CHAT_PREFERENCES = URLs.getBaseWSApiURL() + "tenants/chat-preferences";

    public static String INTERNAL_GET_TICKETS = URLs.getBaseInternalApiUrl() + "chats/ticket?tenantId=%s&page=%s&size=10&state=UNASSIGNED&direction=ASC";

    public static String INTERNAL_PROCESS_TICKET = URLs.getBaseInternalApiUrl() + "chats/ticket/process";

    public static String INTERNAL_CREATE_HISTORY = URLs.getBaseInternalApiUrl() + "chats/chat/history";

    public static String INTERNAL_AUTORESPONDER_CONTROLLER = URLs.getAgentDeskURL() +  "api/platform/autoresponders";

    public static String INTERNAL_CHAT_USER_BY_ID = URLs.getBaseInternalApiUrl() + "api/chat-users/by-external-user-id/%s/%s/%s";

    public static String INTERNAL_CHAT_USERS = URLs.getBaseInternalApiUrl() + "api/chat-users";

    public static String INTERNAL_ELASTIC_CHAT_SEARCH = URLs.getBaseInternalApiUrl() + "api/search/chats";

    public static String INTERNAL_ELASTIC_CHAT_INDEX = URLs.getBaseInternalApiUrl() + "api/search/chats/index";

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

    public static String TIE_TRAININGS = URLs.getBaseTieURL() + "%s/train";

    public static String TIE_ADDING_INTENT_SAMPLE_TEXT_TO_TRAINING = URLs.getBaseTieURL() + "%s/intents/%s/train/%s";

    public static String TIE_CLEARING_CONFIGS = URLs.getBaseTieURL() + "%s/reset?clear=nlp_config,train_data";

    public static String TIE_POST_TRAINSET = URLs.getBaseTieURL() + "%s/trainset/test";

    public static String TIE_GET_TRAINSET = URLs.getBaseTieURL() + "%s/trainset";

    public static String TIE_NER = URLs.getBaseTieURL() + "%s/ner-trainset/";

    public static String TIE_NER_DELETE = URLs.getBaseTieURL() + "%s/ner-trainset/%s";

    public static String TIE_BASE_INTENT_ANSWER_CREATING = URLs.getBaseTieURL() + "%s/answers/?intent=%s";

    public static String TIE_POST_SEMANTIC = URLs.getBaseTieURL() + "%s/chats/?q=%s";

    public static String TIE_USER_INPUT = URLs.getBaseTieURL() + "%s/user_inputs/";

    public static String TIE_NEW_INTENT_MANAGEMENT = URLs.getBaseTieURL() + "%s/intents-management/";

    public static String TIE_SAMPLES = URLs.getBaseTieURL() + "%s/train-data-management/";

    public static String TIE_TRAINING = URLs.getBaseTieURL() + "%s/training";

    public static String TIE_MODELS = URLs.getBaseTieURL() + "%s/models";

    public static String TIE_ALL_INTENTS = URLs.getBaseTieURL() + "%s/intents";

    public static String TIE_SLOTS_MANAGEMENT = URLs.getBaseTieURL() + "%s/slots-management";

    // ====================  SOCIAL ================= //

    public static String SOCIAL_HEALTH_CHECK = URLs.getBaseSocialUrl() + "internal/actuator/health";

    public static String SOCIAL_FACEBOOK_HOOKS = URLs.getBaseSocialUrl() + "facebook/hooks";


    // ====================  .Control ================= //

    public static String DOT_CONTROL_HTTP_INTEGRATION = URLs.getTouchApiBaseIntegrationURL() + "integrations/http";

    public static String DOT_CONTROL_TO_BOT_MESSAGE = URLs.getBaseSocialUrl() + "http/1.0/message";

    public static String DOT_CONTROL_INIT_MESSAGE = URLs.getBaseSocialUrl() + "http/1.0/init";

    public static String DOT_CONTROL_ATTACHMENTS = URLs.getBaseSocialUrl() + "http/1.0/attachments";

    // ====================  Departments ================= //

    public static String DEPARTMENTS = URLs.getAgentDeskURL() + "/api/platform/departments";

    // ====================  ORCA ================= //

    public static String CREATE_ORCA_INTEGRATION = URLs.getBaseWSInternalURL() + "api/channel/%s-orca/%s";

    public static String UPDATE_ORCA_INTEGRATION = URLs.getBaseWSInternalURL() + "api/channel/%s-orca/%s/%s";

    public static String ORCA_INTEGRATIONS_LIST = URLs.getBaseWSInternalURL() + "api/channel/%s";

    // ====================  WS  ================= //

    public static String CREATE_TENANT_WS = URLs.getBaseWSInternalURL() + "tenants/";

    public static String CREATE_AGENT_WS = URLs.getBaseWSInternalURL() + "agents/%s";

    public static String TOUCH_AUTH = URLs.getBaseWSInternalURL() + "auth/fake-auth-token";

    public static String NEW_USER = URLs.getBaseWSInternalURL() + "end-users";

    public static String CLOSED_CHATS = URLs.getBaseWSInternalURL() + "chats/closed";

}

