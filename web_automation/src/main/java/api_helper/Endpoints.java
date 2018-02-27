package api_helper;

public class Endpoints {

    public static String BASE_ENDPOINT = "https://%s-touch.clickatelllabs.com/v6/";

    public static String BASE_INTERNAL_ENDPOINT = "https://%s-touch.clickatelllabs.com/internal/";

    public static String ACCESS_TOKEN_ENDPOINT = "auth/access-token";

    public static String GET_ALL_TENANTS_ENDPOINT = "tenants?state=ACTIVE";

    public static String CREATE_USER_PROFILE_ENDPOINT = "client-profiles/%s/%s?key=%s&value=%s";

    public static String DELETE_USER_PROFILE_ENDPOINT = "client-profiles/%s/%s";
}
