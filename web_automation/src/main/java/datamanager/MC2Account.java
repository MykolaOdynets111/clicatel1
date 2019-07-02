package datamanager;

import drivermanager.ConfigManager;

import java.util.Arrays;
import java.util.List;

public enum MC2Account {

    QA_STARTER_ACCOUNT("starter", "startertgplan@gmail.com", "p@$$w0rd4te$t", "starter_aqa", "Starter AQA", "qa", "ff808081661a899b016653366b13214c", ""),
    QA_UPDATE_ACCOUNT("starter", "updateplan@gmail.com", "p@$$w0rd4te$t", "updatingaccount", "Updating AQA", "qa", "ff808081661a899b0166636e761a292c", "ff808081665d942a0166636cf20200a3"),
    QA_STANDARD_ACCOUNT("standard", "standarttouchgoplan@gmail.com", "p@$$w0rd4te$t", "standardplan", "Standard AQA", "qa", "ff808081661a899b01667d162b6e35c6", "ff808081666834d201667d1279b50061"),

    TESTING_STARTER_ACCOUNT("starter", "startertgplan@gmail.com", "p@$$w0rd4te$t", "starter-aqa", "Starter AQA", "testing", "ff8080816680668201668225f48205f4", "ff8080816642a8850166820e804e00a2"),
    TESTING_STANDARD_ACCOUNT("standard", "standarttouchgoplan@gmail.com", "p@$$w0rd4te$t", "standardplan", "Standard AQA", "testing", "ff808081668ab31c01668b98407f05e5", "ff8080816642a88501668b925a0e00cc"),
    TESTING_UPDATE_ACCOUNT("starter", "updateplan@gmail.com", "p@$$w0rd4te$t", "updatingplan", "Updating AQA", "testing", "", ""),

    TESTING_SIGNUP_ACCOUNT("starter", "account_signup@aqa.test", "p@$$w0rd4te$t", "automationtest", "SignedUp AQA", "testing", "", ""),
//    TESTING_NEW_ONE_ACCOUNT("starter", "account_signup@aqa.test", "p@$$w0rd4te$t", "automationtest1", "New One", "testing", "", ""),

    TESTING_AGENT_MODE_ACCOUNT("starter", "tenantagentmode@gmail.com", "p@$$w0rd4te$t", "agentmode", "Automation", "testing", "", ""),
    QA_AGENT_MODE_ACCOUNT("starter", "tenantagentmode@gmail.com", "p@$$w0rd4te$t", "agentmode", "Automation", "qa", "", ""),
    DEV_AGENT_MODE_ACCOUNT("starter", "tenantagentmode@gmail.com", "p@$$w0rd4te$t", "agentmode", "Automation", "dev", "", ""),
    DEMO_AGENT_MODE_ACCOUNT("starter", "tenantagentmode@gmail.com", "p@$$w0rd4te$t", "agentmode", "Automation", "demo", "", ""),
    DEMO1_AGENT_MODE_ACCOUNT("starter", "tenantagentmode@gmail.com", "p@$$w0rd4te$t", "agentmode", "Automation", "demo", "", ""),

    QA_BOT_MODE("starter", "automation258@gmail.com", "p@$$w0rd4te$t", "automationbot", "Automation Bot", "qa", "", ""),
    DEV_BOT_MODE("starter", "automation258@gmail.com", "p@$$w0rd4te$t", "automationbot", "Automation Bot", "dev", "", ""),
    DEMO_BOT_MODE("starter", "automation258@gmail.com", "p@$$w0rd4te$t", "automationbot", "Automation Bot", "demo", "ff808081682deffe01683875643d0ae6", ""),
    DEMO1_BOT_MODE("starter", "automation258@gmail.com", "p@$$w0rd4te$t", "automationbot", "Automation Bot", "demo1", "", ""),
    TESTING_BOT_MODE("starter", "automation258@gmail.com", "p@$$w0rd4te$t", "automationbot", "Automation Bot", "testing", "", ""),
    INTEGRATION_BOT_MODE("starter", "automation258@gmail.com", "p@$$w0rd4te$t", "automationbot", "Automation Bot", "integration", "", ""),


    INTEGRATION_COMMON_BOT("starter", "startertgplan@gmail.com", "p@$$w0rd4te$t", "aqacomon", "Automation Common", "integration", "", ""),
    DEV_COMMON_BOT("starter", "startertgplan@gmail.com", "p@$$w0rd4te$t", "aqacomon", "Automation Common", "dev", "", ""),
    DEMO_COMMON_BOT("starter", "startertgplan@gmail.com", "p@$$w0rd4te$t", "aqacomon", "Automation Common", "demo", "", ""),
    TESTING_COMMON_BOT("starter", "commontenant@gmail.com", "p@$$w0rd4te$t", "aqacomon", "Automation Common", "testing", "", ""),
    QA_COMMON_BOT("starter", "account_signup@aqa.test", "p@$$w0rd4te$t", "aqacomon", "Automation Common", "qa", "", ""),

    DEV_BILLING_ADMIN("standard", "standardbilling@mailinator.com", "p@$$w0rd4te$t", "standardbilling", "Standard Billing", "dev", "", ""),
    DEMO_BILLING_ADMIN("standard", "standardbilling@mailinator.com", "p@$$w0rd4te$t", "standardbilling", "Standard Billing", "demo", "", ""),
    QA_BILLING_ADMIN("standard", "standardbilling@mailinator.com", "p@$$w0rd4te$t", "standardbilling", "Standard Billing", "qa", "", ""),
    TESTING_BILLING_ADMIN("standard", "standardbilling@mailinator.com", "p@$$w0rd4te$t", "standardbilling", "Standard Billing", "testing", "", "ff8080816b545c7c016b561eb8530040"),

    TOUCH_GO_NEW_ACCOUNT("", "", "p@$$w0rd4te$t","", "", "", "","")
    ;

    String touchGoPlan;
    String email;
    String pass;
    String accountName;
    String tenantOrgName;
    String env;
    String tenantID;
    String accountID;

    MC2Account(String touchGoPlan, String email, String pass, String accountName, String tenantOrgName, String env, String tenantID, String accountID) {
        this.touchGoPlan = touchGoPlan;
        this.email = email;
        this.pass = pass;
        this.accountName = accountName;
        this.tenantOrgName = tenantOrgName;
        this.env = env;
        this.tenantID = tenantID;
        this.accountID = accountID;
    }

    public String getEmail() {
        return email;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getEnv() {
        return env;
    }

    public String getPass() {
        return pass;
    }

    public String getTenantOrgName() {
        return tenantOrgName;
    }

    public String getTouchGoPlan() {
        return touchGoPlan;
    }

    public String getAccountID() {
        return accountID;
    }

    public String getTenantID() {
        return tenantID;
    }

    public MC2Account getAccount(String env, String touchGoPlan){
        MC2Account[] accountsArray = MC2Account.values();
        List<MC2Account> agentsList = Arrays.asList(accountsArray);
        return agentsList.stream()
                .filter(e -> e.getEnv().equalsIgnoreCase(ConfigManager.getEnv())
                        && e.getTouchGoPlan().equalsIgnoreCase(tenantOrgName))
                .findFirst().get();
    }

    public static MC2Account getAccountByOrgName(String env, String tenantOrgName){
        MC2Account[] accountsArray = MC2Account.values();
        List<MC2Account> agentsList = Arrays.asList(accountsArray);
        return agentsList.stream()
                .filter(e -> e.getEnv().equalsIgnoreCase(ConfigManager.getEnv())
                        && e.getTenantOrgName().equalsIgnoreCase(tenantOrgName))
                .findFirst().get();
    }

    public static MC2Account getAccountDetails(String env, String accounName){
        MC2Account[] accountsArray = MC2Account.values();
        List<MC2Account> agentsList = Arrays.asList(accountsArray);
        return agentsList.stream()
                .filter(e -> e.getEnv().equalsIgnoreCase(ConfigManager.getEnv())
                        && e.getAccountName().equalsIgnoreCase(accounName))
                .findFirst().get();
    }

    public MC2Account setTouchGoPlan(String touchGoPlan) {
        this.touchGoPlan = touchGoPlan;
        return this;
    }

    public MC2Account setEmail(String email) {
        this.email = email;
        return this;
    }

    public MC2Account setAccountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public MC2Account setTenantOrgName(String tenantOrgName) {
        this.tenantOrgName = tenantOrgName;
        return this;
    }

    public MC2Account setEnv(String env) {
        this.env = env;
        return this;
    }

    public MC2Account setTenantID(String tenantID) {
        this.tenantID = tenantID;
        return this;
    }

    public MC2Account setAccountID(String accountID) {
        this.accountID = accountID;
        return this;
    }
}
