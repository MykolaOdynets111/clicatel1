package mc2api;

import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalAuth {


    public static Map<String, String> getAccountIDAndToken(String accountName, String userName, String userPass) {
        Map<String, String> tokenAndAccount = new HashMap<>();
        Response resp = ApiHelperPlatform.getPlatformAccountAndToken(userName, userPass);

        Assert.assertFalse(resp.getBody().asString().contains("Not Authorized"),
                "User "+userName +" / "+userPass+" is not authorized in portal.");
        try {
            tokenAndAccount.put("token", resp.jsonPath().get("token"));
            List<HashMap<String, String>> accounts = resp.jsonPath().get("accounts");
            HashMap<String, String> targetAccount = accounts.stream()
                    .filter(e -> e.get("name")
                    .equals(accountName)).findFirst().orElse(null);

            if(targetAccount == null) {
                Assert.fail("Passed accountName " + accountName + " is not returned from mc2\n"
                        +"Returned account name from getCorrectAccountName(tenantOrgName) " + accountName + "\n"
                        +"Returned response " + resp.getBody().asString());
            }
            tokenAndAccount.put("accountId", targetAccount.get("id"));
        }catch(JsonPathException e){
            Assert.fail("Unexpected response received while getting portal access token\n" +
                    "resp status: " + resp.statusCode() + "\n" +
                    "resp body: " + resp.getBody().asString());
        }

        return tokenAndAccount;
    }

    public static String getMC2AuthToken(String accountName, String userName, String userPass){
        Map<String, String> tokenAndAccount = getAccountIDAndToken(accountName, userName, userPass);
        Response resp = ApiHelperPlatform.signInToPortal(tokenAndAccount.get("token"),
                                                            tokenAndAccount.get("accountId"));

        Assert.assertEquals(resp.statusCode(), 200, "Sign in to portal was not successful\n" +
                "resp body: " + resp.getBody().asString());
        return resp.jsonPath().get("token");
    }
}
