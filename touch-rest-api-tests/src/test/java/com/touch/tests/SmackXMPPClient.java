package com.touch.tests;

import com.touch.utils.TestingEnvProperties;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.filter.StanzaTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatException;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Resourcepart;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.jivesoftware.smackx.filetransfer.FileTransfer.Error.connection;

/**
 * Created by oshcherbatyy on 13-09-17.
 */
public class SmackXMPPClient {

    private List<Message> messages;
    private XMPPTCPConnectionConfiguration config;
    AbstractXMPPConnection connenction;
    MultiUserChatManager manager;
    MultiUserChat chatRoom;

    public SmackXMPPClient(String clientId, String roomJid) throws XmppStringprepException, SmackException.NotConnectedException, InterruptedException, MultiUserChatException.NotAMucServiceException, XMPPException.XMPPErrorException, SmackException.NoResponseException {
        messages = new ArrayList<Message>();

        config = XMPPTCPConnectionConfiguration.builder()
                .setXmppDomain(TestingEnvProperties.getPropertyByName("xmpp.domain"))
                .setHost(TestingEnvProperties.getPropertyByName("xmpp.host"))
                .setPort(5222)
                .allowEmptyOrNullUsernames()
                .performSaslAnonymousAuthentication()
                .setDebuggerEnabled(true)
                .build();


        connenction = new XMPPTCPConnection(config);

        try {
            connenction.connect();
            connenction.login();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        StanzaFilter filter = new StanzaTypeFilter(Message.class);

        StanzaListener myListener = new StanzaListener() {
            public void processStanza(Stanza stanza) {
//                System.out.println("#################################################");
//                System.out.println(stanza.toString());
//                System.out.println("######################################################");
            }
        };

        connenction.addAsyncStanzaListener(myListener, filter);

        manager = MultiUserChatManager.getInstanceFor(connenction);

        chatRoom = manager.getMultiUserChat(JidCreate.entityBareFrom(roomJid));


        chatRoom.addMessageListener(new MessageListener() {
            @Override
            public void processMessage(Message message) {
                messages.add(message);
                System.out.println("#################################################");
                System.out.println(message.getBody());
                System.out.println(message.toString());
                System.out.println(message.toXML());
                if (message.hasExtension("tcard", "jabber:client")){
                    System.out.println("EXTENSION FOUND!!!!!!!!!!!!!!!!!");
                    System.out.println(message.getExtension("tcard", "jabber:client").toXML());
                }
                System.out.println("######################################################");
            }
        });
        chatRoom.join(Resourcepart.from(clientId));
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

    public void sendMessage(String messageText) throws SmackException.NotConnectedException, InterruptedException {
        chatRoom.sendMessage(messageText);
    }

    public void sendMessage(Message message) throws SmackException.NotConnectedException, InterruptedException {
        chatRoom.sendMessage(message);
    }

}
