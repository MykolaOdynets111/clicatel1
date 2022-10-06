package sqsreader;

import com.fasterxml.jackson.databind.ObjectMapper;
import datamanager.jacksonschemas.orca.OrcaEvent;
import org.testng.Assert;

import javax.jms.*;
import java.io.IOException;
import java.util.*;

public class OrcaSQSHandler {

    public static volatile Map<String, Set<String>> orcaMessagesMap = new HashMap<>();
    public static volatile List<String> orcaMessages = new ArrayList<>();
    public static volatile List<OrcaEvent> orcaEvents = new ArrayList<>();


    public static void handleMessage(Message message) throws JMSException {
        System.out.println( "Got message " + message.getJMSMessageID() );
        System.out.println( "Content: ");
        if( message instanceof TextMessage) {
            TextMessage txtMessage = ( TextMessage ) message;

            OrcaEvent orcaEvent = convertStringResponseBodyToObject(txtMessage.getText());
                OrcaSQSHandler.orcaMessagesMap.clear();
                //for avoiding NullPointerException in the future
                orcaEvents.add(orcaEvent);
                if (orcaEvent.getContent() != null &&
                        orcaEvent.getContent().getEvent() != null &&
                        orcaEvent.getContent().getEvent().getText() != null &&
                        !orcaEvent.getContent().getEvent().getText().isEmpty()) {
                    Set<String> currentMessages = orcaMessagesMap.get(orcaEvent.getSourceId());
                    if (Objects.isNull(currentMessages))
                        currentMessages = new HashSet<>();
                    currentMessages.add(orcaEvent.getContent().getEvent().getText());
                    OrcaSQSHandler.orcaMessagesMap.put(orcaEvent.getSourceId(), currentMessages);
                    OrcaSQSHandler.orcaMessages.add(orcaEvent.getContent().getEvent().getText());
                }
        } else if( message instanceof BytesMessage){
            BytesMessage byteMessage = ( BytesMessage ) message;
            // Assume the length fits in an int - SQS only supports sizes up to 256k so that
            // should be true
            byte[] bytes = new byte[(int)byteMessage.getBodyLength()];
            byteMessage.readBytes(bytes);
            System.out.println("\t" + "BytesMessage: " + Base64.getEncoder().encodeToString(bytes));
        } else if( message instanceof ObjectMessage) {
            ObjectMessage objMessage = (ObjectMessage) message;
            System.out.println( "\t" + "ObjectMessage: " + objMessage.getObject() );
        }
    }

    private static OrcaEvent convertStringResponseBodyToObject(String body) {
        for (String otherEvent : Arrays.asList("MEDIA", "NOTIFICATION", "TERMINATE", "OPT_OUT", "AUTH",
                "AUTH_STATUS", "TIMESLOT_PICKER", "PAYMENT_REQUEST", "PAYMENT_STATUS", "RICHLINK",
                "UNSTRUCTURED_TEXT_NOTIFICATION", "LIST_PICKER")) {
            if (body.contains(otherEvent) && !body.contains("\"STRUCTURED_TEXT_NOTIFICATION\"")) {
                return new OrcaEvent();
            }
        }

        if ((body.charAt(0) + "").equals("[")) {
            body = body.replace("[", "");
            body = body.replace("]", "");
        }
        System.out.println("Orca Incoming body :" + body);
        OrcaEvent orcaEvent = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            orcaEvent = mapper.readValue(body, OrcaEvent.class);
        } catch (IOException e) {
            Assert.fail(e.getMessage() + "\nIncorrect schema of response from Orca \n" + "Catched Body " + body);
        }

        return orcaEvent;
    }
}