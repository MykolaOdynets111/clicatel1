package mc2api.auth;

public class UserAccessTokenProvider {

    private static volatile ThreadLocal<String> ADMIN_ACCESS_TOKEN = new ThreadLocal<>();
    
    public static String getThreadLocalToken(String accountName, String email, String pass) {
        if (ADMIN_ACCESS_TOKEN.get()==null) {
            String token = PortalAuth.getMC2AuthToken(accountName, email, pass);
            ADMIN_ACCESS_TOKEN.set(token);
            return ADMIN_ACCESS_TOKEN.get();
        } else {
            return ADMIN_ACCESS_TOKEN.get();
        }
    }

    public String getNewToken(String accountName, String email, String pass) {
        return PortalAuth.getMC2AuthToken(accountName, email, pass);
    }
}
