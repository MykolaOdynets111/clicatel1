package restHandler;

import interfaces.APIHelper;

import static io.restassured.RestAssured.given;

public class RestHandlerChatHub implements APIHelper {
    private String response;;

    public void get(String Auth, String Endpoint){
        response = given()
        .get(Endpoint).getBody().asString();
        System.out.println(response);
    }
}
