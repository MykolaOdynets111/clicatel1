package restHandler.repo;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Authentication {
    public void getAuthToken(String endpoint, String username, String password) {
        RequestSpecification request = RestAssured.given();

        //Create a body
        Map<String, Object> Credentials = new HashMap<>();
        Credentials.put("email", username);
        Credentials.put("password", password);

        //Request a body and header
        request.body(Credentials);
        request.header("Content-Type", "application/json");
        request.header("accept", "application/json");

        String response;
        response = request.post(endpoint).getBody().asString();
        System.out.println(response);
    }
}
