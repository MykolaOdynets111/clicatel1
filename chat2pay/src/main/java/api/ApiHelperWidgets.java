package api;

import data.models.request.WidgetBody;
import data.models.response.widget.Widget;
import data.models.response.widget.WidgetsContent;
import data.models.response.widgetconfigurations.ApiKeysResponse;
import io.restassured.response.Response;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static data.enums.WidgetsInfo.GENERAL_WIDGET_NAME;


public class ApiHelperWidgets extends ApiHelperChat2Pay {

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
                .filter(w -> w.getName().startsWith(widgetName)).findFirst()
                .orElseThrow(() -> new AssertionError("Widget couldn't be found"))
                .getId();
    }

    public static Response createWidget(WidgetBody widgetBody) {
        return postQuery(token.get(), Endpoints.WIDGETS_ENDPOINT, widgetBody);
    }

    public static Response getWidget(String widgetId) {
        return getQuery(token.get(), Endpoints.WIDGETS_ENDPOINT + "/" + widgetId);
    }

    public static Response updateWidget(String widgetId, Widget widgetBody) {
        return putQuery(token.get(), Endpoints.WIDGETS_ENDPOINT + "/" + widgetId, widgetBody);
    }

    public static Response updateShowLinkedApiForWidget(String widgetId, Widget widgetBody) {
        return putQuery(token.get(), String.format(Endpoints.WIDGET_SHOWED_LINKED_API_ENDPOINT, widgetId), widgetBody);
    }

    public static WidgetsContent getWidgetsContent() {
        return getChat2PayQuery(Endpoints.EXISTED_WIDGETS_ENDPOINT).as(WidgetsContent.class);
    }

    private static List<Widget> getWidgets() {
        return getChat2PayQuery(Endpoints.EXISTED_WIDGETS_ENDPOINT).as(WidgetsContent.class).getWidgets();
    }

    public static Response deleteWidget(String widgetId) {
        return deleteQuery(token.get(), Endpoints.WIDGETS_ENDPOINT + "/" + widgetId);
    }
    public static void deleteAllGeneratedWidgets() {
        getWidgets().stream().filter(Objects::nonNull)
                .filter(w -> w.name == null).collect(Collectors.toList()).forEach(w -> deleteWidget(w.id));
        getWidgets().stream()
                .filter(Objects::nonNull)
                .filter(w -> w.name.startsWith(GENERAL_WIDGET_NAME.name))
                .forEach(w -> deleteWidget(w.id));
    }
}
