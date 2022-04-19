package mc2api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import mc2api.auth.dto.SignInRequest;
import mc2api.endpoints.EndpointsPlatform;

public class ApiHelperPlatform {

    public static Response getPlatformAccountAndToken(String userName, String userPass){
        return  RestAssured.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(new SignInRequest(userName, userPass))
                .post(EndpointsPlatform.PLATFORM_ACCOUNTS);
    }

    public static Response signInToPortal(String token, String accountId){
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"token\": \"" + token + "\",\n" +
                        "  \"accountId\": \"" + accountId + "\"\n" +
                        "}")
                .post(EndpointsPlatform.PLATFORM_SIGN_IN);
    }

}
