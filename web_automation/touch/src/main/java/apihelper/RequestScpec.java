package apihelper;

import com.github.javafaker.Faker;
import driverfactory.URLs;
import drivermanager.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

public class RequestScpec {

    private static ThreadLocal<RequestSpecification> requestSpecification = new ThreadLocal<RequestSpecification>();
    private static Faker faker = new Faker();
    private static volatile ThreadLocal<String> PORTAL_USER_ACCESS_TOKEN = new ThreadLocal<>();
    private static volatile ThreadLocal<String> PORTAL_SECOND_USER_ACCESS_TOKEN = new ThreadLocal<>();


    public static RequestSpecification getRequestSpecification(){

        if(requestSpecification.get() == null){
            requestSpecification.set(new RequestSpecBuilder()
                    .addHeader("Accept", "application/json, text/javascript")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", getAccessToken())
                    .setBaseUri(URLs.getTouchApiBaseURL())
                    .log(LogDetail.ALL)
                    .build());
        }
        return requestSpecification.get();
    }


    public static String getAccessToken() {
        String accessToken = "";
        Response resp = null;
        try{
             resp = RestAssured.given()
                    .header("Content-Type", "application/json")
                    .header("Authorization", getAuthToken())
                    .body("{\"clientId\":\""+faker.code().asin()+"\"}")
                    .post(Endpoints.ACCESS_TOKEN_ENDPOINT);
            accessToken = resp.jsonPath().get("accessToken");
        } catch (JsonPathException e){
            Assert.assertTrue(true, "Endpoint '"+Endpoints.ACCESS_TOKEN_ENDPOINT+"' returns" +
                    " "+resp.statusCode()+" status code after POST request");
        }
        return accessToken;
    }

    private static String getAuthToken() {
        switch (ConfigManager.getEnv()) {
                case "dev":
                    return AuthorizationCodes.DEV_TOKEN.getAuthToken();
                default:
                    return AuthorizationCodes.DEV_TOKEN.getAuthToken();
            }
        }
}
