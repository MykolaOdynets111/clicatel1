package utils;

import lombok.experimental.UtilityClass;

import java.io.FileWriter;
import java.io.IOException;

import static java.lang.String.format;
import static utils.PropertiesReader.getProperty;

@UtilityClass
public class ConfigurationProvider {
    private static FileWriter fileWriter;
    private static final String ENVIRONMENT_PATH = "src/test/resources/config.dev.properties";

    public static void setApiConfiguration() {
        try {
            fileWriter = new FileWriter(ENVIRONMENT_PATH);
            fileWriter.write(format("Environment: %s \n", getProperty("environment")));

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
