package steps;

import agentpages.AgentHomePage;
import apihelper.*;
import com.google.gson.JsonObject;
import datamanager.Agents;
import datamanager.MC2Account;
import datamanager.Tenants;
import datamanager.jacksonschemas.TenantChatPreferences;
import datamanager.jacksonschemas.supportHours.GeneralSupportHoursItem;
import driverfactory.DriverFactory;
import driverfactory.URLs;
import drivermanager.ConfigManager;
import emailhelper.GmailConnector;
import facebook.FBTenantPage;
import interfaces.JSHelper;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import javaserver.DotControlServer;
import mc2api.ApiHelperPlatform;
import mc2api.auth.PortalAuthToken;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import sqsreader.SQSConfiguration;
import sqsreader.SyncMessageReceiver;
import steps.agentsteps.AbstractAgentSteps;
import steps.dotcontrol.DotControlSteps;
import steps.portalsteps.AbstractPortalSteps;
import steps.portalsteps.BasePortalSteps;
import steps.portalsteps.TenantSteps;
import steps.tiesteps.BaseTieSteps;
import steps.tiesteps.TIEApiSteps;
import touchpages.pages.MainPage;
import touchpages.pages.Widget;
import twitter.TwitterTenantPage;
import twitter.uielements.DMWindow;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.NoSuchElementException;

import static io.restassured.RestAssured.given;

public class Hooks implements JSHelper {

    @Before
    public void beforeScenario(Scenario scenario){

        clearAllSessionData();

        if(scenario.getSourceTagNames().contains("@skip")){
            throw new PendingException();
        }

        if(scenario.getSourceTagNames().contains("@testing_env_only")&!ConfigManager.getEnv().equalsIgnoreCase("testing")){
            throw new PendingException("Designed to run only on testing env. " +
                    "On other envs the test may break the limit of sent activation emails and cause " +
                    "Clickatell to be billed for that");
        }

        if(scenario.getSourceTagNames().contains("@tie")){
                BaseTieSteps.request = new ByteArrayOutputStream();
                BaseTieSteps.response = new ByteArrayOutputStream();
        }

        if (scenario.getSourceTagNames().contains("@start_server")) {
                throw new PendingException();
        }

        if (scenario.getSourceTagNames().contains("@start_orca_server")) {
            System.setProperty("sqsuse", "true");
            new SyncMessageReceiver().startSQSReader();
        }
    }

    @After()
    public void afterScenario(Scenario scenario){

        if (scenario.getSourceTagNames().contains("@orca_api")){
            ORCASteps.cleanUPORCAData();
        }

        try {
            makeScreenshotAndConsoleOutputFromChatdesk(scenario);
            // add this catch since a lot of time while making screenshot there's an exception
            // and other important hooks are skipped
        } catch (Exception | AssertionError e) {
            System.out.println("Error on screenshot taking step:");
            e.printStackTrace();
        } try {

        System.out.println("Scenario: \"" + scenario.getName() + "\" has finished with status: " + scenario.getStatus());

        if(!scenario.getSourceTagNames().equals(Arrays.asList("@tie")) &&
                !scenario.getSourceTagNames().equals(Arrays.asList("@widget_visibility")) &&
                !scenario.getSourceTagNames().contains("@no_widget") &&
                !scenario.getSourceTagNames().contains("@facebook") &&
                !scenario.getSourceTagNames().contains("@twitter") &&
                !scenario.getSourceTagNames().contains("@healthcheck") &&
                !scenario.getSourceTagNames().contains("@camunda")){

            if(DriverFactory.isTouchDriverExists()) {
//                if (scenario.isFailed()) widgetWebSocketLogs();
                takeWebWidgetScreenshot();
                endTouchFlow(scenario, true);
            }
        }

        if (scenario.getSourceTagNames().contains("@support_hours")){
               ApiHelperSupportHours.setSupportDaysAndHours(new GeneralSupportHoursItem());
        }

        if (scenario.getSourceTagNames().contains("@delete_tenant_logo")){
               new TenantSteps().deleteLogo();
        }

        if(scenario.getSourceTagNames().contains(("@remove_dep"))){
            ApiHelperDepartments.deleteDepartmentsById(Tenants.getTenantUnderTestOrgName());
        }

        if(scenario.getSourceTagNames().contains("@new_agent") && scenario.isFailed()) {
            new BasePortalSteps().createNewAgent("existed",
                    MC2Account.TOUCH_GO_NEW_ACCOUNT.getTenantOrgName());
        }

        finishAgentFlowIfExists(scenario);

        if(scenario.getSourceTagNames().equals(Arrays.asList("@widget_visibility"))) {
            takeWebWidgetScreenshot();
            finishVisibilityFlow();
        }

        if(scenario.getSourceTagNames().contains("@facebook")){
            takeWebWidgetScreenshot();
            endFacebookFlow(scenario);
        }

        if(scenario.getSourceTagNames().contains("@twitter")){
            takeWebWidgetScreenshot();
            endTwitterFlow(scenario);
        }

        if(scenario.getSourceTagNames().contains("@tie")){
            endTieFlow(scenario);
        }

        if(scenario.getSourceTagNames().contains("@newagent")){
            if(BasePortalSteps.isNewUserWasCreated()) BasePortalSteps.deleteAgent();
        }

        if(scenario.getSourceTagNames().contains("@healthcheck")){
            takeWebWidgetScreenshot();
            endTouchFlow(scenario, true);
        }

        if(scenario.getSourceTagNames().contains("@dilinking_account")&&scenario.isFailed()){
            ApiHelper.delinkFBIntegration(Tenants.getTenantUnderTestOrgName());
        }

        if(scenario.getSourceTagNames().contains("@dot_control")){
            DotControlSteps.cleanUPMessagesInfo();
            APIHelperDotControl.deleteHTTPIntegrations(Tenants.getTenantUnderTestOrgName());
        }

        if(scenario.getSourceTagNames().contains("@start_server")){
            DotControlServer.stopServer();
        }

        if (scenario.getSourceTagNames().contains("@start_orca_server")) {
            System.setProperty("sqsuse", "false");
            SQSConfiguration.stopSQSReader();
        }

        if(scenario.getSourceTagNames().contains("@camunda")){
            takeWebWidgetScreenshot();
            endTouchFlow(scenario, true);
            try {
                ApiHelper.deleteUserProfile(Tenants.getTenantUnderTestName(), getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()));
            } catch (AssertionError e){
                System.out.println(e);
            }

        }

        if(scenario.getSourceTagNames().contains("@creating_intent")){
            TIEApiSteps.clearCreatedIntentAndSample();
        }

        if(scenario.getSourceTagNames().contains("@slot_management")){
            TIEApiSteps.clearCreatedSlots();
        }

        if(scenario.getSourceTagNames().contains("@chat_transcript")){
            //toDo update with scenarios
            GmailConnector.cleanMailObjects();
            String tenantOrgName = Tenants.getTenantUnderTestOrgName();
            String contactEmail = ApiHelper.getTenantInfoMap(tenantOrgName).get("contactEmail");
//            ApiHelper.updateTenantConfig(tenantOrgName, "supportEmail", "\"" + contactEmail + "\"");
//            ApiHelper.updateTenantConfig(tenantOrgName, "chatTranscript", "\"UNATTENDED_ONLY\"");
        }

        if (scenario.getSourceTagNames().contains("@off_survey_management")){
            ApiHelper.ratingEnabling(Tenants.getTenantUnderTestOrgName(), false,"webchat");
        }

        if(scenario.getSourceTagNames().contains("@off_rating_abc")) {
            ApiHelper.ratingEnabling(Tenants.getTenantUnderTestOrgName(), false,"abc");
        }

        if (scenario.getSourceTagNames().contains("@off_rating_whatsapp")) {
            ApiHelper.ratingEnabling(Tenants.getTenantUnderTestOrgName(), false, "whatsapp");
        }

        if (scenario.getSourceTagNames().contains("@off_rating_sms")) {
            ApiHelper.ratingEnabling(Tenants.getTenantUnderTestOrgName(), false, "sms");
        }


        if (scenario.getSourceTagNames().contains("@close_account")){
            ApiHelperPlatform.closeMC2Account(Tenants.getTenantUnderTestOrgName());
        }

        closeMainBrowserIfOpened();
        clearAllSessionData();

        }catch ( Exception | AssertionError e) {
            e.printStackTrace();
            closeBrowserInCaseOfException();
        }
    }

    private void takeWebWidgetScreenshot() {
        if (DriverFactory.isTouchDriverExists()) {
            takeScreenshot(DriverFactory.getTouchDriverInstance());
        }
    }

    private void takeFirstAgentDriverScreenshot() {
        takeScreenshot(DriverFactory.getAgentDriverInstance());
    }

    private void takeSecondAgentDriverScreenshot() {
        takeScreenshot(DriverFactory.getSecondAgentDriverInstance());
    }

    private void takeScreenshot(WebDriver driver) {
        Allure.addAttachment("Screenshot",new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }


    private void makeScreenshotAndConsoleOutputFromChatdesk(Scenario scenario){
        if (DriverFactory.isAgentDriverExists()) {
            takeFirstAgentDriverScreenshot();
//            if (scenario.isFailed()) {
//                chatDeskConsoleOutput();
//                chatdeskWebSocketLogs();
//            }
        }
        if (DriverFactory.isSecondAgentDriverExists()) {
                takeSecondAgentDriverScreenshot();
//                if (scenario.isFailed()) {
//                    secondAgentChatDeskConsoleOutput();
//                    secondAgentChatdeskWebSocketLogs();
//                }
        }
    }

    private void finishAgentFlowIfExists(Scenario scenario) {

        if (scenario.getSourceTagNames().contains("@delete_agent_on_failure")&&scenario.isFailed()){
            String userID = ApiHelperPlatform.getUserID(Tenants.getTenantUnderTestOrgName(),
                    Agents.TOUCH_GO_SECOND_AGENT.getAgentEmail());
            ApiHelperPlatform.deleteUser(Tenants.getTenantUnderTestOrgName(), userID);
            ConfigManager.setIsSecondCreated("false");
        }

        if (DriverFactory.isAgentDriverExists()) {
            if (scenario.getSourceTagNames().contains("@agent_info")) {
                new AgentHomePage("main").getProfileWindow().closeIfOpened();
            }
            if (scenario.getSourceTagNames().contains("@inactivity_timeout")) {
                TenantChatPreferences tenantChatPreferences = ApiHelper.getTenantChatPreferences();
                tenantChatPreferences.setAgentInactivityTimeoutSec(600);
                ApiHelper.updateTenantConfig(Tenants.getTenantUnderTestOrgName(), tenantChatPreferences);
            }

            if (scenario.getSourceTagNames().contains("@agent_availability")&&scenario.isFailed()){
                //ToDo: replace with API call if appropriate exists
                    AgentHomePage agentHomePage = new AgentHomePage("main agent");
                    agentHomePage.getPageHeader().clickIcon();
                    agentHomePage.getPageHeader().selectStatus("available");
                    agentHomePage.getPageHeader().clickIcon();
            }
            if(!scenario.getSourceTagNames().contains("@no_chatdesk") || scenario.getSourceTagNames().contains("@orca_api")){
                closePopupsIfOpenedEndChatAndlogoutAgent("main agent");}

            if(scenario.getSourceTagNames().contains("@sign_up")){
                newAccountInfo();
            }

            if (scenario.getSourceTagNames().contains("@setting_changes")){
                ApiHelper.updateChatPreferencesParameter(TenantChatPreferences.getDefaultTenantChatPreferences());
            }

            if (scenario.getSourceTagNames().contains("@widget_disabling")){
                ApiHelper.setIntegrationStatus(Tenants.getTenantUnderTestOrgName(), "webchat", true);
            }

            DriverFactory.getAgentDriverInstance().manage().deleteAllCookies();
            DriverFactory.closeAgentBrowser();

        }
        if (DriverFactory.isSecondAgentDriverExists()) {
            if(!scenario.getSourceTagNames().contains("@no_chatdesk") || scenario.getSourceTagNames().contains("@orca_api"))
            {closePopupsIfOpenedEndChatAndlogoutAgent("second agent");}
            if (scenario.getSourceTagNames().contains("@agent_availability")&&scenario.isFailed()){
                //ToDo: replace with API call if appropriate exists
                AgentHomePage agentHomePage = new AgentHomePage("second agent");
                agentHomePage.getPageHeader().clickIcon();
                agentHomePage.getPageHeader().selectStatus("available");
                agentHomePage.getPageHeader().clickIcon();
            }
            DriverFactory.getSecondAgentDriverInstance().manage().deleteAllCookies();
            DriverFactory.closeSecondAgentBrowser();
        }
    }

    private void endTouchFlow(Scenario scenario, boolean typeEndInWidget) {
        if (DriverFactory.isTouchDriverExists()) {
            userName();
            if(scenario.getSourceTagNames().equals(Arrays.asList("@collapsing"))) {
                new MainPage().openWidget();
            }
            try {
                if (scenario.isFailed()) {
                    touchConsoleOutput();
                }
        }catch (WebDriverException e) { }
        }
    }

    public void closeWidgetSession(){
        Widget widget = new Widget("withoutWait");
        widget.getWidgetFooter().tryToCloseSession();
    }

    private void closePopupsIfOpenedEndChatAndlogoutAgent(String agent) {
        try {
            AgentHomePage agentHomePage = new AgentHomePage(agent);
            ApiHelper.closeActiveChats(agent);
            //            ApiHelper.logoutTheAgent(Tenants.getTenantUnderTestOrgName()); commented out because API not working now
            agentHomePage.getPageHeader().logOut();
        } catch(WebDriverException|AssertionError|NoSuchElementException e){
        }
    }

    private void finishVisibilityFlow() {
        try {
            ApiHelper.deleteUserProfile(Tenants.getTenantUnderTestName(), getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()));
        } catch (AssertionError e) {
            System.out.println(e + "maybe client was not created");
            DriverFactory.getTouchDriverInstance().close();
        }
//        ApiHelper.setWidgetVisibilityDaysAndHours(Tenants.getTenantUnderTestOrgName(), "all week", "00:00", "23:59");
        ApiHelper.setAvailableForAllTerritories(Tenants.getTenantUnderTestOrgName());
    }

    private void endFacebookFlow(Scenario scenario) {
        FBTenantPage fbTenantPage = new FBTenantPage(DriverFactory.getTouchDriverInstance());
            try {
                if(scenario.getSourceTagNames().contains("@fb_dm")) {
                    fbTenantPage.getMessengerWindow().deleteConversation();
                }
                if(scenario.getSourceTagNames().contains("@fb_post")){
                    fbTenantPage.getPostFeed().endSessionIfPostFeedIsShown();
                    fbTenantPage.getFBYourPostPage().deletePost();
                }

            } catch (WebDriverException e) { }
        }

    private void closeMainBrowserIfOpened() {
        if (DriverFactory.isTouchDriverExists()) {
            DriverFactory.closeTouchBrowser();
        }
    }

    private void closeBrowserInCaseOfException() {
        if (DriverFactory.isTouchDriverExists()) {
            DriverFactory.closeTouchBrowser();
        }
        if (DriverFactory.isAgentDriverExists()) {
            DriverFactory.closeAgentBrowser();
        }
        if (DriverFactory.isSecondAgentDriverExists()) {
            DriverFactory.closeSecondAgentBrowser();
        }
    }


    private void endTwitterFlow(Scenario scenario) {
        TwitterTenantPage twitterTenantPage = new TwitterTenantPage(DriverFactory.getTouchDriverInstance());
//        Commented out because for now tweets are not working stable
//        try {
//            if(scenario.isFailed()){
//                TweetsSection tweetsSection =  new TweetsSection();
//                if(tweetsSection.getOpenedTweet().isDisplayed()){
//                    tweetsSection.getOpenedTweet().clickSendReplyButton();
//                }
//            }
//        } catch (WebDriverException e) {}
//        TwitterAPI.deleteToTestUserTweets();
//        TwitterAPI.deleteTweetsFromTestUser();
        try {
            if(twitterTenantPage.isDMWindowOpened()) {
                DMWindow dmWindow = twitterTenantPage.getDmWindow();
                dmWindow.deleteConversation();
            }
        } catch (WebDriverException e) {}
    }

    private void endTieFlow(Scenario scenario) {
            if (!TIEApiSteps.getNewTenantNames().isEmpty()) {
                for (long thread : TIEApiSteps.getNewTenantNames().keySet()) {
                    if (thread == Thread.currentThread().getId()) {
                        String url = String.format(Endpoints.TIE_DELETE_TENANT, TIEApiSteps.getNewTenantNames().get(thread));
                        given().delete(url);
                        TIEApiSteps.getNewTenantNames().remove(thread);
                    }
                }
            }
    }

    private void clearAllSessionData(){
        Tenants.clearTenantUnderTest();
        PortalAuthToken.clearAccessTokenForPortalUser();
        TouchAuthToken.clearAccessTokenForPortalUser();
        URLs.clearFinalAgentURL();
        AbstractAgentSteps.cleanAllPages();
        AbstractPortalSteps.cleanAllPortalPages();
        DefaultTouchUserSteps.mediaFileName.remove();
        DotControlSteps.mediaFileName.remove();
        ApiHelper.clientProfileId.remove();
        CamundaFlowsSteps.updatedMessage.remove();
        CamundaFlowsSteps.defaultMessage.remove();
    }

    private byte[] attach(ByteArrayOutputStream log) {
        byte[] array = log.toByteArray();
        log.reset();
        return array;
    }

    @Attachment
    private String userName(){
        return getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance());
    }

    @Attachment
    private String touchConsoleOutput(){
        StringBuilder result = new StringBuilder();
        LogEntries logEntries = DriverFactory.getTouchDriverInstance().manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            result.append(new Date(entry.getTimestamp())).append(", ").append(entry.getLevel()).append(", ").append(entry.getMessage()).append(";  \n");
        }
        return  result.toString();
    }

    @Attachment
    private String chatDeskConsoleOutput(){
        StringBuffer buffer = new StringBuffer();
        LogEntries logEntries = DriverFactory.getAgentDriverInstance().manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            buffer.append(new Date(entry.getTimestamp())).append(", ").append(entry.getLevel()).append(", ").append(entry.getMessage()).append(";  \n");
        }
        return  buffer.toString();
    }

    @Attachment
    private String secondAgentChatDeskConsoleOutput(){
        StringBuilder result = new StringBuilder();
        LogEntries logEntries = DriverFactory.getSecondAgentDriverInstance().manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            result.append(new Date(entry.getTimestamp())).append(", ").append(entry.getLevel()).append(", ").append(entry.getMessage()).append(";  \n");
        }
        return  result.toString();
    }

    private String getWebSocketLogs(WebDriver driver){
        StringBuilder result = new StringBuilder();
        try {
        LogEntries logEntries = driver.manage().logs().get(LogType.PERFORMANCE);
        for (LogEntry entry : logEntries) {

            JsonObject messageJSON = null;
            JsonObject msg = null;

                messageJSON = new JsonObject().getAsJsonObject(entry.getMessage());
                msg  = messageJSON.getAsJsonObject("message");
                String method = msg.getAsString();
                if(method.contains("Network.webSocket") ){
                    if(!msg.get("params").toString().contains("ping")) {
                        result.append(msg.toString()).append(";  \n\n");
                    }
                }
        }
        } catch (Exception e) {
            e.printStackTrace();
            result.append(e);
        }
        return  result.toString();
    }

    @Attachment
    private String newAccountInfo(){
        return MC2Account.TOUCH_GO_NEW_ACCOUNT.toString();
    }
}
