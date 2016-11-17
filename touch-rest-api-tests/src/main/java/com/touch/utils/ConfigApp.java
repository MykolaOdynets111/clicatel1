package com.touch.utils;

import com.clickatell.utils.*;

/**
 * Created by sbryt on 7/13/2016.
 */
public class ConfigApp {
    public static String BASE_API_URL;
    public static String USER_EMAIL;
    public static String USER_PASSWORD;
    public static String DB_URL;
    public static String DB_USER;
    public static String DB_PASSWORD;
    public static String API_VERSION ="/v5";

    public static String DB_URL_PATTERN = "jdbc:mysql://mc2-platform-%s.clb9wu3x1zeb.us-west-2.rds.amazonaws.com:3306/%s?useSSL=false&autoReconnect=true&failOverReadOnly=false&maxReconnects=10";
    public static String DB_URL_PATTERN_TESTING = "jdbc:mysql://mc2-db-testing.clickatelllabs.com:3306/mc2_testing?useSSL=false&autoReconnect=true&failOverReadOnly=false&maxReconnects=10";
//    public static String DB_URL_PATTERN_TESTING = "jdbc:mysql://ec2-35-161-212-158.us-west-2.compute.amazonaws.com:3306/mc2_testing?useSSL=false&autoReconnect=true&failOverReadOnly=false&maxReconnects=10";
    public static String BASE_URL_PATTERN = "https://%s-touch.clickatelllabs.com";


    static {
        String environment = System.getProperty("tests.env", "testing").toLowerCase();
        switch (environment) {
            case "qa": {
                BASE_API_URL = String.format(BASE_URL_PATTERN, "qa");
                DB_URL = String.format(DB_URL_PATTERN, "qa", "qa_mc2_touch");
                TestingEnvProperties.setPropertiesFile("qa.properties");
                break;
            }
            case "dev": {
                BASE_API_URL = String.format(BASE_URL_PATTERN, "dev");
                DB_URL = String.format(DB_URL_PATTERN, "dev", "mc2_touch");
                TestingEnvProperties.setPropertiesFile("dev.properties");
                break;
            }
            case "demo": {
                BASE_API_URL = String.format(BASE_URL_PATTERN, "demo");
                DB_URL = String.format(DB_URL_PATTERN, "qa", "demo_mc2_touch");
                TestingEnvProperties.setPropertiesFile("demo.properties");
                break;
            }
            case "testing": {
                BASE_API_URL = String.format(BASE_URL_PATTERN, "testing");
//                DB_URL = String.format(DB_URL_PATTERN, "testing", "mc2_testing");
                DB_URL = DB_URL_PATTERN_TESTING;
                TestingEnvProperties.setPropertiesFile("testing.properties");
                break;
            }
            case "integration": {
                BASE_API_URL = String.format(BASE_URL_PATTERN, "integration");
                DB_URL = String.format(DB_URL_PATTERN, "integration", "mc2_touch");
                TestingEnvProperties.setPropertiesFile("qa.properties");
                break;
            }
            case "prod": {
                BASE_API_URL = "https://touch.clickatelllabs.com";
                DB_URL = String.format(DB_URL_PATTERN, "prod", "mc2_touch");
                TestingEnvProperties.setPropertiesFile("prod.properties");
                break;
            }
            default: {
                System.out.println("Please set 'tests.env' variable!\nPossible values are:\nqa\ndev\ndemo11");
                System.exit(0);
            }
        }
        USER_EMAIL = System.getProperty("user.email", com.clickatell.utils.ApplicationProperties.getInstance().getPropertyByName("user.email"));
        USER_PASSWORD = System.getProperty("user.password", com.clickatell.utils.ApplicationProperties.getInstance().getPropertyByName("user.password"));
        if (environment.equals("testing")) {
            DB_USER = "mc2_testing";
            DB_PASSWORD = "mc2_testing";
        } else {
            DB_USER = System.getProperty("db.user", com.clickatell.utils.ApplicationProperties.getInstance().getPropertyByName("db.user"));
            DB_PASSWORD = System.getProperty("db.password", com.clickatell.utils.ApplicationProperties.getInstance().getPropertyByName("db.password"));
        }
    }
}
