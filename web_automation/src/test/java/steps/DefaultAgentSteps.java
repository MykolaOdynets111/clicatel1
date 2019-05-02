package steps;

import agentpages.AgentHomePage;
import agentpages.AgentLoginPage;
import agentpages.uielements.ChatInActiveChatHistory;
import agentpages.uielements.LeftMenuWithChats;
import agentpages.uielements.ProfileWindow;
import apihelper.ApiHelper;
import apihelper.RequestSpec;
import com.github.javafaker.Faker;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.Customer360PersonalInfo;
import datamanager.FacebookUsers;
import datamanager.Tenants;
import datamanager.TwitterUsers;
import datamanager.jacksonschemas.CRMTicket;
import datamanager.jacksonschemas.ChatHistoryItem;
import dbmanager.DBConnector;
import drivermanager.ConfigManager;
import drivermanager.DriverFactory;
import interfaces.DateTimeHelper;
import interfaces.JSHelper;
import io.restassured.response.Response;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import steps.dotcontrol.DotControlSteps;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.Collections;

public class DefaultAgentSteps implements JSHelper, DateTimeHelper {
    private AgentHomePage agentHomePage;
    private AgentHomePage secondAgentHomePage;
    private ProfileWindow profileWindow;
    private LeftMenuWithChats leftMenuWithChats;
    private static ThreadLocal<Map<String, Boolean>> PRE_TEST_FEATURE_STATUS = new ThreadLocal<>();
    private static ThreadLocal<Map<String, Boolean>> TEST_FEATURE_STATUS_CHANGES = new ThreadLocal<>();
    private static Customer360PersonalInfo customer360InfoForUpdating;
    private Faker faker = new Faker();
    private List<ChatHistoryItem> chatHistoryItems;
    private Map selectedChatForHistoryTest;
    private static ThreadLocal<Map<String, String>> crmTicketInfoForCreatingViaAPI = new ThreadLocal<>();
    private static ThreadLocal<Map<String, String>> crmTicketInfoForUpdating = new ThreadLocal<>();
    private static ThreadLocal<CRMTicket> createdCrmTicket = new ThreadLocal<>();
    private static ThreadLocal<List<CRMTicket>> createdCrmTicketsList = new ThreadLocal<>();
    private String secondAgentName;

    public static List<CRMTicket> getCreatedCRMTicketsList(){
        return createdCrmTicketsList.get();
    }

    public static CRMTicket getCreatedCRMTicket(){
        return createdCrmTicket.get();
    }

    private static void savePreTestFeatureStatus(String featureName, boolean status){
        Map<String, Boolean> map = new HashMap<>();
        map.put(featureName, status);
        PRE_TEST_FEATURE_STATUS.set(map);
    }

    public static boolean getPreTestFeatureStatus(String featureName){
        return PRE_TEST_FEATURE_STATUS.get().get(featureName);
    }

    private static void saveTestFeatureStatusChanging(String featureName, boolean status){
        Map<String, Boolean> map = new HashMap<>();
        map.put(featureName, status);
        TEST_FEATURE_STATUS_CHANGES.set(map);
    }

    public static boolean getTestFeatureStatusChanging(String featureName){
        return TEST_FEATURE_STATUS_CHANGES.get().get(featureName);
    }

    @Given("^I login as (.*) of (.*)")
    public void loginAsAgentForTenant(String ordinalAgentNumber, String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        ApiHelper.closeAllOvernightTickets(Tenants.getTenantUnderTestOrgName());
        if(!ordinalAgentNumber.contains("second")) ApiHelper.logoutTheAgent(Tenants.getTenantUnderTestOrgName());
        AgentLoginPage.openAgentLoginPage(ordinalAgentNumber, tenantOrgName).loginAsAgentOf(tenantOrgName, ordinalAgentNumber);
        Assert.assertTrue(getAgentHomePage(ordinalAgentNumber).isAgentSuccessfullyLoggedIn(ordinalAgentNumber), "Agent is not logged in.");
    }

    @Given("^Try to login as (.*) of (.*)")
    public void tryToLoginAsAgentForTenant(String ordinalAgentNumber, String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
//        if(!ordinalAgentNumber.contains("second")) ApiHelper.logoutTheAgent(Tenants.getTenantUnderTestOrgName());
        AgentLoginPage.openAgentLoginPage(ordinalAgentNumber, tenantOrgName).loginAsAgentOf(tenantOrgName, ordinalAgentNumber);
    }


    @When("I login with the same credentials in another browser as an agent of (.*)")
    public void loginWithTheSameCreds(String tenantOrgName){
        AgentLoginPage.openAgentLoginPage("second agent", tenantOrgName).loginAsAgentOf(tenantOrgName, "main agent");
        Assert.assertTrue(getAgentHomePage("second agent").isAgentSuccessfullyLoggedIn("second agent"), "Agent is not logged in.");
    }

    @Then("^In the first browser Connection Error should be shown$")
    public void verifyAgentIsDisconnected(){
        Assert.assertTrue(getAgentHomePage("first agent").isConnectionErrorShown("first agent"),
                "Agent in the first browser is not disconnected");
    }

    @Given("^(.*) tenant feature is set to (.*) for (.*)$")
    public void setFeatureStatus(String feature, String status, String tenantOrgName){
        RequestSpec.clearAccessTokenForPortalUser();
        boolean featureStatus = ApiHelper.getFeatureStatus(tenantOrgName, feature);
        savePreTestFeatureStatus(feature, featureStatus);
        saveTestFeatureStatusChanging(feature, Boolean.parseBoolean(status.toLowerCase()));
        if(featureStatus!=Boolean.parseBoolean(status.toLowerCase())) {
            ApiHelper.updateFeatureStatus(tenantOrgName, feature, status);
        }
    }

    @Then("^Icon should contain (.*) agent's initials$")
    public void verifyUserInitials(String tenantOrgName){
        Response agentInfoResp = Tenants.getPrimaryAgentInfoForTenant(tenantOrgName);
        String expectedInitials = Character.toString(agentInfoResp.getBody().jsonPath().get("firstName").toString().charAt(0)) +
                Character.toString(agentInfoResp.getBody().jsonPath().get("lastName").toString().charAt(0));

        Assert.assertEquals(agentHomePage.getPageHeader().getTextFromIcon(), expectedInitials, "Agent initials is not as expected");
    }

    @When("^I click icon with initials$")
    public void clickIconWithInitials(){
        agentHomePage.getPageHeader().clickIconWithInitials();
    }

    @Then("^I see (.*) agent's info$")
    public void verifyAgentInfoInInfoPopup(String tenantOrgName){
        SoftAssert soft = new SoftAssert();
        Response agentInfoResp = Tenants.getPrimaryAgentInfoForTenant(tenantOrgName);
        String agentName = agentInfoResp.getBody().jsonPath().get("firstName") + " "
                + agentInfoResp.getBody().jsonPath().get("lastName");
        soft.assertEquals(agentHomePage.getPageHeader().getAgentName(), agentName,
                "Agent name is not as expected");
        soft.assertEquals(agentHomePage.getPageHeader().getAgentEmail(), agentInfoResp.getBody().jsonPath().get("email"),
                "Agent name is not as expected");
        soft.assertAll();
    }

    @When("^I click \"Profile Settings\" button$")
    public void clickProfileSettingsButton(){
        agentHomePage.getPageHeader().clickProfileSettingsButton();
    }


    @Then("^(.*) Agent's info derails is shown in profile window$")
    public void verifyAgentInfoInProfileWindow(String tenantOrgName){
        Response agentInfoResp = Tenants.getPrimaryAgentInfoForTenant(tenantOrgName);
        SoftAssert soft = new SoftAssert();
        List<String> expected=new ArrayList<>();
        agentInfoResp.getBody().jsonPath().getList("roles", Map.class).stream().map(e -> ((Map) e)).forEach(e -> {expected.add(
                e.get("name").toString().toUpperCase() + " ("+
                        e.get("solution").toString().toUpperCase() +        ")"
        );});
        soft.assertTrue(getProfileWindow("agent").isAgentInfoShown(agentInfoResp.getBody().jsonPath().get("firstName")),
                "Agent first name is not shown in profile window");
        soft.assertTrue(getProfileWindow("agent").isAgentInfoShown(agentInfoResp.getBody().jsonPath().get("lastName")),
                "Agent last name is not shown in profile window");
        soft.assertTrue(getProfileWindow("agent").isAgentInfoShown(agentInfoResp.getBody().jsonPath().get("email")),
                "Agent email is not shown in profile window");
        soft.assertEquals(getProfileWindow("agent").getListOfRoles(), expected,
                "Agent roles listed in Profile window are not as expected");
        soft.assertAll();
    }

    @Then("^(.*) is logged in chat desk$")
    public void verifyAgentLoggedIn(String ordinalAgentNumber){
        agentHomePage = new AgentHomePage(ordinalAgentNumber);
        Assert.assertTrue(agentHomePage.isAgentSuccessfullyLoggedIn(ordinalAgentNumber), "Agent is not logged in chat desk.");
    }

    @Then("^(.*) is not redirected to chatdesk$")
    public void verifyAgentNotLoggedIn(String ordinalAgentNumber){
        agentHomePage = new AgentHomePage(ordinalAgentNumber);
        Assert.assertFalse(agentHomePage.isAgentSuccessfullyLoggedIn(ordinalAgentNumber), "Agent is redirected to chat desk.");
    }

    @When("^Agent transfers chat$")
    public void transferChat(){
        getAgentHomeForMainAgent().getChatHeader().clickTransferButton();
        secondAgentName = getAgentHomeForMainAgent().getTransferChatWindow().transferChat();
    }

    @Then("(.*) receives incoming transfer with \"(.*)\" header")
    public void verifyIncomingTransferHeader(String agent, String expectedHeader){
        Assert.assertEquals(getAgentHomePage(agent).getIncomingTransferWindow().getTransferWindowHeader(agent),
                expectedHeader,
                "Header in incoming transfer window is not as expected");
    }

    @Then("^Correct Rejected by field is shown for (.*)$")
    public void verifyRejectedByField(String agent){
        Assert.assertEquals(getAgentHomePage(agent).getIncomingTransferWindow().getRejectedBy(agent),
                "Rejected by:\n" + secondAgentName,
                "Header in incoming transfer window is not as expected");
    }

    @Then("(.*) receives incoming transfer with \"(.*)\" note from the another agent")
    public void verifyIncomingTransferReceived(String agent, String notes){
        Assert.assertEquals(getAgentHomePage(agent).getIncomingTransferWindow().getTransferNotes(), notes,
                "Notes in incoming transfer window is not as added by the first agent");
    }

    @Then("(.*) can see transferring agent name, (?:user name|twitter user name|facebook user name) and following user's message: '(.*)'")
    public void verifyIncomingTransferDetails(String agent, String userMessage) {
        try {
            SoftAssert soft = new SoftAssert();
            String expectedUserName = "";
            if (ConfigManager.getSuite().equalsIgnoreCase("twitter")) {
                expectedUserName = TwitterUsers.getLoggedInUserName();
                userMessage = TwitterSteps.getCurrentConnectToAgentTweetText();
            }
            if(ConfigManager.getSuite().equalsIgnoreCase("facebook")) {
                    expectedUserName = FacebookUsers.getLoggedInUserName();
                    userMessage = FacebookSteps.getCurrentUserMessageText();
            }
            if (!ConfigManager.getSuite().equalsIgnoreCase("facebook") &&
                    !ConfigManager.getSuite().equalsIgnoreCase("twitter")){
                expectedUserName = getUserNameFromLocalStorage();
            }
            Response agentInfoResp = Tenants.getPrimaryAgentInfoForTenant(Tenants.getTenantUnderTestOrgName());
            String expectedAgentNAme = agentInfoResp.getBody().jsonPath().get("firstName") + " " +
                    agentInfoResp.getBody().jsonPath().get("lastName");

            soft.assertEquals(getAgentHomePage(agent).getIncomingTransferWindow().getClientName(), expectedUserName,
                    "User name in Incoming transfer window is not as expected");
            soft.assertEquals(getAgentHomePage(agent).getIncomingTransferWindow().getClientMessage(), userMessage,
                    "User message in Incoming transfer window is not as expected");
            soft.assertEquals(getAgentHomePage(agent).getIncomingTransferWindow().getFromAgentName(), expectedAgentNAme,
                    "Transferring agent name in Incoming transfer window is not as expected");
            soft.assertAll();
        } catch (NoSuchElementException e){
            Assert.assertTrue(false,
                    "Incoming transfer window is not shown.\n Please see the screenshot");
        }
    }

    @Then("^Second agent click \"Accept transfer\" button$")
    public void acceptIncomingTransfer(){
        getAgentHomeForSecondAgent().getIncomingTransferWindow().acceptTransfer();
    }

    @Then("^Second agent click \"Reject transfer\" button$")
    public void rejectIncomingTransfer(){
        getAgentHomeForSecondAgent().getIncomingTransferWindow().rejectTransfer();
    }

    @Then("^(.*) click \"Accept\" button$")
    public void acceptRejectedTransfer(String agent){
        getAgentHomePage(agent).getIncomingTransferWindow().acceptRejectTransfer(agent);
    }

    @Then("^(.*) has new conversation request$")
    public void verifyIfAgentReceivesConversationRequest(String agent) {
        Assert.assertTrue(getLeftMenu(agent).isNewConversationRequestIsShown(20, agent),
                "There is no new conversation request on Agent Desk (Client ID: "+getUserNameFromLocalStorage()+")\n" +
                        "Number of logged in agents: " + ApiHelper.getNumberOfLoggedInAgents() +"\n");
    }

    @When("^(.*) click 'Pin' button$")
    public void pinChat(String agent){
        getAgentHomePage(agent).getChatHeader().clickPinButton(agent);
    }


    @When("^(.*) click 'Unpin' button$")
    public void unpinChat(String agent) {
        getAgentHomePage(agent).getChatHeader().clickUnpinButton(agent);
    }

    @Then("^(.*) can not click 'Transfer chat' button$")
    public void agentCanNotClickTransferChatButton(String agent) {
        Assert.assertFalse(getAgentHomePage(agent).getChatHeader().isTransferButtonEnabled(),
                "Transfer chat button is enabled ");
    }


    @Then("^(.*) has new conversation request within (.*) seconds$")
    public void verifyIfAgentReceivesConversationRequest(String agent, int timeout) {
        Assert.assertTrue(getLeftMenu(agent).isNewConversationRequestIsShown(timeout, agent),
                "There is no new conversation request on Agent Desk (Client ID: "+getUserNameFromLocalStorage()+")\n" +
                        "Number of logged in agents: " + ApiHelper.getNumberOfLoggedInAgents() +"\n");
    }

    @Then("(.*) sees 'overnight' icon in this chat")
    public void verifyOvernightIconShown(String agent){
        Assert.assertTrue(getLeftMenu(agent).isOvernightTicketIconShown(getUserNameFromLocalStorage()),
                "Overnight icon is not shown for overnight ticket. \n clientId: "+ getUserNameFromLocalStorage());
    }

    @Then("^Overnight ticket is removed from (.*) chatdesk$")
    public void checkThatOvernightTicketIsRemoved(String agent){
        Assert.assertTrue(getLeftMenu(agent).isOvernightTicketIconRemoved(getUserNameFromLocalStorage()),
                "Overnight ticket is still shown");
    }

    @Then("Message that it is overnight ticket is shown for (.*)")
    public void verifyMessageThatThisIsOvernightTicket(String agent){
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(getAgentHomePage(agent).isOvernightTicketMessageShown(),
                "Message that this is Overnight ticket is not shown");
        softAssert.assertTrue(getAgentHomePage(agent).isSendEmailForOvernightTicketMessageShown(),
                "'Send email' button is not enabled");
        softAssert.assertAll();
    }

    @Then("Conversation area contains (.*) to user message")
    public void verifyOutOfSupportMessageShownOnChatdesk(String message){
        String userMessage = message;
        if(message.contains("out_of_support_hours")) {
            userMessage = ApiHelper.getTafMessages().stream().filter(e -> e.getId().equals(message)).findFirst().get().getText();
        }
        Assert.assertTrue(getAgentHomePage("main agent").getChatBody().isToUserMessageShown(userMessage),
                "Expected to user message '"+userMessage+"' is not shown in chatdesk");
    }

    @Then("^Agent select \"(.*)\" filter option$")
    public void selectFilterOption(String option){
        getLeftMenu("main agent").selectFilterOption(option);
    }

    @Then("^Agent see \"(.*)\" filter options$")
    public void seeFilterOptions(List<String> option){
        List<String> displayedFilterOptions = getLeftMenu("main agent").getFilterOption();
        Collections.sort(option);
        Collections.sort(displayedFilterOptions);
        Assert.assertEquals(displayedFilterOptions , option, "Wrong filter options \n ");
    }

    @Then("^(.*) has new conversation request from (.*) user$")
    public void verifyAgentHasRequestFormSocialUser(String agent, String social){
                leftMenuWithChats = getLeftMenu(agent);
                String userName=null;
                if (social.equalsIgnoreCase("twitter")) userName = TwitterUsers.getLoggedInUserName();
                if(social.equalsIgnoreCase("facebook")) userName = FacebookUsers.getLoggedInUserName();
                if(social.equalsIgnoreCase("dotcontrol")) {
                    userName = DotControlSteps.getFromClientRequestMessage().getClientId();
                    Assert.assertTrue(leftMenuWithChats.isNewConversationRequestFromSocialIsShown(userName,15, "main"),
                            "There is no new conversation request on Agent Desk from .Control\n (Client ID: "+
                                    DotControlSteps.getFromClientRequestMessage().getClientId()+")");
                    return;
                }
                Assert.assertTrue(leftMenuWithChats.isNewConversationRequestFromSocialIsShown(userName,20, agent),
                                "There is no new conversation request on Agent Desk (Client name: "+userName+")");
            }

    private boolean waitForDotControlRequestOnChatDesk(){
        for(int i = 0; i<5; i++) {
            String userName = DotControlSteps.getFromClientRequestMessage().getClientId();
            if(leftMenuWithChats.isNewConversationRequestFromSocialIsShown(userName,4, "main")) return true;
            else {
                DriverFactory.getAgentDriverInstance().navigate().refresh();
            }
        }
        return false;
    }

    @Then("^(.*) has new conversation request from (.*) user through (.*) channel$")
    public void verifyAgentHasRequestFormSocialUser(String agent, String social, String channel){
        agentHomePage = getAgentHomePage(agent);
        leftMenuWithChats = agentHomePage.getLeftMenuWithChats();
        String userName=null;
        if (social.equalsIgnoreCase("twitter")) userName = TwitterUsers.getLoggedInUserName();
        if(social.equalsIgnoreCase("facebook")) userName = FacebookUsers.getLoggedInUserName();
        Assert.assertTrue(leftMenuWithChats.isNewConversationRequestFromSocialShownByChannel(userName, channel,20),
                "There is no new conversation request on Agent Desk (Client ID: "+getUserNameFromLocalStorage()+")");
    }


    @Then("^(.*) should not see from user chat in agent desk$")
    public void verifyConversationRemovedFromChatDesk(String agent){
        // ToDo: Update after clarifying timeout in System timeouts
        Assert.assertTrue(getLeftMenu(agent).isConversationRequestIsRemoved(13),
                "Conversation request is not removed from Agent Desk (Client ID: "+getUserNameFromLocalStorage()+")"
        );
    }

    @Then("^(.*) sees 'flag' icon in this chat$")
    public void verifyFlagAppearance(String agent){
        Assert.assertTrue(getLeftMenu(agent).isFlagIconShown(getUserNameFromLocalStorage()),
                "Flag icon is not shown");
    }

    @Then("^(.*) do not see 'flag' icon in this chat$")
    public void verifyFlagNotAppearance(String agent){
        Assert.assertTrue(getLeftMenu(agent).isFlagIconRemoved(getUserNameFromLocalStorage()),
                "Flag icon is shown");
    }

    @When("^(.*) click on new conversation request from (.*)$")
    public void acceptUserFromSocialConversation(String agent, String socialChannel) {
        String userName=null;
        switch (socialChannel.toLowerCase()){
            case "touch":
                userName = getUserNameFromLocalStorage();
                break;
            case "twitter":
                userName = TwitterUsers.getLoggedInUserName();
                break;
            case "facebook":
                userName = FacebookUsers.getLoggedInUserName();
                break;
            case "dotcontrol":
               userName = DotControlSteps.getFromClientRequestMessage().getClientId();

        }
        getLeftMenu(agent).openNewFromSocialConversationRequest(userName);
    }


    @When("^(.*) click on new conversation$")
    public void acceptUserConversation(String ordinalAgentNumber) {
        getLeftMenu(ordinalAgentNumber).openNewConversationRequestByAgent(ordinalAgentNumber);
    }

    @When("^(.*) changes status to: (.*)$")
    public void changeAgentStatus(String agent, String newStatus){
        try {
            getAgentHomePage(agent).getPageHeader().clickIconWithInitials();
            getAgentHomePage(agent).getPageHeader().selectStatus(newStatus);
            getAgentHomePage(agent).getPageHeader().clickIconWithInitials();
        } catch (WebDriverException e) {
            Assert.assertTrue(false, "Unable to change agent status. Please check the screenshot.");
        }
    }

    @When("^(.*) refreshes the page$")
    public void refreshThePage(String agent){
        DriverFactory.getDriverForAgent(agent).navigate().refresh();
    }

    @When("^Agent limit reached popup is show for (.*)$")
    public void verifyAgentLimitPopup(String ordinalAgentNumber){
        Assert.assertTrue(getAgentHomePage(ordinalAgentNumber).isAgentLimitReachedPopupShown(12),
                "'Agent limit reached' pop up is not shown.");
    }

    @Given("^Set agent support hours (.*)$")
    public void setSupportHoursWithShift(String shiftStrategy){
        switch (shiftStrategy){
            case "with day shift":
                LocalDateTime currentTimeWithADayShift = LocalDateTime.now().minusDays(1);

                ApiHelper.setAgentSupportDaysAndHours(Tenants.getTenantUnderTestOrgName(), currentTimeWithADayShift.getDayOfWeek().toString(),
                        "00:00", "23:59");
                break;
            case "for all week":
                ApiHelper.setAgentSupportDaysAndHours(Tenants.getTenantUnderTestOrgName(), "all week",
                        "00:00", "23:59");
                getAgentHomePage("main").waitFor(1500);
                break;
        }
    }


    @Then("Correct (.*) client details are shown")
    public void verifyClientDetails(String clientFrom){
        Customer360PersonalInfo customer360PersonalInfoFromChatdesk = getAgentHomePage("main").getCustomer360Container().getActualPersonalInfo();

        Assert.assertEquals(customer360PersonalInfoFromChatdesk, getCustomer360Info(clientFrom),
                "User info is not as expected \n");
    }



    @When("Click (?:'Edit'|'Save') button in Customer 360 view")
    public void clickEditCustomerView(){
        getAgentHomePage("main").getCustomer360Container().clickSaveEditButton();
    }

    @When("Fill in the form with new (.*) customer 360 info")
    public void updateCustomer360Info(String customerFrom){
        Customer360PersonalInfo currentCustomerInfo = getCustomer360Info(customerFrom);
        customer360InfoForUpdating = currentCustomerInfo.setLocation("Lviv")
                            .setEmail("udated_" + faker.lorem().word()+"@gmail.com")
                            .setPhone("+380931576633");
        if(!(customerFrom.contains("fb")||customerFrom.contains("twitter"))) {
            customer360InfoForUpdating.setFullName("AQA Run");
            customer360InfoForUpdating.setChannelUsername(customer360InfoForUpdating.getFullName());

        }
        getAgentHomePage("main").getCustomer360Container().fillFormWithNewDetails(customer360InfoForUpdating);

    }

    @Then("^(.*) customer info is updated on backend$")
    public void verifyCustomerInfoChangesOnBackend(String customerFrom){
        Assert.assertEquals(getCustomer360Info(customerFrom), customer360InfoForUpdating,
                "Customer 360 info is not updated on backend after making changes on chatdesk. \n");
    }

    @Then("^New info is shown in left menu with chats$")
    public void checkUpdatingUserInfoInLeftMenu(){
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getLeftMenu("main").getActiveChatUserName(), customer360InfoForUpdating.getFullName(),
                "Full user name is not updated in left menu with chats after updating Customer 360 info \n");
        soft.assertEquals(getLeftMenu("main").getActiveChatLocation(), customer360InfoForUpdating.getLocation(),
                "Location is not updated in left menu with chats after updating Customer 360 info \n");
        soft.assertAll();
    }

    @Then("^Customer name is updated in active chat header$")
    public void verifyCustomerNameUpdated(){
        Assert.assertTrue(getAgentHomeForMainAgent().getChatHeader().getChatHeaderText().contains(customer360InfoForUpdating.getFullName()),
                "Updated customer name is not shown in chat header");

    }

    @Then("^Agent photo is updated on chatdesk$")
    public void verifyPhotoLoadedOnChatdesk(){
        Assert.assertTrue(getAgentHomePage("main").getPageHeader().isAgentImageShown(),
                "Agent image is not shown on chatdesk");
    }

    @When("^(.*) searches and selects random chat is chat history list$")
    public void selectRandomChatFromHistory(String ordinalAgentNumber){
        getLeftMenu(ordinalAgentNumber).selectRandomChat(ordinalAgentNumber);
    }

    @When("^Correct chat history is shown$")
    public void getChatHistoryFromBackend(){
        String clientID = agentHomePage.getCustomer360Container().getUserFullName();
        Response sessionDetails  = ApiHelper.getSessionDetails(clientID);
        List<String> sessionIds = sessionDetails.getBody().jsonPath().getList("data.sessionId");
        List<ChatHistoryItem> chatItems = ApiHelper.getChatHistory(Tenants.getTenantUnderTestOrgName(), sessionIds.get(0));
        List<String> messagesFromChatBody = agentHomePage.getChatBody().getAllMessages();
        List<String> expectedMessagesList = getExpectedChatHistoryItems(TimeZone.getDefault().toZoneId(), chatItems);

        Assert.assertEquals(messagesFromChatBody, expectedMessagesList,
                "Shown on chatdesk messages are not as expected from API \n" +
                "Expected list: " + expectedMessagesList + "\n" +
                "Actual list: " + messagesFromChatBody);
    }

    private List<String> getExpectedChatHistoryItems(ZoneId zoneId, List<ChatHistoryItem> items){
        List<String> expectedMessagesList = new ArrayList<>();
        expectedMessagesList.add(0, formDaySeparator(items.get(0).getMessageTime(), zoneId));
        for(int i=0; i<items.size(); i++){
            String expectedChatItem = formExpectedChatItem(items.get(i), zoneId).replace("\n", " ");
            if(expectedChatItem.contains("Input card")) expectedChatItem =
                    expectedChatItem.replace("Input card", "Let me connect you with a live agent to assist you further. Before I transfer you, please give us some basic info:");
            expectedMessagesList.add(i+1, expectedChatItem);
        }
        return expectedMessagesList;
    }

    private String formDaySeparator(long time, ZoneId zoneId){
        LocalDateTime itemDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), zoneId);
        LocalDateTime currentDayTime = LocalDateTime.now(zoneId);
        String timeSeparator = formTimeStringFromMillis(time, zoneId, DateTimeFormatter.ofPattern("EEEE, MM d, yyyy"));

        if(itemDateTime.getDayOfYear() == currentDayTime.getDayOfYear()) timeSeparator="Today";
        if(itemDateTime.getDayOfYear() == (currentDayTime.minusDays(1).getDayOfYear())) timeSeparator="Yesterday";

        return timeSeparator;
    }

    private String formExpectedChatItem(ChatHistoryItem item, ZoneId zoneId){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String message = item.getDisplayMessage();
        long time = item.getMessageTime();
        String chatTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), zoneId)
                .format(formatter);
        return message + " " + chatTime;
    }

    @Then("^(.*) sees correct chat with basic info in chat history container$")
    public void verifyChatHistoryItemInActiveChatView(String agent){
        SoftAssert soft = new SoftAssert();
        selectedChatForHistoryTest = DefaultTouchUserSteps.getSelectedClientForChatHistoryTest();
        String expectedChatHistoryTime = getExpectedChatStartTimeForChatHistoryInActiveChat();
        ChatInActiveChatHistory actualChatHistoryItem = getAgentHomePage(agent).getChatHistoryContainer().getFirstChatHistoryItems();
        chatHistoryItems = ApiHelper.getChatHistory(Tenants.getTenantUnderTestOrgName(),
                (String) selectedChatForHistoryTest.get("sessionId"));

        soft.assertEquals(actualChatHistoryItem.getChatHistoryTime(), expectedChatHistoryTime,
                "Incorrect time for chat in chat history shown.");
        soft.assertTrue(actualChatHistoryItem.isViewButtonClickable(agent),
                "'View chat' button is not enabled.");
        soft.assertAll();
    }

    @When("^(.*) click 'View chat' button$")
    public void clickViewChatButton(String agent){
        getAgentHomePage(agent).getChatHistoryContainer().getFirstChatHistoryItems().clickViewButton();
    }

    @Then("^Correct messages is shown in history details window$")
    public void verifyHistoryInOpenedWindow(){
        SoftAssert soft = new SoftAssert();
        String expectedChatHistoryTime = getExpectedChatStartTimeForChatHistoryInActiveChat();
        List<String> messagesFromChatHistoryDetails = agentHomePage.getHistoryDetailsWindow().getAllMessages();
        List<String> expectedChatHistory = getExpectedChatHistoryItems(TimeZone.getDefault().toZoneId(), chatHistoryItems);

        soft.assertEquals(agentHomePage.getHistoryDetailsWindow().getUserName(), selectedChatForHistoryTest.get("clientId"),
                "User name is not as expected in opened Chat History Details window");
        soft.assertEquals(agentHomePage.getHistoryDetailsWindow().getChatStartDate(), expectedChatHistoryTime,
                "Started date is not as expected in opened Chat History Details window");
        soft.assertEquals(messagesFromChatHistoryDetails, expectedChatHistory,
                "Chat history is not as expected in chat history details (in active chat)");
        soft.assertAll();
    }

    @Given("CRM ticket (.*) is created")
    public void createCRMTicketViaAPI(String urlStatus){
        Map<String, String>  sessionDetails = DBConnector.getActiveSessionDetailsByClientProfileID
                                                                (ConfigManager.getEnv(), getUserNameFromLocalStorage());
        Map<String, String> dataForNewCRMTicket = prepareDataForCrmTicket(sessionDetails, urlStatus);

        Response resp = ApiHelper.createCRMTicket(getUserNameFromLocalStorage(), dataForNewCRMTicket);
        createdCrmTicket.set(resp.getBody().as(CRMTicket.class));
        Assert.assertTrue(resp.statusCode()==200, "Creating CRM ticket via API was not successful\n" +
                                resp.statusCode() + "\n" +
                                "rest body: " +resp.getBody().asString());
    }

    @Given("(.*) CRM tickets are created")
    public void createCRMTicketsViaAPI(int ticketsNumber){
        Map<String, String>  sessionDetails = DBConnector.getActiveSessionDetailsByClientProfileID
                (ConfigManager.getEnv(), getUserNameFromLocalStorage());
        List<CRMTicket> createdCRMTicket = new ArrayList<>();
        for(int i = 0; i < ticketsNumber; i++) {
            Map<String, String> dataForNewCRMTicket = prepareDataForCrmTicket(sessionDetails, "with url");

            Response resp = ApiHelper.createCRMTicket(getUserNameFromLocalStorage(), dataForNewCRMTicket);
            createdCRMTicket.add(resp.getBody().as(CRMTicket.class));
            getAgentHomeForMainAgent().waitFor(400);
        }
        createdCrmTicketsList.set(createdCRMTicket);
        Assert.assertEquals(ApiHelper.getCRMTickets(getUserNameFromLocalStorage(), "TOUCH").size(), ticketsNumber,
                "Not all tickets where successfully created");
    }

    private Map<String, String> prepareDataForCrmTicket( Map<String, String>  sessionDetails, String urlStatus){
        Map<String, String> dataForNewCRMTicket = new HashMap<>();
        dataForNewCRMTicket.put("clientProfileId", sessionDetails.get("clientProfileId"));
        dataForNewCRMTicket.put("conversationId", sessionDetails.get("conversationId"));
        dataForNewCRMTicket.put("sessionId", sessionDetails.get("sessionId"));
        dataForNewCRMTicket.put("link", "about:blank");
        dataForNewCRMTicket.put("ticketNumber", faker.number().digits(5));
        dataForNewCRMTicket.put("agentNote", "AQA_note " + faker.book().title());
        crmTicketInfoForCreatingViaAPI.set(dataForNewCRMTicket);
        if(urlStatus.toLowerCase().contains("without url")) dataForNewCRMTicket.remove("link");
        return dataForNewCRMTicket;
    }

    private Map<String, String> prepareDataForCrmTicketChatdesk( String agentNote, String link, String ticketNumber, List<String> tags){
        Map<String, String>  sessionDetails = DBConnector.getActiveSessionDetailsByClientProfileID
                (ConfigManager.getEnv(), getUserNameFromLocalStorage());
        Map<String, String> dataForNewCRMTicket = new HashMap<>();
        dataForNewCRMTicket.put("clientProfileId", sessionDetails.get("clientProfileId"));
        dataForNewCRMTicket.put("conversationId", sessionDetails.get("conversationId"));
        dataForNewCRMTicket.put("sessionId", sessionDetails.get("sessionId"));
        dataForNewCRMTicket.put("agentNote", agentNote);
        dataForNewCRMTicket.put("agentTags", String.join(", ", tags));
        dataForNewCRMTicket.put("link", link);
        dataForNewCRMTicket.put("ticketNumber", ticketNumber);
        dataForNewCRMTicket.put("date", LocalDateTime.now().toString());
        crmTicketInfoForUpdating.set(dataForNewCRMTicket);
        return dataForNewCRMTicket;
    }

    @Then("Container with new CRM (?:ticket|tickets) is shown")
    public void verifyCRMTicketIsShown(){
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getAgentHomeForMainAgent().getCrmTicketContainer().getContainerHeader(), "Notes",
                "CRM tickets section header is not 'Notes'");
        soft.assertTrue(getAgentHomeForMainAgent().getCrmTicketContainer().isTicketContainerShown(),
                "CRM ticket is not shown");
        soft.assertAll();
    }

    @Then("^Agent sees (.*) CRM tickets$")
    public void verifyTicketsNumber(int expectedNumber){
        Assert.assertEquals(getAgentHomeForMainAgent().getCrmTicketContainer().getNumberOfTickets(), expectedNumber,
                "Shown tickets number is not as expected");
    }

    @Then("^Tickets number is reduced to (.*)$")
    public void verifyTicketsNumberReduced(int expectedNumber){
        boolean isReduced = false;
        for(int i =0; i < 6; i++){
            if(getAgentHomeForMainAgent().getCrmTicketContainer().getNumberOfTickets() == expectedNumber) {
                isReduced = true;
                break;
            }
            else getAgentHomeForMainAgent().waitFor(200);
        }
        Assert.assertTrue(isReduced, "Shown tickets number is not as expected");
    }


    @Then("Tickets are correctly sorted")
    public void verifyTicketsSorting(){
        List<Map<String, String>> actualTickets = getAgentHomeForMainAgent().getCrmTicketContainer().getAllTicketsInfoExceptDate();
        List<CRMTicket> createdTickets = getCreatedCRMTicketsList();

        Collections.reverse(createdTickets);
        List<Map<String, String>> expectedTickets = new ArrayList<>();

        for (CRMTicket ticket : createdTickets){
            expectedTickets.add(new HashMap<String, String>(){{
                put("note", "Note: " + ticket.getAgentNote());
                put("number", "Ticket Number: " + ticket.getTicketNumber());
            }});
        }
        Assert.assertEquals(actualTickets, expectedTickets,
                "Tickets order is not as Expected\n" +
        "Created tickets: " + createdTickets.toString() + "\n\n" +
        "Expected list" + expectedTickets        );
    }

    @Then("New CRM ticket is not shown")
    public void verifyCRMTicketIsNotShown(){
        Assert.assertTrue(getAgentHomeForMainAgent().getCrmTicketContainer().isTicketContainerRemoved(),
                "CRM ticket is still shown");
    }

    @When("^I click CRM ticket number URL$")
    public void clickTicketNumber(){
        getAgentHomeForMainAgent().getCrmTicketContainer().getFirstTicket().clickTicketNumber();

        String currentWindow = DriverFactory.getDriverForAgent("main").getWindowHandle();

            for (String winHandle : DriverFactory.getDriverForAgent("main").getWindowHandles()) {
                if (!winHandle.equals(currentWindow)) {
                    DriverFactory.getDriverForAgent("main").switchTo().window(winHandle);
                }
            }
    }

    @When("^(.*) click 'Edit' button for CRM ticket$")
    public void clickEditCRMTicketButton(String agent){
        getAgentHomePage(agent).getCrmTicketContainer().getFirstTicket().clickEditButton();
    }

    @When("^(.*) deletes first ticket$")
    public void deleteFirstTicket(String agent){
        getAgentHomePage(agent).getCrmTicketContainer().getFirstTicket().clickDeleteButton();
        getAgentHomePage(agent).getDeleteCRMConfirmationPopup().clickDelete();
    }

    @When("^(.*) click 'Delete' button for CRM ticket$")
    public void clickDeleteCRMTicketButton(String agent){
        getAgentHomePage(agent).getCrmTicketContainer().getFirstTicket().clickDeleteButton();
    }


    @Then("^Confirmation deleting popup is shown$")
    public void verifyDeletingCRMTicketPopupShown(){
        Assert.assertTrue(getAgentHomeForMainAgent().getDeleteCRMConfirmationPopup().isOpened(),
                "CRM ticket deleting confirmation popup is not shown");
    }

    @When("^(.*) click 'Cancel' button' in CRM deleting popup$")
    public void cancelCRMDeleting(String agent){
        getAgentHomePage(agent).getDeleteCRMConfirmationPopup().clickCancel();
    }

    @When("^(.*) click 'Delete' button' in CRM deleting popup$")
    public void deleteCRMTicket(String agent){
        getAgentHomePage(agent).getDeleteCRMConfirmationPopup().clickDelete();
    }


    @Then("^'Edit ticket' window is opened$")
    public void verifyEditWindowOpened(){
        Assert.assertTrue(getAgentHomeForMainAgent().getEditCRMTicketWindow().isOpened(),
                "'Edit ticket' window is not opened after clicking 'Edit' button for CRM ticket");
    }

    @When("^(.*) fill in the form with new CRM ticket info$")
    public void fillCRMEditingFormWithNewData(String agent){
        formDataForCRMUpdating();
        getAgentHomePage(agent).getEditCRMTicketWindow().provideCRMNewTicketInfo(crmTicketInfoForUpdating.get());
    }

    @When("^Cancel CRM editing$")
    public void cancelCRMEditing(){
        getAgentHomeForMainAgent().getEditCRMTicketWindow().clickCancel();
    }

    @When("^Save CRM ticket changings$")
    public void saveCRMEditing(){
        getAgentHomeForMainAgent().getEditCRMTicketWindow().saveChanges();
    }

    @Then("CRM ticket is not updated on back end")
    public void verifyCRMTicketNotUpdated() {
        SoftAssert soft = new SoftAssert();
        CRMTicket actualTicketInfoFromBackend = ApiHelper.getCRMTickets(getUserNameFromLocalStorage(), "TOUCH").get(0);

        soft.assertEquals(actualTicketInfoFromBackend.getCreatedDate().split(":")[0],
                                createdCrmTicket.get().getCreatedDate().split(":")[0],
                        "Ticket created date is changed after canceling ticket editing \n");
        soft.assertEquals(actualTicketInfoFromBackend.getTicketNumber(), createdCrmTicket.get().getTicketNumber(),
                "Ticket Number is changed after canceling ticket editing \n");
        soft.assertEquals(actualTicketInfoFromBackend.getAgentNote(), createdCrmTicket.get().getAgentNote(),
                " Ticket note is changed after canceling ticket editing \n");
        soft.assertEquals(actualTicketInfoFromBackend.getLink(), createdCrmTicket.get().getLink(),
                " Ticket link is changed after canceling ticket editing \n");
        soft.assertAll();
    }

    @Then("CRM ticket is updated on back end")
    public void verifyCRMTicketUpdated() {
        SoftAssert soft = new SoftAssert();
        CRMTicket actualTicketInfoFromBackend = ApiHelper.getCRMTickets(getUserNameFromLocalStorage(), "TOUCH").get(0);
        String createdDate = getAgentHomeForMainAgent().getCrmTicketContainer().getFirstTicket().getCreatedDate();
        String createdDateFromBackend = "Created: " + formExpectedCRMTicketCreatedDate(actualTicketInfoFromBackend.getCreatedDate());


        soft.assertEquals(createdDateFromBackend.toLowerCase(), createdDate.toLowerCase(),
                "Ticket created date is changed after ticket editing \n");
        soft.assertEquals(actualTicketInfoFromBackend.getTicketNumber(), crmTicketInfoForUpdating.get().get("ticketNumber"),
                "Ticket Number is not changed after canceling ticket editing \n");
        soft.assertEquals(actualTicketInfoFromBackend.getAgentNote(),  crmTicketInfoForUpdating.get().get("agentNote"),
                " Ticket note is changed after canceling ticket editing \n");
        soft.assertEquals(actualTicketInfoFromBackend.getLink(), crmTicketInfoForUpdating.get().get("link"),
                " Ticket link is changed after canceling ticket editing \n");
        soft.assertAll();
    }

    @Then("^(.*) is redirected by CRM ticket URL$")
    public void verifyUserRedirectedBlankPage(String agent){
        String pageUrl = DriverFactory.getDriverForAgent(agent).getCurrentUrl();
        String currentWindow = DriverFactory.getDriverForAgent("main").getWindowHandle();
        for (String winHandle : DriverFactory.getDriverForAgent("main").getWindowHandles()) {
                if (!winHandle.equals(currentWindow)) {
                    DriverFactory.getDriverForAgent("main").switchTo().window(winHandle);
                }
        }
        Assert.assertEquals(pageUrl, "about:blank",
                "Agent is not redirected by CRM tickets' url");
    }

    @Then("^(.*) is redirected to empty chatdesk page$")
    public void verifyUserRedirectedEmptyChatdeskPage(String agent){
        String pageUrl = DriverFactory.getDriverForAgent(agent).getCurrentUrl();
        String currentWindow = DriverFactory.getDriverForAgent("main").getWindowHandle();
        for (String winHandle : DriverFactory.getDriverForAgent("main").getWindowHandles()) {
            if (!winHandle.equals(currentWindow)) {
                DriverFactory.getDriverForAgent("main").switchTo().window(winHandle);
            }
        }
        Assert.assertTrue(pageUrl.contains("null")&pageUrl.contains("chatdesk"),
                "Agent is not redirected by CRM tickets' url");
    }

    @Then("Correct ticket info is shown")
    public void verifyTicketInfoInActiveChat(){
        SoftAssert soft = new SoftAssert();
        Map<String, String> actualInfo = getAgentHomeForMainAgent().getCrmTicketContainer().getFirstTicket().getTicketInfo();
        String expectedTicketCreated = "Created: " + formExpectedCRMTicketCreatedDate(createdCrmTicket.get().getCreatedDate());
        soft.assertEquals(actualInfo.get("createdDate").toLowerCase(), expectedTicketCreated.toLowerCase(),
                "Shown Ticket created date is not correct \n");
        soft.assertEquals(actualInfo.get("number"), "Ticket Number: " + createdCrmTicket.get().getTicketNumber(),
                "Shown Ticket Number is not correct \n");
        soft.assertEquals(actualInfo.get("note"), "Note: " + createdCrmTicket.get().getAgentNote(),
                "Shown Ticket note is not correct \n");
        soft.assertAll();
    }

    @Then("Ticket info is updated on chatdesk")
    public void verifyTicketInfoUpdatedInActiveChat(){
        SoftAssert soft = new SoftAssert();
        Map<String, String> actualInfo = getAgentHomeForMainAgent().getCrmTicketContainer().getFirstTicket().getTicketInfo();
        String expectedTicketCreated = "Created: " + formExpectedCRMTicketCreatedDate(createdCrmTicket.get().getCreatedDate());
        soft.assertEquals(actualInfo.get("createdDate").toLowerCase(), expectedTicketCreated.toLowerCase(),
                "Shown Ticket created date is not correct \n");
        soft.assertEquals(actualInfo.get("number"), "Ticket Number: " + crmTicketInfoForUpdating.get().get("ticketNumber"),
                "Shown Ticket Number is not correct \n");
        soft.assertEquals(actualInfo.get("note"), "Note: " + crmTicketInfoForUpdating.get().get("agentNote"),
                "Shown Ticket note is not correct \n");
        soft.assertAll();
    }

    @Given("Transfer timeout for (.*) tenant is set to (.*) seconds")
    public void updateAgentInactivityTimeout(String tenantOrgName, String timeout){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        Response resp = ApiHelper.updateTenantConfig(tenantOrgName, "agentInactivityTimeoutSec", timeout);
        Assert.assertEquals(resp.statusCode(), 200,
                "Changing agentInactivityTimeoutSec was not successful for '"+Tenants.getTenantUnderTestName()+"' tenant \n" +
        "Response: " + resp.getBody().asString());
    }

    private void formDataForCRMUpdating(){
        Map<String, String> info = new HashMap<>();
        info.put("agentNote", "Note for updating ticket");
        info.put("link", "http://updateurl.com");
        info.put("ticketNumber", "11111");
        crmTicketInfoForUpdating.set(info);
    }

    private String formExpectedCRMTicketCreatedDate(String createdTimeFromBackend){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        LocalDateTime dateTimeFromBackend =  LocalDateTime.parse(createdCrmTicket.get().getCreatedDate(), formatter).atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(TimeZone.getDefault().toZoneId()).toLocalDateTime();

        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        Map<Long, String> newDaysMap = new HashMap<>();
        newDaysMap.put(1L, "1st");
        newDaysMap.put(2L, "2nd");
        newDaysMap.put(3L, "3rd");
        newDaysMap.put(4L, "4th");
        newDaysMap.put(5L, "5th");
        newDaysMap.put(6L, "6th");
        newDaysMap.put(7L, "7th");
        newDaysMap.put(8L, "8th");
        newDaysMap.put(9L, "9th");
        newDaysMap.put(10L, "10th");
        newDaysMap.put(11L, "11th");
        newDaysMap.put(12L, "12th");
        newDaysMap.put(13L, "13th");
        newDaysMap.put(14L, "14th");
        newDaysMap.put(15L, "15th");
        newDaysMap.put(16L, "16th");
        newDaysMap.put(17L, "17th");
        newDaysMap.put(18L, "18th");
        newDaysMap.put(19L, "19th");
        newDaysMap.put(20L, "20th");
        newDaysMap.put(21L, "21st");
        newDaysMap.put(22L, "22nd");
        newDaysMap.put(23L, "23rd");
        newDaysMap.put(24L, "24th");
        newDaysMap.put(25L, "25th");
        newDaysMap.put(26L, "26th");
        newDaysMap.put(27L, "27th");
        newDaysMap.put(28L, "28th");
        newDaysMap.put(29L, "29th");
        newDaysMap.put(30L, "30th");
        newDaysMap.put(31L, "31st");

        builder.appendText(ChronoField.DAY_OF_MONTH, newDaysMap );
        builder.append(DateTimeFormatter.ofPattern(" yyyy, h:mm a"));
        DateTimeFormatter formatter1 = builder.toFormatter();


        return (dateTimeFromBackend.getMonth() + " " + dateTimeFromBackend.format(formatter1)).toLowerCase();    }

    private String getExpectedChatStartTimeForChatHistoryInActiveChat(){
        ZoneId zoneId =  TimeZone.getDefault().toZoneId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        LocalDateTime chatTimeFromBackendUTC = LocalDateTime.parse((String) selectedChatForHistoryTest.get("startedDate"), formatter);
        return chatTimeFromBackendUTC.atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(zoneId).toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("d MMM yyyy | HH:mm"));
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
        if (agentHomePage==null) {
            agentHomePage = new AgentHomePage("main agent");
            return agentHomePage;
        } else{
            return agentHomePage;
        }
    }

    private ProfileWindow getProfileWindow(String ordinalAgentNumber){
        if (profileWindow==null) {
            profileWindow = getAgentHomePage(ordinalAgentNumber).getProfileWindow();
            return profileWindow;
        } else{
            return profileWindow;
        }
    }


    private LeftMenuWithChats getLeftMenu(String agent) {
        return getAgentHomePage(agent).getLeftMenuWithChats();
    }

    private Customer360PersonalInfo getCustomer360Info(String clientFrom){
        String clientId = "";
        String integrationType = "";
        switch (clientFrom){
            case "fb dm":
                Map chatInfo = (Map) ApiHelper.getActiveChatsByAgent().getBody().jsonPath().getList("content")
                                        .stream()
                                        .filter(e -> ((Map)
                                                            ((Map) e).get("client")
                                                     ).get("type")
                                                .equals("FACEBOOK"))
                                        .findFirst().get();
                clientId = (String) chatInfo.get("clientId");
                integrationType = "FACEBOOK";
                break;
            case "twitter dm":
                clientId = TwitterUsers.getLoggedInUser().getDmUserId();
                integrationType = "TWITTER";
                break;
            case "dotcontrol":
                clientId = DotControlSteps.getFromClientRequestMessage().getClientId();
                integrationType = "HTTP";
                break;
            default:
                clientId = getUserNameFromLocalStorage();
                integrationType = "TOUCH";
                break;
        }
        return  ApiHelper.getCustomer360PersonalInfo(Tenants.getTenantUnderTestOrgName(),
                clientId, integrationType);
    }

    @Then("^CRM ticket is not created$")
    public void crmTicketDidNotCreated() {
        Assert.assertEquals( ApiHelper.getCRMTickets(getUserNameFromLocalStorage(), "TOUCH").size(), 0, "CRM ticket was created on back end");
    }

    @Then("(.*)type Note:(.*), Link:(.*), Number:(.*) for CRM ticket$")
    public void agentCreateCRMTicket(String agent,String note, String link, String number) {
        getAgentHomePage(agent).getAgentFeedbackWindow().fillForm(note, link, number);
        List <String> tags = getAgentHomePage(agent).getAgentFeedbackWindow().getChosenTags();
        prepareDataForCrmTicketChatdesk(note, link, number, tags);
    }


    @Then("^CRM ticket is created on backend with correct information$")
    public void crmTicketIsCreatedOnBackendWithCorrectInformation() {
        SoftAssert soft = new SoftAssert();
        CRMTicket actualTicketInfoFromBackend = ApiHelper.getCRMTickets(getUserNameFromLocalStorage(), "TOUCH").get(0);
        String createdDate = crmTicketInfoForUpdating.get().get("date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        LocalDateTime dateTimeFromBackend =  LocalDateTime.parse(actualTicketInfoFromBackend.getCreatedDate(), formatter).atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(TimeZone.getDefault().toZoneId()).toLocalDateTime();

        soft.assertEquals(dateTimeFromBackend.toString().substring(0, 15), createdDate.substring(0, 15),
                "Ticket created date does not match created on the backend \n");
        soft.assertEquals(actualTicketInfoFromBackend.getTicketNumber(), crmTicketInfoForUpdating.get().get("ticketNumber"),
                "Ticket Number does not match created on the backend  \n");
        soft.assertEquals(actualTicketInfoFromBackend.getAgentNote(),  crmTicketInfoForUpdating.get().get("agentNote"),
                " Ticket note does not match created on the backend \n");
        soft.assertEquals(actualTicketInfoFromBackend.getLink(), crmTicketInfoForUpdating.get().get("link"),
                " Ticket link does not match created on the backend \n");
//        soft.assertEquals(actualTicketInfoFromBackend.getLink(), crmTicketInfoForUpdating.get().get("agentTags"),
//                " Ticket link does not match created on the backend \n");
        soft.assertAll();

    }

    @Then("^Agent add (.*) tag$")
    public void agentAddSelectedTag(int iter) {
        getAgentHomeForMainAgent().getAgentFeedbackWindow().selectTags(iter);
        Assert.assertEquals(getAgentHomeForMainAgent().getAgentFeedbackWindow().getChosenTags().size(), iter,
                "Not all tags was added \n");
    }

    @Then("^Agent delete all tags$")
    public void agentDeleteAllTags() {
        getAgentHomeForMainAgent().getAgentFeedbackWindow().deleteTags();
    }

    @Then("^All tags for tenant is available in the dropdown$")
    public void allTagsForTenantIsAvailableInTheDropdown() {
        List<String> tags= ApiHelper.getTags(getUserNameFromLocalStorage(), "TOUCH");
        List<String> tagsInCRM = getAgentHomeForMainAgent().getAgentFeedbackWindow().getTags();
        Assert.assertTrue(tagsInCRM.equals(tags),
                " CRM ticket 'Tags' does not match created on the backend \n");
    }

    @Then("^Agent can search tag and select tag, selected tag added in tags field$")
    public void agentCanSearchTagAndSelectTag() {
        SoftAssert soft = new SoftAssert();
        List<String> tags= ApiHelper.getTags(getUserNameFromLocalStorage(), "TOUCH");
        String randomTag= tags.get((int)(Math.random() * tags.size()));
        getAgentHomeForMainAgent().getAgentFeedbackWindow().typeTags(randomTag);
        List<String> tagsInCRM = getAgentHomeForMainAgent().getAgentFeedbackWindow().getTags();
        getAgentHomeForMainAgent().getAgentFeedbackWindow().selectTagInSearch();
        List<String> chosenTags = getAgentHomeForMainAgent().getAgentFeedbackWindow().getChosenTags();
        soft.assertTrue(tagsInCRM.contains(randomTag),
                " CRM ticket 'Tags' does not match in search \n");
        soft.assertTrue(chosenTags.contains(randomTag),
                " CRM ticket 'Tag' does not match into the Tags field \n");
        soft.assertAll();
    }

    @Then("^(.*) receives 'pin' error message$")
    public void agentReceivesErrorMessage(String agent) {
        getAgentHomePage(agent).isPinErrorMassageShown(agent);
    }


    @Then("^I check (.*) color to '(.*)' for tenant in agent desk$")
    public void iCheckPrimaryColorToFfDecForTenantInAgentDesk(String color, String hex) {

        if (color.toLowerCase().contains("second")){
            Assert.assertEquals(getAgentHomePage("second agent").getPageHeader().getTenantNameColor(), hex, "Color for tenant name in agent desk window is not correct");
            Assert.assertEquals(getAgentHomePage("second agent").getPageHeader().gettenantLogoBorderColor(), hex, "Color for tenant logo border in agent desk window is not correct");
        }else {
            Assert.assertEquals(getAgentHomePage("second agent").getcustomer360Color(), hex, "Color for tenant 'Costomer' is not correct");
            Assert.assertEquals(getAgentHomePage("second agent").getLeftMenuWithChats().getexpandFilterButtonColor(), hex, "Color for tenant dropdown button is not correct");
            Assert.assertEquals(getAgentHomePage("second agent").gettouchButtonColor(), hex, "Color for tenant chat button is not correct");


        }
    }
}
