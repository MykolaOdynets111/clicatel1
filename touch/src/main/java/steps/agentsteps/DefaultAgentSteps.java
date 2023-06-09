package steps.agentsteps;

import agentpages.uielements.PageHeader;
import agentpages.uielements.Profile;
import apihelper.ApiHelper;
import apihelper.ApiHelperSupportHours;
import datamanager.Tenants;
import datamanager.UserPersonalInfo;
import datamanager.jacksonschemas.TenantChatPreferences;
import datamanager.jacksonschemas.chatextension.ChatExtension;
import datamanager.jacksonschemas.chatusers.UserInfo;
import datamanager.jacksonschemas.dotcontrol.InitContext;
import dbmanager.DBConnector;
import driverfactory.DriverFactory;
import drivermanager.ConfigManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import socialaccounts.TwitterUsers;
import steps.ORCASteps;
import steps.dotcontrol.DotControlSteps;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DefaultAgentSteps extends AbstractAgentSteps {

    private static UserPersonalInfo userPersonalInfoForUpdating;
    public Profile profile;
    private static String initialNotificationCount;

    @When("Save clientID value for (.*) user")
    public void saveClientIDValues(String userFrom) {
        saveClientIDValue(userFrom);
    }


    @Given("^(.*) tenant feature is set to (.*) for (.*)$")
    public void setFeatureStatus(String parameter, String value, String tenantOrgName) {
        TenantChatPreferences tenantChatPreferences = new TenantChatPreferences();
        tenantChatPreferences.setValueForChatPreferencesParameter(parameter, value);
        ApiHelper.updateChatPreferencesParameter(tenantChatPreferences);
    }

    @Given("Agent creates tenant extension with label and name$")
    public void createExtensionForTenant(List<Map<String, String>> datatable) {
        ObjectMapper mapper = new ObjectMapper();
        List<String> listChatExtension = new ArrayList<>();
        for (int i = 0; i < datatable.size(); i++) {
            try {
                listChatExtension.add(mapper.writeValueAsString(new ChatExtension(
                        datatable.get(i).get("label"), datatable.get(i).get("name"),
                        datatable.get(i).get("extensionType"))));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        ApiHelper.createExtensions(listChatExtension.toString());
    }

    @Then("^On backand (.*) tenant feature status is set to (.*) for (.*)$")
    public void isFeatureStatusSet(String feature, boolean status, String tenantOrgName) {
        Assert.assertEquals(ApiHelper.getFeatureStatus(tenantOrgName, feature), status,
                feature + " feature is not expected");
    }

    @And("^(.*) click Whatsapp message icon button on the top bar$")
    public void clickWhatsappIcon(String agent) {
        getHeader(agent).clickOnWhatsapp();
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

    @Then("^(.*) checks channel icon (.*) is displayed in HSM form$")
    public void verifyCorrectHSMChannelIcon(String agent, String image) {
        Assert.assertTrue(getAgentHomePage(agent).getHSMForm().isValidChannelImg(image),
                "Icon for channel in Continue HSM form is not as expected");
    }

    @And("^(.*) fill the customer contact number$")
    public void sendWhatsApp(String agent) {
        getAgentHomePage(agent).getHSMForm().setWAPhoneNumber(ORCASteps.orcaMessageCallBody.get().getSourceId());
    }

    @And("^(.*) fill the customer contact number (.*)$")
    public void sendWhatsApp(String agent, String contactNumber) {
        getAgentHomePage(agent).getHSMForm().setWAPhoneNumber(contactNumber);
    }

    @Then("^(.*) verify customer contact number (.*) is filled$")
    public void checkContactNumber(String agent, String phoneNumber) {
        Assert.assertTrue(getAgentHomePage(agent).getHSMForm().getContactNum().equalsIgnoreCase(phoneNumber), "False : waPhone Field is empty");
    }

    @Then("^Verify (.*) has (.*) conversation requests from (.*) integration$")
    public void verifyConversationRequest(String agent, int numberOfChats, String integration) {
        if (getNumberOfActiveChats(agent, integration) < numberOfChats) {
            waitSomeTime(10);
        }

        assertThat(getNumberOfActiveChats(agent, integration))
                .as(format("Agent should have %s active charts by %s integration", numberOfChats, integration))
                .isEqualTo(numberOfChats);
    }

    @Then("^(.*) button is (.+) on Chat header$")
    public void isButtonEnabled(String button, String state) {
        if (state.equalsIgnoreCase("disabled"))
            Assert.assertTrue(getAgentHomePage("main").getChatHeader().isButtonDisabled(button));
        else if (state.equalsIgnoreCase("enabled"))
            Assert.assertTrue(getAgentHomePage("main").getChatHeader().isButtonEnabled(button));
    }

    @Then("^New info is shown in left menu with chats$")
    public void checkUpdatingUserInfoInLeftMenu() {
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getLeftMenu("main").getActiveChatUserName(), userPersonalInfoForUpdating.getFullName().trim(),
                "Full user name is not updated in left menu with chats after updating User Personal info \n");
        soft.assertEquals(getLeftMenu("main").getActiveChatLocation(), userPersonalInfoForUpdating.getLocation(),
                "Location is not updated in left menu with chats after updating User Personal info \n");
        soft.assertAll();
    }

    @Then("^(.*) button hidden from the Chat header$")
    public void checkIfButtonHidden(String button) {
        Assert.assertTrue(getAgentHomePage("main").getChatHeader().isButtonDisabled(button),
                "'" + button + "' button is displayed");
    }

    @Then("Message that it is overnight ticket is shown for (.*)")
    public void verifyMessageThatThisIsOvernightTicket(String agent) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(getAgentHomePage(agent).getChatForm().getOvernightTicketMessage(), "This is an unattended chat that has been assigned to you.",
                "Message that this is Overnight ticket is not shown");
        softAssert.assertTrue(getAgentHomePage(agent).getChatForm().isSendEmailForOvernightTicketMessageShown(),
                "'Send email' button is not enabled");
        softAssert.assertAll();
    }

    @Then("^Conversation area contains (.*) to user message$")
    public void verifyOutOfSupportMessageShownOnChatdesk(String message) {
        String userMessage = message;
        if (message.contains("out_of_support_hours")) {
            userMessage = ApiHelper.getAutoResponderMessageText(message);
        }
        Assert.assertTrue(getAgentHomePage("main agent").getChatBody().isToUserMessageShown(userMessage),
                "Expected to user message '" + userMessage + "' should be shown in chatdesk");
    }

    @Then("^(.*) select cross button wait and stay dialog$")
    public void selectWaitAndStayCrossButton(String agent) {
        getAgentHomePage(agent).clickCrossButtonWarningDialog();
    }

    @Then("^(.*) select Don't show this message again checkbox$")
    public void selectDontShowWarningCheckbox(String agent) {
        getAgentHomePage(agent).clickDontShowMessageCheckbox();
    }


    @Then("^Verify (.*) tickets section is empty$")
    public void verifyTicketsSectionEmpty(String sectionName) {
        int ticketsCount = getAgentHomeForSecondAgent().getLeftMenuWithChats().getNewChatsCount();
        assertThat(ticketsCount)
                .as(format("Some tickets are present in %s section, but mustn't be", sectionName))
                .isNotPositive();
    }

    @Then("^(.*) can see the message (.*)$")
    public void messageIsPResentInHomePage(String agent, String errorMessage) {
        Assert.assertEquals(getAgentHomePage(agent).getNoResultsFoundMessage(), errorMessage,
                "Wrong no results found error message found");
    }

    @When("^(.*) changes status to: (.*)$")
    public void changeAgentStatus(String agent, String newStatus) {
        getHeader(agent).clickIcon();
        getHeader(agent).selectStatus(newStatus);
    }

    @Then("^(.*) checks initial bell notification count$")
    public void getInitialBellNotificationCount(String agent) {
        initialNotificationCount = getAgentHomePage(agent).getNotificationCount();
    }

    @Then("^(.*) receive increase in the count of the bell icon notification$")
    public void verifyIncreasedBellNotificationCount(String agent) {
        String expectedCount = String.valueOf((Integer.parseInt(initialNotificationCount) + 1));
        Assert.assertEquals(getAgentHomePage(agent).getNotificationCount(), expectedCount,
                "Bell Notification count has not increased");
    }

    @Then("^(.*) should see notifications (.*) at time (.*) ago in the notification frame$")
    public void verifyLatestBellNotification(String agent, String expectedNotificationText, String expectedTime) {
        getAgentHomePage(agent).hoverBellNotificationIcon();
        softAssert.assertEquals(getAgentHomePage(agent).getNotificationText(), expectedNotificationText,
                "Latest bell Notification text is not correct");
        softAssert.assertTrue(getAgentHomePage(agent).getNotificationTime().contains(expectedTime),
                "Latest bell Notification time is not correct");
        softAssert.assertAll();
    }

    @When("^Verify (.*) status: (.*) is displayed on the icon$")
    public void verifyStatusOnByIcon(String agent, String status) {
        assertThat(getHeader(agent).verifyUserStatusOnIcon(status))
                .as(format("%s status is displayed!", status))
                .isTrue();
    }

    @When("^(.*) refreshes the page$")
    public void refreshThePage(String agent) {
        DriverFactory.getDriverForAgent(agent).navigate().refresh();
    }

    @When("^Agent limit reached popup is show for (.*)$")
    public void verifyAgentLimitPopup(String ordinalAgentNumber) {
        Assert.assertTrue(getAgentHomePage(ordinalAgentNumber).isAgentLimitReachedPopupShown(12),
                "'Agent limit reached' pop up is not shown.");
    }

    @Given("^Set agent support hours (.*)$")
    public void setSupportHoursWithShift(String shiftStrategy) {
        switch (shiftStrategy) {
            case "with day shift":
                LocalDateTime currentTimeWithADayShift = LocalDateTime.now().minusDays(1);

                ApiHelperSupportHours.setSupportDaysAndHours(Tenants.getTenantUnderTestOrgName(),
                        currentTimeWithADayShift.getDayOfWeek().toString(), "00:00", "23:59");
                break;
            case "for all week":
                ApiHelperSupportHours.setSupportDaysAndHours(Tenants.getTenantUnderTestOrgName(), "all week",
                        "00:00", "23:59");
                break;
        }
    }

    @Then("^Tab with user info has \"(.*)\" header$")
    public void verifyTabHeader(String headerName) {
        Assert.assertEquals(getAgentHomeForMainAgent().getSelectedTabHeader(), headerName,
                "Incorrect header of customer selected tab, should be " + headerName);
    }

    @Then("Correct (.*) client details are shown")
    public void verifyClientDetails(String clientFrom) {
        UserPersonalInfo userPersonalInfoFromChatdesk = getAgentHomePage("main")
                .getProfile().getActualPersonalInfo();

        Assert.assertEquals(userPersonalInfoFromChatdesk, getUserPersonalInfo(clientFrom),
                "User info is not as expected \n");
    }

    @Then("Correct dotcontrol client details from Init context are shown")
    public void verifyInitContextClientDetails() {
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
    public void clickEditCustomerView() {
        getAgentHomePage("main").getProfile().clickSaveEditButton();
    }

    @Then("^Wait for (.d*) seconds for Phone Number to be (.*)$")
    public void checkPhoneNumberFieldUpdate(int waitFor, String requiredState) throws InterruptedException {
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
    public void waitSomeTimeForBackendUpdatePhone(int waitFor) {
        waitSomeTime(waitFor);
    }

    @And("^Wait for (\\d*) second$")
    public void waitSomeTime(int waitFor) {
        int waitTimeInMillis = waitFor * 1000;
        sleepFor(waitTimeInMillis);
    }

    @When("Fill in the form with new (.*) user profile info")
    public void updateUserInfo(String customerFrom) {
        UserPersonalInfo currentCustomerInfo = getUserPersonalInfo(customerFrom);
        userPersonalInfoForUpdating = currentCustomerInfo.setLocation("Lviv")
                .setEmail("udated_" + faker.lorem().word() + "@gmail.com")
                .setPhone(faker.numerify("38093#######")); //"+380931576633");
        if (!(customerFrom.contains("fb") || customerFrom.contains("twitter"))) {
            userPersonalInfoForUpdating.setFullName("AQA Run");
            userPersonalInfoForUpdating.setChannelUsername(userPersonalInfoForUpdating.getFullName());

        }
        getAgentHomePage("main").getProfile().fillFormWithNewDetails(userPersonalInfoForUpdating);
    }

    @When("^(.*) see (.*) phone number added into User profile$")
    public void updatePhoneNumberUserProfile(String agent, String isPhoneNumberRequired) {
        Assert.assertTrue(isRequiredPhoneNumberDisplayed(agent, isPhoneNumberRequired));
    }

    @Then("^(.*) button (.*) displayed in User profile$")
    public void checkUserProfilePhoneButtonsVisibility(String buttonName, String isOrNotDisplayed) {
        Profile profile = getAgentHomePage("main").getProfile();
        if (isOrNotDisplayed.equalsIgnoreCase("not"))
            Assert.assertFalse(profile.isUserSMSButtonsDisplayed(buttonName), "'" + buttonName + "' button is not displayed");
        else
            Assert.assertTrue(profile.isUserSMSButtonsDisplayed(buttonName), "'" + buttonName + "' button still displayed");
    }

    @Then("^'Verify' and 'Re-send OTP' buttons (.*) displayed in User profile$")
    public void checkUserProfilePhoneVerifyAndReSendButtonsVisibility(String isOrNotDisplayed) {
        Profile profile = getAgentHomePage("main").getProfile();
        SoftAssert softAssert = new SoftAssert();
        if (isOrNotDisplayed.contains("not")) {
            softAssert.assertFalse(profile.isUserSMSButtonsDisplayed("Verify"),
                    "'Verify' button is not displayed");
            softAssert.assertFalse(profile.isUserSMSButtonsDisplayed("Re-send OTP"),
                    "'Re-send OTP' button is not displayed");
            softAssert.assertAll();
        } else {
            softAssert.assertTrue(profile.isUserSMSButtonsDisplayed("Verify"));
            softAssert.assertTrue(profile.isUserSMSButtonsDisplayed("Re-send OTP"));
            softAssert.assertAll();
        }
    }

    @When("^(.*) phone number for (.*) user$")
    public void changePhoneNumberUserProfile(String changeOrDelete, String customerFrom) {
        String phoneNumber = " "; //in case we need to delete phone number
        UserPersonalInfo currentCustomerInfo = getUserPersonalInfo(customerFrom);
        if (changeOrDelete.equalsIgnoreCase("change"))
            phoneNumber = generateUSCellPhoneNumber();

        userPersonalInfoForUpdating = currentCustomerInfo.setPhone(phoneNumber);

        getAgentHomePage("main").getProfile().setPhoneNumber(phoneNumber);
        Assert.assertEquals(currentCustomerInfo.getPhone(), phoneNumber, "Entered phone number is not equal to displayed one");
    }

    @When("Agent click on '(.*)' button in User profile")
    public void clickPhoneActionsButtonsUserProfile(String buttonName) {
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
    public void phoneNumberForVerifyCheck(String isRequiredToDisplay) {
        String phoneNumberInUserProfile = getAgentHomeForMainAgent().getProfile()
                .getPhoneNumber().replaceAll("\\s+", "");
        String phoneNumberInVerifyPopUp = getAgentHomeForMainAgent().getVerifyPhoneNumberWindow()
                .getEnteredPhoneNumber().replaceAll("[\\s-.]", "");
        if (isRequiredToDisplay.contains("not")) {
            Assert.assertEquals(phoneNumberInVerifyPopUp, "",
                    "Some phone number displayed in the field");
        } else {
            Assert.assertEquals(phoneNumberInUserProfile, phoneNumberInVerifyPopUp,
                    "Phone number in Verify phone window is different from displayed in Customer Profile");
        }
    }

    @When("Agent click on (.*) button on 'Verify phone' window")
    public void closeVerifyPhonePopUp(String buttonName) {
        getAgentHomeForMainAgent().getVerifyPhoneNumberWindow().sendOrCancelClick(buttonName);
    }

    @When("Agent send OTP message with API")
    public void sendOTPWithAPI() {
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
    public void checkSMSProfileCreating() {
        String phone = getAgentHomeForMainAgent().getProfile().getPhoneNumber().replaceAll("\\+", "").replaceAll(" ", "");
        UserInfo userInfo = ApiHelper.getUserProfile("webchat", getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()));
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(!userInfo.getSms().getIntegrationId().isEmpty(), "Sms client integration was not added");
        soft.assertEquals(userInfo.getSms().getPhoneNumber(), phone, "Incorrect phone is set into Sms integration");
        soft.assertAll();
    }

    @Then("Chat separator with OTP code and 'I have just sent...' message with user phone number are displayed")
    public void chatSeparatorCheck() {
        String phone = getAgentHomeForMainAgent().getProfile().getPhoneNumber().replaceAll(" ", "");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(getAgentHomeForMainAgent().getChatBody().isOTPDividerDisplayed(), "No OTP divider displayed");
        softAssert.assertTrue(getAgentHomeForMainAgent().getChatForm().getTextFromMessageInputField().replaceAll("\\s", "").contains(phone),
                "Phone number is not displayed in message field");
        softAssert.assertAll();
    }

    @Then("New OTP code is different from the previous one")
    public void checkOTPCodes() {
        Assert.assertTrue(getAgentHomeForMainAgent().getChatBody().isNewOTPCodeDifferent(), "Codes are equal");
    }

    @When("^Agent switches to opened (?:Portal|ChatDesk) page$")
    public void switchBetweenPortalAndChatDesk() {
        String currentWindow = DriverFactory.getDriverForAgent("main").getWindowHandle();

        if (DriverFactory.getDriverForAgent("main").getWindowHandles().size() > 1) {
            for (String winHandle : DriverFactory.getDriverForAgent("main").getWindowHandles()) {
                if (!winHandle.equals(currentWindow)) {
                    DriverFactory.getDriverForAgent("main").switchTo().window(winHandle);
                }
            }
        }
    }

    @Then("^(.*) checks the link (.*) is opened in the new tab$")
    public void checkLinkIsOpenedInNewTab(String agent, String expectedURL) {
        String currentURL = DriverFactory.getDriverForAgent("main").getCurrentUrl();
        Assert.assertTrue(currentURL.equalsIgnoreCase(expectedURL), "The URL is not as expected");
    }

    @When("^Agent refresh current page$")
    public void refreshCurrentPage() {
        DriverFactory.getDriverForAgent("main").navigate().refresh();
    }

    @Then("^(.*) customer info is updated on backend$")
    public void verifyCustomerInfoChangesOnBackend(String customerFrom) {
        Assert.assertEquals(getUserPersonalInfo(customerFrom), userPersonalInfoForUpdating,
                "User Personal info is not updated on backend after making changes on chatdesk. \n");
    }

    @Then("^Customer name is updated in active chat header$")
    public void verifyCustomerNameUpdated() {
        Assert.assertTrue(getAgentHomeForMainAgent().getChatHeader().getChatHeaderText().contains(userPersonalInfoForUpdating.getFullName().trim()),
                "Updated customer name is not shown in chat header");
    }

    @Then("^Tenant photo is shown on chatdesk$")
    public void verifyTenantImageIsShownOnChatdesk() {
        Assert.assertTrue(getHeader("main").isTenantImageShown(),
                "Tenant image is not shown on chatdesk");
    }

    private UserPersonalInfo getUserPersonalInfo(String clientFrom) {
        String clientId;
        String integrationType;
        switch (clientFrom) {
            case "fb dm":
                Map chatInfo = (Map) ApiHelper.getActiveChatsByAgent("main").jsonPath().getList("content")
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
        return ApiHelper.getUserPersonalInfo(Tenants.getTenantUnderTestOrgName(),
                clientId, integrationType);
    }

    public boolean isRequiredPhoneNumberDisplayed(String agent, String isPhoneNumberRequired) {
        String phone = getAgentHomePage(agent).getProfile().getPhoneNumber();
        if (isPhoneNumberRequired.equalsIgnoreCase("no"))
            return phone.equalsIgnoreCase("");
        else
            return !phone.equalsIgnoreCase("");
    }

    @And("^Header in chat box displayed the icon for channel from which the user is chatting$")
    public void headerInChatBoxDisplayedTheIconForChannelFromWhichTheUserIsChatting() {
        Assert.assertTrue(getAgentHomeForMainAgent().getChatHeader().isValidChannelImg("headerChannel"),
                "Icon for channel in chat header as not expected");
    }

    @And("^Time stamp displayed in 24 hours format$")
    public void timeStampDisplayedInHoursFormat() {
        Map<String, String> sessionDetails = DBConnector.getSessionDetailsByClientID(ConfigManager.getEnv()
                , getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()));
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
    public void firstAgentClickOnHeadphonesIconAndSeeAvailableAgents(String agent, int availableAgent) {
        getHeader(agent).clickHeadPhonesButton();
        Assert.assertTrue(
                getHeader(agent).isExpectedNumbersShown(availableAgent, 8),
                "Quantity of available agents not as expected (" + availableAgent + " expected)");
        if (availableAgent == 1) {
            getHeader(agent).clickIcon();
            String agentName = getHeader(agent).getAgentName();
            Assert.assertFalse(
                    getHeader(agent).getAvailableAgents().contains(agentName),
                    "Unavailable agent in list of available agents");
        }
    }

    @And("^(.*) get \"Cannot close chat\" notification modal open$")
    public void notifyCannotCloseChat(String agent) {
        String ActualClosedChatWindowHeader = getAgentHomePage(agent).getC2pMoveToPendingMessage();
        Assert.assertEquals(ActualClosedChatWindowHeader, "Sorry, this chat can't be closed as it has 1 or more active payment(s). " +
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
        getHeader(agent).logOut();

        Assert.assertTrue(getAgentHomePage(agent).isDialogShown(),
                "Log out from agent desk not successful");
    }

    @Given("^(.*) checks error is displayed if selected more then 15 chats$")
    public void errorMessageCheckMoreThan15BulkChats(String agent) {
        Assert.assertTrue(getAgentHomePage(agent).isMultipleBulkMessagesTextShown()
                        .equalsIgnoreCase("You have reached your max bulk of 15 selected chats."),
                "Bulk messages more than 15 selected error not shown");
    }

    @Then("^(.*) checks notification message (.*) should appear$")
    public void bulkChatsotificationMessageCheck(String agent, String bulkNotificationMessage) {
        Assert.assertTrue(getAgentHomePage(agent).bulkMessagesTabSwitchNotification()
                        .equalsIgnoreCase(bulkNotificationMessage),
                "Bulk messages tab switch message not shown");
    }

    @Then("^(.*) checks selected chats should be shown in the (.*) message on the right pane$")
    public void bulkChatsMiddlePaneMessageCheck(String agent, String middlePaneBulkMessage) {
        Assert.assertTrue(getAgentHomePage(agent).bulkChatMiddlePaneMessage()
                        .equalsIgnoreCase(middlePaneBulkMessage),
                "Bulk messages middle pane message not shown");
    }

    @Then("^(.*) checks Bulk Messages section should get displayed on the right side with header (.*)$")
    public void bulkChatsMiddlePaneHeaderCheck(String agent, String middlePaneBulkMessageHeader) {
        Assert.assertTrue(getAgentHomePage(agent).bulkChatMiddlePaneHeaderMessage()
                        .contains(middlePaneBulkMessageHeader),
                "Bulk messages middle pane header not shown");
    }

    @When("^(.*) hover over \"Bulk chat\" button and see (.*) message$")
    public void bulkChatToolTipMessage(String agent, String toolTipMessage) {
        getLeftMenu(agent).hoverBulkChatButton();
        Assert.assertEquals(getAgentHomePage(agent).getBulkMessageToolTipText(), toolTipMessage, "Bulk chat hover message is wrong");
    }

    @Then("^(.*) checks as per sorting preference selected, the chat is at (.*) index of chats section for (.*) user$")
    public void checkActiveChatIndex(String agent, int indexOfActiveChat, String integration) {
        Assert.assertEquals(getLeftMenu(agent).getTargetChatIndex(getUserName(integration)), indexOfActiveChat,
                "Current selected chat is not on top");
    }

    @When("^(.*) checks agent name initials are (.*)$")
    public void checkAgentNameInitials(String agent, String expectedAgentInitials) {
        Assert.assertTrue(getHeader(agent).getAgentInitials().equalsIgnoreCase(expectedAgentInitials),
                "Agent initials are incorrect");
    }

    @When("^(.*) checks agent details contain name (.*) and email (.*)$")
    public void checkAgentInfo(String agent, String expectedAgentName, String expectedAgentEmail) {
        softAssert.assertTrue(getHeader(agent).getAgentName().equalsIgnoreCase(expectedAgentName),
                "Agent Name is incorrect");
        softAssert.assertTrue(getHeader(agent).getAgentEmail().equalsIgnoreCase(expectedAgentEmail),
                "Agent Email is incorrect");
        softAssert.assertAll();
    }

    private static PageHeader getHeader(String agent) {
        return getAgentHomePage(agent).getPageHeader();
    }

    private static int getNumberOfActiveChats(String agent, String integration) {
        return (int) ApiHelper.getActiveChatsByAgent(agent)
                .jsonPath().getList("content.channel.type").stream()
                .filter(ct -> ct.toString().equalsIgnoreCase(integration)).count();
    }
}