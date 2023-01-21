package api;

import datamanager.UnityClients;
import datamodelsclasses.InternalProductToken.InternalProducts;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBody;
import java.util.List;

import static api.UnityAuthenticationAPIHelper.getAuthToken;
import static api.UnityAuthenticationAPIHelper.getMC2ID;
import static clients.Endpoints.ADMIN_INTERNAL_PRODUCTS;

public class ChatHubApiHelper extends MainApi {
    public static ResponseBody getChatHubQuery(String endpoint, int responseCode) {
       return getQuery(endpoint,getAuthToken(UnityClients.DEMO_CHAT_2_PAY_USER), responseCode);
    }

    public static ResponseBody postChatHubQuery(String endpoint, Object body) {
        return postQuery(endpoint, body, getAuthToken(UnityClients.DEMO_CHAT_2_PAY_USER),200);
    }
    public static ResponseBody getChatHubQueryWithoutAuth(String endpoint, int responseCode) {
        return getQueryWithoutAuth(endpoint, responseCode);
    }

    public static ResponseBody getChatHubQueryWithInternalAuth(String endpoint,int responseCode){
        return getQueryForInternalApi(endpoint,getMC2ID(UnityClients.DEMO_CHAT_2_PAY_USER),getInternalProductToken(),responseCode);
    }
    public static String getInternalProductToken(){
        List<InternalProducts> response =RestAssured.given().log().all()
                .accept(ContentType.JSON)
                .get(ADMIN_INTERNAL_PRODUCTS).as(new TypeRef<List<InternalProducts>>(){});
        return response.get(0).getToken();
    }
}