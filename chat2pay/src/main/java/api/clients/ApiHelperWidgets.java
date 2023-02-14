package api.clients;

import api.models.request.WidgetBody;
import api.models.response.ApiKeysResponse;
import api.models.response.integrationresponse.IntegrationResponse;
import api.models.response.widgetresponse.Widget;
import api.models.response.widgetresponse.WidgetsContent;
import io.restassured.response.Response;

import java.util.List;
import java.util.Objects;


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
        return getWidgets().stream()
                .filter(widget -> Objects.nonNull(widget.getName()))
                .filter(w -> w.getName().equals(widgetName)).findFirst()
                .orElseThrow(() -> new AssertionError("Widget didn't find"))
                .getId();
    }

    public static Response createWidget(WidgetBody widgetBody) {
        return postQuery(Endpoints.WIDGETS_ENDPOINT, widgetBody, token.get());
    }

    public static Response getWidget(String widgetId) {
        return getQuery(Endpoints.WIDGETS_ENDPOINT + "/" + widgetId, token.get());
    }

    public static Response updateWidget(String widgetId, Widget widgetBody) {
        return putQuery(Endpoints.WIDGETS_ENDPOINT + "/" + widgetId, widgetBody, token.get());
    }

    public static Response deleteWidget(String widgetId) {
        return deleteQuery(Endpoints.WIDGETS_ENDPOINT + "/" + widgetId, token.get());
    }

    public static Response updateShowLinkedApiForWidget(String widgetId, Widget widgetBody) {
        return putQuery(String.format(Endpoints.WIDGET_SHOWED_LINKED_API_ENDPOINT, widgetId), widgetBody, token.get());
    }

    private static List<Widget> getWidgets() {
        return getChat2PayQuery(Endpoints.EXISTED_WIDGETS_ENDPOINT).as(WidgetsContent.class).getWidgets();
    }
}