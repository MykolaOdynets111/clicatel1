package com.touch.actions;

import com.touch.engines.RequestEngine;
import com.touch.models.EndPointsClass;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.util.HashMap;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class RosterActions {

    RequestEngine requestEngine;

    public RosterActions(RequestEngine requestEngine) {
        this.requestEngine = requestEngine;
    }

    public Response getAgentsList(String rosterJid, String state, String canHandleMoreChats, String token) {
        HashMap<String, String> parameters = new HashMap<>();
        if (rosterJid != null)
            parameters.put("rosterJid", rosterJid);
        if (state != null)
            parameters.put("state", state);
        if (canHandleMoreChats != null)
            parameters.put("canHandleMoreChats", canHandleMoreChats);
        return requestEngine.getRequest(EndPointsClass.ROSTER_AGENTS + EndPointsClass.generateQueryPath(parameters), new Header("Authorization", token));
    }
    public Response getRoster(String rosterJid, String token) {
        HashMap<String, String> parameters = new HashMap<>();
        if (rosterJid != null)
            parameters.put("rosterJid", rosterJid);
        return requestEngine.getRequest(EndPointsClass.ROSTER + EndPointsClass.generateQueryPath(parameters), new Header("Authorization", token));
    }
    public Response getRosterList(String token) {
        return requestEngine.getRequest(EndPointsClass.ROSTERS, new Header("Authorization", token));
    }
    public Response putRoster(String rosterJid, String algorithm, String token) {
        HashMap<String, String> parameters = new HashMap<>();
        if (rosterJid != null)
            parameters.put("rosterJid", rosterJid);
        if (algorithm != null)
            parameters.put("algorithm", algorithm);
        return requestEngine.putRequest(EndPointsClass.ROSTER + EndPointsClass.generateQueryPath(parameters),null, new Header("Authorization", token));
    }
}
