package sqsreader;

import lombok.Data;
import software.amazon.awssdk.regions.Region;

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
    private Region region = DEFAULT_REGION;
    private static String callbackUrl = DEFAULT_CALLBACK_URL;

    public static String getCallbackUrl() {
        return callbackUrl;
    }

}