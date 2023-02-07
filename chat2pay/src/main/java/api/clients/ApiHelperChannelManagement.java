package api.clients;

import api.MainApi;
import api.models.request.ChannelManagement;
import io.restassured.response.Response;

import static java.lang.String.format;


public class ApiHelperChannelManagement extends MainApi {

    public static Response postChannelManagement(ChannelManagement channelManagement, String widgetId, String authToken) {
        return postQuery(format(Endpoints.CHANNEL_MANAGEMENT_LINK_CHANNEL, widgetId), channelManagement, authToken);
    }
}
