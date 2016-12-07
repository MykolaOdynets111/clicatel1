package com.touch.actions;

import com.touch.models.EndPointsClass;
import com.touch.utils.TestingEnvProperties;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class ChatsActions {

    com.touch.engines.RequestEngine requestEngine;

    public ChatsActions(com.touch.engines.RequestEngine requestEngine) {
        this.requestEngine = requestEngine;
    }

    public Response getListOfAttachments() {
        return requestEngine.getRequest(EndPointsClass.CHATS_ATTACHMENTS);
    }

    public Response addAttachmentForSession(String sessionId, String fileName, File file) {
        Map<String, Object> formParameters = new HashMap<>();
        formParameters.put("session-id", sessionId);
        formParameters.put("fileName", fileName);
        return requestEngine.postRequestWithFormParametersAndFile(EndPointsClass.CHATS_ATTACHMENTS, null, formParameters, file);
    }

    public int deleteAttachment(String attachmentId) {
        return requestEngine.deleteRequest(EndPointsClass.CHATS_ATTACHMENT, attachmentId).getStatusCode();
    }

    public Response getAttachment(String attachmentId) {
        return requestEngine.getRequest(EndPointsClass.CHATS_ATTACHMENT, attachmentId);
    }

    public Response getChatRoom(String tenantId, String clientJid, String clientId, String token) {
        return requestEngine.getRequest(EndPointsClass.CHATS_ROOMS + "?tenantId=" + tenantId + "&clientJid=" + clientJid + "&clientId=" + clientId, new Header("Authorization", token));
    }

    public Response getListOfSessions(String tenantId, String clientId, String token) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("tenantId", tenantId);
        parameters.put("clientId", clientId);
        return requestEngine.getRequest(EndPointsClass.CHATS_SESSIONS + EndPointsClass.generateQueryPath(parameters), new Header("Authorization", token));
    }

    public Response addNewSession(String sessionId, String tenantId, String clientId, String token) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("sessionId", sessionId);
        parameters.put("tenantId", tenantId);
        parameters.put("clientId", clientId);
       return requestEngine.postRequestWithQueryParameters(EndPointsClass.CHATS_SESSIONS, null, parameters, new Header("Authorization", token));

    }

    public Response deleteSession(String sessionId, String token) {
        return requestEngine.deleteRequest(EndPointsClass.CHATS_SESSION, sessionId, null, new Header("Authorization", token));

    }

    public Response getSession(String sessionId, String token) {
        return requestEngine.getRequest(EndPointsClass.CHATS_SESSION, sessionId, new Header("Authorization", token));
    }

    public Response terminateSession(String sessionId) {
        return requestEngine.postRequest(EndPointsClass.CHATS_SESSION_TERMINATE,sessionId,null);
    }



}
