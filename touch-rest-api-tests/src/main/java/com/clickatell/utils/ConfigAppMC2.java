package com.clickatell.utils;

/**
 * Created by sbryt on 7/13/2016.
 */
public class ConfigAppMC2 {
    public static String BASE_API_URL;
    public static String USER_EMAIL;
    public static String USER_PASSWORD;
    public static String DB_URL;
    public static String DB_USER;
    public static String DB_PASSWORD;

    public static String DB_URL_PATTERN = "jdbc:mysql://mc2-platform-%s.clb9wu3x1zeb.us-west-2.rds.amazonaws.com:3306/%s?useSSL=false&autoReconnect=true&failOverReadOnly=false&maxReconnects=10";
    public static String BASE_URL_PATTERN = "http://%s.platform.clickatelllabs.com";


    static {
        String environment = System.getProperty("tests.env", "testing").toLowerCase();
        switch (environment) {
            case "qa": {
                BASE_API_URL = String.format(BASE_URL_PATTERN, "qa");
                DB_URL = String.format(DB_URL_PATTERN, "qa", "mc2_platform");
                break;
            }
            case "dev": {
                BASE_API_URL = String.format(BASE_URL_PATTERN, "dev");
                DB_URL = String.format(DB_URL_PATTERN, "dev", "mc2_platform");
                break;
            }
            case "demo": {
                BASE_API_URL = String.format(BASE_URL_PATTERN, "demo");
                DB_URL = String.format(DB_URL_PATTERN, "qa", "demo_mc2_platform");
                break;
            }
            case "testing": {
                BASE_API_URL = String.format(BASE_URL_PATTERN, "testing");
                DB_URL = String.format(DB_URL_PATTERN, "dev", "testing_mc2_platform");
                break;
            }
            default: {
                System.out.println("Please set 'tests.env' variable!\nPossible values are:\nqa\ndev\ndemo");
                System.exit(0);
            }
        }
        USER_EMAIL = System.getProperty("user.email", ApplicationProperties.getInstance().getPropertyByName("user.email"));
        USER_PASSWORD = System.getProperty("user.password", ApplicationProperties.getInstance().getPropertyByName("user.password"));
        DB_USER = System.getProperty("db.user", ApplicationProperties.getInstance().getPropertyByName("db.user"));
        DB_PASSWORD = System.getProperty("db.password", ApplicationProperties.getInstance().getPropertyByName("db.password"));
    }
}
