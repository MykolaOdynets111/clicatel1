package api;

import datamanager.UnityClients;
import datamodelsclasses.InternalProductToken.InternalProducts;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import java.util.List;

import static api.UnityAuthenticationAPIHelper.getAuthToken;
import static api.UnityAuthenticationAPIHelper.getMC2ID;
import static clients.Endpoints.ADMIN_INTERNAL_PRODUCTS;

public class ChatHubApiHelper extends MainApi {
    public static ResponseBody getChatHubQuery(String endpoint, int responseCode) {
        return getQuery(endpoint, getAuthToken(UnityClients.DEMO_CHAT_HUB_USER), responseCode);
    }

    public static ResponseBody getChatHubQueryWithoutAuthToken(String endpoint, int responseCode) {
        return getQueryWithoutAuth(endpoint, responseCode);
    }

    public static Response postChatHubQuery(String endpoint, Object body) {
        return post(getAuthToken(UnityClients.DEMO_CHAT_HUB_USER), endpoint, body);
    }

    public static ResponseBody postChatHubQueryWithoutAuth(String endpoint, Object body, int responseCode) {
        return postQueryWithoutAuth(endpoint, body, responseCode);
    }

    public static ResponseBody putChatHubQuerywithAuthNoBody(String endpoint, int responseCode) {
        return putQueryWithAuthNoBody(endpoint, getAuthToken(UnityClients.DEMO_CHAT_HUB_USER), responseCode);
    }

    public static ResponseBody putChatHubQueryWithoutAuth(String endpoint, Object body, int responseCode) {
        return putQueryWithoutAuth(endpoint, body, responseCode);
    }

    public static ResponseBody deleteChatHubQueryWithAuth(String endpoint, int responseCode) {
        return deleteQueryWithAuth(endpoint, getAuthToken(UnityClients.DEMO_CHAT_HUB_USER), responseCode);
    }

    public static ResponseBody putChatHubQuerywithAuthAndBody(String endpoint, Object body, int responseCode) {
        return putQueryWithAuthAndBody(endpoint, getAuthToken(UnityClients.DEMO_CHAT_HUB_USER), body, responseCode);
    }

    public static ResponseBody getChatHubQueryWithoutAuth(String endpoint, int responseCode) {
        return getQueryWithoutAuth(endpoint, responseCode);
    }

    public static ResponseBody getChatHubQueryWithInternalAuth(String endpoint, int responseCode) {
        return getQueryForInternalApi(endpoint, getMC2ID(UnityClients.DEMO_CHAT_HUB_USER), getInternalProductToken(), responseCode);
    }

    public static ResponseBody getChatHubQueryAdminSecret(String endpoint, int responseCode) {
        return getQueryForAdminConfigurationSecret(endpoint, getAuthToken(UnityClients.DEMO_CHAT_HUB_USER), responseCode);
    }

    public static ResponseBody postChatHubQueryWithMC2Token(String endpoint, Object body, int responseCode) {
        return postQueryAdminMC2TokenAuth(endpoint, body, getAuthToken(UnityClients.DEMO_CHAT_HUB_USER), responseCode);
    }

    public static String getInternalProductToken() {
        List<InternalProducts> response = RestAssured.given().log().all()
                .accept(ContentType.JSON)
                .get(ADMIN_INTERNAL_PRODUCTS).as(new TypeRef<List<InternalProducts>>() {
                });
        return response.get(0).getToken();
    }
}