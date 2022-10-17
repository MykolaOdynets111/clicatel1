package sqsreader;

import drivermanager.ConfigManager;
import lombok.Data;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sts.model.Credentials;

@Data
public class SQSConfiguration {

    public static volatile int SERVER_INDEX = 0;

    public static final String DEFAULT_QUEUE_NAME = "dev-callback-handler-interact-chatdesk-";

    public static final String ROLE_ARN = "arn:aws:iam::215418463085:role/k8s-dev-callback-handler-interact-chatdesk-access-role";

    public static final String ROLE_SESSION_NAME = "mySession";

    public static final Region DEFAULT_REGION = Region.EU_WEST_1;

    public static final String DEFAULT_CALLBACK_URL = "https://j7q5gdrxs0.execute-api.eu-west-1.amazonaws.com/Internal/interact/chat-desk/";

    protected static ThreadLocal<Boolean> running = new ThreadLocal<>();

    public static void stopSQSReader() {
        running.set(false);
    }

    public static String getCallbackUrl() {
        if(ConfigManager.isSQSUsed()){
            return DEFAULT_CALLBACK_URL + SERVER_INDEX;
        }
        return "https://aqadummycallback.com";
    }

    public static synchronized String getQueueName(){
        SERVER_INDEX +=1;
        if (SERVER_INDEX==10){
            SERVER_INDEX=1;
        }
        return DEFAULT_QUEUE_NAME + SERVER_INDEX;
    }

    public static SqsClient getSqsClient(){
        if(ConfigManager.isRemote()){
            Credentials roleCreds = AssumeRole.assumeGivenRole(ROLE_ARN,ROLE_SESSION_NAME);
            AwsCredentials creds  = AwsSessionCredentials.create(roleCreds.accessKeyId(),roleCreds.secretAccessKey(),roleCreds.sessionToken());
            return SqsClient.builder().credentialsProvider(StaticCredentialsProvider.create(creds)).build();
        }

        return SqsClient.builder().region(SQSConfiguration.DEFAULT_REGION)
                .credentialsProvider(ProfileCredentialsProvider.create("215418463085_vulcan-mc2-dev"))
                .build();
    }
}