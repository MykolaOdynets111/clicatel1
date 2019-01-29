package dataManager;

import api_helper.Endpoints;
import driverManager.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Accounts {

    public static Map<String, String> getAccountsAndToken(String tenantOrgName, String userName, String userPass) {
        String accountName = getCorrectAccountName(tenantOrgName);
        Map<String, String> tokenAndAccount = new HashMap<>();
        Response resp =  RestAssured.given()
                .log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"email\": \""+userName+"\",\n" +
                        "  \"password\": \""+userPass+"\"\n" +
                        "}")
                .post(Endpoints.PLATFORM_ACCOUNTS);
        if (resp.getBody().asString().contains("Not Authorized")){
            Assert.assertTrue(false, "User "+userName +" / "+userPass+" is not authorized in portal."
                   );
        }
        try {
            tokenAndAccount.put("token", resp.jsonPath().get("token"));
            List<HashMap<String, String>> accounts = resp.jsonPath().get("accounts");
            HashMap<String, String> targetAccount = accounts.stream().filter(e -> e.get("name").equals(accountName)).findFirst().get();
            tokenAndAccount.put("accountId", targetAccount.get("id"));
        }catch(JsonPathException e){
            Assert.assertTrue(false, "Unexpected response received while getting portal access token");
        }

        return tokenAndAccount;
    }

    public static Map<String, String> getToken(String accountName, String userName, String userPass) {
        Map<String, String> tokenAndAccount = new HashMap<>();
        Response resp =  RestAssured.given()
                .log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"email\": \""+userName+"\",\n" +
                        "  \"password\": \""+userPass+"\"\n" +
                        "}")
                .post(Endpoints.PLATFORM_ACCOUNTS);
        if (resp.getBody().asString().contains("Not Authorized")){
            Assert.assertTrue(false, "User "+userName +" / "+userPass+" is not authorized in portal."
            );
        }
        tokenAndAccount.put("token", resp.jsonPath().get("token"));
        List<HashMap<String, String>> accounts = resp.jsonPath().get("accounts");
        HashMap<String, String> targetAccount =accounts.stream().filter(e -> e.get("name").equals(accountName)).findFirst().get();
        tokenAndAccount.put("accountId", targetAccount.get("id"));

        return tokenAndAccount;
    }

    private static String getCorrectAccountName(String tenantOrgName) {
        String accountName;
        if (tenantOrgName.equalsIgnoreCase("general bank demo")) {
            if (ConfigManager.getEnv().equals("dev")) {
                accountName = "generalbank";
            } else {
                accountName = "generalbankdemo";
            }
            return accountName;
        } else{
           return MC2Account.getAccountByOrgName(ConfigManager.getEnv(), tenantOrgName).getAccountName();
        }
    }
}
