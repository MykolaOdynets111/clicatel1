package com.touch.tests;

import com.clickatell.touch.tbot.config.XmppClientConfigBean;
import com.clickatell.touch.tbot.xmpp.XmppClient;
import com.touch.models.touch.analytics.ConversationCountStatsResponseV5;
import com.touch.models.touch.analytics.ConversationTimeStatsResponseV5;
import com.touch.models.touch.auth.AccessTokenRequest;
import com.touch.models.touch.chats.ChatRoomResponse;
import com.touch.utils.TestingEnvProperties;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import tigase.jaxmpp.core.client.BareJID;

import java.time.LocalDateTime;

public class ChatComunicationTests extends BaseTestClass {
    String tenantId = TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.id");
    String ravyTenantId = TestingEnvProperties.getPropertyByName("touch.tenant.karvy.id");
    String chatToken;
    String accessToken;
    XmppClient xmppClient;
    String clientJid = "testclient1@clickatelllabs.com";
    String testClientId ="test1";

    @BeforeTest
    public void beforeClass() {
        chatToken = getToken(TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.login"), TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.password"));
        XmppClientConfigBean xmppClientConfigBean = new XmppClientConfigBean();
        xmppClientConfigBean.setHost(TestingEnvProperties.getPropertyByName("xmpp.host"));
        xmppClientConfigBean.setPort(5222);
        xmppClientConfigBean.setReconnectInterval(30000);
        xmppClientConfigBean.setConnectRetryCount(10);
        xmppClientConfigBean.setConnectRetrySleepTimeMs(2000);
        xmppClientConfigBean.setXmppDomain(TestingEnvProperties.getPropertyByName("xmpp.domain"));
        xmppClient = new XmppClient(xmppClientConfigBean, clientJid, null, null);
        String refreshToken = authActions.getRefreshToken(chatToken);
        accessToken = authActions.getAccessToken(new AccessTokenRequest(), refreshToken);
    }
@Test
public void createOfferAndConnectAgent(){
    ChatRoomResponse chatRoom = chatsActions.getChatRoom(tenantId, clientJid, testClientId,"Android", accessToken).as(ChatRoomResponse.class);
    xmppClient.connect();
    BareJID room = BareJID.bareJIDInstance(chatRoom.getChatroomJid());
    xmppClient.joinRoom(room.getLocalpart(), room.getDomain(), testClientId);
    xmppClient.sendRoomMessage(room, "FAQs");

}

    // according TPLAT-433
    @Test
    public void verifyIntegrationAnalyticsForTbotAndAgent() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        String year=String.valueOf(now.getYear());
        String month=String.valueOf(now.getMonthValue());
        String day=String.valueOf(now.getDayOfMonth());
        ConversationCountStatsResponseV5 conversationCountBefore = analyticsActions.getConversationCount(tenantId, year, month, day, chatToken).as(ConversationCountStatsResponseV5.class);
        ConversationTimeStatsResponseV5 conversationTimeBefore = analyticsActions.getConversationTime(tenantId, year, month, day, chatToken).as(ConversationTimeStatsResponseV5.class);

        ChatRoomResponse chatRoom = chatsActions.getChatRoom(tenantId, clientJid, testClientId,"Android", accessToken).as(ChatRoomResponse.class);

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
        ConversationCountStatsResponseV5 conversationCountAfter = analyticsActions.getConversationCount(tenantId, year, month, day, chatToken).as(ConversationCountStatsResponseV5.class);
        ConversationTimeStatsResponseV5 conversationTimeAfter = analyticsActions.getConversationTime(tenantId, year, month, day, chatToken).as(ConversationTimeStatsResponseV5.class);
        Assert.assertTrue(conversationCountAfter.getTotalConversationCount()>conversationCountBefore.getTotalConversationCount());
        Assert.assertTrue(conversationTimeAfter.getTotalBotConversationTimeMs()>conversationTimeBefore.getTotalBotConversationTimeMs());

    }

}