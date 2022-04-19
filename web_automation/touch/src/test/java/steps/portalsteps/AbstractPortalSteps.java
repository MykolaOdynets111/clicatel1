package steps.portalsteps;

import agentpages.dashboard.DashboardSettingsPage;
import agentpages.supervisor.SupervisorDeskPage;
import agentpages.dashboard.DashboardPage;
import agentpages.survey.SurveyManagementPage;
import apihelper.ApiHelper;
import com.github.javafaker.Faker;
import datamanager.Tenants;
import driverfactory.DriverFactory;
import interfaces.DateTimeHelper;
import interfaces.JSHelper;
import interfaces.VerificationHelper;
import interfaces.WebWait;
import org.openqa.selenium.WebDriver;
import portalpages.*;
import steps.ORCASteps;
import steps.dotcontrol.DotControlSteps;

import java.util.Map;

public class AbstractPortalSteps implements JSHelper, DateTimeHelper, VerificationHelper, WebWait {

    private static ThreadLocal<PortalLoginPage> currentPortalLoginPage = new ThreadLocal<>();

    private static ThreadLocal<PortalLoginPage> portalLoginPage = new ThreadLocal<>();

    private static ThreadLocal<PortalLoginPage> secondAgentPortalLoginPage = new ThreadLocal<>();

    private static ThreadLocal<PortalMainPage> portalMainPage = new ThreadLocal<>();

    private static ThreadLocal<PortalMainPage> secondPortalMainPage = new ThreadLocal<>();


    private static ThreadLocal<PortalTouchPreferencesPage> portalTouchPreferencesPage = new ThreadLocal<>();


    private static ThreadLocal<DashboardPage> dashboardPage = new ThreadLocal<>();

    private static ThreadLocal<SupervisorDeskPage> chatConsoleInboxPage = new ThreadLocal<>();

    private static ThreadLocal<DepartmentsManagementPage> departmentsManagementPage = new ThreadLocal<>();

    private static ThreadLocal<SurveyManagementPage> surveyManagementPage = new ThreadLocal<>();

    private static ThreadLocal<DashboardSettingsPage> dashboardSettingsPage = new ThreadLocal<>();


    public static Faker faker = new Faker();

    // -- Getters and Setters -- //

    public static void setCurrentPortalLoginPage(PortalLoginPage loginPage) {
        currentPortalLoginPage.set(loginPage);
    }

    public static PortalLoginPage getCurrentPortalLoginPage() {
        return currentPortalLoginPage.get();
    }

    public String getUserName(String channel){
        String userName=null;
        switch (channel.toLowerCase()){
            case "touch":
                userName = getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance());
                break;
            case "dotcontrol":
                userName = DotControlSteps.getClient();
                break;
            case "orca":
                userName = ORCASteps.getClientId();
                break;
            default: throw new AssertionError("Incorrect channel name was provided: " + userName);
        }
        return userName;
    }

    public String getAgentName(String agent){
        Map<String, String> agentInfo = ApiHelper.getAgentInfo(Tenants.getTenantUnderTestOrgName(), agent);
        return agentInfo.get("fullName");
    }

    public static PortalLoginPage getPortalLoginPage(String agent) {
        WebDriver driver = DriverFactory.getDriverForAgent(agent);
        if (agent.equalsIgnoreCase("second agent")) {
            return getSecondPortalLoginPage(driver);
        } else {
            return getMainPortalLoginPage(driver);
        }
    }

    public static PortalLoginPage getMainPortalLoginPage(WebDriver driver){
        if (portalLoginPage.get()==null) {
            portalLoginPage.set(new PortalLoginPage(driver));
            return portalLoginPage.get();
        } else{
            return portalLoginPage.get();
        }
    }

    public static PortalLoginPage getSecondPortalLoginPage(WebDriver driver){
        if (secondAgentPortalLoginPage.get()==null) {
            secondAgentPortalLoginPage.set(new PortalLoginPage(driver));
            return secondAgentPortalLoginPage.get();
        } else{
            return secondAgentPortalLoginPage.get();
        }
    }


    public static PortalMainPage getAdminPortalMainPage() {
        if (portalMainPage.get()==null) {
            portalMainPage.set(new PortalMainPage(DriverFactory.getDriverForAgent("admin")));
            return portalMainPage.get();
        } else{
            return portalMainPage.get();
        }
    }

    public static synchronized PortalMainPage getPortalMainPage(String agent) {
        if (agent.equalsIgnoreCase("second agent")) {
            return getSecondPortalMainPage();
        } else {
            return getAdminPortalMainPage();
        }
    }
    public static PortalMainPage getSecondPortalMainPage() {
        if (secondPortalMainPage.get()==null) {
            secondPortalMainPage.set(new PortalMainPage(DriverFactory.getDriverForAgent("second agent")));
            return secondPortalMainPage.get();
        } else{
            return secondPortalMainPage.get();
        }
    }

    public static void setPortalMainPage(PortalMainPage mainPage) {
       portalMainPage.set(mainPage);
    }


    public static PortalTouchPreferencesPage getPortalTouchPreferencesPage() {
        if (portalTouchPreferencesPage.get()==null) {
            portalTouchPreferencesPage.set(new PortalTouchPreferencesPage(DriverFactory.getDriverForAgent("admin")));
            return portalTouchPreferencesPage.get();
        } else{
            return portalTouchPreferencesPage.get();
        }
    }

    public static void setPortalTouchPreferencesPage(PortalTouchPreferencesPage touchPreferencesPage) {
        portalTouchPreferencesPage.set(touchPreferencesPage);
    }


    public static DashboardPage getDashboardPage() {
        if (dashboardPage.get()==null) {
            dashboardPage.set(new DashboardPage(DriverFactory.getDriverForAgent("admin")));
            return dashboardPage.get();
        } else{
            return dashboardPage.get();
        }
    }


    public static SupervisorDeskPage getSupervisorDeskPage() {
        if (chatConsoleInboxPage.get()==null) {
            chatConsoleInboxPage.set(new SupervisorDeskPage(DriverFactory.getDriverForAgent("admin")));
           // ToDo decrease time for spinner wait
            chatConsoleInboxPage.get().waitForConnectingDisappear(3, 10);
            return chatConsoleInboxPage.get();
        } else{
            return chatConsoleInboxPage.get();
        }
    }

    public static DepartmentsManagementPage getDepartmentsManagementPage(){
        if (departmentsManagementPage.get()==null) {
            departmentsManagementPage.set(new DepartmentsManagementPage(DriverFactory.getDriverForAgent("admin")));
            return departmentsManagementPage.get();
        } else{
            return departmentsManagementPage.get();
        }
    }

    public static SurveyManagementPage getSurveyManagementPage(){
        if (surveyManagementPage.get()==null) {
            surveyManagementPage.set(new SurveyManagementPage(DriverFactory.getDriverForAgent("admin")));
            return surveyManagementPage.get();
        } else{
            return surveyManagementPage.get();
        }
    }

    public static DashboardSettingsPage getDashboardSettingsPage(){
        if (dashboardSettingsPage.get() == null) {
            dashboardSettingsPage.set(new DashboardSettingsPage(DriverFactory.getDriverForAgent("admin")));
            return dashboardSettingsPage.get();
        } else {
            return dashboardSettingsPage.get();
        }
    }

    public static void cleanAllPortalPages(){
        currentPortalLoginPage.remove();
        portalLoginPage.remove();
        secondAgentPortalLoginPage.remove();
        portalMainPage.remove();
        secondPortalMainPage.remove();
        portalTouchPreferencesPage.remove();
        dashboardPage.remove();
        chatConsoleInboxPage.remove();
        departmentsManagementPage.remove();
        surveyManagementPage.remove();
        dashboardSettingsPage.remove();
    }
}
