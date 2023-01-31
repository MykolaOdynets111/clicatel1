package api.clients;

import api.MainApi;
import api.models.response.ApiKeysResponse;
import api.models.response.integrationresponse.IntegrationResponse;
import api.models.response.widgetresponse.Widget;
import api.models.response.widgetresponse.WidgetsContent;

import java.util.List;

import static api.clients.ApiHelperChat2Pay.getChat2PayQuery;

public class ApiHelperWidgets extends MainApi {

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
}
