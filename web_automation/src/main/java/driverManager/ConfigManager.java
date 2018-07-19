package driverManager;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class ConfigManager {

    private static final String REMOTE_FLAG_VARIABLE = "remote";
    private static final String BROWSER_NAME = "browser";
    private static final String ENV = "env";
    private static final String DEPLOY_TO = "deploy_to";

    public static boolean isRemote() {
        String remoteValue = System.getProperty(REMOTE_FLAG_VARIABLE);

        return remoteValue != null ? Boolean.parseBoolean(remoteValue) : false;
    }

    public static DriverType getDriverType() {
        DriverType driverType = DriverType.CHROME;
        String browserName = System.getProperty(BROWSER_NAME);

        if (!StringUtils.isEmpty(browserName)) {
            driverType = DriverType.from(browserName);
        }
        return driverType;
    }

    public static String getEnv() {
        String env = System.getProperty(ENV);
        String deployTo = System.getProperty(DEPLOY_TO);
        if(env==null) env = "testing";
        if(deployTo==null) return env;
        else{
            if (deployTo.equalsIgnoreCase("standby_group")) {
                env = "standby-"+env;
            }
            System.out.println("!!! Will run tests on env: "+env+"");
            return env;
        }
    }
}
