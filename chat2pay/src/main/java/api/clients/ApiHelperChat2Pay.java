package api.clients;

import api.MainApi;
import datamanager.UnityClients;
import io.restassured.response.ResponseBody;
import lombok.Getter;

import static api.UnityAuthenticationAPIHelper.getAuthToken;

@Getter
public class ApiHelperChat2Pay extends MainApi {

    public static final ThreadLocal<String> token = new ThreadLocal<>();

    public static String logInToUnity() {
        token.set(getAuthToken(UnityClients.DEV_CHAT_2_PAY_USER));
        return getAuthToken(UnityClients.DEV_CHAT_2_PAY_USER);
    }

    public static ResponseBody getChat2PayQuery(String endpoint) {
        return getQuery(endpoint, token.get(), 200);
    }
}
