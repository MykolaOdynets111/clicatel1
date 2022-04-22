package com.touch.actions;

import com.touch.models.EndPointsClass;
import com.touch.models.touch.chats.ChatPrivateHistoryRequest;
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

    public Response addAttachmentForSession(String sessionId,String roomJid, String userId,String userType, String tenantId, File file, String token) {
        Map<String, Object> formParameters = new HashMap<>();
        if (sessionId != null)
            formParameters.put("sessionId", sessionId);
        if (roomJid != null)
            formParameters.put("roomJid", roomJid);
        if (userId != null)
            formParameters.put("userId", userId);
        if (userType != null)
            formParameters.put("userType", userType);
        if (tenantId != null)
            formParameters.put("tenantId", tenantId);
        return requestEngine.postRequestWithFormParametersAndFile(EndPointsClass.CHATS_ATTACHMENTS, null, formParameters, file, new Header("Authorization", token));
    }

    public int deleteAttachment(String attachmentId, String token) {
        return requestEngine.deleteRequest(EndPointsClass.CHATS_ATTACHMENT, attachmentId, new Header("Authorization", token)).getStatusCode();
    }

    public Response getAttachmentsList(String sessionId, String roomJid, String userId, String tenantId, String fileType, String token) {
        Map<String, String> parameters = new HashMap<>();
        if (sessionId != null)
            parameters.put("sessionId", sessionId);
        if (roomJid != null)
            parameters.put("roomJid", roomJid);
        if (userId != null)
            parameters.put("userId", userId);
        if (tenantId != null)
            parameters.put("tenantId", tenantId);
        if (fileType != null)
            parameters.put("fileType", fileType);
        return requestEngine.getRequest(EndPointsClass.CHATS_ATTACHMENTS + EndPointsClass.generateQueryPath(parameters), new Header("Authorization", token));
    }
    public Response getAttachmentWithFileName(String attachmentId, String fileName, String token) {
        return requestEngine.getRequest(EndPointsClass.CHATS_ATTACHMENT_WITH_NAME, attachmentId, fileName,null, new Header("Authorization", token));
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
    public Response getListOfChatHistoryForSession(String sessionId, String token) {
       return requestEngine.getRequest(EndPointsClass.CHATS_HISTORY, sessionId, new Header("Authorization", token));
    }
    public Response getListOfInvites(String status, String dateFrom, String dateTo, String token) {
        Map<String, String> parameters = new HashMap<>();
        if(status!=null)
            parameters.put("status", status);
        if(dateFrom!=null)
            parameters.put("dateFrom", dateFrom);
        if(dateTo!=null)
            parameters.put("dateTo", dateTo);
        return requestEngine.getRequest(EndPointsClass.CHATS_INVITES+ EndPointsClass.generateQueryPath(parameters), new Header("Authorization", token));
    }
    public Response getListOfInvitesFromArchive(String nickname, String page, String count, String token) {
        Map<String, String> parameters = new HashMap<>();
        if(nickname!=null)
            parameters.put("nickname", nickname);
        if(page!=null)
            parameters.put("page", page);
        if(count!=null)
            parameters.put("count", count);
        return requestEngine.getRequest(EndPointsClass.CHATS_INVITE_ARCHIVE + EndPointsClass.generateQueryPath(parameters), new Header("Authorization", token));
    }
    public Response getInviteForSession(String sessionId, String token) {
        return requestEngine.getRequest(EndPointsClass.CHATS_INVITE,sessionId, new Header("Authorization", token));
    }

    public Response getListOfPrivateHistories(String to, String fromTs, String toTs, String token) {
        Map<String, String> parameters = new HashMap<>();
        if(to!=null)
            parameters.put("to", to);
        if(fromTs!=null)
            parameters.put("fromTs", fromTs);
        if(toTs!=null)
            parameters.put("toTs", toTs);
        return requestEngine.getRequest(EndPointsClass.CHATS_PRIVATE_HISTORY + EndPointsClass.generateQueryPath(parameters), new Header("Authorization", token));
    }
    public Response addNewPrivateHistory(ChatPrivateHistoryRequest privateHistory, String token) {

        return requestEngine.postRequest(EndPointsClass.CHATS_PRIVATE_HISTORY, null, null, privateHistory, new Header("Authorization", token));

    }
    public Response deleteSession(String sessionId, String token) {
        return requestEngine.deleteRequest(EndPointsClass.CHATS_SESSION, sessionId, new Header("Authorization", token));

    }

    public Response getSession(String sessionId, String token) {
        return requestEngine.getRequest(EndPointsClass.CHATS_SESSION, sessionId, new Header("Authorization", token));
    }

    public Response terminateSession(String sessionId, String token) {
        return requestEngine.postRequest(EndPointsClass.CHATS_SESSION_TERMINATE, sessionId, null, new Header("Authorization", token));
    }
    public Response terminateAllSessions(String clientId, String token) {
        return requestEngine.postRequest(EndPointsClass.CHATS_SESSION_TERMINATE_ALL, clientId, null, new Header("Authorization", token));
    }

}
