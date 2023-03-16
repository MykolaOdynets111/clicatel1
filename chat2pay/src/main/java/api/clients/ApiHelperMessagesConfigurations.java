package api.clients;

import api.models.request.message.MessageBody;
import io.restassured.response.Response;


public class ApiHelperMessagesConfigurations extends ApiHelperChat2Pay {

    public static Response getMessageConfigurationResponse(String widgetId) {
        return getQuery(String.format(Endpoints.MESSAGE_CONFIGURATIONS, widgetId), token.get());
    }

    public static Response putMessageConfiguration(String widgetId, MessageBody messageBody) {
        return putQuery(token.get(), String.format(Endpoints.MESSAGE_CONFIGURATIONS, widgetId), messageBody);
    }

    public static Response getTemplateUsageResponse(String templateId) {
        return getQuery(String.format(Endpoints.TEMPLATE_USAGE, templateId), token.get());
    }
}