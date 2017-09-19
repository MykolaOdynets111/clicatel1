package com.touch.tests;

import com.touch.tests.extensions.*;
import com.touch.tests.xmppdebugger.ConsoleXmppLogger;
import com.touch.utils.StringUtils;
import com.touch.utils.TestingEnvProperties;
import com.touch.utils.reporter.CustomReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oshcherbatyy on 06-09-17.
 */
public class XMPPAgent {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

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
    private boolean offerReceived = false;
    private String xmppHost;
    private String xmppDomain;

    public XMPPAgent(){
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
                .extensions(Extension.of(SubmitPerosnalDataCard.class))
                .extensions(Extension.of(AgentStatus.class))
                .extensions(Extension.of(OfferGeneral.class))
                .extensions(Extension.of(OfferAccept.class))
                .debugger(ConsoleXmppLogger.class)
                .build();

        xmppClient = XmppClient.create(xmppDomain, xmppSessionConfiguration, tcpConfiguration);
        messages = new ArrayList<Message>();

        xmppClient.addInboundMessageListener(e -> {
            Message message = e.getMessage();
            messages.add(message);
            if (message.getBody() != "") {
                LOG.info("Agent receeived incoming message: " + message.getBody());
            }
        });

        xmppClient.addIQHandler(OfferGeneral.class, new IQHandler() {
            @Override
            public IQ handleRequest(IQ iq) {
                offerProposalIQ = iq;
                offerProposal = iq.getExtension(OfferGeneral.class);
                if (offerProposal.getStatus() == null){
                    LOG.info("Agent received offerID : " + offerProposal.getId() + "for roomJid: " + offerProposal.getRoom());
                    offerReceived = true;
                }
                else{
                    LOG.info("Agent received offer status: " + offerProposal.getStatus());
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

    public boolean waitForOffer() throws InterruptedException {
        for (int i=0; i<=9; i++){
                if (offerReceived){
                    return true;
            }
            Thread.sleep(1000);
        }
        return false;
    }

    public void acceptOffer(){
        LOG.info("Agent accepted offer offerId: " + offerProposal.getId());
        offerProposal = offerProposalIQ.getExtension(OfferGeneral.class);
        Jid myJid = offerProposalIQ.getTo();
        xmppClient.sendIQ(new IQ(Jid.of("clickatell@department.clickatelllabs.com"),
                IQ.Type.SET, new OfferAccept(offerProposal.getId()),
                "cf314085-850f-e868-6401-90ee5714e" + StringUtils.generateRandomString(3),
                myJid,
                null,
                null));
    }

    public void joinRoom(){
        String roomJid = offerProposal.getRoom();
        String roomJidLocalPart = Jid.of(roomJid).getLocal();
        LOG.info("Agent joins roomJid: " + roomJidLocalPart);
        multiUserChatManager = xmppClient.getManager(MultiUserChatManager.class);
        List<ChatService> chatServices = null;
        try {
            chatServices = multiUserChatManager.discoverChatServices().getResult();
        } catch (XmppException e) {
            LOG.error("Agent unable to discover xmpp chat services");
            e.printStackTrace();
        }
        chatService = chatServices.get(0);
        chatRoom = chatService.createRoom(roomJidLocalPart);
        String agentNick = "agent-" + StringUtils.generateRandomString(5);
        LOG.info("Agent - " + agentNick + "joins MUC roomJid: " + roomJidLocalPart);
        chatRoom.enter(agentNick);
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

    public void sendMessage(String messageText){
        chatRoom.sendMessage(messageText);
    }

    public void sendMessage(Message message){
        chatRoom.sendMessage(message);
    }

}
