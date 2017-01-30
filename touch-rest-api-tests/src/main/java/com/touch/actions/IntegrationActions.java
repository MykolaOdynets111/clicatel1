package com.touch.actions;

import com.touch.models.EndPointsClass;
import io.restassured.http.Header;
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
    public Response getIntegrationsList(String artifactName, String integrationName, String type, String token) {
        HashMap<String, String> parameters = new HashMap<>();
        if (artifactName != null)
            parameters.put("artifactName", artifactName);
        if (integrationName != null)
            parameters.put("integrationName", integrationName);
        if (type != null)
            parameters.put("type", type);
        return requestEngine.getRequest(EndPointsClass.INTEGRATIONS + EndPointsClass.generateQueryPath(parameters), new Header("Authorization", token));
    }
    public Response getArtifactFileData(String name, String token) {
        return requestEngine.getRequest(EndPointsClass.INTEGRATION_ARTIFACT, name, new Header("Authorization", token));
    }
    public Response addNewIntegrationItem(String integrationType, File file, String token) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("integrationType", integrationType);
        return requestEngine.putRequestWithFormParametersAndFile(EndPointsClass.INTEGRATIONS,parameters, file, new Header("Authorization", token));
    }
    public Response deleteIntegrationItem(String artifactName, String token) {
        return requestEngine.deleteRequest(EndPointsClass.INTEGRATION,artifactName, new Header("Authorization", token));
    }


}
