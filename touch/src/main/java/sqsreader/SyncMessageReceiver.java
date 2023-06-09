package sqsreader;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazon.sqs.javamessaging.SQSSession;
import software.amazon.awssdk.services.sqs.SqsClient;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SyncMessageReceiver {


    public void startSQSReader() {

        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

        clientProcessingPool.execute(
                () -> {
                    try {
                        startSyncMessageReceiver();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                    clientProcessingPool.shutdownNow();
                    try {
                        clientProcessingPool.awaitTermination(8, TimeUnit.SECONDS);
                    } catch (Exception ignored) {
                    }
                }
        );

    }

    public void startSyncMessageReceiver() throws JMSException {
        System.out.println("Start SQS consuming process");

        SqsClient sqsClient = SQSConfiguration.getSqsClient();

        SQSConnectionFactory connectionFactory = new SQSConnectionFactory(
                new ProviderConfiguration(),
                sqsClient);

        // Create the connection
        SQSConnection connection = connectionFactory.createConnection();

        // Create the queue if needed
//        Common.ensureQueueExists(connection, config.getQueueName());

        Session session =  connection.createSession(false, SQSSession.CLIENT_ACKNOWLEDGE);

        MessageConsumer consumer = session.createConsumer( session.createQueue( SQSConfiguration.getQueueName() ) );

        connection.start();

        receiveMessages(consumer);

        connection.close();

        System.out.println("Connection closed");
    }

    private void receiveMessages(MessageConsumer consumer ) {
        SQSConfiguration.running.set(true);
        try {
            while( SQSConfiguration.running.get() ) {
                System.out.println( "Waiting for messages");

                Message message = consumer.receive(TimeUnit.MINUTES.toMillis(5));

                if( message == null ) {
                    System.out.println( "Shutting down after 5 minute of silence" );
                    break;
                }

                OrcaSQSHandler.handleMessage(message);

                message.acknowledge();
            }
        } catch (JMSException e) {
            System.err.println( "Error receiving from SQS: " + e.getMessage() );
            e.printStackTrace();
        }
        System.out.println("Shutting down SQS connection");
    }
}