package api;

import io.restassured.response.Response;

import static api.Endpoints.CHAT_TO_PAY_CONFIGURATION_ENDPOINT;

public class ApiHelperConfigurations extends ApiHelperChat2Pay {

    public static Response getC2PConfigurationResponse(String authToken) {
        return getQuery(authToken, CHAT_TO_PAY_CONFIGURATION_ENDPOINT);
    }
}
