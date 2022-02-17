import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClosingChatsForAllTenanats {

    private static String tenantStartName ="PerformanceIhor";

    //for closing chat on all tenants
    public static void main(String[] args) {
        List<String> tenantsIds = getTenantsIds();
        System.out.println("Tenants number: " + tenantsIds.size());
        System.out.println(tenantsIds);
        for (String tenantId : tenantsIds) {
            System.out.println("Tenant id: " + tenantId);
            String supervisorId = getSupervisor(tenantId);
            int idsQuantity = 0;
            do {
                String jwt = getJWT(tenantId,supervisorId);
                List<String> conversationIds = getChats(jwt);
                System.out.println(conversationIds);
                idsQuantity = conversationIds.size();
                System.out.println("New chats quantity: " + conversationIds.size());

                int i = 1;
                for (String conversationId : conversationIds) {
                    System.out.println(i++ + " of " + idsQuantity);
                    Response response = closeChats(jwt, conversationId);
                    if (response.getStatusCode() != 200) {
                        System.out.println(response.getBody().asString());
                        jwt = ClosingChatsForAllTenanats.getJWT(tenantId,supervisorId);
                        closeChats(jwt, conversationId);
                    }
                }
            } while (idsQuantity > 0);
        }
    }

    public static String getJWT(String tenantId, String agentId){
        String authURL = "https://stage-chatdesk-platform-app-bravo.int-eks-stage.shared-stage.eu-west-1.aws.clickatell.com/internal/auth/fake-auth-token?agentId="+agentId+"&tenantId="+tenantId+"&domain=.int-eks-stage.shared-stage.eu-west-1.aws.clickatell.com&createFakeMc2Token=true";
        return RestAssured.given().contentType(ContentType.JSON).get(authURL).getBody().jsonPath().getString("jwt");
    }

    public static Response closeChats(String jwt, String conversationId){
        return RestAssured.given()
                .accept(ContentType.JSON)
                .header("Authorization",
                        jwt)
                .get(String.format("https://stage-chatdesk-platform-app-bravo.int-eks-stage.shared-stage.eu-west-1.aws.clickatell.com/api/chats/close-chat-by-id?chatId=%s", conversationId));}

    public static List<String> getChats(String jwt){
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .header("Authorization", jwt)
                .body("{\n" +
                        "  \"chatStates\": [\n" +
                        "    \"LIVE_IN_SCHEDULER_QUEUE\",\n" +
                        "    \"LIVE_ASSIGNED_TO_AGENT\"\n" +
                        "  ]\n" +
                        "}")
                .post("https://stage-chatdesk-platform-app-bravo.int-eks-stage.shared-stage.eu-west-1.aws.clickatell.com/api/chats/search?page=0&size=200");
         return response.getBody().jsonPath().getList("content.chatId");
    }


    public static List<String> getTenantsIds(){
        Response response = RestAssured.given()
                .get("https://stage-chatdesk-platform-app-bravo.int-eks-stage.shared-stage.eu-west-1.aws.clickatell.com/internal/tenants");
        List<Map<String,String>> tenants = response.getBody().jsonPath().getList("");
        return tenants.stream().filter(e -> e.get("orgName").contains(tenantStartName)).collect(Collectors.toList())
                .stream().map(s->s.get("id")).collect(Collectors.toList());
    }

    public static String getSupervisor(String tenantId){
        Response response = RestAssured.given()
                .get("https://stage-chatdesk-platform-app-bravo.int-eks-stage.shared-stage.eu-west-1.aws.clickatell.com/internal/agents/"+tenantId+"");
        List<Map<String,String>> agents = response.getBody().jsonPath().getList("");
    return agents.stream().filter(e->e.get("agentType").equals("SUPERVISOR")).findFirst().get().get("id");
    }
}
