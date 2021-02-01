package mc2api;

import datamanager.model.*;
import dbmanager.DBConnector;
import drivermanager.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import mc2api.auth.PortalAuthToken;
import mc2api.auth.dto.SignInRequest;
import mc2api.endpoints.EndpointsPlatform;
import org.testng.Assert;

import java.util.*;
import java.util.stream.Collectors;

public class ApiHelperPlatform {

    public static Response getPlatformAccountAndToken(String userName, String userPass){
        return  RestAssured.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(new SignInRequest(userName, userPass))
                .post(EndpointsPlatform.PLATFORM_ACCOUNTS);
    }

    public static Response signInToPortal(String token, String accountId){
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"token\": \"" + token + "\",\n" +
                        "  \"accountId\": \"" + accountId + "\"\n" +
                        "}")
                .post(EndpointsPlatform.PLATFORM_SIGN_IN);
    }

    public static Response sendNewAgentInvitation(String tenantOrgName, String agentEmail, String name, String lastName){

        List<String> ids = getIdsOfRoles(tenantOrgName, "Touch agent role");
        if(ids.size()==0) ids = getIdsOfRoles(tenantOrgName, "TOUCH_AGENT");
        String[] idsArray = new String[ids.size()];
        for(int i=0; i<ids.size(); i++){
            idsArray[i] = "\""+ids.get(i)+"\"";
        }

       return   RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .body("{\n" +
                        "  \"invitations\": [\n" +
                        "    {\n" +
                        "      \"targetEmail\": \""+agentEmail+"\",\n" +
                        "      \"firstName\": \"" +name+ "\",\n" +
                        "      \"lastName\": \"" +lastName+ "\",\n" +
                        "      \"roleIds\": "+ Arrays.toString(idsArray) +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .post(EndpointsPlatform.PLATFORM_SEND_INVITATION);
    }

    public static List<String> getAllRolePermission(String tenantOrgName, String role){
        List<String> ids = getIdsOfRoles(tenantOrgName, "Touch agent role");
        List<String> permissions = new ArrayList<>();
        for(String id : ids){
            Response resp = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                    .get(String.format(EndpointsPlatform.PLATFORM_ROLES_PERMITIONS, id));
            permissions.addAll(resp.jsonPath().getList(" permissions"));
        }
        return permissions;
    }

    public static void acceptInvitation(String tenantOrgName, String invitationID, String pass){
        Response resp = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .body("{\n" +
                        "  \"password\": \""+pass+"\"\n" +
                        "}")
                .post(String.format(EndpointsPlatform.PLATFORM_ACCEPT_INVITATION, invitationID));
        if(resp.statusCode()!=201){
            Assert.fail("Accepting an invitation was not successful \n"+
                    "Resp status code: " + resp.statusCode() + "\n" +
                    "Resp body: " + resp.getBody().asString());
        }
    }

    public static List<String> getIdsOfRoles(String tenantOrgName, String roleDescription){
        Response resp = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .get(EndpointsPlatform.PLATFORM_USER_ROLES);
        List<String> ids = new ArrayList<>();
        resp.getBody().jsonPath().getList("roles", Map.class).stream().map(e -> e)
                        .filter(e -> e.get("description").equals(roleDescription)). //e.get("name")
                        forEach(e -> {ids.add(
                                (String) e.get("id"));});
         ids.forEach(e -> e = e.replace(e, "\""+e+"\""));
         return ids;
    }

    public static void deleteUser(String tenantOrgName, String userID){
        if(!userID.equals("none")) {
            Response resp = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                    .delete(EndpointsPlatform.PLATFORM_USER + "/" + userID);
            if (resp.statusCode() == 404) {
                RestAssured.given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                        .delete(EndpointsPlatform.PLATFORM_SEND_INVITATION + "/" + userID);
            }
        }
    }

    public static String getUserID(String tenantOrgName, String userEmail){
        Response resp = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .get(EndpointsPlatform.PLATFORM_USER);
        Map user =  resp.getBody().jsonPath().getList("users", Map.class)
                                    .stream().filter(e -> e.get("email").equals(userEmail))
                                    .findFirst().orElse(null);
        return user!=null ? (String) user.get("id") : "none";
    }

    public static List<String> getListOfAutogeneratedUsersIds(String tenantOrgName){
        Response resp = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .get(EndpointsPlatform.PLATFORM_USER);
        return resp.getBody().jsonPath().getList("users", Map.class)
                .stream().filter(e -> e.get("email").toString().contains("aqa_"))
                .map(e -> ((String) e.get("id")))
                .collect(Collectors.toList());
    }

    public static String getAccountUserFullName(String tenantOrgName, String userEmail){
        Response resp = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .get(EndpointsPlatform.PLATFORM_USER);
        Map user = resp.getBody().jsonPath().getList("users", Map.class)
                .stream().filter(e -> e.get("email").equals(userEmail))
                .findFirst().get();
        return user.get("firstName") + " " +  user.get("lastName");
    }

    public static boolean isActiveUserExists(String tenantOrgName, String userEmail){
        Response resp = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .get(EndpointsPlatform.PLATFORM_USER);
        return resp.getBody().jsonPath().getList("users", Map.class)
                .stream().anyMatch(e -> e.get("email").equals(userEmail));
    }

    public static List<Integer> getListOfActiveSubscriptions(String tenantOrgName){
        Response resp =   RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .get(EndpointsPlatform.PLATFORM_SUBSCRIPTIONS_LIST);

        return resp.getBody().jsonPath().getList("data", Map.class)
                .stream().filter(e -> e.get("status").equals("SERVICE_ACTIVE")|e.get("status").equals("WILL_AUTORENEW"))
                .map(e -> ((Integer) e.get("serviceEid")))
                .collect(Collectors.toList());
    }

    public static List<PaymentMethod> getAllActivePaymentMethods(String authToken){
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", authToken)
                .get(EndpointsPlatform.PLATFORM_PAYMENT_METHODS)
                .getBody().jsonPath().getList("paymentMethods", PaymentMethod.class);
    }

    public static List<PaymentMethod> getAllNotDefaultPaymentMethods(String authToken){
        return getAllActivePaymentMethods(authToken)
                .stream().filter(e -> e.getDefaultPaymentMethod().equals(false))
                .collect(Collectors.toList());
    }

    public static void deletePaymentMethod(String authToken, String paymentID){
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", authToken)
                .delete(EndpointsPlatform.PLATFORM_PAYMENT_METHODS+"/"+paymentID);
    }

    public static void deleteAllNotDefaultPayments(String authToken, String paymentType){
        getAllNotDefaultPaymentMethods(authToken)
                .forEach(e -> deletePaymentMethod(authToken, e.getId()));
    }

    public static void closeAccount(String accountName, String email, String pass){
        String accessToken = "";
        try {
            accessToken = PortalAuthToken.getAccessTokenForPortalUser(accountName, email, pass);
        }catch (NullPointerException|NoSuchElementException e){
            return; // nothing to close - account doesn't exist
        }
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization",accessToken)
                .body("{\n" +
                        "  \"email\": \""+email+"\",\n" +
                        "  \"password\": \""+pass+"\",\n" +
                        "  \"reason\": \"string\"\n" +
                        "}")
                .post(EndpointsPlatform.PLATFORM_CLOSE_ACCOUNT);
    }

    public static Response getAccountBillingInfo(String tenantOrgName){
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .get(EndpointsPlatform.PLATFORM_BILLING_INFO);
    }

    public static MC2AccountBalance getAccountBalance(String token){
        return RestAssured.given().log().all()
                .header("Authorization", token)
                .get(EndpointsPlatform.PLATFORM_ACCOUNT_BALANCE)
                .getBody().as(MC2AccountBalance.class);
    }

    public static Response sendSignUpRequest(AccountSignUp accountSignUp){
         Response resp = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(accountSignUp)
                .post(EndpointsPlatform.PLATFORM_ACCOUNT_SIGN_UP);
        if (resp.statusCode()!=201) {
            Assert.fail("Account sign up was unsuccessful\n" + resp.statusCode() + "\n" + "\n" +
                    "URL: " + EndpointsPlatform.PLATFORM_ACCOUNT_SIGN_UP + "\n"
                    +resp.getBody().asString());
        }
         return resp;
    }

    public static Response activateAccount(String activationID){
        Response resp = RestAssured.given().log().all()
                .post(String.format(EndpointsPlatform.PLATFORM_ACCOUNT_ACTIVATION, activationID));
        if (resp.statusCode()!=200) {
            Assert.fail("Account activation was unsuccessful\n" + resp.statusCode() + "\n" + "\n" +
                    "URL: " + String.format(EndpointsPlatform.PLATFORM_ACCOUNT_ACTIVATION, activationID) + "\n"
                    +resp.getBody().asString());
        }
        return resp;
    }

    public static String createNewAccount(AccountSignUp accountSignUp){
        Response resp = sendSignUpRequest(accountSignUp);
        String accountId = resp.getBody().jsonPath().getString("accountId");
        String activationID = DBConnector.getAccountActivationIdFromMC2DB(ConfigManager.getEnv(), accountId);
        activateAccount(activationID);
        return accountId;
    }

    public static List<MC2SandboxNumber> getTestNumbers(String accName, String email, String pass){
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(accName, email, pass))
                .get(EndpointsPlatform.PLATFORM_SANDBOX_NUMBERS)
                .getBody().jsonPath().getList("sandboxNumberList", MC2SandboxNumber.class);
    }

    public static void deleteAllTestNumbers(String accName, String email, String pass){
        List<MC2SandboxNumber> numbers = getTestNumbers(accName, email, pass);
        for (MC2SandboxNumber number : numbers){
            RestAssured.given().log().all()
                    .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(accName, email, pass))
                    .delete(EndpointsPlatform.PLATFORM_SANDBOX_NUMBERS + "/" + number.getId());

        }
    }

    public static List<CartOrder> getAllCartItems(String authToken){
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", authToken)
                .get(EndpointsPlatform.PLATFORM_CART_ORDER)
                .getBody().jsonPath().getList("items", CartOrder.class);
    }

    public static void deleteAllFromCart(String authToken){
        List<CartOrder> cartItems = getAllCartItems(authToken);
        for(CartOrder order : cartItems){
            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .header("Authorization", authToken)
                    .delete(EndpointsPlatform.PLATFORM_CART_ITEMS + order.getId());
        }
    }
}
