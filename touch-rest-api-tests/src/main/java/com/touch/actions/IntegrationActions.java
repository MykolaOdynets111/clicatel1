package com.touch.actions;

import com.touch.models.EndPointsClass;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class IntegrationActions {

    com.touch.engines.RequestEngine requestEngine;

    public IntegrationActions(com.touch.engines.RequestEngine requestEngine) {
        this.requestEngine = requestEngine;
    }


    public Response callGivenAction(String name, String action, String params) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("params", params);
       return requestEngine.postRequestWithFormParameters(EndPointsClass.INTEGRATION_CALL, name, action, parameters, "application/x-www-form-urlencoded");

    }





}
