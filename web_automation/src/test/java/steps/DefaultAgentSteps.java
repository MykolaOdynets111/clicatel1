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
import datamanager.jacksonschemas.ChatHistoryItem;
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
import java.util.*;
import java.util.Collections;

public class DefaultAgentSteps implements JSHelper, DateTimeHelper {
    private AgentHomePage agentHomePage;
    private AgentHomePage secondAgentHomePage;
    private ProfileWindow profileWindow;
    private LeftMenuWithChats leftMenuWithChats;
    private static Map<String, Boolean> PRE_TEST_FEATURE_STATUS = new HashMap<>();
    private static Map<String, Boolean> TEST_FEATURE_STATUS_CHANGES = new HashMap<>();
    private static Customer360PersonalInfo customer360InfoForUpdating;
    private Faker faker = new Faker();
    private List<ChatHistoryItem> chatHistoryItems;
    private Map selectedChatForHistoryTest;

    private static void savePreTestFeatureStatus(String featureName, boolean status){
        PRE_TEST_FEATURE_STATUS.put(featureName, status);
    }

    public static boolean getPreTestFeatureStatus(String featureName){
        return PRE_TEST_FEATURE_STATUS.get(featureName);
    }

    private static void saveTestFeatureStatusChanging(String featureName, boolean status){
        TEST_FEATURE_STATUS_CHANGES.put(featureName, status);
    }

    public static boolean getTestFeatureStatusChanging(String featureName){
        return TEST_FEATURE_STATUS_CHANGES.get(featureName);
    }

    @Given("^I login as (.*) of (.*)")
    public void loginAsAgentForTenant(String ordinalAgentNumber, String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        ApiHelper.closeAllOvernightTickets(Tenants.getTenantUnderTestOrgName());
        if(!ordinalAgentNumber.contains("second")) ApiHelper.logoutTheAgent(Tenants.getTenantUnderTestOrgName());
        AgentLoginPage.openAgentLoginPage(ordinalAgentNumber, tenantOrgName).loginAsAgentOf(tenantOrgName, ordinalAgentNumber);
//        ApiHelper.closeActiveChats();
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

    @When("^Agent transfers chat$")
    public void transferChat(){
        getAgentHomeForMainAgent().getChatHeader().clickTransferButton();
        getAgentHomeForMainAgent().getTransferChatWindow().transferChat();
    }

    @Then("Second agent receives incoming transfer with \"(.*)\" note from the first agent")
    public void verifyIncomingTransferReceived(String notes){
        Assert.assertEquals(getAgentHomeForSecondAgent().getIncomingTransferWindow().getTransferNotes(), notes,
                "Notes in incoming transfer window is not as added by the first agent");
    }

    @Then("(.*) can see transferring agent name, (?:user name|twitter user name|facebook user name) and following user's message: '(.*)'")
    public void verifyIncomingTransferDetails(String agent, String userMessage) {
        try {
            SoftAssert soft = new SoftAssert();
            String expectedUserName = "";
            if(!ConfigManager.getSuite().equalsIgnoreCase("all tests")) {
                if (ConfigManager.getSuite().equalsIgnoreCase("twitter")) {
                    expectedUserName = TwitterUsers.getLoggedInUserName();
                    userMessage = TwitterSteps.getCurrentConnectToAgentTweetText();
                }
                if (ConfigManager.getSuite().equalsIgnoreCase("facebook")) {
                    expectedUserName = FacebookUsers.getLoggedInUserName();
                    userMessage = FacebookSteps.getCurrentUserMessageText();
                }
            }else {
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

    @Then("^(.*) has new conversation request$")
    public void verifyIfAgentReceivesConversationRequest(String agent) {
        Assert.assertTrue(getLeftMenu(agent).isNewConversationRequestIsShown(20, agent),
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
                    Assert.assertTrue(waitForDotControlRequestOnChatDesk(),
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
            if(leftMenuWithChats.isNewConversationRequestFromSocialIsShown(userName,3, "main")) return true;
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
        Assert.assertTrue(leftMenuWithChats.isNewConversationRequestFromSocialShownByChannel(userName, channel,60),
                "There is no new conversation request on Agent Desk (Client ID: "+getUserNameFromLocalStorage()+")");
    }


    @Then("^(.*) should not see from user chat in agent desk$")
    public void verifyConversationRemovedFromChatDesk(String agent){
        Assert.assertTrue(getLeftMenu(agent).isConversationRequestIsRemoved(30),
                "Conversation request is not removed from Agent Desk (Client ID: "+getUserNameFromLocalStorage()+")"
        );
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
        if(!(customerFrom.contains("fb")||customerFrom.contains("twitter"))) customer360InfoForUpdating.setFullName("AQA Run");
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
                Map chatInfo = (Map) ApiHelper.getActiveChatByAgent().getBody().jsonPath().getList("content")
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
}
