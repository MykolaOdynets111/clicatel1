package steps.general_bank_steps;

import agent_side_pages.AgentHomePage;
import agent_side_pages.UIElements.ChatBody;
import agent_side_pages.UIElements.SuggestedGroup;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import interfaces.JSHelper;
import org.testng.Assert;

public class AgentConversationSteps implements JSHelper{

    private AgentHomePage agentHomePage ;
    private ChatBody chatBody;
    private SuggestedGroup suggestedGroup;

    @Then("^Conversation area becomes active with (.*) user's message in it$")
    public void verifyUserMessageOnAgentDesk(String userMessage) {
        Assert.assertTrue(getChatBody().isUserMessageShown(userMessage),
                "'" +userMessage+ "' User message is not shown in conversation area (Client ID: "+getUserNameFromLocalStorage()+")");
    }

    @Then("^There is no more than one from user message$")
    public void checkThereIsNoMoreThanOneUserMessage() {
        Assert.assertFalse(getChatBody().isMoreThanOneUserMassageShown(),
                "More than one user message is shown (Client ID: "+getUserNameFromLocalStorage()+")");
    }

    @Then("^There is no from agent response added by default for (.*) user message$")
    public void verifyIfNoAgentResponseAddedByDefault(String userMessage) {
        Assert.assertFalse(getChatBody().isResponseOnUserMessageShown(userMessage),
                "There is agent answer added without agent's intention (Client ID: "+getUserNameFromLocalStorage()+")");
    }

    @When("^Agent responds with (.*) to User$")
    public void sendAnswerToUser(String responseToUser){
        getAgentHomePage().sendResponseToUser(responseToUser);
    }

    private AgentHomePage getAgentHomePage() {
        if (agentHomePage==null) {
            agentHomePage =  new AgentHomePage();
            return agentHomePage;
        } else{
            return agentHomePage;
        }
    }

    private ChatBody getChatBody(){
        if (chatBody==null) {
            chatBody =  getAgentHomePage().getChatBody();
            return chatBody;
        } else{
            return chatBody;
        }
    }

    @Then("^There is no suggestions on user's input (.*)$")
    public void verifyIfThereIsNoSuggestions(String userMessage) {
        Assert.assertTrue(suggestedGroup.isSuggestionListEmpty(),
                "Suggestions list is not empty on user's input: "+userMessage+"");
    }
}
