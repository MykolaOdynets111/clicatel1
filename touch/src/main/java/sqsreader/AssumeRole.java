package sqsreader;

import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;
import software.amazon.awssdk.services.sts.model.AssumeRoleResponse;
import software.amazon.awssdk.services.sts.model.Credentials;
import software.amazon.awssdk.services.sts.model.StsException;

import java.util.Objects;

/**
 * To make this code example work, create a Role that you want to assume.
 * Then define a Trust Relationship in the AWS Console. You can use this as an example:
 * <p>
 * {
 * "Version": "2012-10-17",
 * "Statement": [
 * {
 * "Effect": "Allow",
 * "Principal": {
 * "AWS": "<Specify the ARN of your IAM user you are using in this code example>"
 * },
 * "Action": "sts:AssumeRole"
 * }
 * ]
 * }
 * <p>
 * For more information, see "Editing the Trust Relationship for an Existing Role" in the AWS Directory Service guide.
 * <p>
 * Also, set up your development environment, including your credentials.
 * <p>
 * For information, see this documentation topic:
 * <p>
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */

public class AssumeRole {

    public static Credentials assumeGivenRole(String roleArn, String roleSessionName) {
        Credentials myCreds = null;

        StsClient stsClient = StsClient.builder()
                .region(SQSConfiguration.DEFAULT_REGION)
                .build();
        try {
            AssumeRoleRequest roleRequest = AssumeRoleRequest.builder()
                    .roleArn(roleArn)
                    .roleSessionName(roleSessionName)
                    .build();

            AssumeRoleResponse roleResponse = stsClient.assumeRole(roleRequest);
            myCreds = roleResponse.credentials();

//            // Display the time when the temp creds expire.
//            Instant exTime = myCreds.expiration();
//            String tokenInfo = myCreds.sessionToken();
//
//            // Convert the Instant to readable date.
//            DateTimeFormatter formatter =
//                    DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
//                            .withLocale(Locale.US)
//                            .withZone(ZoneId.systemDefault());
//
//            formatter.format(exTime);
//            System.out.println("The token " + tokenInfo + "  expires on " + exTime);

        } catch (StsException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return Objects.requireNonNull(myCreds);
    }
}
