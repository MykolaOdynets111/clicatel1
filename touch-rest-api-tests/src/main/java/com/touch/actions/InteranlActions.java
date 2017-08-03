package com.touch.actions;

import com.touch.models.EndPointsClass;
import io.restassured.http.Header;
import io.restassured.response.Response;

/**
 * Created by oshcherbatyy on 19-07-17.
 */
public class InteranlActions {

    com.touch.engines.RequestEngine requestEngine;

    public InteranlActions(com.touch.engines.RequestEngine requestEngine) {
        this.requestEngine = requestEngine;
    }

    public Response getTenantSupportHours(String tenantName) {
        return requestEngine.getRequest(EndPointsClass.CHECK_SUPPORT_HOURS, tenantName);
    }
}
