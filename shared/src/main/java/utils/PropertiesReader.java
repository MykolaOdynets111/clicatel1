package utils;

import groovy.transform.Synchronized;

import java.io.InputStream;
import java.util.Properties;

import static java.lang.String.format;

public class PropertiesReader {

    private static final String CONFIG_PROPERTIES = "config.properties";

    private static Properties PROPERTIES;

    @Synchronized
    public static String getProperty(final String propertyName) {
        if (PROPERTIES == null) {
            try (InputStream reader = PropertiesReader.class
                    .getClassLoader()
                    .getResourceAsStream(CONFIG_PROPERTIES)) {

                Properties properties = new Properties();
                properties.load(reader);
                PROPERTIES = properties;
            } catch (Exception ex) {
                throw new IllegalStateException(format("An issue: '%s' occurred during reading config.properties file !!", ex.getMessage()));
            }
        }

        String systemProperty = System.getProperty(propertyName);

        return systemProperty == null
                ? PROPERTIES.getProperty(propertyName, format("Wrong config.properties name was set: '%s' !!", propertyName))
                : systemProperty;
    }
}
