package com.touch.actions;

import com.touch.engines.RequestEngine;
import com.touch.models.EndPointsClass;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.util.HashMap;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class AppConfigActions {

    RequestEngine requestEngine;

    public AppConfigActions(RequestEngine requestEngine) {
        this.requestEngine = requestEngine;
    }

    public Response getProfileData(String token) {
        return requestEngine.getRequest(EndPointsClass.APP_PROFILE, new Header("Authorization", token));
    }

    public Response getXmppData(String token) {
        return requestEngine.getRequest(EndPointsClass.APP_XMPP, new Header("Authorization", token));
    }

}
