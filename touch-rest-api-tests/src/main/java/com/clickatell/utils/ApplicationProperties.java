package com.clickatell.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by sbryt on 6/10/2016.
 */
public class ApplicationProperties {
    private static ApplicationProperties ourInstance = new ApplicationProperties();

    public static ApplicationProperties getInstance() {
        return ourInstance;
    }

    Properties appProperties;

    private ApplicationProperties() {
        appProperties = new Properties();
        try {
            appProperties.load(ApplicationProperties.class.getResourceAsStream("/tests.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getAppProperties() {
        return appProperties;
    }

    public String getPropertyByName(String propName) {
        return appProperties.getProperty(propName);
    }
}
