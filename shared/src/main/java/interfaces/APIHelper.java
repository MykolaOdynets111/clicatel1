package interfaces;


import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public interface APIHelper {
    public void get(String Auth, String Endpoint);
}