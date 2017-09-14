package com.touch.tests;

//import com.clickatell.touch.component.redis.RedisClient;
//import com.clickatell.touch.tbot.config.XmppClientConfigBean;
//import com.clickatell.touch.tbot.xmpp.XmppClient;
import com.touch.models.touch.auth.AccessTokenRequest;
import com.touch.models.touch.chats.ChatRoomResponse;
import com.touch.tests.cdatamodels.navigationcard.NavigationCardModel;
import com.touch.tests.extensions.SubmitPerosnalDataCard;
import com.touch.tests.extensions.Tcard;
import com.touch.utils.StringUtils;
import com.touch.utils.TestingEnvProperties;

        import org.jivesoftware.smack.SmackException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import rocks.xmpp.addr.Jid;
import rocks.xmpp.core.XmppException;
        import rocks.xmpp.core.session.XmppClient;

import rocks.xmpp.core.stanza.model.Message;
import tigase.jaxmpp.core.client.BareJID;
//import tigase.jaxmpp.core.client.BareJID;
//import tigase.jaxmpp.core.client.exceptions.JaxmppException;


        import java.io.IOException;
        import java.net.UnknownHostException;

public class ChatComunicationTests extends BaseTestClass {
    String tenantId = TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.id");
    String chatToken;
    String accessToken;
    XmppClient xmppClient;
    String testClientId = "testclient" + StringUtils.generateRandomString(4);
    String clientJid = testClientId + "@clickatelllabs.com";

//    String testClientId ="agent-68a22444-02f0-4df8-7000-da0e8269a1f6-ff80808157b899ad0157b89c6b1e0004";
//    String testClientId ="agent-ff80808157b899ad0157b89c6b1e0004";


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

//    @Test
    public void createOfferAndConnectAgent() throws UnknownHostException, InterruptedException, XmppException {

        XMPPClientAgent xmppClientAgent = new XMPPClientAgent();
        Thread.sleep(120000);
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");


    }

    @Test
    public void createOfferAndConnectAgent1() throws IOException, InterruptedException, XmppException {

        chatToken = getToken(TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.login"), TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.password"));
        String refreshToken = authActions.getRefreshToken(chatToken);
        accessToken = authActions.getAccessToken(new AccessTokenRequest(), refreshToken);
        ChatRoomResponse chatRoomResponse = chatsActions.getChatRoom(tenantId, clientJid, testClientId, "Android", accessToken).as(ChatRoomResponse.class);
        BareJID room = BareJID.bareJIDInstance(chatRoomResponse.getChatroomJid());

        XMPPClient xmppClientWebWidget = new XMPPClient(testClientId, room.getLocalpart());
        Assert.assertTrue(xmppClientWebWidget.waitForGreetingMessage());
//        Thread.sleep(5000);
        Tcard navigationCard = xmppClientWebWidget.getNavigationCard();
        Assert.assertNotNull(navigationCard);
        NavigationCardModel navigationCardModel = navigationCard.getJsonCDATA(NavigationCardModel.class);
        xmppClientWebWidget.sendMessage("Chat to Support");
        Tcard inputCard = xmppClientWebWidget.getInputCard();
        Assert.assertNotNull(inputCard);
        Message message = new Message(Jid.of(chatRoomResponse.getChatroomJid()));
        message.setId("cf314085-850f-e868-6401-90ee5714e" + StringUtils.generateRandomString(3));
//        message.addExtension(new SubmitPerosnalDataCard(inputCard.g,
//                "input-card",
//                "card_3",
//                "<![CDATA[{\"inputdata\":[{\"name\":\"firstName\",\"value\":\"sdfsdfd\"},{\"name\":\"lastName\",\"value\":\"sdfsdfsdf\"},{\"name\":\"email\",\"value\":\"sdfsdf@dsfsd.fsdf\"},{\"name\":\"phone\",\"value\":\"324234234\"},{\"name\":\"company\",\"value\":\"sdfsdfsdfsdf\"}]}]]>"));
//        message.setBody("Submitted data:\n" +
//                "test\n" +
//                "test\n" +
//                "test@test.com\n" +
//                "1234\n" +
//                "test");
//        xmppClientWebWidget.sendMessage(message);


        Thread.sleep(20000);

        xmppClientWebWidget.sendMessage("hello2");


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

//    @Test
//    public void smackXMPPClientTest() throws InterruptedException, SmackException.NotConnectedException {
//        chatToken = getToken(TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.login"), TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.password"));
//        String refreshToken = authActions.getRefreshToken(chatToken);
//        accessToken = authActions.getAccessToken(new AccessTokenRequest(), refreshToken);
//        ChatRoomResponse chatRoomResponse = chatsActions.getChatRoom(tenantId, clientJid, testClientId, "Android", accessToken).as(ChatRoomResponse.class);
//        BareJID room = BareJID.bareJIDInstance(chatRoomResponse.getChatroomJid());
//        SmackXMPPClient smackXMPPClient = null;
//        try {
//            smackXMPPClient = new SmackXMPPClient(testClientId, chatRoomResponse.getChatroomJid());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
////        Assert.assertTrue(smackXMPPClient.waitForGreetingMessage());
//        Thread.sleep(5000);
//        smackXMPPClient.sendMessage("Chat to Support");
//        Thread.sleep(10000);
//
//
//    }

}