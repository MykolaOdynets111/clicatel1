package dbManager;

import driverManager.ConfigManager;

import java.util.Arrays;
import java.util.List;

public enum DBProperties {
    TESTING_MC2("testing", "mc2", "mc2_testing", "mc2_int", "mc2_testing",
            "jdbc:mysql://mc2-db-testing.clickatelllabs.com:3306/mc2_testing?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true"),
    TESTING_TOUCH("testing", "touch", "touch_testing", "touch_testing", "testing_touch_platform",
            "jdbc:mysql://touch-db-testing.clickatelllabs.com:3306/testing_touch_platform?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true"),

    INTEGRATION_MC2("integration", "mc2", "mc2_int", "mc2_int", "integration_mc2_platform", "jdbc:mysql://mc2-db-integration.clickatelllabs.com:3306/integration_mc2_platform?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true"),
    INTEGRATION_TOUCH("integration", "touch", "touch_int", "touch_testing", "integration_touch_platform", "jdbc:mysql://touch-db-integration.clickatelllabs.com:3306/integration_touch_platform?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true"),

    DEV_TOUCH("dev", "touch", "touch_dev", "touch_dev", "integration_touch_platform", "jdbc:mysql://touch-db-dev.clickatelllabs.com:3306/dev_touch_platform?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true"),
    DEV_MC2("dev", "mc2", "mc2", "mc2_platform", "mc2_platform", "jdbc:mysql://mc2-platform-dev.clb9wu3x1zeb.us-west-2.rds.amazonaws.com:3306/mc2_platform?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true"),

    QA_TOUCH("qa", "touch", "touch_qa", "touch_qa", "qa_touch_platform", "jdbc:mysql://touch-db-qa.clickatelllabs.com:3306/qa_touch_platform?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true"),
    QA_MC2("qa", "mc2", "mc2", "mc2_platform", "mc2_platform", "jdbc:mysql://mc2-platform-qa.clb9wu3x1zeb.us-west-2.rds.amazonaws.com:3306/mc2_platform?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true"),

    DEMO_TOUCH("demo", "touch", "touch_demo", "touch_demo", "demo_touch_platform", "jdbc:mysql://touch-db-demo.clickatelllabs.com:3306/demo_touch_platform?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true"),
    DEMO_MC2("demo", "mc2", "mc2", "mc2_platform", "demo_mc2_platform", "jdbc:mysql://mc2-platform-qa.clb9wu3x1zeb.us-west-2.rds.amazonaws.com:3306/demo_mc2_platform?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true")

    ;

    String env;
    String platform;
    String user;
    String pass;
    String dbName;
    String url;

    DBProperties(String env, String  platform, String user, String pass, String dbName, String url) {
        this.env = env;
        this.platform = platform;
        this.user = user;
        this.pass = pass;
        this.dbName = dbName;
        this.url = url;
    }

    public String getDBName() {
        return this.dbName;
    }

    public String getEnv() {
        return this.env;
    }

    public String getPlatform() {
        return this.platform;
    }

    public String getURL() {
        return this.url;
    }

    public String getUser() {
        return this.user;
    }

    public String getPass() {
        return this.pass;
    }

    public static DBProperties getPropertiesFor(String env, String platform) {
        DBProperties[] dbsArray = DBProperties.values();
        List<DBProperties> agentsList = Arrays.asList(dbsArray);
        return agentsList.stream()
                .filter(e -> e.getEnv().equalsIgnoreCase(env)
                        && e.getPlatform().equalsIgnoreCase(platform))
                .findFirst().get();
    }
}
