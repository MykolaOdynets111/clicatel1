package api.clients;

import api.MainApi;
import api.models.request.ChannelManagement;
import api.models.request.ChannelStatus;
import io.restassured.response.Response;

import static java.lang.String.format;


public class ApiHelperChannelManagement extends MainApi {

    public static Response postChannelManagement(ChannelManagement channelManagement, String widgetId, String authToken) {
        return postQuery(format(Endpoints.CHANNEL_MANAGEMENT, widgetId), channelManagement, authToken);
    }

    public static Response updateChannelStatus(ChannelStatus status, String widgetId, String authToken) {
        return putQuery(format(Endpoints.CHANNEL_STATUS, widgetId), status, authToken);
    }
}
