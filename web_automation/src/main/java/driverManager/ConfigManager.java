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
//        String env =

        if (System.getProperty(ENV)==null) {
            return "testing";
        } else {
            return System.getProperty(ENV);
        }
    }
}
