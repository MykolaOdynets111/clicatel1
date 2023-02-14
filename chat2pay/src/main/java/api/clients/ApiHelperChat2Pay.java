package api.clients;

import api.MainApi;
import datamanager.UnityClients;
import io.restassured.response.ResponseBody;
import lombok.Getter;

import static api.UnityAuthenticationAPIHelper.getAuthToken;

@Getter
public class ApiHelperChat2Pay extends MainApi {

    public static final ThreadLocal<String> token = new ThreadLocal<>();

    public static String logInToUnity(UnityClients unityClients) {
        if (token.get() == null) {
            token.set(getAuthToken(unityClients));
        }
        return token.get();
    }

    public static ResponseBody getChat2PayQuery(String endpoint) {
        return getQuery(endpoint, token.get(), 200);
    }
}
