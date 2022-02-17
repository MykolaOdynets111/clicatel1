import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClosingChats {

    private static String tenantId = "017e7d4dad7ff39e8f756a7a1098c60c";
    private static String agentId = "017e7d4daf1ad5de8e2a817e2b2aaea4";
    private static String authURL = "https://stage-chatdesk-platform-app-bravo.int-eks-stage.shared-stage.eu-west-1.aws.clickatell.com/internal/auth/fake-auth-token?agentId="+agentId+"&tenantId="+tenantId+"&domain=.int-eks-stage.shared-stage.eu-west-1.aws.clickatell.com&createFakeMc2Token=true";

    public static void main(String[] args) {
        int idsQuantity = 0;
        do {
            System.out.println(getJWT());
            List<String> conversationIds = getChats();
            System.out.println(conversationIds);
            idsQuantity = conversationIds.size();
            System.out.println("New chats quantity: " + conversationIds.size());

            int i =1;
            String jwt = getJWT();
            for(String conversationId : conversationIds) {
                System.out.println(i++ + " of " + idsQuantity);
                Response response = closeChats(jwt, conversationId);
                if (response.getStatusCode() != 200) {
                    System.out.println(response.getBody().asString());
                    jwt = ClosingChats.getJWT();
                    closeChats(jwt, conversationId);
                }
            }
        }while( idsQuantity> 0);
    }

    public static String getJWT(){
        return RestAssured.given().contentType(ContentType.JSON).get(authURL).getBody().jsonPath().getString("jwt");
    }

    public static Response closeChats(String jwt, String conversationId){
        return RestAssured.given()
                .accept(ContentType.JSON)
                .header("Authorization",
                        jwt)
                .get(String.format("https://stage-chatdesk-platform-app-bravo.int-eks-stage.shared-stage.eu-west-1.aws.clickatell.com/api/chats/close-chat-by-id?chatId=%s", conversationId));}

    public static List<String> getChats(){
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .header("Authorization", ClosingChats.getJWT())
                .body("{\n" +
                        "  \"chatStates\": [\n" +
                        "    \"LIVE_IN_SCHEDULER_QUEUE\",\n" +
                        "    \"LIVE_ASSIGNED_TO_AGENT\"\n" +
                        "  ]\n" +
                        "}")
                .post("https://stage-chatdesk-platform-app-bravo.int-eks-stage.shared-stage.eu-west-1.aws.clickatell.com/api/chats/search?page=0&size=200");
         return response.getBody().jsonPath().getList("content.chatId");
    }
}
