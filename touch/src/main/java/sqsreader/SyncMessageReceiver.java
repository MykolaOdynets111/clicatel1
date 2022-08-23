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

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

public class SyncMessageReceiver {

    protected static boolean running = true;

    public static void stopServer() {
        running = false;
    }

    public static void main(String args[]) throws JMSException {
        Configuration config = Configuration.parseConfig("SyncMessageReceiver", args);

        BasicSessionCredentials creds = new BasicSessionCredentials("ASIATEJ7Q4NW7ZZTS2CE","y+2M4xvn+ADfcYpHqAGm/0ds2NytHDzHW1qyKIeE",
                "IQoJb3JpZ2luX2VjEP///////////wEaCWV1LXdlc3QtMSJGMEQCIDRTo9SVvQNRH8ZIwxWHewparxR+WT8Ign4RBNY07+vcAiA8QOxLAACKE4xXefWJBfGZqa6kWqYCG2p204G1zfmO/SqqAwh4EAEaDDIxNTQxODQ2MzA4NSIM9V23Qi1vzt7k+C9MKocD2Lvy2XQ8L6RLodZGLOUv6yAQYHTZuVdSvWgYlHbV0gviuausAAIk73KLvkdHOfj3my7HkRe0eluDkqI+lNzf/sJywo8580lsoF0piW4fL2k4xJRf1wwBfRoFKthoy8gGDJAqNTmIbD7wrS/90NDn4q0syKUIFYebMgUFCjODKz/d+1QHulwT9PBlZg7ELJqKyKhZibPLVcw9rihMXuoxcL9oN3T7iltHM4i9QVvg1RA59QmC55UsFGnO+pdBeu0e+JCGNqtqP3DUXc2hzRHmcBX1IGOpXOfIcVAI6fKBUzWUYUHgTV84tGLJ3V17iRONn3xUzSeYUMuzVjtOJWkNkePGy1pggP/xQQoWu8XP3saVe1z7E2Ug1btLJ1HxwXeJahzDoKxXMZCw/hIu8TKkTEAU5yhXKacWGy+5vaExQcMevV1FGMDyaF2MMAzaOt5WNFS0SsdS/V54VdEeZfmiS1ruRvFCYWVwP3G56kxJO0/ah72WEe1fzv3p0jQjf+tlW6SV0+qhxzCXqo6YBjqnAdOJndpcDIIHx3xtgzPGWpqYECO5wrGPAeVnz7UlOtezNm2J/Spbj7NEPZeP55nqpLD47knXDdlFnIwnryq0Vtz+T6jAezmKJNo1DzrgnFWQS8fbdxOEVeSRb72rNuu5DI5SFXzCZ9zEBS7igZUzeG27HpVJBgvpxYgD8WX/bQtfJOJZD5pKAihakwCfmGvHiMqNCvN34uc6KOWJ2JnZ5WVhV72ZsWDi");
        // Create the connection factory based on the config
        SQSConnectionFactory connectionFactory = new SQSConnectionFactory(
                new ProviderConfiguration(),
                AmazonSQSClientBuilder.standard()
                        .withRegion(config.getRegion().getName())
                        .withCredentials(new AWSStaticCredentialsProvider(creds)));

        // Create the connection
        SQSConnection connection = connectionFactory.createConnection();

        // Create the queue if needed
//        Common.ensureQueueExists(connection, config.getQueueName());

        // Create the session
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

        MessageConsumer consumer = session.createConsumer( session.createQueue( config.getQueueName() ) );

        connection.start();

        receiveMessages(session, consumer);

        // Close the connection. This closes the session automatically
        connection.close();
        System.out.println( "Connection closed" );
    }


    private static void receiveMessages( Session session, MessageConsumer consumer ) {
        try {
            while( true ) {
                System.out.println( "Waiting for messages");
                // Wait 1 minute for a message
                Message message = consumer.receive();
                if(!running) {
                    System.out.println( "Shutting down after 1 minute of silence" );
                    break;
                }
                Common.handleMessage(message);
                message.acknowledge();
                System.out.println( "Acknowledged message " + message.getJMSMessageID() );
            }
        } catch (JMSException e) {
            System.err.println( "Error receiving from SQS: " + e.getMessage() );
            e.printStackTrace();
        }
    }
}