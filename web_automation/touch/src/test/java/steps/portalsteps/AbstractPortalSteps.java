package steps.portalsteps;

import com.github.javafaker.Faker;
import driverfactory.DriverFactory;
import interfaces.DateTimeHelper;
import interfaces.JSHelper;
import interfaces.VerificationHelper;
import interfaces.WebWait;
import org.openqa.selenium.WebDriver;
import portalpages.*;
import portaluielem.LeftMenu;

public class AbstractPortalSteps implements JSHelper, DateTimeHelper, VerificationHelper, WebWait {

    private static ThreadLocal<PortalLoginPage> currentPortalLoginPage = new ThreadLocal<>();

    private static ThreadLocal<PortalLoginPage> portalLoginPage = new ThreadLocal<>();

    private static ThreadLocal<PortalLoginPage> secondAgentPortalLoginPage = new ThreadLocal<>();

    private static ThreadLocal<PortalMainPage> portalMainPage = new ThreadLocal<>();

    private static ThreadLocal<PortalMainPage> secondPortalMainPage = new ThreadLocal<>();

    private static ThreadLocal<PortalTouchIntegrationsPage> portalIntegrationsPage = new ThreadLocal<>();

    private static ThreadLocal<PortalBillingDetailsPage> portalBillingDetailsPage = new ThreadLocal<>();

    private static ThreadLocal<PortalSignUpPage> portalSignUpPage = new ThreadLocal<>();

    private static ThreadLocal<PortalAccountDetailsPage> portalAccountDetailsPage = new ThreadLocal<>();

    private static ThreadLocal<PortalFBIntegrationPage> portalFBIntegrationPage = new ThreadLocal<>();

    private static ThreadLocal<PortalManageAgentUsersPage> portalManagingUsersPage = new ThreadLocal<>();

    private static ThreadLocal<PortalUserEditingPage> portalUserProfileEditingPage = new ThreadLocal<>();

    private static ThreadLocal<PortalTouchPreferencesPage> portalTouchPreferencesPage = new ThreadLocal<>();

    private static ThreadLocal<PortalUserManagementPage> portalUserManagementPage = new ThreadLocal<>();

    private static ThreadLocal<PortalChatConsolePage> portalChatConsolePage = new ThreadLocal<>();

    private static ThreadLocal<ChatConsoleInboxPage> chatConsoleInboxPage = new ThreadLocal<>();

    public static Faker faker = new Faker();

    // -- Getters and Setters -- //

    public static void setCurrentPortalLoginPage(PortalLoginPage loginPage) {
        currentPortalLoginPage.set(loginPage);
    }

    public static PortalLoginPage getCurrentPortalLoginPage() {
        return currentPortalLoginPage.get();
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


    public static PortalTouchIntegrationsPage getPortalIntegrationsPage() {
        if (portalIntegrationsPage.get()==null) {
            portalIntegrationsPage.set(new PortalTouchIntegrationsPage(DriverFactory.getDriverForAgent("admin")));
            return portalIntegrationsPage.get();
        } else{
            return portalIntegrationsPage.get();
        }
    }

    public static void setPortalIntegrationsPage(PortalTouchIntegrationsPage integrationsPage) {
        portalIntegrationsPage.set(integrationsPage);
    }


    public static PortalBillingDetailsPage getPortalBillingDetailsPage() {
        if (portalBillingDetailsPage.get()==null) {
            portalBillingDetailsPage.set(new PortalBillingDetailsPage(DriverFactory.getDriverForAgent("admin")));
            return portalBillingDetailsPage.get();
        } else{
            return portalBillingDetailsPage.get();
        }
    }

    public static void setPortalBillingDetailsPage(PortalBillingDetailsPage billingDetailsPage) {
        portalBillingDetailsPage.set(billingDetailsPage);
    }


    public static PortalSignUpPage getPortalSignUpPage() {
        if (portalSignUpPage.get()==null) {
            portalSignUpPage.set(new PortalSignUpPage(DriverFactory.getDriverForAgent("admin")));
            return portalSignUpPage.get();
        } else{
            return portalSignUpPage.get();
        }
    }

    public static void setPortalSignUpPage(PortalSignUpPage signUpPage) {
        portalSignUpPage.set(signUpPage);
    }


    public PortalAccountDetailsPage getPortalAccountDetailsPage() {
        if (portalAccountDetailsPage.get()==null) {
            portalAccountDetailsPage.set(new PortalAccountDetailsPage(DriverFactory.getDriverForAgent("admin")));
            return portalAccountDetailsPage.get();
        } else{
            return portalAccountDetailsPage.get();
        }
    }

    public void setPortalAccountDetailsPage(PortalAccountDetailsPage accountDetailsPageThreadLocal) {
        portalAccountDetailsPage.set(accountDetailsPageThreadLocal);
    }


    public static PortalFBIntegrationPage getPortalFBIntegrationPage() {
        if (portalFBIntegrationPage.get()==null) {
            portalFBIntegrationPage.set(new PortalFBIntegrationPage(DriverFactory.getDriverForAgent("admin")));
            return portalFBIntegrationPage.get();
        } else{
            return portalFBIntegrationPage.get();
        }
    }

    public static void setPortalFBIntegrationPage(PortalFBIntegrationPage FBIntegrationPage) {
        portalFBIntegrationPage.set(FBIntegrationPage);
    }


    public static PortalManageAgentUsersPage getPortalManagingUsersPage() {
        if (portalManagingUsersPage.get()==null) {
            portalManagingUsersPage.set(new PortalManageAgentUsersPage(DriverFactory.getDriverForAgent("admin")));
            return portalManagingUsersPage.get();
        } else{
            return portalManagingUsersPage.get();
        }
    }

    public static void setPortalManagingUsersPage(PortalManageAgentUsersPage managingUsers) {
        portalManagingUsersPage.set(managingUsers);
    }


    public static PortalUserEditingPage getPortalUserProfileEditingPage() {
        if (portalUserProfileEditingPage.get()==null) {
            portalUserProfileEditingPage.set(new PortalUserEditingPage(DriverFactory.getDriverForAgent("admin")));
            return portalUserProfileEditingPage.get();
        } else{
            return portalUserProfileEditingPage.get();
        }
    }

    public static void setPortalUserProfileEditingPage(PortalUserEditingPage userProfileEditingPage) {
        portalUserProfileEditingPage.set(userProfileEditingPage);
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


    public static PortalUserManagementPage getPortalUserManagementPage() {
        if (portalUserManagementPage.get()==null) {
            portalUserManagementPage.set(new PortalUserManagementPage(DriverFactory.getDriverForAgent("admin")));
            return portalUserManagementPage.get();
        } else{
            return portalUserManagementPage.get();
        }
    }

    public static void setPortalUserManagementPage(PortalUserManagementPage userManagementPage) {
        portalUserManagementPage.set(userManagementPage);
    }


    public static PortalChatConsolePage getPortalChatConsolePage() {
        if (portalChatConsolePage.get()==null) {
            portalChatConsolePage.set(new PortalChatConsolePage(DriverFactory.getDriverForAgent("admin")));
            return portalChatConsolePage.get();
        } else{
            return portalChatConsolePage.get();
        }
    }

    public static void setPortalChatConsolePage(PortalChatConsolePage chatConsolePage) {
       portalChatConsolePage.set(chatConsolePage);
    }


    public static ChatConsoleInboxPage getChatConsoleInboxPage() {
        if (chatConsoleInboxPage.get()==null) {
            chatConsoleInboxPage.set(new ChatConsoleInboxPage(DriverFactory.getDriverForAgent("admin")));
            return chatConsoleInboxPage.get();
        } else{
            return chatConsoleInboxPage.get();
        }
    }

    public static void setChatConsoleInboxPage(ChatConsoleInboxPage chatConsoleInbox) {
        chatConsoleInboxPage.set(chatConsoleInbox);
    }

    public static LeftMenu getLeftMenu() {
        return getAdminPortalMainPage().getLeftMenu();
    }


    public static void cleanAllPortalPages(){
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
        portalChatConsolePage.remove();
        chatConsoleInboxPage.remove();
    }
}
