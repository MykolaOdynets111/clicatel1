package com.touch.tests;

import com.touch.models.ErrorMessage;
import com.touch.models.touch.chats.ChatRoomResponse;
import com.touch.models.touch.chats.ChatSessionResponse;
import com.touch.models.touch.chats.ListChatSessionResponse;
import com.touch.models.touch.tenant.TenantRequest;
import com.touch.models.touch.tenant.TenantResponse;
import com.touch.utils.StringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tigase.jaxmpp.core.client.BareJID;

/**
 * Created by kmakohoniuk on 9/5/2016.
 */
public class ChatComunicationTests extends BaseTestClass {

    @BeforeClass
    public void beforeClass() {
    }

    // according TPLAT-433
    @Test
    public void getSameChatRoomSecondTimeWhenItIsNotTerminated(){

        String tenantWithBot = "20a9c80d53fb11e6a0280626baf6c11d";
        String clientJid = "testclient1@clickatelllabs.com";
        String testClientId ="test1";
        ChatRoomResponse chatRoom = chatsActions.getChatRoom(tenantWithBot, clientJid, testClientId).as(ChatRoomResponse.class);
        xmppClient.connect();
        BareJID room = BareJID.bareJIDInstance(chatRoom.getChatroomJid());
        xmppClient.joinRoom(room.getLocalpart(), room.getDomain(), testClientId);
        xmppClient.sendRoomMessage(room, "Test Message");
        xmppClient.disconnect();
        ChatRoomResponse secondTimeChatRoom = chatsActions.getChatRoom(tenantWithBot, clientJid, testClientId).as(ChatRoomResponse.class);
//        verify that we get same room when user logout from chat and session is still alive
        Assert.assertEquals(chatRoom,secondTimeChatRoom);
    }

}
