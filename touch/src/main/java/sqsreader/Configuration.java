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

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

public class Configuration {
    public static final String DEFAULT_QUEUE_NAME = "dev-callback-handler-interact-chatdesk-1";

    public static final Region DEFAULT_REGION = Region.getRegion(Regions.EU_WEST_1);
//    SET AWS_ACCESS_KEY_ID=ASIATEJ7Q4NWVCFBP37Q
//    SET AWS_SECRET_ACCESS_KEY=0h6NgLlfL0QcKXOAS4KPiNvXYUVLlPWeBqbQzzTA
//    SET AWS_SESSION_TOKEN=IQoJb3JpZ2luX2VjEJ///////////wEaCWV1LXdlc3QtMSJHMEUCIQDm8VyoijHl/a5SL8i9Vg4P6KBy27iI9oAM9+2f066JVgIgRSZpfgju4GjuLaC++NTWFxp7LXd5BnEK2N89jv4kkqYqqgMIGBABGgwyMTU0MTg0NjMwODUiDGlpQy8WC476VAdsQiqHA2miTaFNo4IhH+4I0n0wZwW4L/CKe0RJADYhGN40p9xt/kWylXfmaXGpt5moBTSF5mF7wD+kG5wSuqdrB0qoaBccRquDV7523PVmcfRryNSHj5e8bREG5+cpzOigic2EyZjH78H0OCn+PR4g3CJKYCGHOlrCGoXXy5M+XlM0TTduvMF3tRoooCYuL0uqSRF2FdiRUKH8+sCBRiqjvrGPetkRiU55dpKDTERh1OfIJHWx6BJyX3nUA+17dYermT/KTW0HAfUf+mQ5K/Rvl8+jKgWYCZNGYGwMLOBHr69ahh5g/87o/UEzMum1ORXxwomeYmsQ7Jsbhs120AMPzXwqU8xyUtYaD1WfcolwtdwfxKf8DwdTVuoIF5Jk42+PYos5R6y9aYxZPh1gsqRXnfea1PLC78k/nZJiqkXHWPAh6I+aavC7PvwqyfLYipNi9Nz/1yDuY1yyNQDU7i7FvIVyV1fs6ryd22wk/35vIkAZ6QfHey12MSYWuoBEa7g8WXHpt8I7JMoVESowjZb5lwY6pgExUIzu+lpO2+18pgxZIhYxLjuGpxCHfTif0z0QbV0zhYfLQUPkmCSnXDSYOP1RWpDpHOksYl8vK/AJ+x/fTd8BRaLm1ieuCDy0SFqG9At7j+lZyE52Avrdbpii4uhActLL2aLRcIs7+s/xv3qMy3Qch0sWiQWIABHNYuA7jesEq59rFg6qmJGUOANKXdWuwrE684QZ21ZYfpFO5r+a/Cu6s28W4jxK

    private static String getParameter( String args[], int i ) {
        if( i + 1 >= args.length ) {
            throw new IllegalArgumentException( "Missing parameter for " + args[i] );
        }
        return args[i+1];
    }

    /**
     * Parse the command line and return the resulting config. If the config parsing fails
     * print the error and the usage message and then call System.exit
     *
     * @param app the app to use when printing the usage string
     * @param args the command line arguments
     * @return the parsed config
     */
    public static Configuration parseConfig(String app, String args[]) {
        return new Configuration();
//        try {
//            return new Configuration(args);
//        } catch (IllegalArgumentException e) {
//            System.err.println( "ERROR: " + e.getMessage() );
//            System.err.println();
//            System.err.println( "Usage: " + app + " [--queue <queue>] [--region <region>] [--credentials <credentials>] ");
//            System.err.println( "  or" );
//            System.err.println( "       " + app + " <spring.xml>" );
//            System.exit(-1);
//            return null;
//        }
    }

    private Configuration(){}

    private Configuration(String args[]) {
        for( int i = 0; i < args.length; ++i ) {
            String arg = args[i];
            if( arg.equals( "--queue" ) ) {
                setQueueName(getParameter(args, i));
                i++;
            } else if( arg.equals( "--region" ) ) {
                String regionName = getParameter(args, i);
                try {
                    setRegion(Region.getRegion(Regions.fromName(regionName)));
                } catch( IllegalArgumentException e ) {
                    throw new IllegalArgumentException( "Unrecognized region " + regionName );
                }
                i++;
            } else if( arg.equals( "--credentials" ) ) {
                String credsFile = getParameter(args, i);
                try {
                    setCredentialsProvider( new PropertiesFileCredentialsProvider(credsFile) );
                } catch (AmazonClientException e) {
                    throw new IllegalArgumentException("Error reading credentials from " + credsFile, e );
                }
                i++;
            } else {
                throw new IllegalArgumentException("Unrecognized option " + arg);
            }
        }
    }

    private String queueName = DEFAULT_QUEUE_NAME;
    private Region region = DEFAULT_REGION;
    private AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public AWSCredentialsProvider getCredentialsProvider() {
        return credentialsProvider;
    }

    public void setCredentialsProvider(AWSCredentialsProvider credentialsProvider) {
        // Make sure they're usable first
        credentialsProvider.getCredentials();
        this.credentialsProvider = credentialsProvider;
    }
}