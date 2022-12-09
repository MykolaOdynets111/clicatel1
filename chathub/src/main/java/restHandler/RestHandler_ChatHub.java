package restHandler;

import interfaces.APIHelper;
import static io.restassured.RestAssured.given;

public class RestHandler_ChatHub implements APIHelper {
    public void get(String authorization, String endpoint) {
        String response;
        response = given().get(endpoint).getBody().asString();
        System.out.println(response);
    }
}
