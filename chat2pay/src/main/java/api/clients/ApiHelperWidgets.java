package api.clients;

import api.MainApi;
import api.models.request.WidgetBody;
import api.models.response.ApiKeysResponse;
import api.models.response.integrationresponse.IntegrationResponse;
import api.models.response.widgetresponse.Widget;
import api.models.response.widgetresponse.WidgetsContent;
import io.restassured.response.Response;

import java.util.List;

import static api.clients.ApiHelperChat2Pay.getChat2PayQuery;

public class ApiHelperWidgets extends ApiHelperChat2Pay {

    public static IntegrationResponse getIntegrationResponse(String widgetId) {
        return getChat2PayQuery(String.format(Endpoints.WIDGET_INTEGRATION_ENDPOINT, widgetId))
                .jsonPath()
                .getList("", IntegrationResponse.class)
                .stream().findAny()
                .orElseThrow(() -> new AssertionError("No integrations found for widget" + widgetId));
    }

    public static ApiKeysResponse getActivationKey(String widgetId) {
        return getChat2PayQuery(String.format(Endpoints.WIDGET_API_KEYS_ENDPOINT, widgetId))
                .jsonPath()
                .getList("", ApiKeysResponse.class)
                .stream().findAny()
                .orElseThrow(() -> new AssertionError("No activation key found for widget" + widgetId));
    }

    public static String getWidgetId(String widgetName) {
        List<Widget> widgets = getChat2PayQuery(Endpoints.EXISTED_WIDGETS_ENDPOINT)
                .as(WidgetsContent.class)
                .getWidgets();
        return widgets.stream().filter(w -> w.getName().equals(widgetName)).findFirst()
                .orElseThrow(() -> new AssertionError("Widget didn't find"))
                .getId();
    }
    public static Response createWidget(WidgetBody widgetBody) {
        return postQuery(Endpoints.WIDGETS_ENDPOINT, widgetBody, token.get());
    }

    public static Response deleteWidget(String widgetId) {
        return deleteQuery(Endpoints.WIDGETS_ENDPOINT + "/" + widgetId, token.get());
    }

}
