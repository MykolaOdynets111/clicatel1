package com.touch.tests;

import com.touch.tests.extensions.TButtonItemSubmit;
import com.touch.tests.extensions.Tcard;
import com.touch.tests.extensions.TcardSubmit;
import com.touch.tests.xmppdebugger.ClentConsoleXmppLogger;
import com.touch.utils.TestingEnvProperties;
import net.bytebuddy.implementation.bytecode.Throw;
import org.slf4j.LoggerFactory;
import rocks.xmpp.core.XmppException;
import rocks.xmpp.core.session.Extension;
import rocks.xmpp.core.session.TcpConnectionConfiguration;
import rocks.xmpp.core.session.XmppClient;
import rocks.xmpp.core.session.XmppSessionConfiguration;
import rocks.xmpp.core.stanza.model.Message;
import rocks.xmpp.extensions.muc.ChatRoom;
import rocks.xmpp.extensions.muc.ChatService;
import rocks.xmpp.extensions.muc.MultiUserChatManager;

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

    public XMPPClient(String clientId) {
        xmppHost = TestingEnvProperties.getPropertyByName("xmpp.host");
        xmppDomain = TestingEnvProperties.getPropertyByName("xmpp.domain");
        LOG.info("Creating XMPP client");
        LOG.info("XMPP client host = " + xmppHost);
        LOG.info("XMPP client domain = " + xmppDomain);

        this.clientId = clientId;
        LOG.info("Client room nick name = " + clientId);

        tcpConfiguration = TcpConnectionConfiguration.builder()
                .hostname(xmppHost)
                .port(5222)
                .build();

        xmppSessionConfiguration = XmppSessionConfiguration.builder()
                .extensions(Extension.of(Tcard.class), Extension.of(TcardSubmit.class), Extension.of(TButtonItemSubmit.class))
                .debugger(ClentConsoleXmppLogger.class)
                .build();

        xmppClient = XmppClient.create(xmppDomain, xmppSessionConfiguration, tcpConfiguration);
        messages = new ArrayList<Message>();

        xmppClient.addInboundMessageListener(e -> {
            Message message = e.getMessage();
            messages.add(message);
            if ((message.getBody() != "") & !message.getFrom().getResource().equals(clientId)) {
                LOG.info("Client received message :" + message.getBody());
            }

        });


    }

    public void connect() throws InterruptedException {
        for (int i = 0; i <= 2; i++) {
            try {
                LOG.info("Client " + clientId + "connects to xmpp server");
                xmppClient.connect();
                xmppClient.loginAnonymously();
                return;
            } catch (Exception e) {
                LOG.error("Client failed to connect to xmpp server, retrying");
            }
            Thread.sleep(100);
        }
        LOG.error("Client failed to connect to xmpp server for 3 times");

    }

    public void joinRoom(String roomJidLocalPart) {
        multiUserChatManager = xmppClient.getManager(MultiUserChatManager.class);
        List<ChatService> chatServices = null;
        try {
            chatServices = multiUserChatManager.discoverChatServices().getResult();
        } catch (XmppException e) {
            e.printStackTrace();
        }
        chatService = chatServices.get(0);
        chatRoom = chatService.createRoom(roomJidLocalPart);
        this.roomJidLocalPart = roomJidLocalPart;
        LOG.info("Client: " + clientId + " joins room roomJid = " + roomJidLocalPart);
        chatRoom.enter(clientId);
    }

    public void joinRoom() {
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

    public void leaveRoom() {
        //clear messages history
        messages.clear();
        LOG.info("Client: " + clientId + " leaves room roomJid = " + roomJidLocalPart);
        chatRoom.exit();
    }

    public void restartFlow() {
        chatRoom.sendMessage("/flow:restart");
    }


    public boolean waitForGreetingMessage() throws InterruptedException {
        for (int i = 0; i <= 9; i++) {
            for (Message message : messages) {
                if (message.getBody().contains("Hi there, welcome to Clickatell. How can we help you today?")) {
                    return true;
                }

            }
            Thread.sleep(1000);
        }
        return false;
    }

    public boolean waitForConnectinAgentMessage() throws InterruptedException {
        for (int i = 0; i <= 9; i++) {
            for (Message message : messages) {
                if ((message != null) & (message.getBody() != null)) {
                    if (message.getBody().contains("let me connect you with one of our agents")) {
                        return true;
                    }
                }

            }
            Thread.sleep(1000);
        }
        return false;
    }

    public boolean waitForAgentConnectedMesasge() throws InterruptedException {
        for (int i = 0; i <= 9; i++) {
            for (Message message : messages) {
                if ((message != null) & (message.getBody() != null)) {
                    if (message.getBody().contains("Agent  successfully joined!")) {
                        return true;
                    }
                }

            }
            Thread.sleep(1000);
        }
        return false;
    }

    public boolean waitForMessage(String messageText) throws InterruptedException {
        for (int i = 0; i <= 9; i++) {
            for (Message message : messages) {
                if ((message != null) & (message.getBody() != null)) {
                    if (message.getBody().contains(messageText)) {
                        return true;
                    }
                }
            }
            Thread.sleep(1000);
        }
        return false;
    }

    public void cleanMessagesStorage() {
        messages.clear();
    }


    public Tcard getNavigationCard() throws InterruptedException {
        for (int i = 0; i <= 9; i++) {
            for (Message message : messages) {
                if ((message != null) & (message.getBody() != null)) {
                    if (message.hasExtension(Tcard.class)) {
                        if (message.getExtension(Tcard.class).getTcardName().equals("navigation-card")) {
                            messages.clear();
                            return message.getExtension(Tcard.class);
                        }
                    }
                }
            }
            Thread.sleep(1000);
        }
        return null;
    }


    public Tcard getInputCard() throws InterruptedException {
        for (int i = 0; i <= 9; i++) {
            for (Message message : messages) {
                if ((message != null) & (message.getBody() != null)) {
                    if (message.hasExtension(Tcard.class)) {
                        if (message.getExtension(Tcard.class).getTcardName().equals("input-card")) {
                            return message.getExtension(Tcard.class);
                        }
                    }
                }
            }
            Thread.sleep(1000);
        }
        return null;
    }

    public void disconnect() {
        try {
            xmppClient.close();
        } catch (XmppException e) {
            LOG.error("Error when disconnection client from xmpp server: " + e.getMessage());
        }
    }

    public boolean waitForMidFlowReactionMessage() throws InterruptedException {
        for (int i = 0; i <= 9; i++) {
            for (Message message : messages) {
                if ((message != null) & (message.getBody() != null)) {
                    if (message.getBody().contains("Lets finish this up first")) {
                        return true;
                    }
                }
            }
            Thread.sleep(1000);
        }
        return false;
    }


    public void sendMessage(String messageText) {
        chatRoom.sendMessage(messageText);
    }

    public void sendMessage(Message message) {
        chatRoom.sendMessage(message);
    }

}
