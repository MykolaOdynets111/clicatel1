package api.clients;

import api.MainApi;
import io.restassured.response.Response;


public class ApiHelperAccounts extends MainApi {

    public static Response getAccountSettingsResponse(String authToken) {
        return getQuery(Endpoints.ACCOUNT_SETTINGS, authToken);
    }
}
