package api.clients;

import api.MainApi;
import api.models.request.ChannelManagement;
import io.restassured.response.Response;


public class ApiHelperChannelManagement extends MainApi {

    public static Response postChannelManagement(ChannelManagement channelManagement, String authToken) {
        return postQuery(Endpoints.CHANNEL_MANAGEMENT_LINK_CHANNEL, channelManagement, authToken);
    }

//    public static Response putAccountSettings(AccountSettingsPropertyBody settings, String authToken) {
//        return putQuery(Endpoints.ACCOUNT_SETTINGS_SHOW_TUTORIAL, settings, authToken);
//    }
}
