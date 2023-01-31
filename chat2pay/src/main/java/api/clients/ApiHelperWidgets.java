package api.clients;

import api.models.request.WidgetBody;
import io.restassured.response.Response;


public class ApiHelperWidgets extends ApiHelperChat2Pay {

    public static Response createWidget(WidgetBody widgetBody) {
        return postQuery(Endpoints.WIDGETS_ENDPOINT, widgetBody, token.get());
    }

    public static Response deleteWidget(String widgetId) {
        return deleteQuery(Endpoints.WIDGETS_ENDPOINT + "/" + widgetId, token.get());
    }
}
