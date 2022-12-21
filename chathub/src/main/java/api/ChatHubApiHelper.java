package api;

import clients.Endpoints;
import datamanager.Credentials;
import datamodelsclasses.jwt.GetJWT;
import io.restassured.response.ResponseBody;
import datamodelsclasses.authentication.GetAuthToken;

import java.util.HashMap;
import java.util.Map;

public class ChatHubApiHelper extends MainApi {


    public static ResponseBody getChatHubQuery(String endpoint, int responseCode) {
       return getQuery(endpoint,getAuthToken(), responseCode);
    }

    public static ResponseBody postChatHubQuery(String endpoint, Object body) {
        return postQuery(endpoint, body, getAuthToken(),200);
    }

    public static String getAuthToken() {
        //Create a body
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("email", Credentials.DEMO_CHAT_2_PAY_USER.getUsername());
        credentials.put("password", Credentials.DEMO_CHAT_2_PAY_USER.getPassword());
        //Get Token and ID
        GetAuthToken response = postQueryWithoutAuth(Endpoints.AUTH_ACCOUNTS, credentials,200).as(GetAuthToken.class);
        String token = response.getToken();
        String accountId = response.accounts.get(0).id;
        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("accountId", accountId);
        //Return JWT
        return postQueryWithoutAuth(Endpoints.JWT_ACCOUNT,body,200).as(GetJWT.class).getToken();
    }
}
