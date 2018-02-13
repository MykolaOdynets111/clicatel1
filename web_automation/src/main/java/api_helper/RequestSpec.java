package api_helper;

import com.github.javafaker.Faker;
import driverManager.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

public class RequestSpec {

    private static ThreadLocal<RequestSpecification> requestSpecification = new ThreadLocal<RequestSpecification>();
    private static Faker faker = new Faker();

    public static RequestSpecification getRequestSpecification(){

        if(requestSpecification.get() == null){
            requestSpecification.set(new RequestSpecBuilder()
                    .addHeader("Accept", "application/json, text/javascript")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", getAccessToken())
                    .setBaseUri(String.format(Endpoints.BASE_ENDPOINT, ConfigManager.getEnv()))
                    .log(LogDetail.ALL)
                    .build());
        }
        return requestSpecification.get();
    }



    public static String getAccessToken() {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", getAuthToken())
                .body("{\"clientId\":\""+faker.code().asin()+"\"}")
                .post(String.format(Endpoints.BASE_ENDPOINT, ConfigManager.getEnv()) + Endpoints.ACCESS_TOKEN_ENDPOINT)
                .jsonPath().get("accessToken");
    }

    public static void removeRequestSpecification(){
        if(!(requestSpecification.get() == null)) {
            requestSpecification.set(null);
        }
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
