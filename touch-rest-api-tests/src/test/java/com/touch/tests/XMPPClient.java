package com.touch.tests;

import com.touch.tests.extensions.Tcard;
import com.touch.tests.extensions.SubmitPerosnalDataCard;
import com.touch.utils.TestingEnvProperties;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oshcherbatyy on 06-09-17.
 */
public class XMPPClient {

    private TcpConnectionConfiguration tcpConfiguration;
    private XmppClient xmppClient;
    private List<Message> messages;
    private MultiUserChatManager multiUserChatManager;
    private ChatService chatService;
    private ChatRoom chatRoom;
    private boolean listen;
    private boolean newMessages;
    private XmppSessionConfiguration xmppSessionConfiguration;

    public XMPPClient(String clientId, String roomJidLocalPart){
        tcpConfiguration = TcpConnectionConfiguration.builder()
                .hostname(TestingEnvProperties.getPropertyByName("xmpp.host"))
                .port(5222)
                .build();

        xmppSessionConfiguration = XmppSessionConfiguration.builder()
                .extensions(Extension.of(Tcard.class), Extension.of(SubmitPerosnalDataCard.class))
                .debugger(ConsoleDebugger.class)
                .build();

        xmppClient = XmppClient.create(TestingEnvProperties.getPropertyByName("xmpp.domain"), xmppSessionConfiguration, tcpConfiguration);
        messages = new ArrayList<Message>();


        xmppClient.addInboundMessageListener(e -> {
            Message message = e.getMessage();
            messages.add(message);
            if (message.hasExtension(Tcard.class)) {
                Tcard tcard = message.getExtension(Tcard.class);
                System.out.println("#################################################");
                System.out.println(tcard.getData());
            }
                System.out.println("#################################################");
                System.out.println(message.getBody());
                System.out.println(message.toString());
                System.out.println("######################################################");

        });
        try {
            xmppClient.connect();
            xmppClient.loginAnonymously();
        } catch (XmppException e) {
            e.printStackTrace();
        }
        multiUserChatManager = xmppClient.getManager(MultiUserChatManager.class);
        List<ChatService> chatServices = null;
        try {
            chatServices = multiUserChatManager.discoverChatServices().getResult();
        } catch (XmppException e) {
            e.printStackTrace();
        }
        chatService = chatServices.get(0);
        chatRoom = chatService.createRoom(roomJidLocalPart);
        chatRoom.enter(clientId);
        listen = true;
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

    public void sendMessage(String messageText){
        chatRoom.sendMessage(messageText);
    }

    public void sendMessage(Message message){
        chatRoom.sendMessage(message);
    }

    public void shutdown(){
        listen = false;
    }

}
