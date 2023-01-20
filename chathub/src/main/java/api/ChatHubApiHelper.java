package api;

import datamanager.UnityClients;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import static api.UnityAuthenticationAPIHelper.getAuthToken;

public class ChatHubApiHelper extends MainApi {
    public static ResponseBody getChatHubQuery(String endpoint, int responseCode) {
        return getQuery(endpoint, getAuthToken(UnityClients.DEMO_CHAT_2_PAY_USER), responseCode);
    }

    public static Response postChatHubQuery(String endpoint, Object body) {
        return post(endpoint, body, getAuthToken(UnityClients.DEMO_CHAT_2_PAY_USER));
    }


    public static ResponseBody deleteChatHubQueryWithAuth(String endpoint, int responseCode) {
        return deleteQueryWithAuth(endpoint, getAuthToken(UnityClients.DEMO_CHAT_2_PAY_USER), responseCode);
    }

    public static ResponseBody putChatHubQuerywithAuthAndBody(String endpoint,Object body, int responseCode) {
        return putQuerywithAuthAndBody(endpoint, getAuthToken(UnityClients.DEMO_CHAT_2_PAY_USER),body, responseCode);

    }

}