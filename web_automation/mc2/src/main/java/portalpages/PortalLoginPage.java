package portalpages;

import dbmanager.DBConnector;
import driverfactory.MC2DriverFactory;
import drivermanager.ConfigManager;
import emailhelper.CheckEmail;
import emailhelper.GmailConnector;
import io.qameta.allure.Step;
import mc2api.EndpointsPlatform;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class PortalLoginPage extends PortalAbstractPage {

    @FindBy(css = "input[type='email']")
    private WebElement emailInput;

    @FindBy(css = "input[type='password']")
    private WebElement passInput;

    @FindAll({
        @FindBy(xpath = "//button[text()='Login']"),
        @FindBy(css = "div.account-form button.button.button-primary")
    })
    private WebElement loginButton;

    @FindBy(css = "div[ng-show='newAccountEmail']")
    private WebElement confirmationEmailMessage;

    @FindBy(css = "input[type='password']")
    private List<WebElement> createPassInput;

    @FindBy(css = "div.invitation-welcome.ng-binding")
    private WebElement invitationWelcomeMsg;

    @FindBy(css = "div.set-new-password")
    private WebElement setNewPasswordLabel;

    @FindBy(xpath = "//*[text()='Your account has successfully been created!']")
    private WebElement accountCreatedMessage;

    private String confirmationURL = "none";


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

    public static PortalLoginPage openPortalLoginPage(WebDriver driver) {
        driver.get(EndpointsPlatform.PORTAL_LOGIN_PAGE);
        return new PortalLoginPage(driver);
    }

    public static PortalLoginPage openPortalLoginPage() {
        MC2DriverFactory.getPortalDriver().get(EndpointsPlatform.PORTAL_LOGIN_PAGE);
        return new PortalLoginPage();
    }


    @Step(value = "Log in to portal")
    public PortalMainPage login(String email, String pass){
        try{
            enterAdminCreds(email, pass);
            clickLogin();
        }catch(TimeoutException e){
            getPageHeader().logoutAdmin();
            enterAdminCreds(email, pass);
            clickLogin();
        }
        waitWhileProcessing(2, 14);
        return new PortalMainPage(this.getCurrentDriver());
    }

    public void enterAdminCreds(String email, String pass){
        waitForElementToBeVisible(this.getCurrentDriver(), emailInput, 6);
        emailInput.sendKeys(email);
        passInput.sendKeys(pass);
//        clickLogin();
    }

    public void clickLogin(){
        clickElem(this.getCurrentDriver(), loginButton, 1, "Login Button" );
    }

    public boolean isMessageAboutConfirmationMailSentShown(){
        return isElementShown(this.getCurrentDriver(), confirmationEmailMessage, 15);
    }

    @Step(value = "Get message about confirmation email sent")
    public String getMessageAboutSendingConfirmationEmail(){
        try {
            return confirmationEmailMessage.getText();
        }catch(NoSuchElementException e){
            return "no elemnt to get the text from";
        }
    }

    @Step(value = "Verify Portal Login page opened")
    public  boolean isLoginPageOpened(int wait){
        return isElementShown(this.getCurrentDriver(), emailInput, wait);
    }

    public boolean areCreatePasswordInputsShown(int wait){
        int numbers = createPassInput.size();
        if(numbers!=2){
            waitFor(wait);
            numbers = createPassInput.size();
        }
        return numbers==2;
    }

    public String getWelcomeMessage(int wait){
        return getTextFromElem(this.getCurrentDriver(), invitationWelcomeMsg, wait,
                "Welcome Text in Login screen");
    }

    public PortalLoginPage createNewPass(String pass){
        for(WebElement elem : createPassInput){
            elem.sendKeys(pass);
        }
        return this;
    }

    public String getNewPasswordLabel(){
        return getTextFromElem(this.getCurrentDriver(), setNewPasswordLabel, 4, "Set new password");
    }

    @Step(value = "Verify confirmation sign up email arrives")
    public String checkConfirmationEmail(String account, String email, String emailPass, int wait){

        if (ConfigManager.getEnv().equals("testing")){
            String activationID = DBConnector.getAccountActivationIdFromMC2DB(ConfigManager.getEnv(),
                    account);
            if(activationID==null) return "none";
            confirmationURL = String.format(EndpointsPlatform.PORTAL_ACCOUNT_ACTIVATION, activationID);;
        }else {
            GmailConnector.loginAndGetInboxFolder(email, emailPass);
            confirmationURL = CheckEmail
                    .getConfirmationURL("Clickatell <no-reply@clickatell.com>", wait);
        }
        return confirmationURL;
    }

    @Step(value = "Open account set up confirmation email")
    public void openConfirmationURL(){
        try {
            MC2DriverFactory.getPortalDriver().get(confirmationURL);
        }catch (WebDriverException e){
            Assert.fail("WebDriver exception faced: \n" +
                    e + "\n" +
            "confirmationURL: " + confirmationURL);
        }
    }

    @Step(value = "Verify 'Your account has successfully been created!' message shown")
    public boolean isAccountCreatedMessageShown(){
        return isElementShown(this.getCurrentDriver(), accountCreatedMessage, 3);
    }
}
