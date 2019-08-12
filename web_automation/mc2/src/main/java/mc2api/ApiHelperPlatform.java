package mc2api;

import datamanager.jacksonschemas.MC2AccountBalance;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.*;
import java.util.stream.Collectors;

public class ApiHelperPlatform {

    public static Response getPlatformAccountAndToken(String userName, String userPass){
        return  RestAssured.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"email\": \"" + userName + "\",\n" +
                        "  \"password\": \"" + userPass + "\"\n" +
                        "}")
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

       return   RestAssured.given()
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
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .body("{\n" +
                        "  \"password\": \""+pass+"\"\n" +
                        "}")
                .post(String.format(EndpointsPlatform.PLATFORM_ACCEPT_INVITATION, invitationID));
    }

    public static List<String> getIdsOfRoles(String tenantOrgName, String roleDescription){
        Response resp = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .get(EndpointsPlatform.PLATFORM_USER_ROLES);
        List<String> ids = new ArrayList<>();
        resp.getBody().jsonPath().getList("roles", Map.class).stream().map(e -> ((Map) e))
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

    public static List<String> getListOfActivePaymentMethods(String tenantOrgName, String paymentType){
        Response resp =   RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .get(EndpointsPlatform.PLATFORM_PAYMENT_METHODS);

        return resp.getBody().jsonPath().getList("paymentMethods", Map.class)
                .stream().filter(e -> e.get("paymentType").equals(paymentType))
                .map(e -> ((String) e.get("id")))
                .collect(Collectors.toList());
    }

    public static void deletePaymentMethod(String tenantOrgName, String paymentID){
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .delete(EndpointsPlatform.PLATFORM_PAYMENT_METHODS+"/"+paymentID);
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

    public static MC2AccountBalance getAccountBalance(String tenantOrgName){
        return RestAssured.given().log().all()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .get(EndpointsPlatform.PLATFORM_ACCOUNT_BALANCE)
                .getBody().as(MC2AccountBalance.class);
    }
}
