package drivermanager;

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
}
