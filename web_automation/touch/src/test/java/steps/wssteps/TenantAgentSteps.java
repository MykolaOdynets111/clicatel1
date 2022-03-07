package steps.wssteps;

import apihelper.ApiHelperTenantsWS;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import datamanager.Agents;
import datamanager.Tenants;
import datamanager.jacksonschemas.tenantagentsws.Agent;
import datamanager.jacksonschemas.tenantagentsws.Tenant;


public class TenantAgentSteps {

    @When("^(.*) tenant is created$")
    public void createTenantWS(String tenantOrgName) {
        Tenants.setTenantUnderTestNames(tenantOrgName);
        Agents agent = Agents.getMainAgentFromCurrentEnvByTenantOrgName(Tenants.getTenantUnderTestOrgName());
        Tenant tenantBody = new Tenant(Tenants.getTenantUnderTestOrgName(), Tenants.getTenantUnderTestName(), agent.getAgentEmail());
        ApiHelperTenantsWS.createTenantWS(tenantBody);
    }

    @And("^(.*) agent with (.*) name is created$")
    public void createAgentWS(String agent, String agentName){
        Agent agentBody;
        Agents agents;
        if (!agent.toLowerCase().contains("second")){
            agents = Agents.getMainAgentFromCurrentEnvByTenantOrgName(Tenants.getTenantUnderTestOrgName());
        } else {
            agents = Agents.getSecondAgentFromCurrentEnvByTenantOrgName(Tenants.getTenantUnderTestOrgName());
        }
        agentBody = new Agent(agentName, agents.getAgentEmail());
        ApiHelperTenantsWS.createAgentWS(agentBody);
    }


}
