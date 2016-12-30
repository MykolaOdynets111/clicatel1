package com.touch.actions;

import com.touch.engines.RequestEngine;
import com.touch.models.EndPointsClass;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CardsActions {

    RequestEngine requestEngine;

    public CardsActions(RequestEngine requestEngine) {
        this.requestEngine = requestEngine;
    }

    public Response getAllCards(String name, String platform, String token) {
        Map<String, String> parameters = new HashMap<>();
        if (!name.isEmpty())
            parameters.put("name", name);
        if (!platform.isEmpty())
            parameters.put("platform", platform);
        return requestEngine.getRequest(EndPointsClass.CARDS + EndPointsClass.generateQueryPath(parameters), new Header("Authorization", token));
    }

    public Response getCard(String name, String platform, String version, String token) {
        Map<String, String> parameters = new HashMap<>();
        if (!platform.isEmpty())
        parameters.put("platform", platform);
        if (!version.isEmpty())
        parameters.put("version", version);
        return requestEngine.getRequest(EndPointsClass.CARD + EndPointsClass.generateQueryPath(parameters), name, new Header("Authorization", token));
    }

    public Response addCard(String platform, String cardName, String description, String tenantId, String width, String height,File file, String token) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("platform", platform);
        parameters.put("cardName", cardName);
        if (description != null)
            parameters.put("description", description);
        if (tenantId != null)
            parameters.put("tenantId", tenantId);
        if (width != null)
            parameters.put("width", width);
        if (height != null)
            parameters.put("height", height);

        return requestEngine.postRequestWithFormParametersAndFile(EndPointsClass.CARDS, null, parameters, file, new Header("Authorization", token), new Header("Content-Type", "multipart/form-data"));
    }

    public Response deleteCard(String name, String platform, String version, String token) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("platform", platform);
        parameters.put("version", version);
        return requestEngine.deleteRequest(EndPointsClass.CARD + EndPointsClass.generateQueryPath(parameters), name, new Header("Authorization", token));
    }

    public Response updateCardInfo(String name, String platform, String version, String width, String height, String token) {
        Map<String, String> parameters = new HashMap<>();
        if (!platform.isEmpty())
        parameters.put("platform", platform);
        if (!version.isEmpty())
        parameters.put("version", version);
        if (!width.isEmpty())
        parameters.put("width", width);
        if (!height.isEmpty())
        parameters.put("height", height);
        return requestEngine.putRequest(EndPointsClass.CARD_BUNDLE + EndPointsClass.generateQueryPath(parameters), name, new Header("Authorization", token));
    }


}
