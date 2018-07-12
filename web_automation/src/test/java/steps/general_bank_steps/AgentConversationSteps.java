package steps.general_bank_steps;

import agent_side_pages.AgentHomePage;
import agent_side_pages.UIElements.ChatBody;
import agent_side_pages.UIElements.SuggestedGroup;
import agent_side_pages.UIElements.Suggestion;
import api_helper.ApiHelperTie;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataprovider.Intents;
import dataprovider.jackson_schemas.Intent;
import interfaces.JSHelper;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AgentConversationSteps implements JSHelper{

    private AgentHomePage mainAgentHomePage;
    private AgentHomePage secondAgentHomePage;
    private AgentHomePage agentHomePage ;
    private ChatBody chatBody;
    private SuggestedGroup suggestedGroup;

    @Then("^Conversation area becomes active with (.*) user's message in it$")
    public void verifyUserMessageOnAgentDesk(String userMessage) {
        Assert.assertTrue(getChatBody().isUserMessageShown(userMessage),
                "'" +userMessage+ "' User message is not shown in conversation area (Client ID: "+getUserNameFromLocalStorage()+")");
    }

    @Then("^Conversation area becomes active with (.*) user's message in it for (.*)$")
    public void verifyUserMessageOnAgentDesk(String userMessage, String agent) {
        Assert.assertTrue(getChatBody(agent).isUserMessageShown(userMessage),
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

    @When("^(.*) (?:responds with|sends a new message) (.*) to User$")
    public void sendAnswerToUser(String agent, String responseToUser){
        getAgentHomePage(agent).sendResponseToUser(responseToUser);
    }

    @When("^Agent clear input and send a new message (.*)$")
    public void clearAndSendAnswerToUser(String responseToUser){
        getAgentHomePage().clearAndSendResponseToUser(responseToUser);
    }

    @Then("^There is no suggestions on user's input (.*)$")
    public void verifyIfThereIsNoSuggestions(String userMessage) {
        Assert.assertTrue(getSuggestedGroup().isSuggestionListEmpty(),
                "Suggestions list is not empty on user's input: "+userMessage+"");
    }

    @Then("^There is correct suggestion shown on user message \"(.*)\"(?: and sorted by confidence|)$")
    public void verifySuggestionsCorrectnessFor(String userMessage) {
        getAgentHomePage().clickAgentAssistantButton();
        getAgentHomePage().waitForElementToBeVisible(getAgentHomePage().getSuggestedGroup());
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
                expectedResponse=expectedResponse.substring(0,650);
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
        if (expectedMessage.contains("${firstName}")){
            expectedMessage = expectedMessage.replace("${firstName}", getUserNameFromLocalStorage());
        }
        Assert.assertEquals(actualSuggestion, expectedMessage, "Suggestion in input field is not as expected");
    }


    @When("^Agent click send button$")
    public void clickSendButton() {
        getAgentHomePage().clickSendButton();
    }

    @When("^Agent is able to delete the suggestion from input field and sends his own \"(.*)\" message$")
    public void deleteSuggestionAndSendOwn(String agentMessage){
        getAgentHomePage().deleteSuggestionAndAddAnother(agentMessage);
//        getAgentHomePage().clickSendButton();
    }

    @When("^Agent add additional info \"(.*)\" to suggested message$")
    public void addMoreInfo(String additional) {
        getAgentHomePage().addMoreInfo(additional);
    }

    @Then("^'Clear' buttons are not shown$")
    public void checkClearEditButtonsAreShown(){
        Assert.assertTrue(getAgentHomePage().isClearButtonShown(),
                "'Clear' button is not shown for suggestion input field.");
    }

    @Then("^'Clear' and 'Edit' buttons are shown$")
    public void checkClearEditButtonsAreNotShown(){
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getAgentHomePage().isClearButtonShown(),
                "'Clear' button is not shown for suggestion input field.");
        soft.assertTrue(getAgentHomePage().isEditButtonShown(),
                "'Edit' button is not shown for suggestion input field.");
        soft.assertAll();
    }

    @When("^Agent click Edit suggestions button$")
    public void clickEditButton(){
        getAgentHomePage().clickEditButton();
    }

    @When("^Agent click Clear suggestions button$")
    public void clickClearButton(){
        getAgentHomePage().clickClearButton();
    }

    @Then("^Message input field is cleared$")
    public void verifySuggestionClearedByClearButton(){
        Assert.assertTrue(getAgentHomePage().isMessageInputFieldEmpty(),
                "Message input field is not empty");
    }

    @Then("Agent is able to add \"(.*)\"")
    public void enterAdditionTextForSuggestion(String textToAdd){
        if(!getAgentHomePage().isSuggestionContainerDisappears()){
            Assert.assertTrue(false, "Input field is not become cklickable");
        }
        getAgentHomePage().sendResponseToUser(textToAdd);
    }

    @Then("^'Profanity not allowed' pop up is shown$")
    public void verifyProfanityNotAllowedPopupShwon(){
        Assert.assertTrue(getAgentHomePage().isProfanityPopupShown(),
                "'Profanity not allowed' popup not shown.");
    }

    @When("^Agent closes 'Profanity not allowed' popup$")
    public void closeProfanityPopup(){
        getAgentHomePage().clickAcceptProfanityPopupButton();
    }


    @When("^Agent click \"End chat\" button$")
    public void clickEndChatButton(){
        getAgentHomePage().clickEndChat();
    }

    @Then("^End chat popup should be opened$")
    public void verifyEndChatPopupOpened(){
        Assert.assertTrue(getAgentHomePage().isEndChatPopupShown(),
                "End chat popup is not opened");
    }

    @When("^Agent click 'Close chat' button$")
    public void clickCloseChatButton(){
        getAgentHomePage().clickCloseButtonInCloseChatPopup();
    }



    private AgentHomePage getAgentHomePage() {
        if (agentHomePage==null) {
            agentHomePage =  new AgentHomePage("");
            return agentHomePage;
        } else{
            return agentHomePage;
        }
    }

    private AgentHomePage getAgentHomePage(String ordinalAgentNumber){
        if (ordinalAgentNumber.equalsIgnoreCase("second agent")){
            return getAgentHomeForSecondAgent();
        } else {
            return getAgentHomeForMainAgent();
        }
    }

    private AgentHomePage getAgentHomeForSecondAgent(){
        if (secondAgentHomePage==null) {
            secondAgentHomePage = new AgentHomePage("second agent");
            return secondAgentHomePage;
        } else{
            return secondAgentHomePage;
        }
    }

    private AgentHomePage getAgentHomeForMainAgent(){
        if (mainAgentHomePage==null) {
            mainAgentHomePage = new AgentHomePage("main agent");
            return mainAgentHomePage;
        } else{
            return mainAgentHomePage;
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

    private ChatBody getChatBody(String agent){
        return getAgentHomePage(agent).getChatBody();
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
