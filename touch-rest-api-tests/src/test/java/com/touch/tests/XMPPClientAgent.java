package com.touch.tests;

import com.touch.tests.extensions.*;
import com.touch.utils.StringUtils;
import com.touch.utils.TestingEnvProperties;
import rocks.xmpp.addr.Jid;
import rocks.xmpp.core.XmppException;
import rocks.xmpp.core.session.Extension;
import rocks.xmpp.core.session.TcpConnectionConfiguration;
import rocks.xmpp.core.session.XmppClient;
import rocks.xmpp.core.session.XmppSessionConfiguration;
import rocks.xmpp.core.session.debug.ConsoleDebugger;
import rocks.xmpp.core.stanza.IQHandler;
import rocks.xmpp.core.stanza.model.IQ;
import rocks.xmpp.core.stanza.model.Message;
import rocks.xmpp.core.stanza.model.Presence;
import rocks.xmpp.extensions.muc.ChatRoom;
import rocks.xmpp.extensions.muc.ChatService;
import rocks.xmpp.extensions.muc.MultiUserChatManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oshcherbatyy on 06-09-17.
 */
public class XMPPClientAgent {

    private TcpConnectionConfiguration tcpConfiguration;
    private XmppClient xmppClient;
    private List<Message> messages;
    private MultiUserChatManager multiUserChatManager;
    private ChatService chatService;
    private ChatRoom chatRoom;
    private boolean listen;
    private boolean newMessages;
    private XmppSessionConfiguration xmppSessionConfiguration;
    private OfferGeneral offerProposal;
    private OfferAccepted offerAccepted;
    private IQ offerProposalIQ;

    public XMPPClientAgent(){
        tcpConfiguration = TcpConnectionConfiguration.builder()
                .hostname(TestingEnvProperties.getPropertyByName("xmpp.host"))
                .port(5222)
                .build();

        xmppSessionConfiguration = XmppSessionConfiguration.builder()
                .extensions(Extension.of(SubmitPerosnalDataCard.class))
                .extensions(Extension.of(AgentStatus.class))
                .extensions(Extension.of(OfferGeneral.class))
                .extensions(Extension.of(OfferAccept.class))
                .debugger(ConsoleDebugger.class)
                .build();

        xmppClient = XmppClient.create(TestingEnvProperties.getPropertyByName("xmpp.domain"), xmppSessionConfiguration, tcpConfiguration);
        messages = new ArrayList<Message>();


        xmppClient.addInboundMessageListener(e -> {
            Message message = e.getMessage();
            messages.add(message);
                System.out.println("#################################################");
                System.out.println(message.getBody());
                System.out.println(message.toString());
                System.out.println("######################################################");

        });

        xmppClient.addIQHandler(OfferGeneral.class, new IQHandler() {
            @Override
            public IQ handleRequest(IQ iq) {
                offerProposalIQ = iq;
                offerProposal = iq.getExtension(OfferGeneral.class);
                if (offerProposal.getStatus() == null){
                    System.out.println("########################OFFER RECEIVED " + offerProposal.getId() + " ##################");
                    joinRoom(offerProposal.getRoom());
                }
                else{
                    System.out.println("########################OFFER STATUS RECEIVED " + offerProposal.getStatus() + " ##################");
                }
                return null;
            }
        });


        try {
            xmppClient.connect();
            xmppClient.login("ff8080815e5607e3015e577ec72f0005", "yl6*709p$zp_d!h6t*2!ns/6!9z-778/=7m72r#z9*s*#x9t4/b#r*53b+i@+/81");
            List<AgentStatus> extensions = new ArrayList<AgentStatus>();
            extensions.add(new AgentStatus("5") );
            xmppClient.sendPresence( new Presence(Jid.of("clickatell@department.clickatelllabs.com"),
                    null,
                    Presence.Show.CHAT,
                    null,
                    null,
                    null,
                    null,
                    null,
                    extensions,
                    null
                    )
            );
        } catch (XmppException e) {
            e.printStackTrace();
        }

    }

    public void joinRoom(String roomJid){
        System.out.println("########################OFFER PROCESSED##################");
        offerProposal = offerProposalIQ.getExtension(OfferGeneral.class);
        Jid myJid = offerProposalIQ.getTo();
        xmppClient.sendIQ(new IQ(Jid.of("clickatell@department.clickatelllabs.com"),
                IQ.Type.SET, new OfferAccept(offerProposal.getId()),
                "cf314085-850f-e868-6401-90ee5714e" + StringUtils.generateRandomString(3),
                myJid,
                null,
                null));
        System.out.println("#############JOINING ROOM####################");
        multiUserChatManager = xmppClient.getManager(MultiUserChatManager.class);
        List<ChatService> chatServices = null;
        try {
            chatServices = multiUserChatManager.discoverChatServices().getResult();
        } catch (XmppException e) {
            e.printStackTrace();
        }
        chatService = chatServices.get(0);
        chatRoom = chatService.createRoom(Jid.of(roomJid).getLocal());
        chatRoom.enter("agent-" + StringUtils.generateRandomString(5));
        chatRoom.sendMessage("Hello client");
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

    public void sendMessage(String messageText){
        chatRoom.sendMessage(messageText);
    }

    public void sendMessage(Message message){
        chatRoom.sendMessage(message);
    }


    public static void main(String[] args) {

    }
}
