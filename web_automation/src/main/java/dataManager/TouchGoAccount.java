package dataManager;

import driverManager.ConfigManager;

import java.util.Arrays;
import java.util.List;

public enum TouchGoAccount {

    QA_STARTER_ACCOUNT("starter", "startertgplan@gmail.com", "p@$$w0rd4te$t", "starter-aqa", "Starter AQA", "qa"),
    QA_UPDATE_ACCOUNT("starter", "updateplan@gmail.com", "p@$$w0rd4te$t", "accountForUpdating", "Updating AQA", "qa")

    ;

    String touchGoPlan;
    String email;
    String pass;
    String accountName;
    String tenantOrgName;
    String env;

    TouchGoAccount(String touchGoPlan, String email, String pass, String accountName, String tenantOrgName, String env) {
        this.touchGoPlan = touchGoPlan;
        this.email = email;
        this.pass = pass;
        this.accountName = accountName;
        this.tenantOrgName = tenantOrgName;
        this.env = env;
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

    public TouchGoAccount getAccount(String env, String touchGoPlan){
        TouchGoAccount[] accountsArray = TouchGoAccount.values();
        List<TouchGoAccount> agentsList = Arrays.asList(accountsArray);
        return agentsList.stream()
                .filter(e -> e.getEnv().equalsIgnoreCase(ConfigManager.getEnv())
                        && e.getTouchGoPlan().equalsIgnoreCase(tenantOrgName))
                .findFirst().get();
    }
}
