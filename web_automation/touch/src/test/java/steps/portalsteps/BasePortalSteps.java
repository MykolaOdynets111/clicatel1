package steps.portalsteps;


import apihelper.ApiHelper;
import datamanager.Agents;
import datamanager.Tenants;
import dbmanager.DBConnector;
import driverfactory.DriverFactory;
import driverfactory.URLs;
import drivermanager.ConfigManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import portalpages.PortalLoginPage;
import portaluielem.PreferencesWindow;
import steps.agentsteps.AbstractAgentSteps;
import steps.agentsteps.AgentCRMTicketsSteps;
import touchpages.pages.MainPage;
import touchpages.pages.Widget;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class BasePortalSteps extends AbstractPortalSteps {

    public static String AGENT_FIRST_NAME;
    public static String AGENT_LAST_NAME;
    public static String AGENT_EMAIL;
    private String AGENT_PASS = Agents.TOUCH_GO_SECOND_AGENT.getAgentPass();
    private static Map<String, String> tenantInfo = new HashMap<>();
    private Map<String, Integer> chatConsolePretestValue = new HashMap<>();
    int activeChatsFromChatdesk;
    private String nameOfUnchekedDay = "";
    private String autoSchedulerPreActionStatus;
    public static String tagname;

    public static Map<String, String> getTenantInfoMap(){
        return  tenantInfo;
    }

    @Given("^(.*) New (.*) agent is created$")
    public void createNewAgent(String agentEmail, String tenantOrgName){
        //todo add agent creation

//        if (agentEmail.equalsIgnoreCase("brand")) {
//            AGENT_EMAIL = "aqa_"+System.currentTimeMillis()+"@aqa.com";
//        } else{
//            AGENT_EMAIL = generatePredefinedAgentEmail();
//        }
//        Agents.TOUCH_GO_SECOND_AGENT.setEmail(AGENT_EMAIL);
//        Tenants.setTenantUnderTestNames(tenantOrgName);
//        AGENT_FIRST_NAME = faker.name().firstName();
//        AGENT_LAST_NAME =  faker.name().lastName();
//        AGENT_EMAIL =  Agents.TOUCH_GO_SECOND_AGENT.getAgentEmail();
//
//        AbstractAgentSteps.getListOfCreatedAgents().add(new HashMap<String, String>(){{
//            put("name", AGENT_FIRST_NAME + " " + AGENT_LAST_NAME);
//             put("mail", AGENT_EMAIL);
//        }});

    }

    private String generatePredefinedAgentEmail(){
        String[] email = Agents.TOUCH_GO_SECOND_AGENT.getOriginalEmail().split("@");
        return email[0] + "+" + System.currentTimeMillis() + "@gmail.com";
    }

    @When("^I open portal$")
    public void openPortal(){
        if (ConfigManager.isMc2()) {
            setCurrentPortalLoginPage(PortalLoginPage.openPortalLoginPage(DriverFactory.getDriverForAgent("admin")));
        }else {
            AbstractAgentSteps.getLoginForMainAgent().openPortalLoginPage();
        }
    }

    @Given("(.*) integration status is set to (.*) for (.*) tenant")
    public void changeIntegrationState(String integrationName, String status, String tenantOrgName){
        if (status.equalsIgnoreCase("enabled"))
            ApiHelper.setIntegrationStatus(tenantOrgName, getIntegrationType(integrationName),true);
        else if (status.equalsIgnoreCase("disabled"))
            ApiHelper.setIntegrationStatus(tenantOrgName, getIntegrationType(integrationName),false);
    }

    private String getIntegrationType(String integrationName){
        switch (integrationName.toLowerCase()){
            case "touch":
            case "web widget":
                return "webchat";
            case "sms":
                return "sms";
            case "whatsapp":
                return "whatsapp";

            default:
                throw new NoSuchElementException("Invalid integration name");
        }
    }




    @When("^Login into portal as an (.*) of (.*) account$")
    public void loginToPortal(String ordinalAgentNumber, String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        if (ConfigManager.isMc2()) {
           //todo Unity login
        }else {
            AbstractAgentSteps.getLoginForMainAgent().selectTenant(Tenants.getTenantUnderTestName())
                    .selectAgent("agent").clickAuthenticateButton();
        }
    }

    @When("^(?:I|Admin) select (.*) in left menu and (.*) in submenu$")
    public void navigateInLeftMenu(String menuItem, String submenu){
        if(ConfigManager.isMc2()){
            //todo add Unity navigation to Touch
        } else {
            AbstractAgentSteps.getLoginForMainAgent().getCurrentDriver().get(URLs.getUrlByNameOfPage(submenu));
        }
    }


    @When("^Save (.*) pre-test widget value$")
    public void savePreTestValue(String widgetName){
        try {
            chatConsolePretestValue.put(widgetName, Integer.valueOf(getDashboardPage().getWidgetValue(widgetName)));
        } catch (NumberFormatException e){
            Assert.fail("Cannot read value from " +widgetName + "chat console widget");
        }
    }

    @Then("^(.*) widget shows correct number$")
    public void checkTotalAgentOnlineValue(String widgetName){
        getDashboardPage().waitForConnectingDisappear(1, 5);
        int actualActiveAgentsCount = Integer.parseInt(getDashboardPage().getWidgetValue(widgetName));
        chatConsolePretestValue.put(widgetName, actualActiveAgentsCount);
        int loggedInAgentsCountFromBackend = ApiHelper.getNumberOfLoggedInAgents();
        Assert.assertEquals(actualActiveAgentsCount, loggedInAgentsCountFromBackend,
                widgetName + " counter differs from agent online count on backend");
    }

    @Then("^(.*) widget value (?:increased on|set to) (.*)$")
    public void verifyWidgetValue(String widgetName, int incrementer){
        int expectedValue = 0;
        if(incrementer!=0) expectedValue = chatConsolePretestValue.get(widgetName) + incrementer;
        Assert.assertTrue(checkLiveCounterValue(widgetName, expectedValue),
                "'"+widgetName+"' widget value is not updated\n" +
                "Expected value: " + expectedValue);
    }

    @Then("^(.*) counter shows correct live chats number$")
    public void verifyChatConsoleActiveChats(String widgetName){
        activeChatsFromChatdesk = ApiHelper.getActiveChatsByAgent("Second agent")
                .getBody().jsonPath().getList("content.id").size();
//                new AgentHomePage("second agent").getLeftMenuWithChats().getNewChatsCount();
        Assert.assertTrue(checkLiveCounterValue(widgetName, activeChatsFromChatdesk),
                "'"+widgetName+"' widget value is not updated to " + activeChatsFromChatdesk +" expected value \n");
    }

    @Then("^Average chats per Agent is correct$")
    public void verifyAverageChatsPerAgent(){
       int actualAverageChats = Integer.valueOf(getDashboardPage().getAverageChatsPerAgent());
       int expectedAverageChats = activeChatsFromChatdesk /  ApiHelper.getNumberOfLoggedInAgents();
       Assert.assertEquals(actualAverageChats, expectedAverageChats,
               "Number of Average chats per Agent is not as expected");
    }

    private boolean checkLiveCounterValue(String widgetName, int expectedValue){
        int actualValue = Integer.valueOf(getDashboardPage().getWidgetValue(widgetName));
        boolean result = false;
        for (int i=0; i<45; i++){
            if(expectedValue!=actualValue){
                getDashboardPage().waitFor(1000);
                actualValue = Integer.valueOf(getDashboardPage().getWidgetValue(widgetName));
            } else {
                result =true;
                break;
            }

        }
        return result;
    }

    @When("^Navigate to (.*) page$")
    public void openSettingPage(String settingsName){
        getDashboardPage().openSettingsPage().openSettingsPage(settingsName);
    }

    @When("^Wait for auto responders page to load$")
    public void waitForAutoRespondersToLoad(){
        getPortalTouchPreferencesPage().getAutoRespondersWindow().waitToBeLoaded();
        getPortalTouchPreferencesPage().getAutoRespondersWindow().waitForAutoRespondersToLoad();
    }

    @When("^Change chats per agent:\"(.*)\"$")
    public void changeChatPerAgent(String chats){
        getPortalTouchPreferencesPage().getPreferencesWindow().setChatsAvailable(chats);
    }

    @When("^Agent set (\\d*) hours and (\\d*) minutes in Agent Chat Timeout section$")
    public void setInactivityTimeout(int hours, int minutes){
        getPortalTouchPreferencesPage().getPreferencesWindow().setAgentInactivityTimeout(hours, minutes);
    }

    @Then("^\"(.*)\" error message is shown in Agent Chat Timeout section$")
    public void verifyInactivityTimeoutError(String expectedError){
        Assert.assertEquals(getPortalTouchPreferencesPage().getPreferencesWindow().getAgentInactivityTimeoutLimitError(), expectedError,
                "Incorrect limits message was shown in Agent Chat Timeout section");
    }

    @Then("^Error message is not shown in Agent Chat Timeout section$")
    public void verifyInactivityTimeoutErrorNotShowing(){
        Assert.assertFalse(getPortalTouchPreferencesPage().getPreferencesWindow().isAgentInactivityTimeoutLimitErrorShown(),
                "Error message for Agent Chat Timeout limit should not be shown");
    }


    @When("^Agent set (\\d*) days in Media Files Expiration section$")
    public void setMediaFilesExpiration(int days){
        getPortalTouchPreferencesPage().getPreferencesWindow().setAttachmentLifeTimeDays(days);
    }

    @Then("^\"(.*)\" error message is shown in Media Files Expiration section$")
    public void verifyAttachmentLifeTimeDaysLimitError(String expectedError){
        Assert.assertEquals(getPortalTouchPreferencesPage().getPreferencesWindow().getAttachmentLifeTimeDaysLimitError(), expectedError, "Incorrect limits message was shown in Media Files Expiration section");
    }

    @Then("^Error message is not shown in Media Files Expiration section$")
    public void verifyAttachmentLifeTimeDaysErrorNotShowing(){
        Assert.assertFalse(getPortalTouchPreferencesPage().getPreferencesWindow().isAttachmentLifeTimeDaysLimitErrorShown(),
                "Error message for Agent Chat Timeout limit should not be shown");
    }


    @When("^Agent set (\\d*) hours in Ticket Expiration section$")
    public void setTicketsExpiration(int hours){
        getPortalTouchPreferencesPage().getPreferencesWindow().setTicketExpirationHours(hours);
    }

    @Then("^\"(.*)\" error message is shown in Ticket Expiration section$")
    public void verifyTicketsExpirationLimitError(String expectedError){
        Assert.assertEquals(getPortalTouchPreferencesPage().getPreferencesWindow().getTicketExpirationLimitError(),
                expectedError, "Incorrect limits message was shown in Tickets Expiration section");
    }

    @Then("^Error message is not shown in Ticket Expiration section$")
    public void verifyTicketsExpirationLimitErrorNotShowing(){
        Assert.assertFalse(getPortalTouchPreferencesPage().getPreferencesWindow().isTicketExpirationLimitErrorShown(), "Error message for Tickets Expiration limit should not be shown");
    }


    @When("^Chats per agent became:\"(.*)\"$")
    public void changeChatPerAgentPlusMinus(String result){
            Assert.assertEquals(getPortalTouchPreferencesPage().getPreferencesWindow().getChatsAvailable(), result,"Chats per agent is not as expected");
    }

    @When("^Error message is shown$")
    public void errorIsShownInWindow(){
        Assert.assertTrue(getPortalTouchPreferencesPage().getPreferencesWindow().isErrorMessageShown(),
                "Error message is not shown");
    }

    @When("^Click off/on Chat Conclusion$")
    public void clickOffOnChatConclusion(){
        getPortalTouchPreferencesPage().getPreferencesWindow().clickOnOffChatConclusion();
        agentClickSaveChangesButton();
    }


    @When("^Agent click 'Save changes' button$")
    public void agentClickSaveChangesButton() {
        getPortalTouchPreferencesPage().clickSaveButton();
        getPortalTouchPreferencesPage().waitForSaveMessage();
    }

    @When("^Agent click expand arrow for (.*) auto responder$")
    public void clickExpandArrowForAutoResponder(String autoresponder){
        getPortalTouchPreferencesPage().getAutoRespondersWindow()
                                                            .clickExpandArrowForMessage(autoresponder);
    }

    @When("^Click \"Reset to default\" button for (.*) auto responder$")
    public void clickResetToDefaultButton(String autoresponder){
        getPortalTouchPreferencesPage().getAutoRespondersWindow().clickResetToDefaultForMessage(autoresponder);
        getPortalTouchPreferencesPage().waitWhileProcessing(14, 20);
    }

    @When("^Type new message: (.*) to: (.*) message field$")
    public void typeNewMessage(String message, String autoresponder){
        getPortalTouchPreferencesPage().getAutoRespondersWindow().waitToBeLoaded();
        if (!getPortalTouchPreferencesPage().getAutoRespondersWindow().getTargetAutoResponderItem(autoresponder).isMessageShown()) {
            getPortalTouchPreferencesPage().getAutoRespondersWindow()
                    .clickExpandArrowForMessage(autoresponder);
        }
        getPortalTouchPreferencesPage().getAutoRespondersWindow().getTargetAutoResponderItem(autoresponder).typeMessage(message + faker.letterify("????")).clickSaveButton();
        getPortalTouchPreferencesPage().waitWhileProcessing(1, 4);
    }

    @Then("^(.*) on backend corresponds to (.*) on frontend$")
    public void messageWasUpdatedOnBackend(String tafMessageId, String messageName) {
        String messageOnfrontend = getPortalTouchPreferencesPage().getAutoRespondersWindow().getTargetAutoResponderItem(messageName).getMessage();
        String actualMessage = ApiHelper.getAutoResponderMessageText(tafMessageId);
        Assert.assertEquals(actualMessage, messageOnfrontend,
                messageName + " message is not updated on backend");

    }

    @Then("^(.*) is reset on backend$")
    public void verifyTafMessageIsReset(String autoresponderId){
        String actualMessage = ApiHelper.getAutoResponderMessageText(autoresponderId);
        String defaultMessage = DBConnector.getDefaultAutoResponder(ConfigManager.getEnv(), autoresponderId);
        Assert.assertEquals(actualMessage, defaultMessage,
                autoresponderId + " message is not reset to default");
    }


    @Given("^(.*) tenant has no brand image$")
    public void deleteTenantBrandImage(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        ApiHelper.deleteTenantBrandImage(Tenants.getTenantUnderTestOrgName());
        String fileType = ApiHelper.getTenantBrandImage(Tenants.getTenantUnderTestOrgName()).contentType();
        String fileTypeTrans = ApiHelper.getTenantBrandImageTrans(Tenants.getTenantUnderTestOrgName()).contentType();
        SoftAssert soft = new SoftAssert();
        soft.assertFalse(fileType.contains("image"),
                "Image was not deleted on backend");
        soft.assertFalse(fileTypeTrans.contains("image"),
                "Image for chat desk (tenant_logo_trans) was not deleted on backend");
        soft.assertAll();
    }

    @Then("^New brand image is saved on backend for (.*) tenant$")
    public void verifyBrandImageSaveOnPortal(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        String fileType = ApiHelper.getTenantBrandImage(Tenants.getTenantUnderTestOrgName()).contentType();
        String fileTypeTrans = ApiHelper.getTenantBrandImageTrans(Tenants.getTenantUnderTestOrgName()).contentType();
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(fileType.contains("image"),
                "Image is not saved on backend");
        soft.assertTrue(fileTypeTrans.contains("image"),
                "Image for chat desk (tenant_logo_trans) is not saved on backend");
        soft.assertAll();
    }

    @And("^Change business details$")
    public void changeBusinessDetails() {
        tenantInfo.put("companyName", "New company name "+faker.lorem().word());
        tenantInfo.put("companyCity", "San Francisco "+faker.lorem().word());
        tenantInfo.put("companyIndustry", getPortalTouchPreferencesPage().getBusinessProfileWindow().selectRandomIndastry());
        tenantInfo.put("companyCountry", getPortalTouchPreferencesPage().getBusinessProfileWindow().selectRandomCountry());
        getPortalTouchPreferencesPage().getBusinessProfileWindow().setBusinessName(tenantInfo.get("companyName"));
        getPortalTouchPreferencesPage().getBusinessProfileWindow().setCompanyCity(tenantInfo.get("companyCity"));
        agentClickSaveChangesButton();
    }

    @And("^Refresh page and verify business details was changed for (.*)$")
    public void refreshPageAndVerifyItWasChanged(String tenantOrgName) {
        SoftAssert soft = new SoftAssert();
        Response resp = ApiHelper.getTenantInfo(tenantOrgName);
        DriverFactory.getDriverForAgent("main").navigate().refresh();
        String country = DBConnector.getCountryName(ConfigManager.getEnv(),resp.jsonPath().getList("tenantAddresses.country").get(0).toString());
        soft.assertEquals(getPortalTouchPreferencesPage().getBusinessProfileWindow().getCompanyName(),tenantInfo.get("companyName"), "Company name was not changed");
        soft.assertEquals(resp.jsonPath().get("tenantOrgName"),tenantInfo.get("companyName"), "Company name was not changed on backend");
        soft.assertEquals(getPortalTouchPreferencesPage().getBusinessProfileWindow().getCompanyCity(),tenantInfo.get("companyCity"), "Company city was not changed");
        soft.assertEquals(resp.jsonPath().getList("tenantAddresses.city").get(0).toString(),tenantInfo.get("companyCity"), "Company city was not changed on backend");
        soft.assertEquals(getPortalTouchPreferencesPage().getBusinessProfileWindow().getCompanyIndustry(),tenantInfo.get("companyIndustry"), "Company industry was not changed");
        soft.assertEquals(resp.jsonPath().get("category"),tenantInfo.get("companyIndustry"), "Company industry was not changed on backend");
        soft.assertEquals(getPortalTouchPreferencesPage().getBusinessProfileWindow().getCompanyCountry(),tenantInfo.get("companyCountry"), "Company country was not changed");
        soft.assertEquals(country,tenantInfo.get("companyCountry"), "Company country was not changed on backend");
        getPortalTouchPreferencesPage().getBusinessProfileWindow().setBusinessName("Automation Bot");
        getPortalTouchPreferencesPage().clickSaveButton();
        getPortalTouchPreferencesPage().waitWhileProcessing(14, 20);
        soft.assertAll();
    }

    @Then("^All default values on Preferences page are correct$")
    public void verifyAllDefaultPreferences(Map<String, String> pref){
        SoftAssert soft = new SoftAssert();
        PreferencesWindow preferencesWindow = getPortalTouchPreferencesPage().getPreferencesWindow();
        soft.assertEquals(preferencesWindow.getChatsAvailable(), pref.get("maximumChatsPerAgent"),
                "Default Max Chats per agent preferences are not correct");
        soft.assertEquals(preferencesWindow.getTicketExpirationHours(), pref.get("ticketExpiration"),
                "Default Ticket Expiration hours are not correct");
        soft.assertEquals(preferencesWindow.getAgentChatTimeout(), pref.get("agentChatTimeout"),
                "Default Agent Chat Timeout are not correct");
        soft.assertEquals(preferencesWindow.getAttachmentLifeTimeDays(), pref.get("mediaFilesExpiration"),
                "Default Media Files Expiration days are not correct");
        soft.assertEquals(preferencesWindow.getInactivityTimeoutHours(), pref.get("InactivityTimeoutHours"),
                "Default Inactivity Timeout Hours hours are not correct");
        soft.assertEquals(preferencesWindow.getPendingChatAutoClosureHours(), pref.get("pendingChatsAuto_closureTime"),
                "Default Pending Chats Auto-closure Time hours are not correct");
        soft.assertAll();

    }

    @When("^Select 'Specific Agent Support hours' radio button in Agent Supported Hours section$")
    public void selectSpecificAgentSupportHoursRadioButtonInAgentSupportedHoursSection() {
        getPortalTouchPreferencesPage().getBusinessProfileWindow().openSpecificSupportHours();
    }

    @When("^click off/on 'Automatic Scheduler'$")
    public void clickOnOffAutoScheduler(){
        autoSchedulerPreActionStatus =  ApiHelper.getInternalTenantConfig(Tenants.getTenantUnderTestName(), "autoSchedulingEnabled");
        getPortalTouchPreferencesPage().getPreferencesWindow().clickOnOffAutoScheduler();
        agentClickSaveChangesButton();
    }

    @When("^Select (.*) department By Default$")
    public void selectDefaultDepartment(String name){
        getPortalTouchPreferencesPage().getPreferencesWindow().activateDefaultDepartmentCheckbox().selectDefaultDepartment(name);
        agentClickSaveChangesButton();
    }

    @When("^Switch Last Agent routing$")
    public void activateLastAgent(){
        getPortalTouchPreferencesPage().getPreferencesWindow().clickOnLiveChatRoating();
        agentClickSaveChangesButton();
    }

    @Then("^On backend AUTOMATIC_SCHEDULER status is updated for (.*)$")
    public void verifyAutoSchedulingStatusOnBackend(String tenant){
        Assert.assertNotEquals(ApiHelper.getInternalTenantConfig(Tenants.getTenantUnderTestName(), "autoSchedulingEnabled"),
                autoSchedulerPreActionStatus,
                "Auto scheduling status on backend is not as expected \n");
    }

    @And("^Uncheck today day and apply changes$")
    public void uncheckTodayDayAndApplyChanges() {
        nameOfUnchekedDay = getPortalTouchPreferencesPage().getBusinessProfileWindow().uncheckTodayDay();
        agentClickSaveChangesButton();
    }

    @And("^'support hours' are updated in (.*) configs$")
    public void supportHoursAreUpdatedInTenantConfigs(String tenantOrgName) {
        Assert.assertFalse(ApiHelper.getAgentSupportDaysAndHours(tenantOrgName).toString().contains(nameOfUnchekedDay.toUpperCase()),"Error. 'support hours' contain today day.");
    }

    @Then("^Check that today day is unselected in 'Scheduled hours' pop up$")
    public void checkThatTodayDayIsUnselectedInScheduledHoursPopUp() {
        Assert.assertTrue(getPortalTouchPreferencesPage().getBusinessProfileWindow().isUncheckTodayDay(nameOfUnchekedDay.toUpperCase()),"Today  day was not been unchecked");
    }

    @When("^Turn (.*) the Last Agent routing$")
    public void changeLastAgentRoting(String status){
        if (status.equalsIgnoreCase("on")){
            ApiHelper.updateTenantConfig(Tenants.getTenantUnderTestOrgName(),"lastAgentMode", "true");
        } else if(status.equalsIgnoreCase("off")){
            ApiHelper.updateTenantConfig(Tenants.getTenantUnderTestOrgName(),"lastAgentMode", "false");
        }else{
            throw new AssertionError("Incorrect status was provided");
        }
    }

    @When("^Verify Last Agent routing is turned (.*) on backend$")
    public void verifyLastAgentRoting(String status) {
        boolean statusOnBackend = ApiHelper.getTenantConfig(Tenants.getTenantUnderTestOrgName()).getBody().jsonPath().get("lastAgentMode");
        if (status.equalsIgnoreCase("on")){
            Assert.assertTrue(statusOnBackend, "Last Agent Roting is not turned on");
        } else if(status.equalsIgnoreCase("off")){
            Assert.assertFalse(statusOnBackend, "Last Agent Roting is not turned off");
        }else{
            throw new AssertionError("Incorrect status was provided");
        }
    }

    @When("^Turn (.*) the Default department$")
    public void changeDepartmentPrimaryStatus(String status){
        if (status.equalsIgnoreCase("on")){
            ApiHelper.updateTenantConfig(Tenants.getTenantUnderTestOrgName(),"departmentPrimaryStatus", "true");
        } else if(status.equalsIgnoreCase("off")){
            ApiHelper.updateTenantConfig(Tenants.getTenantUnderTestOrgName(),"departmentPrimaryStatus", "false");
        }else{
            throw new AssertionError("Incorrect status was provided");
        }
    }

    @When("^Create chat tag$")
    public void createChatTag(){
        tagname = faker.artist().name() + faker.numerify("#####");
        getPortalTouchPreferencesPage().getChatTagsWindow().clickAddChatTagButton().setTagName(tagname).clickSaveButton();
    }

    @When("^Update chat tag")
    public void updateTag(){
        getPortalTouchPreferencesPage().getChatTagsWindow().clickEditTagButton(tagname);
        tagname = faker.artist().name() + faker.numerify("#####");
        getPortalTouchPreferencesPage().getChatTagsWindow().setTagName(tagname).clickSaveButton();
        AgentCRMTicketsSteps.crmTicketInfoForUpdating.get().put("agentTags",  tagname);
    }

    @When("^(?:Enable|Disable) tag$")
    public void disableTag(){
        getPortalTouchPreferencesPage().getChatTagsWindow().enableDisableTag(tagname);
    }

}
