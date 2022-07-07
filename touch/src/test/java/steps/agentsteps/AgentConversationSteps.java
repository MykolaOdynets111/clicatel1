package steps.agentsteps;

import agentpages.uielements.ChatAttachment;
import agentpages.uielements.Suggestion;
import apihelper.ApiHelper;
import apihelper.ApiHelperTie;
import com.github.javafaker.Faker;
import com.google.common.io.Files;
import datamanager.Intents;
import datamanager.jacksonschemas.Intent;
import dbmanager.DBConnector;
import driverfactory.DriverFactory;
import drivermanager.ConfigManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import steps.DefaultTouchUserSteps;
import steps.FacebookSteps;
import steps.TwitterSteps;
import steps.dotcontrol.DotControlSteps;
import touchpages.uielements.LocationWindow;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AgentConversationSteps extends AbstractAgentSteps {

    private static String selectedEmoji;
    private static ThreadLocal<List<String>> messagesFromChatBody = new ThreadLocal<List<String>>();
    public static ThreadLocal<String> locationURL = new ThreadLocal<String>();
    private LocationWindow locationWindow ;

    public static String getSelectedEmoji() {
        return selectedEmoji;
    }

    public static ThreadLocal<List<String>> getMessagesFromChatBody() {
        return messagesFromChatBody;
    }

    public static String getLocationURL(){
        return locationURL.get();
    }

    @Then("^Conversation area (?:becomes active with||contains) (.*) user's message$")
    public void verifyUserMessageOnAgentDesk(String userMessage) {
        if (userMessage.contains("personal info"))
        {
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

    @Then ("^Visual indicator (.*) with \"(.*)\" text, (.*) name and time is shown$")
    public void verifyVisualIndicators(String indicator, String message, String agent){
        String  generatedMessage = message + " " + getAgentName(agent);
        validateIndicators(indicator, generatedMessage, agent);
    }

    @Then ("^Transfer indicator (.*) with \"(.*)\" text, (.*) name \" to \" (.*) name and time is shown$")
    public void verifyTransferIndicators(String indicator, String message, String firstAgent, String secondAgent){
        String generatedMessage = message + " " + getAgentName(firstAgent) + " to " + getAgentName(secondAgent);
        validateIndicators(indicator, generatedMessage, secondAgent);
    }

    @Then ("^Transfer reject indicator (.*) with First agent name and \"(.*)\" text")
    public void verifyRejectedIndicator(String indicator, String message) {
        String generatedMessage = getAgentName("First agent") + " " + message;
        String indicatorFromUI = getChatBody("Second agent").getIndicatorsText(indicator);
        Assert.assertEquals(indicatorFromUI, generatedMessage.trim(), indicator + " has incorrect text");
    }

    private void validateIndicators(String indicator, String generatedMessage, String agent){
        String indicatorFromUI = getChatBody(agent).getIndicatorsText(indicator);
        String time = indicatorFromUI.substring(indicatorFromUI.length()-8);
        String massageWithNameUI = indicatorFromUI.substring(0, indicatorFromUI.length()-8).trim();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(massageWithNameUI, generatedMessage.trim(), indicator + " has incorrect text");
        softAssert.assertTrue(time.matches("^([0-1]?[0-9]|[2][0-3]):([0-5][0-9])(:[0-5][0-9])?$"), indicator + " time is not shown");
        softAssert.assertAll();
    }

    @Then("^(.*) see conversation area with (.*) user's message$")
    public void verifyUserMessageOnAgentDeskForSpecificAgent(String agent, String userMessage) {
        if (userMessage.contains("personal info")) {
            userMessage = "Submitted data:\n" +
                    "" + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + "\n" +
                    "health@test.com";

            Assert.assertEquals(getChatBody(agent).getPersonalInfoText(),
                    userMessage, "Personal info message is not shown in conversation area");
            return;
        }
        Assert.assertTrue(getChatBody(agent).isUserMessageShown(userMessage),
                "'" + userMessage + "' User message is not shown in conversation area");
    }

    @Then("^Agent attach (.*) file type$")
    public void attachFile(String fileName) {
        File pathToFile = new File(System.getProperty("user.dir")+"/src/test/resources/mediasupport/" + fileName + "." + fileName);
        String newName = new Faker().letterify(fileName + "?????") + "." + fileName;
        File renamed =  new File(System.getProperty("user.dir")+"/src/test/resources/mediasupport/renamed/" +  newName);
        try {
            Files.copy(pathToFile, renamed);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DefaultTouchUserSteps.mediaFileName.set(newName);

        getAgentHomePage("agent").openAttachmentWindow().setPathToFile(renamed.getPath());
        Assert.assertTrue(getChatAttachmentForm("agent").isFileUploaded(), "File was not uploaded to widget");
    }

    @When("^Agent send attached file$")
    public void agentSendAttachment(){
        getChatAttachmentForm("agent").clickSendButton();
    }

    @Then ("^Attachment message (?:from (.*) is|is) shown for Agent$")
    public void verifyAttachmentPresent(String channel){
        String nameOfFile = DefaultTouchUserSteps.mediaFileName.get();
        if(!(channel == null)){
            nameOfFile = DotControlSteps.mediaFileName.get();
        }
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getChatBody("agent").isAttachmentMessageShown(), "No Attachment message was shown");
        soft.assertEquals(getChatBody("agent").getAttachmentFile().getFileName(), nameOfFile,
                "File name is not the same as the file name which user sent");
        soft.assertAll();
    }

    @When("^(.*) download the file$")
    public void downloadTheFile(String agent){
        getChatBody(agent).getAttachmentFile().clickDownloadLink();
    }

    @Then("^File is not changed after uploading and downloading$")
    public void verifyFilesEquality(){
        File fileForUpload = new File(System.getProperty("user.dir")+"/src/test/resources/mediasupport/renamed/" + DefaultTouchUserSteps.mediaFileName.get());
//        String sharedFolder = File.separator + File.separator+ "172.31.76.251"+File.separator + "Share" + File.separator
//                + "chrome" + File.separator;
        String sharedFolder = "\\\\172.31.76.251\\Share\\chrome\\";
        File downloadedFile = new File( sharedFolder +  DefaultTouchUserSteps.mediaFileName.get());
        List<String> allFiles = new ArrayList<>();
        for (int i=0; i < 10; i++){
            try (Stream<Path> walk = java.nio.file.Files.walk(Paths.get(sharedFolder))) {
                allFiles = walk.filter(java.nio.file.Files::isRegularFile)
                        .map(x -> x.toString()).collect(Collectors.toList());
                if (allFiles.contains(downloadedFile.getPath())){
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (i== 9){
                Assert.fail("File " + downloadedFile.getPath() + " was not downloaded to the shared folder\n"+
                            "the following files are found only: " + allFiles);
            }
            waitFor(2000);
        }
        boolean fileEquality = false;
        try {
            fileEquality =  FileUtils.contentEquals(fileForUpload, downloadedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(fileEquality, "Files are not equal after uploading and downloading");
    }

    @Then("^Agent can play (.*) file$")
    public void verifyIsFilePlaying(String fileType){
        ChatAttachment agentDeskChatAttachment = getChatBody("agent").getAttachmentFile().clickPlayPauseButton(fileType);
        Assert.assertTrue(agentDeskChatAttachment.verifyIsFilePlaying(), "Media content is not playing");
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
        } else if (! userMessage.equalsIgnoreCase("//end") ||
                ! userMessage.equalsIgnoreCase("//stop")){
            userMessage = FacebookSteps.getCurrentUserMessageText();
        }
        Assert.assertTrue(getChatBody("main").isUserMessageShown(userMessage),
                "'" + userMessage + "' User message is not shown in conversation area");
    }

    @Then("^Conversation area (?:becomes active with||contains) (.*) message from facebook user$")
    public void verifyUserMessageOnAgentDeskFromFB(String userMessage) {
        Assert.assertTrue(getChatBody("main agent").isUserMessageShown(FacebookSteps.getCurrentUserMessageText()),
                "'" + FacebookSteps.getCurrentUserMessageText() + "' User message is not shown in conversation area (Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ")");
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
                "'" + userMessage + "' User message is not shown in conversation area");
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

    @When("^(.*) sends (.*) Location to User$")
    public void sendLocationToUser(String agent, String locationName) {
        getAgentHomePage(agent).openLocationWindow().searchLocation(locationName).selectLocation(locationName).clickSendLocationsButton();
        waitFor(2000);// URL needs time for full creation
        locationURL.set(getAgentHomePage(agent).getChatBody().getLocationURL());
    }

    @When("^(.*) replays with (.*) message$")
    public void respondToUserWithCheck(String agent, String agentMessage) {
        if (getAgentHomePage(agent).getChatForm().isSuggestionFieldShown()) {
            deleteSuggestionAndSendOwn(agent, agentMessage);
        } else {
            sendAnswerToUser("main agent", agentMessage);
        }
    }

    @When("^(.*) send (.*) message$")
    public void sendMessageOnCurrentChat(String agent, String agentMessage) {
        getAgentHomePage(agent).getChatForm().clearAndSendResponseToUser(agentMessage);
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
        List<String> suggestionTextsActual = actualSuggestions.stream().map(Suggestion::getSuggestionMessage).collect(Collectors.toList());
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
        getAgentHomePage(agent).getChatForm().sendResponseInSuggestionWrapperToUser(additional);
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
        getAgentHomePage("main").getChatForm().sendResponseInSuggestionWrapperToUser(textToAdd);
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
        getAgentHomePage(agent).getAgentFeedbackWindow().waitForLoadingData();
    }

    @When("^(.*) click \"End chat\" button without window loading$")
    public void clickEndChatButtonWWL(String agent) {
        getAgentHomePage(agent).getChatHeader().clickEndChatButton();
    }

    @Then("^(?:End chat|Agent Feedback) popup for (.*) should be opened$")
    public void verifyAgentFeedbackPopupOpened(String agent) {
        Assert.assertTrue(getAgentHomePage(agent).getAgentFeedbackWindow().isEndChatPopupShown(),
                "End chat popup is not opened");
    }

    @When("^(.*) click 'Close chat' button$")
    public void clickCloseChatButton(String agent) {
        getAgentHomePage(agent).getAgentFeedbackWindow().clickCloseButtonInCloseChatPopup();
    }

    @When("^(.*) click happy sentiment button$")
    public void clickUnsatisfiedSentiment(String agent) {
        getAgentHomePage(agent).getAgentFeedbackWindow().setSentimentHappy();
    }

    @When("(.*) fills form$")
    public void agentFillsForm(String agent) {
        getAgentHomePage(agent).getAgentFeedbackWindow().typeCRMNoteTextField("Note text field");
        getAgentHomePage(agent).getAgentFeedbackWindow().typeCRMLink("http://NoteTextLink.com");
        getAgentHomePage(agent).getAgentFeedbackWindow().typeCRMTicketNumber("12345");
    }

    @When("^(.*) closes chat$")
    public void closeChat(String agent) {
        getAgentHomePage(agent).endChat();
    }

    @Then("^All session attributes are closed in DB$")
    public void verifySessionClosed() {
        SoftAssert soft = new SoftAssert();
        boolean result = waitForSessionToBeClosed(10);
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
    @Then("^Messages is correctly displayed and has correct color$")
    public void verifyMessages(){
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getChatBody("main agent").verifyAgentMessageColours(),
                "Agent messages' color is not as expected");
        soft.assertTrue(getChatBody("main agent").verifyMessagesPosition(),
                "Messages location is not as expected");
        soft.assertAll();
    }

    @Then("^(.*) user initials is shown$")
    public void verifyDefaultUserImage(String integration) {
        String userName = getUserName(integration);
        String initials = userName.substring(0,1);
        if(userName.contains(" ")){
            initials += userName.split(" ")[1].substring(0,1);
        }
        Assert.assertEquals(getChatBody("main agent").isValidDefaultUserProfileIcon(), initials.toUpperCase(),
                "Incorrect default user picture shown");
    }

    @Then("^Correct agent image is shown in conversation area$")
    public void verifyAgentImage() {
        Assert.assertTrue(getChatBody("main agent").isValidAgentAvatarIsShown(),
                "Incorrect agent picture shown");
    }

    @When("^(.*) open chat location form$")
    public void openLocationToUserAndSetSomeText(String agent) {
        locationWindow =  getAgentHomePage(agent).openLocationWindow();
    }

    @And("^Agent search for (.*) Location$")
    public void agentSearchForParisLocation(String locationName) {
        locationWindow.searchLocation(locationName);
    }

    @And("Agent click on reset button")
    public void agentClickOnResetButton() {
        locationWindow.clickCancelLocationButton();
    }

    @Then("Location field becomes empty")
    public void locationFieldBecomesEmpty() {
        Assert.assertFalse(locationWindow.checkSearchFieldisEmpty(), "Location Field in not empty");
    }

    @And("^Agent click on (.*) Location$")
    public void agentClickOnParisLocation(String locationName) {
        locationWindow.selectLocation(locationName);
        Assert.assertEquals(locationWindow.getTextFromSearch(),locationName, "Location did not match");
    }
}

