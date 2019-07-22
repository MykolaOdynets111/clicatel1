package steps.portalsteps;

import com.github.javafaker.Faker;
import interfaces.JSHelper;
import portalpages.*;
import portalpages.uielements.LeftMenu;

public class AbstractPortalSteps implements JSHelper {

    private static ThreadLocal<PortalLoginPage> currentPortalLoginPage = new ThreadLocal<>();

    private static ThreadLocal<PortalLoginPage> portalLoginPage = new ThreadLocal<>();

    private static ThreadLocal<PortalLoginPage> secondAgentPortalLoginPage = new ThreadLocal<>();

    private static ThreadLocal<PortalMainPage> portalMainPage = new ThreadLocal<>();

    private static ThreadLocal<PortalIntegrationsPage> portalIntegrationsPage = new ThreadLocal<>();

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
        if (agent.equalsIgnoreCase("second agent")) {
            return getSecondPortalLoginPage();
        } else {
            return getMainPortalLoginPage();
        }
    }

    public static PortalLoginPage getMainPortalLoginPage(){
        if (portalLoginPage.get()==null) {
            portalLoginPage.set(new PortalLoginPage("main"));
            return portalLoginPage.get();
        } else{
            return portalLoginPage.get();
        }
    }

    public static PortalLoginPage getSecondPortalLoginPage(){
        if (secondAgentPortalLoginPage.get()==null) {
            secondAgentPortalLoginPage.set(new PortalLoginPage("second agent"));
            return secondAgentPortalLoginPage.get();
        } else{
            return secondAgentPortalLoginPage.get();
        }
    }


    public static PortalMainPage getPortalMainPage() {
        if (portalMainPage.get()==null) {
            portalMainPage.set(new PortalMainPage("admin"));
            return portalMainPage.get();
        } else{
            return portalMainPage.get();
        }
    }

    public static void setPortalMainPage(PortalMainPage mainPage) {
       portalMainPage.set(mainPage);
    }


    public static PortalIntegrationsPage getPortalIntegrationsPage() {
        if (portalIntegrationsPage.get()==null) {
            portalIntegrationsPage.set(new PortalIntegrationsPage("admin"));
            return portalIntegrationsPage.get();
        } else{
            return portalIntegrationsPage.get();
        }
    }

    public static void setPortalIntegrationsPage(PortalIntegrationsPage integrationsPage) {
        portalIntegrationsPage.set(integrationsPage);
    }


    public static PortalBillingDetailsPage getPortalBillingDetailsPage() {
        if (portalBillingDetailsPage.get()==null) {
            portalBillingDetailsPage.set(new PortalBillingDetailsPage("admin"));
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
            portalSignUpPage.set(new PortalSignUpPage("admin"));
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
            portalAccountDetailsPage.set(new PortalAccountDetailsPage("admin"));
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
            portalFBIntegrationPage.set(new PortalFBIntegrationPage("admin"));
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
            portalManagingUsersPage.set(new PortalManageAgentUsersPage("admin"));
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
            portalUserProfileEditingPage.set(new PortalUserEditingPage("admin"));
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
            portalTouchPreferencesPage.set(new PortalTouchPreferencesPage("admin"));
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
            portalUserManagementPage.set(new PortalUserManagementPage("admin"));
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
            portalChatConsolePage.set(new PortalChatConsolePage("admin"));
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
            chatConsoleInboxPage.set(new ChatConsoleInboxPage("admin"));
            return chatConsoleInboxPage.get();
        } else{
            return chatConsoleInboxPage.get();
        }
    }

    public static void setChatConsoleInboxPage(ChatConsoleInboxPage chatConsoleInbox) {
        chatConsoleInboxPage.set(chatConsoleInbox);
    }

    public static LeftMenu getLeftMenu() {
        return getPortalMainPage().getLeftMenu();
    }


    public static void сleanAllPortalPages(){
        currentPortalLoginPage.remove();
        portalLoginPage.remove();
        secondAgentPortalLoginPage.remove();
        portalMainPage.remove();
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
