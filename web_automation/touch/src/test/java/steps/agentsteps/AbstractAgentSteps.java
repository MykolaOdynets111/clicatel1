package steps.agentsteps;

import agentpages.AgentHomePage;
import agentpages.AgentLoginPage;
import agentpages.uielements.*;
import com.github.javafaker.Faker;
import interfaces.*;

public class AbstractAgentSteps implements JSHelper, DateTimeHelper, VerificationHelper, WebWait {

    private static ThreadLocal<AgentLoginPage> currentAgentLoginPage = new ThreadLocal<>();

    private static ThreadLocal<AgentLoginPage> secondAgentLoginPage = new ThreadLocal<>();

    private static ThreadLocal<AgentLoginPage> mainAgentLoginPage = new ThreadLocal<>();

    private static ThreadLocal<AgentHomePage> mainAgentHomePage = new ThreadLocal<>();

    private static ThreadLocal<AgentHomePage> currentAgentHomePage = new ThreadLocal<>();

    private static ThreadLocal<AgentHomePage> secondAgentHomePage = new ThreadLocal<>();

    public static Faker faker = new Faker();

    public static void setAgentLoginPage(String ordinalAgentNumber, AgentLoginPage loginPage) {
        if (ordinalAgentNumber.equalsIgnoreCase("second agent")){
            secondAgentLoginPage.set(loginPage);
        } else {
            mainAgentLoginPage.set(loginPage);
        }
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

    public static SuggestedGroup getSuggestedGroup(String agent) {
        return getAgentHomePage(agent).getSuggestedGroup();
    }

    public static Customer360Container getCustomer360Container(String agent){
        return getAgentHomePage(agent).getCustomer360Container();
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

}
