package mc2api.auth;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import mc2api.auth.dto.SignInResponse;
import mc2api.endpoints.EndpointsPlatform;
import mc2api.auth.dto.SignInRequest;
import org.apache.http.HttpStatus;

public class AdminAccessTokenProvider {

    private static volatile ThreadLocal<String> SHARED_ADMIN_ACCESS_TOKEN = new ThreadLocal<>();

    public static String getThreadLocalToken(String email, String pass) {
        if (SHARED_ADMIN_ACCESS_TOKEN.get()==null) {
            String token = getPlatformAdminAuthToken(email, pass);
            SHARED_ADMIN_ACCESS_TOKEN.set(token);
            return SHARED_ADMIN_ACCESS_TOKEN.get();
        } else {
            return SHARED_ADMIN_ACCESS_TOKEN.get();
        }
    }

    public String getNewToken(String email, String pass) {
        return getPlatformAdminAuthToken(email, pass);
    }

    private static String getPlatformAdminAuthToken(String email, String userPass) {
        return  RestAssured.given()
                .log().ifValidationFails()
                .contentType(ContentType.JSON)
                .body(new SignInRequest(email, userPass))
                .post(EndpointsPlatform.PLATFORM_ADMIN_SIGN_IN)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(SignInResponse.class)
                .getToken();
    }
}
