package steps.agentsteps;

import agentpages.AgentHomePage;
import agentpages.AgentLoginPage;
import agentpages.uielements.*;
import apihelper.ApiHelper;
import com.github.javafaker.Faker;
import datamanager.jacksonschemas.dotcontrol.DotControlInitRequest;
import driverfactory.DriverFactory;
import drivermanager.ConfigManager;
import steps.dotcontrol.DotControlSteps;
import steps.portalsteps.AbstractPortalSteps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbstractAgentSteps extends AbstractPortalSteps {

    private static ThreadLocal<AgentLoginPage> currentAgentLoginPage = new ThreadLocal<>();

    private static ThreadLocal<AgentLoginPage> secondAgentLoginPage = new ThreadLocal<>();

    private static ThreadLocal<AgentLoginPage> mainAgentLoginPage = new ThreadLocal<>();

    private static ThreadLocal<AgentHomePage> mainAgentHomePage = new ThreadLocal<>();

    private static ThreadLocal<AgentHomePage> currentAgentHomePage = new ThreadLocal<>();

    private static ThreadLocal<AgentHomePage> secondAgentHomePage = new ThreadLocal<>();

    public static Faker faker = new Faker();

    private static ThreadLocal<String> clientIDGlobal = new ThreadLocal<>();

    public List<DotControlInitRequest> createdChatsViaDotControl = new ArrayList<>();

    private static ThreadLocal<List<Map<String, String>>> createdAgentsMails = new ThreadLocal<>();

    public static void setAgentLoginPage(String ordinalAgentNumber, AgentLoginPage loginPage) {
        if (ordinalAgentNumber.equalsIgnoreCase("second agent")){
            secondAgentLoginPage.set(loginPage);
        } else {
            mainAgentLoginPage.set(loginPage);
        }
    }

    public static List<Map<String, String>> getListOfCreatedAgents() {
        if (createdAgentsMails.get()==null) {
            createdAgentsMails.set(new ArrayList<Map<String, String>>());
        }
        return createdAgentsMails.get();
    }

    public static AgentLoginPage getAgentLoginPage(String ordinalAgentNumber) {
        if (ordinalAgentNumber.equalsIgnoreCase("second agent")){
            return getLoginForSecondAgent();
        } else {
            return getLoginForMainAgent();
        }
    }

    private static AgentLoginPage getLoginForSecondAgent() {
        if (secondAgentLoginPage.get()==null) {
            secondAgentLoginPage.set(new AgentLoginPage("second agent"));
        }
        return secondAgentLoginPage.get();
    }

    private static AgentLoginPage getLoginForMainAgent() {
        if (mainAgentLoginPage.get()==null) {
            mainAgentLoginPage.set(new AgentLoginPage("main"));
        }
        return mainAgentLoginPage.get();
    }




    public static void setCurrentLoginPage(AgentLoginPage loginPage) {
        currentAgentLoginPage.set(loginPage);
    }

    public static AgentLoginPage getCurrentLoginPage() {
        return currentAgentLoginPage.get();
    }


    public static void getCurrentAgentHomePage(AgentHomePage homePage){
        currentAgentHomePage.set(homePage);
    }

    public static AgentHomePage setCurrentAgentHomePage(String ordinalAgentNumber){
        return currentAgentHomePage.get();
    }

    public static AgentHomePage getAgentHomePage(String ordinalAgentNumber){
        if (ordinalAgentNumber.equalsIgnoreCase("second agent")){
            return getAgentHomeForSecondAgent();
        } else {
            return getAgentHomeForMainAgent();
        }
    }

    public static AgentHomePage setAgentHomePage(String ordinalAgentNumber){
        if (ordinalAgentNumber.equalsIgnoreCase("second agent")){
            return getAgentHomeForSecondAgent();
        } else {
            return getAgentHomeForMainAgent();
        }
    }

    public static AgentHomePage getAgentHomeForSecondAgent(){
        if (secondAgentHomePage.get()==null) {
            secondAgentHomePage.set(new AgentHomePage("second agent"));
        }
        return secondAgentHomePage.get();
    }

    public static AgentHomePage getAgentHomeForMainAgent(){
        if (mainAgentHomePage.get()==null) {
            mainAgentHomePage.set(new AgentHomePage("main agent"));
        }
        return mainAgentHomePage.get();
    }

    public static ProfileWindow getProfileWindow(String ordinalAgentNumber){
            return getAgentHomePage(ordinalAgentNumber).getProfileWindow();
    }

    public static LeftMenuWithChats getLeftMenu(String agent) {
        return getAgentHomePage(agent).getLeftMenuWithChats();
    }

    public static ChatBody getChatBody(String agent) {
        return getAgentHomePage(agent).getChatBody();
    }

    public static ChatAttachmentForm getChatAttachmentForm(String agent){
        return getAgentHomePage(agent).getChatAttachmentForm();
    }

    public static SuggestedGroup getSuggestedGroup(String agent) {
        return getAgentHomePage(agent).getSuggestedGroup();
    }

    public static Profile getCustomer360Container(String agent){
        return getAgentHomePage(agent).getProfile();
    }

    public static PageHeader getPageHeader(String agent){
        return getAgentHomePage(agent).getPageHeader();
    }


    public static ChatHeader getChatHeader(String agent){
        return getAgentHomePage(agent).getChatHeader();
    }

    public static void cleanAllPages(){
        currentAgentLoginPage.remove();
        secondAgentLoginPage.remove();
        mainAgentLoginPage.remove();

        mainAgentHomePage.remove();
        currentAgentHomePage.remove();
        secondAgentHomePage.remove();
    }

    public String getUserName(String userFrom){
        if(userFrom.contains("first chat")){
            return createdChatsViaDotControl.get(0).getInitContext().getFullName();
        }
        if (ConfigManager.getSuite().equalsIgnoreCase("twitter")) {
            return super.getUserName("twitter");
        }
        if(ConfigManager.getSuite().equalsIgnoreCase("facebook")) {
            return super.getUserName("facebook");
        }
        if(userFrom.equalsIgnoreCase("dotcontrol")) {
            return super.getUserName("dotcontrol");
        }

        return getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance());
    }

    public synchronized void saveClientIDValue(String userFrom){
        if (userFrom.equalsIgnoreCase("facebook"))
            clientIDGlobal.set(socialaccounts.FacebookUsers.getFBTestUserFromCurrentEnv().getFBUserIDMsg().toString());
        else if (userFrom.equalsIgnoreCase("twitter"))
            clientIDGlobal.set(socialaccounts.TwitterUsers.getLoggedInUser().getDmUserId());
        else if (userFrom.equalsIgnoreCase("dotcontrol"))
            clientIDGlobal.set(DotControlSteps.getClientId());
        else
            clientIDGlobal.set(getAgentHomePage("main").getProfile().getUserFullName());
        DotControlSteps.setChatIDTranscript(ApiHelper
                .getActiveSessionByClientId(clientIDGlobal.get()).get("conversationId").toString());
    }

    public static String getClientIDGlobal(){
        return clientIDGlobal.get();
    }
}
