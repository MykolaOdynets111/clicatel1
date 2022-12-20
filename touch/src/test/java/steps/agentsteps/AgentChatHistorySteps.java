package steps.agentsteps;

import agentpages.uielements.ChatInActiveChatHistory;
import datamanager.jacksonschemas.chathistory.ChatHistory;
import driverfactory.DriverFactory;
import interfaces.JSHelper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class AgentChatHistorySteps extends AbstractAgentSteps implements JSHelper {

    private ChatHistory chatHistory;

    @Then("^(.*) sees correct chat with basic info in chat history container$")
    public void verifyChatHistoryItemInActiveChatView(String agent) {
        SoftAssert soft = new SoftAssert();
        String expectedChatHistoryTime = getExpectedTime(chatHistory.getChatStarted(),
                DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm"), false);
        ChatInActiveChatHistory actualChatHistoryItem = getAgentHomePage(agent).getChatHistoryContainer().getChatHistoryItemsByIndex(1);

        soft.assertEquals(actualChatHistoryItem.getChatHistoryTime().toLowerCase(), expectedChatHistoryTime.toLowerCase(),
                "Incorrect time for chat in chat history shown.");
        soft.assertEquals(actualChatHistoryItem.getChatHistoryUserMessage(),
                chatHistory.getMessages().get(chatHistory.getMessages().size() - 1).getMessage().getText(),
                "Incorrect message in chat history shown.");
        soft.assertAll();
    }

    @When("^(.*) click 'View chat' button$")
    public void clickViewChatButton(String agent) {
        getAgentHomePage(agent).getChatHistoryContainer().getChatHistoryItemsByIndex(1).clickViewButton();
    }

    @When("^(.*) open first 'History view'$")
    public void openFirstHistory(String agent) {
        getAgentHomePage(agent).getChatHistoryContainer().getChatHistoryItemsByIndex(0).clickViewButton();
    }

    @When("^(.*) sees (.*) location in history preview$")
    public void verifyLocationInHistory(String agent, String expectedLocation) {
        String locationInPreview = getAgentHomePage(agent).getChatHistoryContainer().getChatHistoryItemsByIndex(0).getChatHistoryUserMessage();
        Assert.assertTrue(locationInPreview.contains(expectedLocation), "Location is different: " + locationInPreview);
    }

    @Then("^(.*) sees correct location URL in History Details window$")
    public void compareLocationInHistoryDetailsWindows(String agent) {
        String locationInHistoryDetails = getAgentHomePage(agent).getHistoryDetailsWindow().getLocationURL();
        Assert.assertEquals(locationInHistoryDetails, AgentConversationSteps.getLocationURL(), "Location URLs aro different");
    }

    @Then("^(.*) sees the particular message (.*) in History Details window$")
    public void compareParticularMessageInHistoryDetailsWindows(String agent, String message) {
        String locationInHistoryDetails = getAgentHomePage(agent).getHistoryDetailsWindow().getText();
        Assert.assertTrue(locationInHistoryDetails.contains(message), "Particular message is not there in history window");
    }

    @Then("^(.*) sees correct messages in history details window$")
    public void verifyHistoryInOpenedWindow(String agent) {
        SoftAssert soft = new SoftAssert();
        String expectedChatHistoryTime = getExpectedTime(chatHistory.getMessages().get(0).getDateTime(), DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm"), false);
        List<String> messagesFromChatHistoryDetails = getAgentHomePage(agent).getHistoryDetailsWindow().getAllMessages();

        List<String> expectedChatHistory = getExpectedChatHistoryItems(chatHistory);

        soft.assertEquals(getAgentHomePage(agent).getHistoryDetailsWindow().getUserName(),
                getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()),
                "User name is not as expected in opened Chat ChatHistory Details window");
        soft.assertEquals(getAgentHomePage(agent).getHistoryDetailsWindow().getChatStartDate().toLowerCase(), expectedChatHistoryTime.toLowerCase(),
                "Chat started date is not as expected in opened Chat ChatHistory Details window");
        soft.assertEquals(messagesFromChatHistoryDetails, expectedChatHistory,
                "Chat history is not as expected in chat history details (in active chat)");
        soft.assertAll();
    }

    @When("^(.*) searches and selects chat from (.*) in chat history list$")
    public void selectRandomChatFromHistory(String ordinalAgentNumber, String channel) {
        getAgentHomePage(ordinalAgentNumber).waitForLoadingInLeftMenuToDisappear(3, 7);
        Assert.assertTrue(getLeftMenu(ordinalAgentNumber).searchUserChat(getUserName(channel)), "No chats visible in close chats");
    }

    @When("^(.*) sees correct chat history$")
    public void getChatHistoryFromBackend(String agent) {
        List<String> messagesFromChatBody = getChatBody(agent).getAllMessages();

        getAgentHomePage(agent).getChatHistoryContainer().getChatHistoryItemsByIndex(0).clickViewButton();
        List<String> expectedMessagesList = getChatBody(agent).getHistoryMessages();

        Assert.assertEquals(messagesFromChatBody, expectedMessagesList,
                "Shown on chatdesk messages are not as expected from API \n" +
                        "Expected list: " + expectedMessagesList + "\n" +
                        "Actual list: " + messagesFromChatBody);
    }

    @Then("^(.*) sees Rate Card in chat history with (.*) rate selected and (.*) comment$")
    public void verifyRateCardPresent(String agent, String rate, String comment) {
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getChatBody(agent).isRateCardShown(), "No Rate card was shown");
        soft.assertEquals(getChatBody(agent).getRate(), rate, "Selected rate not as expected");
        if (!comment.equalsIgnoreCase("no")) {
            soft.assertEquals(getChatBody(agent).getRateCardComment(), comment, "User comment not as expected");
        }
        soft.assertAll();
    }

    @Then("^(.*) does not see Rate Card in chat history$")
    public void verifyNoRateCardIsPresent(String agent) {
        Assert.assertFalse(getChatBody(agent).isRateCardShown(), "Unexpected Rate card was shown");
    }

    @Then("^(.*) sees stop message notification in chat history$")
    public void verifyIfStopNotificationPresent(String agent) {
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getChatBody(agent).isStopCardShown(), "Stop card is not shown");
        soft.assertEquals(getChatBody(agent).getStopCardText(), "Customer has opted-out of communication. You can no longer send messages.", "Stop card text is not correct");
        soft.assertAll();
    }

    @Then("^(.*) sees correct Location Url in closed chat body$")
    public void verifyLocationURLInClosedChat(String agent) {
        waitFor(3000);//wait till URL will be fully loaded
        String url = getAgentHomePage(agent).getChatBody().getLocationURLFromAgent();
        Assert.assertEquals(url, AgentConversationSteps.getLocationURL(), "Location URLs are different");
    }

    private List<String> getExpectedChatHistoryItems(ChatHistory chatHistory) {
        List<String> expectedMessagesList = new ArrayList<>();
        for (int i = 0; i < chatHistory.getMessages().size(); i++) {
            String message = chatHistory.getMessages().get(i).getMessage().getText();
            String chatTime = getExpectedTime(chatHistory.getMessages().get(i).getDateTime(), DateTimeFormatter.ofPattern("HH:mm"), false);
            String expectedChatItem = message + " " + chatTime;

            expectedMessagesList.add(i, expectedChatItem);
        }
        return expectedMessagesList;
    }

    private String getExpectedTime(String chatStarted, DateTimeFormatter formatter, boolean isDateSeparator) {
        ZoneId zoneId = TimeZone.getDefault().toZoneId();
        DateTimeFormatter frmatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");
        LocalDateTime chatTime = LocalDateTime.parse(chatStarted, frmatter);
        LocalDateTime currentDayTime = LocalDateTime.now(zoneId);

        if (isDateSeparator) {
            if (chatTime.getDayOfYear() == currentDayTime.getDayOfYear()) return "Today";
            if (chatTime.getDayOfYear() == (currentDayTime.minusDays(1).getDayOfYear())) return "Yesterday";
        }

        return chatTime.atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(zoneId).toLocalDateTime()
                .format(formatter);
    }
}
