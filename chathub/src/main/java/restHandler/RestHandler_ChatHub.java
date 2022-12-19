package restHandler;

import interfaces.APIHelper;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RestHandler_ChatHub implements APIHelper {
    public Response executeGetProviders(String authorization, String endpoint) {
        return given().get(endpoint);
    }
}
