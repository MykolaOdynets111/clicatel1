package portalpages;

import dbmanager.DBConnector;
import driverfactory.MC2DriverFactory;
import drivermanager.ConfigManager;
import emailhelper.CheckEmail;
import emailhelper.GmailConnector;
import io.qameta.allure.Step;
import mc2api.endpoints.EndpointsPlatform;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.testng.Assert;
import portaluielem.AccountForm;

public class PortalLoginPage extends PortalAbstractPage {

    @FindBy(xpath = "//*[text()='Your account has successfully been created!']")
    private WebElement accountCreatedMessage;

    private String confirmationURL = "none";

    private AccountForm accountForm;

    // == Constructors == //

    public PortalLoginPage() {
        super();
    }
    public PortalLoginPage(String agent) {
        super(agent);
    }
    public PortalLoginPage(WebDriver driver) {
        super(driver);
    }

    public AccountForm getAccountForm(){
        accountForm.setCurrentDriver(this.getCurrentDriver());
        return accountForm;
    }

    public static PortalLoginPage openPortalLoginPage(WebDriver driver) {
        driver.get(EndpointsPlatform.PORTAL_LOGIN_PAGE);
        return new PortalLoginPage(driver);
    }

    public void openLoginPage(WebDriver driver) {
        driver.get(EndpointsPlatform.PORTAL_LOGIN_PAGE);
    }

    public static PortalLoginPage openPortalLoginPage() {
        MC2DriverFactory.getPortalDriver().get(EndpointsPlatform.PORTAL_LOGIN_PAGE);
        return new PortalLoginPage();
    }


    @Step(value = "Log in to portal")
    public PortalMainPage login(String email, String pass){
        waitFor(1000);
        try{
            getAccountForm().enterAdminCreds(email, pass);
            getAccountForm().clickLogin();
        }catch(TimeoutException e){
            getPageHeader().logoutAdmin();
            getAccountForm().enterAdminCreds(email, pass);
            getAccountForm().clickLogin();
        }
        waitWhileProcessing(2, 14);
        return new PortalMainPage(this.getCurrentDriver());
    }

    @Step(value = "Verify Portal Login page opened")
    public  boolean isLoginPageOpened(int wait){
        return getAccountForm().isEmailInputShown(wait);
    }


    @Step(value = "Verify confirmation email arrives")
    public String checkConfirmationEmail(String account, String email, String emailPass, int wait){

        if (ConfigManager.getEnv().equals("testing")){
            String accountId = DBConnector.getAccountIdFromMC2DB(ConfigManager.getEnv(),
                    account);
            String activationID = DBConnector.getAccountActivationIdFromMC2DB(ConfigManager.getEnv(),
                    accountId);
            if(activationID==null) return "none";
            confirmationURL = String.format(EndpointsPlatform.PORTAL_ACCOUNT_ACTIVATION, activationID);
        }else {
            GmailConnector.loginAndGetInboxFolder(email, emailPass);
            confirmationURL = CheckEmail
                    .getConfirmationURL("Clickatell <no-reply@clickatell.com>", wait);
        }
        return confirmationURL;
    }

    @Step(value = "Verify reset password confirmation email arrives")
    public String checkResetPassConfirmationEmail(String email, String emailPass, int wait){

        if (ConfigManager.getEnv().equals("testing")){
            String resetId = DBConnector.getResetPassId(ConfigManager.getEnv(),
                    email);
            if(resetId==null) return "none";
            confirmationURL = EndpointsPlatform.PORTAL_RESET_PASS_URL + resetId;
        }else {
            GmailConnector.loginAndGetInboxFolder(email, emailPass);
            confirmationURL = CheckEmail
                    .getConfirmationURL("Clickatell <no-reply@clickatell.com>", wait);
        }
        return confirmationURL;
    }


    @Step(value = "Open confirmation email")
    public void openConfirmationURL(){
        try {
            MC2DriverFactory.getPortalDriver().get(confirmationURL);
        }catch (WebDriverException e){
            Assert.fail("WebDriver exception faced: \n" +
                    e + "\n" +
            "confirmationURL: " + confirmationURL);
        }
    }


    public void enterEmailAndSubmit(String email){
        getAccountForm().enterEmailAndSubmit(email);
        waitWhileProcessing(2, 4);
    }

    @Step(value = "Verify 'Your account has successfully been created!' message shown")
    public boolean isAccountCreatedMessageShown(){
        return isElementShown(this.getCurrentDriver(), accountCreatedMessage, 3);
    }
}
