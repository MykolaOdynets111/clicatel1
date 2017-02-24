package com.touch.tests;

import com.touch.models.ErrorMessage;
import com.touch.models.touch.chats.*;
import com.touch.models.touch.integration.IntegrationUserLoginMC2Response;
import com.touch.models.touch.tenant.TenantRequest;
import com.touch.models.touch.tenant.TenantResponseV5;
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
import java.io.FileNotFoundException;
import java.io.InputStream;
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
        chatSessions = chatsActions.getListOfSessions(TestingEnvProperties.getPropertyByName("touch.tanant.clickatell.id"), null, testToken).as(ListChatSessionResponse.class).getChatSessions();
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
    public void getChatsEvents(String sessionId, String clientId, String tenantId, String dateFrom, String dateTo, boolean isEmpty, int statusCode){
        ChatSessionResponse session = chatSessions.get(chatSessions.size() - 1);
        if(sessionId!=null&&sessionId.equals("correct"))
            sessionId = session.getSessionId();
        if(clientId!=null&&clientId.equals("correct"))
            clientId = session.getClientId();
        if(tenantId!=null&&tenantId.equals("correct"))
            tenantId = session.getTenantId();
        if(dateFrom!=null&&dateFrom.equals("correct"))
            dateFrom = session.getStartedDate().toString();
        if(dateTo!=null&&dateTo.equals("correct"))
            dateTo = session.getEndedDate().toString();
        Response response = chatsActions.getListOfChatEvents(sessionId, clientId, tenantId, dateFrom, dateTo, testToken);
        Assert.assertEquals(response.getStatusCode(),statusCode);
        if(statusCode==200){
            List<ChatEventResponseV5> chatEvents = response.as(ListChatEventResponseV5.class).getChatEvents();
            Assert.assertEquals(chatEvents.isEmpty(),isEmpty);
        }
    }
    @DataProvider
    private static Object[][] getEvents() {
        return new Object[][]{
                {"","correct", "correct", "", "", false, 200},
                {"correct","", "", "", "", false, 200},
                {"correct",null, null, null, null, false, 200},
                {null,"correct", "correct", null, null, false, 200},
                {"test",null, null, null, null, true, 200},
                {null,"correct", "test", null, null, true, 200},
                {null,"test", "test", null, null, true, 200},
                {"correct","correct", "correct", "correct", "correct", false, 400},
                {"correct","correct", "correct", "correct", "", false, 400},
                {"correct","correct", "correct", "", "", true, 400},
                {"correct","correct", "", "", "", true, 400},
                {"correct","correct", "correct", "correct", null, false, 400},
                {"correct","correct", "correct", null, null, true, 400},
                {"correct","correct", null, null, null, true, 400},
                {"notExist","notExist", "notExist", "notExist", "notExist", true, 400},
                {"notExist","notExist", "notExist", "11", "11", true, 400},
                {"notExist","notExist", "notExist", "notExist", "11", true, 400},
                {"correct","correct", "correct", "test1", null, false, 400},
                {"correct","correct", "correct", "11", null, false, 400},
                {"correct","correct", "test", null, null, true, 400},
                {"correct","test", null, null, null, true, 400},

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
