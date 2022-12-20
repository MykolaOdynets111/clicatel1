package apihelper;

import datamanager.jacksonschemas.tenantagentsws.Agent;

import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.String.format;

public class ApiHelperAgent extends ApiHelper {

    public static Agent getAgentsByName(String tenant, String name) {
        String id = getAgentId(tenant, name);
        return getAgentsForTenant(tenant)
                .stream().filter(a -> a.getId().contains(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(format("There is no %s", name)));
    }

    public static String getAgentId(String tenantOrgName, String agent) {
        return getAgentInfo(tenantOrgName, agent).get("id");
    }

    private static List<Agent> getAgentsForTenant(String tenantOrgName) {
        return getTouchQuery(tenantOrgName, Endpoints.AGENT_INFO)
                .jsonPath().getList("", Agent.class);
    }
}
