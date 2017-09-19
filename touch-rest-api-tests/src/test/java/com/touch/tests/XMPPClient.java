package com.touch.tests;

import com.touch.tests.extensions.Tcard;
import com.touch.tests.extensions.SubmitPerosnalDataCard;
import com.touch.tests.xmppdebugger.ConsoleXmppLogger;
import com.touch.utils.TestingEnvProperties;
import org.slf4j.LoggerFactory;
import org.testng.log4testng.Logger;
import rocks.xmpp.core.XmppException;
import rocks.xmpp.core.session.Extension;
import rocks.xmpp.core.session.TcpConnectionConfiguration;
import rocks.xmpp.core.session.XmppClient;
import rocks.xmpp.core.session.XmppSessionConfiguration;
import rocks.xmpp.core.session.debug.ConsoleDebugger;
import rocks.xmpp.core.stanza.model.Message;
import rocks.xmpp.extensions.muc.ChatRoom;
import rocks.xmpp.extensions.muc.ChatService;
import rocks.xmpp.extensions.muc.MultiUserChatManager;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oshcherbatyy on 06-09-17.
 */
public class XMPPClient {

    private final org.slf4j.Logger LOG = LoggerFactory.getLogger(getClass());

    private String clientId;
    private String roomJidLocalPart;
    private TcpConnectionConfiguration tcpConfiguration;
    private XmppClient xmppClient;
    private List<Message> messages;
    private MultiUserChatManager multiUserChatManager;
    private ChatService chatService;
    private ChatRoom chatRoom;
    private XmppSessionConfiguration xmppSessionConfiguration;
    private String xmppHost;
    private String xmppDomain;

    public XMPPClient(){
        xmppHost = TestingEnvProperties.getPropertyByName("xmpp.host");
        xmppDomain = TestingEnvProperties.getPropertyByName("xmpp.domain");
        LOG.info("Creating XMPP client");
        LOG.info("XMPP client host = " + xmppHost);
        LOG.info("XMPP client domain = " + xmppDomain);
        tcpConfiguration = TcpConnectionConfiguration.builder()
                .hostname(xmppHost)
                .port(5222)
                .build();

        xmppSessionConfiguration = XmppSessionConfiguration.builder()
                .extensions(Extension.of(Tcard.class), Extension.of(SubmitPerosnalDataCard.class))
                .debugger(ConsoleXmppLogger.class)
                .build();

        xmppClient = XmppClient.create(xmppDomain, xmppSessionConfiguration, tcpConfiguration);
        messages = new ArrayList<Message>();

        xmppClient.addInboundMessageListener(e -> {
            Message message = e.getMessage();
            messages.add(message);
            if (message.getBody() != "") {
                LOG.info("Client received message :" + message.getBody());
            }

        });


    }

    public void connect(){
        try {
            LOG.info("Client atemptrs to log in to xmpp server");
            xmppClient.connect();
            xmppClient.loginAnonymously();
        } catch (XmppException e) {
            LOG.error("Client failed to login to xmpp server");
            e.printStackTrace();
        }
    }

    public void joinRoom(String clientId, String roomJidLocalPart){
        multiUserChatManager = xmppClient.getManager(MultiUserChatManager.class);
        List<ChatService> chatServices = null;
        try {
            chatServices = multiUserChatManager.discoverChatServices().getResult();
        } catch (XmppException e) {
            e.printStackTrace();
        }
        chatService = chatServices.get(0);
        chatRoom = chatService.createRoom(roomJidLocalPart);
        this.clientId = clientId;
        this.roomJidLocalPart = roomJidLocalPart;
        LOG.info("Client: " + clientId + " joins room roomJid = " + roomJidLocalPart);
        chatRoom.enter(clientId);
    }

    public void joinRoom(){
        multiUserChatManager = xmppClient.getManager(MultiUserChatManager.class);
        List<ChatService> chatServices = null;
        try {
            chatServices = multiUserChatManager.discoverChatServices().getResult();
        } catch (XmppException e) {
            e.printStackTrace();
        }
        chatService = chatServices.get(0);
        chatRoom = chatService.createRoom(roomJidLocalPart);
        LOG.info("Client: " + clientId + " joins room roomJid = " + roomJidLocalPart);
        chatRoom.enter(clientId);
    }

    public void leaveRoom(){
        LOG.info("Client: " + clientId + " joins room roomJid = " + roomJidLocalPart);
        chatRoom.exit();
    }



    public boolean waitForGreetingMessage() throws InterruptedException {
        for (int i=0; i<=9; i++){
            for (Message message: messages){
                if (message.getBody().contains("Hi there, welcome to Clickatell. How can we help you today?")){
                    return true;
                }

            }
            Thread.sleep(1000);
        }
        return false;
    }

    public boolean waitForConnectinAgentMessage() throws InterruptedException {
        for (int i=0; i<=9; i++){
            for (Message message: messages){
                if (message.getBody().contains("let me connect you with one of our agents")){
                    return true;
                }

            }
            Thread.sleep(1000);
        }
        return false;
    }

    public boolean waitForAgentConnectedMesasge() throws InterruptedException {
        for (int i=0; i<=9; i++){
            for (Message message: messages){
                if (message.getBody().contains("Agent  successfully joined!")){
                    return true;
                }

            }
            Thread.sleep(1000);
        }
        return false;
    }


    public Tcard getNavigationCard() throws InterruptedException {
        for (int i=0; i<=9; i++){
            for (Message message: messages){
                if (message.hasExtension(Tcard.class)) {
                    if (message.getExtension(Tcard.class).getTcardName().equals("navigation-card")) {
                        return message.getExtension(Tcard.class);
                    }
                }
            }
            Thread.sleep(1000);
        }
        return null;
    }

    public Tcard getInputCard() throws InterruptedException {
        for (int i=0; i<=9; i++){
            for (Message message: messages){
                if (message.hasExtension(Tcard.class)) {
                    if (message.getExtension(Tcard.class).getTcardName().equals("input-card")) {
                        return message.getExtension(Tcard.class);
                    }
                }
            }
            Thread.sleep(1000);
        }
        return null;
    }

    public boolean waitForMidFlowReactionMessage() throws InterruptedException {
        for (int i=0; i<=9; i++){
            for (Message message: messages){
                if (message.getBody().contains("Lets finish this up first")){
                    return true;
                }

            }
            Thread.sleep(1000);
        }
        return false;
    }


    public void sendMessage(String messageText){
        chatRoom.sendMessage(messageText);
    }

    public void sendMessage(Message message){
        chatRoom.sendMessage(message);
    }

}
