package com.touch.tests;

import com.touch.tests.extensions.*;
import com.touch.tests.xmppdebugger.AgentConsoleXmppLogger;
import com.touch.utils.StringUtils;
import com.touch.utils.TestingEnvProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rocks.xmpp.addr.Jid;
import rocks.xmpp.core.XmppException;
import rocks.xmpp.core.session.Extension;
import rocks.xmpp.core.session.TcpConnectionConfiguration;
import rocks.xmpp.core.session.XmppClient;
import rocks.xmpp.core.session.XmppSessionConfiguration;
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
public class XMPPAgent {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private TcpConnectionConfiguration tcpConfiguration;
    private XmppClient xmppClient;
    private List<Message> messages;
    private MultiUserChatManager multiUserChatManager;
    private ChatService chatService;
    private ChatRoom chatRoom;
    private XmppSessionConfiguration xmppSessionConfiguration;
    private OfferGeneral offerProposal;
    private IQ offerProposalIQ;
    private boolean offerReceived = false;
    private String xmppHost;
    private String xmppDomain;
    private String agentId;
    private String agentLogin;
    private String agentPassword;
    private String roomJid;
    private String roomJidLocalPart;

    public XMPPAgent(String agentLogin, String agentPassword){
        this.agentLogin = agentLogin;
        this.agentPassword = agentPassword;

        xmppHost = TestingEnvProperties.getPropertyByName("xmpp.host");
        xmppDomain = TestingEnvProperties.getPropertyByName("xmpp.domain");
        LOG.info("Creating Agent's XMPP client");
        LOG.info("XMPP client host = " + xmppHost);
        LOG.info("XMPP client domain = " + xmppDomain);

        agentId = "agent-" + StringUtils.generateRandomString(5);
        LOG.info("Agent room nick name = " + agentId);

        tcpConfiguration = TcpConnectionConfiguration.builder()
                .hostname(xmppHost)
                .port(5222)
                .build();

        xmppSessionConfiguration = XmppSessionConfiguration.builder()
                .extensions(Extension.of(SubmitPerosnalDataCard.class))
                .extensions(Extension.of(AgentStatus.class))
                .extensions(Extension.of(OfferGeneral.class))
                .extensions(Extension.of(OfferAccept.class))
                .debugger(AgentConsoleXmppLogger.class)
                .build();

        xmppClient = XmppClient.create(xmppDomain, xmppSessionConfiguration, tcpConfiguration);
        messages = new ArrayList<Message>();

        xmppClient.addInboundMessageListener(e -> {
            Message message = e.getMessage();
            messages.add(message);
            if ((message.getBody() != "") & !message.getFrom().getResource().equals(agentId)) {
                LOG.info("Agent received incoming message: " + message.getBody());
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

    }

    public void connect(){
        try {
            LOG.info("Agent " +  agentId + "connects to xmpp server");
            xmppClient.connect();
            xmppClient.login(agentLogin, agentPassword);
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

    public void leaveRoom(){
        LOG.info("Agent: " + agentId + " leaves room roomJid = " + roomJidLocalPart);
        chatRoom.exit();
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
        roomJid = offerProposal.getRoom();
        roomJidLocalPart = Jid.of(roomJid).getLocal();
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
        LOG.info("Agent - " + agentId + "joins MUC roomJid: " + roomJidLocalPart);
        chatRoom.enter(agentId);
    }


    public boolean waitForMessage(String messageText) throws InterruptedException {
        for (int i=0; i<=9; i++){
            for (Message message: messages){
                if (message.getBody().contains(messageText)){
                    return true;
                }

            }
            Thread.sleep(1000);
        }
        return false;
    }

    public void cleanMessagesStorage(){
        messages.clear();
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
