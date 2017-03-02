package com.touch.tests;

import com.touch.models.ErrorMessage;
import com.touch.models.touch.agent.AgentCredentialsDto;
import com.touch.models.touch.chats.*;
import com.touch.utils.StringUtils;
import com.touch.utils.TestingEnvProperties;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class ChatsTests extends BaseTestClass {
    String sessionId;
    String file = getFullPathToFile("TenantResources/tenant_logo.jpg");
    AttachmentCreateResponse testAttachment;
    List<ChatSessionResponse> chatSessions;

    @BeforeClass
    public void beforeClass() {
        token = getToken();
        testTenant = getTestTenant1();
        testToken = getToken(TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.email"), TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.password"));
        sessionId = chatsActions.getListOfSessions(null, null, testToken).as(ListChatSessionResponse.class).getChatSessions().get(0).getSessionId();
        testAttachment = chatsActions.addAttachmentForSession(sessionId, "testImage", new File(file), testToken).as(AttachmentCreateResponse.class);
        chatSessions = chatsActions.getListOfSessions(TestingEnvProperties.getPropertyByName("touch.tenant.genbank.id"), null, testToken).as(ListChatSessionResponse.class).getChatSessions();
    }

    @AfterClass
    public void afterClass() {
        Assert.assertEquals(chatsActions.deleteAttachment(testAttachment.getId(), testToken), 200);
    }

    @Test
    public void getNewChatRoom() {
        String tenantWithBot = getTestTenant1().getId();
        ChatRoomResponse chatRoom = chatsActions.getChatRoom(tenantWithBot, "testclient1@clickatelllabs.com", "test1", "Android", token).as(ChatRoomResponse.class);
        Assert.assertTrue(chatRoom.getChatroomJid().matches(".{32}@muc.clickatelllabs.com"));

    }

    @Test(dataProvider = "chatRoomsParameters")
    public void tryToGetNewChatRoomWithDifferentData(String tenantId, String clientJid, String clientId, int statusCode) {
        if (tenantId.equals("correct"))
            tenantId = getTestTenant1().getId();
        Assert.assertEquals(chatsActions.getChatRoom(tenantId, clientJid, clientId, "Android", token).getStatusCode(), statusCode);

    }

    @Test
    public void getNewSessionAndSessionList() {
        String sessionId = "testSession" + StringUtils.generateRandomString(10);

        ListChatSessionResponse listSessionBeforeAddingNew = chatsActions.getListOfSessions(null, null, token).as(ListChatSessionResponse.class);
        ChatSessionResponse session = chatsActions.addNewSession(sessionId, testTenant.getId(), "testClientId", token).as(ChatSessionResponse.class);
        ListChatSessionResponse listSessionAfterAddingNew = chatsActions.getListOfSessions(null, null, token).as(ListChatSessionResponse.class);
//        verify that we have one more new session in session list
        Assert.assertEquals(listSessionBeforeAddingNew.getChatSessions().size() + 1, listSessionAfterAddingNew.getChatSessions().size());
//        verify that session list contains new session
        Assert.assertTrue(listSessionAfterAddingNew.getChatSessions().contains(session));
        ChatSessionResponse as = chatsActions.getSession(session.getSessionId(), token).as(ChatSessionResponse.class);
//        verify that we get correct session by new sessionId
        Assert.assertTrue(chatsActions.getSession(session.getSessionId(), token).as(ChatSessionResponse.class).equals(session));

        //        post conditions delete tenant and session
        Assert.assertEquals(chatsActions.deleteSession(session.getSessionId(), token).getStatusCode(), 200);

    }

    @Test
    public void getSessionDataWithWrongSessionId() {
        String sessionId = "wrong_sessionId";
        String expectedMessage = "ChatSession with id " + sessionId + " not found";
//        Verify error message which we get when we try to get session data with wrong sessionId
        Assert.assertTrue(chatsActions.getSession(sessionId, token).as(ErrorMessage.class).getErrorMessage().matches(expectedMessage));

    }


    @Test
    public void getNewChatRoomWithWrongData() {
        // verify error message when we try to get chat room for not existing tenant
        Assert.assertTrue(chatsActions.getChatRoom("wrong_tenantId", "testclient1@clickatelllabs.com", "test1", "Android", token).as(ErrorMessage.class).getErrorMessage().matches("Tenant with id .+ not found"));
    }

    @Test(dataProvider = "getAttachments")
    public void getChatsAttachments(String attachmentId, String sessionId, String fileType, int statusCode) {
        if (attachmentId != null && attachmentId.equals("correct"))
            attachmentId = testAttachment.getId();
        if (sessionId != null && sessionId.equals("correct"))
            sessionId = this.sessionId;
        if (fileType != null && fileType.equals("correct"))
            fileType = "image/jpeg";
        Response getAttachmentResponse = chatsActions.getAttachmentsList(attachmentId, sessionId, fileType, testToken);
        Assert.assertEquals(getAttachmentResponse.getStatusCode(), statusCode);
        if (getAttachmentResponse.getStatusCode() == 200) {
            List<AttachmentResponse> attachmentsList = getAttachmentResponse.as(ListAttachmentResponse.class).getAttachments();
            Assert.assertFalse(attachmentsList.isEmpty());
            boolean containsCorrectAttachmentsData = false;
            for (AttachmentResponse attach : attachmentsList) {
                if (attach.getFileName().equals(testAttachment.getFileName())) {
                    containsCorrectAttachmentsData = attach.getFileType().equals(testAttachment.getFileType());
                }
            }
            Assert.assertTrue(containsCorrectAttachmentsData);
        }
    }

    @Test(dataProvider = "addAttachments")
    public void addChatAttachments(String sessionId, String fileName, int statusCode) {
        Response response = chatsActions.addAttachmentForSession(sessionId, fileName, new File(file), testToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (response.getStatusCode() == 200) {
            AttachmentCreateResponse addedAttachment = response.as(AttachmentCreateResponse.class);
            Assert.assertEquals(addedAttachment.getFileName(), fileName);
            Assert.assertEquals(chatsActions.deleteAttachment(addedAttachment.getId(), testToken), 200);
        }
    }

    @Test(dataProvider = "getDeleteAttachments")
    public void getChatAttachment(String attachmentId, int statusCode) throws Exception {
        if (attachmentId.equals("correct"))
            attachmentId = testAttachment.getId();
        Response response = chatsActions.getAttachment(attachmentId, testToken);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200)
            Assert.assertTrue(isEqualInputStreams(response.asInputStream(), new FileInputStream(new File(file))));
    }

    @Test(dataProvider = "getDeleteAttachments")
    public void deleteChatAttachment(String attachmentId, int statusCode) {
        if (attachmentId.equals("correct"))
            attachmentId = chatsActions.addAttachmentForSession(sessionId, "testImage", new File(file), testToken).as(AttachmentCreateResponse.class).getId();
        Assert.assertEquals(chatsActions.deleteAttachment(attachmentId, testToken), statusCode);
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
        Response response = chatsActions.getListOfChatEvents(sessionId, clientId, tenantId, dateFrom, dateTo, testToken);
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
        Response response = chatsActions.getListOfChatHistory(sessionId, clientId, tenantId, dateFrom, dateTo, String.valueOf(returnDisplayMessage), token);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            List<ChatHistoryRecordResponse> records = response.as(ListChatHistoryRecordsResponse.class).getRecords();
//            Assert.assertFalse(records.isEmpty());
        }
    }

    @Test(dataProvider = "getHistorySession")
    public void getChatsHistoryForSession(String sessionId, boolean isEmpty, int statusCode) {
        ChatSessionResponse session = chatSessions.get(chatSessions.size() - 1);
        if (sessionId != null && sessionId.equals("correct"))
            sessionId = session.getSessionId();

        Response response = chatsActions.getListOfChatHistoryForSession(sessionId, token);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            List<ChatHistoryRecordResponse> records = response.as(ListChatHistoryRecordsResponse.class).getRecords();
            Assert.assertEquals(records.isEmpty(), isEmpty);
        }
    }

    @Test(dataProvider = "invitesList")
    public void getInvites(String status, String dateFrom, String dateTo, int statusCode) {

        Response response = chatsActions.getListOfInvites(status, dateFrom, dateTo, token);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            List<ChatInviteHistoryResponseV5> records = response.as(ListChatInviteHistoryResponseV5.class).getChatInviteHistory();
            Assert.assertFalse(records.isEmpty());
        }
    }

    @Test(dataProvider = "invitesListArchive")
    public void getInvitesFromArchive(String nickname, String page, String count, int statusCode) {
        if (nickname != null && nickname.equals("correct"))
            nickname = agentActions.getCredentials(token, AgentCredentialsDto.class).getJid().split("@")[0];
        Response response = chatsActions.getListOfInvitesFromArchive(nickname, page, count, token);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            List<ChatInviteHistoryArchiveResponse> records = response.as(ListChatInviteHistoryArchiveResponse.class).getChatInviteHistory();
//            Assert.assertFalse(records.isEmpty());
        }
    }
    @Test(dataProvider = "getInvitesSession")
    public void getInvitesForSession(String sessionId, boolean isEmpty, int statusCode) {
        ChatSessionResponse session = chatSessions.get(chatSessions.size() - 1);
        if (sessionId != null && sessionId.equals("correct"))
            sessionId = session.getSessionId();
        Response response = chatsActions.getInviteForSession(sessionId,token);
        Assert.assertEquals(response.getStatusCode(), statusCode);
        if (statusCode == 200) {
            List<ChatInviteHistoryResponseV5> records = response.as(ListChatInviteHistoryResponseV5.class).getChatInviteHistory();
//            Assert.assertEquals(records.isEmpty(),isEmpty);
        }
    }
    @DataProvider
    private static Object[][] invitesListArchive() {
        return new Object[][]{
                {"correct", "1", "5", 200},
                {"correct", "2", "5", 200},
                {"correct", "1", "10", 200},
                {"correct", "tt", "5", 400},
                {"correct", "2", "tt", 400},
                {"correct", "0", "5", 500},
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
                {"Test", "0", "2288442453000", 400},
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
                {null, "correct", "test", null, null, true, 200},
                {null, "test", "test", null, null, true, 200},
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
    private static Object[][] addAttachments() {
        return new Object[][]{
                {"", "", 500},
                {"correct", "", 500},
                {"", "test1", 200},
                {"correct", "test1", 200},
                {null, "test1", 500},
                {"correct", null, 500},
                {null, null, 500},


        };
    }

    @DataProvider
    private static Object[][] getAttachments() {
        return new Object[][]{
                {"", "", "", 200},
                {"", "", "correct", 200},
                {"", "correct", "", 200},
                {"", "correct", "correct", 200},
                {"correct", "correct", "correct", 200},
                {null, "correct", "correct", 200},
                {null, null, "correct", 200},
                {null, "correct", null, 200},
                {null, null, null, 200},
                {"111", "111", "11", 404},
                {"11", "11", "correct", 404},
                {"11", "correct", "11", 404},
                {"111", "correct", "correct", 404},
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

}
