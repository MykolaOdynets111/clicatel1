package utils;

import lombok.Synchronized;
import lombok.var;

import java.util.Properties;

import static java.lang.String.format;


public class PropertiesReader {
    private PropertiesReader() {
    }

    private static final String CONFIG_PROPERTIES = "config.dev.properties";

    private static Properties PROPERTIES;

    @Synchronized
    public static String getProperty(final String propertyName) {
        if (PROPERTIES == null) {
            try (var reader = PropertiesReader.class
                    .getClassLoader()
                    .getResourceAsStream(CONFIG_PROPERTIES)) {

                var properties = new Properties();
                properties.load(reader);
                PROPERTIES = properties;
            } catch (Exception ex) {
                throw new IllegalStateException(format("An issue: '%s' occurred during reading config.properties file !!", ex.getMessage()));
            }
        }

        var systemProperty = System.getProperty(propertyName);

        return systemProperty == null
                ? PROPERTIES.getProperty(propertyName, format("Wrong config.properties name was set: '%s' !!", propertyName))
                : systemProperty;
    }
}
