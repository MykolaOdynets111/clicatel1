package api;

import datamanager.UnityClients;
import io.restassured.response.ResponseBody;

import static api.UnityAuthenticationAPIHelper.getAuthToken;

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

}