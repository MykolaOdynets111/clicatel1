package drivermanager;

import java.util.Arrays;

public enum Environment {


    QA("qa"),
    DEMO("demo"),
    DEV("dev"),
    INTEGRATION("integration"),
    TESTING("testing");

    String env;

    Environment(String env) {
        this.env = env;
    }

    public String getEnv(){
        return env;
    }

    public static Environment fromString(String text) {
        return Arrays.stream(Environment.values())
                .filter(e -> e.getEnv().equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new AssertionError(text +  " environment is not supported"));
    }
}
