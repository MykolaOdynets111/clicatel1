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
import dbmanager.DBConnector;
import drivermanager.ConfigManager;
import interfaces.JSHelper;
import interfaces.WebActions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AgentConversationSteps implements JSHelper, WebActions {

    private AgentHomePage mainAgentHomePage;
    private AgentHomePage secondAgentHomePage;
    //    private AgentHomePage agentHomePage ;
    private static String selectedEmoji;

    public static String getSelectedEmoji() {
        return selectedEmoji;
    }

    @Then("^Conversation area (?:becomes active with||contains) (.*) user's message$")
    public void verifyUserMessageOnAgentDesk(String userMessage) {
        if (userMessage.contains("personal info")) {
            userMessage = "Submitted data:\n" +
                    "" + getUserNameFromLocalStorage() + "\n" +
                    "health@test.com";
        }
        Assert.assertTrue(getChatBody("main agent").isUserMessageShown(userMessage, "main agent"),
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
        Assert.assertTrue(getChatBody("main").isUserMessageShown(userMessage, "main agent"),
                "'" + userMessage + "' User message is not shown in conversation area");
    }

    @Then("^Conversation area (?:becomes active with||contains) (.*) message from facebook user$")
    public void verifyUserMessageOnAgentDeskFromFB(String userMessage) {
        Assert.assertTrue(getChatBody("main agent").isUserMessageShown(FacebookSteps.getCurrentUserMessageText(), "main agent"),
                "'" + userMessage + "' User message is not shown in conversation area (Client ID: " + getUserNameFromLocalStorage() + ")");
    }

    @Then("^Conversation area becomes active with (.*) user's message in it for (.*)$")
    public void verifyUserMessageOnAgentDesk(String userMessage, String agent) {
        if (ConfigManager.getSuite().equalsIgnoreCase("twitter") & userMessage.contains("support")) {
            userMessage = TwitterSteps.getCurrentConnectToAgentTweetText();
        }
        if (ConfigManager.getSuite().equalsIgnoreCase("facebook")) {
            userMessage = FacebookSteps.getCurrentUserMessageText();
        }
        Assert.assertTrue(getChatBody(agent).isUserMessageShown(userMessage, agent),
                "'" + userMessage + "' User message is not shown in conversation area (Client ID: " + getUserNameFromLocalStorage() + ")");
    }

    @Then("^There is no more than one from user message$")
    public void checkThereIsNoMoreThanOneUserMessage() {
        Assert.assertFalse(getChatBody("main").isMoreThanOneUserMassageShown(),
                "More than one user message is shown (Client ID: " + getUserNameFromLocalStorage() + ")");
    }

    @Then("^There is no from (.*) response added by default for (.*) user message$")
    public void verifyIfNoAgentResponseAddedByDefault(String agent, String userMessage) {
        Assert.assertFalse(getChatBody(agent).isResponseOnUserMessageShown(userMessage),
                "There is agent answer added without agent's intention (Client ID: " + getUserNameFromLocalStorage() + ")");
    }

    @Then("^Sent emoji is displayed on chatdesk$")
    public void verifyEmojiDisplayedOnChatdesk() {
        String userMessage = "Submitted data:\n" +
                "" + getUserNameFromLocalStorage() + "\n" +
                "health@test.com";
        Assert.assertTrue(getAgentHomePage("main agent").getChatBody()
                        .getAgentEmojiResponseOnUserMessage(userMessage).contains(selectedEmoji),
                "Expected to user emoji '" + selectedEmoji + "' is not shown in chatdesk");
    }

    @Then("^There is no from (.*) response added by default for (.*) message from fb user$")
    public void verifyIfNoAgentResponseAddedByDefaultToFBMessage(String agent, String userMessage) {
        Assert.assertFalse(getChatBody(agent).isResponseOnUserMessageShown(FacebookSteps.getCurrentUserMessageText()),
                "There is agent answer added without agent's intention (Client ID: " + getUserNameFromLocalStorage() + ")");
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
        getAgentHomePage("main").waitForElementToBeVisible(getAgentHomePage("main").getSuggestedGroup());
        if (getSuggestedGroup("main").isSuggestionListEmpty()) {
            Assert.fail("Suggestion list is empty");
        }
        String expectedResponse = "no response";
        List<Intent> listOfIntentsFromTIE = ApiHelperTie.getListOfIntentsOnUserMessage(userMessage);
        List<String> answersFromTie = new ArrayList<>();
        for (int i = 0; i < listOfIntentsFromTIE.size(); i++) {
            expectedResponse = ApiHelperTie.getExpectedMessageOnIntent(listOfIntentsFromTIE.get(i).getIntent());
            if (expectedResponse.contains("${firstName}")) {
                expectedResponse = expectedResponse.replace("${firstName}", getUserNameFromLocalStorage());
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
        if (actualSuggestion == null) {
            Assert.assertTrue(false, "There is no added suggestion in input field");
        }
        String expectedMessage = ApiHelperTie.getExpectedMessageOnIntent(
                Intents.getIntentWithMaxConfidence(userMessage).getIntent());
        if (expectedMessage.contains("${firstName}")) {
            expectedMessage = expectedMessage.replace("${firstName}", getUserNameFromLocalStorage());
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

    @Then("^'Clear' and 'Edit' buttons are shown$")
    public void checkClearEditButtonsAreNotShown() {
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getAgentHomePage("main").getChatForm().isClearButtonShown(),
                "'Clear' button is not shown for suggestion input field.");
        soft.assertTrue(getAgentHomePage("main").getChatForm().isEditButtonShown(),
                "'Edit' button is not shown for suggestion input field.");
        soft.assertAll();
    }

    @When("^(.*) click Edit suggestions button$")
    public void clickEditButton(String agent) {
        getAgentHomePage(agent).getChatForm().clickEditButton();
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
        if (!getAgentHomePage("main").getChatForm().isSuggestionContainerDisappears()) {
            Assert.assertTrue(false, "Input field is not become cklickable");
        }
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
    public void closeChat(String agent) {
        getAgentHomePage(agent).endChat();
    }

    @Then("^All session attributes are closed in DB$")
    public void verifySessionClosed() {
        SoftAssert soft = new SoftAssert();
        Map<String, String> sessionDetails = DBConnector
                .getSessionDetailsByClientID(ConfigManager.getEnv(), getUserNameFromLocalStorage());
        Map<String, String> chatAgentDetails = DBConnector
                .getChatAgentHistoryDetailsBySessionID(ConfigManager.getEnv(), sessionDetails.get("sessionId"));
        Map<String, String> conversationDetails = DBConnector
                .getConversationByID(ConfigManager.getEnv(), sessionDetails.get("conversationId"));

        soft.assertEquals(sessionDetails.get("state"), "TERMINATED",
                "Session " + sessionDetails.get("sessionId") + " is not terminated after ending chat. ");
        soft.assertTrue(sessionDetails.get("endedDate") != null,
                "Ended date is not set for session " + sessionDetails.get("sessionId") + " after ending chat");
        soft.assertTrue(chatAgentDetails.get("endedDate") != null,
                "Ended date is not set for chat agent history record after ending chat." +
                        "\nSession " + sessionDetails.get("sessionId") + "");
        soft.assertEquals(conversationDetails.get("active"), "0",
                "Conversation is still active after ending chat." +
                        "\nSession " + sessionDetails.get("sessionId") + "");
        soft.assertAll();
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
        String sentimentFromAPI = ApiHelper.getSessionDetails(getUserNameFromLocalStorage()).getBody().jsonPath().get("data[0].attributes.sentiment");
        for (int i = 0; i < 8; i++) {
            if (!expectedSentiment.equalsIgnoreCase(sentimentFromAPI)) {
                getAgentHomePage("main").waitFor(1000);
                sentimentFromAPI = ApiHelper.getSessionDetails(getUserNameFromLocalStorage()).getBody().jsonPath().get("data[0].attributes.sentiment");
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
        File image = new File("src/test/resources/sentimenticons/sentimentsConcludeWindowNeutral.png");
        Assert.assertTrue(getAgentHomePage(agent).getAgentFeedbackWindow().isValidSentiments(image), "Sentiments in agent feedback window as not expected. (Neutral sentiment by default, There are 3 icons for sentiments) \n");
    }

    @Then("^(.*) is able to select sentiments, when sentiment is selected, 2 other should be blurred$")
    public void agentIsAbleToSelectSentimentWhenSentimentIsSelectedOtherShouldBeBlurred(String agent) {
        boolean result = false;
        boolean resultHappy = false;
        boolean resultUnsatisfied = false;
        File imageNeutral = new File("src/test/resources/sentimenticons/sentimentsConcludeWindowNeutral.png");
        result = getAgentHomePage(agent).getAgentFeedbackWindow().isValidSentiments(imageNeutral);
        getAgentHomePage(agent).getAgentFeedbackWindow().setSentimentHappy();
        File imageHappy = new File("src/test/resources/sentimenticons/sentimentsConcludeWindowHappy.png");
        resultHappy = getAgentHomePage(agent).getAgentFeedbackWindow().isValidSentiments(imageHappy);
        getAgentHomePage(agent).getAgentFeedbackWindow().setSentimentUnsatisfied();
        File imageUnsatisfied = new File("src/test/resources/sentimenticons/sentimentsConcludeWindowUnsatisfied.png");
        resultUnsatisfied = getAgentHomePage(agent).getAgentFeedbackWindow().isValidSentiments(imageUnsatisfied);
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(result, "Neutral. Sentiments in agent feedback window as not expected. \n");
        soft.assertTrue(resultHappy, "Happy. Sentiments in agent feedback window as not expected. \n");
        soft.assertTrue(resultUnsatisfied, "Unsatisfied. Sentiments in agent feedback window as not expected. \n");
        soft.assertAll();
    }


    private AgentHomePage getAgentHomePage(String ordinalAgentNumber) {
        if (ordinalAgentNumber.equalsIgnoreCase("second agent")) {
            return getAgentHomeForSecondAgent();
        } else {
            return getAgentHomeForMainAgent();
        }
    }

    private AgentHomePage getAgentHomeForSecondAgent() {
        if (secondAgentHomePage == null) {
            secondAgentHomePage = new AgentHomePage("second agent");
            return secondAgentHomePage;
        } else {
            return secondAgentHomePage;
        }
    }

    private AgentHomePage getAgentHomeForMainAgent() {
        if (mainAgentHomePage == null) {
            mainAgentHomePage = new AgentHomePage("main agent");
            return mainAgentHomePage;
        } else {
            return mainAgentHomePage;
        }
    }


    private ChatBody getChatBody(String agent) {
        return getAgentHomePage(agent).getChatBody();
    }

    private SuggestedGroup getSuggestedGroup(String agent) {
        return getAgentHomePage(agent).getSuggestedGroup();
    }


}
