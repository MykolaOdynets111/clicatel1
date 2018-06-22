package api_helper;

import driverManager.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
                .post(String.format(Endpoints.BASE_PLATFORM_ENDPOINT, ConfigManager.getEnv()) +
                        Endpoints.PLATFORM_SEND_INVITATION);
    }


    public static void acceptInvitation(String tenantOrgName, String invitationID){
        RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .body("{\n" +
                        "  \"password\": \"p@$$w0rd4te$t\"\n" +
                        "}")
                .post(String.format(Endpoints.BASE_PLATFORM_ENDPOINT, ConfigManager.getEnv()) +
                        String.format(Endpoints.PLATFORM_ACCEPT_INVITATION, invitationID));
    }

    public static List<String> getIdsOfRoles(String tenantOrgName, String roleDescription){
        Response resp = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .get(String.format(Endpoints.BASE_PLATFORM_ENDPOINT, ConfigManager.getEnv()) +
                        Endpoints.PLATFORM_USER_ROLES);
        List<String> ids = new ArrayList<>();
        resp.getBody().jsonPath().getList("roles", Map.class).stream().map(e -> ((Map) e)).filter(e -> e.get("description").equals(roleDescription)).
                        forEach(e -> {ids.add(
                                (String) e.get("id"));});
         ids.stream().forEach(e -> e.replace(e, "\""+e+"\""));
         return ids;
    }

//    public static void main(String [] args){
//
//
//    {
}