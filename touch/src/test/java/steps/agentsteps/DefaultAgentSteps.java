package steps.agentsteps;

import agentpages.uielements.FilterMenu;
import agentpages.uielements.Profile;
import apihelper.ApiHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import apihelper.ApiHelperSupportHours;
import datamanager.Tenants;
import datamanager.UserPersonalInfo;
import datamanager.jacksonschemas.ChatPreferenceSettings;
import datamanager.jacksonschemas.chatextension.ChatExtension;
import datamanager.jacksonschemas.chatusers.UserInfo;
import datamanager.jacksonschemas.dotcontrol.InitContext;
import datamanager.jacksonschemas.supportHours.SupportHoursMapping;
import dbmanager.DBConnector;
import driverfactory.DriverFactory;
import drivermanager.ConfigManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import socialaccounts.FacebookUsers;
import socialaccounts.TwitterUsers;
import steps.ORCASteps;
import steps.dotcontrol.DotControlSteps;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DefaultAgentSteps extends AbstractAgentSteps {

    private static final ThreadLocal<Map<String, Boolean>> PRE_TEST_FEATURE_STATUS = new ThreadLocal<>();
    private static final ThreadLocal<Map<String, Boolean>> TEST_FEATURE_STATUS_CHANGES = new ThreadLocal<>();
    private static UserPersonalInfo userPersonalInfoForUpdating;
    public Profile profile;

    @When("Save clientID value for (.*) user")
    public void saveClientIDValues(String userFrom){
        saveClientIDValue(userFrom);
    }

    @Given("^(.*) has no active chats$")
    public void closeActiveChats(String agent){
        ApiHelper.closeActiveChats(agent);
        getAgentHomePage(agent).getLeftMenuWithChats().waitForAllChatsToDisappear(4);
    }

    @Given("^(.*) tenant feature is set to (.*) for (.*)$")
    public void setFeatureStatus(String feature, String status, String tenantOrgName){
        ChatPreferenceSettings chatPreferenceSettings = new ChatPreferenceSettings();
        chatPreferenceSettings.setFeatureStatus(feature, status);
        ApiHelper.updateFeatureStatus(chatPreferenceSettings);
    }

    @Given("(.*) creates (.*) tenant extension with label (.*) and name (.*)$")
    public void createExtensionForTenant(String agent, String extensionType, String label, Optional name){
        String extensionBody;
        ObjectMapper mapper = new ObjectMapper();
        try {
            extensionBody = mapper.writeValueAsString(Arrays.asList(new ChatExtension(label, name, extensionType)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        ApiHelper.createExtensions(extensionBody);
    }

    @Then("^On backand (.*) tenant feature status is set to (.*) for (.*)$")
    public void isFeatureStatusSet(String feature, boolean status, String tenantOrgName){
        Assert.assertEquals(ApiHelper.getFeatureStatus(tenantOrgName, feature),status,
                feature + " feature is not expected");
    }

    @And("^(.*) click Whatsapp message icon button on the top bar$")
    public void clickWhatsappIcon(String agent) {
        getPageHeader(agent).clickOnWhatsapp();
    }

    @And("^(.*) select \"(.*)\" in Chanel Template$")
    public void selectChanelFilter(String agent, String name) {
        getAgentHomePage(agent).getHSMForm().selectTemplate(name);
    }

    @And("^(.*) insert the Variable type \"(.*)\" for template$")
    public void insertVarible(String agent, String variable) {
        getAgentHomePage(agent).getHSMForm().insertVariableForWhatsapp(variable);
    }

    @And("(.*) click send button in HSM form$")
    public void clickSendHSMButton(String agent) {
        getAgentHomePage(agent).getHSMForm().clickSendButton();
    }

    @And("^(.*) fill the customer contact number$")
    public void sendWhatsApp(String agent) {
        getAgentHomePage(agent).getHSMForm().setWAPhoneNumber(ORCASteps.orcaMessageCallBody.get().getSourceId());
    }

    @Then("^(.*) has (?:new|old) (.*) (?:request|shown)(?: from (.*) user|)$")
    public void verifyIfAgentReceivesConversationRequest(String agent, String chatType, String integration) {
        if(integration == null) {
            if(ConfigManager.isWebWidget()) {integration = "touch";
            }else {integration = "ORCA";}
        }

        String userName=getUserName(integration);

        boolean isConversationShown = getLeftMenu(agent).isNewConversationIsShown(userName,30);

        Map settingResults = new HashMap<String, Object>();

        if(!isConversationShown){
            settingResults = verifyAndUpdateChatSettings(chatType);
            isConversationShown = getLeftMenu(agent).isNewConversationIsShown(userName,30);
        }

        Assert.assertTrue(isConversationShown,
                "There is no new conversation request on Agent Desk (Client ID: "+userName+")\n" +
//                        "Number of logged in agents: " + ApiHelper.getNumberOfLoggedInAgents() +"\n" +
                        "sessionsCapacity: " + settingResults.get("sessionCapacityUpdate") + " and was: "+ settingResults.get("sessionCapacity") +" before updating \n" +
                        "Support hours: " + settingResults.get("supportHoursUpdated") + "\n" +
                        "and was:" +settingResults.get("supportHours") +" before changing\n"
        );
    }


    private Map<String, Object> verifyAndUpdateChatSettings(String type) {
        Map<String, Object> results = new HashMap<String, Object>();

        int sessionCapacity = 0;
        List<SupportHoursMapping> supportHours = null;
        List<SupportHoursMapping> supportHoursUpdated = null;
        sessionCapacity = ApiHelper.getTenantInfo(Tenants.getTenantUnderTestOrgName()).jsonPath().get("sessionsCapacity");
        results.put("sessionCapacity", sessionCapacity);

        if (sessionCapacity < 50) {
            results.put("sessionCapacityUpdate", ApiHelper.updateSessionCapacity(Tenants.getTenantUnderTestOrgName(), 50).jsonPath().get("sessionsCapacity"));
        } else {
            results.put("sessionCapacityUpdate", 50);
        }

        supportHours = ApiHelperSupportHours.getSupportDaysAndHoursForMainAgent(Tenants.getTenantUnderTestOrgName()).getAgentMapping();
        results.put("supportHours", supportHours);

        if (!type.equalsIgnoreCase("ticket")) {
            if (supportHours.size() < 7) {
                ApiHelperSupportHours.setSupportDaysAndHours(Tenants.getTenantUnderTestOrgName(), "all week", "00:00", "23:59");
                supportHoursUpdated = ApiHelperSupportHours.getSupportDaysAndHoursForMainAgent(Tenants.getTenantUnderTestOrgName()).getAgentMapping();
                results.put("supportHoursUpdated", supportHoursUpdated);
            }else {results.put("supportHoursUpdated", "were not updated because \"All week\" was set");}
        }else {results.put("supportHoursUpdated", "were not updated because it is ticket");}

        return results;
    }

    @Then("^(.*) button is (.+) on Chat header$")
    public void isButtonEnabled(String button, String state){
        if (state.equalsIgnoreCase("disabled"))
            Assert.assertTrue(getAgentHomePage("main").getChatHeader().isButtonDisabled(button));
        else if (state.equalsIgnoreCase("enabled"))
            Assert.assertTrue(getAgentHomePage("main").getChatHeader().isButtonEnabled(button));
    }

    @Then("^(.*) button hidden from the Chat header$")
    public void checkIfButtonHidden(String button){
            Assert.assertTrue( getAgentHomePage("main").getChatHeader().isButtonDisabled(button),
                    "'" + button + "' button is displayed");
    }

    @Then("^(.*) has new conversation request within (.*) seconds$")
    public void verifyIfAgentReceivesConversationRequest(String agent, int timeout) {
        Assert.assertTrue(getLeftMenu(agent).isNewWebWidgetRequestIsShown(timeout),
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
        softAssert.assertEquals(getAgentHomePage(agent).getChatForm().getOvernightTicketMessage(), "This is an unattended chat that has been assigned to you.",
                "Message that this is Overnight ticket is not shown");
        softAssert.assertTrue(getAgentHomePage(agent).getChatForm().isSendEmailForOvernightTicketMessageShown(),
                "'Send email' button is not enabled");
        softAssert.assertAll();
    }

    @Then("Conversation area contains (.*) to user message")
    public void verifyOutOfSupportMessageShownOnChatdesk(String message){
        String userMessage = message;
        if(message.contains("out_of_support_hours")) {
            userMessage = ApiHelper.getAutoResponderMessageText(message);
        }
        Assert.assertTrue(getAgentHomePage("main agent").getChatBody().isToUserMessageShown(userMessage),
                "Expected to user message '"+userMessage+"' should be shown in chatdesk");
    }

    @Then("^(.*) select \"(.*)\" left menu option$")
    public void selectFilterOption(String agent,String option){
        getLeftMenu(agent).selectChatsMenu(option);
        getLeftMenu(agent).waitForConnectingDisappear(5,10);
    }

    @Then("^(.*) clicks close filter button$")
    public void clickCloseFilterButton(String agent){
        getLeftMenu(agent).clickCloseButton();
    }

    @Then("^(.*) opens filter menu$")
    public void openFilterMenuAgentDesk(String agent){
        getLeftMenu(agent).openFilterMenu();
    }

    @When("^(.*) filter Live Chants with (.*) channel, (.*) sentiment and flagged is (.*)$")
    public void setLiveChatsFilter(String agent, String channel, String sentiment, boolean flagged){
        getLeftMenu(agent).applyTicketsFilters(channel.trim(), sentiment.trim(), flagged);
    }

    @When("^(.*) click on filter button$")
    public void setLiveChatsFilter(String agent){
        getLeftMenu(agent).openFilterMenu();
    }

    @Then ("^(.*) see channel, sentiment(?: and flagged|, flagged and dates) as filter options for (.*)$")
    public void verifyFilterOptions(String agent, String chatType){
        FilterMenu filterMenu = getLeftMenu(agent).getFilterMenu();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(filterMenu.expandChannels().getDropdownOptions(),  Arrays.asList("Webchat", "Facebook", "Twitter", "WhatsApp", "Apple Business Chat"),
                "Channel dropdown has incorrect options");
        filterMenu.expandChannels();
        softAssert.assertEquals(filterMenu.expandSentiment().getDropdownOptions(), Arrays.asList("Positive", "Neutral", "Negative"),
                "Sentiment dropdown has incorrect options");
        filterMenu.expandSentiment();
        if(!chatType.contains("live")){
            softAssert.assertTrue(filterMenu.isStartDateIsPresent(),"Start day option should be present in " + chatType +" filters");
            softAssert.assertTrue(filterMenu.isEndDateIsPresent(),"End day option should be present in " + chatType +" filters");
        }
        softAssert.assertAll();
    }

    @When("(.*) remove Chat Filter$")
    public void removeFilter(String agent){
        getLeftMenu(agent).removeFilter();
    }

//    @Then("^(.*) has new (.*) request from (.*) user$")
//    public void verifyAgentHasRequestFormSocialUser(String agent, String chatType, String social){
//        String userName=getUserName(social);
//        boolean isConversationShown = getLeftMenu(agent).isNewConversationIsShown(userName,25);
//        Map settingResults = new HashMap<String, Object>();
//
//        if(!isConversationShown){
//            settingResults = verifyAndUpdateChatSettings(chatType);
//            isConversationShown = getLeftMenu(agent).isNewConversationIsShown(userName,25);
//        }
//
//        Assert.assertTrue(isConversationShown,
//                "There is no new conversation request on Agent Desk (Client ID: "+userName+")\n" +
//                        "Number of logged in agents: " + ApiHelper.getNumberOfLoggedInAgents() +"\n" +
//                        "sessionsCapacity: " + settingResults.get("sessionCapacityUpdate") + " and was: "+ settingResults.get("sessionCapacity") +"before updating \n" +
//                        "Support hours: " + settingResults.get("supportHoursUpdated") + "\n" +
//                        "and was:" +settingResults.get("supportHours") +" before changing\n"
//        );
//    }

//    @Then("^(.*) has new conversation request from (.*) user through (.*) channel$")
//    public void verifyAgentHasRequestFormSocialUser(String agent, String social, String channel){
//        String userName=getUserName(social);
//        Assert.assertTrue(getLeftMenu(agent).isNewConversationRequestFromSocialShownByChannel(userName, channel,20),
//                "There is no new conversation request on Agent Desk (Client ID: "+getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance())+")");
//    }
    @Then("^(.*) should not see from user chat in agent desk$")
    public void verifyConversationRemovedFromChatDesk(String agent){
        // ToDo: Update after clarifying timeout in System timeouts
        String userName = getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance());
        Assert.assertTrue(getLeftMenu(agent).isConversationRequestIsRemoved(20, userName),
                "Conversation request is not removed from Agent Desk (Client ID: "+getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance())+")"
        );
    }
    @Then("^(.*) should not see from user chat in agent desk from (.*)$")
    public void verifyDotControllConversationRemovedFromChatDesk(String agent, String social ){
        String userName = getUserName(social);
        Assert.assertTrue(getLeftMenu(agent).isConversationRequestIsRemoved(20, userName),
                "Conversation request is not removed from Agent Desk (Client ID: "+userName+")"
        );
    }
    @When("^(.*) click on (?:new|last opened) conversation request from (.*)$")
    public void acceptUserFromSocialConversation(String agent, String socialChannel) {
        if(!ConfigManager.isWebWidget() && socialChannel.equalsIgnoreCase("touch")){socialChannel="orca";}
        getLeftMenu(agent).openNewFromSocialConversationRequest(getUserName(socialChannel));
    }

    @When("^(.*) click on new conversation$")
    public void acceptUserConversation(String ordinalAgentNumber) {
        getLeftMenu(ordinalAgentNumber).openNewConversationRequestByAgent();
    }

    @When("^(.*) changes status to: (.*)$")
    public void changeAgentStatus(String agent, String newStatus){
            getAgentHomePage(agent).getPageHeader().clickIcon();
            getAgentHomePage(agent).getPageHeader().selectStatus(newStatus);
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

                resp = ApiHelperSupportHours.setSupportDaysAndHours(Tenants.getTenantUnderTestOrgName(),
                        currentTimeWithADayShift.getDayOfWeek().toString(),"00:00", "23:59");
                break;
            case "for all week":
                resp = ApiHelperSupportHours.setSupportDaysAndHours(Tenants.getTenantUnderTestOrgName(), "all week",
                        "00:00", "23:59");
                getAgentHomePage("main").waitFor(1500);
                break;
        }
        Assert.assertEquals(resp.statusCode(), 200,
                "Changing support hours was not successful\n" +
                "resp body: " + resp.getBody().asString() + "\n");
    }

    @Then("^Tab with user info has \"(.*)\" header$")
    public void verifyTabHeader(String headerName){
        Assert.assertEquals(getAgentHomeForMainAgent().getSelectedTabHeader(), headerName,
                "Incorrect header of customer selected tab, should be " + headerName);
    }


    @Then("Correct (.*) client details are shown")
    public void verifyClientDetails(String clientFrom){
        UserPersonalInfo userPersonalInfoFromChatdesk = getAgentHomePage("main")
                .getProfile().getActualPersonalInfo();

        Assert.assertEquals(userPersonalInfoFromChatdesk, getUserPersonalInfo(clientFrom),
                "User info is not as expected \n");
    }

    @Then("Correct dotcontrol client details from Init context are shown")
    public void verifyInitContextClientDetails(){
        UserPersonalInfo userPersonalInfoFromChatdesk = getUserProfileContainer("main").getActualPersonalInfo();

        UserPersonalInfo expectedResult = getUserPersonalInfo("dotcontrol");
        InitContext initContext = DotControlSteps.getInitContext();
        expectedResult.setFullName(initContext.getFullName())
                        .setEmail(initContext.getEmail())
                        .setPhone(initContext.getPhone().replace(" ", ""))
        .setChannelUsername("");

        Assert.assertEquals(userPersonalInfoFromChatdesk, expectedResult,
                "User info is not as in init context \n");
    }


    @When("^Click (?:'Edit'|'Save') button in Profile$")
    public void clickEditCustomerView(){
        getAgentHomePage("main").getProfile().clickSaveEditButton();
    }

    @Then("^Wait for (.d*) seconds for Phone Number to be (.*)$")
    public void checkPhoneNumberFieldUpdate(int waitFor, String requiredState) throws InterruptedException{
        Profile profile = new Profile();
        profile.setCurrentDriver(DriverFactory.getDriverForAgent("agent"));

        int waitTimeInMillis = waitFor * 1000;
        long endTime = System.currentTimeMillis() + waitTimeInMillis;

        while (!profile.isPhoneNumberFieldUpdated(requiredState)
                    && System.currentTimeMillis() < endTime) {
            Thread.sleep(200);
        }
    }

    @When("^Wait for (.d*) seconds for Phone Number update$")
    public void waitSomeTimeForBackendUpdatePhone(int waitFor){
        waitSomeTime(waitFor);
    }

    @And("^Wait for (\\d*) second$")
    public void waitSomeTime(int waitFor){
        int waitTimeInMillis = waitFor * 1000;
        sleepFor(waitTimeInMillis);
    }

    @When("Fill in the form with new (.*) user profile info")
    public void updateUserInfo(String customerFrom){
        UserPersonalInfo currentCustomerInfo = getUserPersonalInfo(customerFrom);
        userPersonalInfoForUpdating = currentCustomerInfo.setLocation("Lviv")
                            .setEmail("udated_" + faker.lorem().word()+"@gmail.com")
                            .setPhone(faker.numerify("38093#######")); //"+380931576633");
        if(!(customerFrom.contains("fb")||customerFrom.contains("twitter"))) {
            userPersonalInfoForUpdating.setFullName("AQA Run");
            userPersonalInfoForUpdating.setChannelUsername(userPersonalInfoForUpdating.getFullName());

        }
        getAgentHomePage("main").getProfile().fillFormWithNewDetails(userPersonalInfoForUpdating);

    }

    @When("^(.*) see (.*) phone number added into User profile$")
    public void updatePhoneNumberUserProfile(String agent, String isPhoneNumberRequired){
        Assert.assertTrue(isRequiredPhoneNumberDisplayed(agent, isPhoneNumberRequired));
    }

    @Then("^(.*) button (.*) displayed in User profile$")
    public void checkUserProfilePhoneButtonsVisibility(String buttonName, String isOrNotDisplayed){
        Profile profile = getAgentHomePage("main").getProfile();
        if (isOrNotDisplayed.equalsIgnoreCase("not"))
            Assert.assertFalse(profile.isUserSMSButtonsDisplayed(buttonName), "'" + buttonName + "' button is not displayed");
        else
            Assert.assertTrue(profile.isUserSMSButtonsDisplayed(buttonName), "'" + buttonName + "' button still displayed");
    }

    @Then("^'Verify' and 'Re-send OTP' buttons (.*) displayed in User profile$")
    public void checkUserProfilePhoneVerifyAndReSendButtonsVisibility(String isOrNotDisplayed){
        Profile profile = getAgentHomePage("main").getProfile();
        SoftAssert softAssert = new SoftAssert();
        if (isOrNotDisplayed.contains("not")) {
            softAssert.assertFalse(profile.isUserSMSButtonsDisplayed("Verify"),
                    "'Verify' button is not displayed");
            softAssert.assertFalse(profile.isUserSMSButtonsDisplayed("Re-send OTP"),
                    "'Re-send OTP' button is not displayed");
            softAssert.assertAll();
        }
        else {
            softAssert.assertTrue(profile.isUserSMSButtonsDisplayed("Verify"));
            softAssert.assertTrue(profile.isUserSMSButtonsDisplayed("Re-send OTP"));
            softAssert.assertAll();
        }
    }

    @When("^(.*) phone number for (.*) user$")
    public void changePhoneNumberUserProfile(String changeOrDelete, String customerFrom){
        String phoneNumber = " "; //in case we need to delete phone number
        UserPersonalInfo currentCustomerInfo = getUserPersonalInfo(customerFrom);
        if (changeOrDelete.equalsIgnoreCase("change"))
            phoneNumber = generateUSCellPhoneNumber();

        userPersonalInfoForUpdating = currentCustomerInfo.setPhone(phoneNumber);

        getAgentHomePage("main").getProfile().setPhoneNumber(phoneNumber);
        Assert.assertEquals(currentCustomerInfo.getPhone(), phoneNumber, "Entered phone number is not equal to displayed one");
    }

    @When("Agent click on '(.*)' button in User profile")
    public void clickPhoneActionsButtonsUserProfile(String buttonName){
        getAgentHomeForMainAgent().getProfile().clickPhoneNumberVerificationButton(buttonName);
    }

    @Then("^'Verify phone' window is (.*)$")
    public void verifyPhoneNumberWindowOpened(String isWindowOpen) {
        if (isWindowOpen.equalsIgnoreCase("opened"))
            Assert.assertTrue(getAgentHomeForMainAgent().getVerifyPhoneNumberWindow().isOpened(3),
                    "'Verify phone' window is not opened.");
        else
            Assert.assertTrue(getAgentHomeForMainAgent().getVerifyPhoneNumberWindow().isClosed(),
                    "'Verify phone' window wasn't closed.");
    }

    @Then("User's profile phone number (.*) in 'Verify phone' input field")
    public void phoneNumberForVerifyCheck(String isRequiredToDisplay){
        String phoneNumberInUserProfile = getAgentHomeForMainAgent().getProfile()
                .getPhoneNumber().replaceAll("\\s+", "");
        String phoneNumberInVerifyPopUp = getAgentHomeForMainAgent().getVerifyPhoneNumberWindow()
                .getEnteredPhoneNumber().replaceAll("[\\s-.]", "");
        if (isRequiredToDisplay.contains("not")){
            Assert.assertEquals(phoneNumberInVerifyPopUp, "",
                    "Some phone number displayed in the field");
        }
        else{
            Assert.assertEquals(phoneNumberInUserProfile, phoneNumberInVerifyPopUp,
                    "Phone number in Verify phone window is different from displayed in Customer Profile");
        }
    }

    @When("Agent click on (.*) button on 'Verify phone' window")
    public void closeVerifyPhonePopUp(String buttonName){
        getAgentHomeForMainAgent().getVerifyPhoneNumberWindow().sendOrCancelClick(buttonName);
    }

    @When("Agent send OTP message with API")
    public void sendOTPWithAPI(){
        UserInfo userInfo = ApiHelper.getUserProfile("webchat", getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()));
        userInfo.setOtpSent(true);
        ApiHelper.updateUserProfile(userInfo);
//        String linkedClientProfileId = DBConnector.getLinkedClientProfileID(ConfigManager.getEnv(), getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()));
//        DBConnector.addPhoneAndOTPStatusIntoDB(ConfigManager.getEnv(), linkedClientProfileId);
    }

    @Then("'Verified' label become (.*)")
    public void checkVerifiedLabel(String isVisible) {
        if (isVisible.equalsIgnoreCase("visible"))
            Assert.assertTrue(getAgentHomeForMainAgent().getProfile().isVerifiedLabelDisplayed(), "Verified label is not displayed");
        else
            Assert.assertTrue(getAgentHomeForMainAgent().getProfile().isVerifiedLabelHidden(), "Verified label remains displayed");
    }

    @Then("SMS client-profile added into DB")
    public void checkSMSProfileCreating(){
        String phone = getAgentHomeForMainAgent().getProfile().getPhoneNumber().replaceAll("\\+", "").replaceAll(" ", "");
        UserInfo userInfo = ApiHelper.getUserProfile("webchat", getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()));
        SoftAssert soft =new SoftAssert();
        soft.assertTrue(!userInfo.getSms().getIntegrationId().isEmpty(), "Sms client integration was not added");
        soft.assertEquals(userInfo.getSms().getPhoneNumber(), phone, "Incorrect phone is set into Sms integration");
        soft.assertAll();
    }

    @Then("Chat separator with OTP code and 'I have just sent...' message with user phone number are displayed")
    public void chatSeparatorCheck(){
        String phone = getAgentHomeForMainAgent().getProfile().getPhoneNumber().replaceAll(" ", "");
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
        Assert.assertEquals(getUserPersonalInfo(customerFrom), userPersonalInfoForUpdating,
                "User Personal info is not updated on backend after making changes on chatdesk. \n");
    }

    @Then("^New info is shown in left menu with chats$")
    public void checkUpdatingUserInfoInLeftMenu(){
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getLeftMenu("main").getActiveChatUserName(), userPersonalInfoForUpdating.getFullName().trim(),
                "Full user name is not updated in left menu with chats after updating User Personal info \n");
        soft.assertEquals(getLeftMenu("main").getActiveChatLocation(), userPersonalInfoForUpdating.getLocation(),
                "Location is not updated in left menu with chats after updating User Personal info \n");
        soft.assertAll();
    }

    @And("^Empty image is not shown for chat with (.*) user$")
    public void verifyEmptyImgNotShown(String customerFrom){
        String user = "";
        if(customerFrom.equalsIgnoreCase("facebook")) user = socialaccounts.FacebookUsers.getLoggedInUserName();
        Assert.assertTrue(getLeftMenu("main").isProfileIconNotShown(),
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
        Assert.assertTrue(getAgentHomeForMainAgent().getChatHeader().getChatHeaderText().contains(userPersonalInfoForUpdating.getFullName().trim()),
                "Updated customer name is not shown in chat header");
    }

    @Then("^Tenant photo is shown on chatdesk$")
    public void verifyTenantImageIsShownOnChatdesk(){
        Assert.assertTrue(getAgentHomePage("main").getPageHeader().isTenantImageShown(),
                "Tenant image is not shown on chatdesk");
    }

    private UserPersonalInfo getUserPersonalInfo(String clientFrom){
        String clientId;
        String integrationType;
        switch (clientFrom){
            case "fb dm":
                Map chatInfo = (Map) ApiHelper.getActiveChatsByAgent("main").getBody().jsonPath().getList("content")
                                        .stream()
                                        .filter(e -> ((Map)
                                                            ((Map) e).get("client")
                                                     ).get("type")
                                                .equals("FACEBOOK"))
                                        .findFirst()
                                        .orElseThrow(() -> new AssertionError("Cannot get active FACEBOOK type chat"));
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
        return  ApiHelper.getUserPersonalInfo(Tenants.getTenantUnderTestOrgName(),
                clientId, integrationType);
    }

    public boolean isRequiredPhoneNumberDisplayed(String agent, String isPhoneNumberRequired){
        String phone = getAgentHomePage(agent).getProfile().getPhoneNumber();
        if (isPhoneNumberRequired.equalsIgnoreCase("no"))
            return phone.equalsIgnoreCase("");
        else
            return !phone.equalsIgnoreCase("");
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

    @And("^Header in chat box displayed the icon for channel from which the user is chatting$")
    public void headerInChatBoxDisplayedTheIconForChannelFromWhichTheUserIsChatting() {
        Assert.assertTrue(getAgentHomeForMainAgent().getChatHeader().isValidChannelImg("headerChannel"),
                "Icon for channel in chat header as not expected");
    }

    @And("^Time stamp displayed in 24 hours format$")
    public void timeStampDisplayedInHoursFormat() {
        Map<String, String> sessionDetails = DBConnector.getSessionDetailsByClientID(ConfigManager.getEnv()
                ,getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()));
        String startedDate = sessionDetails.get("startedDate");
        int numberOfMillis = startedDate.split("\\.")[1].length();
        String dateFormat = "yyyy-MM-dd HH:mm:ss." + StringUtils.repeat("S", numberOfMillis);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        LocalDateTime formatDateTime = LocalDateTime.parse(startedDate, formatter).atZone(ZoneId.of("UTC")).withZoneSameInstant(TimeZone.getDefault().toZoneId()).toLocalDateTime();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm|");
        Assert.assertEquals(getAgentHomeForMainAgent().getChatHeader().getTimeStamp(), formatDateTime.format(formatter2),
                "Time stamp in chat header as not expected");
    }

    @And("^Header in chat box displayed customer name$")
    public void headerInChatBoxDisplayedCustomerName() {
        Assert.assertEquals(getAgentHomeForMainAgent().getChatHeader().getTextHeader(),
                getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()),
            "Header in chat header as not expected( do not contain \"chatting to \" or 'customer name'");

}

    @When("^(.*) click on 'headphones' icon and see (\\d+) available agents$")
    public void firstAgentClickOnHeadphonesIconAndSeeAvailableAgents(String agent,int availableAgent) {
        getAgentHomePage(agent).getPageHeader().clickHeadPhonesButton();
        Assert.assertTrue(
                getAgentHomePage(agent).getPageHeader().isExpectedNumbersShown(availableAgent, 8),
                "Quantity of available agents not as expected ("+ availableAgent+" expected)");
        if (availableAgent==1){
            getAgentHomePage(agent).getPageHeader().clickIcon();
            String agentName =  getAgentHomePage(agent).getPageHeader().getAgentName();
            Assert.assertFalse(
                    getAgentHomePage(agent).getPageHeader().getAvailableAgents().contains(agentName),
                    "Unavailable agent in list of available agents");
        }
    }

    @And("^(.*) search ticket with a customer name \"([^\"]*)\"$")
    public void agentTypesACustomerNameBlaBlaOnTheSearchField(String agent, String userName) {
        getAgentHomePage(agent).getLeftMenuWithChats().searchTicket(userName);
    }

    @Then("^(.*) receives an error message \"([^\"]*)\"$")
    public void agentReceivesAnErrorMessage(String agent, String errorMessage) {
        Assert.assertEquals(getAgentHomePage(agent).getLeftMenuWithChats().getNoResultsFoundMessage(), errorMessage,
                "Wrong no results found error message found");
    }

    @And("^(.*) click on search button in left menu$")
    public void agentClickOnSearchButtonInLeftMenu(String agent) {
        getAgentHomePage(agent).getLeftMenuWithChats().clickOnSearchButton();
    }

    @And("^(.*) types a customer name \"([^\"]*)\" on the search field$")
    public void agentTypesACustomerNameOnTheSearchField(String agent, String userName) {
        getAgentHomePage(agent).getLeftMenuWithChats().inputUserNameIntoSearch(userName);
    }

    @And("^(.*) get \"Cannot close chat\" notification modal open$")
    public void notifyCannotCloseChat(String agent) {
        String ActualClosedChatWindowHeader = getAgentHomePage(agent).getC2pMoveToPendingMessage();
        Assert.assertEquals(ActualClosedChatWindowHeader,"Sorry, this chat can't be closed as it has 1 or more active payment(s). " +
                "Would you like to move it to the pending tab where it will close automatically?");
    }

    @And("^(.*) click on option \"Move to Pending\" from notification$")
    public void agentClickOnMoveToPendingOption(String agent) {
        getAgentHomePage(agent).clickMoveToPendingButton();
    }

    @When("^Agent click on Edit button in User profile$")
    public void agentClickOnEditButtonInUserProfile() {
        getAgentHomeForMainAgent().getProfile().clickEditButton();
    }

    @And("^Enter (.*) in the phone number field$")
    public void enterInThePhoneNumberField(String number) {
        getAgentHomeForMainAgent().getProfile().setPhoneNumber(number);
    }

    @And("Agent click Save button in User profile")
    public void agentClickSaveButtonInUserProfile() {
        getAgentHomeForMainAgent().getProfile().clickSaveEditButton();
    }

    @Then("Not verified label is displayed")
    public void notVerifiedLabelIsDisplayed() {
        Assert.assertTrue(getAgentHomeForMainAgent().getProfile().isNotVerifiedLabelDisplayed(), "NotVerified label is not displayed");
    }

    @Given("^(.*) is logged out from the Agent Desk$")
    public void logOutAgentDesk(String agent) {
        getAgentHomePage(agent).getPageHeader().logOut();

        Assert.assertTrue(getAgentHomePage(agent).isLoginDialogShown(),
                "Log out from agent desk not successful");
    }

}