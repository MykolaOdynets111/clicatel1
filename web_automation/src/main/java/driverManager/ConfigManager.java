package driverManager;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;

public class ConfigManager {

    private static final String REMOTE_FLAG_VARIABLE = "remote";
    private static final String BROWSER_TYPE = "browsertype";
    private static final String ENV = "env";
    private static final String DEPLOY_TO = "deploy_to";
    private static final String TENANT_ORG_NAME = "tenantorgname";
    private static final String TENANT_ID = "tenantid";
    private static final String FACEBOOK_PASS = "fbuserpass";
    private static final String SUITE = "suite";


    public static void setTenantId(String tenantId) {
        System.setProperty("tenantid", tenantId);
    }

    public static void setBrowserType(String browserType) {
        System.setProperty("browsertype", browserType);
    }

    public static void setEnv(String env) {
        System.setProperty("env", env);
    }


    public static boolean isRemote() {
        String remoteValue = System.getProperty(REMOTE_FLAG_VARIABLE);

        return remoteValue != null && Boolean.parseBoolean(remoteValue);
    }

    public static DriverType getDriverType() {
        DriverType driverType = DriverType.CHROME;
        String browserType = System.getProperty(BROWSER_TYPE);

        if (!StringUtils.isEmpty(browserType)) {
            driverType = DriverType.from(browserType);
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

    public static String getTenantOrgName(){
        String tenantOrgName = System.getProperty(TENANT_ORG_NAME);
        if(tenantOrgName==null){
            Assert.assertTrue(false,
                    "Tenant org name was not provided");
        }
        return tenantOrgName;
    }

    public static String getID(){
        String tenantID = System.getProperty(TENANT_ID);
        if(tenantID==null){
            Assert.assertTrue(false,
                    "Tenant ID was not provided");
        }
        return tenantID;
    }

    public static  String getFBUserPass(){
        return System.getProperty(FACEBOOK_PASS);
    }

    public static String getSuite(){ return System.getProperty(SUITE);}
}
