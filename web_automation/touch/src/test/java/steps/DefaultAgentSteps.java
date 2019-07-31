package steps;

import agentpages.AgentLoginPage;
import agentpages.uielements.*;
import apihelper.ApiHelper;
import driverfactory.DriverFactory;
import drivermanager.ConfigManager;
import emailhelper.CheckEmail;
import mc2api.PortalAuthToken;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.*;
import datamanager.jacksonschemas.ChatHistoryItem;
import datamanager.jacksonschemas.SupportHoursItem;
import datamanager.jacksonschemas.dotcontrol.DotControlRequestMessage;
import datamanager.jacksonschemas.dotcontrol.InitContext;
import datamanager.jacksonschemas.usersessioninfo.ClientProfile;
import dbmanager.DBConnector;
import emailhelper.GmailConnector;
import io.restassured.response.Response;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import socialaccounts.FacebookUsers;
import socialaccounts.TwitterUsers;
import steps.portalsteps.BasePortalSteps;
import steps.agentsteps.AbstractAgentSteps;
import steps.dotcontrol.DotControlSteps;

import javax.mail.Message;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Collections;

public class DefaultAgentSteps extends AbstractAgentSteps {

    private static ThreadLocal<Map<String, Boolean>> PRE_TEST_FEATURE_STATUS = new ThreadLocal<>();
    private static ThreadLocal<Map<String, Boolean>> TEST_FEATURE_STATUS_CHANGES = new ThreadLocal<>();
    private static Customer360PersonalInfo customer360InfoForUpdating;
    private List<ChatHistoryItem> chatHistoryItems;
    private Map selectedChatForHistoryTest;
    private String secondAgentName;
    private List<DotControlRequestMessage> createdChatsViaDotControl = new ArrayList<>();
    private String clientIDGlobal;
    private Message chatTranscriptEmail;


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
        ApiHelper.closeAllOvernightTickets(Tenants.getTenantUnderTestOrgName(), ordinalAgentNumber);
//        if(!ordinalAgentNumber.contains("second")) ApiHelper.logoutTheAgent(Tenants.getTenantUnderTestOrgName());
        AgentLoginPage loginPage = AgentLoginPage.openAgentLoginPage(ordinalAgentNumber, tenantOrgName)
                .loginAsAgentOf(tenantOrgName, ordinalAgentNumber);
        setAgentLoginPage(ordinalAgentNumber, loginPage);
        Assert.assertTrue(getAgentHomePage(ordinalAgentNumber).isAgentSuccessfullyLoggedIn(ordinalAgentNumber),
                "Agent is not logged in.");
    }

    @Given("^Try to login as (.*) of (.*)")
    public void tryToLoginAsAgentForTenant(String ordinalAgentNumber, String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        AgentLoginPage loginPage = AgentLoginPage.openAgentLoginPage(ordinalAgentNumber, tenantOrgName)
                .loginAsAgentOf(tenantOrgName, ordinalAgentNumber);
        setAgentLoginPage(ordinalAgentNumber, loginPage);
    }


    @Given("^(.*) has no active chats$")
    public void closeActiveChats(String agent){
        ApiHelper.closeActiveChats(agent);
        getAgentHomePage(agent).getLeftMenuWithChats().waitForAllChatsToDisappear(4);
    }



    @When("I login with the same credentials in another browser as an agent of (.*)")
    public void loginWithTheSameCreds(String tenantOrgName){
        AgentLoginPage loginPage = AgentLoginPage.openAgentLoginPage("second agent", tenantOrgName)
                .loginAsAgentOf(tenantOrgName, "main agent");
        setAgentLoginPage("second agent", loginPage);

        Assert.assertTrue(getAgentHomePage("second agent").isAgentSuccessfullyLoggedIn("second agent"),
                "Agent is not logged in.");
    }

    @When("I open browser to log in in chat desk as an agent of (.*)")
    public void openBrowserToLogin(String tenantOrgName){
        setCurrentLoginPage(AgentLoginPage.openAgentLoginPage("second agent", tenantOrgName));
    }

    @When("I check primary color for tenant in login page")
    public void checkPrimaryColorLoginPage(){
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getCurrentLoginPage().getLoginButtonColor(), BasePortalSteps.getTenantInfoMap().get("newColor"),
                "Color for tenant 'Login button' is not correct");
        soft.assertEquals(getCurrentLoginPage().getStringForgotColor(), BasePortalSteps.getTenantInfoMap().get("newColor"),
                "Color for tenant 'Forgot password?' string is not correct");
        soft.assertAll();
    }

    @When("I login in another browser as an agent of (.*)")
    public void loginWithTheSameAnotherBrowser(String tenantOrgName){
        getCurrentLoginPage().loginAsAgentOf(tenantOrgName, "main agent");
        Assert.assertTrue(getAgentHomePage("second agent").isAgentSuccessfullyLoggedIn("second agent"),
                "Agent is not logged in.");
    }

    @Then("^In the first browser Connection Error should be shown$")
    public void verifyAgentIsDisconnected(){
        Assert.assertTrue(getAgentHomePage("first agent").isConnectionErrorShown("first agent"),
                "Agent in the first browser is not disconnected");
    }

    @Given("^(.*) tenant feature is set to (.*) for (.*)$")
    public void setFeatureStatus(String feature, String status, String tenantOrgName){
        PortalAuthToken.clearAccessTokenForPortalUser();
        boolean featureStatus = ApiHelper.getFeatureStatus(tenantOrgName, feature);
        savePreTestFeatureStatus(feature, featureStatus);
        saveTestFeatureStatusChanging(feature, Boolean.parseBoolean(status.toLowerCase()));
        if(featureStatus!=Boolean.parseBoolean(status.toLowerCase())) {
            ApiHelper.updateFeatureStatus(tenantOrgName, feature, status);
        }
    }

    @Then("^On backand (.*) tenant feature status is set to (.*) for (.*)$")
    public void isFeatureStatusSet(String feature, boolean status, String tenantOrgName){
        Assert.assertEquals(ApiHelper.getFeatureStatus(tenantOrgName, feature),status,
                "Agent feature is not expected");
    }

    @Then("^(.*) is logged in chat desk$")
    public void verifyAgentLoggedIn(String ordinalAgentNumber){
        boolean result = getAgentHomePage(ordinalAgentNumber).isAgentSuccessfullyLoggedIn(ordinalAgentNumber);
        if(result) ConfigManager.setIsSecondCreated("true");
        Assert.assertTrue(result, "Agent is not logged in chat desk.");
    }



    @Then("^(.*) is not redirected to chatdesk$")
    public void verifyAgentNotLoggedIn(String ordinalAgentNumber){
        Assert.assertFalse(getAgentHomePage(ordinalAgentNumber).isAgentSuccessfullyLoggedIn(ordinalAgentNumber),
                "Agent is redirected to chat desk.");
    }

    @When("^(.*) transfers chat$")
    public void transferChat(String agent){
        getAgentHomePage(agent).getChatHeader().clickTransferButton(agent);
        secondAgentName = getAgentHomePage(agent).getTransferChatWindow().transferChat(agent);
    }

    @When("^(.*) transfer a few chats$")
    public void transferFewDotControlChats(String agent){
        for(DotControlRequestMessage chat : createdChatsViaDotControl) {
            int availableAgents = ApiHelper.getNumberOfLoggedInAgents();
            for(int i = 0; i<11; i ++){
                if(availableAgents<2) {
                    getAgentHomePage(agent).waitForDeprecated(1000);
                    availableAgents = ApiHelper.getNumberOfLoggedInAgents();
                } else{
                    break;
                }
            }
            if(availableAgents<2) Assert.fail(
                    "Second agent is not available after waiting 11 seconds after chat transfer");
            getLeftMenu(agent).openNewFromSocialConversationRequest(chat.getClientId());
            transferChat(agent);
            getAgentHomePage(agent).waitForModalWindowToDisappear();

        }
    }

    @When("^(.*) receives incoming transfer notification with \"Transfer waiting\" header and collapsed view$")
    public void verifyTransferredChatsCollapsed(String agent){
        Assert.assertEquals(getAgentHomePage(agent).getCollapsedTransfers().size(), 2,
                "Not all expected collapsed transferred chats shown");
    }

    @When("^(.*) click on \"Transfer waiting\" header$")
    public void expandFirstOfCollapsedTransfer(String agent){
        getAgentHomePage(agent).getCollapsedTransfers().get(0).click();
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

    @Then("(.*) can see transferring agent name, (.*) and following user's message: '(.*)'")
    public void verifyIncomingTransferDetails(String agent, String user, String userMessage) {
        try {
            SoftAssert soft = new SoftAssert();
            String expectedUserName = "";
            if (ConfigManager.getSuite().equalsIgnoreCase("twitter")) {
                expectedUserName = socialaccounts.TwitterUsers.getLoggedInUserName();
                userMessage = TwitterSteps.getCurrentConnectToAgentTweetText();
            }
            if(ConfigManager.getSuite().equalsIgnoreCase("facebook")) {
                    expectedUserName = socialaccounts.FacebookUsers.getLoggedInUserName();
                    userMessage = FacebookSteps.getCurrentUserMessageText();
            }
            if (!ConfigManager.getSuite().equalsIgnoreCase("facebook") &&
                    !ConfigManager.getSuite().equalsIgnoreCase("twitter")){
                expectedUserName = getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance());
            }
            if(user.contains("first chat")){
                expectedUserName = createdChatsViaDotControl.get(0).getClientId();
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

    @Then("^(.*) receives incoming transfer on the right side of the screen with user's profile picture, channel and sentiment$")
    public void secondAgentReceivesIncomingTransferOnTheRightSideOfTheScreenWithUserSProfilePicturePriorityChannelAndSentiment(String agent) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(getAgentHomePage(agent).getIncomingTransferWindow().isValidImgTransferPicture(),
                "User picture as not expected");
        softAssert.assertTrue(getAgentHomePage(agent).getIncomingTransferWindow().isValidImTransferChannel(),
                "Channel picture as not expected");
        softAssert.assertTrue(getAgentHomePage(agent).getIncomingTransferWindow().isValidImgTransferSentiment(),
                "Sentiment picture as not expected");
        softAssert.assertTrue(getAgentHomePage(agent).getIncomingTransferWindow().isRigthSideTransferChatWindow(),
                "Transfered chat window not on the right side of the screen");

        softAssert.assertAll();


    }

    @Then("^(.*) click \"Accept transfer\" button$")
    public void acceptIncomingTransfer(String agent){
        getAgentHomePage(agent).getIncomingTransferWindow().acceptTransfer();
    }

    @Then("^Second agent click \"Reject transfer\" button$")
    public void rejectIncomingTransfer(){
        getAgentHomeForSecondAgent().getIncomingTransferWindow().rejectTransfer();
    }

    @Then("^(.*) click \"Accept\" button$")
    public void acceptRejectedTransfer(String agent){
        getAgentHomePage(agent).getIncomingTransferWindow().acceptRejectTransfer(agent);
    }

    @Then("^(.*) has (?:new|old) conversation (?:request|shown)$")
    public void verifyIfAgentReceivesConversationRequest(String agent) {
        boolean isConversationShown = getLeftMenu(agent).isNewConversationRequestIsShown(20, agent);
        int sessionCapacity;
        if(!isConversationShown){
            sessionCapacity = ApiHelper.getTenantInfo(Tenants.getTenantUnderTestOrgName()).jsonPath().get("sessionsCapacity");
            if (sessionCapacity==0) ApiHelper.updateSessionCapacity(Tenants.getTenantUnderTestOrgName(), 50);
        }
        List<SupportHoursItem> supportHours = ApiHelper.getAgentSupportDaysAndHours(Tenants.getTenantUnderTestOrgName());
        if(supportHours.size()<7){
            ApiHelper.setAgentSupportDaysAndHours(Tenants.getTenantUnderTestOrgName(), "all week", "00:00", "23:59");
        }
        Assert.assertTrue(isConversationShown,
                "There is no new conversation request on Agent Desk (Client ID: "+getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance())+")\n" +
                        "Number of logged in agents: " + ApiHelper.getNumberOfLoggedInAgents() +"\n" +
                        "sessionsCapacity: " + ApiHelper.getTenantInfo(Tenants.getTenantUnderTestOrgName()).jsonPath().get("sessionsCapacity") + "\n" +
                        "Support hours: " + supportHours + "\n"
        );
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

    @When("^(.*) click 'Pin' button$")
    public void pinChat(String agent){
        getAgentHomePage(agent).getChatHeader().clickPinButton(agent);
    }


    @When("^(.*) click 'Unpin' button$")
    public void unpinChat(String agent) {
        getAgentHomePage(agent).getChatHeader().clickUnpinButton(agent);
    }

    @Then("^(.*) can not click '(.*)' button$")
    public void agentCanNotClickTransferChatButton(String agent, String transferButton) {
        Assert.assertFalse(getAgentHomePage(agent).getChatHeader().isButtonEnabled(transferButton),
                "Transfer chat button is enabled ");
    }

    @Then("^(.*) button is (.+) on Chat header$")
    public void isButtonEnabled(String button, String state){
        if (state.equalsIgnoreCase("disabled"))
            Assert.assertFalse(getAgentHomePage("main").getChatHeader().isButtonEnabled(button));
        else if (state.equalsIgnoreCase("enabled"))
            Assert.assertTrue(getAgentHomePage("main").getChatHeader().isButtonEnabled(button));
    }

    @Then("^(.*) button hidden from the Chat header$")
    public void checkIfButtonHidden(String button){
            Assert.assertFalse( getAgentHomePage("main").getChatHeader().isButtonEnabled(button),
                    "'" + button + "' button is displayed");
    }


    @Then("^(.*) has new conversation request within (.*) seconds$")
    public void verifyIfAgentReceivesConversationRequest(String agent, int timeout) {
        Assert.assertTrue(getLeftMenu(agent).isNewConversationRequestIsShown(timeout, agent),
                "There is no new conversation request on Agent Desk (Client ID: "+getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance())+")\n" +
                        "Number of logged in agents: " + ApiHelper.getNumberOfLoggedInAgents() +"\n");
    }

    @Then("(.*) sees 'overnight' icon in this chat")
    public void verifyOvernightIconShown(String agent){
        Assert.assertTrue(getLeftMenu(agent).isOvernightTicketIconShown(getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance())),
                "Overnight icon is not shown for overnight ticket. \n clientId: "+ getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()));
    }

    @Then("^Overnight ticket is removed from (.*) chatdesk$")
    public void checkThatOvernightTicketIsRemoved(String agent){
        Assert.assertTrue(getLeftMenu(agent).isOvernightTicketIconRemoved(getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance())),
                "Overnight ticket is still shown");
    }

    @Then("Message that it is overnight ticket is shown for (.*)")
    public void verifyMessageThatThisIsOvernightTicket(String agent){
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(getAgentHomePage(agent).getChatForm().isOvernightTicketMessageShown(),
                "Message that this is Overnight ticket is not shown");
        softAssert.assertTrue(getAgentHomePage(agent).getChatForm().isSendEmailForOvernightTicketMessageShown(),
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

    @Then("^(.*) select \"(.*)\" filter option$")
    public void selectFilterOption(String agent,String option){
        getLeftMenu(agent).selectFilterOption(option);
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
                String userName=null;
                if (social.equalsIgnoreCase("twitter")) userName = socialaccounts.TwitterUsers.getLoggedInUserName();
                if(social.equalsIgnoreCase("facebook")) userName = socialaccounts.FacebookUsers.getLoggedInUserName();
                if(social.equalsIgnoreCase("dotcontrol")) userName = DotControlSteps.getClient();
                Assert.assertTrue(getLeftMenu(agent).isNewConversationRequestFromSocialIsShown(userName,40, agent),
                                "There is no new conversation request on Agent Desk (Client name: "+userName+")");
    }

    private boolean waitForDotControlRequestOnChatDesk(String agent){
        for(int i = 0; i<5; i++) {
            String userName = DotControlSteps.getFromClientRequestMessage().getClientId();
            if(getLeftMenu(agent).isNewConversationRequestFromSocialIsShown(userName,4, "main")) return true;
            else {
                DriverFactory.getAgentDriverInstance().navigate().refresh();
            }
        }
        return false;
    }

    @Then("^(.*) has new conversation request from (.*) user through (.*) channel$")
    public void verifyAgentHasRequestFormSocialUser(String agent, String social, String channel){
        String userName=null;
        if (social.equalsIgnoreCase("twitter")) userName = socialaccounts.TwitterUsers.getLoggedInUserName();
        if(social.equalsIgnoreCase("facebook")) userName = socialaccounts.FacebookUsers.getLoggedInUserName();
        Assert.assertTrue(getLeftMenu(agent).isNewConversationRequestFromSocialShownByChannel(userName, channel,20),
                "There is no new conversation request on Agent Desk (Client ID: "+getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance())+")");
    }


    @Then("^(.*) should not see from user chat in agent desk$")
    public void verifyConversationRemovedFromChatDesk(String agent){
        // ToDo: Update after clarifying timeout in System timeouts
        Assert.assertTrue(getLeftMenu(agent).isConversationRequestIsRemoved(20),
                "Conversation request is not removed from Agent Desk (Client ID: "+getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance())+")"
        );
    }

    @Then("^(.*) sees 'flag' icon in this chat$")
    public void verifyFlagAppearance(String agent){
        Assert.assertTrue(getLeftMenu(agent).isFlagIconShown(getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance())),
                "Flag icon is not shown");
    }

    @Then("^(.*) do not see 'flag' icon in this chat$")
    public void verifyFlagNotAppearance(String agent){
        Assert.assertTrue(getLeftMenu(agent).isFlagIconRemoved(getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance())),
                "Flag icon is shown");
    }

    @When("^(.*) click on (?:new|last opened) conversation request from (.*)$")
    public void acceptUserFromSocialConversation(String agent, String socialChannel) {
        String userName=null;
        switch (socialChannel.toLowerCase()){
            case "touch":
                userName = getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance());
                break;
            case "twitter":
                userName = socialaccounts.TwitterUsers.getLoggedInUserName();
                break;
            case "facebook":
                userName = socialaccounts.FacebookUsers.getLoggedInUserName();
                break;
            case "dotcontrol":
               userName = DotControlSteps.getClient();

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
        Response resp = null;
        switch (shiftStrategy){
            case "with day shift":
                LocalDateTime currentTimeWithADayShift = LocalDateTime.now().minusDays(1);

                resp = ApiHelper.setAgentSupportDaysAndHours(Tenants.getTenantUnderTestOrgName(),
                        currentTimeWithADayShift.getDayOfWeek().toString(),"00:00", "23:59");
                break;
            case "for all week":
                resp = ApiHelper.setAgentSupportDaysAndHours(Tenants.getTenantUnderTestOrgName(), "all week",
                        "00:00", "23:59");
                getAgentHomePage("main").waitForDeprecated(1500);
                break;
        }
        Assert.assertEquals(resp.statusCode(), 200,
                "Changing support hours was not successful\n" +
                "resp body: " + resp.getBody().asString() + "\n");
    }

    @Then("^Tab with user info has \"(.*)\" header$")
    public void verifyTabHeader(String headerName){
        Assert.assertEquals(getAgentHomeForMainAgent().getSelectedTabHeader(), headerName,
                "Incorrect header of Customer 360 container");
    }


    @Then("Correct (.*) client details are shown")
    public void verifyClientDetails(String clientFrom){
        Customer360PersonalInfo customer360PersonalInfoFromChatdesk = getAgentHomePage("main")
                .getCustomer360Container().getActualPersonalInfo();

        Assert.assertEquals(customer360PersonalInfoFromChatdesk, getCustomer360Info(clientFrom),
                "User info is not as expected \n");
    }

    @Then("Correct dotcontrol client details from Init context are shown")
    public void verifyInitContextClientDetails(){
        Customer360PersonalInfo customer360PersonalInfoFromChatdesk = getCustomer360Container("main").getActualPersonalInfo();

        Customer360PersonalInfo expectedResult = getCustomer360Info("dotcontrol");
        InitContext initContext = DotControlSteps.getInitContext();
        expectedResult.setFullName(initContext.getFullName())
                        .setEmail(initContext.getEmail())
                        .setPhone(initContext.getPhone().replace(" ", ""))
        .setChannelUsername("");

        Assert.assertEquals(customer360PersonalInfoFromChatdesk, expectedResult,
                "User Customer360 info is not as in init context \n");
    }


    @When("Click (?:'Edit'|'Save') button in Customer 360 view")
    public void clickEditCustomerView(){
        getAgentHomePage("main").getCustomer360Container().clickSaveEditButton();
    }

    @Then("^Wait for (.d*) seconds for Phone Number to be (.*)$")
    public void checkPhoneNumberFieldUpdate(int waitFor, String requiredState) throws InterruptedException{
        Customer360Container customer360Container = new Customer360Container();
        customer360Container.setCurrentDriver(DriverFactory.getDriverForAgent("agent"));

        int waitTimeInMillis = waitFor * 1000;
        long endTime = System.currentTimeMillis() + waitTimeInMillis;

        while (!customer360Container.isPhoneNumberFieldUpdated(requiredState)
                    && System.currentTimeMillis() < endTime) {
            Thread.sleep(200);
        }
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

    @When("^(.*) see (.*) phone number added into customer's profile$")
    public void updatePhoneNumberCustomer360(String agent, String isPhoneNumberRequired){
        Assert.assertTrue(isRequiredPhoneNumberDisplayed(agent, isPhoneNumberRequired));
    }

    @Then("^(.*) button (.*) displayed in Customer 360$")
    public void checkCustomer360PhoneButtonsVisibility(String buttonName, String isOrNotDisplayed){
        Customer360Container customer360Container = getAgentHomePage("main").getCustomer360Container();
        if (isOrNotDisplayed.equalsIgnoreCase("not"))
            Assert.assertFalse(customer360Container.isCustomer360SMSButtonsDisplayed(buttonName), "'" + buttonName + "' button is not displayed");
        else
            Assert.assertTrue(customer360Container.isCustomer360SMSButtonsDisplayed(buttonName), "'" + buttonName + "' button still displayed");
    }

    @Then("^'Verify' and 'Re-send OTP' buttons (.*) displayed in Customer 360$")
    public void checkCustomer360PhoneVerifyAndReSendButtonsVisibility(String isOrNotDisplayed){
        Customer360Container customer360Container = getAgentHomePage("main").getCustomer360Container();
        SoftAssert softAssert = new SoftAssert();
        if (isOrNotDisplayed.contains("not")) {
            softAssert.assertFalse(customer360Container.isCustomer360SMSButtonsDisplayed("Verify"),
                    "'Verify' button is not displayed");
            softAssert.assertFalse(customer360Container.isCustomer360SMSButtonsDisplayed("Re-send OTP"),
                    "'Re-send OTP' button is not displayed");
            softAssert.assertAll();
        }
        else {
            softAssert.assertTrue(customer360Container.isCustomer360SMSButtonsDisplayed("Verify"));
            softAssert.assertTrue(customer360Container.isCustomer360SMSButtonsDisplayed("Re-send OTP"));
            softAssert.assertAll();
        }
    }

    @When("^(.*) phone number for (.*) user$")
    public void changePhoneNumberCustomer360(String changeOrDelete, String customerFrom){
        String phoneNumber = " "; //in case we need to delete phone number
        Customer360PersonalInfo currentCustomerInfo = getCustomer360Info(customerFrom);
        if (changeOrDelete.equalsIgnoreCase("change"))
            phoneNumber = generateUSCellPhoneNumber();

        customer360InfoForUpdating = currentCustomerInfo.setPhone(phoneNumber);

        getAgentHomePage("main").getCustomer360Container().setPhoneNumber(phoneNumber);
        Assert.assertEquals(currentCustomerInfo.getPhone(), phoneNumber, "Entered phone number is not equal to displayed one");
    }

    @When("Agent click on '(.*)' button in Customer 360")
    public void clickPhoneActionsButtonsCustomer360(String buttonName){
        getAgentHomeForMainAgent().getCustomer360Container().clickPhoneNumberVerificationButton(buttonName);
    }

    @Then("^'Verify phone' window is (.*)$")
    public void verifyPhoneNumberWindowOpened(String isWindowOpen) {
        if (isWindowOpen.equalsIgnoreCase("opened"))
            Assert.assertTrue(getAgentHomeForMainAgent().getVerifyPhoneNumberWindow().isOpened(),
                    "'Verify phone' window is not opened.");
        else
            Assert.assertTrue(getAgentHomeForMainAgent().getVerifyPhoneNumberWindow().isClosed(),
                    "'Verify phone' window wasn't closed.");
    }

    @Then("User's profile phone number (.*) in 'Verify phone' input field")
    public void phoneNumberForVerifyCheck(String isRequiredToDisplay){

        String phoneNumberInCustomer360 = getAgentHomeForMainAgent().getCustomer360Container()
                .getPhoneNumber().replaceAll("\\s+", "");
        String phoneNumberInVerifyPopUp = getAgentHomeForMainAgent().getVerifyPhoneNumberWindow()
                .getEnteredPhoneNumber().replaceAll("[\\s-.]", "");
        if (isRequiredToDisplay.contains("not")){
            Assert.assertTrue(phoneNumberInVerifyPopUp.equals(""), "Some phone number displayed in the field");
        }
        else{
            Assert.assertTrue(phoneNumberInVerifyPopUp.equals(phoneNumberInCustomer360),
                    "Phone number in Verify phone window is different from displayed in Customer 360");
        }
    }

    @When("Agent click on (.*) button on 'Verify phone' window")
    public void closeVerifyPhonePopUp(String buttonName){
        getAgentHomeForMainAgent().getVerifyPhoneNumberWindow().sendOrCancelClick(buttonName);
    }

    @When("Agent send OTP message with API")
    public void sendOTPWithAPI(){
        String linkedClientProfileId = DBConnector.getLinkedClientProfileID(ConfigManager.getEnv(), getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()));
        DBConnector.addPhoneAndOTPStatusIntoDB(ConfigManager.getEnv(), linkedClientProfileId);
    }

    @Then("'Verified' label become (.*)")
    public void checkVerifiedLabel(String isVisible) {
        if (isVisible.equalsIgnoreCase("visible"))
            Assert.assertTrue(getAgentHomeForMainAgent().getCustomer360Container().isVerifiedLabelDisplayed(), "Verified label is not displayed");
        else
            Assert.assertTrue(getAgentHomeForMainAgent().getCustomer360Container().isVerifiedLabelHidden(), "Verified label remains displayed");
    }

    @Then("SMS client-profile added into DB")
    public void checkSMSProfileCreating(){
        String linkedClientProfileId = DBConnector.getLinkedClientProfileID(ConfigManager.getEnv(), getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()));
        String phone = getAgentHomeForMainAgent().getCustomer360Container().getPhoneNumber().replaceAll("\\+", "");
        Assert.assertTrue(DBConnector.isSMSClientProfileCreated(ConfigManager.getEnv(), phone, linkedClientProfileId, "MC2_SMS"),
                "MC2_SMS client profile wasn't created");
    }

    @Then("Chat separator with OTP code and 'I have just sent...' message with user phone number are displayed")
    public void chatSeparatorCheck(){
        String phone = getAgentHomeForMainAgent().getCustomer360Container().getPhoneNumber();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(getAgentHomeForMainAgent().getChatBody().isOTPDividerDisplayed(), "No OTP divider displayed");
        softAssert.assertTrue(getAgentHomeForMainAgent().getChatForm().getTextFromMessageInputField().replaceAll("\\s", "").contains(phone),
                "Phone number is not displayed in message field");
        softAssert.assertAll();
    }

    @Then("New OTP code is different from the previous one")
    public void checkOTPCodes(){
        Assert.assertTrue(getAgentHomeForMainAgent().getChatBody().isNewOTPCodeDifferent(), "Codes are equal");
    }

    @When("^Agent switches to opened (?:Portal|ChatDesk) page$")
    public void switchBetweenPortalAndChatDesk(){
        String currentWindow = DriverFactory.getDriverForAgent("main").getWindowHandle();

        if (DriverFactory.getDriverForAgent("main").getWindowHandles().size() > 1) {
            for (String winHandle : DriverFactory.getDriverForAgent("main").getWindowHandles()) {
                if (!winHandle.equals(currentWindow)) {
                    DriverFactory.getDriverForAgent("main").switchTo().window(winHandle);
                }
            }
        }
    }

    @When("^Agent refresh current page$")
    public void refreshCurrentPage(){
        DriverFactory.getDriverForAgent("main").navigate().refresh();
    }

    @Then("^(.*) customer info is updated on backend$")
    public void verifyCustomerInfoChangesOnBackend(String customerFrom){
        Assert.assertEquals(getCustomer360Info(customerFrom), customer360InfoForUpdating,
                "Customer 360 info is not updated on backend after making changes on chatdesk. \n");
    }

    @Then("^New info is shown in left menu with chats$")
    public void checkUpdatingUserInfoInLeftMenu(){
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getLeftMenu("main").getActiveChatUserName(), customer360InfoForUpdating.getFullName().trim(),
                "Full user name is not updated in left menu with chats after updating Customer 360 info \n");
        soft.assertEquals(getLeftMenu("main").getActiveChatLocation(), customer360InfoForUpdating.getLocation(),
                "Location is not updated in left menu with chats after updating Customer 360 info \n");
        soft.assertAll();
    }

    @And("^Empty image is not shown for chat with (.*) user$")
    public void verifyEmptyImgNotShown(String customerFrom){
        String user = "";
        if(customerFrom.equalsIgnoreCase("facebook")) user = socialaccounts.FacebookUsers.getLoggedInUserName();
        Assert.assertTrue(getLeftMenu("main").isProfileIconNotShown(user),
                "Image is not updated in left menu with chats. \n");
    }

    @Then("^Message (.*) shown like last message in left menu with chat$")
    public void verifyLastMessageInLeftMenu(String customerMsg){
        Assert.assertEquals(getLeftMenu("main").getActiveChatLastMessage(), customerMsg,
                "Last message in left menu with chat as not expected. \n");
    }

    @Then("^Valid image for (.*) integration are shown in left menu with chat$")
    public void verifyImgForLastMessageInLeftMenu(String adapter) {
        Assert.assertTrue(getLeftMenu("main").isValidImgForActiveChat(adapter),
                "Image in last message in left menu for " + adapter + " adapter as not expected. \n");
//        getLeftMenu("main").createValidImgForActiveChat(adapter); //do not delete
    }

    @Then("^Valid sentiment icon are shown for (.*) message in left menu with chat$")
    public void verifyIconSentimentForLastMessageInLeftMenu(String message) {
        Assert.assertTrue(getLeftMenu("main").isValidIconSentimentForActiveChat(message),
                "Image in last message in left menu for sentiment as not expected. \n");
    }

    @Then("^Customer name is updated in active chat header$")
    public void verifyCustomerNameUpdated(){
        Assert.assertTrue(getAgentHomeForMainAgent().getChatHeader().getChatHeaderText().contains(customer360InfoForUpdating.getFullName().trim()),
                "Updated customer name is not shown in chat header");
    }

    @Then("^Agent photo is updated on chatdesk$")
    public void verifyPhotoLoadedOnChatdesk(){
        Assert.assertTrue(getAgentHomePage("main").getPageHeader().isAgentImageShown(),
                "Agent image is not shown on chatdesk");
    }

    @Then("^Tenant photo is shown on chatdesk$")
    public void verifyTenantImageIsShownOnChatdesk(){
        Assert.assertTrue(getAgentHomePage("main").getPageHeader().isTenantImageShown(),
                "Tenant image is not shown on chatdesk");
    }

    @When("^(.*) searches and selects random chat is chat history list$")
    public void selectRandomChatFromHistory(String ordinalAgentNumber){
        getAgentHomePage(ordinalAgentNumber).waitForLoadingInLeftMenuToDisappear(ordinalAgentNumber, 3,7);
        getLeftMenu(ordinalAgentNumber).selectRandomChat(ordinalAgentNumber);
    }

    @When("^(.*) sees correct chat history$")
    public void getChatHistoryFromBackend(String agent){
        String clientID = getCustomer360Container(agent).getUserFullName();
        Response sessionDetails  = ApiHelper.getSessionDetails(clientID);
        List<String> sessionIds = sessionDetails.getBody().jsonPath().getList("data.sessionId");
        List<ChatHistoryItem> chatItems = ApiHelper.getChatHistory(Tenants.getTenantUnderTestOrgName(), sessionIds.get(0));
        List<String> messagesFromChatBody = getChatBody(agent).getAllMessages();
        List<String> expectedMessagesList = getExpectedChatHistoryItems(TimeZone.getDefault().toZoneId(), chatItems);

        Assert.assertEquals(messagesFromChatBody, expectedMessagesList,
                "Shown on chatdesk messages are not as expected from API \n" +
                "Expected list: " + expectedMessagesList + "\n" +
                "Actual list: " + messagesFromChatBody);
    }

    @When("Save clientID value for (.*) user")
    public void saveClientIDValue(String userFrom){
        if (userFrom.equalsIgnoreCase("facebook"))
            clientIDGlobal = socialaccounts.FacebookUsers.getFBTestUserFromCurrentEnv().getFBUserIDMsg().toString();
        else if (userFrom.equalsIgnoreCase("twitter"))
            clientIDGlobal = socialaccounts.TwitterUsers.getLoggedInUser().getDmUserId();
        else
            clientIDGlobal = getAgentHomePage("main").getCustomer360Container().getUserFullName();
    }

    @Then("Chat Transcript email arrives")
    public void verifyChatTranscriptEmail(){
        // Checking added "standarttouchgoplan@gmail.com" email for chat transcript emails
        GmailConnector.loginAndGetInboxFolder(MC2Account.QA_STANDARD_ACCOUNT.getEmail(), MC2Account.QA_STANDARD_ACCOUNT.getPass());

        chatTranscriptEmail = CheckEmail.getLastMessageBySender("Clickatell <transcripts@clickatell.com>", 60);

        Assert.assertFalse(CheckEmail.getEmailSubject(chatTranscriptEmail).isEmpty(), "No Chat Transcript message received");
    }

    @Then("Email title contains (.*) adapter, client ID/Name/Email, chat ID, session number values")
    public void verifyChatTranscriptTitle(String adapter){
        Response sessionDetails  = ApiHelper.getSessionDetails(clientIDGlobal);
        String chatID = sessionDetails.getBody().jsonPath().getList("data.conversationId").get(0).toString();
        int sessionsNumber = DBConnector.getNumberOfSessionsInConversation(ConfigManager.getEnv(), chatID);

        String chatTranscriptEmailTitle = CheckEmail.getEmailSubject(chatTranscriptEmail);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(getAdapter(adapter), getAdapterFromEmailSubject(chatTranscriptEmailTitle), "Adapters are not match");
        softAssert.assertEquals(getSecondParameterPerAdapter(adapter), getClientIDFromEmailSubject(chatTranscriptEmailTitle), "ClientIDs are not match");
        softAssert.assertEquals(chatID, getChatIDFromEmailSubject(chatTranscriptEmailTitle), "chatIDs are not match");
        softAssert.assertEquals(sessionsNumber, getLastSessionNumberFromEmailSubject(chatTranscriptEmailTitle), "Different session number");
        softAssert.assertAll();
    }

    @Then("Email content contains chat history from the terminated conversation")
    public void compareHistoriesInChatTranscript(){
        List<String> historyFromEmail = CheckEmail.getChatTranscriptEmailContent(chatTranscriptEmail);

        String lastSessionID = DBConnector.getLastSessioinID(ConfigManager.getEnv(), Tenants.getTenantUnderTestName(), clientIDGlobal);
        List<ChatHistoryItem> chatItems = ApiHelper.getChatHistory(Tenants.getTenantUnderTestOrgName(), lastSessionID);
        List<String> historyFromDB = getBareChatHistory(chatItems);

        Assert.assertEquals(historyFromEmail, historyFromDB, "Histories in Email and in DB are different");
    }

    // WARNING: Bad Code!
    public String getAdapter(String adapter){
        switch(adapter.toLowerCase()) {
            //Because of we are creating .Control integration for "fbmsg" adapter, this method will return this adapter name
            case "dotcontrol":
                return "fbmsg";

            default:
                return adapter;
        }
    }

    public String getAdapterFromEmailSubject(String chatTranscriptEmailTitle) {
        String [] dataInString = chatTranscriptEmailTitle.split(",");
        return dataInString[0].trim();
    }

    public String getSecondParameterPerAdapter(String adapter){
        ClientProfile clientAttributes = ApiHelper.getClientAttributes(ApiHelper.getClientProfileId(clientIDGlobal));
        switch(adapter.toLowerCase()){
            case "dotcontrol":
            case "whatsapp":
                return clientIDGlobal;
            case "fbmsg":
                return clientAttributes.getAttributes().getFirstName();
            case "twdm":
                return clientAttributes.getAttributes().getFirstName() + " " + clientAttributes.getAttributes().getLastName();
            case "webchat":
                return clientAttributes.getAttributes().getEmail();
            default:
                return null;
        }
    }

    public String getClientIDFromEmailSubject(String chatTranscriptEmailTitle) {
        String [] dataInString = chatTranscriptEmailTitle.split(",");
        return dataInString[1].trim();
    }

    private String getChatIDAndSessionNumberFromEmailSubject(String chatTranscriptEmailTitle) {
        String [] dataInString = chatTranscriptEmailTitle.split(",");
        return dataInString[2].trim();
    }

    public String getChatIDFromEmailSubject(String chatTranscriptEmailTitle) {
        String chatAndSessionID = getChatIDAndSessionNumberFromEmailSubject(chatTranscriptEmailTitle);
        return chatAndSessionID.substring(0, chatAndSessionID.indexOf("(")).trim();
    }

    public int getLastSessionNumberFromEmailSubject(String chatTranscriptEmailTitle) {
        String chatAndSessionID = getChatIDAndSessionNumberFromEmailSubject(chatTranscriptEmailTitle);
        return Integer.parseInt(chatAndSessionID.substring(chatAndSessionID.indexOf("(")+1, chatAndSessionID.indexOf(")")));
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

    private List<String> getBareChatHistory(List<ChatHistoryItem> items){
        List<String> expectedMessagesList = new ArrayList<>();

        for (ChatHistoryItem historyItem : items) {
            String expectedChatItem = historyItem.getDisplayMessage().replace("\n", "");
            if(expectedChatItem.contains("Input card")) expectedChatItem =
                    expectedChatItem.replace("Input card", "Let me connect you with a live agent to assist you further. Before I transfer you, please give us some basic info:");
            expectedMessagesList.add(expectedChatItem);
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

    @Then("^(.*) sees correct messages in history details window$")
    public void verifyHistoryInOpenedWindow(String agent){
        SoftAssert soft = new SoftAssert();
        String expectedChatHistoryTime = getExpectedChatStartTimeForChatHistoryInActiveChat();
        List<String> messagesFromChatHistoryDetails = getAgentHomePage(agent).getHistoryDetailsWindow().getAllMessages();
        List<String> expectedChatHistory = getExpectedChatHistoryItems(TimeZone.getDefault().toZoneId(), chatHistoryItems);

        soft.assertEquals(getAgentHomePage(agent).getHistoryDetailsWindow().getUserName(), selectedChatForHistoryTest.get("clientId"),
                "User name is not as expected in opened Chat History Details window");
        soft.assertEquals(getAgentHomePage(agent).getHistoryDetailsWindow().getChatStartDate(), expectedChatHistoryTime,
                "Started date is not as expected in opened Chat History Details window");
        soft.assertEquals(messagesFromChatHistoryDetails, expectedChatHistory,
                "Chat history is not as expected in chat history details (in active chat)");
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
            else getAgentHomeForMainAgent().waitForDeprecated(200);
        }
        Assert.assertTrue(isReduced, "Shown tickets number is not as expected");
    }


    @When("^(.*) deletes first ticket$")
    public void deleteFirstTicket(String agent){
        getAgentHomePage(agent).getCrmTicketContainer().getFirstTicket().clickDeleteButton();
        getAgentHomePage(agent).getDeleteCRMConfirmationPopup().clickDelete();
    }

    @Given("Clear Chat Transcript email inbox")
    public void clearInbox(){
        // using standarttouchgoplan@gmail.com for all chat transcripts
        GmailConnector.loginAndGetInboxFolder(MC2Account.QA_STANDARD_ACCOUNT.getEmail(), MC2Account.QA_STANDARD_ACCOUNT.getPass());
        CheckEmail.clearEmailInbox();
    }

    @Given("Set Chat Transcript attribute to (.*) for (.*) tenant")
    public void setConfigAttributeValueTo(String value, String tenantOrgName){
        if (Tenants.getTenantUnderTestOrgName() == null)  // should it be applied?
            Tenants.setTenantUnderTestOrgName(tenantOrgName);
        // Adding "standarttouchgoplan@gmail.com" email from MC2 accounts for chat transcript emails
        ApiHelper.updateTenantConfig(tenantOrgName, "supportEmail", "\"" + MC2Account.QA_STANDARD_ACCOUNT.getEmail() + "\"");
        ApiHelper.updateTenantConfig(tenantOrgName, "chatTranscript", "\""+value+"\"");

    }

    @Given("Transfer timeout for (.*) tenant is set to (.*) seconds")
    public void updateAgentInactivityTimeout(String tenantOrgName, String timeout){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        Response resp = ApiHelper.updateTenantConfig(tenantOrgName, "agentInactivityTimeoutSec", timeout);
        Assert.assertEquals(resp.statusCode(), 200,
                "Changing agentInactivityTimeoutSec was not successful for '"+Tenants.getTenantUnderTestName()+"' tenant \n" +
        "Response: " + resp.getBody().asString());
    }

    private String getExpectedChatStartTimeForChatHistoryInActiveChat(){
        ZoneId zoneId =  TimeZone.getDefault().toZoneId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        LocalDateTime chatTimeFromBackendUTC = LocalDateTime.parse((String) selectedChatForHistoryTest.get("startedDate"), formatter);
        return chatTimeFromBackendUTC.atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(zoneId).toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("d MMM yyyy | HH:mm"));
    }

    private Customer360PersonalInfo getCustomer360Info(String clientFrom){
        String clientId = "";
        String integrationType = "";
        switch (clientFrom){
            case "fb dm":
                Map chatInfo = (Map) ApiHelper.getActiveChatsByAgent("main").getBody().jsonPath().getList("content")
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
                clientId = getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance());
                integrationType = "TOUCH";
                break;
        }
        return  ApiHelper.getCustomer360PersonalInfo(Tenants.getTenantUnderTestOrgName(),
                clientId, integrationType);
    }

    public boolean isRequiredPhoneNumberDisplayed(String agent, String isPhoneNumberRequired){
        String phone = getAgentHomePage(agent).getCustomer360Container().getPhoneNumber();
        if (isPhoneNumberRequired.equalsIgnoreCase("no"))
            return phone.equalsIgnoreCase("unknown");
        else
            return !phone.equalsIgnoreCase("unknown");
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
        List<String> tags= ApiHelper.getAllTags();
        List<String> tagsInCRM = getAgentHomeForMainAgent().getAgentFeedbackWindow().getTags();
        Assert.assertTrue(tagsInCRM.equals(tags),
                " CRM ticket 'Tags' does not match created on the backend \n");
    }

    @Then("^Agent can search tag and select tag, selected tag added in tags field$")
    public void agentCanSearchTagAndSelectTag() {
        SoftAssert soft = new SoftAssert();
        List<String> tags= ApiHelper.getAllTags();
        String randomTag= tags.get((int)(Math.random() * tags.size()));
        getAgentHomeForMainAgent().getAgentFeedbackWindow().typeTags(randomTag);
        List<String> tagsInCRM = getAgentHomeForMainAgent().getAgentFeedbackWindow().getTags();
        getAgentHomeForMainAgent().getAgentFeedbackWindow().selectTagInSearch();
        List<String> chosenTags = getAgentHomeForMainAgent().getAgentFeedbackWindow().getChosenTags();
        soft.assertTrue(tagsInCRM.contains(randomTag),
                "CRM ticket 'Tags' does not match in search \n");
        soft.assertTrue(chosenTags.contains(randomTag),
                "CRM ticket: selected 'Tag' does not match (or was not added) into the input field for Tags \n");
        soft.assertAll();
    }

    @Then("^(.*) receives 'pin' error message$")
    public void agentReceivesErrorMessage(String agent) {
        getAgentHomePage(agent).isPinErrorMassageShown(agent);
    }


    @Given("^Create (.*) chat via API$")
    public void createChatViaAPI(String chatOrigin){
        switch (chatOrigin){
            case "fb dm message":
                ApiHelper.createFBChat(socialaccounts.FacebookPages.getFBPageFromCurrentEnvByTenantOrgName(Tenants.getTenantUnderTestOrgName()).getFBPageId(),
                        socialaccounts.FacebookUsers.TOM_SMITH.getFBUserIDMsg(), "to agent message");
                socialaccounts.FacebookUsers.setLoggedInUser(FacebookUsers.TOM_SMITH);

        }
    }

    @When("^(.*) click on 'Transfer' chat$")
    public void agentClickOnTransferChat(String agent) {
        getAgentHomeForMainAgent().getChatHeader().clickTransferButton(agent);
    }


    @And("^Header in chat box displayed the icon for channel from which the user is chatting$")
    public void headerInChatBoxDisplayedTheIconForChannelFromWhichTheUserIsChatting() {
        Assert.assertTrue(getAgentHomeForMainAgent().getChatHeader().isValidChannelImg(),
                "Icon for channel in chat header as not expected");
    }

    @And("^Time stamp displayed in 24 hours format$")
    public void timeStampDisplayedInHoursFormat() {
        Assert.assertTrue(getAgentHomeForMainAgent().getChatHeader().isValidTimeStamp(),
                "Time stamp in chat header as not expected");
    }

    @And("^Header in chat box displayed \"chatting to \"customer name\"\"$")
    public void headerInChatBoxDisplayedCustomerName() {
        Assert.assertEquals(getAgentHomeForMainAgent().getChatHeader().getTextHeader(),
                "chatting to " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()),
                "Header in chat header as not expected( do not contain \"chatting to \" or 'customer name'");

    }

    @Then("^Transfer chat pop up appears$")
    public void transferChatPopUpAppears() {
        Assert.assertTrue(getAgentHomeForMainAgent().getTransferChatWindow().isTransferChatShown(),"Transfer chat pop up is not appears");
    }

    @When("^Select 'Transfer to' drop down$")
    public void selectTransferToDropDown() {
        getAgentHomeForMainAgent().getTransferChatWindow().openDropDownAgent();
    }

    @When("^(.*) select an agent in 'Transfer to' drop down$")
    public void selectAgentTransferToDropDown(String agent) {
        getAgentHomePage(agent).getTransferChatWindow().selectDropDownAgent(agent);
    }

    @Then("^Agent sees '(.*)'$")
    public void agentSeesCurrentlyThereSNoAgentsAvailable(String message) {
        Assert.assertEquals(getAgentHomeForMainAgent().getTransferChatWindow().getTextDropDownMessage(), message, "message in drop down menu not as expected");
    }

    @When("^Click on 'Transfer' button in pop-up$")
    public void clickOnTransferButtonInPopUp() {
        getAgentHomeForMainAgent().getTransferChatWindow().clickTransferChatButton();
    }

    @When("^Complete 'Note' field$")
    public void sentNotesTransferChatPopup() {
        getAgentHomeForMainAgent().getTransferChatWindow().sentNote();
    }

    @Then("^'Transfer to' fields highlighted red color$")
    public void transferToFieldsHighlightedRedColor() {
        Assert.assertEquals(getAgentHomeForMainAgent().getTransferChatWindow().getDropDownColor(),"rgb(242, 105, 33)",
                "Drop down: not expected border color");
    }

    @Then("^'Note' fields highlighted red color$")
    public void noteFieldsHighlightedRedColor() {
        Assert.assertEquals(getAgentHomeForMainAgent().getTransferChatWindow().getNoteInputColor(),"rgb(242, 105, 33)",
                "Note: not expected border color");
    }

    @Given("^(.*) receives a few conversation requests$")
    public void create2DotControlChats(String agent){
        SoftAssert soft = new SoftAssert();
        DotControlSteps dotControlSteps = new DotControlSteps();
        dotControlSteps.createIntegration(Tenants.getTenantUnderTestOrgName());
        createdChatsViaDotControl.add(dotControlSteps.sendMessageToDotControl("connect to agent'"));
        DotControlSteps.cleanUPDotControlRequestMessage();
        createdChatsViaDotControl.add(dotControlSteps.sendMessageToDotControl("chat to support"));

        soft.assertTrue(getLeftMenu(agent)
                        .isNewConversationRequestFromSocialIsShown(
                                createdChatsViaDotControl.get(0).getClientId(),20, agent),
                "There is no new conversation request on Agent Desk (Client name: "+createdChatsViaDotControl.get(0).getClientId()+")");
        soft.assertTrue(getLeftMenu(agent)
                        .isNewConversationRequestFromSocialIsShown(
                                createdChatsViaDotControl.get(1).getClientId(),20, agent),
                "There is no new conversation request on Agent Desk (Client name: "+createdChatsViaDotControl.get(1).getClientId()+")");
        soft.assertAll();
    }

    @When("^(.*) click 'Cancel transfer' button$")
    public void cancelTransferChat(String agent){
        getAgentHomePage(agent).getChatHeader().clickCancelTransferButton(agent);
    }

    @Then("^(.*) has not see incoming transfer pop-up$")
    public void secondAgentHasNotSeeIncomingTransferPopUp(String agent) {
           Assert.assertTrue(
            getAgentHomePage(agent).getIncomingTransferWindow().isTransferWindowHeaderNotShown(agent),
                   "Transfer chat header is shown for "+ agent + " agent");

    }

    @When("^(.*) click on 'headphones' icon and see (\\d+) available agents$")
    public void firstAgentClickOnHeadphonesIconAndSeeAvailableAgents(String agent,int availableAgent) {
        getAgentHomePage(agent).getPageHeader().clickHeadPhonesButton(agent);
        List<String> availableAgents = getAgentHomePage(agent).getPageHeader().getAvailableAgents();
        Assert.assertEquals(
                availableAgents.size(), availableAgent,
                "Quantity of available agents not as expected");
        if (availableAgent==1){
            getAgentHomePage(agent).getPageHeader().clickIconWithInitials();
            String agentName =  getAgentHomePage(agent).getPageHeader().getAgentName();
            Assert.assertFalse(
                    availableAgents.contains(agentName),
                    "Unavailable agent in list of available agents");
        }


    }

    @And("^(.*) transfers overnight ticket$")
    public void agentTransfersOvernightTicket(String agent) {
        getAgentHomePage(agent).getChatHeader().clickTransferButton(agent);
        secondAgentName = getAgentHomePage(agent).getTransferChatWindow().transferChat(agent);
    }
}
