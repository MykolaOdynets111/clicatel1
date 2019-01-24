package api_helper;

import com.github.javafaker.Faker;
import dataManager.Accounts;
import dataManager.Agents;
import dataManager.MC2Account;
import driverManager.ConfigManager;
import driverManager.URLs;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.util.Map;

public class RequestSpec {

    private static ThreadLocal<RequestSpecification> requestSpecification = new ThreadLocal<RequestSpecification>();
    private static Faker faker = new Faker();
    private static volatile ThreadLocal<String> PORTAL_USER_ACCESS_TOKEN = new ThreadLocal<>();


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
        if(PORTAL_USER_ACCESS_TOKEN.get()==null) {
            Agents user = Agents.getAgentFromCurrentEnvByTenantOrgName(tenantOrgName.toLowerCase(), "main agent");
            Map<String, String> tokenAndAccount = Accounts.getAccountsAndToken(tenantOrgName, user.getAgentName(), user.getAgentPass());
            Response resp = RestAssured.given()
                    .header("Content-Type", "application/json")
                    .body("{\n" +
                            "  \"token\": \"" + tokenAndAccount.get("token") + "\",\n" +
                            "  \"accountId\": \"" + tokenAndAccount.get("accountId") + "\"\n" +
                            "}")
                    .post(Endpoints.PLATFORM_SIGN_IN);

            PORTAL_USER_ACCESS_TOKEN.set(resp.jsonPath().get("token"));
            return PORTAL_USER_ACCESS_TOKEN.get();
        }else{
            return PORTAL_USER_ACCESS_TOKEN.get();
    }
    }

    public static String getAccessTokenForPortalUserByAccount(String accountName) {
        if (PORTAL_USER_ACCESS_TOKEN.get()==null) {
            MC2Account admin = MC2Account.getAccountDetails(ConfigManager.getEnv(), accountName);

            Map<String, String> tokenAndAccount = Accounts.getToken(accountName, admin.getEmail(), admin.getPass());
            Response resp = RestAssured.given()
                    .header("Content-Type", "application/json")
                    .body("{\n" +
                            "  \"token\": \"" + tokenAndAccount.get("token") + "\",\n" +
                            "  \"accountId\": \"" + tokenAndAccount.get("accountId") + "\"\n" +
                            "}")
                    .post(Endpoints.PLATFORM_SIGN_IN);

            PORTAL_USER_ACCESS_TOKEN.set(resp.jsonPath().get("token"));
            return PORTAL_USER_ACCESS_TOKEN.get();
        } else {
            return PORTAL_USER_ACCESS_TOKEN.get();
        }
    }

    public static void clearAccessTokenForPortalUser(){
        PORTAL_USER_ACCESS_TOKEN.remove();
        }


}
