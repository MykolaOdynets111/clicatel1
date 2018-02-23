package steps.general_bank_steps;

import agent_side_pages.AgentHomePage;
import agent_side_pages.UIElements.ChatBody;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;

public class AgentConversationSteps {

    private AgentHomePage agentHomePage = new AgentHomePage();
    private ChatBody chatBody = agentHomePage.getChatBody();

    @Then("^Conversation area becomes active with (.*) user's message in it$")
    public void verifyUserMessageOnAgentDesk(String userMessage) {
        Assert.assertTrue(chatBody.isUserMessageShown(userMessage),
                "'" +userMessage+ "' User message is not shown in conversation area");
    }

    @Then("^There is no more than one from user message$")
    public void checkThereIsNoMoreThanOneUserMessage() {
        Assert.assertFalse(chatBody.isMoreThanOneUserMassageShown(),
                "More than one user message is shown");
    }

    @Then("^There is no from agent response added by default for (.*) user message$")
    public void verifyIfNoAgentResponseAddedByDefault(String userMessage) {
        Assert.assertFalse(chatBody.isResponseOnUserMessageShown(userMessage),
                "There is agent answer added without agent's intention.");
    }

    @When("^Agent responds with (.*) to User$")
    public void sendAnswerToUser(String responseToUser){
        agentHomePage.sendResponseToUser(responseToUser);
    }
}
