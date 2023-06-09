package steps.portalsteps;

import agentpages.dashboard.DashboardPage;
import agentpages.dashboard.DashboardSettingsPage;
import agentpages.supervisor.SupervisorDeskPage;
import agentpages.survey.SurveyManagementPage;
import apihelper.ApiHelper;
import com.github.javafaker.Faker;
import datamanager.Tenants;
import driverfactory.DriverFactory;
import interfaces.JSHelper;
import interfaces.VerificationHelper;
import interfaces.WebWait;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;
import portalpages.*;
import portaluielem.LeftMenu;
import steps.ORCASteps;
import steps.dotcontrol.DotControlSteps;

import java.util.Map;

public class AbstractPortalSteps implements JSHelper, VerificationHelper, WebWait {

    private static final ThreadLocal<PortalLoginPage> currentPortalLoginPage = new ThreadLocal<>();
    private static final ThreadLocal<PortalLoginPage> portalLoginPage = new ThreadLocal<>();
    private static final ThreadLocal<PortalLoginPage> secondAgentPortalLoginPage = new ThreadLocal<>();
    private static final ThreadLocal<PortalMainPage> portalMainPage = new ThreadLocal<>();
    private static final ThreadLocal<PortalMainPage> secondPortalMainPage = new ThreadLocal<>();
    private static final ThreadLocal<PortalTouchIntegrationsPage> portalIntegrationsPage = new ThreadLocal<>();
    private static final ThreadLocal<PortalBillingDetailsPage> portalBillingDetailsPage = new ThreadLocal<>();
    private static final ThreadLocal<PortalSignUpPage> portalSignUpPage = new ThreadLocal<>();
    private static final ThreadLocal<PortalAccountDetailsPage> portalAccountDetailsPage = new ThreadLocal<>();
    private static final ThreadLocal<PortalFBIntegrationPage> portalFBIntegrationPage = new ThreadLocal<>();
    private static final ThreadLocal<PortalManageAgentUsersPage> portalManagingUsersPage = new ThreadLocal<>();
    private static final ThreadLocal<PortalUserEditingPage> portalUserProfileEditingPage = new ThreadLocal<>();
    private static final ThreadLocal<PortalTouchPreferencesPage> portalTouchPreferencesPage = new ThreadLocal<>();
    private static final ThreadLocal<PortalUserManagementPage> portalUserManagementPage = new ThreadLocal<>();
    private static final ThreadLocal<DashboardPage> dashboardPage = new ThreadLocal<>();
    private static final ThreadLocal<SupervisorDeskPage> supervisorDeskPage = new ThreadLocal<>();
    private static final ThreadLocal<DepartmentsManagementPage> departmentsManagementPage = new ThreadLocal<>();
    private static final ThreadLocal<SurveyManagementPage> surveyManagementPage = new ThreadLocal<>();
    private static final ThreadLocal<DashboardSettingsPage> dashboardSettingsPage = new ThreadLocal<>();

    protected static Faker faker = new Faker();
    protected static SoftAssert softAssert = new SoftAssert();

    // -- Getters and Setters -- //

    public static void setCurrentPortalLoginPage(PortalLoginPage loginPage) {
        currentPortalLoginPage.set(loginPage);
    }

    public static PortalLoginPage getCurrentPortalLoginPage() {
        return currentPortalLoginPage.get();
    }

    public String getUserName(String channel) {
        String userName = null;
        switch (channel.toLowerCase()) {
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
                break;
            case "orca":
                userName = ORCASteps.getClientId();
                break;
            case "sms":
                userName = ORCASteps.getSmsSourceId();
                break;
            default:
                throw new AssertionError("Incorrect channel name was provided: " + userName);
        }
        return userName;
    }

    public String getAgentName(String agent) {
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

    public static PortalLoginPage getMainPortalLoginPage(WebDriver driver) {
        if (portalLoginPage.get() == null) {
            portalLoginPage.set(new PortalLoginPage(driver));
        }
        return portalLoginPage.get();
    }

    public static PortalLoginPage getSecondPortalLoginPage(WebDriver driver) {
        if (secondAgentPortalLoginPage.get() == null) {
            secondAgentPortalLoginPage.set(new PortalLoginPage(driver));
        }
        return secondAgentPortalLoginPage.get();
    }

    public static PortalMainPage getAdminPortalMainPage() {
        if (portalMainPage.get() == null) {
            portalMainPage.set(new PortalMainPage(DriverFactory.getDriverForAgent("admin")));
        }
        return portalMainPage.get();
    }

    public static synchronized PortalMainPage getPortalMainPage(String agent) {
        if (agent.equalsIgnoreCase("second agent")) {
            return getSecondPortalMainPage();
        } else {
            return getAdminPortalMainPage();
        }
    }

    public static PortalMainPage getSecondPortalMainPage() {
        if (secondPortalMainPage.get() == null) {
            secondPortalMainPage.set(new PortalMainPage(DriverFactory.getDriverForAgent("second agent")));
        }
        return secondPortalMainPage.get();
    }

    public static void setPortalMainPage(PortalMainPage mainPage) {
        portalMainPage.set(mainPage);
    }

    public static PortalTouchIntegrationsPage getPortalIntegrationsPage() {
        if (portalIntegrationsPage.get() == null) {
            portalIntegrationsPage.set(new PortalTouchIntegrationsPage(DriverFactory.getDriverForAgent("admin")));
        }
        return portalIntegrationsPage.get();
    }

    public static PortalBillingDetailsPage getPortalBillingDetailsPage() {
        if (portalBillingDetailsPage.get() == null) {
            portalBillingDetailsPage.set(new PortalBillingDetailsPage(DriverFactory.getDriverForAgent("admin")));
        }
        return portalBillingDetailsPage.get();
    }

    public static void setPortalBillingDetailsPage(PortalBillingDetailsPage billingDetailsPage) {
        portalBillingDetailsPage.set(billingDetailsPage);
    }

    public static PortalSignUpPage getPortalSignUpPage() {
        if (portalSignUpPage.get() == null) {
            portalSignUpPage.set(new PortalSignUpPage(DriverFactory.getDriverForAgent("admin")));
        }
        return portalSignUpPage.get();
    }

    public static void setPortalSignUpPage(PortalSignUpPage signUpPage) {
        portalSignUpPage.set(signUpPage);
    }

    public PortalAccountDetailsPage getPortalAccountDetailsPage() {
        if (portalAccountDetailsPage.get() == null) {
            portalAccountDetailsPage.set(new PortalAccountDetailsPage(DriverFactory.getDriverForAgent("admin")));
        }
        return portalAccountDetailsPage.get();
    }

    public static PortalManageAgentUsersPage getPortalManagingUsersPage() {
        if (portalManagingUsersPage.get() == null) {
            portalManagingUsersPage.set(new PortalManageAgentUsersPage(DriverFactory.getDriverForAgent("admin")));
        }
        return portalManagingUsersPage.get();
    }

    public static PortalUserEditingPage getPortalUserProfileEditingPage() {
        if (portalUserProfileEditingPage.get() == null) {
            portalUserProfileEditingPage.set(new PortalUserEditingPage(DriverFactory.getDriverForAgent("admin")));
        }
        return portalUserProfileEditingPage.get();
    }

    public static void setPortalUserProfileEditingPage(PortalUserEditingPage userProfileEditingPage) {
        portalUserProfileEditingPage.set(userProfileEditingPage);
    }

    public static PortalTouchPreferencesPage getPortalTouchPreferencesPage() {
        if (portalTouchPreferencesPage.get() == null) {
            portalTouchPreferencesPage.set(new PortalTouchPreferencesPage(DriverFactory.getDriverForAgent("admin")));
        }
        return portalTouchPreferencesPage.get();
    }

    public static PortalUserManagementPage getPortalUserManagementPage() {
        if (portalUserManagementPage.get() == null) {
            portalUserManagementPage.set(new PortalUserManagementPage(DriverFactory.getDriverForAgent("admin")));
        }
        return portalUserManagementPage.get();
    }

    public static DashboardPage getDashboardPage() {
        if (dashboardPage.get() == null) {
            dashboardPage.set(new DashboardPage(DriverFactory.getDriverForAgent("admin")));
        }
        return dashboardPage.get();
    }

    public static SupervisorDeskPage getSupervisorDeskPage() {
        return getChatDeskPage("admin");
    }

    public static SupervisorDeskPage getChatDeskPage(String agent) {
        if (supervisorDeskPage.get() == null) {
            supervisorDeskPage.set(new SupervisorDeskPage(DriverFactory.getDriverForAgent(agent)));
            // ToDo decrease time for spinner wait
            supervisorDeskPage.get().waitForConnectingDisappear(3, 10);
        }
        return supervisorDeskPage.get();
    }

    public static DepartmentsManagementPage getDepartmentsManagementPage() {
        if (departmentsManagementPage.get() == null) {
            departmentsManagementPage.set(new DepartmentsManagementPage(DriverFactory.getDriverForAgent("admin")));
        }
        return departmentsManagementPage.get();
    }

    public static SurveyManagementPage getSurveyManagementPage() {
        if (surveyManagementPage.get() == null) {
            surveyManagementPage.set(new SurveyManagementPage(DriverFactory.getDriverForAgent("admin")));
        }
        return surveyManagementPage.get();
    }

    public static DashboardSettingsPage getDashboardSettingsPage() {
        if (dashboardSettingsPage.get() == null) {
            dashboardSettingsPage.set(new DashboardSettingsPage(DriverFactory.getDriverForAgent("admin")));
        }
        return dashboardSettingsPage.get();
    }

    public static LeftMenu getLeftMenu() {
        return getAdminPortalMainPage().getLeftMenu();
    }

    public static void cleanAllPortalPages() {
        currentPortalLoginPage.remove();
        portalLoginPage.remove();
        secondAgentPortalLoginPage.remove();
        portalMainPage.remove();
        secondPortalMainPage.remove();
        portalIntegrationsPage.remove();
        portalBillingDetailsPage.remove();
        portalSignUpPage.remove();
        portalAccountDetailsPage.remove();
        portalFBIntegrationPage.remove();
        portalManagingUsersPage.remove();
        portalUserProfileEditingPage.remove();
        portalTouchPreferencesPage.remove();
        portalUserManagementPage.remove();
        dashboardPage.remove();
        supervisorDeskPage.remove();
        departmentsManagementPage.remove();
        surveyManagementPage.remove();
        dashboardSettingsPage.remove();
    }
}
