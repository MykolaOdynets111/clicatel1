package steps.agentsteps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class AgentPendingChatSteps extends AbstractAgentSteps {

    @When("^(.*) click '(?:Pending||Pending On)' chat button$")
    public void pinChat(String agent){
        getAgentHomePage(agent).getChatHeader().clickPendingChatButton();
    }

    @Then("^(.*) receives pending message with (.*) user name$")
    public void agentReceivesErrorMessage(String agent, String integration) {
        String userName=getUserName(integration);
        Assert.assertEquals(getAgentHomePage(agent).getPendingMessage(),"Your chat with "+userName+" has been moved to pending from live.","Incorrect pending message is shown");
    }




}
