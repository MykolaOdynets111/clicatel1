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

    public Response getAttachment(String attachmentId, String token) {
        return requestEngine.getRequest(EndPointsClass.CHATS_ATTACHMENT, attachmentId, new Header("Authorization", token));
    }

    public Response addAttachmentForSession(String sessionId, String fileName, File file, String token) {
        Map<String, Object> formParameters = new HashMap<>();
        if (sessionId != null)
            formParameters.put("session-id", sessionId);
        if (fileName != null)
            formParameters.put("fileName", fileName);
        return requestEngine.postRequestWithFormParametersAndFile(EndPointsClass.CHATS_ATTACHMENTS, null, formParameters, file, new Header("Authorization", token));
    }

    public int deleteAttachment(String attachmentId, String token) {
        return requestEngine.deleteRequest(EndPointsClass.CHATS_ATTACHMENT, attachmentId, new Header("Authorization", token)).getStatusCode();
    }

    public Response getAttachmentsList(String attachmentId, String sessionId, String fileType, String token) {
        Map<String, String> parameters = new HashMap<>();
        if (attachmentId != null)
            parameters.put("attachment-id", attachmentId);
        if (sessionId != null)
            parameters.put("session-id", sessionId);
        if (fileType != null)
            parameters.put("fileType", fileType);
        return requestEngine.getRequest(EndPointsClass.CHATS_ATTACHMENTS + EndPointsClass.generateQueryPath(parameters), new Header("Authorization", token));
    }

    public Response getChatRoom(String tenantId, String clientJid, String clientId, String app, String token) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("tenantId", tenantId);
        parameters.put("clientId", clientId);
        parameters.put("clientJid", clientJid);
        parameters.put("app", app);
        return requestEngine.postRequest(EndPointsClass.CHATS_ROOMS + EndPointsClass.generateQueryPath(parameters), null, null, new Header("Authorization", token));
    }

    public Response getListOfSessions(String tenantId, String clientId, String token) {
        Map<String, String> parameters = new HashMap<>();
        if(tenantId!=null)
        parameters.put("tenantId", tenantId);
        if(clientId!=null)
        parameters.put("clientId", clientId);
        return requestEngine.getRequest(EndPointsClass.CHATS_SESSIONS + EndPointsClass.generateQueryPath(parameters), new Header("Authorization", token));
    }
    public Response getListOfChatEvents(String sessionId, String clientId, String tenantId, String dateFrom, String dateTo, String token) {
        Map<String, String> parameters = new HashMap<>();
        if(sessionId!=null)
            parameters.put("sessionId", sessionId);
        if(clientId!=null)
            parameters.put("clientId", clientId);
        if(tenantId!=null)
            parameters.put("tenantId", tenantId);
        if(dateFrom!=null)
            parameters.put("dateFrom", dateFrom);
        if(dateTo!=null)
            parameters.put("dateTo", dateTo);
        return requestEngine.getRequest(EndPointsClass.CHATS_EVENTS + EndPointsClass.generateQueryPath(parameters), new Header("Authorization", token));
    }
    public Response getListOfChatHistory(String sessionId, String clientId, String tenantId, String dateFrom, String dateTo, String returnDisplayMessage, String token) {
        Map<String, String> parameters = new HashMap<>();
        if(sessionId!=null)
            parameters.put("sessionId", sessionId);
        if(clientId!=null)
            parameters.put("clientId", clientId);
        if(tenantId!=null)
            parameters.put("tenantId", tenantId);
        if(dateFrom!=null)
            parameters.put("dateFrom", dateFrom);
        if(dateTo!=null)
            parameters.put("dateTo", dateTo);
        if(returnDisplayMessage!=null)
            parameters.put("returnDisplayMessage", returnDisplayMessage);
        return requestEngine.getRequest(EndPointsClass.CHATS_HISTORIES + EndPointsClass.generateQueryPath(parameters), new Header("Authorization", token));
    }

    public Response addNewSession(String sessionId, String tenantId, String clientId, String token) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("sessionId", sessionId);
        parameters.put("tenantId", tenantId);
        parameters.put("clientId", clientId);
        return requestEngine.postRequestWithQueryParameters(EndPointsClass.CHATS_SESSIONS, null, parameters, new Header("Authorization", token));

    }

    public Response deleteSession(String sessionId, String token) {
        return requestEngine.deleteRequest(EndPointsClass.CHATS_SESSION, sessionId, new Header("Authorization", token));

    }

    public Response getSession(String sessionId, String token) {
        return requestEngine.getRequest(EndPointsClass.CHATS_SESSION, sessionId, new Header("Authorization", token));
    }

    public Response terminateSession(String sessionId) {
        return requestEngine.postRequest(EndPointsClass.CHATS_SESSION_TERMINATE, sessionId, null);
    }


}
