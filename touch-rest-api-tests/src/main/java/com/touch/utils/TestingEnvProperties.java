package com.touch.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by sbryt on 6/10/2016.
 */
public class TestingEnvProperties {
    static Properties appProperties;
    public static void setPropertiesFile(String propertiesFile) {
        new TestingEnvProperties(propertiesFile);
    }

    private TestingEnvProperties(String propertiesFile) {
        appProperties = new Properties();
        try {
            appProperties.load(TestingEnvProperties.class.getResourceAsStream("/"+propertiesFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getAppProperties() {
        return appProperties;
    }

    public static  String getPropertyByName(String propName) {
        return appProperties.getProperty(propName);
    }
}
