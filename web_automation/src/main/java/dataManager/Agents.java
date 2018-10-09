package dataManager;

import driverManager.ConfigManager;

import java.util.Arrays;
import java.util.List;

public enum Agents {
    QA_AGENT_GEN_BANK("generalbankaqa@gmail.com", "p@$$w0rd4te$t", "general bank demo", "qa"),

    DEV_AGENT("taras.mytlovych@perfectial.com", "passw0rd", "capitec bank", "dev"),

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

    // Touch Go

    QA_STARTER_TOUCH_GO_ADMIN("startertgplan@gmail.com", "p@$$w0rd4te$t", "Starter AQA", "qa"),
    QA_STARTER_TOUCH_GO_AGENT("touchgosecondagent@gmail.com", "p@$$w0rd4te$t", "Starter AQA", "qa")
    ;

    String userName;
    String userPass;
    String tenant;
    String env;

    Agents(String userName, String userPass, String tenant, String env) {
        this.userName = userName;
        this.userPass = userPass;
        this.tenant = tenant;
        this.env = env;
    }

    public String getAgentPass() {
        return this.userPass;
    }

    public String getAgentName() {
        return this.userName;
    }

    public String getAgentEnv() {
        return this.env;
    }

    public String getAgentTenant() {
        return this.tenant;
    }

    public static Agents getMainAgentFromCurrentEnvByTenantOrgName(String tenantOrgName) {
        Agents[] agentsArray = Agents.values();
        List<Agents> agentsList = Arrays.asList(agentsArray);
        return agentsList.stream()
                .filter(e -> e.getAgentEnv().equalsIgnoreCase(ConfigManager.getEnv())
                        && e.getAgentTenant().equalsIgnoreCase(tenantOrgName)
                        && !e.getAgentName().contains("second"))
                .findFirst().get();
    }

    public static Agents getSecondAgentFromCurrentEnvByTenantOrgName(String tenantOrgName) {
        Agents[] agentsArray = Agents.values();
        List<Agents> agentsList = Arrays.asList(agentsArray);
        return agentsList.stream()
                .filter(e -> e.getAgentEnv().equalsIgnoreCase(ConfigManager.getEnv())
                        && e.getAgentTenant().equalsIgnoreCase(tenantOrgName)
                        && e.getAgentName().contains("second"))
                .findFirst().get();
    }

    public static  Agents getAgentFromCurrentEnvByTenantOrgName(String tenantOrgName, String ordinalAgentNumber){
        if (ordinalAgentNumber.equalsIgnoreCase("second agent")) return getSecondAgentFromCurrentEnvByTenantOrgName(tenantOrgName);
        else return getMainAgentFromCurrentEnvByTenantOrgName(tenantOrgName);
    }
}
