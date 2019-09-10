package datamanager;

import drivermanager.ConfigManager;

import java.util.Arrays;
import java.util.List;

public enum Agents {

    DEV_AGENT("taras.mytlovych@perfectial.com", "passw0rd", "capitec bank", "dev"),

    // =======  Active agents of General Bank Demo ======== //

    QA_AGENT_GEN_BANK("generalbankaqa@gmail.com", "p@$$w0rd4te$t", "general bank demo", "qa"),
    DEV_AGENT_GEN_BANK("generalbankaqa@gmail.com", "p@$$w0rd4te$t", "general bank demo", "dev"),
    TESTING_AGENT("generalbankaqa@gmail.com", "p@$$w0rd4te$t", "general bank demo", "testing"),
    DEMO_AGENT_GEN_BANK("touchdemotenant@gmail.com", "12345qwer", "general bank demo", "demo"),
    INTEGRATION_AGENT_GEN_BANK("touchdemotenant@gmail.com", "12345qwer", "general bank demo", "integration"),
    DEMO1_AGENT("generalbankaqa@gmail.com", "p@$$w0rd4te$t", "general bank demo", "demo1"),
    BETA_AGENT_GEN_BANK("generalbankaqa@gmail.com", "p@$$w0rd4te$t", "general bank demo", "beta"),

    // email credentials: devgeneralbankdemo@gmail.com, pass p@$$w0rd4te$t
    SECOND_AGENT_QA("generabanksecondagent@gmail.com", "p@$$w0rd4te$t", "general bank demo", "qa"),
    SECOND_AGENT_DEV("generabanksecondagent@gmail.com", "p@$$w0rd4te$t", "general bank demo", "dev"),
    SECOND_AGENT_TESTING("generabanksecondagent@gmail.com", "p@$$w0rd4te$t", "general bank demo", "testing"),
    SECOND_AGENT_DEMO("generabanksecondagent@gmail.com", "p@$$w0rd4te$t", "general bank demo", "demo"),
    SECOND_AGENT_DEMO1("generabanksecondagent@gmail.com", "p@$$w0rd4te$t", "general bank demo", "demo1"),
    SECOND_AGENT_INTEGRATION("generabanksecondagent@gmail.com", "p@$$w0rd4te$t", "general bank demo", "integration"),

    // =======  Active agents for TouchGo tests ======== //

    QA_STARTER_TOUCH_GO_ADMIN("startertgplan@gmail.com", "p@$$w0rd4te$t", "Starter AQA", "qa"),
    QA_STARTER_TOUCH_GO_SECOND_AGENT("touchgosecondagent@gmail.com", "p@$$w0rd4te$t", "Starter AQA", "qa"),
    QA_UPDATE_ACCOUNT_ADMIN("updateplan@gmail.com", "p@$$w0rd4te$t", "Updating AQA", "qa"),
    QA_STANDARD_ACCOUNT("standarttouchgoplan@gmail.com", "p@$$w0rd4te$t", "Standard AQA", "qa"),
    TESTING_STARTER_TOUCH_GO_ADMIN("startertgplan@gmail.com", "p@$$w0rd4te$t", "Starter AQA", "testing"),
    TESTING_STARTER_TOUCH_GO_SECOND_AGENT("touchgosecondagent@gmail.com", "p@$$w0rd4te$t", "Starter AQA", "testing"),
    TESTING_STANDARD_TOUCH_GO_AGENT("standarttouchgoplan@gmail.com", "p@$$w0rd4te$t", "Standard AQA", "testing"),
    TESTING_UPDATE_ACCOUNT_ADMIN("updateplan@gmail.com", "p@$$w0rd4te$t", "Updating AQA", "testing"),
    TESTING_LOCAL_SIGNUP_ACCOUNT(MC2Account.TESTING_LOCAL_ACCOUNT.getEmail(), "p@$$w0rd4te$t", "Local AQA", "testing"),
    TESTING_NEW_ONE_ACCOUNT("account_signup@aqa.test", "p@$$w0rd4te$t", "New One2", "testing"),

    // =======  Active agents for Agent mode tests ======== //
    // linked phone number to GMail box: +38050 508 36 62
    TESTING_AGENT_MODE_ADMIN("tenantagentmode@gmail.com", "p@$$w0rd4te$t", "Automation", "testing"),
    QA_AGENT_MODE_ADMIN("tenantagentmode@gmail.com", "p@$$w0rd4te$t", "Automation", "qa"),
    DEV_AGENT_MODE_ADMIN("tenantagentmode@gmail.com", "p@$$w0rd4te$t", "Automation", "dev"),
    DEMO_AGENT_MODE_ADMIN("tenantagentmode@gmail.com", "p@$$w0rd4te$t", "Automation", "demo"),
    DEMO1_AGENT_MODE_ADMIN("tenantagentmode@gmail.com", "p@$$w0rd4te$t", "Automation", "demo1"),
    INTEGRATION_AGENT_MODE_ADMIN("tenantagentmode@gmail.com", "p@$$w0rd4te$t", "Automation", "integration"),


    // =======  Active agents for Bot mode for Camunda flows tests ======== //
    // ==== phone number for Gmail inbox +380 50 5083662
    TESTING_BOT_MODE_ADMIN("automation258@gmail.com", "p@$$w0rd4te$t", "Automation Bot", "testing"),
    QA_BOT_MODE_ADMIN("automation258@gmail.com", "p@$$w0rd4te$t", "Automation Bot", "qa"),
    DEV_BOT_MODE_ADMIN("automation258@gmail.com", "p@$$w0rd4te$t", "Automation Bot", "dev"),
    DEMO_BOT_MODE_ADMIN("automation258@gmail.com", "p@$$w0rd4te$t", "Automation Bot", "demo"),
    DEMO1_BOT_MODE_ADMIN("automation258@gmail.com", "p@$$w0rd4te$t", "Automation Bot", "demo1"),
    INTEGRATION_BOT_MODE_ADMIN("automation258@gmail.com", "p@$$w0rd4te$t", "Automation Bot", "integration"),

    DEV_BOT_MODE_SECOND_AGENT("updateplan@gmail.com", "p@$$w0rd4te$t", "Automation Bot", "dev"),
    DEMO_BOT_MODE_SECOND_AGENT("updateplan@gmail.com", "p@$$w0rd4te$t", "Automation Bot", "demo"),
    QA_BOT_MODE_SECOND_AGENT("devgeneralbankdemo@gmail.com", "p@$$w0rd4te$t", "Automation Bot", "qa"),
    TESTING_BOT_MODE_SECOND_AGENT("devgeneralbankdemo@gmail.com", "p@$$w0rd4te$t", "Automation Bot", "testing"),

    // =======  Active agents for Bot mode with default settings ======== //

    INTEGRATION_COMMON_ADMIN("startertgplan@gmail.com", "p@$$w0rd4te$t", "Automation Common", "integration"),
    DEV_COMMON_ADMIN("startertgplan@gmail.com", "p@$$w0rd4te$t", "Automation Common", "dev"),
    DEMO_COMMON_ADMIN("startertgplan@gmail.com", "p@$$w0rd4te$t", "Automation Common", "demo"),
    QA_COMMON_ADMIN("account_signup@aqa.test", "p@$$w0rd4te$t", "Automation Common", "qa"),
    TESTING_COMMON_ADMIN("commontenant@gmail.com", "p@$$w0rd4te$t", "Automation Common", "testing"),

    // ======= Test tenants with Standard plan
    DEV_BILLING_ADMIN("standardbilling@mailinator.com", "p@$$w0rd4te$t", "Standard Billing", "dev"),
    DEMO_BILLING_ADMIN("standardbilling@mailinator.com", "p@$$w0rd4te$t", "Standard Billing", "demo"),
    QA_BILLING_ADMIN("standardbilling@mailinator.com", "p@$$w0rd4te$t", "Standard Billing", "qa"),
    TESTING_BILLING_ADMIN("standardbilling@mailinator.com", "p@$$w0rd4te$t", "Standard Billing", "testing"),

    // ======= User of newly created tenant for touch go tests =========== //
    TOUCH_GO_ADMIN("", "p@$$w0rd4te$t", "", ""),
    TOUCH_GO_SECOND_AGENT("touchgoagent@gmail.com", "p@$$w0rd4te$t", "", ""),
    ;

    String email;
    String userPass;
    String tenant;
    String env;

    Agents(String email, String userPass, String tenant, String env) {
        this.email = email;
        this.userPass = userPass;
        this.tenant = tenant;
        this.env = env;
    }

    public String getAgentPass() {
        return this.userPass;
    }

    public String getAgentEmail() {
        return this.email;
    }

    public String getAgentEnv() {
        return this.env;
    }

    public String getAgentTenant() {
        return this.tenant;
    }

    public static Agents getMainAgentFromCurrentEnvByTenantOrgName(String tenantOrgName) {
        return Arrays.stream(Agents.values())
                .filter(e -> e.getAgentEnv().equalsIgnoreCase(ConfigManager.getEnv())
                        && e.getAgentTenant().equalsIgnoreCase(tenantOrgName)
                        && !e.getAgentEmail().contains("second"))
                .findFirst().get();
    }

    public static Agents getSecondAgentFromCurrentEnvByTenantOrgName(String tenantOrgName) {
        return Arrays.stream(Agents.values())
                .filter(e -> e.getAgentEnv().equalsIgnoreCase(ConfigManager.getEnv())
                        && e.getAgentTenant().equalsIgnoreCase(tenantOrgName)
                        && e.toString().toLowerCase().contains("second"))
                .findFirst().get();
    }

    public static  Agents getAgentFromCurrentEnvByTenantOrgName(String tenantOrgName, String ordinalAgentNumber){
        if (ordinalAgentNumber.equalsIgnoreCase("second agent")) return getSecondAgentFromCurrentEnvByTenantOrgName(tenantOrgName);
        else return getMainAgentFromCurrentEnvByTenantOrgName(tenantOrgName);
    }

    public Agents setEmail(String email) {
        this.email = email;
        return this;
    }


    public Agents setTenant(String tenant) {
        this.tenant = tenant;
        return this;
    }

    public Agents setEnv(String env) {
        this.env = env;
        return this;
    }

    public Agents setPass(String pass) {
        this.userPass = pass;
        return this;
    }
}
