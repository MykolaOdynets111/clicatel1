package steps.agentsteps;

import agentpages.uielements.ChatInActiveChatHistory;
import apihelper.ApiHelper;
import com.github.javafaker.Faker;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.Tenants;
import datamanager.jacksonschemas.chathistory.*;
import dbmanager.DBConnector;
import driverfactory.DriverFactory;
import drivermanager.ConfigManager;
import interfaces.JSHelper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AgentChatHistorySteps extends AbstractAgentSteps implements JSHelper {

    private Faker faker = new Faker();
    private ChatHistory chatHistory;
    private String userId;

    @Then("^(.*) sees correct chat with basic info in chat history container$")
    public void verifyChatHistoryItemInActiveChatView(String agent){
        SoftAssert soft = new SoftAssert();
        String expectedChatHistoryTime = getExpectedTime(chatHistory.getChatStarted(),
                DateTimeFormatter.ofPattern("d MMM yyyy h:mm a"), false);
        ChatInActiveChatHistory actualChatHistoryItem = getAgentHomePage(agent).getChatHistoryContainer().getSecondChatHistoryItems();


        soft.assertEquals(actualChatHistoryItem.getChatHistoryTime().toLowerCase(), expectedChatHistoryTime.toLowerCase(),
                "Incorrect time for chat in chat history shown.");
        soft.assertEquals(actualChatHistoryItem.getChatHistoryUserMessage(),
                chatHistory.getMessages().get(chatHistory.getMessages().size()-1).getMessage().getText(),
                "Incorrect message in chat history shown.");
        soft.assertAll();
    }

    @When("^(.*) click 'View chat' button$")
    public void clickViewChatButton(String agent){
        getAgentHomePage(agent).getChatHistoryContainer().getSecondChatHistoryItems().clickViewButton();
    }

    @Then("^(.*) sees correct messages in history details window$")
    public void verifyHistoryInOpenedWindow(String agent){
        SoftAssert soft = new SoftAssert();
        String expectedChatHistoryTime = getExpectedTime(chatHistory.getMessages().get(0).getDateTime(), DateTimeFormatter.ofPattern("d MMM yyyy, h:mm a"), false);
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

    @Given("Chat history of the client is available for the (.*) of (.*)")
    public void createHistory(String agent, String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        String previousChatId = "aqa_" + faker.lorem().characters(15) + System.currentTimeMillis();
        String sessionId = "aqa_" + faker.lorem().characters(15) + System.currentTimeMillis();
        String clientProfileId;
        ZoneId zoneId = TimeZone.getDefault().toZoneId();
        LocalDateTime chatStarted;
        LocalDateTime chatEnded;
        if(DriverFactory.isTouchDriverExists()){
            userId = getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance());
            clientProfileId = DBConnector.getClientProfileID(ConfigManager.getEnv(), userId, "TOUCH", 0);
            chatStarted = LocalDateTime.now(zoneId).minusDays(3).minusHours(4);
            chatEnded = LocalDateTime.now(zoneId).minusDays(3);
        }else{
            userId = "testing_" + faker.number().digits(7);
            clientProfileId = ApiHelper.createUserProfile(tenantOrgName, userId).jsonPath().getString("id");
            chatStarted = LocalDateTime.now(zoneId).minusMinutes(10);
            chatEnded = LocalDateTime.now(zoneId);
        }

        String tenantId = ApiHelper.getTenantInfoMap(tenantOrgName).get("id");
        String agentId = ApiHelper.getAgentInfo(tenantOrgName, agent).getBody().jsonPath().get("id");
        String channelId = ApiHelper.getChannelID(tenantOrgName, "webchat");



        Message userMessage = createMessageInHistory(previousChatId, tenantId, sessionId, chatStarted.atZone(zoneId).toInstant().toString(),
                                                    clientProfileId, "USER", channelId,
                                        "webchat", "user_message " + faker.book().title());
        Message agentMessage = createMessageInHistory(previousChatId, tenantId, sessionId, chatStarted.plusMinutes(5).atZone(zoneId).toInstant().toString(),
                agentId, "AGENT", channelId,
                "webchat", "agent_message " + faker.book().title());
        List<Message> messages = Arrays.asList(userMessage, agentMessage);

        chatHistory  = new ChatHistory(previousChatId, tenantId, clientProfileId, agentId, channelId,
                                                chatStarted.atZone(zoneId).toInstant().toString(),
                                                chatEnded.atZone(zoneId).toInstant().toString())
                               .setMessages(messages);

        Response resp = ApiHelper.createChatHistory(chatHistory);

        Assert.assertEquals(resp.statusCode(), 200, "History creation was not successful\n" +
                "response: " + resp.getBody().asString() + "\n" +
                "request body: " + chatHistory);
    }

    private Message createMessageInHistory(String chatId, String tenantId, String sessionId, String dateTime,
                                           String senderId, String senderType, String channelId, String channelType, String text){

        String messageId = "aqa_msg" + faker.lorem().characters(12) + System.currentTimeMillis();
        Message_ userMessage = new Message_(text)
                                        .setSender(new Sender(senderId, senderType))
                                        .setChannel(new Channel(channelId, channelType));
        return new Message("MESSAGE", messageId, chatId, tenantId, sessionId, "XMPP",dateTime).setMessage(userMessage);
    }


    @When("^(.*) searches and selects chat in chat history list$")
    public void selectRandomChatFromHistory(String ordinalAgentNumber){
        getAgentHomePage(ordinalAgentNumber).waitForLoadingInLeftMenuToDisappear(3,7);
        if (userId == null) userId = getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance());
        getLeftMenu(ordinalAgentNumber).searchUserChat(userId);
    }


    @When("Close chat to generate history record")
    public void closeChat() {
        ApiHelper.closeActiveChats("main");
    }

    @When("^(.*) sees correct chat history$")
    public void getChatHistoryFromBackend(String agent){
        List<String> messagesFromChatBody = getChatBody(agent).getAllMessages();
        List<String> expectedMessagesList = getExpectedChatHistoryItems(chatHistory);

        Assert.assertEquals(messagesFromChatBody, expectedMessagesList,
                "Shown on chatdesk messages are not as expected from API \n" +
                        "Expected list: " + expectedMessagesList + "\n" +
                        "Actual list: " + messagesFromChatBody);
    }

    @Then("^(.*) sees Rate Card in chat history with (.*) rate selected and (.*) comment$")
    public void verifyRateCardPresent(String agent, String rate, String comment){
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getChatBody(agent).isRateCardShown(), "No Rate card was shown");
        soft.assertEquals(getChatBody(agent).getRate(), rate, "Selected rate not as expected");
        if(!comment.equalsIgnoreCase("no")){
            soft.assertEquals(getChatBody(agent).getRateCardComment(), comment, "User comment not as expected");
        }
        soft.assertAll();
    }

    @Then("^(.*) does not see Rate Card in chat history$")
    public void verifyNoRateCardIsPresent(String agent){
        Assert.assertFalse(getChatBody(agent).isRateCardShown(), "Unexpected Rate card was shown");
    }

    private List<String> getExpectedChatHistoryItems(ChatHistory chatHistory){
        List<String> expectedMessagesList = new ArrayList<>();
        expectedMessagesList.add(0,
                getExpectedTime(chatHistory.getChatStarted(), DateTimeFormatter.ofPattern("EEEE, MM d, yyyy"), true));

        for(int i=0; i< chatHistory.getMessages().size(); i++){
            String message = chatHistory.getMessages().get(i).getMessage().getText();
            String chatTime = getExpectedTime(chatHistory.getMessages().get(i).getDateTime(), DateTimeFormatter.ofPattern("HH:mm"), false);
            String expectedChatItem = message + " " + chatTime;

            expectedMessagesList.add(i+1, expectedChatItem);
        }
        return expectedMessagesList;
    }

    private String getExpectedTime(String chatStarted, DateTimeFormatter formatter, boolean isDateSeparator){
        ZoneId zoneId =  TimeZone.getDefault().toZoneId();
        DateTimeFormatter frmatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");
        LocalDateTime chatTime = LocalDateTime.parse(chatStarted, frmatter);
        LocalDateTime currentDayTime = LocalDateTime.now(zoneId);

        if(isDateSeparator){
            if(chatTime.getDayOfYear() == currentDayTime.getDayOfYear()) return "Today";
            if(chatTime.getDayOfYear() == (currentDayTime.minusDays(1).getDayOfYear())) return "Yesterday";
        }

        return chatTime.atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(zoneId).toLocalDateTime()
                .format(formatter);
    }

}
