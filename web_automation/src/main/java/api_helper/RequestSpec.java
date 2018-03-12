package api_helper;

import com.github.javafaker.Faker;
import dataprovider.Accounts;
import dataprovider.Agents;
import driverManager.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestSpec {

    private static ThreadLocal<RequestSpecification> requestSpecification = new ThreadLocal<RequestSpecification>();
    private static Faker faker = new Faker();
    private static String PORTAL_USER_ACCESS_TOKEN = null;


    public static RequestSpecification getRequestSpecification(){

        if(requestSpecification.get() == null){
            requestSpecification.set(new RequestSpecBuilder()
                    .addHeader("Accept", "application/json, text/javascript")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", getAccessToken())
                    .setBaseUri(String.format(Endpoints.BASE_TOUCH_ENDPOINT, ConfigManager.getEnv()))
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
                .post(String.format(Endpoints.BASE_TOUCH_ENDPOINT, ConfigManager.getEnv()) + Endpoints.ACCESS_TOKEN_ENDPOINT)
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

    public static String getAccessTokenForPortalUser(String tenantOrgName) {
        if (PORTAL_USER_ACCESS_TOKEN==null) {
            Agents user = Agents.getAgentFromCurrentEnvByTenantOrgName(tenantOrgName.toLowerCase());
            Map<String, String> tokenAndAccount = Accounts.getAccountsAndToken(tenantOrgName, user.getAgentName(), user.getAgentPass());
            Response resp = RestAssured.given()
                    .header("Content-Type", "application/json")
                    .body("{\n" +
                            "  \"token\": \"" + tokenAndAccount.get("token") + "\",\n" +
                            "  \"accountId\": \"" + tokenAndAccount.get("accountId") + "\"\n" +
                            "}")
                    .post(String.format(Endpoints.BASE_PLATFORM_ENDPOINT, ConfigManager.getEnv()) + Endpoints.PLATFORM_SIGN_IN);

            PORTAL_USER_ACCESS_TOKEN = resp.jsonPath().get("token");
            return PORTAL_USER_ACCESS_TOKEN;
        } else {
            return PORTAL_USER_ACCESS_TOKEN;
        }
    }


}
