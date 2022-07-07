package datamanager;

import drivermanager.ConfigManager;
import drivermanager.Environment;

import java.util.Arrays;

import static drivermanager.Environment.*;

public enum Agents {

    DEV_AGENT("taras.mytlovych@perfectial.com", "passw0rd", "capitec bank", DEV),

    // =======  Active agents of General Bank Demo ======== //

    QA_AGENT_GEN_BANK("generalbankaqa@gmail.com", "p@$$w0rd4te$t", "general bank demo", QA),
    DEV_AGENT_GEN_BANK("generalbankaqa@gmail.com", "p@$$w0rd4te$t", "general bank demo", DEV),
    TESTING_AGENT("generalbankaqa@gmail.com", "p@$$w0rd4te$t", "general bank demo", TESTING),
    DEMO_AGENT_GEN_BANK("touchdemotenant2@gmail.com", "p@$$w0rd4te$t", "general bank demo", DEMO),
    INTEGRATION_AGENT_GEN_BANK("touchdemotenant@gmail.com", "12345qwer", "general bank demo", INTEGRATION),


    // email credentials: devgeneralbankdemo@gmail.com, pass p@$$w0rd4te$t
    SECOND_AGENT_QA("generabanksecondagent@gmail.com", "p@$$w0rd4te$t", "general bank demo", QA),
    SECOND_AGENT_DEV("generabanksecondagent@gmail.com", "p@$$w0rd4te$t", "general bank demo", DEV),
    SECOND_AGENT_TESTING("generabanksecondagent@gmail.com", "p@$$w0rd4te$t", "general bank demo", TESTING),
    SECOND_AGENT_DEMO("generabanksecondagent@gmail.com", "p@$$w0rd4te$t", "general bank demo", DEMO),
    SECOND_AGENT_INTEGRATION("generabanksecondagent@gmail.com", "p@$$w0rd4te$t", "general bank demo", INTEGRATION),

    // =======  Active agents for TouchGo tests ======== //

    QA_STARTER_TOUCH_GO_ADMIN("startertgplan@gmail.com", "p@$$w0rd4te$t", "Starter AQA", QA),
    QA_STARTER_TOUCH_GO_SECOND_AGENT("touchgosecondagent@gmail.com", "p@$$w0rd4te$t", "Starter AQA", QA),
    QA_UPDATE_ACCOUNT_ADMIN("updateplan@gmail.com", "p@$$w0rd4te$t", "Updating AQA", QA),
    QA_STANDARD_ACCOUNT("standarttouchgoplan@gmail.com", "p@$$w0rd4te$t", "Standard AQA", QA),
    TESTING_STARTER_TOUCH_GO_ADMIN("startertgplan@gmail.com", "p@$$w0rd4te$t", "Starter AQA", TESTING),
    TESTING_STARTER_TOUCH_GO_SECOND_AGENT("touchgosecondagent@gmail.com", "p@$$w0rd4te$t", "Starter AQA", TESTING),
    TESTING_STANDARD_TOUCH_GO_AGENT("standarttouchgoplan@gmail.com", "p@$$w0rd4te$t", "Standard AQA", TESTING),
    TESTING_UPDATE_ACCOUNT_ADMIN("updateplan@gmail.com", "p@$$w0rd4te$t", "Updating AQA", TESTING),
    TESTING_LOCAL_SIGNUP_ACCOUNT(MC2Account.TESTING_LOCAL_ACCOUNT.getEmail(), "p@$$w0rd4te$t", "Local AQA", TESTING),
    TESTING_NEW_ONE_ACCOUNT("account_signup@aqa.test", "p@$$w0rd4te$t", "New One2", TESTING),

    // =======  Active agents for Agent mode tests ======== //
    // linked phone number to GMail box: +38050 508 36 62
    TESTING_AGENT_MODE_ADMIN("tenantagentmode@gmail.com", "p@$$w0rd4te$t", "Automation", TESTING),
    QA_AGENT_MODE_ADMIN("tenantagentmode@gmail.com", "p@$$w0rd4te$t", "Automation", QA),
    DEV_AGENT_MODE_ADMIN("tenantagentmode@gmail.com", "p@$$w0rd4te$t", "Automation", DEV),
    DEMO_AGENT_MODE_ADMIN("tenantagentmode@gmail.com", "p@$$w0rd4te$t", "Automation", DEMO),
    INTEGRATION_AGENT_MODE_ADMIN("tenantagentmode@gmail.com", "p@$$w0rd4te$t", "Automation", INTEGRATION),


    // =======  Active agents for Bot mode for Camunda flows tests ======== //
    // ==== phone number for Gmail inbox +380 50 5083662
    TESTING_BOT_MODE_ADMIN("automation258@gmail.com", "p@$$w0rd4te$t", "Automation Bot", TESTING),
    QA_BOT_MODE_ADMIN("automation258@gmail.com", "p@$$w0rd4te$t", "Automation Bot", QA),
    DEV_BOT_MODE_ADMIN("automation258@gmail.com", "p@$$w0rd4te$t", "Automation Bot", DEV),
    DEMO_BOT_MODE_ADMIN("automation258@gmail.com", "p@$$w0rd4te$t", "Automation Bot", DEMO),
    INTEGRATION_BOT_MODE_ADMIN("automation258@gmail.com", "p@$$w0rd4te$t", "Automation Bot", INTEGRATION),

    DEV_BOT_MODE_SECOND_AGENT("updateplan@gmail.com", "p@$$w0rd4te$t", "Automation Bot", DEV),
    DEMO_BOT_MODE_SECOND_AGENT("updateplan@gmail.com", "p@$$w0rd4te$t", "Automation Bot", DEMO),
    QA_BOT_MODE_SECOND_AGENT("devgeneralbankdemo@gmail.com", "p@$$w0rd4te$t", "Automation Bot", QA),
    TESTING_BOT_MODE_SECOND_AGENT("devgeneralbankdemo@gmail.com", "p@$$w0rd4te$t", "Automation Bot", TESTING),
    INTEGRATION_BOT_MODE_SECOND_AGENT("devgeneralbankdemo@gmail.com", "p@$$w0rd4te$t", "Automation Bot", INTEGRATION),

    // =======  Active agents for Bot mode with default settings ======== //

    INTEGRATION_COMMON_ADMIN("startertgplan@gmail.com", "p@$$w0rd4te$t", "Automation Common", INTEGRATION),
    DEV_COMMON_ADMIN("startertgplan@gmail.com", "p@$$w0rd4te$t", "Automation Common", DEV),
    DEMO_COMMON_ADMIN("startertgplan@gmail.com", "p@$$w0rd4te$t", "Automation Common", DEMO),
    QA_COMMON_ADMIN("account_signup@aqa.test", "p@$$w0rd4te$t", "Automation Common", QA),
    TESTING_COMMON_ADMIN("commontenant@gmail.com", "p@$$w0rd4te$t", "Automation Common", TESTING),

    // ======= Test tenants with Standard plan
    DEV_BILLING_ADMIN("standardbilling@mailinator.com", "p@$$w0rd4te$t", "Standard Billing", DEV),
    DEMO_BILLING_ADMIN("standardbilling@mailinator.com", "p@$$w0rd4te$t", "Standard Billing", DEMO),
    QA_BILLING_ADMIN("standardbilling@mailinator.com", "p@$$w0rd4te$t", "Standard Billing", QA),
    TESTING_BILLING_ADMIN("standardbilling@mailinator.com", "p@$$w0rd4te$t", "Standard Billing", TESTING),

    DEV_BILLING_ADMIN_SECOND("standardbillingsecondagent@mailinator.com", "p@$$w0rd4te$t", "Standard Billing", DEV),
    DEMO_BILLING_ADMIN_SECOND("standardbillingsecondagent@mailinator.com", "p@$$w0rd4te$t", "Standard Billing", DEMO),
    QA_BILLING_ADMIN_SECOND("standardbillingsecondagent@mailinator.com", "p@$$w0rd4te$t", "Standard Billing", QA),
    TESTING_BILLING_ADMIN_SECOND("standardbillingsecondagent@mailinator.com", "p@$$w0rd4te$t", "Standard Billing", TESTING),

    // ======= Test tenant for attachment
    DEV_ATTACHMENTS_ADMIN("touchattachments@gmail.com", "p@$$w0rd4te$t", "Attachments", DEV),
    DEMO_ATTACHMENTS_ADMIN("touchattachments@gmail.com", "p@$$w0rd4te$t", "Attachments", DEMO),
    QA_ATTACHMENTS_ADMIN("touchattachments@gmail.com", "p@$$w0rd4te$t", "Attachments", QA),
//    TESTING_BILLING_ADMIN("standardbilling@mailinator.com", "p@$$w0rd4te$t", "Standard Billing", TESTING),

    // ======= Stage tenant used for performance
    STAGE_AGENT_GEN_BANK("touchperformance1@gmail.com", "p@$$w0rd4te$t", "Performance", STAGE),

    // ======= User of newly created tenant for touch go tests =========== //
    TOUCH_GO_ADMIN("", "p@$$w0rd4te$t", "", TESTING),
    TOUCH_GO_SECOND_AGENT("touchgoagent+", "p@$$w0rd4te$t", "", TESTING),
    ;

    String email;
    String userPass;
    String tenant;
    Environment env;

    Agents(String email, String userPass, String tenant, Environment env) {
        this.email = email;
        this.userPass = userPass;
        this.tenant = tenant;
        this.env = env;
    }

    public String getOriginalEmail(){
        return "touchgoagent@gmail.com"; // For Touch Go tests
    }

    public String getAgentPass() {
        return this.userPass;
    }

    public String getAgentEmail() {
        return this.email;
    }

    public String getAgentEnv() {
        return this.env.getEnv();
    }

    public String getAgentTenant() {
        return this.tenant;
    }

    public static synchronized Agents getMainAgentFromCurrentEnvByTenantOrgName(String tenantOrgName) {
        return Arrays.stream(Agents.values())
                .filter(e -> e.getAgentEnv().equalsIgnoreCase(ConfigManager.getEnv())
                        && e.getAgentTenant().equalsIgnoreCase(tenantOrgName)
                       && !e.getAgentEmail().contains("second"))
                .findFirst().orElseThrow(() -> new AssertionError(
                        "No admin user found for " + tenantOrgName + ", env: " + ConfigManager.getEnv()));
    }

    public static synchronized Agents getSecondAgentFromCurrentEnvByTenantOrgName(String tenantOrgName) {
        return Arrays.stream(Agents.values())
                .filter(e -> e.getAgentEnv().equalsIgnoreCase(ConfigManager.getEnv())
                        && e.getAgentTenant().equalsIgnoreCase(tenantOrgName)
                       && e.toString().toLowerCase().contains("second"))

                .findFirst().orElseThrow(() -> new AssertionError(
                        "No second agent found for " + tenantOrgName + ", env: " + ConfigManager.getEnv()));
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
        this.env = Environment.fromString(env);
        return this;
    }

    public Agents setPass(String pass) {
        this.userPass = pass;
        return this;
    }
}
