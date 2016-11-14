package com.touch.tests;

import com.clickatell.models.user_profiles.UserProfile;
import com.touch.models.ErrorMessage;
import com.touch.models.touch.agent.AgentCredentialsDto;
import com.touch.models.touch.agent.AgentResponse;
import com.touch.models.touch.agent.ListAgentResponse;
import com.touch.models.touch.chats.ChatRoomResponse;
import com.touch.models.touch.chats.ChatSessionResponse;
import com.touch.models.touch.chats.ListChatSessionResponse;
import com.touch.models.touch.department.DepartmentDto;
import com.touch.models.touch.department.DepartmentResponse;
import com.touch.models.touch.tenant.TenantRequest;
import com.touch.models.touch.tenant.TenantResponse;
import com.touch.utils.StringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class ChatsTests extends BaseTestClass {

    @BeforeClass
    public void beforeClass() {
    }

    @Test
    public void getNewChatRoom() {
        String tenantWithBot = "20a9c80d53fb11e6a0280626baf6c11d";
        ChatRoomResponse chatRoom = chatsActions.getChatRoom(tenantWithBot, "testclient1@clickatelllabs.com", "test1").as(ChatRoomResponse.class);
        Assert.assertTrue(chatRoom.getChatroomJid().matches(".{32}@muc.clickatelllabs.com"));

    }

    @Test
    public void getNewSessionAndSessionList() {
        String sessionId = "testSession" + StringUtils.generateRandomString(10);
        TenantRequest tenantRequest = new TenantRequest();
        TenantResponse newTenant = tenantActions.createNewTenantInTouchSide(tenantRequest, TenantResponse.class);
        ListChatSessionResponse listSessionBeforeAddingNew = chatsActions.getListOfSessions(null, null).as(ListChatSessionResponse.class);
        ChatSessionResponse session = chatsActions.addNewSession(sessionId, newTenant.getId(), "testClientId").as(ChatSessionResponse.class);
        ListChatSessionResponse listSessionAfterAddingNew = chatsActions.getListOfSessions(null, null).as(ListChatSessionResponse.class);
//        verify that we have one more new session in session list
        Assert.assertEquals(listSessionBeforeAddingNew.getChatSessions().size() + 1, listSessionAfterAddingNew.getChatSessions().size());
//        verify that session list contains new session
        Assert.assertTrue(listSessionAfterAddingNew.getChatSessions().contains(session));
        ChatSessionResponse as = chatsActions.getSession(session.getSessionId()).as(ChatSessionResponse.class);
//        verify that we get correct session by new sessionId
        Assert.assertTrue(chatsActions.getSession(session.getSessionId()).as(ChatSessionResponse.class).equals(session));

        //        post conditions delete tenant and session
        Assert.assertEquals(chatsActions.deleteSession(session.getSessionId()).getStatusCode(), 200);
        Assert.assertEquals(tenantActions.deleteTenant(newTenant.getId()), 200);

    }

    @Test
    public void getSessionDataWithWrongSessionId() {
        String sessionId = "wrong_sessionId";
        String expectedMessage = "ChatSession with id " + sessionId + " not found";
//        Verify error message which we get when we try to get session data with wrong sessionId
        Assert.assertTrue(chatsActions.getSession(sessionId).as(ErrorMessage.class).getErrorMessage().matches(expectedMessage));

    }


    @Test
    public void getNewChatRoomWithWrongData() {
        // verify error message when we try to get chat room for not existing tenant
        Assert.assertTrue(chatsActions.getChatRoom("wrong_tenantId", "testclient1@clickatelllabs.com", "test1").as(ErrorMessage.class).getErrorMessage().matches("Tenant with id .+ not found"));
    }


}
