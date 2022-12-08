package restHandler;

import interfaces.APIHelper;
import static io.restassured.RestAssured.given;

public class RestHandler_ChatHub implements APIHelper {
    public void get(String Authorization, String Endpoint) {
        String response;
        response = given().get(Endpoint).getBody().asString();
        System.out.println(response);
    }
}
