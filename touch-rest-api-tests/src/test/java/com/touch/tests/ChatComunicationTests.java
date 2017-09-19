package com.touch.tests;


import com.touch.models.touch.auth.AccessTokenRequest;
import com.touch.models.touch.chats.ChatRoomResponse;
import com.touch.tests.cdatamodels.inputcard.InputCardModel;
import com.touch.tests.cdatamodels.navigationcard.NavigationCardModel;
import com.touch.tests.extensions.SubmitPerosnalDataCard;
import com.touch.tests.extensions.Tcard;
import com.touch.utils.StringUtils;
import com.touch.utils.TestingEnvProperties;

import org.testng.Assert;
import org.testng.annotations.Test;


import rocks.xmpp.addr.Jid;
import rocks.xmpp.core.XmppException;
import rocks.xmpp.core.session.XmppClient;

import rocks.xmpp.core.stanza.model.Message;
import tigase.jaxmpp.core.client.BareJID;



import java.io.IOException;

public class ChatComunicationTests extends BaseTestClass {
    String tenantId = TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.id");
    String chatToken;
    String accessToken;
    String testClientId = "testclient" + StringUtils.generateRandomString(4);
    String clientJid = testClientId + "@clickatelllabs.com";


    @Test
    public void createOfferAndConnectAgent() throws IOException, InterruptedException, XmppException {

        chatToken = getToken(TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.login"), TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.password"));
        String refreshToken = authActions.getRefreshToken(chatToken);
        accessToken = authActions.getAccessToken(new AccessTokenRequest(), refreshToken);
        ChatRoomResponse chatRoomResponse = chatsActions.getChatRoom(tenantId, clientJid, testClientId, "Android", accessToken).as(ChatRoomResponse.class);
        BareJID room = BareJID.bareJIDInstance(chatRoomResponse.getChatroomJid());

        XMPPAgent xmppAgent = new XMPPAgent();


        XMPPClient xmppClientWebWidget = new XMPPClient();
        xmppClientWebWidget.connect();
        xmppClientWebWidget.joinRoom(testClientId, room.getLocalpart());
        Assert.assertTrue(xmppClientWebWidget.waitForGreetingMessage());
        Tcard navigationCard = xmppClientWebWidget.getNavigationCard();
        Assert.assertNotNull(navigationCard);
        NavigationCardModel navigationCardModel = navigationCard.getJsonCDATA(NavigationCardModel.class);
        xmppClientWebWidget.sendMessage("Chat to Support");
        Tcard inputCard = xmppClientWebWidget.getInputCard();
        Assert.assertNotNull(inputCard);
        InputCardModel inputCardModel = inputCard.getJsonCDATA(InputCardModel.class);
        Message message = new Message(Jid.of(chatRoomResponse.getChatroomJid()));
        message.setId("cf314085-850f-e868-6401-90ee5714e" + StringUtils.generateRandomString(3));
        message.addExtension(new SubmitPerosnalDataCard(inputCardModel.getAction(),
                inputCard.getTcardName(),
                "card_3",
                "<![CDATA[{\"inputdata\":[{\"name\":\"firstName\",\"value\":\"sdfsdfd\"},{\"name\":\"lastName\",\"value\":\"sdfsdfsdf\"},{\"name\":\"email\",\"value\":\"sdfsdf@dsfsd.fsdf\"},{\"name\":\"phone\",\"value\":\"324234234\"},{\"name\":\"company\",\"value\":\"sdfsdfsdfsdf\"}]}]]>"));
        message.setBody("Submitted data:\n" +
                "test\n" +
                "test\n" +
                "test@test.com\n" +
                "1234\n" +
                "test");
        xmppClientWebWidget.sendMessage(message);
        xmppClientWebWidget.waitForConnectinAgentMessage();
        Assert.assertTrue(xmppAgent.waitForOffer(), "Agent didn't receive offer!");
        xmppAgent.acceptOffer();
        xmppAgent.joinRoom();
        xmppClientWebWidget.waitForAgentConnectedMesasge();
        xmppAgent.sendMessage("Hi, how can I help You");
        xmppClientWebWidget.sendMessage("hello2");
    }


    @Test
    public void checkInputCardMidFlowReaction() throws IOException, InterruptedException, XmppException {

        chatToken = getToken(TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.login"), TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.password"));
        String refreshToken = authActions.getRefreshToken(chatToken);
        accessToken = authActions.getAccessToken(new AccessTokenRequest(), refreshToken);
        ChatRoomResponse chatRoomResponse = chatsActions.getChatRoom(tenantId, clientJid, testClientId, "Android", accessToken).as(ChatRoomResponse.class);
        BareJID room = BareJID.bareJIDInstance(chatRoomResponse.getChatroomJid());

        XMPPAgent xmppAgent = new XMPPAgent();


        XMPPClient xmppClientWebWidget = new XMPPClient();
        xmppClientWebWidget.connect();
        xmppClientWebWidget.joinRoom(testClientId, room.getLocalpart());
        Assert.assertTrue(xmppClientWebWidget.waitForGreetingMessage());
        Tcard navigationCard = xmppClientWebWidget.getNavigationCard();
        Assert.assertNotNull(navigationCard);
        NavigationCardModel navigationCardModel = navigationCard.getJsonCDATA(NavigationCardModel.class);
        xmppClientWebWidget.sendMessage("Chat to Support");
        Tcard inputCard = xmppClientWebWidget.getInputCard();
        Assert.assertNotNull(inputCard, "InputCard not receiced by client");
        InputCardModel inputCardModel = inputCard.getJsonCDATA(InputCardModel.class);
        Message message = new Message(Jid.of(chatRoomResponse.getChatroomJid()));
        message.setId("cf314085-850f-e868-6401-90ee5714e" + StringUtils.generateRandomString(3));
        message.setBody("Message to check InputCard mid flow reaction");
        xmppClientWebWidget.sendMessage(message);
        Assert.assertTrue(xmppClientWebWidget.waitForMidFlowReactionMessage(), "Mid flow reaction message wasn't received after InputCard");

    }


    @Test
    public void checkInputCardIsShownOncePerSession() throws IOException, InterruptedException, XmppException {

        chatToken = getToken(TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.login"), TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.password"));
        String refreshToken = authActions.getRefreshToken(chatToken);
        accessToken = authActions.getAccessToken(new AccessTokenRequest(), refreshToken);
        ChatRoomResponse chatRoomResponse = chatsActions.getChatRoom(tenantId, clientJid, testClientId, "Android", accessToken).as(ChatRoomResponse.class);
        BareJID room = BareJID.bareJIDInstance(chatRoomResponse.getChatroomJid());
        XMPPClient xmppClientWebWidget = new XMPPClient();
        xmppClientWebWidget.connect();
        xmppClientWebWidget.joinRoom(testClientId, room.getLocalpart());
        Assert.assertTrue(xmppClientWebWidget.waitForGreetingMessage(), "Greeting message was not received by client");
        Tcard navigationCard = xmppClientWebWidget.getNavigationCard();
        Assert.assertNotNull(navigationCard);
        NavigationCardModel navigationCardModel = navigationCard.getJsonCDATA(NavigationCardModel.class);
        xmppClientWebWidget.sendMessage("Chat to Support");
        Tcard inputCard = xmppClientWebWidget.getInputCard();
        Assert.assertNotNull(inputCard, "InputCard not receiced by client");
        InputCardModel inputCardModel = inputCard.getJsonCDATA(InputCardModel.class);
        Message message = new Message(Jid.of(chatRoomResponse.getChatroomJid()));
        message.setId("cf314085-850f-e868-6401-90ee5714e" + StringUtils.generateRandomString(3));
        message.addExtension(new SubmitPerosnalDataCard(inputCardModel.getAction(),
                inputCard.getTcardName(),
                "card_3",
                "<![CDATA[{\"inputdata\":[{\"name\":\"firstName\",\"value\":\"sdfsdfd\"},{\"name\":\"lastName\",\"value\":\"sdfsdfsdf\"},{\"name\":\"email\",\"value\":\"sdfsdf@dsfsd.fsdf\"},{\"name\":\"phone\",\"value\":\"324234234\"},{\"name\":\"company\",\"value\":\"sdfsdfsdfsdf\"}]}]]>"));
        message.setBody("Submitted data:\n" +
                "test\n" +
                "test\n" +
                "test@test.com\n" +
                "1234\n" +
                "test");
        xmppClientWebWidget.sendMessage(message);
        xmppClientWebWidget.waitForConnectinAgentMessage();
        xmppClientWebWidget.leaveRoom();
        xmppClientWebWidget.joinRoom();
        Thread.sleep(10000);
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