package steps;

import agentpages.AgentHomePage;
import apihelper.*;
import com.google.gson.JsonObject;
import datamanager.Agents;
import datamanager.MC2Account;
import datamanager.Tenants;
import datamanager.jacksonschemas.CRMTicket;
import driverfactory.DriverFactory;
import driverfactory.URLs;
import drivermanager.ConfigManager;
import emailhelper.GmailConnector;;
import interfaces.JSHelper;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.Scenario;
import io.qameta.allure.Attachment;
import io.restassured.response.Response;
import javaserver.DotControlServer;
import javaserver.OrcaServer;
import mc2api.ApiHelperPlatform;
import mc2api.auth.PortalAuthToken;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import steps.agentsteps.AbstractAgentSteps;
import steps.agentsteps.AgentCRMTicketsSteps;
import steps.agentsteps.DefaultAgentSteps;
import steps.dotcontrol.DotControlSteps;
import steps.portalsteps.AbstractPortalSteps;
import steps.portalsteps.BasePortalSteps;
import steps.tiesteps.BaseTieSteps;
import steps.tiesteps.TIEApiSteps;
import touchpages.pages.MainPage;
import touchpages.pages.Widget;

import java.io.ByteArrayOutputStream;
import java.util.*;

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
            new DotControlServer().startServer();
            ApiHelper.waitForServerToBeReady();
        }

        if (scenario.getSourceTagNames().contains("@start_orca_server")) {
            new OrcaServer().startServer();
            ApiHelper.waitForServerToBeReady();
        }
    }

    @After()
    public void afterScenario(Scenario scenario){

        try {
            makeScreenshotAndConsoleOutputFromChatdesk(scenario);
            // add this catch since a lot of time while making screenshot there's an exception
            // and other important hooks are skipped
        } catch (Exception | AssertionError e) {
            e.printStackTrace();
        } try {

        System.out.println("Scenario: \"" + scenario.getName() + "\" has finished with status: " + scenario.getStatus());

        if(!scenario.getSourceTagNames().equals(Arrays.asList("@tie")) &&
                !scenario.getSourceTagNames().equals(Arrays.asList("@widget_visibility")) &&
                !scenario.getSourceTagNames().contains("@no_widget") &&
                !scenario.getSourceTagNames().contains("@healthcheck") &&
                !scenario.getSourceTagNames().contains("@camunda")){

            if(DriverFactory.isTouchDriverExists()) {
                if (scenario.isFailed()) widgetWebSocketLogs();
                takeScreenshot();
                endTouchFlow(scenario, true);
            }
        }

        if(scenario.getSourceTagNames().contains("@agent_support_hours")){
            Response resp = ApiHelper.setAgentSupportDaysAndHours(Tenants.getTenantUnderTestOrgName(), "all week", "00:00", "23:59");
//ToDo Update API after it will be ready
//            String agent;
//            if(DriverFactory.isSecondAgentDriverExists()) agent = "second agent";
//            else agent = "main";
//            ApiHelper.closeAllOvernightTickets(Tenants.getTenantUnderTestOrgName(), agent);
            if(resp.statusCode()!=200) {
                supportHoursUpdates(resp);
            }
        }

        if(scenario.getSourceTagNames().contains("@auto_scheduler_disabled")){
            ApiHelper.updateTenantConfig(Tenants.getTenantUnderTestOrgName(), "autoSchedulingEnabled", "true");
        }

        if(scenario.getSourceTagNames().contains(("@remove_dep"))){
            ApiHelper.deleteDepartmentsById(Tenants.getTenantUnderTestOrgName());
        }

        if(scenario.getSourceTagNames().contains("@agent_session_capacity")){
            ApiHelper.updateSessionCapacity(Tenants.getTenantUnderTestOrgName(), 50);
        }

        if(scenario.getSourceTagNames().contains("@new_agent") && scenario.isFailed()) {
            new BasePortalSteps().createNewAgent("existed",
                    MC2Account.TOUCH_GO_NEW_ACCOUNT.getTenantOrgName());
        }

        finishAgentFlowIfExists(scenario);

        if(scenario.getSourceTagNames().equals(Arrays.asList("@widget_visibility"))) {
            takeScreenshot();
            finishVisibilityFlow();
        }

        if(scenario.getSourceTagNames().contains("@tie")){
            endTieFlow(scenario);
        }

        if(scenario.getSourceTagNames().contains("@newagent")){
            //todo add the agent removing function
//            if(BasePortalSteps.isNewUserWasCreated()) BasePortalSteps.deleteAgent();
        }

        if(scenario.getSourceTagNames().contains("@healthcheck")){
            takeScreenshot();
            endTouchFlow(scenario, true);
        }

        if(scenario.getSourceTagNames().contains("@dot_control")){
            DotControlSteps.cleanUPMessagesInfo();
            APIHelperDotControl.deleteHTTPIntegrations(Tenants.getTenantUnderTestOrgName());
        }

        if(scenario.getSourceTagNames().contains("@start_server")){
            DotControlServer.stopServer();
            ApiHelper.waitForServerToBeClosed();
        }

        if (scenario.getSourceTagNames().contains("@start_orca_server")) {
            OrcaServer.stopServer();
            ApiHelper.waitForServerToBeClosed();
        }

        if(scenario.getSourceTagNames().contains("@camunda")){
            takeScreenshot();
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
            GmailConnector.cleanMailObjects();
            String tenantOrgName = Tenants.getTenantUnderTestOrgName();
            String contactEmail = ApiHelper.getTenantInfoMap(tenantOrgName).get("contactEmail");
            ApiHelper.updateTenantConfig(tenantOrgName, "supportEmail", "\"" + contactEmail + "\"");
            ApiHelper.updateTenantConfig(tenantOrgName, "chatTranscript", "\"UNATTENDED_ONLY\"");
        }

        if (scenario.getSourceTagNames().contains("@off_survey_management")){
            ApiHelper.ratingEnabling(Tenants.getTenantUnderTestOrgName(), false,"webchat");
        }

        if(scenario.getSourceTagNames().contains("@off_rating_abc")) {
            ApiHelper.ratingEnabling(Tenants.getTenantUnderTestOrgName(), false,"abc");
        }


        if (scenario.getSourceTagNames().contains("@orca_api")){
            ORCASteps.cleanUPORCAData();
        }

        closeMainBrowserIfOpened();
        clearAllSessionData();

        }catch ( Exception | AssertionError e) {
            e.printStackTrace();
            closeBrowserInCaseOfException();
        }
    }


    @Attachment(value = "Screenshot")
    private byte[] takeScreenshot() {
        if (DriverFactory.isTouchDriverExists()) {
            return ((TakesScreenshot) DriverFactory.getTouchDriverInstance()).getScreenshotAs(OutputType.BYTES);
        } else{
            return null;
        }
    }

    @Attachment(value = "Screenshot")
    private byte[] takeScreenshotFromSecondDriver() {
        return ((TakesScreenshot) DriverFactory.getAgentDriverInstance()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Screenshot")
    private byte[] takeScreenshotFromThirdDriverIfExists() {
        return ((TakesScreenshot) DriverFactory.getSecondAgentDriverInstance()).getScreenshotAs(OutputType.BYTES);
    }

    private void makeScreenshotAndConsoleOutputFromChatdesk(Scenario scenario){
        if (DriverFactory.isAgentDriverExists()) {
            takeScreenshotFromSecondDriver();
            if (scenario.isFailed()) {
                chatDeskConsoleOutput();
                chatdeskWebSocketLogs();
            }
        }
        if (DriverFactory.isSecondAgentDriverExists()) {
                takeScreenshotFromThirdDriverIfExists();
                if (scenario.isFailed()) {
                    secondAgentChatDeskConsoleOutput();
                    secondAgentChatdeskWebSocketLogs();
                }
        }
    }


    private void finishAgentFlowIfExists(Scenario scenario) {

        if (DriverFactory.isAgentDriverExists()) {
            if (scenario.getSourceTagNames().contains("@agent_info")) {
                new AgentHomePage("main").getProfileWindow().closeIfOpened();
            }
            if (scenario.getSourceTagNames().contains("@inactivity_timeout")) {
                ApiHelper.updateTenantConfig(Tenants.getTenantUnderTestOrgName(), "agentInactivityTimeoutSec", "600");
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

            if(scenario.getSourceTagNames().contains("@sign_up")) newAccountInfo();

            if (scenario.getSourceTagNames().contains("@suggestions")){
                boolean pretestFeatureStatus = DefaultAgentSteps.getPreTestFeatureStatus("AGENT_ASSISTANT");
                if(pretestFeatureStatus != DefaultAgentSteps.getTestFeatureStatusChanging("AGENT_ASSISTANT")) {
                    ApiHelper.updateFeatureStatus(Tenants.getTenantUnderTestOrgName(), "AGENT_ASSISTANT", Boolean.toString(pretestFeatureStatus));
                }
            }

            if (scenario.getSourceTagNames().contains("@agent_feedback")){
                try{
                    boolean pretestFeatureStatus = DefaultAgentSteps.getPreTestFeatureStatus("AGENT_FEEDBACK");
                    if (pretestFeatureStatus != DefaultAgentSteps.getTestFeatureStatusChanging("AGENT_FEEDBACK")) {
                        ApiHelper.updateFeatureStatus(Tenants.getTenantUnderTestOrgName(), "AGENT_FEEDBACK", Boolean.toString(pretestFeatureStatus));
                    }
                }catch(NullPointerException e){
                    //no feature status interaction
                }
                if(AgentCRMTicketsSteps.getCreatedCRMTicket()!=null){
                    ApiHelper.deleteCRMTicket(AgentCRMTicketsSteps.getCreatedCRMTicket().getId());
                }
                if(AgentCRMTicketsSteps.getCreatedCRMTicketsList()!=null){
                    for(CRMTicket ticket: AgentCRMTicketsSteps.getCreatedCRMTicketsList()){
                        ApiHelper.deleteCRMTicket(ticket.getId());
                    }
                }
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
//        TIEApiSteps.clearTenantNames();
//        logRequest(BaseTieSteps.request);
//        logResponse(BaseTieSteps.response);
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
    }

    @Attachment(value = "request")
    public byte[] logRequest(ByteArrayOutputStream stream) {
        return attach(stream);
    }

    @Attachment(value = "response")
    public byte[] logResponse(ByteArrayOutputStream stream) {
        return attach(stream);
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

    @Attachment
    private String chatdeskWebSocketLogs(){
        return getWebSocketLogs(DriverFactory.getAgentDriverInstance());
    }

    @Attachment
    private String secondAgentChatdeskWebSocketLogs(){
        return getWebSocketLogs(DriverFactory.getSecondAgentDriverInstance());
    }

    @Attachment
    private String widgetWebSocketLogs(){
        return getWebSocketLogs(DriverFactory.getTouchDriverInstance());
    }

    @Attachment
    private String supportHoursUpdates(Response resp){
        StringBuffer buffer = new StringBuffer();
        buffer.append(resp.statusCode()).append("\n").append(resp.getBody().asString()).append("\n");
        return  buffer.toString();
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


    @Attachment
    private String newAgent(){
        return BasePortalSteps.AGENT_FIRST_NAME + "\n"
                + BasePortalSteps.AGENT_LAST_NAME + "\n"
                + BasePortalSteps.AGENT_EMAIL;
    }

}
