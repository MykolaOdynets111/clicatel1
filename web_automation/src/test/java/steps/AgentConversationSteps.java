package steps;

import agentpages.AgentHomePage;
import agentpages.uielements.ChatBody;
import agentpages.uielements.SuggestedGroup;
import agentpages.uielements.Suggestion;
import apihelper.ApiHelper;
import apihelper.ApiHelperTie;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.Intents;
import datamanager.jacksonschemas.Intent;
import drivermanager.ConfigManager;
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

    @Then("^Conversation area (?:becomes active with||contains) (.*) user's message$")
    public void verifyUserMessageOnAgentDesk(String userMessage) {
        if(userMessage.contains("personal info")){
            userMessage = "Submitted data:\n" +
                    ""+getUserNameFromLocalStorage()+"\n" +
                    "health@test.com";
        }
        Assert.assertTrue(getChatBody("main agent").isUserMessageShown(userMessage, "main agent"),
                "'" +userMessage+ "' User message is not shown in conversation area");
    }

    @Then("^Conversation area (?:becomes active with||contains) (.*) message from tweet user$")
    public void verifyUserMessageOnAgentDeskFromTwitter(String userMessage) {
        if (userMessage.contains("agent")||userMessage.contains("support")){
            userMessage = TwitterSteps.getCurrentConnectToAgentTweetText();
        }
        Assert.assertTrue(getChatBody().isUserMessageShown(userMessage, "main agent"),
                "'" +userMessage+ "' User message is not shown in conversation area (Client ID: "+getUserNameFromLocalStorage()+")");
    }

    @Then("^Conversation area (?:becomes active with||contains) (.*) message from facebook user$")
    public void verifyUserMessageOnAgentDeskFromFB(String userMessage) {
        Assert.assertTrue(getChatBody("main agent").isUserMessageShown(FacebookSteps.getCurrentUserMessageText(), "main agent"),
                "'" +userMessage+ "' User message is not shown in conversation area (Client ID: "+getUserNameFromLocalStorage()+")");
    }

    @Then("^Conversation area becomes active with (.*) user's message in it for (.*)$")
    public void verifyUserMessageOnAgentDesk(String userMessage, String agent) {
        if(ConfigManager.getSuite().equalsIgnoreCase("twitter")&userMessage.contains("support")){
            userMessage = TwitterSteps.getCurrentConnectToAgentTweetText();
        }
        if(ConfigManager.getSuite().equalsIgnoreCase("facebook")){
            userMessage = FacebookSteps.getCurrentUserMessageText();
        }
        Assert.assertTrue(getChatBody(agent).isUserMessageShown(userMessage, agent),
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

    @Then("^There is no from agent response added by default for (.*) message from fb user$")
    public void verifyIfNoAgentResponseAddedByDefaultToFBMessage(String userMessage) {
        Assert.assertFalse(getChatBody().isResponseOnUserMessageShown(FacebookSteps.getCurrentUserMessageText()),
                "There is agent answer added without agent's intention (Client ID: "+getUserNameFromLocalStorage()+")");
    }

    @When("^(.*) (?:responds with|sends a new message) (.*) to User$")
    public void sendAnswerToUser(String agent, String responseToUser){
        getAgentHomePage(agent).clearAndSendResponseToUser(responseToUser);
    }


    @When("^Agent replays with (.*) message$")
    public void respondToUserWithCheck(String agentMessage) {
        if (getAgentHomePage("main agent").isSuggestionFieldShown()) {
            deleteSuggestionAndSendOwn(agentMessage);
        } else {
            sendAnswerToUser("main agent", agentMessage);
        }
    }

    @When("^Agent clear input and send a new message (.*)$")
    public void clearAndSendAnswerToUser(String responseToUser){
        getAgentHomePage().clearAndSendResponseToUser(responseToUser);
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
//            ToDo: remove this after tie fixes the issue
            expectedResponse = expectedResponse.replace("  ", " ");
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


    @Then("There is no suggestions on '(.*)' user input")
    public void verifySuggestionIsNotShown(String userInput){
        getAgentHomePage().clickAgentAssistantButton();
        SoftAssert softAssert = new SoftAssert();
        String actualSuggestion = getAgentHomePage().getSuggestionFromInputFiled();
        softAssert.assertTrue(actualSuggestion.isEmpty(),"Input field is not empty\n");
        softAssert.assertTrue(getSuggestedGroup().isSuggestionListEmpty(), "Suggestions list is not empty");
        softAssert.assertAll();
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
    public void verifyProfanityNotAllowedPopupShown(){
        Assert.assertTrue(getAgentHomePage().isProfanityPopupShown(),
                "'Profanity not allowed' popup not shown.");
    }

    @When("^Agent closes 'Profanity not allowed' popup$")
    public void closeProfanityPopup(){
        getAgentHomePage().clickAcceptProfanityPopupButton();
    }


    @When("^Agent click \"End chat\" button$")
    public void clickEndChatButton(){

        getAgentHomePage().getChatHeader().clickEndChatButton();
    }

    @Then("^(?:End chat|Agent Feedback) popup should be opened$")
    public void verifyAgentFeedbackPopupOpened(){
        Assert.assertTrue(getAgentHomePage().getAgentFeedbackWindow().isEndChatPopupShown(),
                "End chat popup is not opened");
    }

    @When("^Agent click 'Close chat' button$")
    public void clickCloseChatButton(){
        getAgentHomePage().getAgentFeedbackWindow().clickCloseButtonInCloseChatPopup();
    }

//    @When("(.*) click 'Close chat' button$")
//    public void agentClickCloseСhatButton(String agent) {
//        getAgentHomePage(agent).getAgentFeedbackWindow().clickCloseСhat();
//    }

    @When("(.*) click 'Skip' button$")
    public void agentClickSkipButton(String agent) {
        getAgentHomePage(agent).getAgentFeedbackWindow().clickSkip();
    }


    @When("(.*) fills form$")
    public void agentFillsForm(String agent) {
        getAgentHomePage(agent).getAgentFeedbackWindow().typeCRMNoteTextField("Note text field");
        getAgentHomePage(agent).getAgentFeedbackWindow().typeCRMLink("Note text Link");
        getAgentHomePage(agent).getAgentFeedbackWindow().typeCRMTicketNumber("12345");
    }

    @When("(.*) closes chat")
    public void closeChat(String agent){
        getAgentHomePage(agent).endChat();
    }

    @When("(.*) click 'Cancel' button$")
    public void agentClickCancelButton(String agent) {
        getAgentHomePage(agent).getAgentFeedbackWindow().clickCancel();
    }

    @Then("^Suggestions are not shown$")
    public void verifySuggestionNotShown(){
        getAgentHomePage().clickAgentAssistantButton();
        Assert.assertTrue(getSuggestedGroup().isSuggestionListEmpty(),
                "Suggestions list is not empty.");
    }

    @Then("And message that feature is not available is shown")
    public void verifySuggestionFeatureNotAvailable(){
        String expectedMessage = "Agent assist is not available on your current touch package";
        Assert.assertEquals(getSuggestedGroup().getSuggestionsNotAvailableMessage(), expectedMessage,
                "Error message that Agent Assist feature is not available is not as expected");
    }

    @Then("^(?:End chat|Agent Feedback) popup is not shown$")
    public void verifyAgentFeedbackPopupNotOpened(){
        Assert.assertFalse(getAgentHomePage().getAgentFeedbackWindow().isEndChatPopupShown(),
                "Agent Feedback popup is opened");
    }

    @Then("^Correct sentiment on (.*) user's message is stored in DB$")
    public void verifyCorrectSentimentStoredInDb(String userMessage){
        String expectedSentiment = ApiHelperTie.getTIESentimentOnMessage(userMessage);
        String sentimentFromAPI = ApiHelper.getSessionDetails(getUserNameFromLocalStorage()).getBody().jsonPath().get("data[0].attributes.sentiment");
        for(int i = 0; i<8; i++){
            if(!expectedSentiment.equalsIgnoreCase(sentimentFromAPI)){
                getAgentHomePage().waitFor(1000);
                sentimentFromAPI = ApiHelper.getSessionDetails(getUserNameFromLocalStorage()).getBody().jsonPath().get("data[0].attributes.sentiment");
            }else {
                break;
            }
        }
        Assert.assertEquals(sentimentFromAPI, expectedSentiment,
                "Sentiment on '" + userMessage + "' user message is saved incorrectly\n");
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
