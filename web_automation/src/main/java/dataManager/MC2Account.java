package dataManager;

import driverManager.ConfigManager;

import java.util.Arrays;
import java.util.List;

public enum MC2Account {

    QA_STARTER_ACCOUNT("starter", "startertgplan@gmail.com", "p@$$w0rd4te$t", "starter-aqa", "Starter AQA", "qa", "ff808081661a899b016653366b13214c", ""),
    QA_UPDATE_ACCOUNT("starter", "updateplan@gmail.com", "p@$$w0rd4te$t", "updatingaccount", "Updating AQA", "qa", "ff808081661a899b0166636e761a292c", "ff808081665d942a0166636cf20200a3"),
    QA_STANDARD_ACCOUNT("standard", "standarttouchgoplan@gmail.com", "p@$$w0rd4te$t", "standardplan", "Standard AQA", "qa", "", ""),

    TESTING_STARTER_ACCOUNT("starter", "startertgplan@gmail.com", "p@$$w0rd4te$t", "starter-aqa", "Starter AQA", "testing", "", ""),
    TESTING_STANDARD_ACCOUNT("standard", "standarttouchgoplan@gmail.com", "p@$$w0rd4te$t", "standardplan", "Standard AQA", "testing", "", "")

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
}
