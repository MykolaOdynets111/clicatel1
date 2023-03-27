package api;

import api.MainApi;
import api.Endpoints;
import data.models.request.widgetconfigurations.AccountSettingsPropertyBody;
import io.restassured.response.Response;


public class ApiHelperAccounts extends MainApi {

    public static Response getAccountSettingsResponse(String authToken) {
        return getQuery(authToken, Endpoints.ACCOUNT_SETTINGS);
    }

    public static Response putAccountSettings(AccountSettingsPropertyBody settings, String authToken) {
        return putQuery(authToken, Endpoints.ACCOUNT_SETTINGS_SHOW_TUTORIAL, settings);
    }
}
