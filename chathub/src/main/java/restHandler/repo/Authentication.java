package restHandler.repo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojoClasses.Authentication.GetAuthToken;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Authentication {
    public String getAuthToken(String endpoint, String username, String password) {
        RequestSpecification request = RestAssured.given();

        //Create a body
        Map<String, Object> Credentials = new HashMap<>();
        Credentials.put("email", username);
        Credentials.put("password", password);

        //Request a body and header
        request.body(Credentials);
        request.header("Content-Type", "application/json");
        request.header("accept", "application/json");

        GetAuthToken authToken = null;
        Response response = request.post(endpoint);
        response.then().assertThat().statusCode(200);

        authToken = response.getBody().as(GetAuthToken.class);
        String token = authToken.getToken();
        return token;
    }
}
