package steps.agentsteps;

import agentpages.uielements.Suggestion;
import apihelper.ApiHelper;
import apihelper.ApiHelperTie;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.Intents;
import datamanager.jacksonschemas.Intent;
import dbmanager.DBConnector;
import driverfactory.DriverFactory;
import drivermanager.ConfigManager;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import steps.FacebookSteps;
import steps.TwitterSteps;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AgentConversationSteps extends AbstractAgentSteps {

    private static String selectedEmoji;
    private static ThreadLocal<List<String>> messagesFromChatBody = new ThreadLocal<List<String>>();

    public static String getSelectedEmoji() {
        return selectedEmoji;
    }

    public static ThreadLocal<List<String>> getMessagesFromChatBody() {
        return messagesFromChatBody;
    }

    @Then("^Conversation area (?:becomes active with||contains) (.*) user's message$")
    public void verifyUserMessageOnAgentDesk(String userMessage) {
        if (userMessage.contains("personal info")) {
            userMessage = "Submitted data:\n" +
                    "" + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + "\n" +
                    "health@test.com";

            Assert.assertEquals(getChatBody("main agent").getPersonalInfoText(),
                    userMessage, "Personal info message is not shown in conversation area");
            return;
        }
        Assert.assertTrue(getChatBody("main agent").isUserMessageShown(userMessage),
                "'" + userMessage + "' User message is not shown in conversation area");
    }

    @When("^Agent click on emoji icon$")
    public void selectRamdomFrequetlyUsedEmogy() {
        getAgentHomePage("main").getChatForm().clickEmoticonButton();
        selectedEmoji = getAgentHomePage("main").getChatForm().selectRandomFrequentlyUsedEmoji();
    }

    @Then("^Conversation area (?:becomes active with||contains) (.*) message from twitter user$")
    public void verifyUserMessageOnAgentDeskFromTwitter(String userMessage) {
        if (userMessage.contains("agent") || userMessage.contains("support")) {
            userMessage = TwitterSteps.getCurrentConnectToAgentTweetText();
        } else {
            userMessage = FacebookSteps.getCurrentUserMessageText();
        }
        Assert.assertTrue(getChatBody("main").isUserMessageShown(userMessage),
                "'" + userMessage + "' User message is not shown in conversation area");
    }

    @Then("^Conversation area (?:becomes active with||contains) (.*) message from facebook user$")
    public void verifyUserMessageOnAgentDeskFromFB(String userMessage) {
        Assert.assertTrue(getChatBody("main agent").isUserMessageShown(FacebookSteps.getCurrentUserMessageText()),
                "'" + userMessage + "' User message is not shown in conversation area (Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ")");
    }

    @Then("^Conversation area becomes active with (.*) user's message in it for (.*)$")
    public void verifyUserMessageOnAgentDesk(String userMessage, String agent) {
        if (ConfigManager.getSuite().equalsIgnoreCase("twitter") & userMessage.contains("support")) {
            userMessage = TwitterSteps.getCurrentConnectToAgentTweetText();
        }
        if (ConfigManager.getSuite().equalsIgnoreCase("facebook")) {
            userMessage = FacebookSteps.getCurrentUserMessageText();
        }
        Assert.assertTrue(getChatBody(agent).isUserMessageShown(userMessage),
                "'" + userMessage + "' User message is not shown in conversation area (Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ")");
    }

    @Then("^There is no more than one from user message$")
    public void checkThereIsNoMoreThanOneUserMessage() {
        Assert.assertFalse(getChatBody("main").isMoreThanOneUserMassageShown(),
                "More than one user message is shown (Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ")");
    }

    @Then("^There is no from (.*) response added by default for (.*) user message$")
    public void verifyIfNoAgentResponseAddedByDefault(String agent, String userMessage) {
        Assert.assertFalse(getChatBody(agent).isResponseOnUserMessageShown(userMessage),
                "There is agent answer added without agent's intention (Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ")");
    }

    @And("Collect (.*) chat messages")
    public void collectMassages(String agent){
        messagesFromChatBody.set(getChatBody(agent).getAllMessages());
    }

    @Then("^Sent emoji is displayed on chatdesk$")
    public void verifyEmojiDisplayedOnChatdesk() {
        String userMessage = "Submitted data:\n" +
                "" + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + "\n" +
                "health@test.com";
        Assert.assertTrue(getAgentHomePage("main agent").getChatBody()
                        .getAgentEmojiResponseOnUserMessage(userMessage).contains(selectedEmoji),
                "Expected to user emoji '" + selectedEmoji + "' is not shown in chatdesk");
    }

    @Then("^There is no from (.*) response added by default for (.*) message from fb user$")
    public void verifyIfNoAgentResponseAddedByDefaultToFBMessage(String agent, String userMessage) {
        Assert.assertFalse(getChatBody(agent).isResponseOnUserMessageShown(FacebookSteps.getCurrentUserMessageText()),
                "There is agent answer added without agent's intention (Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ")");
    }

    @When("^(.*) (?:responds with|sends a new message) (.*) to User$")
    public void sendAnswerToUser(String agent, String responseToUser) {
        getAgentHomePage(agent).getChatForm().clearAndSendResponseToUser(responseToUser);
    }


    @When("^(.*) replays with (.*) message$")
    public void respondToUserWithCheck(String agent, String agentMessage) {
        if (getAgentHomePage(agent).getChatForm().isSuggestionFieldShown()) {
            deleteSuggestionAndSendOwn(agent, agentMessage);
        } else {
            sendAnswerToUser("main agent", agentMessage);
        }
    }

    @When("^(.*) clear input and send a new message (.*)$")
    public void clearAndSendAnswerToUser(String agent, String responseToUser) {
        getAgentHomePage(agent).getChatForm().clearAndSendResponseToUser(responseToUser);
    }

    @When("^(.*) response with emoticon to User$")
    public void clickSendMessageButton(String agent) {
        getAgentHomePage(agent).getChatForm().clickSendButton();
    }

    @Then("^There is correct suggestion shown on user message \"(.*)\"(?: and sorted by confidence|)$")
    public void verifySuggestionsCorrectnessFor(String userMessage) {
        getAgentHomePage("main").clickAgentAssistantButton();
        getAgentHomePage("main").isElementShown(getAgentHomePage("main").getCurrentDriver(),
                getAgentHomePage("main").getSuggestedGroup(), 4);
        if (getSuggestedGroup("main").isSuggestionListEmpty()) {
            Assert.fail("Suggestion list is empty");
        }
        String expectedResponse = "no response";
        List<Intent> listOfIntentsFromTIE = ApiHelperTie.getListOfIntentsOnUserMessage(userMessage);
        List<String> answersFromTie = new ArrayList<>();
        for (int i = 0; i < listOfIntentsFromTIE.size(); i++) {
            expectedResponse = ApiHelperTie.getExpectedMessageOnIntent(listOfIntentsFromTIE.get(i).getIntent());
            if (expectedResponse.contains("${firstName}")) {
                String userName = getAgentHomePage("main").getChatHeader().getChatHeaderText().split(" ")[0];
                expectedResponse = expectedResponse.replace("${firstName}", userName);
            }
//            ToDo: remove this after tie fixes the issue
            expectedResponse = expectedResponse.replace("  ", " ");
            answersFromTie.add(i, expectedResponse);
        }
        List<Suggestion> actualSuggestions = getSuggestedGroup("main").getSuggestionsList();
        List<String> suggestionTextsActual = actualSuggestions.stream().map(e -> e.getSuggestionMessage()).collect(Collectors.toList());
        Assert.assertEquals(suggestionTextsActual, answersFromTie, "Shown Suggestions is not as expected");
    }

    @Then("^The suggestion for user message \"(.*)\" with the biggest confidence is added to the input field$")
    public void verifyAutomaticAddingSuggestingToInputField(String userMessage) {
        String actualSuggestion = getAgentHomePage("main").getChatForm().getSuggestionFromInputFiled();
        if (actualSuggestion == null) Assert.fail("There is no added suggestion in input field");
        String expectedMessage = ApiHelperTie.getExpectedMessageOnIntent(
                Intents.getIntentWithMaxConfidence(userMessage).getIntent());
        if (expectedMessage.contains("${firstName}")) {
            String userName = getAgentHomePage("main").getChatHeader().getChatHeaderText().split(" ")[0];
            expectedMessage = expectedMessage.replace("${firstName}", userName);
        }
        Assert.assertEquals(actualSuggestion, expectedMessage, "Suggestion in input field is not as expected");
    }


    @Then("There is no suggestions on '(.*)' user input")
    public void verifySuggestionIsNotShown(String userInput) {
        getAgentHomePage("main").clickAgentAssistantButton();
        SoftAssert softAssert = new SoftAssert();
        String actualSuggestion = getAgentHomePage("main").getChatForm().getSuggestionFromInputFiled();
        softAssert.assertTrue(actualSuggestion.isEmpty(), "Input field is not empty\n");
        softAssert.assertTrue(getSuggestedGroup("main").isSuggestionListEmpty(), "Suggestions list is not empty");
        softAssert.assertAll();
    }

    @When("^(.*) click send button$")
    public void clickSendButton(String agent) {
        getAgentHomePage(agent).getChatForm().clickSendButton();
    }

    @When("^(.*) is able to delete the suggestion from input field and sends his own \"(.*)\" message$")
    public void deleteSuggestionAndSendOwn(String agent, String agentMessage) {
        getAgentHomePage(agent).getChatForm().deleteSuggestionAndAddAnother(agentMessage);
    }


    @When("^(.*) add additional info \"(.*)\" to suggested message$")
    public void addMoreInfo(String agent, String additional) {
        getAgentHomePage(agent).getChatForm().addMoreInfo(additional);
    }

    @Then("^'Clear' buttons are not shown$")
    public void checkClearEditButtonsAreShown() {
        Assert.assertTrue(getAgentHomePage("main").getChatForm().isClearButtonShown(),
                "'Clear' button is not shown for suggestion input field.");
    }

    @Then("^'Clear' button is shown$")
    public void checkClearEditButtonsAreNotShown() {
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getAgentHomePage("main").getChatForm().isClearButtonShown(),
                "'Clear' button is not shown for suggestion input field.");
        soft.assertAll();
    }

    @When("^(.*) click Clear suggestions button$")
    public void clickClearButton(String agent) {
        getAgentHomePage(agent).getChatForm().clickClearButton();
    }

    @Then("^Message input field is cleared$")
    public void verifySuggestionClearedByClearButton() {
        Assert.assertTrue(getAgentHomePage("main").getChatForm().isMessageInputFieldEmpty(),
                "Message input field is not empty");
    }

    @Then("Agent is able to add \"(.*)\"")
    public void enterAdditionTextForSuggestion(String textToAdd) {
        getAgentHomePage("main").getChatForm().sendResponseToUser(textToAdd);
    }

    @Then("^'Profanity not allowed' pop up is shown$")
    public void verifyProfanityNotAllowedPopupShown() {
        Assert.assertTrue(getAgentHomePage("main").isProfanityPopupShown(),
                "'Profanity not allowed' popup not shown.");
    }

    @When("^(.*) closes 'Profanity not allowed' popup$")
    public void closeProfanityPopup(String agent) {
        getAgentHomePage(agent).clickAcceptProfanityPopupButton();
    }


    @When("^(.*) click \"End chat\" button$")
    public void clickEndChatButton(String agent) {
        getAgentHomePage(agent).getChatHeader().clickEndChatButton();
    }

    @Then("^(?:End chat|Agent Feedback) popup should be opened$")
    public void verifyAgentFeedbackPopupOpened() {
        Assert.assertTrue(getAgentHomePage("main").getAgentFeedbackWindow().isEndChatPopupShown(),
                "End chat popup is not opened");
    }

    @When("^(.*) click 'Close chat' button$")
    public void clickCloseChatButton(String agent) {
        getAgentHomePage(agent).getAgentFeedbackWindow().clickCloseButtonInCloseChatPopup();
    }

    @When("(.*) fills form$")
    public void agentFillsForm(String agent) {
        getAgentHomePage(agent).getAgentFeedbackWindow().typeCRMNoteTextField("Note text field");
        getAgentHomePage(agent).getAgentFeedbackWindow().typeCRMLink("Note text Link");
        getAgentHomePage(agent).getAgentFeedbackWindow().typeCRMTicketNumber("12345");
    }

    @When("(.*) closes chat")
    public void closeChat(String agent) {
        getAgentHomePage(agent).endChat();
    }

    @Then("^All session attributes are closed in DB$")
    public void verifySessionClosed() {
        SoftAssert soft = new SoftAssert();
        boolean result = waitForSessionToBeClosed(5);
        Map<String, String> sessionDetails = DBConnector
                .getSessionDetailsByClientID(ConfigManager.getEnv(), getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()));

        Map<String, String> chatAgentDetails = DBConnector
                .getChatAgentHistoryDetailsBySessionID(ConfigManager.getEnv(), sessionDetails.get("sessionId"));
        Map<String, String> conversationDetails = DBConnector
                .getConversationByID(ConfigManager.getEnv(), sessionDetails.get("conversationId"));

        soft.assertTrue(result,
                "Ended date is not set for session " + sessionDetails.get("sessionId") + " after ending chat\n" +
                "Session " + sessionDetails.get("sessionId") + " is not terminated after ending chat. \n\n");
        soft.assertTrue(chatAgentDetails.get("endedDate") != null,
                "Ended date is not set for chat agent history record after ending chat.\n" +
                        "\nSession " + sessionDetails.get("sessionId") + "\n\n");
        soft.assertEquals(conversationDetails.get("active"), "0",
                "Conversation is still active after ending chat.\n" +
                        "\nSession " + sessionDetails.get("sessionId") + "\n\n");
        soft.assertAll();
    }

    private boolean waitForSessionToBeClosed(int wait){
        Map<String, String> sessionDetails;
        for(int i=0; i<wait; i++){
            sessionDetails = DBConnector.getSessionDetailsByClientID(ConfigManager.getEnv(), getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()));
            if(sessionDetails.get("state").equals("TERMINATED") & sessionDetails.get("endedDate") != null) return true;
            else waitFor(1000);
        }
        return false;
    }

    @When("(.*) click 'Cancel' button$")
    public void agentClickCancelButton(String agent) {
        getAgentHomePage(agent).getAgentFeedbackWindow().clickCancel();
    }

    @Then("^Suggestions are not shown$")
    public void verifySuggestionNotShown() {
        getAgentHomePage("main").clickAgentAssistantButton();
        Assert.assertTrue(getSuggestedGroup("main").isSuggestionListEmpty(),
                "Suggestions list is not empty.");
    }

    @Then("And message that feature is not available is shown")
    public void verifySuggestionFeatureNotAvailable() {
        String expectedMessage = "Agent assist is not available on your current touch package";
        Assert.assertEquals(getSuggestedGroup("main").getSuggestionsNotAvailableMessage(), expectedMessage,
                "Error message that Agent Assist feature is not available is not as expected");
    }

    @Then("^(?:End chat|Agent Feedback) popup is not shown$")
    public void verifyAgentFeedbackPopupNotOpened() {
        Assert.assertFalse(getAgentHomePage("main").getAgentFeedbackWindow().isEndChatPopupShown(),
                "Agent Feedback popup is opened");
    }

    @Then("^Correct sentiment on (.*) user's message is stored in DB$")
    public void verifyCorrectSentimentStoredInDb(String userMessage) {
        String expectedSentiment = ApiHelperTie.getTIESentimentOnMessage(userMessage);
        String sentimentFromAPI = ApiHelper.getSessionDetails(getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance())).getBody().jsonPath().get("data[0].attributes.sentiment");
        for (int i = 0; i < 8; i++) {
            if (!expectedSentiment.equalsIgnoreCase(sentimentFromAPI)) {
                getAgentHomePage("main").waitFor(1000);
                sentimentFromAPI = ApiHelper.getSessionDetails(getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance())).getBody().jsonPath().get("data[0].attributes.sentiment");
            } else {
                break;
            }
        }
        Assert.assertEquals(sentimentFromAPI, expectedSentiment,
                "Sentiment on '" + userMessage + "' user message is saved incorrectly\n");
    }

    @Then("(.*) can see default (.*) placeholder for note if there is no input made$")
    public void defaultWordsShouldBeIfThereIsNoInputMade(String agent, String words) {
        Assert.assertEquals(getAgentHomePage(agent).getAgentFeedbackWindow().getPlaceholder(), words,
                "Placeholder for note is incorrect\n");
    }

    @Then("^(.*) can see valid sentiments \\(Neutral sentiment by default, There are 3 icons for sentiments\\)$")
    public void validSentimentsAreShown(String agent) {
        File image = new File(System.getProperty("user.dir")+"/touch/src/test/resources/sentimenticons/sentimentsConcludeWindowNeutral.png");
        Assert.assertTrue(getAgentHomePage(agent).getAgentFeedbackWindow().isValidSentiments(image), "Sentiments in agent feedback window as not expected. (Neutral sentiment by default, There are 3 icons for sentiments) \n");
    }

    @Then("^(.*) is able to select sentiments, when sentiment is selected, 2 other should be blurred$")
    public void agentIsAbleToSelectSentimentWhenSentimentIsSelectedOtherShouldBeBlurred(String agent) {
        boolean result = false;
        boolean resultHappy = false;
        boolean resultUnsatisfied = false;
        File imageNeutral = new File(System.getProperty("user.dir")+"/touch/src/test/resources/sentimenticons/sentimentsConcludeWindowNeutral.png");
        result = getAgentHomePage(agent).getAgentFeedbackWindow().isValidSentiments(imageNeutral);
        getAgentHomePage(agent).getAgentFeedbackWindow().setSentimentHappy();
        File imageHappy = new File(System.getProperty("user.dir")+"/touch/src/test/resources/sentimenticons/sentimentsConcludeWindowHappy.png");
        resultHappy = getAgentHomePage(agent).getAgentFeedbackWindow().isValidSentiments(imageHappy);
        getAgentHomePage(agent).getAgentFeedbackWindow().setSentimentUnsatisfied();
        File imageUnsatisfied = new File(System.getProperty("user.dir")+"/touch/src/test/resources/sentimenticons/sentimentsConcludeWindowUnsatisfied.png");
        resultUnsatisfied = getAgentHomePage(agent).getAgentFeedbackWindow().isValidSentiments(imageUnsatisfied);
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(result, "Neutral. Sentiments in agent feedback window as not expected. \n");
        soft.assertTrue(resultHappy, "Happy. Sentiments in agent feedback window as not expected. \n");
        soft.assertTrue(resultUnsatisfied, "Unsatisfied. Sentiments in agent feedback window as not expected. \n");
        soft.assertAll();
    }


    @Then("^(.*) sees \"(.*)\" tip in conversation area$")
    public void verifyTipIfNoSelectedChat(String agent, String note){
        Assert.assertEquals(getAgentHomePage(agent).getTipIfNoChatSelected(), note,
                "Tip note if no chat selected is not as expected");
    }

    @Then("^(.*) sees \"(.*)\" tip in context area$")
    public void verifyTipIfNoSelectedChatInContextArea(String agent, String note){
        Assert.assertEquals(getAgentHomePage(agent).getTipIfNoChatSelectedFromContextArea(), note,
                "Tip note in context area if no chat selected is not as expected");
    }

    @Then("^(.*) sees \"(.*)\" placeholder in input field$")
    public void verifyInputFieldPlaceholder(String agent, String placeholder){
        Assert.assertEquals(getAgentHomePage(agent).getChatForm().getPlaceholderFromInputLocator(), placeholder,
                "Placeholder in input field in opened chat is not as expected");
    }
    @Then("Messages is correctly displayed and has correct color")
    public void verifyMessages(){
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getChatBody("main agent").verifyAgentMessageColours(),
                "Agent messages' color is not as expected");
        soft.assertTrue(getChatBody("main agent").verifyMessagesPosition(),
                "Messages location is not as expected");
        soft.assertAll();
    }

    @Then("Default user image is shown")
    public void verifyDefaultUserImage() {
        Assert.assertTrue(getChatBody("main agent").isValidDefaultUserProfileIcon(),
                "Incorrect default user picture shown");
    }

}
