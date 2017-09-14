package com.touch.utils;


/**
 * Created by sbryt on 7/13/2016.
 */
public class ConfigApp {
    public static String BASE_API_URL;
    public static String DB_URL;
    public static String DB_USER;
    public static String DB_PASSWORD;
    public static String API_VERSION ="/v6";

    public static String DB_URL_PATTERN = "jdbc:mysql://mc2-platform-%s.clb9wu3x1zeb.us-west-2.rds.amazonaws.com:3306/%s?useSSL=false&autoReconnect=true&failOverReadOnly=false&maxReconnects=10";
    public static String DB_URL_PATTERN_TESTING = "jdbc:mysql://mc2-db-testing.clickatelllabs.com:3306/mc2_testing?useSSL=false&autoReconnect=true&failOverReadOnly=false&maxReconnects=10";
//    public static String DB_URL_PATTERN_TESTING = "jdbc:mysql://ec2-35-161-212-158.us-west-2.compute.amazonaws.com:3306/mc2_testing?useSSL=false&autoReconnect=true&failOverReadOnly=false&maxReconnects=10";
    public static String BASE_URL_PATTERN = "https://%s-touch.clickatelllabs.com";


    static {
        String environment = System.getProperty("tests.env", "testing").toLowerCase();
        switch (environment) {

            case "dev": {
                BASE_API_URL = String.format(BASE_URL_PATTERN, "dev");
                DB_URL = String.format(DB_URL_PATTERN, "dev", "mc2_platform");
                TestingEnvProperties.setPropertiesFile("dev.properties");
                DB_USER = "mc2";
                DB_PASSWORD = "mc2_platform";
                break;
            }

            case "testing": {
                BASE_API_URL = String.format(BASE_URL_PATTERN, "testing");
//                DB_URL = String.format(DB_URL_PATTERN, "testing", "mc2_testing");
                DB_URL = DB_URL_PATTERN_TESTING;
                TestingEnvProperties.setPropertiesFile("testing.properties");
                DB_USER = "mc2_testing";
                DB_PASSWORD = "mc2_testing";
                break;
            }

            case "qa": {
                BASE_API_URL = String.format(BASE_URL_PATTERN, "qa");
                TestingEnvProperties.setPropertiesFile("qa.properties");
                break;
            }


            default: {
                System.out.println("Please set 'tests.env' variable!\nPossible values are:\ndev");
                System.exit(0);
            }
        }

    }
}
