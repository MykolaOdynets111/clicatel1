package api_helper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApiHelperPlatform {

    public static void sendNewAgentInvitation(String tenantOrgName, String agentEmail){

        List<String> ids = getIdsOfRoles(tenantOrgName, "Touch agent role");
        String[] idsArray = new String[ids.size()];
        for(int i=0; i<ids.size(); i++){
            idsArray[i] = "\""+ids.get(i)+"\"";
        }

       Response resp =  RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .body("{\n" +
                        "  \"invitations\": [\n" +
                        "    {\n" +
                        "      \"targetEmail\": \""+agentEmail+"\",\n" +
                        "      \"firstName\": \"AQA\",\n" +
                        "      \"lastName\": \"TEST\",\n" +
                        "      \"roleIds\": "+ Arrays.toString(idsArray) +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .post(Endpoints.PLATFORM_SEND_INVITATION);
        int a = 2;
    }


    public static void acceptInvitation(String tenantOrgName, String invitationID, String pass){
        RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .body("{\n" +
                        "  \"password\": \""+pass+"\"\n" +
                        "}")
                .post(String.format(Endpoints.PLATFORM_ACCEPT_INVITATION, invitationID));
    }

    public static List<String> getIdsOfRoles(String tenantOrgName, String roleDescription){
        Response resp = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .get(Endpoints.PLATFORM_USER_ROLES);
        List<String> ids = new ArrayList<>();
        resp.getBody().jsonPath().getList("roles", Map.class).stream().map(e -> ((Map) e)).filter(e -> e.get("description").equals(roleDescription)).
                        forEach(e -> {ids.add(
                                (String) e.get("id"));});
         ids.stream().forEach(e -> e.replace(e, "\""+e+"\""));
         return ids;
    }

    public static void deleteUser(String tenantOrgName, String userID){
        Response resp = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .delete(Endpoints.PLATFORM_USER +"/"+ userID);
    }

    public static String getUserID(String tenantOrgName, String userEmail){
        Response resp = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .get(Endpoints.PLATFORM_USER);
        return (String) resp.getBody().jsonPath().getList("users", Map.class)
                .stream().filter(e -> e.get("email").equals(userEmail))
                .findFirst().get().get("id");
    }

    public static List<Integer> getListOfActiveSubscriptions(String tenantOrgName){
        Response resp =   RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .get(Endpoints.PLATFORM_SUBSCRIPTIONS_LIST);

        return resp.getBody().jsonPath().getList("data", Map.class)
                .stream().filter(e -> e.get("status").equals("SERVICE_ACTIVE"))
                .map(e -> ((Integer) e.get("serviceEid")))
                .collect(Collectors.toList());
    }

    public static void deactivateSubscription(String tenantOrgName, int subscriptionID){
        String url = String.format(Endpoints.PLATFORM_SUBSCRIPTIONS_DEACTIVATION, subscriptionID);
        RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .post(url);
    }

    public static List<String> getListOfActivePaymentMethods(String tenantOrgName, String paymentType){
        Response resp =   RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .get(Endpoints.PLATFORM_PAYMENT_METHODS);

        return resp.getBody().jsonPath().getList("paymentMethods", Map.class)
                .stream().filter(e -> e.get("paymentType").equals(paymentType))
                .map(e -> ((String) e.get("id")))
                .collect(Collectors.toList());
    }

    public static void deletePaymentMethod(String tenantOrgName, String paymentID){
        RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .delete(Endpoints.PLATFORM_PAYMENT_METHODS+"/"+paymentID);
    }

    public static void closeAccount(String accountName, String email, String pass){
        RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUserByAccount(accountName))
                .body("{\n" +
                        "  \"email\": \""+email+"\",\n" +
                        "  \"password\": \""+pass+"\",\n" +
                        "  \"reason\": \"string\"\n" +
                        "}")
                .post(Endpoints.PLATFORM_CLOSE_ACCOUNT);
    }

}
