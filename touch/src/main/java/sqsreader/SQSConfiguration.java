package sqsreader;

import drivermanager.ConfigManager;
import lombok.Data;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Data
public class SQSConfiguration {

    public static final String DEFAULT_QUEUE_NAME = "dev-callback-handler-interact-chatdesk-1";

    public static final Region DEFAULT_REGION = Region.EU_WEST_1;

    public static final String DEFAULT_CALLBACK_URL = "https://j7q5gdrxs0.execute-api.eu-west-1.amazonaws.com/Internal/interact/chat-desk/1";

    protected static boolean running = true;

    public static void stopSQSReader() {
        running = false;
    }

    public static SQSConfiguration parseConfig() {
        return new SQSConfiguration();
    }

    private SQSConfiguration() {
    }

    private String queueName = DEFAULT_QUEUE_NAME;
    private static String callbackUrl = DEFAULT_CALLBACK_URL;

    public static String getCallbackUrl() {
        return callbackUrl;
    }

    public String getQueueName(){
        if(ConfigManager.isRemote()){
            return "arn:aws:sqs:eu-west-1:215418463085:dev-callback-handler-interact-chatdesk-1";
        }
        return queueName;
    }

    public static SqsClient getSqsClient(){
        if(ConfigManager.isRemote()){
            return SqsClient.builder().build();
        }
        return SqsClient.builder().region(SQSConfiguration.DEFAULT_REGION)
                .credentialsProvider(ProfileCredentialsProvider.create("215418463085_vulcan-mc2-dev"))
                .build();
    }

}