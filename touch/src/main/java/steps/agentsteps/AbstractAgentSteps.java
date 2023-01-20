package steps.agentsteps;

import agentpages.AgentHomePage;
import agentpages.AgentLoginPage;
import agentpages.commonelements.SupervisorAndTicketsHeader;
import agentpages.leftmenu.LeftMenuWithChats;
import agentpages.tickets.TicketsPage;
import agentpages.uielements.*;
import apihelper.ApiHelper;
import com.github.javafaker.Faker;
import datamanager.jacksonschemas.dotcontrol.DotControlInitRequest;
import driverfactory.DriverFactory;
import steps.dotcontrol.DotControlSteps;
import steps.portalsteps.AbstractPortalSteps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbstractAgentSteps extends AbstractPortalSteps {

    private static final ThreadLocal<AgentLoginPage> secondAgentLoginPage = new ThreadLocal<>();

    private static final ThreadLocal<AgentLoginPage> mainAgentLoginPage = new ThreadLocal<>();

    private static final ThreadLocal<AgentHomePage> mainAgentHomePage = new ThreadLocal<>();

    private static final ThreadLocal<AgentHomePage> secondAgentHomePage = new ThreadLocal<>();

    private static final ThreadLocal<TicketsPage> ticketsPage = new ThreadLocal<>();

    public static Faker faker = new Faker();

    private static final ThreadLocal<String> clientIDGlobal = new ThreadLocal<>();

    public List<DotControlInitRequest> createdChatsViaDotControl = new ArrayList<>();

    private static final ThreadLocal<List<Map<String, String>>> createdAgentsMails = new ThreadLocal<>();

    public static List<Map<String, String>> getListOfCreatedAgents() {
        if (createdAgentsMails.get()==null) {
            createdAgentsMails.set(new ArrayList<Map<String, String>>());
        }
        return createdAgentsMails.get();
    }

    public static AgentLoginPage getAgentLoginPage(String ordinalAgentNumber) {
        if (ordinalAgentNumber.equalsIgnoreCase("second agent")){
            return getLoginForSecondAgent();
        } else
        {
            return getLoginForMainAgent();
        }
    }
    public static AgentLoginPage getLoginForSecondAgent() {
        if (secondAgentLoginPage.get()==null)
        {
            secondAgentLoginPage.set(new AgentLoginPage("second agent"));
        }
        return secondAgentLoginPage.get();
    }

    public static AgentLoginPage getLoginForMainAgent() {
        if (mainAgentLoginPage.get()==null) {
            mainAgentLoginPage.set(new AgentLoginPage("main"));
        }
        return mainAgentLoginPage.get();
    }

    public static AgentHomePage getAgentHomePage(String ordinalAgentNumber){
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

    public static LeftMenuWithChats getLeftMenu(String agent) {
        return getAgentHomePage(agent).getLeftMenuWithChats();
    }

    public static ChatBody getChatBody(String agent) {
        return getAgentHomePage(agent).getChatBody();
    }

    public static TicketsPage getTicketsPage(String agent){
        return getAgentHomePage(agent).getTicketsPage();
    }

    public static SupervisorAndTicketsHeader getSupervisorAndTicketsHeader(String agent){
        return getAgentHomePage(agent).getSupervisorAndTicketsHeader();
    }

    public static ChatAttachmentForm getChatAttachmentForm(String agent){
        return getAgentHomePage(agent).getChatAttachmentForm();
    }

    public static SuggestedGroup getSuggestedGroup(String agent) {
        return getAgentHomePage(agent).getSuggestedGroup();
    }

    public static Profile getUserProfileContainer(String agent){
        return getAgentHomePage(agent).getProfile();
    }

    public static PageHeader getPageHeader(String agent) {
        return getAgentHomePage(agent).getPageHeader();
    }

    public static ChatHeader getChatHeader(String agent){
        return getAgentHomePage(agent).getChatHeader();
    }
    public static void cleanAllPages(){
        secondAgentLoginPage.remove();
        mainAgentLoginPage.remove();

        mainAgentHomePage.remove();
        secondAgentHomePage.remove();
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