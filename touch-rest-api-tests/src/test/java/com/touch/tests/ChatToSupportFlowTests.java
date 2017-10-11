package com.touch.tests;


import com.touch.models.touch.auth.AccessTokenRequest;
import com.touch.models.touch.chats.ChatRoomResponse;
import com.touch.tests.cdatamodels.inputcard.InputCardModel;
import com.touch.tests.cdatamodels.navigationcard.NavigationCardModel;
import com.touch.tests.extensions.TButtonItemSubmit;
import com.touch.tests.extensions.TcardSubmit;
import com.touch.tests.extensions.Tcard;
import com.touch.utils.MySQLConnector;
import com.touch.utils.StringUtils;
import com.touch.utils.TestingEnvProperties;

import org.testng.Assert;
import org.testng.annotations.*;


import rocks.xmpp.addr.Jid;
import rocks.xmpp.core.XmppException;

import rocks.xmpp.core.stanza.model.Message;
import tigase.jaxmpp.core.client.BareJID;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChatToSupportFlowTests extends BaseTestClass {
    String tenantId = TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.id");
    String chatToken;
    String accessToken;
    String clientId = "testclient" + StringUtils.generateRandomString(4);
    String clientJid = clientId + "@clickatelllabs.com";
    BareJID room;
    ChatRoomResponse chatRoomResponse;
    XMPPAgent xmppAgent;
    XMPPClient xmppClientWebWidget;


    @BeforeClass
    public void beforeClass(){
        chatToken = getToken(TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.login"), TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.password"));
        String refreshToken = authActions.getRefreshToken(chatToken);
        accessToken = authActions.getAccessToken(new AccessTokenRequest(), refreshToken);
    }




    @BeforeMethod
    public void beforeMethod(){
        clientId = "testclient" + StringUtils.generateRandomString(4);
        clientJid = clientId + "@clickatelllabs.com";
        chatRoomResponse = chatsActions.getChatRoom(tenantId, clientJid, clientId, "webchat", accessToken).as(ChatRoomResponse.class);
        room = BareJID.bareJIDInstance(chatRoomResponse.getChatroomJid());
        xmppAgent = new XMPPAgent(TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.agent.xmpp.login"),
                TestingEnvProperties.getPropertyByName("touch.tenant.clickatell.agent.xmpp.password"));
        xmppClientWebWidget = new XMPPClient(clientId);

    }


    @Test()
    public void connectAgentSendChatMessageFlow() throws IOException, InterruptedException, XmppException {
        xmppAgent.connect();

        xmppClientWebWidget.connect();
        xmppClientWebWidget.joinRoom(room.getLocalpart());
        Assert.assertTrue(xmppClientWebWidget.waitForGreetingMessage(), "Client didn't receive grreting message within timeout");
        Tcard navigationCard = xmppClientWebWidget.getNavigationCard();
        Assert.assertNotNull(navigationCard, "Client didn't receive navigation card within timeout");
        Assert.assertEquals(navigationCard.getTcardName(), "navigation-card", "Client received wrong card " + navigationCard.getTcardName());
        NavigationCardModel navigationCardModel = navigationCard.getJsonCDATA(NavigationCardModel.class);

        xmppClientWebWidget.sendMessage("Chat to Support");
        Tcard inputCard = xmppClientWebWidget.getInputCard();
        Assert.assertNotNull(inputCard, "Client didn't receive input card after sending \"Chat to Support\" message");
        InputCardModel inputCardModel = inputCard.getJsonCDATA(InputCardModel.class);
        Message message = new Message(Jid.of(chatRoomResponse.getChatroomJid()));
        message.setId("cf314085-850f-e868-6401-90ee5714e" + StringUtils.generateRandomString(3));
        message.addExtension(new TcardSubmit(inputCardModel.getAction(),
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
        Assert.assertTrue(xmppClientWebWidget.waitForMessage("Hi, how can I help You"), "Client didn't receive message from agent");
        xmppClientWebWidget.sendMessage("hello2");
        Assert.assertTrue(xmppAgent.waitForMessage("hello2"), "Agent didn't receive message from client");

        xmppClientWebWidget.endChat();
        xmppClientWebWidget.disconnect();
        xmppAgent.leaveRoom();
        xmppAgent.disconnect();

    }


    @Test()
    public void connectAgentSubmitNavigationCardFlow() throws IOException, InterruptedException, XmppException {
        xmppAgent.connect();

        xmppClientWebWidget.connect();
        xmppClientWebWidget.joinRoom(room.getLocalpart());
        Assert.assertTrue(xmppClientWebWidget.waitForGreetingMessage(), "Client didn't receive grreting message within timeout");
        Tcard navigationCard = xmppClientWebWidget.getNavigationCard();
        Assert.assertNotNull(navigationCard, "Client didn't receive navigation card within timeout");
        Assert.assertEquals(navigationCard.getTcardName(), "navigation-card");
        NavigationCardModel navigationCardModel = navigationCard.getJsonCDATA(NavigationCardModel.class);

        Message submitNavigationCardMessage = new Message(Jid.of(chatRoomResponse.getChatroomJid()));
        submitNavigationCardMessage.setId("cf314085-850f-e868-6401-90ee5714e" + StringUtils.generateRandomString(3));
        TcardSubmit submitNavigationCard = new TcardSubmit();
        submitNavigationCard.setAction(navigationCardModel.getAction());
        submitNavigationCard.setId("card_3");
        submitNavigationCard.setTcardName(navigationCard.getTcardName());
        submitNavigationCard.setCDATA("Chat to Support");
        submitNavigationCardMessage.addExtension(submitNavigationCard);
        xmppClientWebWidget.sendMessage(submitNavigationCardMessage);
        Tcard inputCard = xmppClientWebWidget.getInputCard();
        Assert.assertNotNull(inputCard);
        InputCardModel inputCardModel = inputCard.getJsonCDATA(InputCardModel.class);
        Message submitInputCardMessage = new Message(Jid.of(chatRoomResponse.getChatroomJid()));
        submitInputCardMessage.setId("cf314085-850f-e868-6401-90ee5714e" + StringUtils.generateRandomString(3));
        submitInputCardMessage.addExtension(new TcardSubmit(inputCardModel.getAction(),
                inputCard.getTcardName(),
                "card_3",
                "<![CDATA[{\"inputdata\":[{\"name\":\"firstName\",\"value\":\"sdfsdfd\"},{\"name\":\"lastName\",\"value\":\"sdfsdfsdf\"},{\"name\":\"email\",\"value\":\"sdfsdf@dsfsd.fsdf\"},{\"name\":\"phone\",\"value\":\"324234234\"},{\"name\":\"company\",\"value\":\"sdfsdfsdfsdf\"}]}]]>"));
        submitInputCardMessage.setBody("Submitted data:\n" +
                "test\n" +
                "test\n" +
                "test@test.com\n" +
                "1234\n" +
                "test");
        xmppClientWebWidget.sendMessage(submitInputCardMessage);
        try {
            xmppClientWebWidget.waitForConnectinAgentMessage();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        Assert.assertTrue(xmppAgent.waitForOffer(), "Agent didn't receive offer!");
        xmppAgent.acceptOffer();
        xmppAgent.joinRoom();
        try {
            xmppClientWebWidget.waitForAgentConnectedMesasge();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        xmppAgent.sendMessage("Hi, how can I help You");
        Assert.assertTrue(xmppClientWebWidget.waitForMessage("Hi, how can I help You"), "Client didn't receive message from agent");
        xmppClientWebWidget.sendMessage("hello2");
        Assert.assertTrue(xmppAgent.waitForMessage("hello2"), "Agent didn't receive message from client");


        xmppClientWebWidget.endChat();
        xmppClientWebWidget.disconnect();
        xmppAgent.leaveRoom();
        xmppAgent.disconnect();
    }

    @Test()
    public void connectAgentTButtonFlow() throws IOException, InterruptedException, XmppException {
        xmppAgent.connect();

        xmppClientWebWidget.connect();
        xmppClientWebWidget.joinRoom(room.getLocalpart());
        Assert.assertTrue(xmppClientWebWidget.waitForGreetingMessage(), "Client didn't receive grreting message within timeout");
        Tcard navigationCard = xmppClientWebWidget.getNavigationCard();
        Assert.assertNotNull(navigationCard, "Client didn't receive navigation card within timeout");
        Assert.assertEquals(navigationCard.getTcardName(), "navigation-card");
        NavigationCardModel navigationCardModel = navigationCard.getJsonCDATA(NavigationCardModel.class);

        Message submitTButtonItemMessage = new Message(Jid.of(chatRoomResponse.getChatroomJid()));
        submitTButtonItemMessage.setId("cf314085-850f-e868-6401-90ee5714e" + StringUtils.generateRandomString(3));
        submitTButtonItemMessage.setBody("Chat to Support");
        TButtonItemSubmit tButtonItemSubmit = new TButtonItemSubmit();
        submitTButtonItemMessage.addExtension(tButtonItemSubmit);
        xmppClientWebWidget.sendMessage(submitTButtonItemMessage);
        Tcard inputCard = xmppClientWebWidget.getInputCard();
        Assert.assertNotNull(inputCard, "Input card was not received by agent, after submitting Chat to Support from TButton");
        InputCardModel inputCardModel = inputCard.getJsonCDATA(InputCardModel.class);
        Message submitInputCardMessage = new Message(Jid.of(chatRoomResponse.getChatroomJid()));
        submitInputCardMessage.setId("cf314085-850f-e868-6401-90ee5714e" + StringUtils.generateRandomString(3));
        submitInputCardMessage.addExtension(new TcardSubmit(inputCardModel.getAction(),
                inputCard.getTcardName(),
                "card_3",
                "<![CDATA[{\"inputdata\":[{\"name\":\"firstName\",\"value\":\"sdfsdfd\"},{\"name\":\"lastName\",\"value\":\"sdfsdfsdf\"},{\"name\":\"email\",\"value\":\"sdfsdf@dsfsd.fsdf\"},{\"name\":\"phone\",\"value\":\"324234234\"},{\"name\":\"company\",\"value\":\"sdfsdfsdfsdf\"}]}]]>"));
        submitInputCardMessage.setBody("Submitted data:\n" +
                "test\n" +
                "test\n" +
                "test@test.com\n" +
                "1234\n" +
                "test");
        xmppClientWebWidget.sendMessage(submitInputCardMessage);
        try {
            xmppClientWebWidget.waitForConnectinAgentMessage();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        Assert.assertTrue(xmppAgent.waitForOffer(), "Agent didn't receive offer!");
        xmppAgent.acceptOffer();
        xmppAgent.joinRoom();
        try {
            xmppClientWebWidget.waitForAgentConnectedMesasge();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        xmppAgent.sendMessage("Hi, how can I help You");
        Assert.assertTrue(xmppClientWebWidget.waitForMessage("Hi, how can I help You"), "Client didn't receive message from agent");
        xmppClientWebWidget.sendMessage("hello2");
        Assert.assertTrue(xmppAgent.waitForMessage("hello2"), "Agent didn't receive message from client");


        xmppClientWebWidget.endChat();
        xmppClientWebWidget.disconnect();
        xmppAgent.leaveRoom();
        xmppAgent.disconnect();
    }




    @Test
    public void checkInputCardMidFlowReaction() throws IOException, InterruptedException, XmppException {


        XMPPClient xmppClientWebWidget = new XMPPClient(clientId);
        xmppClientWebWidget.connect();
        xmppClientWebWidget.joinRoom(room.getLocalpart());
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

        xmppClientWebWidget.endChat();
        xmppClientWebWidget.disconnect();
        xmppAgent.leaveRoom();
        xmppAgent.disconnect();

    }

    @Test
    public void checkInputIsShownOncePerSession() throws InterruptedException, IOException {

        xmppClientWebWidget.connect();
        xmppClientWebWidget.joinRoom(room.getLocalpart());
        Assert.assertTrue(xmppClientWebWidget.waitForGreetingMessage(), "Client didn't receive grreting message within timeout");
        Tcard navigationCard = xmppClientWebWidget.getNavigationCard();
        Assert.assertNotNull(navigationCard, "Client didn't receive navigation card within timeout");
        Assert.assertEquals(navigationCard.getTcardName(), "navigation-card");
        NavigationCardModel navigationCardModel = navigationCard.getJsonCDATA(NavigationCardModel.class);

        xmppClientWebWidget.sendMessage("Chat to Support");
        Tcard inputCard = xmppClientWebWidget.getInputCard();
        Assert.assertNotNull(inputCard);
        InputCardModel inputCardModel = inputCard.getJsonCDATA(InputCardModel.class);
        Message message = new Message(Jid.of(chatRoomResponse.getChatroomJid()));
        message.setId("cf314085-850f-e868-6401-90ee5714e" + StringUtils.generateRandomString(3));
        message.addExtension(new TcardSubmit(inputCardModel.getAction(),
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
        xmppClientWebWidget.leaveRoom();

        //request new room to simulate user reconnection or page refresh
        chatRoomResponse = chatsActions.getChatRoom(tenantId, clientJid, clientId, "webchat", accessToken).as(ChatRoomResponse.class);
        room = BareJID.bareJIDInstance(chatRoomResponse.getChatroomJid());
        xmppClientWebWidget.connect();
        xmppClientWebWidget.joinRoom(room.getLocalpart());
        xmppClientWebWidget.sendMessage("Chat to Support");
        inputCard = xmppClientWebWidget.getInputCard();

        xmppClientWebWidget.endChat();
        xmppClientWebWidget.disconnect();

    }

    @Test
    public void inactiveTenantBusinessHoursTryAgainLaterFlow() throws InterruptedException, IOException {
        //get current day of week
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()).toUpperCase();

        MySQLConnector.getDbConnection()
                .updateTenantBusinessHours(tenantId, dayOfWeek, "00:00:00", "00:01:00");

        xmppClientWebWidget.connect();
        xmppClientWebWidget.joinRoom(room.getLocalpart());
        Assert.assertTrue(xmppClientWebWidget.waitForGreetingMessage(), "Client didn't receive grreting message within timeout");
        Tcard navigationCard = xmppClientWebWidget.getNavigationCard();
        Assert.assertNotNull(navigationCard, "Client didn't receive navigation card within timeout");
        Assert.assertEquals(navigationCard.getTcardName(), "navigation-card");
        NavigationCardModel navigationCardModel = navigationCard.getJsonCDATA(NavigationCardModel.class);

        xmppClientWebWidget.sendMessage("Chat to Support");
        navigationCard = xmppClientWebWidget.getNavigationCard();
        Assert.assertNotNull(navigationCard, "Client didn't receive navigation card within timeout");
        Assert.assertEquals(navigationCard.getTcardName(), "navigation-card");

        MySQLConnector.getDbConnection()
                .updateTenantBusinessHours(tenantId, dayOfWeek, "00:00:00", "23:59:59");

        xmppClientWebWidget.endChat();
        xmppClientWebWidget.disconnect();
    }

    @Test
    public void inactiveTenantBusinessHoursGetBackToMeFlow() throws InterruptedException, IOException {
        //get current day of week
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()).toUpperCase();

        MySQLConnector.getDbConnection()
                .updateTenantBusinessHours(tenantId, dayOfWeek, "00:00:00", "00:01:00");

        xmppClientWebWidget.connect();
        xmppClientWebWidget.joinRoom(room.getLocalpart());
        Assert.assertTrue(xmppClientWebWidget.waitForGreetingMessage(), "Client didn't receive grreting message within timeout");
        Tcard navigationCard = xmppClientWebWidget.getNavigationCard();
        Assert.assertNotNull(navigationCard, "Client didn't receive navigation card within timeout");
        Assert.assertEquals(navigationCard.getTcardName(), "navigation-card");
        NavigationCardModel navigationCardModel = navigationCard.getJsonCDATA(NavigationCardModel.class);

        xmppClientWebWidget.sendMessage("Chat to Support");

        MySQLConnector.getDbConnection()
                .updateTenantBusinessHours(tenantId, dayOfWeek, "00:00:00", "23:59:59");

        xmppClientWebWidget.endChat();
        xmppClientWebWidget.disconnect();
    }


    @Test()
    public void agentJoinedChatDidntRerspondDuringTimeout() throws IOException, InterruptedException, XmppException {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()).toUpperCase();

        MySQLConnector.getDbConnection()
                .updateTenantBusinessHours(tenantId, dayOfWeek, "00:00:00", "23:59:00");
        xmppAgent.connect();

        xmppClientWebWidget.connect();
        xmppClientWebWidget.joinRoom(room.getLocalpart());
        Assert.assertTrue(xmppClientWebWidget.waitForGreetingMessage(), "Client didn't receive grreting message within timeout");
        Tcard navigationCard = xmppClientWebWidget.getNavigationCard();
        Assert.assertNotNull(navigationCard, "Client didn't receive navigation card within timeout");
        Assert.assertEquals(navigationCard.getTcardName(), "navigation-card");
        NavigationCardModel navigationCardModel = navigationCard.getJsonCDATA(NavigationCardModel.class);

        xmppClientWebWidget.sendMessage("Chat to Support");
        Tcard inputCard = xmppClientWebWidget.getInputCard();
        Assert.assertNotNull(inputCard, "Client didn't receive input card after sending \"Chat to Support\" message");
        InputCardModel inputCardModel = inputCard.getJsonCDATA(InputCardModel.class);
        Message message = new Message(Jid.of(chatRoomResponse.getChatroomJid()));
        message.setId("cf314085-850f-e868-6401-90ee5714e" + StringUtils.generateRandomString(3));
        message.addExtension(new TcardSubmit(inputCardModel.getAction(),
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
        Thread.sleep(25000);
        Assert.assertTrue(xmppClientWebWidget.waitForMessage("Thanks for reaching out! It seems agent  is currently unavailable. Will find another one."));


        xmppClientWebWidget.endChat();
        xmppClientWebWidget.disconnect();
        xmppAgent.leaveRoom();
        xmppAgent.disconnect();

        MySQLConnector.getDbConnection()
                .updateTenantBusinessHours(tenantId, dayOfWeek, "00:00:00", "23:59:00");

    }

    @Test()
    public void noAgentAcceptsOfferTimeoutCardAppears() throws IOException, InterruptedException, XmppException {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()).toUpperCase();

        MySQLConnector.getDbConnection()
                .updateTenantBusinessHours(tenantId, dayOfWeek, "00:00:00", "23:59:00");
        xmppAgent.connect();

        xmppClientWebWidget.connect();
        xmppClientWebWidget.joinRoom(room.getLocalpart());
        Assert.assertTrue(xmppClientWebWidget.waitForGreetingMessage(), "Client didn't receive grreting message within timeout");
        Tcard navigationCard = xmppClientWebWidget.getNavigationCard();
        Assert.assertNotNull(navigationCard, "Client didn't receive navigation card within timeout");
        Assert.assertEquals(navigationCard.getTcardName(), "navigation-card");
        NavigationCardModel navigationCardModel = navigationCard.getJsonCDATA(NavigationCardModel.class);

        xmppClientWebWidget.sendMessage("Chat to Support");
        Tcard inputCard = xmppClientWebWidget.getInputCard();
        Assert.assertNotNull(inputCard, "Client didn't receive input card after sending \"Chat to Support\" message");
        InputCardModel inputCardModel = inputCard.getJsonCDATA(InputCardModel.class);
        Message message = new Message(Jid.of(chatRoomResponse.getChatroomJid()));
        message.setId("cf314085-850f-e868-6401-90ee5714e" + StringUtils.generateRandomString(3));
        message.addExtension(new TcardSubmit(inputCardModel.getAction(),
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

        Thread.sleep(25000);
        Assert.assertTrue(xmppClientWebWidget.waitForMessage("Thanks for reaching out! All our agents are currently busy."));


        xmppClientWebWidget.endChat();
        xmppClientWebWidget.disconnect();
        xmppAgent.leaveRoom();
        xmppAgent.disconnect();

    }

    @Test()
    public void timeoutCardGetCollapsedIfAgentJoins() throws IOException, InterruptedException, XmppException {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()).toUpperCase();

        MySQLConnector.getDbConnection()
                .updateTenantBusinessHours(tenantId, dayOfWeek, "00:00:00", "23:59:00");
        xmppAgent.connect();

        xmppClientWebWidget.connect();
        xmppClientWebWidget.joinRoom(room.getLocalpart());
        Assert.assertTrue(xmppClientWebWidget.waitForGreetingMessage(), "Client didn't receive grreting message within timeout");
        Tcard navigationCard = xmppClientWebWidget.getNavigationCard();
        Assert.assertNotNull(navigationCard, "Client didn't receive navigation card within timeout");
        Assert.assertEquals(navigationCard.getTcardName(), "navigation-card");
        NavigationCardModel navigationCardModel = navigationCard.getJsonCDATA(NavigationCardModel.class);

        xmppClientWebWidget.sendMessage("Chat to Support");
        Tcard inputCard = xmppClientWebWidget.getInputCard();
        Assert.assertNotNull(inputCard, "Client didn't receive input card after sending \"Chat to Support\" message");
        InputCardModel inputCardModel = inputCard.getJsonCDATA(InputCardModel.class);
        Message message = new Message(Jid.of(chatRoomResponse.getChatroomJid()));
        message.setId("cf314085-850f-e868-6401-90ee5714e" + StringUtils.generateRandomString(3));
        message.addExtension(new TcardSubmit(inputCardModel.getAction(),
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

        Thread.sleep(25000);
//        Assert.assertTrue(xmppClientWebWidget.waitForMessage("Thanks for reaching out! All our agents are currently busy."));

        xmppAgent.acceptOffer();
        xmppAgent.joinRoom();
        try {
            xmppClientWebWidget.waitForAgentConnectedMesasge();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        xmppAgent.sendMessage("Hi, how can I help You");
        Assert.assertTrue(xmppClientWebWidget.waitForMessage("Hi, how can I help You"));
        xmppClientWebWidget.sendMessage("hello2");
        Assert.assertTrue(xmppAgent.waitForMessage("hello2"));


        xmppClientWebWidget.endChat();
        xmppClientWebWidget.disconnect();
        xmppAgent.leaveRoom();
        xmppAgent.disconnect();

    }

    @Test()
    public void cancelOfferTest() throws IOException, InterruptedException, XmppException {
        xmppAgent.connect();

        xmppClientWebWidget.connect();
        xmppClientWebWidget.joinRoom(room.getLocalpart());
        Assert.assertTrue(xmppClientWebWidget.waitForGreetingMessage(), "Client didn't receive grreting message within timeout");
        Tcard navigationCard = xmppClientWebWidget.getNavigationCard();
        Assert.assertNotNull(navigationCard, "Client didn't receive navigation card within timeout");
        Assert.assertEquals(navigationCard.getTcardName(), "navigation-card");
        NavigationCardModel navigationCardModel = navigationCard.getJsonCDATA(NavigationCardModel.class);

        xmppClientWebWidget.sendMessage("Chat to Support");
        Tcard inputCard = xmppClientWebWidget.getInputCard();
        Assert.assertNotNull(inputCard, "Client didn't receive input card after sending \"Chat to Support\" message");
        InputCardModel inputCardModel = inputCard.getJsonCDATA(InputCardModel.class);
        Message message = new Message(Jid.of(chatRoomResponse.getChatroomJid()));
        message.setId("cf314085-850f-e868-6401-90ee5714e" + StringUtils.generateRandomString(3));
        message.addExtension(new TcardSubmit(inputCardModel.getAction(),
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
        xmppAgent.cancelOffer();


        xmppClientWebWidget.endChat();
        xmppClientWebWidget.disconnect();
        xmppAgent.leaveRoom();
        xmppAgent.disconnect();

    }

//    @AfterMethod
//    public void afterMethod() throws InterruptedException {
//        xmppClientWebWidget.endChat();
//        xmppClientWebWidget.disconnect();
//        xmppAgent.leaveRoom();
//    }

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