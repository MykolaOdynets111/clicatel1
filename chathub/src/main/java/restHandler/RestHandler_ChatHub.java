package restHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.APIHelper;
import io.restassured.response.Response;
import pojoClasses.Providers.GetProviders;

import static io.restassured.RestAssured.given;

public class RestHandler_ChatHub implements APIHelper {
    public void getProviders(String authorization, String endpoint) {
        GetProviders[] getProviders = null;
        Response response = given().get(endpoint);

        getProviders = response.getBody().as(GetProviders[].class);
        String version = getProviders[0].getVersions().get(0).getVersion();

    }
}
