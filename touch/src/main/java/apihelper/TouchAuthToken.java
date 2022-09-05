package apihelper;

public class TouchAuthToken {

    private static final ThreadLocal<String> TOUCH_USER_ACCESS_TOKEN = new ThreadLocal<>();
    private static final ThreadLocal<String> TOUCH_SECOND_USER_ACCESS_TOKEN = new ThreadLocal<>();

    public static String getAccessTokenForTouchUser(String tenantOrgName, String agent) {
        if (agent.toLowerCase().contains("second")) {
            return getAccessTokenForSecondAgent(tenantOrgName);
        } else {
            return getAccessTokenForMainAgent(tenantOrgName);
        }
    }

    private static String getAccessTokenForMainAgent(String tenantOrgName) {
        if (TOUCH_USER_ACCESS_TOKEN.get() == null) {
            TOUCH_USER_ACCESS_TOKEN.set(ApiHelper.getJWTToken(tenantOrgName, "main"));
        }
        return TOUCH_USER_ACCESS_TOKEN.get();
    }

    private static String getAccessTokenForSecondAgent(String tenantOrgName) {
        if (TOUCH_SECOND_USER_ACCESS_TOKEN.get() == null) {
            TOUCH_SECOND_USER_ACCESS_TOKEN.set(ApiHelper.getJWTToken(tenantOrgName, "second agent"));
        }
        return TOUCH_SECOND_USER_ACCESS_TOKEN.get();
    }

    public static void clearAccessTokenForPortalUser() {
        TOUCH_USER_ACCESS_TOKEN.remove();
        TOUCH_SECOND_USER_ACCESS_TOKEN.remove();
    }
}
