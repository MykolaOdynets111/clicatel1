package api;

import clients.Endpoints;
import datamanager.Credentials;
import io.restassured.response.ResponseBody;
import datamodelsclasses.Authentication.GetAuthToken;

import java.util.HashMap;
import java.util.Map;

public class ChatHubApiHelper extends MainApi {


    public static ResponseBody getChatHubQuery(String endpoint, int responseCode) {
       return getQuery(endpoint,getAuthToken(), responseCode);
    }

    public static ResponseBody postChatHubQuery(String endpoint, Object body) {
        return postQuery(endpoint, body, getAuthToken());
    }

    public static String getAuthToken() {
        //Create a body
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", Credentials.DEMO_CHAT_2_PAY_USER.getUsername());
        credentials.put("password", Credentials.DEMO_CHAT_2_PAY_USER.getPassword());

        return postQueryWithoutAuth(Endpoints.AUTH_ACCOUNTS,credentials).as(GetAuthToken.class).getToken();
    }
}
