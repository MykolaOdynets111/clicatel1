import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClosingBrokenChats {

    public static void main(String[] args) {
        List<String> tenantsIds = getTenantsIds();
        System.out.println("Tenants number: " + tenantsIds.size());
        System.out.println(tenantsIds);
        for (String tenantId: tenantsIds) {
            System.out.println("tenantId: " + tenantId);
            int idsQuantity = 0;
            do {
                List<String> conversationIds = getChatsFromDB(tenantId);
                idsQuantity = conversationIds.size();
                System.out.println("LiveClosing status chats quantity: " + conversationIds.size());
                System.out.println(conversationIds);
                if (idsQuantity > 0) {
                    DBConnector.updateDataInDB("update chat set chat_status_id = 11 where chat_status_id = 10  and tenant_id = '" + tenantId + "'");
                }
                int i = 1;

                for (String conversationId : conversationIds) {
                    System.out.println(i++ + " of " + idsQuantity);
                    Response response = closeChats(tenantId, conversationId);
                    if (response.getStatusCode() != 200) {
                        System.out.println(response.getBody().asString());
                        DBConnector.updateDataInDB("update chat set chat_status_id = 11 where chat_status_id = 10  and tenant_id = '" + tenantId + "'");
                        closeChats(tenantId, conversationId);
                    }
                }
            } while (idsQuantity > 0);
        }
    }

    public static Response closeChats(String tenantId, String conversationId){
        return RestAssured.given()
                .get(String.format("https://stage-chatdesk-platform-app-bravo.int-eks-stage.shared-stage.eu-west-1.aws.clickatell.com/internal/chats/resend-chat-end-response/%s/%s", tenantId, conversationId));
    }

    public static List<String> getChatsFromDB(String tenantId){
       List<String> ids = DBConnector.getListOfUnclosedChats("select * from chat where chat_status_id = 10  and tenant_id = '"+tenantId+"'", "id");
       return ids;
    }

    public static List<String> getTenantsIds(){
        Response response = RestAssured.given()
                .get("https://stage-chatdesk-platform-app-bravo.int-eks-stage.shared-stage.eu-west-1.aws.clickatell.com/internal/tenants");
        List<Map<String,String>> tenants = response.getBody().jsonPath().getList("");
        return tenants.stream().filter(e -> e.get("orgName").contains("Performance")).collect(Collectors.toList())
                .stream().map(s->s.get("id")).collect(Collectors.toList());
    }

}
