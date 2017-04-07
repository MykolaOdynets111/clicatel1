package com.touch.tests;

import com.clickatell.touch.tbot.xmpp.XmppClient;
import com.run.AgentClient;
import com.touch.models.touch.analytics.ConversationCountStatsResponseV5;
import com.touch.models.touch.analytics.ConversationTimeStatsResponseV5;
import com.touch.models.touch.chats.ChatRoomResponse;
import com.touch.models.touch.tenant.TenantResponseV5;
import com.touch.utils.TestingEnvProperties;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import tigase.jaxmpp.core.client.BareJID;
import com.touch.utils.ApplicationProperties;
import tigase.jaxmpp.core.client.JID;
import tigase.jaxmpp.core.client.xmpp.stanzas.Presence;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ChatComunicationTests extends BaseTestClass {
    String tenantId;
    XmppClient xmppClient;
    private static final String HOST = "localhost";
    private static final String XMPP_DOMAIN = "jabber.dev.net";
    private static final String ROSTER = "demo@department.jabber.dev.net";
    private static final String AGENT_PASSWORD = "agent";
    @BeforeTest
    public void beforeClass() {
        token = getToken();
        testTenant = getTestTenant1();
        testToken = getToken(TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.email"), TestingEnvProperties.getPropertyByName("touch.tenant.mc2.user.password"));
        xmppClient = new XmppClient(TestingEnvProperties.getPropertyByName("xmpp.host"), 5222, TestingEnvProperties.getPropertyByName("xmpp.domain"), 30000, null);
        List<TenantResponseV5> tenantsList = tenantActions.getTenantsList(testToken);
        for(TenantResponseV5 tenant : tenantsList){
            if(tenant.getTenantJid().equals(TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.jid"))){
                tenantId = tenant.getId();
                break;
            }
        }
    }

    // according TPLAT-433
    @Test
    public void verifyIntegrationAnalyticsForTbotAndAgent() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        String year=String.valueOf(now.getYear());
        String month=String.valueOf(now.getMonthValue());
        String day=String.valueOf(now.getDayOfMonth());
        ConversationCountStatsResponseV5 conversationCountBefore = analyticsActions.getConversationCount(tenantId, year, month, day, testToken).as(ConversationCountStatsResponseV5.class);
        ConversationTimeStatsResponseV5 conversationTimeBefore = analyticsActions.getConversationTime(tenantId, year, month, day, testToken).as(ConversationTimeStatsResponseV5.class);
        String clientJid = "testclient1@clickatelllabs.com";
        String testClientId ="test1";
        BareJID rosterJid = BareJID.bareJIDInstance("clickatell@department.clickatelllabs.com");
        BareJID agentJid = BareJID.bareJIDInstance("ff80808157b899ad0157b89c6b1e0004@clickatelllabs.com");
        ChatRoomResponse chatRoom = chatsActions.getChatRoom(tenantId, clientJid, testClientId,"Android", token).as(ChatRoomResponse.class);
        xmppClient.connect();
        BareJID room = BareJID.bareJIDInstance(chatRoom.getChatroomJid());
        xmppClient.joinRoom(room.getLocalpart(), room.getDomain(), testClientId);

        xmppClient.sendRoomMessage(room, "FAQs");
        Thread.sleep(60000);
        xmppClient.sendRoomMessage(room, "MC2RatingTest Message");
//        AgentClient agentClient = new AgentClient(TestingEnvProperties.getPropertyByName("xmpp.host"), 5222, TestingEnvProperties.getPropertyByName("xmpp.domain"), agentJid, "KefBk8nx", rosterJid);
//        agentClient.changePresence(Presence.Show.chat, 2);
//        xmppClient.inviteAgent(rosterJid, agentJid, null, room, testClientId, "", null);
//        xmppClient.sendRoomMessage(room, "MC2RatingTest Message");
        xmppClient.disconnect();
//        ChatRoomResponse secondTimeChatRoom = chatsActions.getChatRoom(tenantId, clientJid, testClientId, "Android", token).as(ChatRoomResponse.class);
////        verify that we get same room when user logout from chat and session is still alive
//        Assert.assertEquals(chatRoom,secondTimeChatRoom);

//        this delay should be fixed in future, however now we so slow adding static to db
        Thread.sleep(200000);
        ConversationCountStatsResponseV5 conversationCountAfter = analyticsActions.getConversationCount(tenantId, year, month, day, testToken).as(ConversationCountStatsResponseV5.class);
        ConversationTimeStatsResponseV5 conversationTimeAfter = analyticsActions.getConversationTime(tenantId, year, month, day, testToken).as(ConversationTimeStatsResponseV5.class);
        Assert.assertTrue(conversationCountAfter.getTotalConversationCount()>conversationCountBefore.getTotalConversationCount());
        Assert.assertTrue(conversationTimeAfter.getTotalBotConversationTimeMs()>conversationTimeBefore.getTotalBotConversationTimeMs());

    }

}