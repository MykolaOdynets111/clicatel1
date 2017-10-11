package com.touch.tests;


import com.clickatell.touch.tbot.config.XmppClientConfigBean;
import com.clickatell.touch.tbot.xmpp.XmppClient;
import com.touch.models.ErrorMessage;
import com.touch.models.touch.agent.AgentCredentialsDto;
import com.touch.models.touch.auth.AccessTokenRequest;
import com.touch.models.touch.chats.*;
import com.touch.utils.StringUtils;
import com.touch.utils.TestingEnvProperties;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tigase.jaxmpp.core.client.BareJID;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.List;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class ChatsTests extends BaseTestClass {
    String sessionId;
    String file = getFullPathToFile("TenantResources/tenant_logo.jpg");
    String clickatellId=TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.id");
    List<ChatSessionResponse> chatSessions;
    String clientJid = "testclient1@clickatelllabs.com";
    String testClientId = "test1";
    String chatToken;
    String accessToken;
    ChatRoomResponse chatRoom;


    @BeforeClass
    public void beforeClass() {
//        token = getToken();
//        testTenant = getTestTenant1();
        chatToken = getToken(TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.login"), TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.password"));
        testToken = getToken(TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.email"), TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.password"));
        String refreshToken = authActions.getRefreshToken(chatToken);
        accessToken = authActions.getAccessToken(new AccessTokenRequest(), refreshToken);
        chatRoom = chatsActions.getChatRoom(clickatellId, clientJid, testClientId, "webchat", accessToken).as(ChatRoomResponse.class);
        generateMessageForChatRoom(chatRoom, testClientId);
        sessionId = chatsActions.getListOfSessions(clickatellId, testClientId, chatToken).as(ListChatSessionResponse.class).getChatSessions().get(0).getSessionId();
        chatSessions = chatsActions.getListOfSessions(clickatellId, null, chatToken).as(ListChatSessionResponse.class).getChatSessions();
    }


    @Test
    public void getNewChatRoom() {
        String tenantWithBot = getTestTenant1().getId();
        ChatRoomResponse chatRoom = chatsActions.getChatRoom(tenantWithBot, "testclient1@clickatelllabs.com", "test1", "webchat", accessToken).as(ChatRoomResponse.class);
        Assert.assertTrue(chatRoom.getChatroomJid().matches(".{32}@muc.clickatelllabs.com"));

    }

    @Test(dataProvider = "chatRoomsParameters")
    public void tryToGetNewChatRoomWithDifferentData(String tenantId, String clientJid, String clientId, int statusCode) {
        if (tenantId.equals("correct"))
            tenantId = getTestTenant1().getId();
        Assert.assertEquals(chatsActions.getChatRoom(tenantId, clientJid, clientId, "webchat", accessToken).getStatusCode(), statusCode);

    }



    @Test
    public void terminateSession() {
//terminate not existing session
        Assert.assertEquals(chatsActions.terminateSession("testSession" + StringUtils.generateRandomString(10), chatToken).getStatusCode(), 404);
        ChatRoomResponse chatRoom = chatsActions.getChatRoom(clickatellId, clientJid, testClientId, "webchat", accessToken).as(ChatRoomResponse.class);
        generateMessageForChatRoom(chatRoom, testClientId);
        String sessionId = chatsActions.getListOfSessions(clickatellId, testClientId, chatToken).as(ListChatSessionResponse.class).getChatSessions().get(0).getSessionId();
        ChatSessionResponse session = chatsActions.getSession(sessionId,chatToken).as(ChatSessionResponse.class);
//        terminate existing session
        Response response = chatsActions.terminateSession(sessionId, chatToken);
        Assert.assertEquals(response.getStatusCode(), 200);
        ChatSessionResponse terminatedSession = response.as(ChatSessionResponse.class);
        Assert.assertEquals(terminatedSession.getId(), session.getId());
        Assert.assertEquals(terminatedSession.getClientId(), session.getClientId());
        Assert.assertEquals(terminatedSession.getState(), "TERMINATED");
        Assert.assertTrue(terminatedSession.getEndedDate() != null);
    }

    @Test
    public void terminateAllSessions() {
        String testClientId = "testClientId";
//terminate not existing session
        Response response = chatsActions.terminateAllSessions("testSession" + StringUtils.generateRandomString(10), chatToken);
        Assert.assertEquals(response.getStatusCode(), 200);
        List<ChatSessionResponse> terminatedChatSessions = response.as(ListChatSessionResponse.class).getChatSessions();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(terminatedChatSessions.size(), 0);
        ChatRoomResponse chatRoom1 = chatsActions.getChatRoom(clickatellId, clientJid, testClientId, "webchat", accessToken).as(ChatRoomResponse.class);
        generateMessageForChatRoom(chatRoom1, testClientId);
        List<ChatSessionResponse> allChatSessions = chatsActions.getListOfSessions(clickatellId, testClientId, chatToken).as(ListChatSessionResponse.class).getChatSessions();
        ChatSessionResponse activeChatSessionForTestClient = null;
        for (ChatSessionResponse chatSessionResponse: allChatSessions){
            if (chatSessionResponse.getState().equals("ACTIVE") && chatSessionResponse.getClientId().equals(testClientId) )
                activeChatSessionForTestClient = chatSessionResponse;
        }

//        terminate existing session
        response = chatsActions.terminateAllSessions(activeChatSessionForTestClient.getClientId(), chatToken);
        Assert.assertEquals(response.getStatusCode(), 200);
        terminatedChatSessions = response.as(ListChatSessionResponse.class).getChatSessions();

        Assert.assertEquals(terminatedChatSessions.get(0).getId(), activeChatSessionForTestClient.getId());
        Assert.assertEquals(terminatedChatSessions.get(0).getState(), "TERMINATED");
        Assert.assertTrue(terminatedChatSessions.get(0).getEndedDate() != null);

    }

    @Test
    public void getSessionDataWithWrongSessionId() {
        String sessionId = "wrong_sessionId";
        String expectedMessage = "ChatSession with id " + sessionId + " not found";
//        Verify error message which we get when we try to get session data with wrong sessionId
        Assert.assertTrue(chatsActions.getSession(sessionId, chatToken).as(ErrorMessage.class).getErrorMessage().matches(expectedMessage));

    }


    @Test
    public void getNewChatRoomWithWrongData() {
        // verify error message when we try to get chat room for not existing tenant
        Assert.assertTrue(chatsActions.getChatRoom("wrong_tenantId", "testclient1@clickatelllabs.com", "test1", "webchat", chatToken).as(ErrorMessage.class).getErrorMessage().matches("This request requires HTTP authentication"));
    }

    @Test(dataProvider = "getAttachments")
    public void getChatsAttachments(String sessionId, String roomJid, String userId, String tenantId, String fileType, int statusCode) {
        if (sessionId != null && sessionId.equals("correct"))
            sessionId = this.sessionId;
        if (roomJid != null && roomJid.equals("correct"))
            roomJid = this.chatRoom.getChatroomJid();
        if (userId != null && userId.equals("correct"))
            userId = this.testClientId;
        if (tenantId != null && tenantId.equals("correct"))
            tenantId = clickatellId;
        if (fileType != null && fileType.equals("correct"))
            fileType = "image/jpeg";
        Response getAttachmentResponse = chatsActions.getAttachmentsList(sessionId, roomJid, userId, tenantId, fileType, chatToken);
        Assert.assertEquals(getAttachmentResponse.getStatusCode(), statusCode);
        if (getAttachmentResponse.getStatusCode() == 200) {
            List<AttachmentResponse> attachmentsList = getAttachmentResponse.as(ListAttachmentResponse.class).getAttachments();
        }
    }

    @Test(dataProvider = "addAttachments")
    public void addChatAttachments(String sessionId, String roomJid, String userId, String tenantId, String userType, int statusCode) {
        if (sessionId != null && sessionId.equals("correct"))
            sessionId = this.sessionId;
        if (roomJid != null && roomJid.equals("correct"))
            roomJid = this.chatRoom.getChatroomJid();
        if (userId != null && userId.equals("correct"))
            userId = this.testClientId;
        if (tenantId != null && tenantId.equals("correct"))
            tenantId = clickatellId;
        Response response = chatsActions.addAttachmentForSession(sessionId, roomJid, userId, userType, tenantId, new File(file), chatToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (response.getStatusCode() == 200) {
            AttachmentCreateResponse addedAttachment = response.as(AttachmentCreateResponse.class);
            Assert.assertEquals(chatsActions.deleteAttachment(addedAttachment.getId(), chatToken), 200);
        }
    }

    @Test(dataProvider = "getDeleteAttachments")
    public void getChatAttachment(String attachmentId, int statusCode) throws Exception {
        AttachmentCreateResponse testAttachment = chatsActions.addAttachmentForSession(sessionId, chatRoom.getChatroomJid(), testClientId, "AGENT", clickatellId, new File(file), testToken).as(AttachmentCreateResponse.class);
        if (attachmentId.equals("correct"))
            attachmentId = testAttachment.getId();

        Response response = chatsActions.getAttachment(attachmentId, chatToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200)
            Assert.assertTrue(isEqualInputStreams(response.asInputStream(), new FileInputStream(new File(file))));
        Assert.assertEquals(chatsActions.deleteAttachment(testAttachment.getId(), chatToken), 200);
    }

    @Test(dataProvider = "getAttachmentsWithFileName")
    public void getChatAttachmentWithName(String attachmentId, String fileName, int statusCode) throws Exception {
        AttachmentCreateResponse testAttachment = chatsActions.addAttachmentForSession(sessionId, chatRoom.getChatroomJid(), testClientId, "AGENT", clickatellId, new File(file), chatToken).as(AttachmentCreateResponse.class);
        if (attachmentId.equals("correct"))
            attachmentId = testAttachment.getId();
        if (fileName.equals("correct"))
            fileName = testAttachment.getFileName();
        Response response = chatsActions.getAttachmentWithFileName(attachmentId, fileName, chatToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200)
            Assert.assertTrue(isEqualInputStreams(response.asInputStream(), new FileInputStream(new File(file))));
        Assert.assertEquals(chatsActions.deleteAttachment(testAttachment.getId(), chatToken), 200);
    }

    @Test(dataProvider = "getDeleteAttachments")
    public void deleteChatAttachment(String attachmentId, int statusCode) {
        if (attachmentId.equals("correct"))
            attachmentId = chatsActions.addAttachmentForSession(sessionId, chatRoom.getChatroomJid(), testClientId, "AGENT", clickatellId, new File(file), chatToken).as(AttachmentCreateResponse.class).getId();
        Assert.assertEquals(chatsActions.deleteAttachment(attachmentId, chatToken), statusCode);
    }

    @Test(dataProvider = "getEvents")
    public void getChatsEvents(String sessionId, String clientId, String tenantId, String dateFrom, String dateTo, boolean isEmpty, int statusCode) {
        ChatSessionResponse session = chatSessions.get(chatSessions.size() - 1);
        if (sessionId != null && sessionId.equals("correct"))
            sessionId = session.getSessionId();
        if (clientId != null && clientId.equals("correct"))
            clientId = session.getClientId();
        if (tenantId != null && tenantId.equals("correct"))
            tenantId = session.getTenantId();
        if (dateFrom != null && dateFrom.equals("correct"))
            dateFrom = session.getStartedDate().toString();
        if (dateTo != null && dateTo.equals("correct"))
            if (session.getEndedDate() != null)
                dateTo = session.getEndedDate().toString();
        Response response = chatsActions.getListOfChatEvents(sessionId, clientId, tenantId, dateFrom, dateTo, chatToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            List<ChatEventResponseV5> chatEvents = response.as(ListChatEventResponseV5.class).getChatEvents();
            Assert.assertEquals(chatEvents.isEmpty(), isEmpty);
        }
    }

    @Test(dataProvider = "getHistory")
    public void getChatsHistory(String sessionId, String clientId, String tenantId, String dateFrom, String dateTo, boolean returnDisplayMessage, int statusCode) {
        ChatSessionResponse session = chatSessions.get(chatSessions.size() - 1);
        if (sessionId != null && sessionId.equals("correct"))
            sessionId = session.getSessionId();
        if (clientId != null && clientId.equals("correct"))
            clientId = session.getClientId();
        if (tenantId != null && tenantId.equals("correct"))
            tenantId = session.getTenantId();
        if (dateFrom != null && dateFrom.equals("correct"))
            dateFrom = session.getStartedDate().toString();
        if (dateTo != null && dateTo.equals("correct"))
            if (session.getEndedDate() != null)
                dateTo = session.getEndedDate().toString();
        Response response = chatsActions.getListOfChatHistory(sessionId, clientId, tenantId, dateFrom, dateTo, String.valueOf(returnDisplayMessage), chatToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            List<ChatHistoryRecordResponse> records = response.as(ListChatHistoryRecordsResponse.class).getRecords();
        }
    }



    @Test(dataProvider = "getHistorySession")
    public void getChatsHistoryForSession(String sessionId, boolean isEmpty, int statusCode) {
        ChatSessionResponse session = chatSessions.get(chatSessions.size() - 1);
        if (sessionId != null && sessionId.equals("correct"))
            sessionId = session.getSessionId();

        Response response = chatsActions.getListOfChatHistoryForSession(sessionId, chatToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            List<ChatHistoryRecordResponse> records = response.as(ListChatHistoryRecordsResponse.class).getRecords();
        }
    }

    @Test(dataProvider = "invitesList")
    public void getInvites(String status, String dateFrom, String dateTo, int statusCode) {

        Response response = chatsActions.getListOfInvites(status, dateFrom, dateTo, chatToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            List<ChatInviteHistoryResponseV5> records = response.as(ListChatInviteHistoryResponseV5.class).getChatInviteHistory();
            Assert.assertFalse(records.isEmpty());
        }
    }

    @Test(dataProvider = "invitesListArchive")
    public void getInvitesFromArchive(String nickname, String page, String count, int statusCode) {
        if (nickname != null && nickname.equals("correct"))
            nickname = agentActions.getCredentials(chatToken, AgentCredentialsDto.class).getJid().split("@")[0];
        Response response = chatsActions.getListOfInvitesFromArchive(nickname, page, count, chatToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            List<ChatInviteHistoryArchiveResponse> records = response.as(ListChatInviteHistoryArchiveResponse.class).getChatInviteHistory();
        }
    }

    @Test(dataProvider = "getInvitesSession")
    public void getInvitesForSession(String sessionId, boolean isEmpty, int statusCode) {
        ChatSessionResponse session = chatSessions.get(chatSessions.size() - 1);
        if (sessionId != null && sessionId.equals("correct"))
            sessionId = session.getSessionId();
        Response response = chatsActions.getInviteForSession(sessionId, chatToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            List<ChatInviteHistoryResponseV5> records = response.as(ListChatInviteHistoryResponseV5.class).getChatInviteHistory();
//            Assert.assertEquals(records.isEmpty(),isEmpty);
        }
    }

    @Test(dataProvider = "addPrivateH")
    public void addPrivateHistory(String to, String message, int statusCode) {
        if (to.equals("correct"))
            to = chatRoom.getChatroomJid();
        ChatPrivateHistoryRequest historyRequest = new ChatPrivateHistoryRequest(message, to);
        Response response = chatsActions.addNewPrivateHistory(historyRequest, chatToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
    }

    @Test(dataProvider = "getPrivateH")
    public void getPrivateHistory(String to, String fromTs, String toTs, int statusCode) {
        if (to.equals("correct")) {
            to = chatRoom.getChatroomJid();
            ChatPrivateHistoryRequest historyRequest = new ChatPrivateHistoryRequest("test", to);
            chatsActions.addNewPrivateHistory(historyRequest, chatToken);
        }
        Response response = chatsActions.getListOfPrivateHistories(to, fromTs, toTs, chatToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            List<ChatPrivateHistoryResponse> records = response.as(ChatPrivateHistoryList.class).getRecords();
            Assert.assertFalse(records.isEmpty());
        }
    }

    @DataProvider
    private static Object[][] addPrivateH() {
        return new Object[][]{
                {"correct", "test1", 201},
                {"test", "test1", 400},
                {"test", "", 400},
                {"", "", 400},
                {"", "test", 400},
        };
    }

    @DataProvider
    private static Object[][] getPrivateH() {
        return new Object[][]{
                {"correct", "0", "11111111111111111", 200},
                {"correct", "0", "", 200},
                {"correct", "", "11111111111111111", 400},
                {"correct", "0", "111111111111111112", 200},
                {"correct", "0", "test", 400},
                {"correct", "test", "", 400},
                {"correct", "111111111111111112", "11111111111111111", 400},
                {"test", "0", "11111111111111111", 400},
                {"test", "0", "", 400},
                {"test", "", "11111111111111111", 400},
        };
    }

    @DataProvider
    private static Object[][] invitesListArchive() {
        return new Object[][]{
                {"correct", "1", "5", 200},
                {"correct", "2", "5", 200},
                {"correct", "1", "10", 200},
                {"correct", "tt", "5", 400},
                {"correct", "2", "tt", 400},
                {"correct", "0", "5", 400},
                {"test", "2", "5", 200},
                {"", "2", "5", 400},
                {null, "2", "5", 400},
                {"correct", "", "5", 200},
                {"correct", "2", "", 200},
                {"correct", null, "5", 200},
                {"correct", "2", null, 200},


        };
    }

    @DataProvider
    private static Object[][] invitesList() {
        return new Object[][]{
                {"JOINED", "0", "2288442453000", 200},
                {"LEFT", "0", "2288442453000", 200},
                {"LEFT", "ttt", "2288442453000", 400},
                {"LEFT", "0", "ttt", 400},
                {"MC2RatingTest", "0", "2288442453000", 400},
                {"", "0", "2288442453000", 200},
                {null, "0", "2288442453000", 200},
                {"LEFT", null, "2288442453000", 200},
                {"LEFT", "0", null, 200},
                {"LEFT", "11111111111111111111", "2288442453000", 400},
                {"LEFT", "0", "111111111111111111111", 400},
                {"", "", "", 200},
                {null, null, null, 200},
        };
    }

    @DataProvider
    private static Object[][] getInvitesSession() {
        return new Object[][]{
                {"test", true, 200},
                {"correct", false, 200},
                {"", false, 200}
        };
    }

    @DataProvider
    private static Object[][] getHistorySession() {
        return new Object[][]{
                {"test", true, 200},
                {"correct", false, 200},
                {"", true, 400}
        };
    }

    @DataProvider
    private static Object[][] getHistory() {
        return new Object[][]{
                {"", "correct", "correct", "correct", "", false, 200},
                {"correct", "", "", "correct", "", false, 200},
                {"correct", null, null, "correct", null, false, 200},
                {null, "correct", "correct", "correct", null, false, 200},
                {null, "correct", "correct", "correct", null, false, 200},
                {"", "correct", "correct", "correct", "", true, 200},
                {"correct", "", "", "correct", "", true, 200},
                {"correct", null, null, "correct", null, true, 200},
                {null, "correct", "correct", "correct", null, true, 200},
                {null, "correct", "correct", "correct", null, true, 200},
                {"test", null, null, "correct", null, true, 200},
                {null, "correct", "test", "correct", null, true, 401},
                {null, "test", "test", "correct", null, true, 401},
                {"correct", "correct", "correct", "correct", "correct", false, 400},
                {"correct", "correct", "correct", "correct", "", false, 400},
                {"correct", "correct", "correct", "", "", true, 400},
                {"correct", "correct", "", "", "", true, 400},
                {"correct", "correct", "correct", "correct", null, false, 400},
                {"correct", "correct", "correct", null, null, true, 400},
                {"correct", "correct", null, null, null, true, 400},
                {"notExist", "notExist", "notExist", "notExist", "notExist", true, 400},
                {"notExist", "notExist", "notExist", "11", "11", true, 400},
                {"notExist", "notExist", "notExist", "notExist", "11", true, 400},
                {"correct", "correct", "correct", "test1", null, false, 400},
                {"correct", "correct", "correct", "11", null, false, 400},
                {"correct", "correct", "test", null, null, true, 400},
                {"correct", "test", null, null, null, true, 400},

        };
    }

    @DataProvider
    private static Object[][] getEvents() {
        return new Object[][]{
                {"", "correct", "correct", "", "", false, 200},
                {"correct", "", "", "", "", false, 200},
                {"correct", null, null, null, null, false, 200},
                {null, "correct", "correct", null, null, false, 200},
                {"test", null, null, null, null, true, 200},
                {null, "correct", "test", null, null, true, 401},
                {null, "test", "test", null, null, true, 401},
                {"correct", "correct", "correct", "correct", "correct", false, 400},
                {"correct", "correct", "correct", "correct", "", false, 400},
                {"correct", "correct", "correct", "", "", true, 400},
                {"correct", "correct", "", "", "", true, 400},
                {"correct", "correct", "correct", "correct", null, false, 400},
                {"correct", "correct", "correct", null, null, true, 400},
                {"correct", "correct", null, null, null, true, 400},
                {"notExist", "notExist", "notExist", "notExist", "notExist", true, 400},
                {"notExist", "notExist", "notExist", "11", "11", true, 400},
                {"notExist", "notExist", "notExist", "notExist", "11", true, 400},
                {"correct", "correct", "correct", "test1", null, false, 400},
                {"correct", "correct", "correct", "11", null, false, 400},
                {"correct", "correct", "test", null, null, true, 400},
                {"correct", "test", null, null, null, true, 400},

        };
    }

    @DataProvider
    private static Object[][] getDeleteAttachments() {
        return new Object[][]{
                {"test", 404},
                {"correct", 200},
        };
    }

    @DataProvider
    private static Object[][] getAttachmentsWithFileName() {
        return new Object[][]{
                {"test", "test", 404},
                {"test", "correct", 404},
                {"correct", "test", 200},
                {"correct", "correct", 200},
                {"correct", "", 200},
                {"", "correct", 404},
        };
    }

    @DataProvider
    private static Object[][] addAttachments() {
        return new Object[][]{
                {"", "", "", "", "", 400},
                {"", "correct", "correct", "correct", "AGENT", 400},
                {"correct", "correct", "correct", "correct", "", 500},
                {"correct", "correct", "correct", "correct", "AGENT", 200},
                {"", "", "correct", "correct", "AGENT", 400},
                {"", "", "correct", "correct", "", 400},
                {"", "", "", "correct", "AGENT", 400},
                {"", "", "", "correct", "", 400},
                {"", "", "", "", "AGENT", 400},
                {"test", "correct", "correct", "correct", "AGENT", 200},
                {"test", "correct", "correct", "correct", "CLIENT", 200},
                {"test", "test", "correct", "correct", "CLIENT", 200},
                {"test", "test", "correct", "correct", "AGENT", 200},
                {"test", "test", "test", "correct", "AGENT", 200},
                {"test", "test", "test", "correct", "CLIENT", 200},
                {"test", "test", "test", "test", "AGENT", 200},
                {"test", "test", "test", "test", "CLIENT", 200},
                {"test", "test", "test", "test", "test", 400},
                {null, null, null, null, null, 400},
                {null, null, null, null, "AGENT", 400},
                {null, "correct", "correct", "correct", "AGENT", 400},
                {null, null, "correct", "correct", "AGENT", 400},
                {null, null, null, "correct", "AGENT", 400},
                {null, null, null, null, "AGENT", 400}
        };
    }

    @DataProvider
    private static Object[][] getAttachments() {
        return new Object[][]{
                {"", "", "", "", "", 400},
                {"", "correct", "correct", "correct", "correct", 400},
                {"correct", "correct", "correct", "correct", "correct", 400},
                {"", "", "correct", "correct", "correct", 200},
                {"", "", "", "correct", "correct", 400},
                {"", "", "", "", "correct", 400},
                {"test", "correct", "correct", "correct", "correct", 400},
                {"test", "test", "correct", "correct", "correct", 400},
                {"test", "test", "test", "correct", "correct", 400},
                {"test", "test", "test", "test", "correct", 400},
                {"test", "test", "test", "test", "test", 400},
                {null, null, null, null, null, 400},
                {null, null, null, null, "correct", 400},
                {null, "correct", "correct", "correct", "correct", 400},
                {null, null, "correct", "correct", "correct", 200},
                {null, null, null, "correct", "correct", 400}

        };
    }

    @DataProvider
    private static Object[][] chatRoomsParameters() {
        return new Object[][]{
                {"", "", "", 400},
                {"correct", "", "", 400},
                {"correct", "", "test1", 400},
                {"test1", "testclient1@clickatelllabs.com", "test1", 404},
                {"correct", "testclient1@clickatelllabs.com", "", 400},
                {"correct", "test1", "test1", 200},
                {"test1", "test1", "test1", 404},
                {"correct", "testclient1@", "test1", 200}
        };
    }

    private String getFullPathToFile(String pathToFile) {
        return TenantTests.class.getClassLoader().getResource(pathToFile).getPath();
    }

    private void generateMessageForChatRoom(ChatRoomResponse chatRoom, String clientId) {
        XmppClientConfigBean xmppClientConfigBean = new XmppClientConfigBean();
        xmppClientConfigBean.setHost(TestingEnvProperties.getPropertyByName("xmpp.host"));
        xmppClientConfigBean.setPort(5222);
        xmppClientConfigBean.setReconnectInterval(30000);
        xmppClientConfigBean.setConnectRetryCount(10);
        xmppClientConfigBean.setConnectRetrySleepTimeMs(2000);
        xmppClientConfigBean.setXmppDomain(TestingEnvProperties.getPropertyByName("xmpp.domain"));

        XmppClient xmppClient = new XmppClient(xmppClientConfigBean, clientJid, null, null);
        xmppClient.connect();
        BareJID room = BareJID.bareJIDInstance(chatRoom.getChatroomJid());
        xmppClient.joinRoom(room.getLocalpart(), room.getDomain(), clientId);

        xmppClient.sendRoomMessage(room, "FAQs");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        xmppClient.sendRoomMessage(room, "MC2RatingTest Message");
        xmppClient.disconnect();

    }
}
