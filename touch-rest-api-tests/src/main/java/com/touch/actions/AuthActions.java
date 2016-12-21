package com.touch.actions;

import com.touch.engines.RequestEngine;
import com.touch.models.EndPointsClass;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class AuthActions {

    RequestEngine requestEngine;

    public AuthActions(com.touch.engines.RequestEngine requestEngine) {
        this.requestEngine = requestEngine;
    }

    public Response getListOfTockens(String token) {
        return requestEngine.getRequest(EndPointsClass.AUTH_TOCKEN,new Header("Authorization", token));
    }

    public Response addTocken(String device, String tenantId, String token) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("device", device);
        parameters.put("tenantId", tenantId);
        return requestEngine.postRequest(EndPointsClass.AUTH_TOCKEN+ EndPointsClass.generateQueryPath(parameters),null,null,new Header("Authorization", token));
    }





}
