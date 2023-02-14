package api.clients;

import io.restassured.response.Response;


public class ApiHelperMessagesConfigurations extends ApiHelperChat2Pay {

    public static Response getMessageConfigurationResponse(String widgetId) {
        return getQuery(String.format(Endpoints.MESSAGE_CONFIGURATIONS, widgetId), token.get());
    }
}