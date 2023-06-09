package dbmanager;

import drivermanager.Environment;

import java.util.Arrays;
import java.util.List;
import static drivermanager.Environment.*;


public enum DBProperties {
    TESTING_MC2(TESTING, "mc2", "mc2_testing", "mc2_testing", "mc2_testing", "jdbc:mysql://mc2-db-testing.clickatelllabs.com:3306/mc2_testing?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true"),
    TESTING_TOUCH(TESTING, "touch", "touch_testing", "touch_testing", "testing_touch_platform", "jdbc:mysql://touch-db-testing.clickatelllabs.com:3306/testing_touch_platform?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true"),

    INTEGRATION_MC2(INTEGRATION, "mc2", "mc2_int", "mc2_int", "integration_mc2_platform", "jdbc:mysql://mc2-db-integration.clickatelllabs.com:3306/integration_mc2_platform?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true"),
    INTEGRATION_TOUCH(INTEGRATION, "touch", "touch_int", "touch_int", "integration_touch_platform", "jdbc:mysql://touch-db-integration.clickatelllabs.com:3306/integration_touch_platform?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true"),

    DEV_TOUCH(DEV, "touch", "touch_dev", "touch_dev", "dev_touch_platform", "jdbc:mysql://touch-db-dev.clickatelllabs.com:3306/dev_touch_platform?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true"),
    DEV_MC2(DEV, "mc2", "mc2", "mc2_platform", "mc2_platform", "jdbc:mysql://mc2-platform-dev.clb9wu3x1zeb.us-west-2.rds.amazonaws.com:3306/mc2_platform?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true"),

    QA_TOUCH(QA, "touch", "touch_qa", "touch_qa", "qa_touch_platform", "jdbc:mysql://touch-db-qa.clickatelllabs.com:3306/qa_touch_platform?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true"),
    QA_MC2(QA, "mc2", "mc2", "mc2_platform", "mc2_platform", "jdbc:mysql://mc2-platform-qa.clb9wu3x1zeb.us-west-2.rds.amazonaws.com:3306/mc2_platform?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true"),

    DEMO_TOUCH(DEMO, "touch", "w.chatdesk_platform", "I&m2$k7bYc", "demo_chatdesk_platform", "jdbc:mysql://chatd-db-primary-01a.shared-dev.eu-west-1.aws.clickatell.com:3306/demo_chatdesk_platform?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true"),
    DEMO_MC2(DEMO, "mc2", "mc2", "mc2_platform", "demo_mc2_platform", "jdbc:mysql://db-primary-01.mc2-dev.eu-west-1.aws.clickatell.com:4001/demo_mc2_platform?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true"),

//    STAGE_MC2_LOCAL(STAGE, "mc2", "t.qa.automation", "T2ytm2t78bx2", "mc2_prod", "jdbc:mysql://db-primary-01a.platform.stage.eu-west-1.aws.clickatell.com:3308"), //For debug only
    STAGE_MC2_REMOTE(STAGE, "mc2", "dev", "iamgroot", "mc2_prod", "jdbc:mysql://db-primary-01-nlb-7464f67ab3a4f79b.elb.eu-west-1.amazonaws.com:4001/mc2_prod?characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true"),

    ;

    Environment env;
    String platform;
    String user;
    String pass;
    String dbName;
    String url;

    DBProperties(Environment env, String  platform, String user, String pass, String dbName, String url) {
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
        return this.env.getEnv();
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
                .findFirst()
                .orElseThrow(() ->
                        new AssertionError("Cannot get properties for desired configuration:\n" + "env: " + env +"\n" + "platform: " + platform));
    }
}
