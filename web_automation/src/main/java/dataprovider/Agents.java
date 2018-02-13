package dataprovider;

import driverManager.ConfigManager;

import java.util.Arrays;
import java.util.List;

public enum Agents {
    QA_AGENT("taras.mytlovych@perfectial.com", "p@$$w0rd4te$t", "capitec bank", "qa"),

    DEV_AGENT("taras.mytlovych@perfectial.com", "p@$$w0rd4te$t", "capitec bank", "dev"),

    TESTING_AGENT("taras.mytlovych@perfectial.com", "p@$$w0rd4te$t", "general bank demo", "testing");


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

    public static Agents getAgentFromCurrentEnvByTenant(String tenant) {
        Agents[] agentsArray = Agents.values();
        List<Agents> agentsList = Arrays.asList(agentsArray);
        return agentsList.stream()
                .filter(e -> e.getAgentEnv().equalsIgnoreCase(ConfigManager.getEnv())
                        && e.getAgentTenant().equalsIgnoreCase(tenant))
                .findFirst().get();
    }
}
