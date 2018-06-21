package dbManager;

import dataprovider.Agents;
import driverManager.ConfigManager;

import java.util.Arrays;
import java.util.List;

public enum DBUrls {


   ;

    String url;
    String env;

    DBUrls(String url, String env) {
        this.url = url;
        this.env = env;
    }

//    public String getAgentPass() {
//        return this.userPass;
//    }
//
//    public String getAgentName() {
//        return this.userName;
//    }
//
//    public String getAgentEnv() {
//        return this.env;
//    }
//
//    public String getAgentTenant() {
//        return this.tenant;
//    }

    public static Agents getAgentFromCurrentEnvByTenantOrgName(String tenantOrgName) {
        Agents[] agentsArray = Agents.values();
        List<Agents> agentsList = Arrays.asList(agentsArray);
        return agentsList.stream()
                .filter(e -> e.getAgentEnv().equalsIgnoreCase(ConfigManager.getEnv())
                        && e.getAgentTenant().equalsIgnoreCase(tenantOrgName))
                .findFirst().get();
    }
}
