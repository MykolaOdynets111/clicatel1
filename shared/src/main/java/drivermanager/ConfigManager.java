package drivermanager;

import org.testng.Assert;


public class ConfigManager {

    // Base configs
    private static final String REMOTE_FLAG_VARIABLE = "remote";
    private static final String ENV = "env";
    private static final String DEPLOY_TO = "deploy_to";
    private static final String TENANT_ORG_NAME = "tenantorgname";
    private static final String TENANT_ID = "tenantid";
    private static final String FACEBOOK_PASS = "fbuserpass";
    private static final String SUITE = "suite";
    private static final String IS_MC2 = "ismc2";
    private static final String IS_WEB_WIDGET = "iswebwidget";
    private static final String IS_SQS_USED = "sqsuse";


    // TestFLO reporter configs
    private static final String REPORT_TESTFLO = "reportToTestFLO";
    private static final String TPLAN_KEY = "tplanKey";
    private static final String CREATE_NEW_TPLAN = "createTPlan";
    private static final String JIRA_USER = "jirauser";
    private static final String JIRA_PASS = "jirapass";
    private static final String IS_ALLURE_2_REPORT = "isAllure2Report";
    private static final String RERUN_TEST_PLAN = "isRerun";


    //TouchGo configs
    private static final String DEBUG_TOUCHGO = "gebugTouchGo";
    private static final String NEW_ACCOUNT_CREATED = "signupSuccessful";
    private static final String NEW_TENANT_CREATED = "tenantCreationSuccessful";
    private static final String SECOND_AGENT_CREATED = "agentCreationSuccessful";
    private static final String TENANT_UPGRADED = "tenantUpgradeSuccessful";
    private static final String PAYMENT_ADDED = "paymentAdded";

    public static boolean isPaymentAdded(){
        String result = System.getProperty(PAYMENT_ADDED, "false");
        return Boolean.parseBoolean(result);
    }

    public static void setIsPaymentAdded(String status){
        System.setProperty(PAYMENT_ADDED, status);
    }

    public static boolean isNewAccountCreated(){
        String result = System.getProperty(NEW_ACCOUNT_CREATED, "false");
        return Boolean.parseBoolean(result);
    }

    public static void setIsNewAccountCreated(String status){
        System.setProperty(NEW_ACCOUNT_CREATED, status);
    }

    public static boolean isNewTenantCreated(){
        String result = System.getProperty(NEW_TENANT_CREATED, "false");
        return Boolean.parseBoolean(result);
    }

    public static void setIsNewTenantCreated(String status){
        System.setProperty(NEW_TENANT_CREATED, status);
    }

    public static boolean isSecondAgentCreated(){
        String result = System.getProperty(SECOND_AGENT_CREATED, "false");
        return Boolean.parseBoolean(result);
    }

    public static void setIsSecondCreated(String status){
        System.setProperty(SECOND_AGENT_CREATED, status);
    }

    public static boolean isTenantUpgraded(){
        String result = System.getProperty(TENANT_UPGRADED, "false");
        return Boolean.parseBoolean(result);
    }

    public static void setIsTenantUpgraded(String status){
        System.setProperty(TENANT_UPGRADED, status);
    }

    public static boolean reportToTouchFlo() {
        String reportToTestFlo = System.getProperty(REPORT_TESTFLO, "false");
        return Boolean.parseBoolean(reportToTestFlo);
    }

    public static boolean rerunTestPlan(){
        String rerun = System.getProperty(RERUN_TEST_PLAN, "false");
        return Boolean.parseBoolean(rerun);
    }

    public static boolean debugTouchGo() {
        String reportToTestFlo = System.getProperty(DEBUG_TOUCHGO, "false");
        return Boolean.parseBoolean(reportToTestFlo);
    }

    public static void setDebugTouchGo(String debug) {
        System.setProperty(DEBUG_TOUCHGO, debug);
    }

    public static boolean createNewTPlan() {
        String createNewTPlan = System.getProperty(CREATE_NEW_TPLAN, "false");
        return Boolean.parseBoolean(createNewTPlan);
    }

    public static String getTplanKey(){
        return System.getProperty(TPLAN_KEY, "");
    }

    public static String getJiraUser(){
        return System.getProperty(JIRA_USER, "");
    }

    public static boolean isAllure2Report(){
        String createNewTPlan = System.getProperty(IS_ALLURE_2_REPORT, "false");
        return Boolean.parseBoolean(createNewTPlan);
    }

    public static String getJiraPass(){
        return System.getProperty(JIRA_PASS, "");
    }

    public static void setTenantId(String tenantId) {
        System.setProperty(TENANT_ID, tenantId);
    }

    public static void setEnv(String env) {
        System.setProperty(ENV, env);
    }

    public static boolean isRemote() {
        String remoteValue = System.getProperty(REMOTE_FLAG_VARIABLE);

        return remoteValue != null && Boolean.parseBoolean(remoteValue);
    }

    public static DriverType getDriverType() {
        DriverType driverType = DriverType.CHROME;

        if (ConfigManager.isRemote()) {
            driverType = DriverType.HEADLESS_CHROME;
        }

        return driverType;
    }

    public static String getEnv() {
        String env = System.getProperty(ENV, "qa");
        String deployTo = System.getProperty(DEPLOY_TO);
        if(deployTo==null) return env;
        else{
            if (deployTo.equalsIgnoreCase("standby_group")) {
                env = "standby-"+env;
            }
            System.out.println("!!! Will run tests on env: "+env+"");
            return env;
        }
    }

    public static Boolean isMc2() {
        String isMc2 = System.getProperty(IS_MC2);
        return isMc2 != null && Boolean.parseBoolean(isMc2);
    }

    public static Boolean isWebWidget() {
        String isWebWidget = System.getProperty(IS_WEB_WIDGET);
        return isWebWidget != null && Boolean.parseBoolean(isWebWidget);
    }

     public static String getTenantOrgName(){
        String tenantOrgName = System.getProperty(TENANT_ORG_NAME);
        if(tenantOrgName==null){
            Assert.fail("Tenant org name was not provided");
        }
        return tenantOrgName;
    }

    public static String getID(){
        String tenantID = System.getProperty(TENANT_ID);
        if(tenantID==null){
            Assert.fail("Tenant ID was not provided");
        }
        return tenantID;
    }

    public static  String getFBUserPass(){
        return System.getProperty(FACEBOOK_PASS);
    }

    public static String getSuite(){
        return System.getProperty(SUITE, "all");
    }

    public static boolean isSQSUsed(){
        return Boolean.parseBoolean(System.getProperty(IS_SQS_USED, "false"));
    }
}
