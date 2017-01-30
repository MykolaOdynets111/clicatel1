package com.touch.actions;

import com.touch.engines.RequestEngine;
import com.touch.models.EndPointsClass;
import com.touch.models.touch.auth.TokenBase;
import com.touch.models.touch.auth.TokenDto;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.util.HashMap;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class AnalyticsActions {

    RequestEngine requestEngine;

    public AnalyticsActions(RequestEngine requestEngine) {
        this.requestEngine = requestEngine;
    }

    public Response getAnalyticsData(String tenantId, String date, String timeZone, String token) {
        HashMap<String, String> parameters = new HashMap<>();
        if (tenantId != null)
            parameters.put("tenantId", tenantId);
        if (tenantId != null)
            parameters.put("date", date);
        if (tenantId != null)
            parameters.put("timeZone", timeZone);
        return requestEngine.getRequest(EndPointsClass.ANALYTICS + EndPointsClass.generateQueryPath(parameters), new Header("Authorization", token));
    }

}
