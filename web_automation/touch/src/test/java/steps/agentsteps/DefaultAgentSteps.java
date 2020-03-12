package steps.agentsteps;

import agentpages.uielements.*;
import apihelper.ApiHelper;
import datamanager.jacksonschemas.SupportHoursItem;
import driverfactory.DriverFactory;
import drivermanager.ConfigManager;
import mc2api.auth.PortalAuthToken;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.*;
import datamanager.jacksonschemas.dotcontrol.InitContext;
import dbmanager.DBConnector;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import socialaccounts.FacebookUsers;
import socialaccounts.TwitterUsers;
import steps.portalsteps.BasePortalSteps;
import steps.dotcontrol.DotControlSteps;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DefaultAgentSteps extends AbstractAgentSteps {

    private static ThreadLocal<Map<String, Boolean>> PRE_TEST_FEATURE_STATUS = new ThreadLocal<>();
    private static ThreadLocal<Map<String, Boolean>> TEST_FEATURE_STATUS_CHANGES = new ThreadLocal<>();
    private static Customer360PersonalInfo customer360InfoForUpdating;

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

    @When("Save clientID value for (.*) user")
    public void saveClientIDValues(String userFrom){
        saveClientIDValue(userFrom);
    }

    @Given("^(.*) has no active chats$")
    public void closeActiveChats(String agent){
        ApiHelper.closeActiveChats(agent);
        getAgentHomePage(agent).getLeftMenuWithChats().waitForAllChatsToDisappear(4);
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

    @Then("^(.*) has (?:new|old) conversation (?:request|shown)$")
    public void verifyIfAgentReceivesConversationRequest(String agent) {
        boolean isConversationShown = getLeftMenu(agent).isNewConversationRequestIsShown(20);
        int sessionCapacity = 0;
        if(!isConversationShown){
            sessionCapacity = ApiHelper.getTenantInfo(Tenants.getTenantUnderTestOrgName()).jsonPath().get("sessionsCapacity");
            if (sessionCapacity==0) ApiHelper.updateSessionCapacity(Tenants.getTenantUnderTestOrgName(), 50);
        }
        List<SupportHoursItem> supportHours = ApiHelper.getAgentSupportDaysAndHours(Tenants.getTenantUnderTestOrgName());
        List<SupportHoursItem> supportHoursUpdated = null;
        if(supportHours.size()<7){
            ApiHelper.setAgentSupportDaysAndHours(Tenants.getTenantUnderTestOrgName(), "all week", "00:00", "23:59");
            supportHoursUpdated = ApiHelper.getAgentSupportDaysAndHours(Tenants.getTenantUnderTestOrgName());
        }

        Assert.assertTrue(isConversationShown,
                "There is no new conversation request on Agent Desk (Client ID: "+getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance())+")\n" +
                        "Number of logged in agents: " + ApiHelper.getNumberOfLoggedInAgents() +"\n" +
                        "sessionsCapacity: " + ApiHelper.getTenantInfo(Tenants.getTenantUnderTestOrgName()).jsonPath().get("sessionsCapacity") + " and was: "+ sessionCapacity +"before updating \n" +
                        "Support hours: " + supportHoursUpdated + "\n" +
                        "and was:" +supportHours +" before changing\n"
        );
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
        Assert.assertTrue(getLeftMenu(agent).isNewConversationRequestIsShown(timeout),
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
            userMessage = ApiHelper.getTafMessages().stream().filter(e -> e.getId().equals(message)).findFirst().get().getText();
        }
        Assert.assertTrue(getAgentHomePage("main agent").getChatBody().isToUserMessageShown(userMessage),
                "Expected to user message '"+userMessage+"' is not shown in chatdesk");
    }

    @Then("^(.*) select \"(.*)\" left menu option$")
    public void selectFilterOption(String agent,String option){
        getLeftMenu(agent).selectChatsMenu(option);
    }

    @Then("^(.*) has new conversation request from (.*) user$")
    public void verifyAgentHasRequestFormSocialUser(String agent, String social){
        String userName=null;
        if (social.equalsIgnoreCase("twitter")) userName = socialaccounts.TwitterUsers.getLoggedInUserName();
        if(social.equalsIgnoreCase("facebook")) userName = socialaccounts.FacebookUsers.getLoggedInUserName();
        if(social.equalsIgnoreCase("dotcontrol")) userName = DotControlSteps.getClient();
        Assert.assertTrue(getLeftMenu(agent).isNewConversationRequestFromSocialIsShown(userName,40),
                                "There is no new conversation request on Agent Desk (Client name: "+userName+")");
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
        getLeftMenu(ordinalAgentNumber).openNewConversationRequestByAgent();
    }

    @When("^(.*) changes status to: (.*)$")
    public void changeAgentStatus(String agent, String newStatus){
        try {
            getAgentHomePage(agent).getPageHeader().clickIcon();
            getAgentHomePage(agent).getPageHeader().selectStatus(newStatus);
            getAgentHomePage(agent).getPageHeader().clickIcon();
        } catch (WebDriverException e) {
            Assert.fail("Unable to change agent status. Please check the screenshot.");
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

    @When("^Wait for (.d*) seconds for Phone Number update")
    public void waitSomeTimeForBackendUpdatePhone(int waitFor){
        int waitTimeInMillis = waitFor * 1000;
        sleepFor(waitTimeInMillis);
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
            Assert.assertTrue(getAgentHomeForMainAgent().getVerifyPhoneNumberWindow().isOpened(3),
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
            Assert.assertEquals(phoneNumberInVerifyPopUp, "",
                    "Some phone number displayed in the field");
        }
        else{
            Assert.assertEquals(phoneNumberInCustomer360, phoneNumberInVerifyPopUp,
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
        String phone = getAgentHomeForMainAgent().getCustomer360Container().getPhoneNumber().replaceAll("\\+", "").replaceAll(" ", "");
        Assert.assertTrue(DBConnector.isSMSClientProfileCreated(ConfigManager.getEnv(), phone, linkedClientProfileId, "MC2_SMS"),
                "MC2_SMS client profile wasn't created");
    }

    @Then("Chat separator with OTP code and 'I have just sent...' message with user phone number are displayed")
    public void chatSeparatorCheck(){
        String phone = getAgentHomeForMainAgent().getCustomer360Container().getPhoneNumber().replaceAll(" ", "");
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

    private Customer360PersonalInfo getCustomer360Info(String clientFrom){
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
        return  ApiHelper.getCustomer360PersonalInfo(Tenants.getTenantUnderTestOrgName(),
                clientId, integrationType);
    }

    public boolean isRequiredPhoneNumberDisplayed(String agent, String isPhoneNumberRequired){
        String phone = getAgentHomePage(agent).getCustomer360Container().getPhoneNumber();
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
        Assert.assertTrue(getAgentHomeForMainAgent().getChatHeader().isValidChannelImg(),
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
}
