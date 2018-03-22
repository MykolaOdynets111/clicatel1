package steps.general_bank_steps;

import agent_side_pages.AgentHomePage;
import agent_side_pages.UIElements.ChatBody;
import agent_side_pages.UIElements.SuggestedGroup;
import agent_side_pages.UIElements.Suggestion;
import api_helper.ApiHelper;
import api_helper.ApiHelperTie;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataprovider.Intents;
import dataprovider.jackson_schemas.Intent;
import interfaces.JSHelper;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


    @Then("^There is no suggestions on user's input (.*)$")
    public void verifyIfThereIsNoSuggestions(String userMessage) {
        Assert.assertTrue(getSuggestedGroup().isSuggestionListEmpty(),
                "Suggestions list is not empty on user's input: "+userMessage+"");
    }

    @Then("^There is correct suggestion shown on user message \"(.*)\"(?: and sorted by confidence|)$")
    public void verifySuggestionsCorrectnessFor(String userMessage) {
        if (getSuggestedGroup().isSuggestionListEmpty()){
            Assert.assertTrue(false, "Suggestion list is empty");
        }
        String expectedResponse = "no response";
        List<Intent> listOfIntentsFromTIE = ApiHelperTie.getListOfIntentsOnUserMessage(userMessage);
        List<String> answersFromTie = new ArrayList<>();
        for(int i =0; i<listOfIntentsFromTIE.size(); i++){
            expectedResponse = ApiHelperTie.getExpectedMessageOnIntent(listOfIntentsFromTIE.get(i).getIntent());
            if (expectedResponse.contains("${firstName}")) {
                expectedResponse = expectedResponse.replace("${firstName}", getUserNameFromLocalStorage());
            }
            if(expectedResponse.toCharArray().length>650){
                expectedResponse=expectedResponse.substring(0,649);
            }
            answersFromTie.add(i, expectedResponse);
        }
        List<Suggestion> actualSuggestions = getSuggestedGroup().getSuggestionsList();
        List<String> suggestionTextsActual = actualSuggestions.stream().map(e -> e.getSuggestionMessage()).collect(Collectors.toList());
        Assert.assertEquals(suggestionTextsActual, answersFromTie, "Shown Suggestions is not as expected");
    }

    @Then("^The suggestion for user message \"(.*)\" with the biggest confidence is added to the input field$")
    public void verifyAutomaticAddingSuggestingToInputField(String userMessage){
        String actualSuggestion = getAgentHomePage().getSuggestionFromInputFiled();
        if (actualSuggestion==null){
            Assert.assertTrue(false, "There is no added suggestion in input field");
        }
        String expectedMessage = ApiHelperTie.getExpectedMessageOnIntent(
                                         Intents.getIntentWithMaxConfidence(userMessage).getIntent());
        Assert.assertEquals(actualSuggestion, expectedMessage, "Expected suggestion in input field is not as expected");
    }


    @When("^Agent click send button$")
    public void clickSendButton() {
        getAgentHomePage().clickSendButton();
    }

    @When("^Agent is able to delete the suggestion from input field and sent his own \"(.*)\" message$")
    public void deleteSuggestionAndSendOwn(String agentMessage){
        getAgentHomePage().deleteSuggestionAndAddAnother(agentMessage);
        getAgentHomePage().clickSendButton();
    }

    @When("^Agent add additional info \"(.*)\" to suggested message$")
    public void addMoreInfo(String additional) {
        getAgentHomePage().addMoreInfo(additional);
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

    private SuggestedGroup getSuggestedGroup() {
        if (suggestedGroup==null) {
            suggestedGroup =  getAgentHomePage().getSuggestedGroup();
            return suggestedGroup;
        } else{
            return suggestedGroup;
        }
    }
}
