package com.clickatell.models;

/**
 * Created by sbryt on 6/7/2016.
 */
public class EndPointsClass {


// MC2 END POINTS
// Accounts endpoint
public final static String ACCOUNTS = "/accounts";
    public final static String ACCOUNT_PROFILE = ACCOUNTS + "/account-profile";
    public final static String ACCOUNT_SANDBOX_NUMBERS = "/sandbox-numbers";
    public final static String ACCOUNT_BILLING_INFO = ACCOUNTS + "/billing-info";
    public final static String ACCOUNT_STATE = ACCOUNTS + "/{accountId}/state";

    //Admin two-way numbers short
    public final static String ADMIN_TWO_WAY_NUMBERS_SHORT_APPLICATION = "/admin/two-way-numbers/short/application/{id}";
    public final static String ADMIN_TWO_WAY_NUMBERS_SHORT_APPLICATION_APPROVED = ADMIN_TWO_WAY_NUMBERS_SHORT_APPLICATION + "/approved";
    public final static String ADMIN_TWO_WAY_NUMBERS_SHORT_APPLICATION_COMPLETED = ADMIN_TWO_WAY_NUMBERS_SHORT_APPLICATION + "/completed";

    //Admin user profile
    public final static String ADMIN_USER_PROFILES = "/admin/user-profiles";
    public final static String ADMIN_USER_PROFILES_ACCOUNT_LIMITS = ADMIN_USER_PROFILES + "/account-limits";
    public final static String ADMIN_USER_PROFILES_USER_QUOTA = ADMIN_USER_PROFILES + "/{userMetadataId}/account-limit";
    //App config endpoint
    public final static String APP_CONFIG = "/app-config";
    public final static String APP_CONFIG_PROFILE = APP_CONFIG + "/profile";

    //Auth
    public final static String AUTH = "/auth";
    public final static String AUTH_ACCOUNT = AUTH + ACCOUNTS;
    public final static String AUTH_ACCOUNT_SIGN_IN = AUTH_ACCOUNT + "/sign-in";
    public final static String AUTH_SIGN_UP = AUTH + "/sign-up";

    //Common data endpoint
    public final static String COMMON_DATA = "/common-data";
    public final static String COMMON_DATA_ACCOUNT_TYPES = COMMON_DATA + "/account-types";
    public final static String COMMON_DATA_COUNTRIES = COMMON_DATA + "/countries";
    public final static String COMMON_DATA_COUNTRY_PHONES = COMMON_DATA + "/country-phones";
    public final static String COMMON_DATA_CURRENCIES = COMMON_DATA + "/currencies";
    public final static String COMMON_DATA_GET_AREA_CODES_WITH_STATES = COMMON_DATA + "/country/{countryId}";
    public final static String COMMON_DATA_COUNTRY_STATES = COMMON_DATA_GET_AREA_CODES_WITH_STATES + "/states";
    public final static String COMMON_DATA_COUNTRY_COVERAGE = COMMON_DATA_GET_AREA_CODES_WITH_STATES + "/coverage";


    // Integration endpoint
    public final static String INTEGRATIONS = "/integrations";
    public final static String INTEGRATION = INTEGRATIONS + "/{integrationId}";
    public final static String INTEGRATION_ACTIVITY = INTEGRATION + "/activity";
    public final static String INTEGRATION_API_KEYS = INTEGRATION + "/api-keys";
    public final static String INTEGRATION_ARCHIVE = INTEGRATION + "/archived";

    //Invitations endpoint
    public final static String INVITATIONS = "/invitations";
    public final static String INVITATIONS_ID = INVITATIONS + "/{invitationId}";
    public final static String INVITATIONS_PROFILE = INVITATIONS_ID + "/profile";
    public final static String INVITATIONS_TOKEN = INVITATIONS_ID + "/token";
    public final static String INVITATIONS_TOKEN_ID = INVITATIONS + "/{invitationToken}";
    public final static String INVITATIONS_CSV = INVITATIONS + "/csv";
    public final static String INVITATIONS_SIGN_UP = INVITATIONS_TOKEN_ID + "/accepted";

    //Messages endpoint
    public final static String MESSAGES = "/messages";
    public final static String MESSAGES_CALLBACK = MESSAGES + "/callback";

    //Roles endpoint
    public final static String ROLES = "/roles";
    public final static String ROLE = ROLES + "/{roleId}";
    public final static String ROLE_PERMISSIONS = ROLE + "/permissions";
    public final static String ROLE_PERMISSION = ROLE_PERMISSIONS + "/{permissionId}";

    //Send box numbers
    public static final String SANDBOX_NUMBERS = "/sandbox-numbers";
    public static final String SANDBOX_NUMBER = SANDBOX_NUMBERS + "/{phoneId}";
    public static final String SANDBOX_NUMBER_TEST_MESSAGES = SANDBOX_NUMBER + "/test-messages";
    public static final String SANDBOX_NUMBER_VERIFICATION_CODES = SANDBOX_NUMBER + "/verification-codes";
    public static final String SANDBOX_NUMBER_VERIFIED_CODES = SANDBOX_NUMBER + "/verified-codes";

    //Solutions endpoint
    public static final String SOLUTIONS = "/solutions";

    //User profiles endpoint
    public static final String USER_PROFILES = "/user-profiles";
    public static final String USER_PROFILES_ACCOUNTS = USER_PROFILES + "/accounts";
    public static final String USER_PROFILES_RESET_PASSWORD = USER_PROFILES + "/reset-password-email";
    public static final String SAVE_NEW_PASSWORD = USER_PROFILES + "/{passwordResetId}/password";

    //User endpoint
    public static final String USERS = "/users";
    public static final String USERS_PERMISSIONS = USERS + "/permissions";
    public static final String USERS_ROLES = USERS + "/roles";
    public static final String USER = USERS + "/{userId}";
    public static final String USER_PERMISSION = USER + "/permissions";
    public static final String USER_ROLES = USER + "/roles";
    public static final String USER_ROLE = USER_ROLES + "/{roleId}";
    public static final String USERS_PROFILE = USERS + "/profile";
    public static final String USER_PROFILE = USER + "/profile";
    public static final String USER_STATUS = USER + "/state";

    //Payment methods
    public static final String PAYMENT_METHODS = "/payment-methods";
    public static final String PAYMENT_METHODS_CREDIT_CARDS = PAYMENT_METHODS + "/credit-cards";
    public static final String PAYMENT_METHODS_REFERENCE_TOKEN = PAYMENT_METHODS + "/reference-token";
    public static final String PAYMENT_METHODS_ACCEPT_CALLBACK = PAYMENT_METHODS + "/tract-callback/{accountId}/{status}";

    //Two-Way endpoint
    public static final String TWO_WAY = "/two-way";
    public static final String TWO_WAY_NUMBER = TWO_WAY + "/{numberId}";

    //Two-ways-long endpoint
    public static final String TWO_WAY_LONG = TWO_WAY + "/long";
    public static final String TWO_WAY_LONG_AVAILABLE = TWO_WAY_LONG + "/available";
    public static final String TWO_WAY_LONG_RESERVED = TWO_WAY_LONG + "/reserved";
    public static final String TWO_WAY_LONG_SUBSCRIPTION_PLANS = TWO_WAY_LONG + "/subscription-plans";

    //Two-way-short endpoint
    public static final String TWO_WAY_SHORT = TWO_WAY + "/short";
    public static final String TWO_WAY_SHORT_CREATE_APPLICATION = TWO_WAY_SHORT + "/application";
    public static final String TWO_WAY_SHORT_UPDATE_APPLICATION = TWO_WAY_SHORT_CREATE_APPLICATION + "/{applicationId}";
    public static final String TWO_WAY_SHORT_CONFIRM_APPLICATION = TWO_WAY_SHORT_UPDATE_APPLICATION + "/confirmed";
    public static final String TWO_WAY_SHORT_ORDERED = TWO_WAY_SHORT_UPDATE_APPLICATION + "/ordered";
    public static final String TWO_WAY_SHORT_SUBSCRIPTION_PLANS = TWO_WAY_SHORT + "/subscription-plans";

    //Orders endpoint
    public static final String ORDERS = "/orders";
    public static final String ORDERS_CONFIRMED = ORDERS + "/confirmed";
    public static final String ORDERS_DELETE_ORDER_ITEM = ORDERS + "/items/{itemId}";

// TOUCH END POINTS
    //Tenants endpoint
    public final static String TENANTS =  "/tenants";
    public final static String TENANT =  TENANTS+"/{tenantId}";
    public final static String ADDRESS =  TENANT+"/address/{addressId}";
    public final static String COLOURS =  TENANT+"/colours";
    public final static String RESOURCES =  TENANT+"/resources/{name}";

}
