package steps;

import agent_side_pages.AgentHomePage;
import agent_side_pages.AgentLoginPage;
import cucumber.api.java.en.Given;

public class DefaultAgentSteps {
    private AgentHomePage agentHomePage;


    @Given("^I login as agent of (.*)")
    public void loginAsAgentForTenant(String tenant){
        agentHomePage = AgentLoginPage.openAgentLoginPage(tenant).loginAsAgentOf(tenant);
    }

}
