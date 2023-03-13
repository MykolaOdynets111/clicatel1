package api.clients;

import api.MainApi;
import api.models.request.channels.ChannelManagement;
import api.models.request.channels.ChannelStatus;
import api.models.request.channels.ChannelType;
import io.restassured.response.Response;

import static java.lang.String.format;


public class ApiHelperChannelManagement extends MainApi {

    public static Response postChannelManagement(ChannelManagement channelManagement, String widgetId, String authToken) {
        return postQuery(authToken, format(Endpoints.CHANNEL_CONFIGURATION, widgetId), channelManagement);
    }

    public static Response updateChannelStatus(ChannelStatus status, String widgetId, String authToken) {
        return putQuery(format(Endpoints.CHANNEL_STATUS, widgetId), status, authToken);
    }

    public static Response removeChannelIntegration(ChannelType status, String widgetId, String authToken) {
        return deleteQuery(format(Endpoints.CHANNEL_CONFIGURATION, widgetId), status, authToken);
    }
}
