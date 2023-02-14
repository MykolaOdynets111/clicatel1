package api.clients;

import api.models.Message;
import io.restassured.response.Response;


public class ApiHelperMessagesConfigurations extends ApiHelperChat2Pay {

    public static Response getMessageConfigurationResponse(String widgetId) {
        return getQuery(String.format(Endpoints.MESSAGE_CONFIGURATIONS, widgetId), token.get());
    }

    public static Response putMessageConfiguration(String widgetId, Message messageBody) {
        return putQuery((String.format(Endpoints.MESSAGE_CONFIGURATIONS, widgetId)), messageBody, token.get());
    }
}