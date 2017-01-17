package com.touch.tests;

import com.touch.models.ErrorMessage;
import com.touch.models.touch.chats.ChatRoomResponse;
import com.touch.models.touch.chats.ChatSessionResponse;
import com.touch.models.touch.chats.ListChatSessionResponse;
import com.touch.models.touch.integration.IntegrationUserLoginMC2Response;
import com.touch.models.touch.tenant.TenantRequest;
import com.touch.models.touch.tenant.TenantResponseV5;
import com.touch.utils.StringUtils;
import com.touch.utils.TestingEnvProperties;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class ChatsTests extends BaseTestClass {

    @Test
    public void getNewChatRoom() {
        String tenantWithBot = getTestTenant1().getId();
        ChatRoomResponse chatRoom = chatsActions.getChatRoom(tenantWithBot, "testclient1@clickatelllabs.com", "test1","Android", token).as(ChatRoomResponse.class);
        Assert.assertTrue(chatRoom.getChatroomJid().matches(".{32}@muc.clickatelllabs.com"));

    }
    @Test(dataProvider = "chatRoomsParameters")
    public void tryToGetNewChatRoomWithDifferentData(String tenantId, String clientJid, String clientId, int statusCode) {
        Assert.assertEquals(chatsActions.getChatRoom(tenantId, clientJid, clientId,"Android", token).getStatusCode(),statusCode);

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
        Assert.assertTrue(chatsActions.getSession(sessionId,token).as(ErrorMessage.class).getErrorMessage().matches(expectedMessage));

    }


    @Test
    public void getNewChatRoomWithWrongData() {
        // verify error message when we try to get chat room for not existing tenant
        Assert.assertTrue(chatsActions.getChatRoom("wrong_tenantId", "testclient1@clickatelllabs.com", "test1","Android",token).as(ErrorMessage.class).getErrorMessage().matches("Tenant with id .+ not found"));
    }
    @DataProvider
    private static Object[][] chatRoomsParameters() {
        return new Object[][]{
                {"", "", "", 400},
                {"20a9c80d53fb11e6a0280626baf6c11d", "", "", 400},
                {"20a9c80d53fb11e6a0280626baf6c11d", "", "test1", 400},
                {"test1", "testclient1@clickatelllabs.com", "test1", 404},
                {"20a9c80d53fb11e6a0280626baf6c11d", "testclient1@clickatelllabs.com", "", 400},
                {"20a9c80d53fb11e6a0280626baf6c11d", "test1", "test1", 200},
                {"test1", "test1", "test1", 404},
                {"20a9c80d53fb11e6a0280626baf6c11d", "testclient1@", "test1", 200}
        };
    }

}
