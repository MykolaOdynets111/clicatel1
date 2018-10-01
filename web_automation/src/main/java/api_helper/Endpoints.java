package api_helper;

import driverManager.URLs;

public class Endpoints {

    // =================== TOUCH =============== //

    public static String ACCESS_TOKEN_ENDPOINT = URLs.getTouchApiBaseURL() + "auth/access-token";

    public static String GET_ALL_TENANTS_ENDPOINT = "tenants?state=ACTIVE";

    public static String WIDGET_VISIBILITY_HOURS = URLs.getTouchApiBaseURL() + "tenants/%s/hours/webchat";

    public static String WIDGET_VISIBILITY_TERRITORIES = URLs.getTouchApiBaseURL() + "territories/availability";

    public static String FACEBOOK_INTEGRATION =  URLs.getTouchApiBaseURL() + "facebook-registration/integration";

    public static String AGENT_INFO = URLs.getTouchApiBaseURL() + "agents/info?mc2Token=%s";

    public static String FEATURE =  URLs.getTouchApiBaseURL() + "features/tenant";

    // ================== INTERNAL ============== //

    public static String INTERNAL_TENANT_ADDRESS = URLs.getBaseInternalApiUrl() + "tenants/%s/addresses";

    public static String INTERNAL_LAST_CLIENT_SESSION = URLs.getBaseInternalApiUrl() + "sessions/tenant/%s/client/%s/last";

    public static String INTERNAL_TENANT_CONFIG = URLs.getBaseInternalApiUrl() + "tenants/%s/config ";

    public static String INTERNAL_FEATURE_STATE = URLs.getBaseInternalApiUrl() + "features/tenants/%s/%s/%s";

    public static String INTERNAL_COUNT_OF_LOGGED_IN_AGENTS = URLs.getBaseInternalApiUrl() + "loggedin-agents-count/%s";

    public static String INTERNAL_CREATE_USER_PROFILE_ENDPOINT = URLs.getBaseInternalApiUrl() + "client-profiles/%s/TENANT/%s?key=%s&value=%s";

    public static String INTERNAL_DELETE_USER_PROFILE_ENDPOINT = URLs.getBaseInternalApiUrl() + "client-profiles/%s/TENANT/%s";

    public static String INTERNAL_TENANT_CONFIGURED_MESSAGES = URLs.getBaseInternalApiUrl() + "taf/message-responses/%s";

    public static String INTERNAL_LOGOUT_AGENT = URLs.getBaseInternalApiUrl() + "v6/agents/logout/%s";

    // ==================  PLATFORM ============== //

    public static  String PLATFORM_ACCOUNTS = URLs.getBasePlatformUrl() + "/auth/accounts";

    public static  String PLATFORM_SIGN_IN = URLs.getBasePlatformUrl() + "/auth/accounts/sign-in";

    public static String PLATFORM_SEND_INVITATION = URLs.getBasePlatformUrl() + "/invitations";

    public static String PLATFORM_ACCEPT_INVITATION = URLs.getBasePlatformUrl() + "/invitations/%s/accepted";

    public static String PLATFORM_USER_ROLES = URLs.getBasePlatformUrl() + "/roles?details=false";

    public static String PLATFORM_USER = URLs.getBasePlatformUrl() + "/users";


    // ====================  TIE ================= //

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

    public static String TIE_CLEARING_CONFIGS = URLs.getBaseTieURL() + "?tenant=%s&clear=nlp_config,train_data";

    public static String TIE_POST_TRAINSET = URLs.getBaseTieURL() + "%s/trainset/test";

    public static String TIE_GET_TRAINSET = URLs.getBaseTieURL() + "%s/trainset";

    public  static String TIE_NER = URLs.getBaseTieURL() + "%s/ner-trainset/";

    public  static String TIE_NER_DELETE = URLs.getBaseTieURL() + "ner-trainset/%s";

    public static String TIE_BASE_INTENT_ANSWER_CREATING = URLs.getBaseTieURL() + "%s/answers/?intent=%s";

    public static String TIE_POST_SEMANTIC = URLs.getBaseTieURL() + "%s/chats/?q=%s";

    public static String TIE_TYPE = URLs.getBaseTieURL() + "";

    // ====================  SOCIAL ================= //

    public static String SOCIAL_HEALTH_CHECK = URLs.getBaseSocialUrl() + "/actuator/health";

}

