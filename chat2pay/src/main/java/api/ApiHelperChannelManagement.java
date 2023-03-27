package api;

import api.MainApi;
import api.Endpoints;
import data.models.request.channels.ChannelManagement;
import data.models.request.channels.ChannelStatus;
import data.models.request.channels.ChannelType;
import io.restassured.response.Response;

import static java.lang.String.format;


public class ApiHelperChannelManagement extends MainApi {

    public static Response postChannelManagement(ChannelManagement channelManagement, String widgetId, String authToken) {
        return postQuery(authToken, String.format(Endpoints.CHANNEL_CONFIGURATION, widgetId), channelManagement);
    }

    public static Response updateChannelStatus(ChannelStatus status, String widgetId, String authToken) {
        return putQuery(authToken, format(Endpoints.CHANNEL_STATUS, widgetId), status);
    }

    public static Response removeChannelIntegration(ChannelType status, String widgetId, String authToken) {
        return deleteQuery(authToken, format(Endpoints.CHANNEL_CONFIGURATION, widgetId), status);
    }
}
