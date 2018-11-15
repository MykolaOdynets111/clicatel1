package testNGtest_helper;

import driverManager.ConfigManager;

import java.util.Arrays;
import java.util.List;

public enum AQAHeathTest {

    BASE_BOT_CONNECTION_TEST("bot connection", "Widget should connect and bot should respond"),
    BASE_TO_AGENT_CARD_TEST("redirection to agent", "User should be able to provide personal info before going to the agent"),

    ;

    String shortName;
    String fullName;


    AQAHeathTest(String shortName, String fullName) {
        this.shortName = shortName;
        this.fullName = fullName;

    }

    public static String getAQATestDescriptionByShortName(String shortName) {
        AQAHeathTest[] agentsArray = AQAHeathTest.values();
        List<AQAHeathTest> agentsList = Arrays.asList(agentsArray);
        return agentsList.stream()
                .filter(e -> e.getShortName().equalsIgnoreCase(shortName))
                .findFirst().get()
                .getFullName();
    }

    public String getShortName() {
        return shortName;
    }

    public String getFullName() {
        return fullName;
    }

}
