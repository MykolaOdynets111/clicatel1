package sqsreader;

/*
 * Copyright 2010-2022 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  https://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */

import com.amazon.sqs.javamessaging.AmazonSQSMessagingClientWrapper;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazonaws.util.Base64;
import com.fasterxml.jackson.databind.ObjectMapper;
import datamanager.jacksonschemas.orca.OrcaEvent;
import javaserver.OrcaServer;
import org.testng.Assert;

import javax.jms.*;
import java.io.IOException;
import java.util.*;

public class Common {

    public static volatile Map<String, Set<String>> orcaMessagesMap = new HashMap<>();
    public static volatile List<String> orcaMessages = new ArrayList<>();
    public static volatile List<OrcaEvent> orcaEvents = new ArrayList<>();

    /**
     * A utility function to check the queue exists and create it if needed. For most
     * use cases this is usually done by an administrator before the application is run.
     */
    public static void ensureQueueExists(SQSConnection connection, String queueName) throws JMSException {
        AmazonSQSMessagingClientWrapper client = connection.getWrappedAmazonSQSClient();

        /**
         * In most cases, you can do this with just a createQueue call, but GetQueueUrl
         * (called by queueExists) is a faster operation for the common case where the queue
         * already exists. Also many users and roles have permission to call GetQueueUrl
         * but don't have permission to call CreateQueue.
         */
        if( !client.queueExists(queueName) ) {
            client.createQueue( queueName );
        }
    }

    public static void handleMessage(Message message) throws JMSException {
        System.out.println( "Got message " + message.getJMSMessageID() );
        System.out.println( "Content: ");
        if( message instanceof TextMessage) {
            TextMessage txtMessage = ( TextMessage ) message;

            OrcaEvent orcaEvent = convertStringResponseBodyToObject(txtMessage.getText());
                OrcaServer.orcaMessagesMap.clear();
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
                    OrcaServer.orcaMessagesMap.put(orcaEvent.getSourceId(), currentMessages);
                    OrcaServer.orcaMessages.add(orcaEvent.getContent().getEvent().getText());
                }
            System.out.println( "\t" +"TextMessag: "+ txtMessage.getText() );
        } else if( message instanceof BytesMessage){
            BytesMessage byteMessage = ( BytesMessage ) message;
            // Assume the length fits in an int - SQS only supports sizes up to 256k so that
            // should be true
            byte[] bytes = new byte[(int)byteMessage.getBodyLength()];
            byteMessage.readBytes(bytes);
            System.out.println( "\t" + "BytesMessage: " + Base64.encodeAsString( bytes ) );
        } else if( message instanceof ObjectMessage) {
            ObjectMessage objMessage = (ObjectMessage) message;
            System.out.println( "\t" + "ObjectMessage: " + objMessage.getObject() );
        }
    }

    private static OrcaEvent convertStringResponseBodyToObject(String body) {
        for (String otherEvent : Arrays.asList("MEDIA", "NOTIFICATION", "TERMINATE", "OPT_OUT", "AUTH",
                "AUTH_STATUS", "TIMESLOT_PICKER", "PAYMENT_REQUEST", "PAYMENT_STATUS", "RICHLINK", "LIST_PICKER")) {
            if (body.contains(otherEvent) && !body.contains("STRUCTURED_TEXT_NOTIFICATION")) {
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