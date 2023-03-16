package api.clients;

import api.MainApi;
import api.models.request.widgetconfigurations.AccountSettingsPropertyBody;
import io.restassured.response.Response;


public class ApiHelperAccounts extends MainApi {

    public static Response getAccountSettingsResponse(String authToken) {
        return getQuery(Endpoints.ACCOUNT_SETTINGS, authToken);
    }

    public static Response putAccountSettings(AccountSettingsPropertyBody settings, String authToken) {
        return putQuery(authToken, Endpoints.ACCOUNT_SETTINGS_SHOW_TUTORIAL, settings);
    }
}
