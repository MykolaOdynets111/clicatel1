package api_helper;

public class Endpoints {

    public static String BASE_TOUCH_ENDPOINT = "https://%s-touch.clickatelllabs.com/v6/";

    public static String BASE_INTERNAL_ENDPOINT = "https://%s-touch.clickatelllabs.com/internal/";

    public static String ACCESS_TOKEN_ENDPOINT = "auth/access-token";

    public static String GET_ALL_TENANTS_ENDPOINT = "tenants?state=ACTIVE";

    public static String CREATE_USER_PROFILE_ENDPOINT = "client-profiles/%s/%s?key=%s&value=%s";

    public static String DELETE_USER_PROFILE_ENDPOINT = "client-profiles/%s/%s";

    public static String TENANT_CONFIGURED_MESSAGES = "taf/message-responses/%s";

    public static String WIDGET_VISIBILITY_HOURS = "tenants/%s/hours/webchat";

    public static String WIDGET_VISIBILITY_TERRITORIES = "territories/availability";

    public static String FACEBOOK_INTEGRATION = "facebook-registration/integration";


    // ================== INTERNAL ============== //

    public static String INTERNAL_TENANT_ADDRESS = "tenants/%s/addresses";

    public static String INTERNAL_LAST_CLIENT_SESSION = "sessions/tenant/%s/client/%s/last";


     // ==================  PORTAL ============== //

    public static String BASE_PLATFORM_ENDPOINT = "https://%s-platform.clickatelllabs.com";

    public static  String PLATFORM_ACCOUNTS = "/auth/accounts";

    public static  String PLATFORM_SIGN_IN = "/auth/accounts/sign-in";

    // ====================  TIE ================= //

    public static String BASE_TIE_URL = "http://%s-tie.clickatelllabs.com/tenants/";

    public static String BASE_TIE_PROD_URL = "http://tie.clickatelllabs.com/tenants/";

    public static String TIE_INTENT_WITHOUT_SENTIMENT_URL = BASE_TIE_URL+"%s/chats/?q=%s";

    public static String TIE_INTENT_WITH_TIE_SENTIMENT_URL = BASE_TIE_URL+"%s/chats/?q=%s&tie_sentiment=True";

    public static String TIE_INTENT_PLUS_SENTIMENT_URL = BASE_TIE_URL+"%s/chats/?q=%s&sentiment=true";

    public static String TIE_INTENT_SPECIFYING_SENTIMENT_URL = BASE_TIE_URL+"%s/intents/%s";

    public static String TIE_SENTIMENTS = BASE_TIE_URL+"%s/sentiment/?q=%s";

    public static String TIE_ANSWER_URL = BASE_TIE_URL + "%s/answers/?intent=%s";

    public static String TIE_ANSWERS_LIST = BASE_TIE_URL+"%s/answers_map/?intents=%s";

    public static String TIE_ANSWER_BY_CATEGORY_URL = BASE_TIE_URL + "%s/answers/?category=%s";

    public static String TIE_DELETE_TENANT = "?tenant=%s";

    public static String TIE_CONFIG = "%s/config";

    public static String TIE_TRAININGS = "%s/train";

    public static String TIE_ADDING_INTENT_SAMPLE_TEXT_TO_TRAINING = "%s/intents/%s/train/%s";

    public static String TIE_CLEARING_CONFIGS = "?tenant=%s&clear=nlp_config,train_data";
}

