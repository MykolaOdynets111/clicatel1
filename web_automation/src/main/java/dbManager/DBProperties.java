package dbManager;

import driverManager.ConfigManager;

import java.util.Arrays;
import java.util.List;

public enum DBProperties {
    TESTING_MC2("testing", "mc2", "mc2_testing", "mc2_testing", "jdbc:mysql://mc2-db-testing.clickatelllabs.com:3306/mc2_testing?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true"),
    TESTING_TOUCH("testing", "touch", "testing_touch", "testing_touch_platform", "jdbc:mysql://touch-db-testing.clickatelllabs.com:3306/testing_touch_platform?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true"),

    INTEGRATION_MC2("integration", "mc2", "mc2_int", "integration_mc2_platform", "jdbc:mysql://mc2-db-integration.clickatelllabs.com:3306/integration_mc2_platform?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true"),
    INTEGRATION_TOUCH("integration", "touch", "touch_int", "integration_touch_platform", "jdbc:mysql://touch-db-integration.clickatelllabs.com:3306/integration_touch_platform?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true")
   ;

    String env;
    String platform;
    String creds;
    String dbName;
    String url;

    DBProperties(String env, String  platform, String creds, String dbName, String url) {
        this.env = env;
        this.platform = platform;
        this.creds = creds;
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

    public String getCreds() {
        return this.creds;
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
