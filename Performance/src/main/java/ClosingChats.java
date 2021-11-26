import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;

public class ClosingChats {

    private static String authURL = "https://stage-chatdesk-platform-app-bravo.int-eks-stage.shared-stage.eu-west-1.aws.clickatell.com/internal/auth/fake-auth-token?agentId=017d5277ffb4e8b3b2b5f13d80fcd9c9&tenantId=017d2870899b1a5c6c9ed8f85e9771e3&domain=.int-eks-stage.shared-stage.eu-west-1.aws.clickatell.com&createFakeMc2Token=true";

    public static void main(String[] args) {
        int idsQuantity = 0;
        do {
            System.out.println(getJWT());
            List<String> conversationIds = getChats();
            System.out.println(conversationIds);
            idsQuantity = conversationIds.size();
            System.out.println(conversationIds.size());
            System.out.println(conversationIds);

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
