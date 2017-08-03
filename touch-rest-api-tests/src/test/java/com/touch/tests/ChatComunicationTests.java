package com.touch.tests;

import com.clickatell.touch.RedisTestClient;
import com.clickatell.touch.RedisTestService;
//import com.clickatell.touch.component.redis.RedisClient;
import com.clickatell.touch.model.Agent;
import com.clickatell.touch.model.Offer;
//import com.clickatell.touch.tbot.config.XmppClientConfigBean;
//import com.clickatell.touch.tbot.xmpp.XmppClient;
import com.touch.models.touch.analytics.ConversationCountStatsResponseV5;
import com.touch.models.touch.analytics.ConversationTimeStatsResponseV5;
import com.touch.models.touch.auth.AccessTokenRequest;
import com.touch.models.touch.chats.ChatRoomResponse;
import com.touch.utils.TestingEnvProperties;
import jdk.nashorn.internal.runtime.ECMAException;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;



import rocks.xmpp.addr.Jid;
import rocks.xmpp.core.XmppException;
import rocks.xmpp.core.session.TcpConnectionConfiguration;
import rocks.xmpp.core.session.XmppClient;

import rocks.xmpp.core.stanza.model.Message;
import rocks.xmpp.extensions.muc.ChatRoom;
import rocks.xmpp.extensions.muc.ChatService;
import rocks.xmpp.extensions.muc.MultiUserChatManager;
import tigase.jaxmpp.core.client.BareJID;
//import tigase.jaxmpp.core.client.BareJID;
//import tigase.jaxmpp.core.client.exceptions.JaxmppException;




import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
//        chatToken = getToken(TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.login"), TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.password"));
//        XmppClientConfigBean xmppClientConfigBean = new XmppClientConfigBean();
//        xmppClientConfigBean.setHost(TestingEnvProperties.getPropertyByName("xmpp.host"));
//        xmppClientConfigBean.setPort(5222);
//        xmppClientConfigBean.setReconnectInterval(30000);
//        xmppClientConfigBean.setConnectRetryCount(10);
//        xmppClientConfigBean.setConnectRetrySleepTimeMs(2000);
//        xmppClientConfigBean.setXmppDomain(TestingEnvProperties.getPropertyByName("xmpp.domain"));
//        xmppClient = new XmppClient(xmppClientConfigBean, clientJid, null, null);
//        String refreshToken = authActions.getRefreshToken(chatToken);
//        accessToken = authActions.getAccessToken(new AccessTokenRequest(), refreshToken);
    }
@Test
public void createOfferAndConnectAgent() throws UnknownHostException, InterruptedException,  XmppException {
    chatToken = getToken(TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.login"), TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.password"));
    String refreshToken = authActions.getRefreshToken(chatToken);
    accessToken = authActions.getAccessToken(new AccessTokenRequest(), refreshToken);
    ChatRoomResponse chatRoomResponse = chatsActions.getChatRoom(tenantId, clientJid, testClientId,"Android", accessToken).as(ChatRoomResponse.class);
    BareJID room = BareJID.bareJIDInstance(chatRoomResponse.getChatroomJid());

    TcpConnectionConfiguration tcpConfiguration = TcpConnectionConfiguration.builder()
            .hostname(TestingEnvProperties.getPropertyByName("xmpp.host"))
            .port(5222)
            .build();

    XmppClient xmppClient = XmppClient.create(TestingEnvProperties.getPropertyByName("xmpp.domain"), tcpConfiguration);
    xmppClient.addInboundMessageListener(e -> {
        Message message = e.getMessage();
        System.out.println("#################################################");
        System.out.println(message.getBody());
        System.out.println("######################################################");
    });
    xmppClient.connect();
    xmppClient.loginAnonymously();

    MultiUserChatManager multiUserChatManager = xmppClient.getManager(MultiUserChatManager.class);
    List<ChatService> chatServices = null;
    try {
        chatServices = multiUserChatManager.discoverChatServices().getResult();
        System.out.println(chatServices);
    } catch (XmppException e) {
        e.printStackTrace();
    }

    ChatService chatService = chatServices.get(0);
    ChatRoom chatRoom = chatService.createRoom(room.getLocalpart());
    chatRoom.enter(testClientId);
    chatRoom.sendMessage("hellohello");
    Thread.sleep(5000);
    chatRoom.sendMessage("/agent");
    Thread.sleep(10000);

    RedisTestService t = RedisTestClient.createService("clickatell", "mc2_platform", "dev", new String []{"172.31.23.63:26379","172.31.25.124:26379","172.31.25.171:26379"});
    Map<String, String> m = t.getMapOffers();
    m.values().forEach(e -> {
        System.out.printf(String.format("$$$$$$$$$$$$$ roster key %s", e));
        List<Offer> offers = t.getActiveOffers4RosterByKeyName(e);
    });
    m.keySet().forEach(e -> {
        System.out.printf(String.format("########## roster %s", e));
        Map<String, Agent> agents = t.getMapAgents4Roster(e);
    });


}

     //according TPLAT-433
//    @Test
//    public void verifyIntegrationAnalyticsForTbotAndAgent() throws InterruptedException {
//        LocalDateTime now = LocalDateTime.now();
//        String year=String.valueOf(now.getYear());
//        String month=String.valueOf(now.getMonthValue());
//        String day=String.valueOf(now.getDayOfMonth());
//        ConversationCountStatsResponseV5 conversationCountBefore = analyticsActions.getConversationCount(tenantId, year, month, day, chatToken).as(ConversationCountStatsResponseV5.class);
//        ConversationTimeStatsResponseV5 conversationTimeBefore = analyticsActions.getConversationTime(tenantId, year, month, day, chatToken).as(ConversationTimeStatsResponseV5.class);
//
//        ChatRoomResponse chatRoom = chatsActions.getChatRoom(tenantId, clientJid, testClientId,"Android", accessToken).as(ChatRoomResponse.class);
//
//        xmppClient.connect();
//        BareJID room = BareJID.bareJIDInstance(chatRoom.getChatroomJid());
//        xmppClient.joinRoom(room.getLocalpart(), room.getDomain(), testClientId);
//
//        xmppClient.sendRoomMessage(room, "FAQs");
//        Thread.sleep(60000);
//        xmppClient.sendRoomMessage(room, "MC2RatingTest Message");
////        AgentClient agentClient = new AgentClient(TestingEnvProperties.getPropertyByName("xmpp.host"), 5222, TestingEnvProperties.getPropertyByName("xmpp.domain"), agentJid, "KefBk8nx", rosterJid);
////        agentClient.changePresence(Presence.Show.chat, 2);
////        xmppClient.inviteAgent(rosterJid, agentJid, null, room, testClientId, "", null);
////        xmppClient.sendRoomMessage(room, "MC2RatingTest Message");
//
//        xmppClient.disconnect();
////        ChatRoomResponse secondTimeChatRoom = chatsActions.getChatRoom(tenantId, clientJid, testClientId, "Android", token).as(ChatRoomResponse.class);
//////        verify that we get same room when user logout from chat and session is still alive
////        Assert.assertEquals(chatRoom,secondTimeChatRoom);
//
////        this delay should be fixed in future, however now we so slow adding static to db
//        Thread.sleep(200000);
//        ConversationCountStatsResponseV5 conversationCountAfter = analyticsActions.getConversationCount(tenantId, year, month, day, chatToken).as(ConversationCountStatsResponseV5.class);
//        ConversationTimeStatsResponseV5 conversationTimeAfter = analyticsActions.getConversationTime(tenantId, year, month, day, chatToken).as(ConversationTimeStatsResponseV5.class);
//        Assert.assertTrue(conversationCountAfter.getTotalConversationCount()>conversationCountBefore.getTotalConversationCount());
//        Assert.assertTrue(conversationTimeAfter.getTotalBotConversationTimeMs()>conversationTimeBefore.getTotalBotConversationTimeMs());
//
//    }

}